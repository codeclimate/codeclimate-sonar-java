package support;

import org.sonarlint.cli.util.System2;

import java.util.Properties;

public class SystemHelper extends System2 {
    public int exitCode;
    final Properties properties = new Properties();

    @Override
    public void exit(int exitCode) {
        this.exitCode = exitCode;
    }

    @Override
    public Properties properties() {
        return properties;
    }

    @Override
    public String property(String key) {
        return properties.getProperty(key);
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
