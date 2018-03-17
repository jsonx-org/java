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

import org.libx4j.jsonx.runtime.ArrayElement;
import org.libx4j.jsonx.runtime.ArrayProperty;
import org.libx4j.jsonx.runtime.BooleanProperty;
import org.libx4j.jsonx.runtime.JsonxObject;
import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.ObjectProperty;
import org.libx4j.jsonx.runtime.StringElement;
import org.libx4j.jsonx.runtime.StringProperty;

@JsonxObject
public class Library {
  @JsonxObject
  public static class Journal extends Publication {
    @StringProperty(nullable=false)
    public String subject;

    @BooleanProperty(nullable=false)
    public Boolean openAccess;
  }

  @JsonxObject
  public static class StreetSheet extends Publication {
  }

  @ObjectProperty(nullable=false, type=Address.class)
  public Address address;

  @BooleanProperty(name="handicap", nullable=false)
  public Boolean handicapEquipped;

  @StringElement(id=2, pattern="\\d{2}:\\d{2}", nullable=false, minOccurs=2, maxOccurs=2)
  @ArrayElement(id=1, nullable=false, elementIds=2, minOccurs=7, maxOccurs=7)
  @ArrayProperty(nullable=false, elementIds=1)
  public String[][] schedule;

  @ObjectElement(id=1, type=Publication.class, nullable=false)
  @ArrayProperty(nullable=false, elementIds=1)
  public final List<Publication> publications = new ArrayList<Publication>();

  @ObjectElement(id=1, type=StreetSheet.class, nullable=false)
  @ArrayProperty(nullable=false, elementIds=1)
  public final List<StreetSheet> streetSheets = new ArrayList<StreetSheet>();
}