package cc.report;

import org.sonarlint.cli.report.Reporter;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ReportFactory extends org.sonarlint.cli.report.ReportFactory {
    public ReportFactory(Charset charset) {
        super(charset);
    }

    @Override
    public List<Reporter> createReporters(Path basePath) {
        return Arrays.asList(new JsonReport(basePath.toString()));
    }
}
