package support;

import cc.analysis.SonarLintFactory;
import org.sonarlint.cli.analysis.LogOutputWrapper;
import org.sonarsource.sonarlint.core.StandaloneSonarLintEngineImpl;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;

import java.nio.file.Paths;

public class Factory {
    public static StandaloneSonarLintEngineImpl sonarlint() {
        SonarLintFactory factory = new SonarLintFactory(null, Paths.get("/tmp/sonarlint"));
        return factory.createEngine(new LogOutputWrapper(false));
    }

    public static RuleDetails createRule(String key) {
        return sonarlint().getRuleDetails("squid:" + key);
    }
}