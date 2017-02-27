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
package org.sonarlint.cli.report;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import org.sonarlint.cli.util.Logger;
import org.sonarsource.sonarlint.core.client.api.common.RuleDetails;
import org.sonarsource.sonarlint.core.client.api.common.analysis.AnalysisResults;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;
import org.sonarsource.sonarlint.core.tracking.Trackable;

public class JsonReport implements Reporter {

  private static final Logger LOGGER = Logger.get();

  JsonReport() {

  }

  @Override
  public void execute(String projectName, Date date, Collection<Trackable> trackables, AnalysisResults result, Function<String, RuleDetails> ruleDescriptionProducer) {
    for (Trackable trackable : trackables) {
      Issue issue = trackable.getIssue();
      RuleDetails ruleDetails = ruleDescriptionProducer.apply(issue.getRuleKey());

      JsonObject json = new JsonObject();
      json.addProperty("type", "issue");
      json.addProperty("check_name", issue.getRuleKey());

      JsonArray categories = new JsonArray();
      json.add("categories", categories);
      categories.add("Bug Risk");

      json.addProperty("description", issue.getMessage());

      JsonObject content = new JsonObject();
      json.add("content", content);
      content.addProperty("body", ruleDetails.getHtmlDescription());
      // // ruleDetails.getExtendedDescription();

      JsonObject location = new JsonObject();
      json.add("location", location);

      // Code Climate CLI expects relative path to file
      location.addProperty("path", issue.getInputFile().getPath().replaceFirst("^/code-read-write/", ""));

      JsonObject lines = new JsonObject();
      location.add("lines", lines);

      if (issue.getStartLine() != null) {
        lines.addProperty("begin", issue.getStartLine());

        if (issue.getEndLine() != null) {
          lines.addProperty("end", issue.getEndLine());
        } else {
          lines.addProperty("end", 1);
        }
      } else {
        lines.addProperty("begin", 1);
        lines.addProperty("end", 1);
      }

      System.out.println(json.toString() + "\0");
    }
  }
}
