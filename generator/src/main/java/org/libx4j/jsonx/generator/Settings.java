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

package org.libx4j.jsonx.generator;

import java.io.Serializable;

public class Settings implements Serializable {
  private static final long serialVersionUID = -9151836587381478204L;

  public static final Settings DEFAULT = new Settings(1);

  private final int templateThreshold;

  public Settings(final int templateThreshold) {
    this.templateThreshold = templateThreshold;
    if (templateThreshold < 0)
      throw new IllegalArgumentException("templateThreshold < 0: " + templateThreshold);
  }

  /**
   * @return The non-negative number of referrers needed for a <code>Model</code>
   *         to be declared as a template root member of the jsonx element.
   */
  public int getTemplateThreshold() {
    return this.templateThreshold;
  }
}