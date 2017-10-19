package cc;

import cc.models.Severity;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.sonarlint.cli.Options;

import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Config extends Options {
    private List<String> includePaths = Arrays.asList("");
    private Map<String, Object> config = new LinkedHashMap<>();

    public List<String> getIncludePaths() {
        return includePaths;
    }

    public Charset getCharset() {
        return createCharset(charset());
    }

    @Override
    public String charset() {
        return (String) config.get("charset");
    }

    public String getTestsPatterns() {
        List<String> testsPatterns = (List<String>) config.get("tests_patterns");
        return joinPatterns(testsPatterns);
    }

    public Path getSonarlintDir() {
        return Paths.get("/tmp/sonarlint");
    }

    public Severity getMinimumSeverity() {
        String severity = (String) config.get("minimum_severity");
        return Severity.from(severity, Severity.MAJOR);
    }

    @Override
    public Properties properties() {
        Properties properties = super.properties();
        for (Map.Entry entry : config.entrySet()) {
            properties.put(entry.getKey(), entry.getValue().toString());
        }
        return properties;
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
