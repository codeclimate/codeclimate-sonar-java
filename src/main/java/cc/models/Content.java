package cc.models;

import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;

class Content {
    String body;

    public Content(String body) {
        this.body = body;
    }

    public static Content from(RuleDetails ruleDetails) {
        return new Content(ruleDetails.getHtmlDescription());
    }
}
