package cc.models;

import cc.serialization.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;
import support.FakeRuleDetails;

import static org.assertj.core.api.Assertions.assertThat;

public class CodeClimateIssueTest {

    @Test(expected = IllegalArgumentException.class)
    public void raise_error_on_invalid_severity() throws Exception {
        createIssueForSeverity("unknown");
    }

    @Test
    public void accepts_only_valid_severities() throws Exception {
        assertThat(createIssueForSeverity("major").severity).isEqualTo(Severity.MAJOR);
        assertThat(createIssueForSeverity("minor").severity).isEqualTo(Severity.MINOR);
        assertThat(createIssueForSeverity("critical").severity).isEqualTo(Severity.CRITICAL);
        assertThat(createIssueForSeverity("blocker").severity).isEqualTo(Severity.BLOCKER);
        assertThat(createIssueForSeverity("info").severity).isEqualTo(Severity.INFO);
    }

    @Test
    public void properly_serialize_severity() throws Exception {
        Gson gson = new GsonFactory().create();
        assertThat(gson.toJson(createIssueForSeverity("info"))).contains("\"severity\":\"info\"");
    }

    private CodeClimateIssue createIssueForSeverity(String severity) {
        return new CodeClimateIssue(
                "check",
                Severity.from(new FakeRuleDetails(severity)),
                "desc",
                new Content(""),
                new Location("/tmp", "path", 0, 1),
                new Categories("VULNERABILITY")
        );
    }
}