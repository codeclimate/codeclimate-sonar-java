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
        String charset;
        List<String> testsPatterns;
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

    String joinPatterns(List<String> patterns) {
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

    static Gson gson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
