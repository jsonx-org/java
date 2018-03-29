/* Copyright (c) 2018 lib4j
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

package org.libx4j.jsonx.generator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.lib4j.lang.Strings;
import org.lib4j.xml.SimpleNamespaceContext;
import org.lib4j.xml.dom.DOMStyle;
import org.lib4j.xml.dom.DOMs;
import org.lib4j.xml.validate.ValidationException;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc;
import org.libx4j.xsb.runtime.Binding;
import org.libx4j.xsb.runtime.Bindings;
import org.libx4j.xsb.runtime.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonListener;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DOMDifferenceEngine;
import org.xmlunit.diff.DifferenceEngine;

public class JsonxTest {
  private static final Logger logger = LoggerFactory.getLogger(JsonxTest.class);

  private static xL2gluGCXYYJc.Jsonx newControlBinding() throws IOException, MalformedURLException, ParseException, ValidationException {
    return (xL2gluGCXYYJc.Jsonx)Bindings.parse(new File("../../maven/plugin/jsonx-maven-plugin/src/test/resources/type.jsonx").toURI().toURL());
  }

  @Test
  @Ignore
  public void testToJsonx() throws IOException, MalformedURLException, ParseException, ValidationException, XPathExpressionException {
    final xL2gluGCXYYJc.Jsonx controlBinding = newControlBinding();
    final xL2gluGCXYYJc.Jsonx testBinding = (xL2gluGCXYYJc.Jsonx)Bindings.parse(new ByteArrayInputStream(new Schema(controlBinding).toJSONX().getBytes()));
    assertEqual(controlBinding, testBinding);
  }

  @Test
  public void testToJava() throws IOException, MalformedURLException, ParseException, ValidationException {
    final xL2gluGCXYYJc.Jsonx controlBinding = newControlBinding();
    new Schema(controlBinding).toJava(new File("target/generated-test-sources/jsonx"));
  }

  private static final Map<String,String> prefixToNamespaceURI = new HashMap<String,String>();

  static {
    prefixToNamespaceURI.put("jsonx", "http://rdb.libx4j.org/jsonx-0.9.7.xsd");
    prefixToNamespaceURI.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
  }

  private static XPath newXPath() {
    final XPath xPath = XPathFactory.newInstance().newXPath();
    xPath.setNamespaceContext(new SimpleNamespaceContext(prefixToNamespaceURI));
    return xPath;
  }

  private static String evalXPath(final Element element, final String xpath) throws XPathExpressionException {
    final NodeList nodes = (NodeList)newXPath().evaluate(xpath, element, XPathConstants.NODESET);
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < nodes.getLength(); ++i) {
      final Node node = nodes.item(i);
      builder.append('\n').append(DOMs.domToString(node, DOMStyle.INDENT));
    }

    return builder.length() == 0 ? null : builder.substring(1);
  }

  private static void assertEqual(final Binding expected, final Binding actual) throws XPathExpressionException {
    final Element controlElement = expected.toDOM();
    final Element testElement = actual.toDOM();

    final String controlXml = DOMs.domToString(controlElement, DOMStyle.INDENT);
    final String testXml = DOMs.domToString(testElement, DOMStyle.INDENT);
    System.out.println(testXml);

    final Source controlSource = Input.fromString(controlXml).build();
    final Source testSource = Input.fromString(testXml).build();

    final DifferenceEngine diffEngine = new DOMDifferenceEngine();
    diffEngine.addDifferenceListener(new ComparisonListener() {
      @Override
      public void comparisonPerformed(final Comparison comparison, final ComparisonResult result) {
        final String controlXPath = comparison.getControlDetails().getXPath() == null ? null : comparison.getControlDetails().getXPath().replaceAll("/([^@])", "/jsonx:$1");
        if (controlXPath == null || controlXPath.matches("^.*\\/@[:a-z]+$") || controlXPath.contains("text()"))
          return;

        try {
          final String controlEval = evalXPath(controlElement, controlXPath);

          final String testXPath = comparison.getTestDetails().getXPath() == null ? null : comparison.getTestDetails().getXPath().replaceAll("/([^@])", "/jsonx:$1");
          final String testEval = testXPath == null ? null : evalXPath(testElement, testXPath);
          logger.info(Strings.printColumns("Expected: " + controlXPath + "\n" + controlEval, "Actual: " + testXPath + "\n" + testEval));
        }
        catch (final XPathExpressionException e) {
          throw new RuntimeException(e);
        }

        Assert.fail("Found a difference: " + comparison);
      }
    });

    try {
      diffEngine.compare(controlSource, testSource);
    }
    catch (final RuntimeException e) {
      if (e.getCause() instanceof XPathExpressionException)
        throw (XPathExpressionException)e.getCause();

      throw e;
    }
  }
}