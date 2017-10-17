package integration;

import cc.App;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sonarlint.cli.SonarProperties;
import support.SystemHelper;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationOptionsTest {
    ByteArrayOutputStream stdout;
    ByteArrayOutputStream stderr;

    SystemHelper system;

    @BeforeClass
    public static void beforeAll() {
        System.setProperty(SonarProperties.SONARLINT_HOME, "build");
    }

    @Before
    public void setUp() throws Exception {
        system = new SystemHelper();
        stdout = new ByteArrayOutputStream();
        stderr = new ByteArrayOutputStream();

        System.setOut(new PrintStream(stdout));
        System.setErr(new PrintStream(stderr));

        system.setProperty(SonarProperties.PROJECT_HOME, "fixtures/multiple_paths");
    }

    @Test
    public void limit_path_included_within_analysis() throws Exception {
        system.setProperty("config", "fixtures/multiple_paths/config.json");

        App.execute(system);

        String output = stdout.toString();
        assertThat(output).contains("\"type\":\"issue\"", "src/included/java/pkg1/HasIssue.java");
        assertThat(output).doesNotContain("src/excluded/java/pkg1/HasIssue.java");
    }

    @Test
    public void include_all_files_by_default() throws Exception {
        App.execute(system);

        assertThat(stdout.toString()).contains(
                "\"type\":\"issue\"",
                "src/included/java/pkg1/HasIssue.java",
                "src/excluded/java/pkg1/HasIssue.java"
        );
    }

    @Test
    public void capture_exceptions_and_log_to_stderr() throws Exception {
        system.removeProperty(SonarProperties.PROJECT_HOME);

        App.execute(system);

        assertThat(system.exitCode).isEqualTo(1);
        assertThat(stderr.toString()).contains("Can't find project home. System property not set: project.home");
    }
}