package cc;

import cc.files.FileCollector;
import cc.files.FileMatcher;
import org.sonarlint.cli.InputFileFinder;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomInputFileFinder extends InputFileFinder {
    final List<String> includedPaths;
    final Charset charset;
    final FileMatcher fileMatcher;


    public CustomInputFileFinder(String srcGlobPattern, String testsGlobPattern, String excludeGlobPattern, Charset charset) {
        this(new ArrayList<>(), srcGlobPattern, testsGlobPattern, excludeGlobPattern, charset);
    }

    public CustomInputFileFinder(List<String> includedPaths, String srcGlobPattern, String testsGlobPattern, String excludeGlobPattern, Charset charset) {
        super(srcGlobPattern, testsGlobPattern, excludeGlobPattern, charset);
        this.includedPaths = includedPaths;
        this.charset = charset;
        this.fileMatcher = new FileMatcher(srcGlobPattern, testsGlobPattern, excludeGlobPattern, charset);
    }

    @Override
    public List<ClientInputFile> collect(Path baseDir) throws IOException {
        List<Path> paths = new ArrayList<>();
        for (String path : includedPaths) {
            Path resolvedPath = baseDir.resolve(path);
            if (path.endsWith("/")) {
                paths.addAll(collectDir(baseDir, resolvedPath));
            } else {
                paths.add(resolvedPath);
            }
        }
        return paths.stream()
                .map(p -> toFile(baseDir, p))
                .filter(f -> f != null)
                .collect(Collectors.toList());
    }

    ClientInputFile toFile(Path baseDir, Path path) {
        boolean valid = fileMatcher.validatePath(baseDir, path);
        if (valid) {
            return createInputFile(path, fileMatcher.isTest(baseDir, path));
        }
        return null;
    }

    ClientInputFile createInputFile(Path resolvedPath, boolean test) {
        return new DefaultClientInputFile(resolvedPath, test, charset);
    }

    List<Path> collectDir(Path baseDir, Path dir) throws IOException {
        FileCollector collector = new FileCollector(baseDir, fileMatcher);
        Files.walkFileTree(dir, collector);
        return collector.getFiles();
    }
}
