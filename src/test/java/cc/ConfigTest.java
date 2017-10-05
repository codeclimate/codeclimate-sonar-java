package cc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigTest {

    @Test
    public void include_paths() throws Exception {
        Config config = Config.from("fixtures/multiple_paths/config.json");
        assertThat(config.includePaths).containsOnly("Main.java", "src/included/");
    }
}