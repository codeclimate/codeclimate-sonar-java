package support.fakes;

import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

import javax.annotation.CheckForNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class FakeClientInputFile implements ClientInputFile {
    String path;

    public FakeClientInputFile(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

    @CheckForNull
    @Override
    public boolean isTest() {
        return false;
    }

    @CheckForNull
    @Override
    public Charset getCharset() {
        return null;
    }

    @Override
    public <G> G getClientObject() {
        return null;
    }

    @Override
    public InputStream inputStream() throws IOException {
        return null;
    }

    @Override
    public String contents() throws IOException {
        return null;
    }
}
