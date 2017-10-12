package cc.models;

import cc.serialization.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;
import support.fakes.FakeRuleDetails;

import static org.assertj.core.api.Assertions.assertThat;

public class CodeClimateIssueTest {

    @Test
    public void down_case_severities() throws Exception {
        assertThat(createIssueForSeverity("MAJOR").severity).isEqualTo("major");
        assertThat(createIssueForSeverity("MINOR").severity).isEqualTo("minor");
        assertThat(createIssueForSeverity("CRITICAL").severity).isEqualTo("critical");
        assertThat(createIssueForSeverity(null).severity).isNull();
    }

    @Test
    public void properly_serialize_severity() throws Exception {
        Gson gson = new GsonFactory().create();
        assertThat(gson.toJson(createIssueForSeverity("INFO"))).contains("\"severity\":\"info\"");
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