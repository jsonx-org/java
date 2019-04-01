/* Copyright (c) 2017 OpenJAX
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

package org.openjax.jsonx;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;
import org.openjax.standard.maven.mojo.GeneratorMojo;
import org.openjax.standard.maven.mojo.SourceInput;
import org.openjax.standard.util.Identifiers;
import org.openjax.standard.xml.api.ValidationException;
import org.openjax.xsb.runtime.Bindings;

@Mojo(name="generate", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
@Execute(goal="generate")
public class JsonxMojo extends GeneratorMojo {
  @SourceInput
  @Parameter(property="schemas", required=true)
  private List<String> schemas;

  @Parameter(name="prefix", property="prefix", required=true)
  private String prefix;

  @Override
  public void execute(final Configuration configuration) throws MojoExecutionException, MojoFailureException {
    final char lastChar = prefix == null ? '\0' : prefix.charAt(prefix.length() - 1);
    if (!Identifiers.isValid(lastChar == '$' || lastChar == '.' ? prefix.substring(0, prefix.length() - 1) : prefix))
      throw new IllegalArgumentException("Illegal \"prefix\" parameter: " + prefix);

    try {
      for (final URL resource : configuration.getSourceInputs("schemas")) {
        try (final InputStream in = resource.openStream()) {
          final SchemaElement schema = new SchemaElement((xL4gluGCXYYJc.Schema)Bindings.parse(in), prefix);
          schema.toSource(configuration.getDestDir());
        }
      }
    }
    catch (final IOException | ValidationException e) {
      throw new MojoExecutionException(e.getClass().getSimpleName() + ": " + e.getMessage(), e);
    }
  }
}