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

package org.libx4j.jsonx.generator;

import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Element;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$MaxCardinality;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;

abstract class SimpleModel extends Model {
  // Annullable, Recurrable
  public SimpleModel(final $Element binding, final boolean nullable, final $NonNegativeInteger minOccurs, final $MaxCardinality maxOccurs) {
    super(binding, nullable, minOccurs, maxOccurs);
  }

  // Nameable, Annullable
  public SimpleModel(final $Element binding, final String name, final boolean required, final boolean nullable) {
    super(binding, name, required, nullable);
  }

  // Nameable
  public SimpleModel(final String name, final Boolean required, final Boolean nullable, final Integer minOccurs, final Integer maxOccurs) {
    super(name, required, nullable, minOccurs, maxOccurs);
  }

  public SimpleModel() {
    super(null, null, null, null, null);
  }

  public SimpleModel(final Element element) {
    super(element);
  }
}