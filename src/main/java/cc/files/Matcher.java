package cc.files;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

public class Matcher {
    static final String GLOB_PREFIX = "glob:";
    static PathMatcher REFUSE_ALL = p -> false;

    final PathMatcher pathMatcher;
    final Charset charset;

    public Matcher(PathMatcher pathMatcher, Charset charset) {
        this.pathMatcher = pathMatcher;
        this.charset = charset;
    }

    public Matcher(String testsGlobPattern, Charset charset) {
        this(createPathMatcher(testsGlobPattern, REFUSE_ALL), charset);
    }

    public boolean match(Path baseDir, Path absoluteFilePath) {
        Path relativeFilePath = baseDir.relativize(absoluteFilePath);
        return pathMatcher.matches(absoluteFilePath) || pathMatcher.matches(relativeFilePath);
    }

    static PathMatcher createPathMatcher(String pattern, PathMatcher defaultMatcher) {
        try {
            if (pattern != null) {
                return FileSystems.getDefault().getPathMatcher(GLOB_PREFIX + pattern);
            } else {
                return defaultMatcher;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating testMatcher with pattern: " + pattern, e);
        }
    }
}