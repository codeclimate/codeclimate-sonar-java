package cc.models;

import com.google.gson.annotations.SerializedName;

public enum Severity {
    @SerializedName("major")
    MAJOR,
    @SerializedName("minor")
    MINOR,
    @SerializedName("info")
    INFO,
    @SerializedName("critical")
    CRITICAL,
    @SerializedName("blocker")
    BLOCKER;

    public static Severity from(String value) {
        return valueOf(value.toUpperCase());
    }
}
