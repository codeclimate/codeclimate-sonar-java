package cc;

import org.junit.Before;
import org.junit.Test;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;
import org.sonarsource.sonarlint.core.tracking.Trackable;
import support.fakes.FakeRuleDetails;
import support.OutputHelper;
import support.fakes.FakeIssue;
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
    public void does_not_include_unknown_severity() throws Exception {
        executeReport(null, new FakeIssue("file.java", 0, 1));
        assertThat(output.stdout.toString()).doesNotContain("severity");
    }

    @Test
    public void does_not_create_issue_for_unknown_path() throws Exception {
        executeReport("major", new FakeIssue(null, 1, 0));
        assertThat(output.stderr.toString()).contains("Impossible to identify issue's location");
    }

    @Test
    public void does_not_create_issue_for_unknown_location() throws Exception {
        executeReport("major", new FakeIssue("file.java", null, null));
        assertThat(output.stderr.toString()).contains("Impossible to identify issue's location");
    }

    void executeReport(String severity, FakeIssue issue) {
        RuleDetails ruleDetails = new FakeRuleDetails(severity);
        List<Trackable> trackables = asList(new FakeTrackable(issue));
        new JsonReport("/tmp").execute("prj", new Date(0), trackables, null, _ruleKey -> ruleDetails);
    }
}