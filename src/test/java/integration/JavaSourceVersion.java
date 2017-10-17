package integration;

import org.junit.Test;
import support.Shell;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaSourceVersion {
    @Test
    public void specify_java_source_version_through_config() throws Exception {
        Shell.Process process = Shell.execute("build/codeclimate-sonar fixtures/java_source_version fixtures/java_source_version/config.json");
        assertThat(process.stderr).contains("Configured Java source version (sonar.java.source): 6");
    }
}
