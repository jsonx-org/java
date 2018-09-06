/* Copyright (c) 2016 lib4j
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

import org.lib4j.json.jas.JasReader;

public class DecodeException extends Exception {
  private static final long serialVersionUID = 7087309932016830988L;

  public DecodeException(final JasReader json) {
    this(null, json, null);
  }

  public DecodeException(final String message, final JasReader json) {
    this(message, json, null);
  }

  public DecodeException(final JasReader json, final Throwable cause) {
    this(null, json, cause);
  }

  public DecodeException(final String message, final JasReader json, final Throwable cause) {
    super(message != null ? message + " [errorOffset: " + json.getIndex() + "]" : "[errorOffset: " + json.getIndex() + "]", cause);
  }
}