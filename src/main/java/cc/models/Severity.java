package cc.models;

import com.google.gson.annotations.SerializedName;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;

import static java.util.Optional.ofNullable;

public enum Severity {
    @SerializedName("info")
    INFO,
    @SerializedName("minor")
    MINOR,
    @SerializedName("major")
    MAJOR,
    @SerializedName("critical")
    CRITICAL,
    @SerializedName("blocker")
    BLOCKER;

    public static Severity from(RuleDetails ruleDetails) {
        String severity = ruleDetails.getSeverity();
        return from(severity);
    }

    public static Severity from(String severity) {
        if (severity == null) {
            return null;
        }
        return valueOf(severity.toUpperCase());
    }

    public static Severity from(String severity, Severity defaultValue) {
        return ofNullable(from(severity)).orElse(defaultValue);
    }
}
