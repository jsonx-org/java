/* Copyright (c) 2017 JSONx
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

package org.jsonx.library;

import org.jsonx.StringProperty;
import org.libj.net.URLs;

public class OnlineArticle extends Publication {
  private String url;

  @StringProperty(name = "url", pattern = URLs.REGEX)
  public String getUrl() {
    return this.url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }
}