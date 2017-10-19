package cc.report;

import cc.models.Severity;
import org.sonarlint.cli.report.Reporter;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ReportFactory extends org.sonarlint.cli.report.ReportFactory {

    Severity minimumSeverity;

    public ReportFactory(Charset charset, Severity minimumSeverity) {
        super(charset);
        this.minimumSeverity = minimumSeverity;
    }

    @Override
    public List<Reporter> createReporters(Path basePath) {
        return Arrays.asList(new JsonReport(minimumSeverity, basePath.toString()));
    }
}
