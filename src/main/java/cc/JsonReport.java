package cc;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.function.Function;
import org.sonarlint.cli.util.Logger;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;
import org.sonarsource.sonarlint.core.client.api.common.analysis.AnalysisResults;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;
import org.sonarsource.sonarlint.core.tracking.Trackable;

public class JsonReport implements org.sonarlint.cli.report.Reporter {

    private static final List<String> VALID_RULE_DETAIL_TYPES = Arrays.asList(
            "BUG",
            "VULNERABILITY"
    );


    @Override
    public void execute(String projectName, Date date, Collection<Trackable> trackables, AnalysisResults result, Function<String, RuleDetails> ruleDescriptionProducer) {
        for (Trackable trackable : trackables) {
            Issue issue = trackable.getIssue();
            RuleDetails ruleDetails = ruleDescriptionProducer.apply(issue.getRuleKey());

            if (VALID_RULE_DETAIL_TYPES.contains(ruleDetails.getType())) {
                JsonObject json = new JsonObject();
                json.addProperty("type", "issue");
                json.addProperty("check_name", issue.getRuleKey());
                json.addProperty("severity", ruleDetails.getSeverity().toLowerCase());
                json.addProperty("description", issue.getMessage());

                JsonObject content = new JsonObject();
                json.add("content", content);
                content.addProperty("body", ruleDetails.getHtmlDescription());
                // // ruleDetails.getExtendedDescription();

                JsonObject location = new JsonObject();
                json.add("location", location);

                // Code Climate CLI expects relative path to file
                location.addProperty("path", issue.getInputFile().getPath().replaceFirst("^/tmp/code/", ""));

                JsonObject lines = new JsonObject();
                location.add("lines", lines);

                if (issue.getStartLine() != null) {
                    lines.addProperty("begin", issue.getStartLine());

                    if (issue.getEndLine() != null) {
                        lines.addProperty("end", issue.getEndLine());
                    } else {
                        lines.addProperty("end", 1);
                    }
                } else {
                    lines.addProperty("begin", 1);
                    lines.addProperty("end", 1);
                }

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
                JsonArray categories = new JsonArray();
                categories.add(category);
                json.add("categories", categories);

                System.out.println(json.toString() + "\0");
            }
        }
    }
}
