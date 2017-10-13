package org.sonarlint.cli.analysis;

import org.sonarlint.cli.config.ConfigurationReader;

import java.net.URL;

public class SonarLintFactoryWrapper extends SonarLintFactory {
    public SonarLintFactoryWrapper(ConfigurationReader configurationReader) {
        super(configurationReader);
    }

    public URL[] plugins() {
        try {
            return loadPlugins();
        } catch (Exception e) {
            throw new IllegalStateException("Error loading plugins", e);
        }

    }
}
