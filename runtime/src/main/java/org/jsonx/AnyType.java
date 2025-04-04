/* Copyright (c) 2019 JSONx
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
import java.util.Map;

import org.libj.lang.Numbers;

final class AnyType {
  static boolean isEnabled(final Class<?> annotation) {
    return annotation != JxObject.class && annotation != Annotation.class;
  }

  static boolean isEnabled(final BooleanType booleanType) {
    return !"\0".equals(booleanType.decode());
  }

  static boolean isEnabled(final NumberType numberType) {
    return !"\0".equals(numberType.decode());
  }

  static boolean isEnabled(final StringType stringType) {
    return !"\0".equals(stringType.decode());
  }

  static t fromToken(final String token) {
    if ("true".equals(token) || "false".equals(token))
      return booleans;

    if (Numbers.isNumber(token))
      return numbers;

    final int len = token.length();
    if (len > 1 && token.charAt(0) == '"' && token.charAt(len - 1) == '"')
      return strings;

    throw new UnsupportedOperationException("Unsupported token: " + token);
  }

  static t fromObject(final Object obj) {
    if (obj instanceof Boolean)
      return booleans;

    if (obj instanceof Number)
      return numbers;

    if (obj instanceof CharSequence)
      return strings;

    if (obj instanceof List)
      return arrays;

    if (obj instanceof JxObject)
      return objects;

    if (obj instanceof Map)
      return objects;

    throw new UnsupportedOperationException("Unsupported type: " + obj.getClass().getName());
  }

  private static final StringType defaultStringType = new StringType() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return StringType.class;
    }

    @Override
    public String pattern() {
      return "";
    }

    @Override
    public Class<?> type() {
      return String.class;
    }

    @Override
    public String decode() {
      return "\0";
    }

    @Override
    public String encode() {
      return "";
    }
  };

  private static final StringType wildcardStringType = new StringType() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return StringType.class;
    }

    @Override
    public String pattern() {
      return "";
    }

    @Override
    public Class<?> type() {
      return String.class;
    }

    @Override
    public String decode() {
      return "";
    }

    @Override
    public String encode() {
      return "";
    }
  };

  private static final BooleanType defaultBooleanType = new BooleanType() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return BooleanType.class;
    }

    @Override
    public Class<?> type() {
      return Boolean.class;
    }

    @Override
    public String decode() {
      return "\0";
    }

    @Override
    public String encode() {
      return "";
    }
  };

  private static final BooleanType wildcardBooleanType = new BooleanType() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return BooleanType.class;
    }

    @Override
    public Class<?> type() {
      return Boolean.class;
    }

    @Override
    public String decode() {
      return "";
    }

    @Override
    public String encode() {
      return "";
    }
  };

  private static final NumberType defaultNumberType = new NumberType() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return NumberType.class;
    }

    @Override
    public int scale() {
      return Integer.MAX_VALUE;
    }

    @Override
    public String range() {
      return "";
    }

    @Override
    public Class<?> type() {
      return Number.class;
    }

    @Override
    public String decode() {
      return "\0";
    }

    @Override
    public String encode() {
      return "";
    }
  };

  private static final NumberType wildcardNumberType = new NumberType() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return NumberType.class;
    }

    @Override
    public int scale() {
      return Integer.MIN_VALUE;
    }

    @Override
    public String range() {
      return "";
    }

    @Override
    public Class<?> type() {
      return Number.class;
    }

    @Override
    public String decode() {
      return "";
    }

    @Override
    public String encode() {
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
    public BooleanType booleans() {
      return defaultBooleanType;
    }

    @Override
    public NumberType numbers() {
      return defaultNumberType;
    }

    @Override
    public StringType strings() {
      return defaultStringType;
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
    public BooleanType booleans() {
      return wildcardBooleanType;
    }

    @Override
    public NumberType numbers() {
      return defaultNumberType;
    }

    @Override
    public StringType strings() {
      return defaultStringType;
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
    public BooleanType booleans() {
      return defaultBooleanType;
    }

    @Override
    public NumberType numbers() {
      return wildcardNumberType;
    }

    @Override
    public StringType strings() {
      return defaultStringType;
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
    public BooleanType booleans() {
      return defaultBooleanType;
    }

    @Override
    public NumberType numbers() {
      return defaultNumberType;
    }

    @Override
    public StringType strings() {
      return defaultStringType;
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
    public BooleanType booleans() {
      return defaultBooleanType;
    }

    @Override
    public NumberType numbers() {
      return defaultNumberType;
    }

    @Override
    public StringType strings() {
      return wildcardStringType;
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