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
import java.util.Comparator;
import java.util.List;
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
     * @return The results of the checkers.
     */
    public static List<CheckerResult> runCheckers(
            List<Checker> checkers, List<SubmissionFile> submissionFiles, User user) throws CheckerExecutionException {
        LOGGER.finer("Start running checkers.");
        submissionFiles.sort(Comparator.comparing(SubmissionFile::getName));
        List<CheckerResult> results = new ArrayList<>();
        for (Checker checker : checkers) {
            switch (checker.getCheckerType()) {
            case COMPILATION -> results.add(runCompilationChecker(checker, submissionFiles));
            case IDENTITY -> results.add(runIdentityChecker(checker, submissionFiles, user));
            case LINE_WIDTH -> results.add(runLineWidthChecker(checker, submissionFiles));
            case USER_DEFINED -> results.add(runUserDefinedChecker(checker, submissionFiles));
            default -> {
                return null;
            }
            }
        }
        return results;
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

        if (compileClasses(filePaths, diagnostics)) {
            checkerResult.setPassed(true);
            checkerResult.setStackTrace("All files compiled successfully.");
            LOGGER.finer("Compilation checker " + checker.getName() + " ran successfully.");
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
            LOGGER.finer("Compilation checker " + checker.getName() + " failed.");
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
            LOGGER.finer("Identity checker " + checker.getName() + " ran successfully.");
        } else {
            result.setPassed(false);
            LOGGER.finer("Identity checker " + checker.getName() + " failed.");

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
              .append(" characters.\nThe Checker ")
              .append(checker.getName())
              .append(" ran successfully.\n");
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
     */
    private static CheckerResult runUserDefinedChecker(
            Checker checker, List<SubmissionFile> submissionFiles) throws CheckerExecutionException {
        LOGGER.finer("Start user-defined checker " + checker.getName() + ".");
        CheckerResult result = new CheckerResult();
        result.setChecker(checker);
        StringBuilder sb = new StringBuilder();

        String input = checker.getParameterOne();
        String expectedOutput = checker.getParameterTwo();
        List<String> filePaths = saveJavaClasses(checker, submissionFiles);

        if (!compileClasses(filePaths, null)) {
            sb.append("The Checker: ")
              .append(checker.getName())
              .append(" was not executed due to a compilation error.");
            result.setPassed(false);
            result.setStackTrace(sb.toString());
            return result;
        }

        String actualOutput = runWithInput(filePaths, input, checker);

        if (expectedOutput.equals(actualOutput)) {
            result.setPassed(true);
            sb.append("Checker ").append(checker.getName()).append(" ran successfully.");
            LOGGER.finer("User defined checker " + checker.getName() + " ran successfully.");
        } else {
            result.setPassed(false);
            sb.append("Checker ")
              .append(checker.getName())
              .append(" did not run successfully.\nExpected:\n")
              .append(expectedOutput)
              .append("\nActual:\n")
              .append(actualOutput);
            LOGGER.finer("User defined checker " + checker.getName() + " failed.");
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
     */
    private static String extractClassName(String fileName) {
        int lastSlashIndex = fileName.lastIndexOf('/');
        int lastBackslashIndex = fileName.lastIndexOf('\\');
        int firstDotIndex = fileName.indexOf('.', lastSlashIndex);
        if (lastBackslashIndex > lastSlashIndex && firstDotIndex >= 0) {
            return fileName.substring(lastBackslashIndex + 1, firstDotIndex);
        }
        if (firstDotIndex >= 0) {
            return fileName.substring(lastSlashIndex + 1, firstDotIndex);
        } else {
            LOGGER.finer("File " + fileName + " could not be saved.");
            throw new IllegalArgumentException("Invalid file path format");
        }
    }

    /**
     * Saves the given classes to an appropriate temp directory.
     *
     * @param checker The associated checker.
     * @param files   The files to save.
     * @return A list of paths to the saved classes.
     */
    private static List<String> saveJavaClasses(Checker checker, List<SubmissionFile> files) throws CheckerExecutionException {
        LOGGER.finest("Saving files " + files + " for checker " + checker.getName());
        List<String> filePaths = new ArrayList<>();
        for (SubmissionFile file : files) {
            try {
                String className = extractClassName(file.getName());
                Path parentPath = Paths.get(file.getName()).getParent();
                String suffix = parentPath != null ? String.valueOf(parentPath) : "";
                String tempDirectory = getTempDirectory(checker) + suffix;

                File tempDir = new File(tempDirectory);
                if (!tempDir.exists()) {
                    tempDir.mkdirs();
                }
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
     * @return Whether the files compiled successfully.
     */
    private static boolean compileClasses(
            List<String> filePaths, DiagnosticCollector<JavaFileObject> diagnostics) throws CheckerExecutionException {
        LOGGER.finest("Start compiling classes " + filePaths + ".");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager fileManager =
                     compiler.getStandardFileManager(diagnostics, null, null)) {
            JavaCompiler.CompilationTask task =
                    compiler.getTask(
                            null,
                            fileManager,
                            diagnostics,
                            null,
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
     * @return The execution output.
     */
    private static String runWithInput(List<String> filePaths, String input, Checker checker) throws CheckerExecutionException {
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
            processBuilder.directory(new File(getTempDirectory(checker)));
            Process process = processBuilder.start();

            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder output = new StringBuilder();
            StringBuilder errorOutput = new StringBuilder();

            String line;
            while ((line = outputReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }

            outputReader.close();
            errorReader.close();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return output.append(errorOutput).toString();
            }
            LOGGER.finest("Running classes for checker " + checker.getName() + " was successful.");
            return output.toString();
        } catch (IOException | InterruptedException e) {
            LOGGER.finest("Running classes for checker " + checker.getName() + " failed.\n" + e.getMessage());
            throw new CheckerExecutionException("Running classes encountered an exception.", e);
        }
    }
}
