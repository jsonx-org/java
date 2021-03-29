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

import java.util.ArrayList;
import java.util.List;

import org.jsonx.JxEncoder;

public class BankSample {
  public static void main(final String[] args) {
    for (final Message message : createMessages()) {
      System.out.println(JxEncoder.VALIDATING._2.toString(message));
    }
  }

  private static List<Message> createMessages() {
    final List<Message> messages = new ArrayList<>();

    final Swift swift = new Swift();
    swift.setType("swift");
    swift.setCode("CTBAAU2S");
    messages.add(swift);

    final Iban iban = new Iban();
    iban.setType("iban");
    iban.setCode("DE91 1000 0000 0123 4567 89");
    messages.add(iban);

    final Ach ach = new Ach();
    ach.setType("ach");
    ach.setCode("379272957729384");
    ach.setRouting("021000021");
    messages.add(ach);

    return messages;
  }
}