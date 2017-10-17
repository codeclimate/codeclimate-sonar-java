package integration;

import org.junit.Test;
import support.Shell;

import static org.assertj.core.api.Assertions.assertThat;

public class MavenProjectTest {

    @Test
    public void successfully_analyze_project() throws Exception {
        Shell.Process process = Shell.execute("build/codeclimate-sonar fixtures/maven fixtures/maven/config.json");

        assertThat(process.exitCode).isEqualTo(0);
        assertThat(process.stderr).doesNotContain("class \"com.ctc.wstx.exc.WstxEOFException\"'s signer information does not match signer information of other classes in the same package");
    }
}
