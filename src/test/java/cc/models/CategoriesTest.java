package cc.models;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sonar.api.batch.rule.Rule;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;
import support.Factory;

import java.util.Collection;

import static cc.models.Categories.Category.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesTest {

    @BeforeClass
    public static void beforeAll() throws Exception {
        System.setProperty("sonarlint.home", "build");
    }

    @Test
    public void set_clarity_for_generic_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S1602")).contains(CLARITY);
    }

    @Test
    public void set_complexity_for_brain_overload_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S1067")).contains(COMPLEXITY);
    }

    @Test
    public void set_duplication_for_duplicate_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S4144")).contains(DUPLICATION);
    }

    @Test
    public void set_clarity_for_confusing_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S1141")).contains(CLARITY);
    }

    @Test
    public void set_bug_risk_for_deadlock_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S3046")).contains(BUG_RISK);
    }

    @Test
    public void set_compatibility_for_maven_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S3423")).contains(COMPATIBILITY);
    }

    @Test
    public void set_performance_for_performance_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S1149")).contains(PERFORMANCE);
    }

    @Test
    public void set_style_for_convention_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S00115")).contains(Categories.Category.STYLE);
    }

    @Test
    public void set_style_for_style_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S00122")).contains(Categories.Category.STYLE);
    }

    @Test
    public void set_bug_risk_for_unpredictable_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S1215")).contains(Categories.Category.BUG_RISK);
    }

    @Test
    public void set_bug_risk_for_bad_practice_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S106")).contains(Categories.Category.BUG_RISK);
    }

    @Test
    public void set_bug_risk_for_suspicious_code_smells() throws Exception {
        assertThat(getCategoriesForRule("S1186")).contains(Categories.Category.BUG_RISK);
    }

    private Categories getCategoriesForRule(String key) {
        RuleDetails rule = Factory.createRule(key);
        return Categories.from(rule);
    }
}