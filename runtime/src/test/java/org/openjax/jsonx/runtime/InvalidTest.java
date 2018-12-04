/* Copyright (c) 2018 OpenJAX
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

package org.openjax.jsonx.runtime;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;

import org.fastjax.json.JsonReader;
import org.junit.Test;

public class InvalidTest {
  private static final JxEncoder validEncoder = new JxEncoder(2, true);
  private static final JxEncoder invalidEncoder = new JxEncoder(2, false);

  public static class InvalidName {
    @Test
    public void testInvalidName() throws IOException {
      final Invalid.InvalidName binding = new Invalid.InvalidName();
      binding.setInvalidName(true);

      try {
        validEncoder.encode(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Method getFoo() does not exist for " + InvalidName.class.getSimpleName() + ".foo", e.getMessage());
      }

      try {
        final String json = "{\"invalidName\": true}";
        JxDecoder.parseObject(Invalid.InvalidName.class, new JsonReader(new StringReader(json)));
        fail("Expected DecodeException");
      }
      catch (final DecodeException e) {
        assertEquals("Unknown property: \"invalidName\" [errorOffset: 1]", e.getMessage());
      }
    }
  }

  public static class Bool {
    @Test
    public void testInvalidType() throws DecodeException, IOException, NoSuchFieldException {
      final Invalid.Bool binding = new Invalid.Bool();
      binding.setInvalidType(7);

      try {
        validEncoder.encode(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JsonxUtil.getFullyQualifiedFieldName(Invalid.Bool.class.getDeclaredField("invalidType")), e.getMessage());
      }

      try {
        final String json = "{\"invalidType\": true}";
        JxDecoder.parseObject(Invalid.Bool.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertTrue(e.getMessage().contains("is not compatible with property \"invalidType\" of type \"boolean\""));
      }
    }

    @Test
    public void testInvalidAnnotation() throws IOException, NoSuchFieldException {
      final Invalid.Bool binding = new Invalid.Bool();
      binding.setInvalidAnnotation(true);

      try {
        validEncoder.encode(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JsonxUtil.getFullyQualifiedFieldName(Invalid.Bool.class.getDeclaredField("invalidAnnotation")), e.getMessage());
      }

      try {
        final String json = "{\"invalidAnnotation\": true}";
        JxDecoder.parseObject(Invalid.Bool.class, new JsonReader(new StringReader(json)));
        fail("Expected DecodeException");
      }
      catch (final DecodeException e) {
        assertTrue(e.getMessage().startsWith("Expected \"invalidAnnotation\" to be a \"number\", but got: true"));
      }
    }
  }

  public static class Num {
    @Test
    public void testInvalidType() throws DecodeException, IOException, NoSuchFieldException {
      final Invalid.Num binding = new Invalid.Num();
      binding.setInvalidType(true);

      try {
        validEncoder.encode(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JsonxUtil.getFullyQualifiedFieldName(Invalid.Num.class.getDeclaredField("invalidType")), e.getMessage());
      }

      try {
        final String json = "{\"invalidType\": 7}";
        JxDecoder.parseObject(Invalid.Num.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertTrue(e.getMessage().contains("is not compatible with property \"invalidType\" of type \"number\""));
      }
    }

    @Test
    public void testInvalidAnnotation() throws IOException, NoSuchFieldException {
      final Invalid.Num binding = new Invalid.Num();
      binding.setInvalidAnnotation(7);

      try {
        validEncoder.encode(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JsonxUtil.getFullyQualifiedFieldName(Invalid.Num.class.getDeclaredField("invalidAnnotation")), e.getMessage());
      }

      try {
        final String json = "{\"invalidAnnotation\": true}";
        JxDecoder.parseObject(Invalid.Num.class, new JsonReader(new StringReader(json)));
        fail("Expected DecodeException");
      }
      catch (final DecodeException e) {
        assertTrue(e.getMessage().startsWith("Expected \"invalidAnnotation\" to be a \"string\", but got: true"));
      }
    }

    @Test
    public void testInvalidForm() throws IOException {
      final Invalid.Num binding = new Invalid.Num();
      binding.setInvalidForm(BigDecimal.valueOf(5.8));

      try {
        validEncoder.encode(binding);
        fail("Expected EncodeException");
      }
      catch (final EncodeException e) {
        assertEquals("Illegal Form.INTEGER value: 5.8", e.getMessage());
      }

      try {
        final String json = "{\"invalidForm\": 10.9}";
        JxDecoder.parseObject(Invalid.Num.class, new JsonReader(new StringReader(json)));
        fail("Expected DecodeException");
      }
      catch (final DecodeException e) {
        assertEquals("Illegal Form.INTEGER value [errorOffset: 1]", e.getMessage());
      }
    }
  }

  public static class NumRange {
    @Test
    public void testInvalidRange() throws DecodeException, IOException {
      final Invalid.NumRange binding = new Invalid.NumRange();
      binding.setInvalidRange((byte)7);

      try {
        validEncoder.encode(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertTrue(e.getMessage().startsWith("Invalid range attribute:"));
      }

      try {
        final String json = "{\"invalidRange\": 7}";
        JxDecoder.parseObject(Invalid.NumRange.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertTrue(e.getMessage().startsWith("Invalid range attribute:"));
      }
    }
  }

  public static class Str {
    @Test
    public void testInvalidType() throws DecodeException, IOException, NoSuchFieldException {
      final Invalid.Str binding = new Invalid.Str();
      binding.setInvalidType(7);

      try {
        validEncoder.encode(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JsonxUtil.getFullyQualifiedFieldName(Invalid.Str.class.getDeclaredField("invalidType")), e.getMessage());
      }

      try {
        final String json = "{\"invalidType\": \"foo\"}";
        JxDecoder.parseObject(Invalid.Str.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertTrue(e.getMessage().contains("is not compatible with property \"invalidType\" of type \"string\""));
      }
    }

    @Test
    public void testInvalidAnnotation() throws IOException, NoSuchFieldException {
      final Invalid.Str binding = new Invalid.Str();
      binding.setInvalidAnnotation("foo");

      try {
        validEncoder.encode(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JsonxUtil.getFullyQualifiedFieldName(Invalid.Str.class.getDeclaredField("invalidAnnotation")), e.getMessage());
      }

      try {
        final String json = "{\"invalidAnnotation\": \"foo\"}";
        JxDecoder.parseObject(Invalid.Str.class, new JsonReader(new StringReader(json)));
        fail("Expected DecodeException");
      }
      catch (final DecodeException e) {
        assertTrue(e.getMessage().startsWith("Expected \"invalidAnnotation\" to be a \"boolean\", but got: \"foo\""));
      }
    }

    @Test
    public void testInvalidPattern() throws DecodeException, IOException, NoSuchFieldException {
      final Invalid.Str binding = new Invalid.Str();
      binding.setInvalidPattern("foo");

      try {
        validEncoder.encode(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JsonxUtil.getFullyQualifiedFieldName(Invalid.Str.class.getDeclaredField("invalidPattern")), e.getMessage());
      }

      try {
        final String json = "{\"invalidPattern\": \"foo\"}";
        JxDecoder.parseObject(Invalid.Str.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Malformed pattern: [0-9]{{2,4}", e.getMessage());
      }
    }
  }
}