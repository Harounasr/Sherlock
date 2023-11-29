package de.ssherlock.control.util;

import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.CheckerType;
import de.ssherlock.global.transport.SubmissionFile;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for running various checkers on a set of submission files.
 *
 * @author Leon Höfling
 */
public class CheckerUtils {

    /**
     * Default constructor.
     */
    private CheckerUtils() {

    }

    /**
     * Runs all checkers depending on their CheckerType.
     *
     * @param checkers        The list of checkers to run.
     * @param submissionFiles The list of files to run the checkers on.
     * @return The results of the checkers.
     */
    public static List<CheckerResult> runCheckers(List<Checker> checkers, List<SubmissionFile> submissionFiles) {
        List<CheckerResult> results = new ArrayList<>();
        for (Checker checker : checkers) {
            switch (checker.getCheckerType()) {
            case COMPILATION -> results.add(runCompilationChecker(checker, submissionFiles));
            case IDENTITY -> results.add(runIdentityChecker(checker, submissionFiles));
            case LINE_WIDTH -> results.add(runSpacingChecker(checker, submissionFiles));
            case USER_DEFINED -> results.add(runUserDefinedChecker(checker, submissionFiles));
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
    private static CheckerResult runCompilationChecker(Checker checker, List<SubmissionFile> submissionFiles) {
        if (checker.getCheckerType() != CheckerType.COMPILATION) {
            throw new IllegalArgumentException();
        }
        CheckerResult checkerResult = new CheckerResult();
        checkerResult.setChecker(checker);
        List<String> filePaths = new ArrayList<>();
        for (SubmissionFile file : submissionFiles) {
            try {
                String className = extractClassName(file.getName());

                String tempDirectory = System.getProperty("java.io.tmpdir") + File.separator + "submission" +
                                       File.separator + checker.hashCode() + File.separator + Paths.get(file.getName()).getParent();

                File tempDir = new File(tempDirectory);
                if (!tempDir.exists()) {
                    tempDir.mkdirs();
                }

                File tempFile = new File(tempDirectory, className + ".java");
                Files.write(tempFile.toPath(), file.getBytes());

                filePaths.add(tempFile.getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null,
                                                                 fileManager.getJavaFileObjectsFromStrings(filePaths));
            if (task.call()) {
                checkerResult.setPassed(true);
                checkerResult.setStackTrace("All files compiled successfully.");
            } else {
                StringBuilder sb = new StringBuilder();
                // TODO: Do not append the absolute path to the source.
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    sb.append(diagnostic.getSource().toString()).append(": ");
                    sb.append(diagnostic.getMessage(null)).append("\n");
                }
                checkerResult.setPassed(false);
                checkerResult.setStackTrace(sb.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return checkerResult;
    }

    /**
     * Runs the identity checker on the provided submission files using the given checker.
     *
     * @param checker         The checker to be used.
     * @param submissionFiles The list of submission files to be checked.
     * @return The result of the identity checker.
     */
    private static CheckerResult runIdentityChecker(Checker checker, List<SubmissionFile> submissionFiles) {
        return null;
    }

    /**
     * Runs the spacing checker on the provided submission files using the given checker.
     *
     * @param checker         The checker to be used.
     * @param submissionFiles The list of submission files to be checked.
     * @return The result of the spacing checker.
     */
    private static CheckerResult runSpacingChecker(Checker checker, List<SubmissionFile> submissionFiles) {
        return null;
    }

    /**
     * Runs a user-defined checker on the provided submission files using the given checker.
     *
     * @param checker         The checker to be used.
     * @param submissionFiles The list of submission files to be checked.
     * @return The result of the user-defined checker.
     */
    private static CheckerResult runUserDefinedChecker(Checker checker, List<SubmissionFile> submissionFiles) {
        return null;
    }

    /**
     * Extracts the class name of a given file.
     * For example com/example/main/Main.java -> Main
     *
     * @param fileName The name of the file.
     * @return The class name.
     */
    private static String extractClassName(String fileName) {
        int lastSlashIndex = fileName.lastIndexOf('/');
        int firstDotIndex = fileName.indexOf('.', lastSlashIndex);

        if (lastSlashIndex >= 0 && firstDotIndex >= 0) {
            return fileName.substring(lastSlashIndex + 1, firstDotIndex);
        } else {
            throw new IllegalArgumentException("Invalid file path format");
        }
    }
}

