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

import org.junit.Test;

public class LibraryTest {
  @Test
  public void testLibrary() {
    final Publishing bookPub1 = new Publishing();
    bookPub1.publisher = "Pubby Wubby";
    bookPub1.year = 2000;

    final Publishing bookPub2 = new Publishing();
    bookPub2.publisher = "Inter Minter";
    bookPub2.year = 2010;

    final Book book = new Book();
    book.title = "Magical Book";
    book.authors = new String[] {"Billy Bob", "Jimmy James", "Wendy Woo"};
    book.editors = new String[] {"Silly Willy", "Johnie John", "Randy Dandy"};
    book.index = new Object[] {1, new Object[] {1, "Part 1, Chapter 1"}, new Object[] {2, "Part 1, Chapter 2"}, new Object[] {3, "Part 1, Chapter 3"},
                               2, new Object[] {1, "Part 2, Chapter 1"}, new Object[] {2, "Part 2, Chapter 2"}, new Object[] {3, "Part 2, Chapter 3"}};
    book.isbn = "978-3-16-148410-0";
    book.publishings.add(bookPub1);
    book.publishings.add(bookPub2);

    final Publishing onlinePub1 = new Publishing();
    onlinePub1.publisher = "Online Pub";
    onlinePub1.year = 2001;

    final Publishing onlinePub2 = new Publishing();
    onlinePub2.publisher = "Super Online Pub";
    onlinePub2.year = 2007;

    final OnlineArticle article = new OnlineArticle();
    article.authors = new String[] {"Mr. Online", "Mrs. Online"};
    article.editors = new String[] {"Mr. Editor"};
    article.publishings.add(onlinePub1);
    article.publishings.add(onlinePub2);

    final Publishing journalPub = new Publishing();
    journalPub.publisher = "Science Publisher";
    journalPub.year = 2003;

    final Library.Journal journal = new Library.Journal();
    journal.title = "Zoology Redefined";
    journal.subject = "Zoology";
    journal.authors = new String[] {"Mr. Smith"};
    journal.editors = new String[] {"Mr. Echo"};
    journal.openAccess = true;
    journal.publishings.add(journalPub);

    final Address address = new Address();
    address.number = 372;
    address.street = "Welcome St.";
    address.city = "Salt Lake City";
    address.postalCode = "73923";
    address.locality = "Utah";
    address.country = "USA";

    final Library library = new Library();
    library.address = address;
    library.handicapEquipped = true;
    library.schedule = new String[][] {{"07:00", "17:00"}, {"07:00", "17:00"}, {"07:00", "17:00"}, {"07:00", "17:00"}, {"07:00", "17:00"}, {"09:00", "15:00"}, {"09:00", "15:00"}};

    library.publications.add(book);
    library.publications.add(article);
    library.publications.add(journal);

    System.err.println(library);
  }
}