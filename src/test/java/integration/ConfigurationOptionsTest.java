package integration;

import cc.App;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sonarlint.cli.SonarProperties;
import org.sonarlint.cli.util.System2;
import support.TestSystem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationOptionsTest {
    ByteArrayOutputStream stdout;
    ByteArrayOutputStream stderr;

    System2 system;

    @BeforeClass
    public static void beforeAll() {
        System.setProperty(SonarProperties.PROJECT_HOME, "fixtures/multiple_paths");
        System.setProperty(SonarProperties.SONARLINT_HOME, "build");
    }

    @Before
    public void setUp() throws Exception {
        system = new TestSystem();
        stdout = new ByteArrayOutputStream();
        stderr = new ByteArrayOutputStream();

        System.setOut(new PrintStream(stdout));
        System.setErr(new PrintStream(stderr));
    }

    @Test
    public void limit_path_included_within_analysis() throws Exception {
        App.execute(new String[]{"-Dconfig=fixtures/multiple_paths/config.json"}, system);

        String output = stdout.toString();
        assertThat(output).contains("issue", "fixtures/multiple_paths/src/included/java/pkg1/HasIssue.java");
        assertThat(output).doesNotContain("fixtures/multiple_paths/src/excluded/java/pkg1/HasIssue.java");
    }

    @Test
    public void include_all_files_by_default() throws Exception {
        App.execute(new String[]{}, system);

        String output = stdout.toString();
        assertThat(output).contains(
                "issue",
                "fixtures/multiple_paths/src/included/java/pkg1/HasIssue.java",
                "fixtures/multiple_paths/src/excluded/java/pkg1/HasIssue.java"
        );
    }

}
