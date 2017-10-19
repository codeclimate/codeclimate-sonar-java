package cc.models;

import cc.serialization.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;
import support.fakes.FakeRuleDetails;

import static org.assertj.core.api.Assertions.assertThat;

public class CodeClimateIssueTest {

    private Gson gson = new GsonFactory().create();

    @Test
    public void down_case_severities_uppon_serialization() throws Exception {
        assertThat(gson.toJson(createIssueForSeverity("CRITICAL"))).contains("critical");
    }

    @Test
    public void properly_serialize_severity() throws Exception {
        assertThat(gson.toJson(createIssueForSeverity("INFO"))).contains("\"severity\":\"info\"");
    }

    private CodeClimateIssue createIssueForSeverity(String severity) {
        return new CodeClimateIssue(
                "check",
                Severity.from(new FakeRuleDetails(severity)),
                "desc",
                new Content(""),
                new Location("/tmp", "path", new Lines(0, 1)),
                new Categories("VULNERABILITY")
        );
    }
}