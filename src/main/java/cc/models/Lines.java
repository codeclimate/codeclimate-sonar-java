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
        if(issue.getStartLine() == null || issue.getEndLine() == null) {
            return null;
        }
        return new Lines(issue.getStartLine(), issue.getEndLine());
    }
}
