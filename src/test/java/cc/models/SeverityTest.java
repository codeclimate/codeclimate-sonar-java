package cc.models;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SeverityTest {

    @Test
    public void has_order_of_importance() throws Exception {
        assertThat(Severity.BLOCKER).isGreaterThan(Severity.CRITICAL);
        assertThat(Severity.CRITICAL).isGreaterThan(Severity.MAJOR);
        assertThat(Severity.MAJOR).isGreaterThan(Severity.MINOR);
        assertThat(Severity.MINOR).isGreaterThan(Severity.INFO);
    }
}