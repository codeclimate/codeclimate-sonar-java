package cc.files;

import org.sonarlint.cli.InputFileFinder;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

public class FileMatcher {
    private static final String GLOB_PREFIX = "glob:";
    public static PathMatcher ACCEPT_ALL = p -> true;
    public static PathMatcher REFUSE_ALL = p -> false;

    final PathMatcher srcMatcher;
    final PathMatcher testsMatcher;
    final PathMatcher excludeMatcher;
    final Charset charset;

    public FileMatcher(PathMatcher srcMatcher, PathMatcher testsMatcher, PathMatcher excludeMatcher, Charset charset) {
        this.srcMatcher = srcMatcher;
        this.testsMatcher = testsMatcher;
        this.excludeMatcher = excludeMatcher;
        this.charset = charset;
    }

    public FileMatcher(String srcGlobPattern, String testsGlobPattern, String excludeGlobPattern, Charset charset) {
        this(create(srcGlobPattern, ACCEPT_ALL), create(testsGlobPattern, REFUSE_ALL), create(excludeGlobPattern, REFUSE_ALL), charset);
    }

    public boolean validatePath(Path baseDir, Path absoluteFilePath) {
        Path relativeFilePath = baseDir.relativize(absoluteFilePath);
        boolean isSrc = srcMatcher.matches(absoluteFilePath) || srcMatcher.matches(relativeFilePath);
        boolean isExcluded = excludeMatcher.matches(absoluteFilePath) || excludeMatcher.matches(relativeFilePath);
        return isSrc && !isExcluded;
    }

    public boolean isTest(Path baseDir, Path absoluteFilePath) {
        Path relativeFilePath = baseDir.relativize(absoluteFilePath);
        return testsMatcher.matches(absoluteFilePath) || testsMatcher.matches(relativeFilePath);
    }

    static PathMatcher create(String pattern, PathMatcher defaultMatcher) {
        try {
            if (pattern != null) {
                return FileSystems.getDefault().getPathMatcher(GLOB_PREFIX + pattern);
            } else {
                return defaultMatcher;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating create with pattern: " + pattern, e);
        }
    }
}