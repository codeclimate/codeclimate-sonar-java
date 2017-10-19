package cc;

import cc.analysis.SonarLintFactory;
import cc.files.Finder;
import org.sonarlint.cli.EngineWrapper;
import org.sonarlint.cli.InputFileFinder;
import org.sonarlint.cli.config.ConfigurationReader;
import org.sonarlint.cli.report.ReportFactory;
import org.sonarlint.cli.util.System2;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.sonarlint.cli.SonarProperties.PROJECT_HOME;

public class App {
    static final int ERROR = 1;

    public static void main(String[] args) {
        execute(System2.INSTANCE);
    }

    public static void execute(System2 system) {
        try {
            Config config = Config.from(system.getProperty("config"));
            Charset charset = config.getCharset();

            InputFileFinder fileFinder = new Finder(config.getIncludePaths(), config.getTestsPatterns(), charset);
            ReportFactory reportFactory = new cc.report.ReportFactory(charset, config.getMinimumSeverity());
            ConfigurationReader reader = new ConfigurationReader();
            SonarLintFactory sonarLintFactory = new SonarLintFactory(reader, config.getSonarlintDir());
            Path projectHome = getProjectHome(system);

            int exitCode = new EngineWrapper(config, sonarLintFactory, reportFactory, fileFinder, projectHome).run();
            system.exit(exitCode);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            system.exit(ERROR);
        }
    }

    private static Path getProjectHome(System2 system) {
        String projectHome = system.getProperty(PROJECT_HOME);
        if (projectHome == null) {
            throw new IllegalStateException("Can't find project home. System property not set: " + PROJECT_HOME);
        }
        return Paths.get(projectHome);
    }
}
