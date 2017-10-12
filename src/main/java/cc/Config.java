package cc;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {
    public List<String> includePaths = Arrays.asList("");

    public static Config from(String file) {
        try {
            return gson().fromJson(new JsonReader(new FileReader(file)), Config.class);
        } catch (Exception e) {
            return new Config();
        }
    }

    private static Gson gson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
