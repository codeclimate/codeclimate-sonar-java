package cc.models;

import org.junit.Test;
import support.fakes.FakeIssue;

import static org.assertj.core.api.Assertions.assertThat;

public class LinesTest {

    @Test
    public void default_to_line_1() throws Exception {
        FakeIssue issue = new FakeIssue("path", null, null);
        Lines lines = Lines.from(issue);
        assertThat(lines.begin).isEqualTo(1);
        assertThat(lines.end).isEqualTo(1);
    }
}