package cc.models;

import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;

import java.util.ArrayList;

public class CodeClimateIssue {
    final String type;
    final String checkName;
    final String severity;
    final String description;
    final Content content;
    final Location location;
    final Categories categories;

    public CodeClimateIssue(Issue issue, RuleDetails ruleDetails) {
        this.type = "issue";
        this.checkName = issue.getRuleKey();
        this.description = issue.getMessage();
        this.severity = ruleDetails.getSeverity().toLowerCase();
        this.content = new Content(ruleDetails.getHtmlDescription());
        this.location = new Location(issue);
        this.categories = new Categories(ruleDetails);
    }

    class Content {
        String body;

        public Content(String body) {
            this.body = body;
        }
    }

    class Location {
        final String path;
        final Lines lines;

        public Location(Issue issue) {
            this.path = issue.getInputFile().getPath().replaceFirst("^/tmp/code/", "");
            this.lines = new Lines(issue);
        }
    }

    class Lines {
        final Integer begin;
        final Integer end;

        public Lines(Issue issue) {
            if (issue.getStartLine() != null) {
                this.begin = issue.getStartLine();

                if (issue.getEndLine() != null) {
                    this.end = issue.getEndLine();
                } else {
                    this.end = 1;
                }
            } else {
                this.begin = 1;
                this.end = 1;
            }

        }
    }

    class Categories extends ArrayList<String> {
        public Categories(RuleDetails ruleDetails) {
            String category;
            switch (ruleDetails.getType()) {
                case "VULNERABILITY": {
                    category = "Security";
                    break;
                }
                default: {
                    category = "Bug Risk";
                    break;
                }
            }
            add(category);
        }
    }
}
