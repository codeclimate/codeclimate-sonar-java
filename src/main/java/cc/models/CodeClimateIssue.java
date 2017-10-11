package cc.models;

import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;

public class CodeClimateIssue {
    public final String type = "issue";
    public final String checkName;
    public final Severity severity;
    public final String description;
    public final Content content;
    public final Location location;
    public final Categories categories;

    public CodeClimateIssue(String checkName, Severity severity, String description, Content content, Location location, Categories categories) {
        this.checkName = checkName;
        this.severity = severity;
        this.description = description;
        this.content = content;
        this.location = location;
        this.categories = categories;
    }

    public static CodeClimateIssue from(Issue issue, RuleDetails ruleDetails) {
        String checkName = issue.getRuleKey();
        String description = issue.getMessage();
        Severity severity = Severity.from(ruleDetails.getSeverity());
        Content content = new Content(ruleDetails.getHtmlDescription());
        Location location = new Location(issue.getInputFile().getPath(), issue.getStartLine(), issue.getEndLine());
        Categories categories = new Categories(ruleDetails.getType());
        return new CodeClimateIssue(checkName, severity, description, content, location, categories);
    }
}
