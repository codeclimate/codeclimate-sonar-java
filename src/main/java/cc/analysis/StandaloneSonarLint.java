package cc.analysis;

import org.sonarlint.cli.analysis.IssueCollector;
import org.sonarlint.cli.analysis.LogOutputWrapper;
import org.sonarlint.cli.report.ReportFactory;
import org.sonarsource.sonarlint.core.client.api.common.ProgressMonitor;
import org.sonarsource.sonarlint.core.client.api.common.analysis.AnalysisResults;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneAnalysisConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneSonarLintEngine;
import org.sonarsource.sonarlint.core.tracking.IssueTrackable;
import org.sonarsource.sonarlint.core.tracking.Trackable;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StandaloneSonarLint extends org.sonarlint.cli.analysis.StandaloneSonarLint {
    private StandaloneSonarLintEngine engine;
    private Path workDir;
    private LogOutputWrapper logWrapper;

    public StandaloneSonarLint(StandaloneSonarLintEngine engine, Path workDir, LogOutputWrapper logWrapper) {
        super(engine);
        this.engine = engine;
        this.workDir = workDir;
        this.logWrapper = logWrapper;
    }

    @Override
    protected void doAnalysis(Map<String, String> properties, ReportFactory reportFactory, List<ClientInputFile> inputFiles, Path baseDirPath) {
        Date start = new Date();

        IssueCollector collector = new IssueCollector();
        StandaloneAnalysisConfiguration config = new StandaloneAnalysisConfiguration(baseDirPath, workDir, inputFiles, properties);
        AnalysisResults result = engine.analyze(config, collector, logWrapper, new NoOpProgressMonitor());
        Collection<Trackable> trackables = collector.get().stream().map(IssueTrackable::new).collect(Collectors.toList());
        generateReports(trackables, result, reportFactory, baseDirPath.getFileName().toString(), baseDirPath, start);
    }

    private static class NoOpProgressMonitor extends ProgressMonitor {
    }
}
