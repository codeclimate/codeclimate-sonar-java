package cc;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Config {
    private List<String> includePaths = Arrays.asList("");
    private EngineConfig config = new EngineConfig();

    private class EngineConfig {
        String charset;
        List<String> testsPatterns;
        String workDir;
    }

    public List<String> getIncludePaths() {
        return includePaths;
    }

    public Charset getCharset() {
        return createCharset(config.charset);
    }

    public String getTestsPatterns() {
        return joinPatterns(config.testsPatterns);
    }

    public Path getWorkdir() {
        if (config.workDir == null) {
            return Paths.get("/tmp/sonarlint");
        }
        return Paths.get(config.workDir);
    }

    String joinPatterns(List<String> patterns) {
        if (patterns == null) {
            return null;
        }
        return "{" + String.join(",", patterns) + "}";
    }


    Charset createCharset(String charset) {
        if (charset != null) {
            return Charset.forName(charset);
        } else {
            return Charset.defaultCharset();
        }
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
