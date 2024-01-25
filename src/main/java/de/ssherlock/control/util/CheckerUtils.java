package de.ssherlock.control.util;

import de.ssherlock.control.exception.CheckerExecutionException;
import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.SubmissionFile;
import de.ssherlock.global.transport.User;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for running various checkers on a set of submission files.
 *
 * @author Victor Vollmann
 */
public final class CheckerUtils {

    /**
     * The Logger instance of this class.
     */
    private static final SerializableLogger LOGGER = LoggerCreator.get(CheckerUtils.class);

    /**
     * Default constructor.
     */
    private CheckerUtils() {}

    /**
     * Runs all checkers depending on their CheckerType.
     *
     * @param checkers        The list of checkers to run.
     * @param submissionFiles The list of files to run the checkers on.
     * @param user            The user for whom the checkers are run.
     * @param resultConsumer  The consumer handling the results.
     */
    public static void runCheckers(
            List<Checker> checkers, List<SubmissionFile> submissionFiles, User user, Consumer<CheckerResult> resultConsumer) {
        LOGGER.finer("Start running checkers.");
        submissionFiles.sort(Comparator.comparing(SubmissionFile::getName));

        ExecutorService executorService = Executors.newFixedThreadPool(checkers.size());
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (Checker checker : checkers) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                CheckerResult result;
                try {
                    result = runChecker(checker, submissionFiles, user);
                } catch (CheckerExecutionException e) {
                    result = new CheckerResult();
                    result.setChecker(checker);
                    result.setPassed(false);
                    result.setStackTrace(e.getMessage());
                }
                resultConsumer.accept(result);
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();
    }

    /**
     * Runs a checkers depending on its CheckerType.
     *
     * @param checker         The checker to run.
     * @param submissionFiles The files to check.
     * @param user            The associated user.
     * @return The Checker result.
     * @throws CheckerExecutionException when the Checker Type is not set.
     */
    private static CheckerResult runChecker(Checker checker, List<SubmissionFile> submissionFiles, User user) throws CheckerExecutionException {
        switch (checker.getCheckerType()) {
        case COMPILATION -> {
            return runCompilationChecker(checker, submissionFiles);
        }
        case IDENTITY -> {
            return runIdentityChecker(checker, submissionFiles, user);
        }
        case LINE_WIDTH -> {
            return runLineWidthChecker(checker, submissionFiles);
        }
        case USER_DEFINED -> {
            return runUserDefinedChecker(checker, submissionFiles);
        }
        default -> {
            throw new CheckerExecutionException("The Checker Type was not set.");
        }
        }
    }

    /**
     * Runs the compilation checker on the provided submission files using the given checker.
     *
     * @param checker         The checker to be used.
     * @param submissionFiles The list of submission files to be checked.
     * @return The result of the compilation checker.
     */
    private static CheckerResult runCompilationChecker(
            Checker checker, List<SubmissionFile> submissionFiles) throws CheckerExecutionException {
        LOGGER.finer("Start running compilation checker " + checker.getName() + ".");
        CheckerResult checkerResult = new CheckerResult();
        checkerResult.setChecker(checker);
        List<String> filePaths = saveJavaClasses(checker, submissionFiles);
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        if (compileClasses(filePaths, diagnostics, getClasspath(checker))) {
            checkerResult.setPassed(true);
            checkerResult.setStackTrace("All files compiled successfully.");
            LOGGER.finer("Compilation checker ran successfully.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                if (diagnostic.getSource() == null || diagnostic.getKind() != Diagnostic.Kind.ERROR) {
                    continue;
                }
                sb.append(extractClassName(diagnostic.getSource().toString())).append(": ");
                sb.append(diagnostic.getMessage(null)).append("\n");
            }
            checkerResult.setPassed(false);
            checkerResult.setStackTrace(sb.toString());
            LOGGER.finer("Compilation checker failed.");
        }
        return checkerResult;
    }

    /**
     * Runs the identity checker on the provided submission files using the given checker.
     *
     * @param checker         The checker to be used.
     * @param submissionFiles The list of submission files to be checked.
     * @param user            The user for whom the checker should be run.
     * @return The result of the identity checker.
     */
    private static CheckerResult runIdentityChecker(
            Checker checker, List<SubmissionFile> submissionFiles, User user) {
        LOGGER.finer("Start running identity checker " + checker.getName() + ".");
        CheckerResult result = new CheckerResult();
        result.setChecker(checker);
        StringBuilder sb = new StringBuilder();

        for (SubmissionFile file : submissionFiles) {
            String fileContents = new String(file.getBytes(), StandardCharsets.UTF_8);
            String[] identityContent = {
                    user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName()
            };
            String regexPattern = String.join("|", identityContent);
            Pattern pattern = Pattern.compile(regexPattern);
            String[] lines = fileContents.split("\\r?\\n");
            for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
                String line = lines[lineNumber];
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    sb.append("Found '")
                      .append(matcher.group())
                      .append("' in line ")
                      .append(lineNumber + 1)
                      .append(" of file ")
                      .append(file.getName())
                      .append("\n");
                }
            }
        }
        if (sb.isEmpty()) {
            sb.append("No identity content found in files.\nThe checker ran successfully.\n");
            result.setPassed(true);
            LOGGER.finer("Identity checker ran successfully.");
        } else {
            result.setPassed(false);
            LOGGER.finer("Identity checker failed.");
        }
        result.setStackTrace(sb.toString());
        return result;
    }

    /**
     * Runs the spacing checker on the provided submission files using the given checker.
     *
     * @param checker         The checker to be used.
     * @param submissionFiles The list of submission files to be checked.
     * @return The result of the spacing checker.
     */
    private static CheckerResult runLineWidthChecker(
            Checker checker, List<SubmissionFile> submissionFiles) {
        LOGGER.finer("Start line-width checker " + checker.getName() + ".");
        CheckerResult result = new CheckerResult();
        result.setChecker(checker);
        StringBuilder sb = new StringBuilder();
        int maxLineWidth = Integer.parseInt(checker.getParameterOne());

        for (SubmissionFile file : submissionFiles) {
            String fileContents = new String(file.getBytes(), StandardCharsets.UTF_8);
            String[] lines = fileContents.split("\\r?\\n");
            for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
                String line = lines[lineNumber];
                if (line.length() > maxLineWidth) {
                    sb.append("Line ")
                      .append(lineNumber + 1)
                      .append(" in file ")
                      .append(file.getName())
                      .append(" exceeds the maximum line width (")
                      .append(maxLineWidth)
                      .append(" characters).\n");
                }
            }
        }
        if (sb.isEmpty()) {
            sb.append("All lines adhere to the maximum line width of ")
              .append(maxLineWidth)
              .append(" characters.\nThe Checker ran successfully.\n");
            result.setPassed(true);
            LOGGER.finer("Line Width checker " + checker.getName() + " ran successfully.");
        } else {
            result.setPassed(false);
            LOGGER.finer("Line Width checker " + checker.getName() + " failed.");
        }
        result.setStackTrace(sb.toString());
        return result;
    }

    /**
     * Runs a user-defined checker on the provided submission files using the given checker.
     *
     * @param checker         The checker to be used.
     * @param submissionFiles The list of submission files to be checked.
     * @return The result of the user-defined checker.
     * @throws CheckerExecutionException When there is an error during execution.
     */
    private static CheckerResult runUserDefinedChecker(
            Checker checker, List<SubmissionFile> submissionFiles) throws CheckerExecutionException {
        CheckerResult result = new CheckerResult();
        result.setChecker(checker);

        List<String> filePaths = saveJavaClasses(checker, submissionFiles);

        StringBuilder sb = new StringBuilder();
        if (!compileClasses(filePaths, null, getClasspath(checker))) {
            sb.append("The Checker: ")
              .append(checker.getName())
              .append(" was not executed due to a compilation error.");
            result.setPassed(false);
            result.setStackTrace(sb.toString());
            return result;
        }

        String input = checker.getParameterOne();
        String actualOutput = runWithInput(filePaths, input, checker, getClasspath(checker));

        String expectedOutput = checker.getParameterTwo();
        if (expectedOutput.equals(actualOutput)) {
            result.setPassed(true);
            sb.append("User Defined Checker ran successfully.");
            LOGGER.finer("User defined checker ran successfully.");
        } else {
            result.setPassed(false);
            sb.append("Checker ")
              .append("did not run successfully.\nExpected:\n")
              .append(expectedOutput)
              .append("\nActual:\n")
              .append(actualOutput);
            LOGGER.finer("User defined checker failed.");
        }

        result.setChecker(checker);
        result.setStackTrace(sb.toString());
        return result;
    }

    /**
     * Extracts the class name of a given file. For example com/example/main/Main.java -> Main
     *
     * @param fileName The name of the file.
     * @return The class name.
     * @throws CheckerExecutionException When there is an error during execution.
     */
    private static String extractClassName(String fileName) throws CheckerExecutionException {
        int lastSlashIndex = fileName.lastIndexOf('/');
        int lastBackslashIndex = fileName.lastIndexOf('\\');
        int firstDotIndex = fileName.indexOf('.', lastSlashIndex);
        if (lastBackslashIndex > lastSlashIndex && firstDotIndex >= 0 && lastBackslashIndex > firstDotIndex) {
            LOGGER.info(fileName);
            return fileName.substring(lastBackslashIndex + 1, firstDotIndex);
        }
        if (firstDotIndex >= 0) {
            return fileName.substring(lastSlashIndex + 1, firstDotIndex);
        } else {
            LOGGER.finer("File " + fileName + " could not be saved.");
            throw new CheckerExecutionException("Invalid file path format.");
        }
    }

    /**
     * Saves the given classes to an appropriate temp directory.
     *
     * @param checker The associated checker.
     * @param files   The files to save.
     * @return A list of paths to the saved classes.
     * @throws CheckerExecutionException When there is an error during execution.
     */
    private static List<String> saveJavaClasses(Checker checker, List<SubmissionFile> files) throws CheckerExecutionException {
        LOGGER.finest("Saving files " + files + " for checker " + checker.getName());
        List<String> filePaths = new ArrayList<>();
        for (SubmissionFile file : files) {
            try {
                Path parentPath = Paths.get(file.getName()).getParent();
                String suffix = parentPath != null ? String.valueOf(parentPath) : "";
                String tempDirectory = getTempDirectory(checker) + "/" + suffix;

                File tempDir = new File(tempDirectory);
                if (!tempDir.exists()) {
                    boolean dirsCreated = tempDir.mkdirs();
                    if (!dirsCreated) {
                        throw new CheckerExecutionException("Failed to create directories for temporary files.");
                    }
                }
                String className = extractClassName(file.getName());
                File tempFile = new File(tempDirectory, className + ".java");
                Files.write(tempFile.toPath(), file.getBytes());
                filePaths.add(tempFile.getPath());
            } catch (IOException e) {
                throw new CheckerExecutionException("The uploaded files could not be saved.", e);
            }
        }
        return filePaths;
    }

    /**
     * Gets the correct temp directory for a given checker.
     *
     * @param checker The associated checker.
     * @return The path to the temp directory.
     */
    private static String getTempDirectory(Checker checker) {
        return System.getProperty("java.io.tmpdir")
               + File.separator
               + "submission"
               + File.separator
               + checker.hashCode();
    }

    /**
     * Compiles a list of given classes and sets the diagnostics.
     *
     * @param filePaths   The classes to compile.
     * @param diagnostics The diagnostics collector.
     * @param classpath  The classpath.
     * @return Whether the files compiled successfully.
     * @throws CheckerExecutionException When there is an error during execution.
     */
    private static boolean compileClasses(
            List<String> filePaths, DiagnosticCollector<JavaFileObject> diagnostics, String classpath) throws CheckerExecutionException {
        LOGGER.finest("Start compiling classes " + filePaths + ".");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager fileManager =
                     compiler.getStandardFileManager(diagnostics, null, null)) {
            List<String> optionList = new ArrayList<>(Arrays.asList("-classpath", classpath));
            JavaCompiler.CompilationTask task =
                    compiler.getTask(
                            null,
                            fileManager,
                            diagnostics,
                            optionList,
                            null,
                            fileManager.getJavaFileObjectsFromStrings(filePaths));
            if (task.call()) {
                LOGGER.finest("Compiling classes was successful.");
                return true;
            }
        } catch (IOException e) {
            LOGGER.finest("Compiling classes has failed.\n" + e.getMessage());
            throw new CheckerExecutionException("Encountered an exception during compilation", e);
        }
        return false;
    }

    /**
     * Runs a java program using the given files and the input command.
     *
     * @param filePaths The files to execute.
     * @param input     The input command.
     * @param checker   The associated checker.
     * @param classpath The classpath.
     * @return The execution output.
     * @throws CheckerExecutionException When there is an error during execution.
     */
    private static String runWithInput(List<String> filePaths, String input, Checker checker, String classpath) throws CheckerExecutionException {
        LOGGER.finest("Start running files " + filePaths + " for checker " + checker.getName());
        try {
            String[] commandParts = input.split("\\s+");
            for (String filePath : filePaths) {
                if (filePath.endsWith(File.separator + commandParts[1])) {
                    commandParts[1] = filePath;
                    break;
                }
            }
            ProcessBuilder processBuilder = new ProcessBuilder(commandParts);
            processBuilder.environment().put("CLASSPATH", classpath);
            processBuilder.directory(new File(getTempDirectory(checker)));
            Process process = processBuilder.start();

            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));

            StringBuilder output = new StringBuilder();
            StringBuilder errorOutput = new StringBuilder();

            String line;
            while ((line = outputReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            if (!output.isEmpty()) {
                output.deleteCharAt(output.length() - 1);
            }

            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line.replace("\t", "    ")).append("\n");
            }
            if (!errorOutput.isEmpty()) {
                errorOutput.deleteCharAt(output.length() - 1);
            }

            outputReader.close();
            errorReader.close();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return output.append(errorOutput).toString();
            }
            LOGGER.finest("Running classes for checker was successful.");
            return output.toString();
        } catch (IOException | InterruptedException e) {
            LOGGER.finest("Running classes for checker " + checker.getName() + " failed.\n" + e.getMessage());
            throw new CheckerExecutionException("Running classes encountered an exception.", e);
        }
    }

    /**
     * Gets the classpath.
     *
     * @param checker The checker.
     * @return The classpath.
     */
    private static String getClasspath(Checker checker) {
        String baseDir = getTempDirectory(checker);
        Set<String> directoriesWithJavaFiles = new HashSet<>();
        findDirectoriesWithJavaFiles(new File(baseDir), directoriesWithJavaFiles, baseDir);
        return String.join(File.pathSeparator, directoriesWithJavaFiles);
    }

    /**
     * Finds all subdirectories that contain java files.
     *
     * @param dir All directories.
     * @param directoriesWithJavaFiles The found subdirectories.
     * @param baseDir The base directory.
     */
    private static void findDirectoriesWithJavaFiles(File dir, Set<String> directoriesWithJavaFiles, String baseDir) {
        File[] files = dir.listFiles();
        if (files != null) {
            boolean javaFileFound = false;
            for (File file : files) {
                if (file.isDirectory()) {
                    findDirectoriesWithJavaFiles(file, directoriesWithJavaFiles, baseDir);
                } else if (file.getName().endsWith(".java")) {
                    javaFileFound = true;
                }
            }
            if (javaFileFound) {
                directoriesWithJavaFiles.add(dir.getAbsolutePath());
                directoriesWithJavaFiles.add(dir.getAbsolutePath());
            }
        }
    }
}
