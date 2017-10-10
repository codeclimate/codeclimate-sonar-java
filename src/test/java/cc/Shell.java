package cc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Shell {
    public static Process execute(String command) throws IOException, InterruptedException {
        java.lang.Process process = Runtime.getRuntime().exec(command);
        StringBuilder stdout = consumeBuffer(process.getInputStream());
        StringBuilder stderr = consumeBuffer(process.getErrorStream());

        process.waitFor();

        Process localProcess = new Process(process.exitValue(), stdout.toString(), stderr.toString());
        if (localProcess.exitCode != 0) {
            System.err.println(localProcess.stderr);
        }
        return localProcess;
    }

    private static StringBuilder consumeBuffer(InputStream stream) throws IOException {
        StringBuilder stdout = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = in.readLine()) != null) {
            stdout.append(line);
        }
        return stdout;
    }

    public static class Process {
        public final int exitCode;
        public final String stdout;
        public final String stderr;

        public Process(int exitCode, String stdout, String stderr) {
            this.exitCode = exitCode;
            this.stdout = stdout;
            this.stderr = stderr;
        }
    }
}
