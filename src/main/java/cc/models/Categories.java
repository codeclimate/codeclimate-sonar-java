package cc.models;

import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;

import java.util.ArrayList;

class Categories extends ArrayList<String> {
    public Categories(String ruleType) {
        String category;
        switch (ruleType) {
            case "VULNERABILITY": {
                category = "Security";
                break;
            }
            default: {
                category = "Bug Risk";
                break;
            }
        }
        add(category);
    }

    public static Categories from(RuleDetails ruleDetails) {
        return new Categories(ruleDetails.getType());
    }
}
