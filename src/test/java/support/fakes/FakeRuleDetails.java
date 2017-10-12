package support.fakes;

import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;

import javax.annotation.CheckForNull;

public class FakeRuleDetails implements RuleDetails {
    private String severity;

    public FakeRuleDetails(String severity) {
        this.severity = severity;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getHtmlDescription() {
        return null;
    }

    @Override
    public String getLanguage() {
        return null;
    }

    @Override
    public String getSeverity() {
        return severity;
    }

    @CheckForNull
    @Override
    public String getType() {
        return "BUG";
    }

    @Override
    public String[] getTags() {
        return new String[0];
    }

    @Override
    public String getExtendedDescription() {
        return null;
    }
}
