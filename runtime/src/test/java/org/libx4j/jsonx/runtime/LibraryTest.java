/* Copyright (c) 2017 lib4j
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

package org.libx4j.jsonx.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryTest {
  final Logger logger = LoggerFactory.getLogger(LibraryTest.class);

  @Test
  public void testLibrary() {
    final Publishing bookPub1 = new Publishing();
    bookPub1.setPublisher("Pubby Wubby");
    bookPub1.setYear(2000);

    final Publishing bookPub2 = new Publishing();
    bookPub2.setPublisher("Inter Minter");
    bookPub2.setYear(2010);

    final Book book = new Book();
    book.setTitle("Magical Book");
    book.setAuthors(Arrays.asList("Billy Bob", "Jimmy James", "Wendy Woo"));
    book.setEditors(Arrays.asList("Silly Willy", "Johnie John", "Randy Dandy"));
    book.setIndex(Arrays.asList(1, new Object[] {1, "Part 1, Chapter 1"}, new Object[] {2, "Part 1, Chapter 2"}, new Object[] {3, "Part 1, Chapter 3"},
                                2, new Object[] {1, "Part 2, Chapter 1"}, new Object[] {2, "Part 2, Chapter 2"}, new Object[] {3, "Part 2, Chapter 3"}));
    book.setIsbn("978-3-16-148410-0");
    book.setPublishings(new ArrayList<>());
    book.getPublishings().add(bookPub1);
    book.getPublishings().add(bookPub2);

    final Publishing onlinePub1 = new Publishing();
    onlinePub1.setPublisher("Online Pub");
    onlinePub1.setYear(2001);

    final Publishing onlinePub2 = new Publishing();
    onlinePub2.setPublisher("Super Online Pub");
    onlinePub2.setYear(2007);

    final OnlineArticle article = new OnlineArticle();
    article.setAuthors(Arrays.asList("Mr. Online", "Mrs. Online"));
    article.setEditors(Arrays.asList("Mr. Editor"));
    article.setPublishings(new ArrayList<>());
    article.getPublishings().add(onlinePub1);
    article.getPublishings().add(onlinePub2);

    final Publishing journalPub = new Publishing();
    journalPub.setPublisher("Science Publisher");
    journalPub.setYear(2003);

    final Library.Journal journal = new Library.Journal();
    journal.setTitle("Zoology Redefined");
    journal.setSubject("Zoology");
    journal.setAuthors(Arrays.asList("Mr. Smith"));
    journal.setEditors(Arrays.asList("Mr. Echo"));
    journal.setOpenAccess(true);
    journal.setPublishings(new ArrayList<>());
    journal.getPublishings().add(journalPub);

    final Address address = new Address();
    address.setNumber(372);
    address.setStreet("Welcome St.");
    address.setCity("Salt Lake City");
    address.setPostalCode("73923");
    address.setLocality("Utah");
    address.setCountry("USA");

    final Library library = new Library();
    library.setAddress(address);
    library.setHandicap(true);
    final List<List<String>> schedule = new ArrayList<>();
    for (final String[] slot : new String[][] {{"07:00", "17:00"}, {"07:00", "17:00"}, {"07:00", "17:00"}, {"07:00", "17:00"}, {"07:00", "17:00"}, {"09:00", "15:00"}, {"09:00", "15:00"}})
      schedule.add(Arrays.asList(slot));

    library.setSchedule(schedule);

    final List<Publication> publications = new ArrayList<>();
    publications.add(book);
    publications.add(article);
    publications.add(journal);
    library.setPublications(publications);

    logger.info(JSObject.toString(library));
  }
}