package integration;

import org.assertj.core.util.Strings;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import support.File;
import support.Shell;

import static org.assertj.core.api.Assertions.assertThat;


public class SanityCheckTest {
    @Test
    public void executeJavaLibFixture() throws Exception {
        String expectedOutput = File.read("src/test/resources/sanity_check_expected_issues.json");

        Shell.Process process = Shell.execute("build/codeclimate-sonar fixtures/java_lib");

        assertThat(process.exitCode).isEqualTo(0);
        assertThat(process.stdout)
                .withFailMessage("Issues must be split by a NULL (\\0) character")
                .contains("\0");

        String stdoutAsJson = "[" + Strings.join(process.stdout.split("\0")).with(",") + "]";
        JSONAssert.assertEquals(expectedOutput, stdoutAsJson, false);
    }
}
