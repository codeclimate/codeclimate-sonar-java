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
    private static final Logger LOGGER = Logger.get();
    private Path workDir;

    public SonarLintFactory(ConfigurationReader reader, Path workDir) {
        super(reader);
        this.workDir = workDir;
    }

    @Override
    public SonarLint createSonarLint(Path projectHome, boolean mustBeConnected, boolean verbose) {
        LOGGER.info("Standalone mode");

        StandaloneGlobalConfiguration config = StandaloneGlobalConfiguration.builder()
                .addPlugins(plugins())
                .setLogOutput(new LogOutputWrapper(LOGGER, verbose))
                .build();

        StandaloneSonarLintEngine engine = new StandaloneSonarLintEngineImpl(config);
        return new StandaloneSonarLint(engine, workDir);
    }
}
