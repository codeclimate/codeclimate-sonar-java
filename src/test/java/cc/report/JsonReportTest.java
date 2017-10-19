package cc.report;

import cc.models.Severity;
import org.junit.Before;
import org.junit.Test;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;
import org.sonarsource.sonarlint.core.tracking.Trackable;
import support.OutputHelper;
import support.fakes.FakeIssue;
import support.fakes.FakeRuleDetails;
import support.fakes.FakeTrackable;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonReportTest {

    OutputHelper output;

    @Before
    public void setUp() throws Exception {
        output = OutputHelper.setup();
    }

    @Test
    public void serialize_issue() throws Exception {
        executeReport("major", new FakeIssue("file.java", 0, 1));
        assertThat(output.stdout.toString()).contains("\"type\":\"issue\"");
    }

    @Test
    public void serialize_issue_relative_path() throws Exception {
        executeReport("major", new FakeIssue("/tmp/dir/file.java", 0, 1));
        assertThat(output.stdout.toString()).contains("\"path\":\"dir/file.java\"");
    }

    @Test
    public void does_not_include_unknown_path() throws Exception {
        executeReport("major", new FakeIssue(null, 1, 0));
        assertThat(output.stdout.toString()).doesNotContain("path");
    }

    @Test
    public void does_not_report_issue_below_minimum_severity() throws Exception {
        executeReport(Severity.INFO,null, new FakeIssue(null, 1, 0));
        assertThat(output.stdout.toString()).isEmpty();

        executeReport(Severity.CRITICAL,"major", new FakeIssue(null, 1, 0));
        assertThat(output.stdout.toString()).isEmpty();

        executeReport(Severity.CRITICAL,"critical", new FakeIssue(null, 1, 0));
        assertThat(output.stdout.toString()).contains("issue");
    }

    @Test
    public void unknown_location_defaults_to_first_line() throws Exception {
        executeReport("major", new FakeIssue("file.java", null, null));
        assertThat(output.stdout.toString()).contains("\"lines\":{\"begin\":1,\"end\":1}");
        assertThat(output.stderr.toString()).contains("File location was not provided, defaulting to line 1");
    }

    void executeReport(String ruleSeverity, FakeIssue issue) {
        executeReport(Severity.MAJOR, ruleSeverity, issue);
    }

    void executeReport(Severity minimumSeverity, String ruleSeverity, FakeIssue issue) {
        RuleDetails ruleDetails = new FakeRuleDetails(ruleSeverity);
        List<Trackable> trackables = asList(new FakeTrackable(issue));
        new JsonReport(minimumSeverity, "/tmp").execute("prj", new Date(0), trackables, null, _ruleKey -> ruleDetails);
    }
}