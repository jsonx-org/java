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

package org.jsonx.sample.bank;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsonx.EncodeException;
import org.jsonx.JxEncoder;
import org.junit.Test;

public class BankSampleTest {
  @Test
  public void testSwiftTypeRequired() {
    final Swift swift = new Swift();
    try {
      JxEncoder._2.marshal(swift);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("is required"));
    }
  }

  @Test
  public void testSwiftCodeRequired() {
    final Swift swift = new Swift();
    swift.setType("swift");
    try {
      JxEncoder._2.marshal(swift);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("is required"));
    }
  }

  @Test
  public void testSwiftCodeInvalid() {
    final Swift swift = new Swift();
    swift.setType("swift");
    swift.setCode("invalid");
    try {
      JxEncoder._2.marshal(swift);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("Pattern does not match"));
    }
  }

  @Test
  public void testSwiftValid() {
    final Swift swift = new Swift();
    swift.setType("swift");
    swift.setCode("CTBAAU2S");
    System.out.println(JxEncoder._2.marshal(swift));
  }

  @Test
  public void testIbanTypeRequired() {
    final Iban iban = new Iban();
    try {
      JxEncoder._2.marshal(iban);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("is required"));
    }
  }

  @Test
  public void testIbanCodeRequired() {
    final Iban iban = new Iban();
    iban.setType("iban");
    try {
      JxEncoder._2.marshal(iban);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("is required"));
    }
  }

  @Test
  public void testIbanCodeInvalid() {
    final Iban iban = new Iban();
    iban.setType("iban");
    iban.setCode("invalid");
    try {
      JxEncoder._2.marshal(iban);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("Pattern does not match"));
    }
  }

  @Test
  public void testIbanValid() {
    final Iban iban = new Iban();
    iban.setType("iban");
    iban.setCode("DE91 1000 0000 0123 4567 89");
    System.out.println(JxEncoder._2.marshal(iban));
  }

  @Test
  public void testAchTypeRequired() {
    final Ach ach = new Ach();
    try {
      JxEncoder._2.marshal(ach);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("is required"));
    }
  }

  @Test
  public void testAchCodeRequired() {
    final Ach ach = new Ach();
    ach.setType("ach");
    try {
      JxEncoder._2.marshal(ach);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("is required"));
    }
  }

  @Test
  public void testAchRoutingRequired() {
    final Ach ach = new Ach();
    ach.setType("ach");
    ach.setCode("379272957729384");
    try {
      JxEncoder._2.marshal(ach);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("is required"));
    }
  }

  @Test
  public void testAchCodeInvalid() {
    final Ach ach = new Ach();
    ach.setType("ach");
    ach.setCode("");
    ach.setRouting("021000021");
    try {
      JxEncoder._2.marshal(ach);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("Pattern does not match"));
    }
  }

  @Test
  public void testAchRoutingInvalid() {
    final Ach ach = new Ach();
    ach.setType("ach");
    ach.setCode("379272957729384");
    ach.setRouting("02100_ABC");
    try {
      JxEncoder._2.marshal(ach);
      fail("Expected EncodeException");
    }
    catch (final EncodeException e) {
      System.err.println(e.getMessage());
      assertTrue(e.getMessage().contains("Pattern does not match"));
    }
  }

  @Test
  public void testAchValid() {
    final Ach ach = new Ach();
    ach.setType("ach");
    ach.setCode("379272957729384");
    ach.setRouting("021000021");
    System.out.println(JxEncoder._2.marshal(ach));
  }
}