package cc;

import cc.models.Severity;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigTest {

    @Test
    public void include_paths() throws Exception {
        Config config = Config.from("fixtures/multiple_paths/config.json");
        assertThat(config.getIncludePaths()).containsOnly("Main.java", "src/included/");
    }

    @Test
    public void default_config_path_include_base_dir() throws Exception {
        Config config = new Config();
        assertThat(config.getIncludePaths()).containsOnly("");
    }

    @Test
    public void fetch_charset() throws Exception {
        Config config = Config.gson().fromJson("{\"config\":{\"charset\":\"utf-16\"}}", Config.class);
        assertThat(config.getCharset()).isEqualTo(Charset.forName("UTF-16"));
    }

    @Test
    public void defaults_charset_to_utf8() throws Exception {
        Config config = Config.gson().fromJson("{\"config\":{}}", Config.class);
        assertThat(config.getCharset()).isEqualTo(Charset.forName("UTF-8"));
    }

    @Test
    public void fetch_minimum_severity() throws Exception {
        Config config = Config.gson().fromJson("{\"config\":{\"minimum_severity\":\"critical\"}}", Config.class);
        assertThat(config.getMinimumSeverity()).isEqualTo(Severity.CRITICAL);
    }

    @Test
    public void defaults_minimum_severity_to_major() throws Exception {
        Config config = Config.gson().fromJson("{}", Config.class);
        assertThat(config.getMinimumSeverity()).isEqualTo(Severity.MAJOR);
    }

    @Test
    public void fetch_tests_patterns() throws Exception {
        Config config = Config.gson().fromJson("{\"config\":{\"tests_patterns\":[\"src/test/**\",\"src/test2/**\"]}}", Config.class);
        assertThat(config.getTestsPatterns()).isEqualTo("{src/test/**,src/test2/**}");
    }

    @Test
    public void null_tests_patterns_does_not_cause_error() throws Exception {
        Config config = Config.gson().fromJson("{\"config\":{}}", Config.class);
        assertThat(config.getTestsPatterns()).isNull();
    }

    @Test
    public void has_default_work_dir() throws Exception {
        Config config = Config.gson().fromJson("{}", Config.class);
        assertThat(config.getSonarlintDir()).isEqualTo(Paths.get("/tmp/sonarlint"));
    }

    @Test
    public void include_generic_properties() throws Exception {
        Config config = Config.gson().fromJson("{\"config\":{\"key\":\"value\",\"charset\":\"utf-16\"}}", Config.class);
        assertThat(config.properties().getProperty("key")).isEqualTo("value");
        assertThat(config.getCharset()).isEqualTo(Charset.forName("UTF-16"));
    }
}