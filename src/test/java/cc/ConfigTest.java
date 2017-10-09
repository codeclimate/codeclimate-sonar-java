package cc;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigTest {

    @Test
    public void include_paths() throws Exception {
        Config config = Config.from("fixtures/multiple_paths/config.json");
        assertThat(config.includePaths).containsOnly("Main.java", "src/included/");
    }

    @Test
    public void default_config_path_include_base_dir() throws Exception {
        Config config = new Config();
        assertThat(config.includePaths).containsOnly("");
    }
}