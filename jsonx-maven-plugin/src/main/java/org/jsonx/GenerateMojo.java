/* Copyright (c) 2017 JSONx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.jsonx;

import java.io.IOException;
import java.net.URL;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.libj.util.Identifiers;

@Mojo(name="generate", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
@Execute(goal="generate")
public class GenerateMojo extends JxMojo {
  @Parameter(name="prefix", property="prefix", required=true)
  private String prefix;

  @Override
  public void execute(final Configuration configuration) throws MojoExecutionException, MojoFailureException {
    final char lastChar = prefix == null ? '\0' : prefix.charAt(prefix.length() - 1);
    if (!Identifiers.isValid(lastChar == '$' || lastChar == '.' ? prefix.substring(0, prefix.length() - 1) : prefix))
      throw new IllegalArgumentException("Illegal \"prefix\" parameter: " + prefix);

    try {
      for (final String schema : schemas) {
        SchemaElement.parse(new URL(schema), prefix).toSource(configuration.getDestDir());
      }
    }
    catch (final IOException e) {
      throw new MojoExecutionException(e.getClass().getSimpleName() + ": " + e.getMessage(), e);
    }
  }
}