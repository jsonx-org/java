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
import java.util.List;

import org.libx4j.jsonx.runtime.ArrayProperty;
import org.libx4j.jsonx.runtime.JsonxObject;
import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.StringElement;
import org.libx4j.jsonx.runtime.StringProperty;

@JsonxObject
public abstract class Publication {
  @StringProperty(nullable=false)
  public String title;

  @StringElement(id=1, pattern="\\S+ \\S+", nullable=false, minOccurs=1)
  @ArrayProperty(nullable=false, elementIds=1)
  public String[] authors;

  @StringElement(id=1, pattern="\\S+ \\S+", nullable=false, minOccurs=1)
  @ArrayProperty(nullable=false, elementIds=1)
  public String[] editors;

  @ObjectElement(id=1, type=Publishing.class, nullable=false, minOccurs=1)
  @ArrayProperty(nullable=false, elementIds=1)
  public final List<Publishing> publishings = new ArrayList<>();
}