package cc.files;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

public class Matcher {
    private static final String GLOB_PREFIX = "glob:";
    public static PathMatcher REFUSE_ALL = p -> false;

    final PathMatcher testsMatcher;
    final PathMatcher excludeMatcher;
    final Charset charset;

    public Matcher(PathMatcher testsMatcher, PathMatcher excludeMatcher, Charset charset) {
        this.testsMatcher = testsMatcher;
        this.excludeMatcher = excludeMatcher;
        this.charset = charset;
    }

    public Matcher(String testsGlobPattern, String excludeGlobPattern, Charset charset) {
        this(create(testsGlobPattern, REFUSE_ALL), create(excludeGlobPattern, REFUSE_ALL), charset);
    }

    public boolean validatePath(Path baseDir, Path absoluteFilePath) {
        Path relativeFilePath = baseDir.relativize(absoluteFilePath);
        boolean isExcluded = excludeMatcher.matches(absoluteFilePath) || excludeMatcher.matches(relativeFilePath);
        return !isExcluded;
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
            throw new RuntimeException("Error creating matcher with pattern: " + pattern, e);
        }
    }
}