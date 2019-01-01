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
import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.openjax.classic.json.JsonReader;

public class InvalidTest {
  private static final JxEncoder validEncoder = new JxEncoder(2, true);

  public static class InvalidName {
    @Test
    public void testInvalidName() throws IOException {
      final Invalid.InvalidName binding = new Invalid.InvalidName();
      binding.setInvalidName(true);

      try {
        validEncoder.marshal(binding);
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

  public static class InvalidType {
    @Test
    public void testInvalidType() throws DecodeException, IOException {
      final Invalid.InvalidType binding = new Invalid.InvalidType();
      binding.setInvalidType(true);

      try {
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + Invalid.InvalidType.class.getName() + "#invalidType: Field with (nullable=true & use=Use.OPTIONAL) must be of type: " + Optional.class.getName(), e.getMessage());
      }

      try {
        final String json = "{\"invalidType\": true}";
        JxDecoder.parseObject(Invalid.InvalidType.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + Invalid.InvalidType.class.getName() + "#invalidType: Field with (nullable=true & use=Use.OPTIONAL) must be of type: " + Optional.class.getName(), e.getMessage());
      }
    }
  }

  public static class Bool {
    @Test
    public void testInvalidType() throws DecodeException, IOException, NoSuchFieldException {
      final Invalid.Bool binding = new Invalid.Bool();
      binding.setInvalidType(7);

      try {
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JxUtil.getFullyQualifiedFieldName(Invalid.Bool.class.getDeclaredField("invalidType")), e.getMessage());
      }

      try {
        final String json = "{\"invalidType\": true}";
        JxDecoder.parseObject(Invalid.Bool.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertTrue(e.getMessage().contains("is not compatible with property \"invalidType\" of type \"boolean\" with value:"));
      }
    }

    @Test
    public void testInvalidAnnotation() throws IOException, NoSuchFieldException {
      final Invalid.Bool binding = new Invalid.Bool();
      binding.setInvalidAnnotation(true);

      try {
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JxUtil.getFullyQualifiedFieldName(Invalid.Bool.class.getDeclaredField("invalidAnnotation")), e.getMessage());
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
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JxUtil.getFullyQualifiedFieldName(Invalid.Num.class.getDeclaredField("invalidType")), e.getMessage());
      }

      try {
        final String json = "{\"invalidType\": 7}";
        JxDecoder.parseObject(Invalid.Num.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertTrue(e.getMessage().contains("is not compatible with property \"invalidType\" of type \"number\" with value:"));
      }
    }

    @Test
    public void testInvalidAnnotation() throws IOException, NoSuchFieldException {
      final Invalid.Num binding = new Invalid.Num();
      binding.setInvalidAnnotation(7);

      try {
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JxUtil.getFullyQualifiedFieldName(Invalid.Num.class.getDeclaredField("invalidAnnotation")), e.getMessage());
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
      binding.setInvalidForm(Optional.of(BigDecimal.valueOf(5.8)));

      try {
        validEncoder.marshal(binding);
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
      binding.setInvalidRange(Optional.of((byte)7));

      try {
        validEncoder.marshal(binding);
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
      binding.setInvalidType(Optional.of(7));

      try {
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JxUtil.getFullyQualifiedFieldName(Invalid.Str.class.getDeclaredField("invalidType")), e.getMessage());
      }

      try {
        final String json = "{\"invalidType\": \"foo\"}";
        JxDecoder.parseObject(Invalid.Str.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertTrue(e.getMessage().contains("is not compatible with property \"invalidType\" of type \"string\" with value:"));
      }
    }

    @Test
    public void testInvalidAnnotation() throws IOException, NoSuchFieldException {
      final Invalid.Str binding = new Invalid.Str();
      binding.setInvalidAnnotation(Optional.of("foo"));

      try {
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JxUtil.getFullyQualifiedFieldName(Invalid.Str.class.getDeclaredField("invalidAnnotation")), e.getMessage());
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
      binding.setInvalidPattern(Optional.of("foo"));

      try {
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JxUtil.getFullyQualifiedFieldName(Invalid.Str.class.getDeclaredField("invalidPattern")), e.getMessage());
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

  public static class Arr {
    @Test
    public void testInvalidType() throws DecodeException, IOException, NoSuchFieldException {
      final Invalid.Arr binding = new Invalid.Arr();
      binding.setInvalidType(Optional.of(7));

      try {
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JxUtil.getFullyQualifiedFieldName(Invalid.Arr.class.getDeclaredField("invalidType")), e.getMessage());
      }

      try {
        final String json = "{\"invalidType\": [\"foo\"]}";
        JxDecoder.parseObject(Invalid.Arr.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertTrue(e.getMessage().contains("is not compatible with property \"invalidType\" of type \"array\" with value:"));
      }
    }

    @Test
    public void testInvalidAnnotation() throws IOException, NoSuchFieldException {
      final Invalid.Arr binding = new Invalid.Arr();
      binding.setInvalidAnnotation(Collections.emptyList());

      try {
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals("Invalid field: " + JxUtil.getFullyQualifiedFieldName(Invalid.Arr.class.getDeclaredField("invalidAnnotation")), e.getMessage());
      }

      try {
        final String json = "{\"invalidAnnotation\": []}";
        JxDecoder.parseObject(Invalid.Arr.class, new JsonReader(new StringReader(json)));
        fail("Expected DecodeException");
      }
      catch (final DecodeException e) {
        assertTrue(e.getMessage().startsWith("Expected \"invalidAnnotation\" to be a \"boolean\", but got: ["));
      }
    }

    @Test
    public void testInvalidAnnotationType() throws DecodeException, IOException {
      final Invalid.ArrAnnotationType binding = new Invalid.ArrAnnotationType();
      binding.setInvalidAnnotationType(Optional.of(Collections.emptyList()));

      try {
        validEncoder.marshal(binding);
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals(Override.class.getName() + " does not declare @ArrayType or @ArrayProperty", e.getMessage());
      }

      try {
        final String json = "{\"invalidPattern\": \"foo\"}";
        JxDecoder.parseObject(Invalid.ArrAnnotationType.class, new JsonReader(new StringReader(json)));
        fail("Expected ValidationException");
      }
      catch (final ValidationException e) {
        assertEquals(Override.class.getName() + " does not declare @ArrayType or @ArrayProperty", e.getMessage());
      }
    }
  }
}