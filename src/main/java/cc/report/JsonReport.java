package cc.report;

import cc.models.CodeClimateIssue;
import cc.serialization.GsonFactory;
import com.google.gson.Gson;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;
import org.sonarsource.sonarlint.core.client.api.common.analysis.AnalysisResults;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;
import org.sonarsource.sonarlint.core.tracking.Trackable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class JsonReport implements org.sonarlint.cli.report.Reporter {

    private static final List<String> VALID_RULE_DETAIL_TYPES = Arrays.asList(
            "BUG",
            "VULNERABILITY"
    );

    final Gson gson;
    final String baseDir;

    public JsonReport(String baseDir) {
        this.baseDir = baseDir;
        this.gson = new GsonFactory().create();
    }


    @Override
    public void execute(String projectName, Date date, Collection<Trackable> trackables, AnalysisResults result, Function<String, RuleDetails> ruleDescriptionProducer) {
        for (Trackable trackable : trackables) {
            Issue issue = trackable.getIssue();
            RuleDetails ruleDetails = ruleDescriptionProducer.apply(issue.getRuleKey());

            String type = ruleDetails.getType();
            if (VALID_RULE_DETAIL_TYPES.contains(type)) {
                CodeClimateIssue codeClimateIssue = CodeClimateIssue.from(issue, ruleDetails, baseDir);
                System.out.println(gson.toJson(codeClimateIssue) + "\0");
            }
        }
    }
}
