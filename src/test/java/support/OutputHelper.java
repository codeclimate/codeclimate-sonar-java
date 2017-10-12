package support;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class OutputHelper {
    public final OutputStream stdout;
    public final OutputStream stderr;

    public OutputHelper(OutputStream out, OutputStream err) {
        this.stdout = out;
        this.stderr = err;
    }

    public static OutputHelper setup() {
        OutputStream out = new ByteArrayOutputStream();
        OutputStream err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
        return new OutputHelper(out, err);
    }
}
