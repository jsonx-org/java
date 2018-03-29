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
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;

abstract class ComplexModel extends Model {
  // Annullable, Recurrable
  public ComplexModel(final Schema schema, final $Element binding, final boolean nullable, final $NonNegativeInteger minOccurs, final $MaxCardinality maxOccurs) {
    super(schema, binding, nullable, minOccurs, maxOccurs);
  }

  // Nameable, Annullable
  public ComplexModel(final Schema schema, final $Element binding, final String name, final boolean required, final boolean nullable) {
    super(schema, binding, name, required, nullable);
  }

  // Nameable
  public ComplexModel(final Schema schema, final String name, final Boolean required, final Boolean nullable, final Integer minOccurs, final Integer maxOccurs, final String doc) {
    super(schema, name, required, nullable, minOccurs, maxOccurs, doc);
  }

  protected ComplexModel(final Element copy) {
    super(copy);
  }
}