package org.jsonx;

import java.util.Iterator;
import java.util.LinkedList;

public class JsonPath implements Iterable<Object> {
  private final LinkedList<Object> path = new LinkedList<>();
  private final String str;

  public JsonPath(final String str) {
    this.str = str;
    boolean escape = false;
    boolean indexes = false;
    int start = 0;
    for (int i = 0, i$ = str.length(); i < i$; ++i) {
      final char ch = str.charAt(i);
      if (ch == '\\') {
        escape = !escape;
      }
      else if (escape) {
        escape = false;
      }
      else if (ch == '.') {
        path.add(str.substring(start, i));
        start = ++i;
      }
      else if (ch == '[') {
        path.add(str.substring(start, i));
        start = ++i;
        indexes = true;
      }
      else if (ch == ']') {
        path.add(Integer.valueOf(str.substring(start, i)));
        start = i += 2;
      }
    }

    if (!indexes)
      path.add(str.substring(start));
  }

  @Override
  public Iterator<Object> iterator() {
    return path.iterator();
  }

  @Override
  public String toString() {
    return str;
  }
}