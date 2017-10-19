package cc.report;

import cc.models.CodeClimateIssue;
import cc.models.Severity;
import cc.serialization.GsonFactory;
import com.google.gson.Gson;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;
import org.sonarsource.sonarlint.core.client.api.common.analysis.AnalysisResults;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;
import org.sonarsource.sonarlint.core.tracking.Trackable;

import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

public class JsonReport implements org.sonarlint.cli.report.Reporter {

    final Gson gson;
    final Severity minimumSeverity;
    final String baseDir;

    public JsonReport(Severity minimumSeverity, String baseDir) {
        this.minimumSeverity = minimumSeverity;
        this.baseDir = baseDir;
        this.gson = new GsonFactory().create();
    }

    @Override
    public void execute(String projectName, Date date, Collection<Trackable> trackables, AnalysisResults result, Function<String, RuleDetails> ruleDescriptionProducer) {
        for (Trackable trackable : trackables) {
            Issue issue = trackable.getIssue();
            RuleDetails ruleDetails = ruleDescriptionProducer.apply(issue.getRuleKey());

            CodeClimateIssue codeClimateIssue = CodeClimateIssue.from(issue, ruleDetails, baseDir);
            Severity severity = codeClimateIssue.severity;
            if (severity!= null && severity.compareTo(minimumSeverity) >= 0) {
                System.out.println(gson.toJson(codeClimateIssue) + "\0");
            }
        }
    }
}