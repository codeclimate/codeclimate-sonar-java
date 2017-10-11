package cc.models;

import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;

public class CodeClimateIssue {
    public final String type = "issue";
    public final String checkName;
    public final String severity;
    public final String description;
    public final Content content;
    public final Location location;
    public final Categories categories;

    public CodeClimateIssue(String checkName, String severity, String description, Content content, Location location, Categories categories) {
        this.checkName = checkName;
        this.severity = severity;
        this.description = description;
        this.content = content;
        this.location = location;
        this.categories = categories;
    }

    public CodeClimateIssue(Issue issue, RuleDetails ruleDetails) {
        this.checkName = issue.getRuleKey();
        this.description = issue.getMessage();
        this.severity = ruleDetails.getSeverity().toLowerCase();
        this.content = new Content(ruleDetails.getHtmlDescription());
        this.location = new Location(issue.getInputFile().getPath(), issue.getStartLine(), issue.getEndLine());
        this.categories = new Categories(ruleDetails.getType());
    }
}
