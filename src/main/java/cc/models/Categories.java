package cc.models;

import com.google.gson.annotations.SerializedName;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cc.models.Categories.Category.*;

class Categories extends ArrayList<Categories.Category> {
    public enum Category {
        @SerializedName("Bug Risk")
        BUG_RISK,

        @SerializedName("Clarity")
        CLARITY,

        @SerializedName("Compatibility")
        COMPATIBILITY,

        @SerializedName("Complexity")
        COMPLEXITY,

        @SerializedName("Duplication")
        DUPLICATION,

        @SerializedName("Performance")
        PERFORMANCE,

        @SerializedName("Security")
        SECURITY,

        @SerializedName("Style")
        STYLE;
    }

    public Categories(RuleDetails rule) {
        switch (rule.getType()) {
            case "VULNERABILITY": {
                add(SECURITY);
                break;
            }
            case "BUG": {
                add(BUG_RISK);
                break;
            }
            case "CODE_SMELL":
            default: {
                add(fromTags(rule.getTags()));
                break;
            }
        }
    }

    private Category fromTags(String[] tags) {
        Category category = CLARITY;
        List<String> tagList = Arrays.asList(tags);
        if (tagList.contains("brain-overload")) {
            category = COMPLEXITY;
        }

        if (tagList.contains("duplicate")) {
            category = DUPLICATION;
        }

        if (tagList.contains("deadlock") || tagList.contains("unpredictable")
                || tagList.contains("bad-practice") || tagList.contains("suspicious")) {
            category = BUG_RISK;
        }

        if (tagList.contains("maven")) {
            category = COMPATIBILITY;
        }

        if (tagList.contains("performance")) {
            category = PERFORMANCE;
        }

        if (tagList.contains("convention") || tagList.contains("style")) {
            category = STYLE;
        }

        if (tagList.contains("confusing")) {
            category = CLARITY;
        }

        return category;
    }

    public static Categories from(RuleDetails ruleDetails) {
        return new Categories(ruleDetails);
    }
}
