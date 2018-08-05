package org.libx4j.jsonx.runtime;

import java.lang.reflect.Field;

public class JsonxUtil {
  public static String getName(final String name, final Field field) {
    return name.length() > 0 ? name : field.getName();
  }
}