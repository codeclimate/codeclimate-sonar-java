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

    public static CodeClimateIssue from(Issue issue, RuleDetails ruleDetails, String baseDir) {
        String checkName = issue.getRuleKey();
        String description = issue.getMessage();
        String severity = Severity.from(ruleDetails);
        Content content = Content.from(ruleDetails);
        Location location = Location.from(baseDir, issue);
        Categories categories = Categories.from(ruleDetails);
        return new CodeClimateIssue(checkName, severity, description, content, location, categories);
    }

}
