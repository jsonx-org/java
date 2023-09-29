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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.libj.net.URLs;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@Execute(goal = "generate")
public class GenerateMojo extends JxMojo {
  @Parameter(property = "templateThreshold", required = false)
  private int templateThreshold = 1;

  @Parameter(property = "setBuilder", required = false)
  private boolean setBuilder = true;

  @Parameter(property = "namespacePackages", required = false)
  private List namespacePackages;

  private static void processConfiguration(final Settings.Builder builder, final Xpp3Dom root) throws MojoExecutionException {
    for (int i = 0, i$ = root.getChildCount(); i < i$; ++i) { // [RA]
      final Xpp3Dom child0 = root.getChild(i);
      if ("namespacePackages".equals(child0.getName())) {
        for (int j = 0, j$ = child0.getChildCount(); j < j$; ++j) { // [RA]
          final Xpp3Dom child1 = child0.getChild(j);
          if (!"namespacePackage".equals(child1.getName()))
            throw new MojoExecutionException("Unsupported element: configuration/namespacePackages/" + child1.getName());

          final String namespace = child1.getAttribute("namespace");
          final String pkg = child1.getAttribute("package");
          if (namespace != null)
            builder.withNamespacePackage(namespace, pkg);
          else
            builder.withDefaultPackage(pkg);
        }
      }
    }
  }

  private boolean scanPluginConfiguration(final Settings.Builder builder) throws MojoExecutionException {
    for (final Plugin plugin : project.getBuildPlugins()) { // [L]
      if (plugin.getId().equals(execution.getPlugin().getId())) {
        final Xpp3Dom configuration = (Xpp3Dom)plugin.getConfiguration();
        if (configuration != null)
          processConfiguration(builder, configuration);

        return true;
      }
    }

    return false;
  }

  @Override
  public void execute(final Configuration configuration) throws MojoExecutionException, MojoFailureException {
    if (schemas.size() == 0) {
      getLog().info("Nothing to do -- no schemas provided");
      return;
    }

    final Settings.Builder builder = new Settings.Builder();
    if (!scanPluginConfiguration(builder))
      throw new IllegalStateException();

    processConfiguration(builder, execution.getConfiguration());
    final Settings settings = builder.withTemplateThreshold(templateThreshold).withSetBuilder(setBuilder).build();

    try {
      final LinkedHashSet<String> set = new LinkedHashSet<>(schemas);
      final int size = set.size();
      final URL[] urls = new URL[size];
      final Iterator<String> iterator = set.iterator();
      for (int i = 0; i < size; ++i)
        urls[i] = URLs.create(iterator.next());

      Generator.generate(configuration.getDestDir(), settings, urls);
    }
    catch (final IOException e) {
      throw new MojoExecutionException(e.getClass().getSimpleName() + ": " + e.getMessage(), e);
    }
  }
}