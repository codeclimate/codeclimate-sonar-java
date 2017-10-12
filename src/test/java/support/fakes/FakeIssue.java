package support.fakes;

import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;

import javax.annotation.CheckForNull;
import java.util.List;

public class FakeIssue implements Issue {

    String path = "/tmp/File.java";
    Integer startLine;
    Integer endLine;

    public FakeIssue(String path, Integer startLine, Integer endLine) {
        this.path = path;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    @Override
    public String getSeverity() {
        return null;
    }

    @CheckForNull
    @Override
    public String getType() {
        return null;
    }

    @CheckForNull
    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getRuleKey() {
        return null;
    }

    @Override
    public String getRuleName() {
        return null;
    }

    @CheckForNull
    @Override
    public Integer getStartLine() {
        return startLine;
    }

    @CheckForNull
    @Override
    public Integer getStartLineOffset() {
        return null;
    }

    @CheckForNull
    @Override
    public Integer getEndLine() {
        return endLine;
    }

    @CheckForNull
    @Override
    public Integer getEndLineOffset() {
        return null;
    }

    @Override
    public List<Flow> flows() {
        return null;
    }

    @CheckForNull
    @Override
    public ClientInputFile getInputFile() {
        return new FakeClientInputFile(path);
    }
}
