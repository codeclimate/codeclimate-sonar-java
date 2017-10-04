/*
 * SonarLint CLI
 * Copyright (C) 2016-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarlint.cli;

import cc.JsonReport;
import org.sonarlint.cli.analysis.SonarLintFactory;
import org.sonarlint.cli.config.ConfigurationReader;
import org.sonarlint.cli.report.ReportFactory;
import org.sonarlint.cli.report.Reporter;
import org.sonarlint.cli.util.Logger;
import org.sonarlint.cli.util.System2;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.sonarlint.cli.SonarProperties.PROJECT_HOME;


public class CustomMain extends org.sonarlint.cli.Main {
    static final int SUCCESS = 0;
    static final int ERROR = 1;
    private static final Logger LOGGER = Logger.get();

    public CustomMain(Options opts, SonarLintFactory sonarLintFactory, ReportFactory reportFactory, InputFileFinder fileFinder, Path projectHome) {
        super(opts, sonarLintFactory, reportFactory, fileFinder, projectHome);
    }

    public static void main(String[] args) {
        execute(args, System2.INSTANCE);
    }

    static void execute(String[] args, System2 system) {
        Options parsedOpts;
        try {
            parsedOpts = Options.parse(args);
        } catch (ParseException e) {
            LOGGER.error("Error parsing arguments: " + e.getMessage(), e);
            Options.printUsage();
            system.exit(ERROR);
            return;
        }

        Charset charset;
        try {
            if (parsedOpts.charset() != null) {
                charset = Charset.forName(parsedOpts.charset());
            } else {
                charset = Charset.defaultCharset();
            }
        } catch (Exception e) {
            LOGGER.error("Error creating charset: " + parsedOpts.charset(), e);
            system.exit(ERROR);
            return;
        }

        InputFileFinder fileFinder = new InputFileFinder(parsedOpts.src(), parsedOpts.tests(), parsedOpts.exclusions(), charset);
        ReportFactory reportFactory = new CustomReportFactory(charset);
        ConfigurationReader reader = new ConfigurationReader();
        SonarLintFactory sonarLintFactory = new SonarLintFactory(reader);

        int ret = new CustomMain(parsedOpts, sonarLintFactory, reportFactory, fileFinder, getProjectHome(system)).run();
        system.exit(ret);
        return;
    }

    private static Path getProjectHome(System2 system) {
        String projectHome = system.getProperty(PROJECT_HOME);
        if (projectHome == null) {
            throw new IllegalStateException("Can't find project home. System property not set: " + PROJECT_HOME);
        }
        return Paths.get(projectHome);
    }

    private static class CustomReportFactory extends ReportFactory {
        public CustomReportFactory(Charset charset) {
            super(charset);
        }

        @Override
        public List<Reporter> createReporters(Path basePath) {
            return Arrays.asList(new JsonReport());
        }
    }
}
