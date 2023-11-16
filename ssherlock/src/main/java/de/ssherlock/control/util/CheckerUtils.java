package de.ssherlock.control.util;

import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;

import java.io.File;
import java.util.List;

/**
 * Utility class for running various checkers on a set of submission files.
 */
public class CheckerUtils {

    /**
     * Runs the compilation checker on the provided submission files using the given checker.
     *
     * @param checker          The checker to be used.
     * @param submissionFiles  The list of submission files to be checked.
     * @return The result of the compilation checker.
     */
    public static CheckerResult runCompilationChecker(Checker checker, List<File> submissionFiles) {
        return null;
    }

    /**
     * Runs the identity checker on the provided submission files using the given checker.
     *
     * @param checker          The checker to be used.
     * @param submissionFiles  The list of submission files to be checked.
     * @return The result of the identity checker.
     */
    public static CheckerResult runIdentityChecker(Checker checker, List<File> submissionFiles) {
        return null;
    }

    /**
     * Runs the spacing checker on the provided submission files using the given checker.
     *
     * @param checker          The checker to be used.
     * @param submissionFiles  The list of submission files to be checked.
     * @return The result of the spacing checker.
     */
    public static CheckerResult runSpacingChecker(Checker checker, List<File> submissionFiles) {
        return null;
    }

    /**
     * Runs a user-defined checker on the provided submission files using the given checker.
     *
     * @param checker          The checker to be used.
     * @param submissionFiles  The list of submission files to be checked.
     * @return The result of the user-defined checker.
     */
    public static CheckerResult runUserDefinedChecker(Checker checker, List<File> submissionFiles) {
        return null;
    }
}

