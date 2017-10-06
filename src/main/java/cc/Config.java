package cc;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class Config {
    private List<String> includePaths = Arrays.asList("");
    private EngineConfig config = new EngineConfig();

    private class EngineConfig {
        public String charset;
        public List<String> testsPatterns;
        public List<String> exclusionPatterns;
    }

    public List<String> getIncludePaths() {
        return includePaths;
    }

    public String getCharset() {
        return config.charset;
    }

    public String getTestsPatterns() {
        return joinPatterns(config.testsPatterns);
    }

    public String getExclusionPatterns() {
        return joinPatterns(config.exclusionPatterns);
    }

    private String joinPatterns(List<String> patterns) {
        if (patterns == null) {
            return null;
        }
        return "{" + String.join(",", patterns) + "}";
    }

    public static Config from(String file) {
        try {
            return gson().fromJson(new JsonReader(new FileReader(file)), Config.class);
        } catch (Exception e) {
            return new Config();
        }
    }

    public static Gson gson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
