package cc.models;

import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;

class Location {
    final String path;
    final Lines lines;

    public Location(String baseDir, String path, Integer startLine, Integer endLine) {
        String regex = ("^" + baseDir + "/").replace("//", "/");
        this.path = path.replaceFirst(regex, "");
        this.lines = new Lines(startLine, endLine);
    }

    public static Location from(Issue issue, String baseDir) {
        ClientInputFile inputFile = issue.getInputFile();

        if (inputFile == null || inputFile.getPath() == null ||
                issue.getStartLine() == null || issue.getEndLine() == null) {
            throw new IllegalArgumentException("Impossible to identify issue's location");
        }

        return new Location(baseDir, inputFile.getPath(), issue.getStartLine(), issue.getEndLine());
    }
}
