package org.sonarlint.cli.analysis;

import org.sonarlint.cli.util.Logger;

public class LogOutputWrapper extends DefaultLogOutput {
    public LogOutputWrapper(Logger logger, boolean verbose) {
        super(logger, verbose);
    }
}
