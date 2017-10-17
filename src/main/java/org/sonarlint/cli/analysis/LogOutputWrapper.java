package org.sonarlint.cli.analysis;

import org.sonarlint.cli.util.Logger;

public class LogOutputWrapper extends DefaultLogOutput {

    public LogOutputWrapper(boolean verbose) {
        super(Logger.get(), verbose);
    }
}
