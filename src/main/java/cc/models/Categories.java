package cc.models;

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
}
