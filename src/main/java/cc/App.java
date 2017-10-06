package cc;

import cc.files.Finder;
import org.sonarlint.cli.CustomMain;
import org.sonarlint.cli.InputFileFinder;
import org.sonarlint.cli.Options;
import org.sonarlint.cli.analysis.SonarLintFactory;
import org.sonarlint.cli.config.ConfigurationReader;
import org.sonarlint.cli.report.ReportFactory;
import org.sonarlint.cli.util.Logger;
import org.sonarlint.cli.util.System2;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.sonarlint.cli.SonarProperties.PROJECT_HOME;

public class App {
    public static Logger LOGGER = Logger.get();
    static final int ERROR = 1;

    public static void main(String[] args) {
        execute(args, System2.INSTANCE);
    }

    public static void execute(String[] args, System2 system) {
        try {
            Options parsedOpts = Options.parse(args);
            Charset charset = createCharset(parsedOpts);

            Config config = Config.from(system.getProperty("config"));
            InputFileFinder fileFinder = new Finder(config.includePaths, parsedOpts.tests(), charset);
            ReportFactory reportFactory = new cc.report.ReportFactory(charset);
            ConfigurationReader reader = new ConfigurationReader();
            SonarLintFactory sonarLintFactory = new SonarLintFactory(reader);
            Path projectHome = getProjectHome(system);

            int exitCode = new CustomMain(parsedOpts, sonarLintFactory, reportFactory, fileFinder, projectHome).run();
            system.exit(exitCode);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            system.exit(ERROR);
        }
    }

    private static Charset createCharset(Options parsedOpts) {
        if (parsedOpts.charset() != null) {
            return Charset.forName(parsedOpts.charset());
        } else {
            return Charset.defaultCharset();
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
