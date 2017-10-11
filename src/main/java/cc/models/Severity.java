package cc.models;

import com.google.gson.annotations.SerializedName;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;

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

    public static Severity from(RuleDetails ruleDetails) {
        try {
            return valueOf(ruleDetails.getSeverity().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown severity", e);
        }
    }
}
