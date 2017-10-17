package cc.models;

import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;

class Lines {
    final Integer begin;
    final Integer end;

    public Lines(Integer begin, Integer end) {
        this.begin = begin;
        this.end = end;
    }

    public static Lines from(Issue issue) {
        if (issue.getStartLine() == null || issue.getEndLine() == null) {
            System.err.println("File location was not provided, defaulting to line 1.");
            System.err.println(issue);
            return new Lines(1, 1);
        }
        return new Lines(issue.getStartLine(), issue.getEndLine());
    }
}
