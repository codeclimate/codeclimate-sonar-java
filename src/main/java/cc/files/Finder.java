package cc.files;

import org.sonarlint.cli.InputFileFinder;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.nio.file.Files.isDirectory;

public class Finder extends InputFileFinder {
    final List<String> includedPaths;
    final Charset charset;
    final Matcher matcher;

    public Finder(List<String> includedPaths, String testsGlobPattern, Charset charset) {
        super(null, testsGlobPattern, null, charset);
        this.includedPaths = includedPaths;
        this.charset = charset;
        this.matcher = new Matcher(testsGlobPattern, charset);
    }

    @Override
    public List<ClientInputFile> collect(Path baseDir) throws IOException {
        return findPaths(baseDir).stream()
                .map(path -> toClientInputFile(baseDir, path))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    List<Path> findPaths(Path baseDir) throws IOException {
        List<Path> paths = new ArrayList<>();
        for (String path : includedPaths) {
            Path resolvedPath = baseDir.resolve(path);
            if (isDirectory(resolvedPath)) {
                paths.addAll(collectDir(baseDir, resolvedPath));
            } else {
                paths.add(resolvedPath);
            }
        }
        return paths;
    }

    ClientInputFile toClientInputFile(Path baseDir, Path path) {
        return createInputFile(path, isTest(baseDir, path));
    }

    boolean isTest(Path baseDir, Path path) {
        return matcher.isTest(baseDir, path);
    }

    ClientInputFile createInputFile(Path resolvedPath, boolean test) {
        return new DefaultClientInputFile(resolvedPath, test, charset);
    }

    List<Path> collectDir(Path baseDir, Path dir) throws IOException {
        Collector collector = new Collector(baseDir);
        Files.walkFileTree(dir, collector);
        return collector.getFiles();
    }
}