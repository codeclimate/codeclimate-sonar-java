package support.fakes;

import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;
import org.sonarsource.sonarlint.core.tracking.TextRange;
import org.sonarsource.sonarlint.core.tracking.Trackable;

import javax.annotation.CheckForNull;

public class FakeTrackable implements Trackable{

    private FakeIssue fakeIssue;

    public FakeTrackable(FakeIssue fakeIssue) {
        this.fakeIssue = fakeIssue;
    }

    @Override
    public Issue getIssue() {
        return fakeIssue;
    }

    @Override
    public String getRuleKey() {
        return null;
    }

    @Override
    public String getRuleName() {
        return null;
    }

    @Override
    public String getSeverity() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @CheckForNull
    @Override
    public String getType() {
        return null;
    }

    @CheckForNull
    @Override
    public Integer getLine() {
        return null;
    }

    @CheckForNull
    @Override
    public Integer getLineHash() {
        return null;
    }

    @CheckForNull
    @Override
    public TextRange getTextRange() {
        return null;
    }

    @CheckForNull
    @Override
    public Integer getTextRangeHash() {
        return null;
    }

    @CheckForNull
    @Override
    public Long getCreationDate() {
        return null;
    }

    @CheckForNull
    @Override
    public String getServerIssueKey() {
        return null;
    }

    @Override
    public boolean isResolved() {
        return false;
    }

    @Override
    public String getAssignee() {
        return null;
    }
}
