package cc.models;

import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;

class Location {
    final String path;
    final Lines lines;

    public Location(String baseDir, String path, Lines lines) {
        String regex = ("^" + baseDir + "/").replace("//", "/");
        this.path = path.replaceFirst(regex, "");
        this.lines = lines;
    }

    public static Location from(String baseDir, Issue issue) {
        ClientInputFile inputFile = issue.getInputFile();

        if (inputFile == null || inputFile.getPath() == null) {
            return null;
        }

        return new Location(baseDir, inputFile.getPath(), Lines.from(issue));
    }
}
