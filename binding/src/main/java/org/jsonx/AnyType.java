/* Copyright (c) 2019 OpenJAX
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

import java.lang.annotation.Annotation;
import java.util.List;

import org.openjax.ext.util.Numbers;

final class AnyType {
  static boolean isEnabled(final Class<?> annotation) {
    return annotation != JxObject.class && annotation != Annotation.class;
  }

  static boolean isEnabled(final NumberType number) {
    return !"\0".equals(number.range());
  }

  static boolean isEnabled(final String pattern) {
    return !"\0".equals(pattern);
  }

  static t fromToken(final String token) {
    if ("true".equals(token) || "false".equals(token))
      return booleans;

    if (Numbers.isNumber(token))
      return numbers;

    if (token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"')
      return strings;

    throw new UnsupportedOperationException("Unsupported token: " + token);
  }

  static t fromObject(final Object obj) {
    if (obj instanceof List)
      return arrays;

    if (obj instanceof Boolean)
      return booleans;

    if (obj instanceof Number)
      return numbers;

    if (obj instanceof JxObject)
      return objects;

    if (obj instanceof String)
      return strings;

    throw new UnsupportedOperationException("Unsupported object type: " + obj.getClass().getName());
  }

  private static final NumberType defaultNumberType = new NumberType() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return NumberType.class;
    }

    @Override
    public Form form() {
      return null;
    }

    @Override
    public String range() {
      return "\0";
    }
  };

  private static final NumberType wildcardNumberType = new NumberType() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return NumberType.class;
    }

    @Override
    public Form form() {
      return Form.REAL;
    }

    @Override
    public String range() {
      return "";
    }
  };

  static final t arrays = new t() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return t.class;
    }

    @Override
    public Class<? extends Annotation> arrays() {
      return AnyArray.class;
    }

    @Override
    public boolean booleans() {
      return false;
    }

    @Override
    public NumberType numbers() {
      return defaultNumberType;
    }

    @Override
    public String strings() {
      return "\0";
    }

    @Override
    public Class<? extends JxObject> objects() {
      return JxObject.class;
    }
  };

  static final t booleans = new t() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return t.class;
    }

    @Override
    public Class<? extends Annotation> arrays() {
      return Annotation.class;
    }

    @Override
    public boolean booleans() {
      return true;
    }

    @Override
    public NumberType numbers() {
      return defaultNumberType;
    }

    @Override
    public String strings() {
      return "\0";
    }

    @Override
    public Class<? extends JxObject> objects() {
      return JxObject.class;
    }
  };

  static final t numbers = new t() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return t.class;
    }

    @Override
    public Class<? extends Annotation> arrays() {
      return Annotation.class;
    }

    @Override
    public boolean booleans() {
      return false;
    }

    @Override
    public NumberType numbers() {
      return wildcardNumberType;
    }

    @Override
    public String strings() {
      return "\0";
    }

    @Override
    public Class<? extends JxObject> objects() {
      return JxObject.class;
    }
  };

  static final t objects = new t() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return t.class;
    }

    @Override
    public Class<? extends Annotation> arrays() {
      return Annotation.class;
    }

    @Override
    public boolean booleans() {
      return false;
    }

    @Override
    public NumberType numbers() {
      return defaultNumberType;
    }

    @Override
    public String strings() {
      return "\0";
    }

    @Override
    public Class<? extends JxObject> objects() {
      return AnyObject.class;
    }
  };

  static final t strings = new t() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return t.class;
    }

    @Override
    public Class<? extends Annotation> arrays() {
      return Annotation.class;
    }

    @Override
    public boolean booleans() {
      return false;
    }

    @Override
    public NumberType numbers() {
      return defaultNumberType;
    }

    @Override
    public String strings() {
      return ".*";
    }

    @Override
    public Class<? extends JxObject> objects() {
      return JxObject.class;
    }
  };

  static final t[] all = {
    arrays,
    booleans,
    numbers,
    objects,
    strings
  };

  private AnyType() {
  }
}