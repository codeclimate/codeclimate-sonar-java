package cc.files;

import cc.CustomInputFileFinder;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileCollector extends SimpleFileVisitor<Path> {
    final FileMatcher fileMatcher;
    final List<Path> files;
    final Path baseDir;

    public FileCollector(Path baseDir, FileMatcher fileMatcher) {
        this.fileMatcher = fileMatcher;
        this.baseDir = baseDir;
        this.files = new ArrayList<>();
    }

    public List<Path> getFiles() {
        return files;
    }

    @Override
    public FileVisitResult visitFile(final Path file, BasicFileAttributes attrs) throws IOException {
        boolean valid = fileMatcher.validatePath(baseDir, file);
        if (valid) {
            files.add(file);
        }
        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (Files.isHidden(dir)) {
            return FileVisitResult.SKIP_SUBTREE;
        }

        return super.preVisitDirectory(dir, attrs);
    }
}
