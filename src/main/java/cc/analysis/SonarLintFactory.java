package cc.analysis;

import org.sonarlint.cli.analysis.LogOutputWrapper;
import org.sonarlint.cli.analysis.SonarLint;
import org.sonarlint.cli.config.ConfigurationReader;
import org.sonarlint.cli.util.Logger;
import org.sonarsource.sonarlint.core.StandaloneSonarLintEngineImpl;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneGlobalConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneSonarLintEngine;

import java.nio.file.Path;

public class SonarLintFactory extends org.sonarlint.cli.analysis.SonarLintFactoryWrapper {
    private Path workDir;

    public SonarLintFactory(ConfigurationReader reader, Path workDir) {
        super(reader);
        this.workDir = workDir;
    }

    @Override
    public SonarLint createSonarLint(Path projectHome, boolean mustBeConnected, boolean verbose) {
        LogOutputWrapper logWrapper = new LogOutputWrapper(verbose);
        return new StandaloneSonarLint(createEngine(logWrapper), workDir, logWrapper);
    }

    public StandaloneSonarLintEngineImpl createEngine(LogOutputWrapper logWrapper) {
        return new StandaloneSonarLintEngineImpl(config(logWrapper));
    }

    public StandaloneGlobalConfiguration config(LogOutputWrapper logWrapper) {
        return StandaloneGlobalConfiguration.builder()
                    .addPlugins(plugins())
                    .setLogOutput(logWrapper)
                    .build();
    }
}
