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
import org.fastjax.maven.mojo.GeneratorMojo;
import org.fastjax.maven.mojo.SourceInput;
import org.fastjax.xml.ValidationException;
import org.openjax.jsonx.generator.Schema;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.Jsonx;
import org.openjax.xsb.runtime.Bindings;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@Execute(goal = "generate")
public class JsonxMojo extends GeneratorMojo {
  @SourceInput
  @Parameter(property = "schemas", required = true)
  private List<String> schemas;

  @Parameter(property = "pkg", required = true)
  private String pkg;

  @Override
  public void execute(final Configuration configuration) throws MojoExecutionException, MojoFailureException {
    try {
      for (final URL resource : configuration.getSourceInputs("schemas")) {
        try (final InputStream in = resource.openStream()) {
          final Schema schema = new Schema((Jsonx)Bindings.parse(in), pkg);
          schema.toSource(configuration.getDestDir());
        }
      }
    }
    catch (final IOException | ValidationException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}