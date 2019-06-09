/* Copyright (c) 2019 JSONx
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.libj.net.URLs;
import org.xml.sax.SAXException;

@Mojo(name="convert", defaultPhase=LifecyclePhase.GENERATE_RESOURCES)
@Execute(goal="convert")
public class ConvertMojo extends JxMojo {
  @Override
  public void execute(final Configuration configuration) throws MojoExecutionException, MojoFailureException {
    try {
      for (final String schema : schemas) {
        final URL url = new URL(schema);
        final String name = URLs.getName(url);
        final int dot = name.lastIndexOf('.') + 1;
        final String shortName = name.substring(0, dot);
        final String ext = name.substring(dot);

        final String destName;
        final String converted;
        if ("jsd".equals(ext)) {
          destName = shortName + "jsdx";
          converted = Converter.jsdToJsdx(url);
        }
        else if ("jsdx".equals(ext)) {
          destName = shortName + "jsd";
          converted = Converter.jsdxToJsd(url);
        }
        else {
          throw new MojoExecutionException("File extension not recognized as JSD or JSDx: " + schema);
        }

        final File destFile = new File(configuration.getDestDir(), destName);
        Files.write(destFile.toPath(), converted.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      }
    }
    catch (final DecodeException | IOException | SAXException e) {
      throw new MojoExecutionException(e.getClass().getSimpleName() + ": " + e.getMessage(), e);
    }
  }
}