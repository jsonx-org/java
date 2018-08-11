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

package org.libx4j.jsonx.runtime;

import java.util.Comparator;

public class AttributeComparator implements Comparator<String> {
  private static final String[] order = {"id", "name", "xsi:type", "template", "reference", "min", "max", "minOccurs", "maxOccurs", "urlEncode", "urlDecode"};

  @Override
  public int compare(final String o1, final String o2) {
    for (final String term : order) {
      if (term.equals(o1))
        return term.equals(o2) ? 0 : -1;

      if (term.equals(o2))
        return term.equals(o1) ? 0 : 1;
    }

    return o1.compareTo(o2);
  }
}