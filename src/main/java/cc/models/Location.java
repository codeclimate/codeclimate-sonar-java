package cc.models;

import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;

class Location {
    final String path;
    final Lines lines;

    public Location(String path, Integer startLine, Integer endLine) {
        this.path = path.replaceFirst("^/tmp/code/", "");
        this.lines = new Lines(startLine, endLine);
    }

    public static Location from(Issue issue) {
        ClientInputFile inputFile = issue.getInputFile();

        if (inputFile == null || inputFile.getPath() == null ||
                issue.getStartLine() == null || issue.getEndLine() == null) {
            throw new IllegalArgumentException("Impossible to identify issue's location");
        }

        return new Location(inputFile.getPath(), issue.getStartLine(), issue.getEndLine());
    }
}
