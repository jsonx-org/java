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
import java.util.LinkedHashSet;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.libj.lang.Identifiers;

@Mojo(name="generate", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
@Execute(goal="generate")
public class GenerateMojo extends JxMojo {
  @Parameter(property="prefix", required=true)
  private String prefix;

  @Parameter(property="templateThreshold", required=false)
  private int templateThreshold = 1;

  @Parameter(property="setBuilder", required=false)
  private boolean setBuilder = true;

  @Override
  public void execute(final Configuration configuration) throws MojoExecutionException, MojoFailureException {
    if (schemas.size() == 0) {
      getLog().info("Nothing to do -- no schemas provided");
      return;
    }

    final Settings.Builder builder = new Settings.Builder();

    final Xpp3Dom root = execution.getConfiguration();
    for (int i = 0, i$ = root.getChildCount(); i < i$; ++i) {
      final Xpp3Dom child0 = root.getChild(i);
      if ("defaultBinding".equals(child0.getName())) {
        for (int j = 0, j$ = child0.getChildCount(); j < j$; ++j) {
          final Xpp3Dom child1 = child0.getChild(i);
          if (!"number".equals(child1.getName()))
            throw new MojoExecutionException("Unsupported element: configuration/defaultBinding/" + child1.getName());

          for (int k = 0, k$ = child1.getChildCount(); k < k$; ++k) {
            final Xpp3Dom child2 = child1.getChild(i);
            final String name = child2.getName();
            if ("integer".equals(name)) {
              final String p = child2.getAttribute("primitive");
              if (p != null)
                builder.withIntegerPrimitive(p);

              final String o = child2.getAttribute("object");
              if (o != null)
                builder.withIntegerObject(o);
              else
                throw new MojoExecutionException("configuration/defaultBinding/number/integer/@object must be specified");
            }
            else if ("real".equals(name)) {
              final String p = child2.getAttribute("primitive");
              if (p != null)
                builder.withRealPrimitive(p);

              final String o = child2.getAttribute("object");
              if (o != null)
                builder.withRealObject(o);
              else
                throw new MojoExecutionException("configuration/defaultBinding/number/real/@object must be specified");
            }
            else {
              throw new MojoExecutionException("Unsupported element: configuration/defaultBinding/number" + name);
            }
          }
        }
      }
    }

    final char lastChar = prefix == null ? '\0' : prefix.charAt(prefix.length() - 1);
    if (!Identifiers.isValid(lastChar == '$' || lastChar == '.' ? prefix.substring(0, prefix.length() - 1) : prefix))
      throw new IllegalArgumentException("Illegal \"prefix\" parameter: " + prefix);

    final Settings settings = builder.withPrefix(prefix).withTemplateThreshold(templateThreshold).withSetBuilder(setBuilder).build();

    try {
      for (final String schema : new LinkedHashSet<>(schemas)) // [S]
        SchemaElement.parse(new URL(schema), settings).toSource(configuration.getDestDir());
    }
    catch (final IOException e) {
      throw new MojoExecutionException(e.getClass().getSimpleName() + ": " + e.getMessage(), e);
    }
  }
}