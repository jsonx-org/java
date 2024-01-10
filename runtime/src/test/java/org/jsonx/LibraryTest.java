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

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jsonx.library.Address;
import org.jsonx.library.Book;
import org.jsonx.library.Employee;
import org.jsonx.library.Individual;
import org.jsonx.library.Library;
import org.jsonx.library.OnlineArticle;
import org.jsonx.library.Publishing;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryTest {
  private static final Logger logger = LoggerFactory.getLogger(LibraryTest.class);
  private static final JxEncoder encoder = JxEncoder.VALIDATING._2;

  @SafeVarargs
  private static void testArray(final Object obj, final Class<? extends Annotation> annotationType, final Class<? extends Annotation> ... annotationTypes) throws DecodeException, IOException {
    final String json = encoder.toString((List<?>)obj, annotationType);
    System.err.println(json);
    try {
      JxDecoder.VALIDATING.parseArray(json);
    }
    catch (final IllegalArgumentException e) {
    }

    final Object decoded = JxDecoder.VALIDATING.parseArray(json, annotationTypes);
    assertEquals(obj.toString(), decoded.toString());
  }

  @SafeVarargs
  @SuppressWarnings({"rawtypes", "unchecked"})
  private static void testObject(final JxObject obj, final Class ... classes) throws DecodeException, IOException {
    final String json = encoder.toString(obj);
    final Object decoded = JxDecoder.VALIDATING.parseObject(json, classes);
    assertEquals(obj.toString(), decoded.toString());
  }

  private static String r(final String ... items) {
    return items[(int)(Math.random() * items.length)];
  }

  private static Address createAddress(final boolean withNumber) {
    final Address address = new Address();
    if (withNumber)
      address.setNumber((int)(Math.random() * 1000));

    address.setStreet(r("Welcome Cir.", "Sukhumvit Soi 13", "Fillmore St.", "Lake Ave.", "12th St."));
    address.setCity(r("Salt Lake City", "San Franciso", "Minneapolis", "New York", "Wattana"));
    address.setPostalCode(String.valueOf((int)(Math.random() * 100000)));
    address.setLocality(r("Utah", "California", "Minnesota", "New York", "Bangkok"));
    address.setCountry(r("USA", "Thailand", "France", "Germany", "Mexico"));
    return address;
  }

  private static Employee createEmployee() {
    final Individual emergencyContact = new Individual();
    emergencyContact.setName(r("John Doe", "Mark Taylor", "Stan Kolev", "Jeremy Olander", "Laird Smith"));
    emergencyContact.setGender("M");
    emergencyContact.setAddress(createAddress(true));

    final Employee employee = new Employee();
    employee.setName(r("Joanne Mazzella", "Aura Dantin", "Candis Kivi", "Era Gilmer", "Hisako Hunt"));
    employee.setGender("F");
    employee.setAddress(createAddress(true));
    employee.setEmergencyContact(emergencyContact);

    return employee;
  }

  @Test
  public void testAddress() throws DecodeException, IOException {
    testObject(createAddress(true), Book.class, Employee.class, Address.class, Library.class);
  }

  @Test
  public void testAddressDecodeException() throws IOException {
    try {
      final String json = "{\"street\":\"Sukhumvit Soi 13\",\"city\":\"Salt Lake City\",\"postalCode\":\"19207\",\"locality\":\"Minnesota\",\"country\":\"Thailand\"}";
      JxDecoder.VALIDATING.parseObject(json, Address.class);
      fail("Expected DecodeException");
    }
    catch (final DecodeException e) {
      assertEquals("Property \"number\" is required: null", e.getMessage());
    }
  }

  @Test
  public void testAddressDecodeExceptionEmpty() throws IOException {
    try {
      JxDecoder.VALIDATING.parseObject("{}", Address.class);
      fail("Expected DecodeException");
    }
    catch (final DecodeException e) {
      assertTrue(e.getMessage().contains("is required: null"));
    }
  }

  @Test
  public void testStaff() throws DecodeException, IOException {
    final ArrayList<Employee> staff = new ArrayList<>();
    for (int i = 0; i < 12; ++i) // [N]
      staff.add(createEmployee());

    testArray(staff, Library.Staff.class, TestArray.Array1d2.class, Library.Staff.class);
  }

  @Test
  public void testEmployee() throws DecodeException, IOException {
    testObject(createEmployee(), Book.class, Address.class, Employee.class, Library.class);
  }

  @Test
  public void testLibrary() throws DecodeException, IOException {
    final Publishing bookPub1 = new Publishing();
    bookPub1.setPublisher("Pubby Wubby");
    bookPub1.setYear(BigInteger.valueOf(2000));

    final Publishing bookPub2 = new Publishing();
    bookPub2.setPublisher("Inter Minter");
    bookPub2.setYear(BigInteger.valueOf(2010));

    final Book book = new Book();
    book.setTitle("Magical Book");
    book.setAuthors(Optional.of(Arrays.asList("Z&$GY 6-4si[1", "Billy Bob", "Jimmy James", "Wendy Woo")));
    book.setEditors(Optional.of(Arrays.asList("[&]$b |6)?f)A$", "Silly Willy", "Johnie John", "Randy Dandy")));
    book.setIndex(Arrays.asList(Arrays.asList(1, "Part 1, Chapter 1"), Arrays.asList(2, "Part 1, Chapter 2"),
      Arrays.asList(3, "Part 1, Chapter 3"), Arrays.asList(1, "Part 2, Chapter 1"),
      Arrays.asList(2, "Part 2, Chapter 2"), Arrays.asList(3, "Part 2, Chapter 3")));
    book.setIsbn("978-3-16-148410-0");
    book.setPublishings(new ArrayList<>());
    book.getPublishings().add(bookPub1);
    book.getPublishings().add(bookPub2);

    final Publishing onlinePub1 = new Publishing();
    onlinePub1.setPublisher("Online Pub");
    onlinePub1.setYear(BigInteger.valueOf(2001));

    final Publishing onlinePub2 = new Publishing();
    onlinePub2.setPublisher("Super Pub");
    onlinePub2.setYear(BigInteger.valueOf(2007));

    final OnlineArticle article = new OnlineArticle();
    article.setTitle("Online Article");
    article.setAuthors(Optional.of(Arrays.asList("Z&$GY 6-4si[1", "Mr. Online", "Mrs. Online")));
    article.setEditors(Optional.of(Arrays.asList("[&]$b |6)?f)A$", "Mr. Editor")));
    article.setPublishings(new ArrayList<>());
    article.getPublishings().add(onlinePub1);
    article.getPublishings().add(onlinePub2);
    article.setUrl("http://www.jbc.org/content/274/19/13434.full");

    final Publishing journalPub = new Publishing();
    journalPub.setPublisher("Science Publisher");
    journalPub.setYear(BigInteger.valueOf(2003));

    final Library.Journal journal = new Library.Journal();
    journal.setTitle("Zoology Redefined");
    journal.setSubject(Optional.of("Zoology"));
    journal.setAuthors(Optional.of(Arrays.asList("Mr. Smith")));
    journal.setEditors(Optional.of(Arrays.asList("Mr. Echo")));
    journal.setOpenAccess(Optional.empty());
    journal.setPublishings(new ArrayList<>());
    journal.getPublishings().add(journalPub);

    final Library library = new Library();
    library.setAddress(Optional.of(createAddress(true)));
    library.setHandicap(true);
    final ArrayList<List<String>> schedule = new ArrayList<>();
    for (final String[] slot : new String[][] {{"07:00", "17:00"}, {"08:00", "18:00"}, {"09:00", "19:00"}, {"10:00", "20:00"},{"11:00", "21:00"}, {"12:00", "22:00"}, {"13:00", "23:00"}})
      schedule.add(Arrays.asList(slot));

    library.setSchedule(schedule);

    library.setBooks(Collections.singletonList(book));
    library.setArticles(Collections.singletonList(article));
    library.setJournals(Collections.singletonList(journal));

    library.setStaff(Collections.singletonList(createEmployee()));

    final String json = encoder.toString(library);
    if (logger.isInfoEnabled()) { logger.info(json); }
    JxDecoder.VALIDATING.parseObject(json, Library.class);
  }

  @Test
  public void testOnPropertyDecode() throws DecodeException, IOException {
    final String json = "{\"year\":2003,\"publisher\":\"Science Publisher\",\"extra\":false}";
    try {
      JxDecoder.VALIDATING.parseObject(json, Publishing.class);
      fail("Expected DecodeException");
    }
    catch (final DecodeException e) {
      assertTrue(e.getMessage().startsWith("Unknown property: \"extra\""));
    }

    JxDecoder.VALIDATING.parseObject(json, (o, p, v) -> o instanceof Publishing, Publishing.class);
  }

  @Test
  public void testNullValidateError() throws IOException {
    final String json = "{\"number\":1894,\"street\":\"Welcome Circle\",\"city\":\"San Francisco\",\"postalCode\":\"94110\",\"locality\":\"San Francisco\",\"country\":null}";
    try {
      JxDecoder.VALIDATING.parseObject(json, Address.class);
      fail("Expected DecodeException");
    }
    catch (final DecodeException e) {
      assertTrue(e.getMessage().startsWith("Property \"country\" is not nullable: null"));
    }
  }
}