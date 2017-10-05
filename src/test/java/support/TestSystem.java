package support;

import org.sonarlint.cli.util.System2;

public class TestSystem extends System2 {
    public int exitCode;

    @Override
    public void exit(int exitCode) {
        this.exitCode = exitCode;
    }
}
