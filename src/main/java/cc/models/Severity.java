package cc.models;

import com.google.gson.annotations.SerializedName;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;

public class Severity {

    public static String from(RuleDetails ruleDetails) {
        String severity = ruleDetails.getSeverity();
        if(severity == null) {
            return null;
        }
        return severity.toLowerCase();
    }
}
