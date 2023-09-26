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

package org.jsonx;

import java.util.List;

import org.apache.maven.plugins.annotations.Parameter;
import org.openjax.maven.mojo.FilterParameter;
import org.openjax.maven.mojo.FilterType;
import org.openjax.maven.mojo.GeneratorMojo;

public abstract class JxMojo extends GeneratorMojo {
  @FilterParameter(FilterType.URL)
  @Parameter(property = "schemas", required = true)
  List<String> schemas;
}