package cc.models;

import com.google.gson.annotations.SerializedName;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;

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
        if (severity == null) {
            return null;
        }
        return valueOf(severity.toUpperCase());
    }
}
