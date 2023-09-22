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

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;

import org.libj.lang.Numbers.Composite;
import org.libj.lang.Strings;
import org.openjax.json.JsonReader;
import org.openjax.xml.api.CharacterDatas;
import org.openjax.xml.sax.SAXParsers;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Utility class that provides functions to convert JSON document to JSONx documents, and vice versa.
 */
public final class JxConverter {
  @SuppressWarnings("unchecked")
  private static final ThreadLocal<WeakReference<SAXParser>>[] weakParsers = new ThreadLocal[2];
  private static final Pattern pattern = Pattern.compile("(?<value>null|false|true|-?(([0-9])|([1-9][0-9]+))(\\.[.0-9]+)?([eE][+-]?(([0-9])|([1-9][0-9]+)))?|\"(\\\\.|[^\"])*\")(?<ws>\\s+|$)");

  private static SAXParser getParser(final boolean validating) throws SAXException {
    ThreadLocal<WeakReference<SAXParser>> threadLocal = weakParsers[validating ? 0 : 1];
    final WeakReference<SAXParser> reference = threadLocal == null ? null : threadLocal.get();
    SAXParser parser = reference == null ? null : reference.get();
    if (parser == null) {
      if (threadLocal == null)
        threadLocal = weakParsers[validating ? 0 : 1] = new ThreadLocal<>();

      parser = SAXParsers.newParser(validating);
      threadLocal.set(new WeakReference<>(parser));
    }

    parser.reset();
    return parser;
  }

  private static void appendValue(final JsonReader reader, final int off, final int len, final StringBuilder b) throws IOException {
    final char c0 = reader.bufToChar(off);
    if (c0 == '{')
      appendObject(reader, false, b);
    else if (c0 == '[')
      appendArray(reader, b);
    else
      CharacterDatas.escapeForElem(b, reader.buf(), off, len);
  }

  private static void appendArray(final JsonReader reader, final StringBuilder b) throws IOException {
    b.append("<a>");
    char prev = '\0';
    for (long token = -1; (token == -1 ? token = reader.readToken() : token) != -1;) { // [N]
      int off = Composite.decodeInt(token, 0);
      int len = Composite.decodeInt(token, 1);
      char c0 = reader.bufToChar(off);
      if (Character.isWhitespace(c0)) {
        reader.bufToString(b, off, len);
        token = -1;
      }
      else if (c0 == ']') {
        break;
      }
      else if (c0 == '{') {
        appendObject(reader, false, b);
        prev = c0;
        token = -1;
      }
      else if (c0 == '[') {
        appendArray(reader, b);
        prev = c0;
        token = -1;
      }
      else if (c0 == ',') {
        token = reader.readToken();
        off = Composite.decodeInt(token, 0);
        len = Composite.decodeInt(token, 1);
        c0 = reader.bufToChar(off);
        boolean ws = Character.isWhitespace(c0);
        if (ws)
          token = reader.readToken();

        c0 = reader.bufToChar(off);
        if (prev != '{' && prev != '[' && c0 != '{' && c0 != '[')
          b.append(' ');

        if (ws)
          reader.bufToString(b, off, len);
      }
      else {
        appendValue(reader, off, len, b);
//        CharacterDatas.escapeForElem(b, reader.buf(), off, len);
        prev = '\0';
        token = -1;
      }
    }

    b.append("</a>");
  }

  private static void appendObject(final JsonReader reader, final boolean declareNamespace, final StringBuilder b) throws IOException {
    b.append("<o");
    if (declareNamespace) {
      b.append(" xmlns=\"http://www.jsonx.org/jsonxml-0.5.xsd\"");
      b.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
      b.append(" xsi:schemaLocation=\"http://www.jsonx.org/jsonx-0.5.xsd http://www.jsonx.org/jsonx-0.5.xsd\"");
    }

    b.append('>');
    Boolean nextName = true;
    for (long token = -1; (token == -1 ? token = reader.readToken() : token) != -1;) { // [N]
      int off = Composite.decodeInt(token, 0);
      int len = Composite.decodeInt(token, 1);
      char c0 = reader.bufToChar(off);
      if (Character.isWhitespace(c0)) {
        reader.bufToString(b, off, len);
        token = -1;
      }
      else if (c0 == '}') {
        break;
      }
      else if (nextName != null && nextName) {
        if (c0 == ':') {
          nextName = false;
          token = -1;
        }
        else {
          b.append("<p n=\"");
          CharacterDatas.escapeForAttr(b, reader.buf(), '"', off + 1, len - 2);
          b.append('"');
          token = reader.readToken();
          off = Composite.decodeInt(token, 0);
          len = Composite.decodeInt(token, 1);
          c0 = reader.bufToChar(off);
          if (Character.isWhitespace(c0)) {
            reader.bufToString(b, off, len);
            token = -1;
          }

          b.append('>');
        }
      }
      else if (nextName != null && !nextName) {
        appendValue(reader, off, len, b);
        token = reader.readToken();
        off = Composite.decodeInt(token, 0);
        len = Composite.decodeInt(token, 1);
        c0 = reader.bufToChar(off);
        if (Character.isWhitespace(c0)) {
          reader.bufToString(b, off, len);
          token = -1;
        }

        b.append("</p>");
        nextName = null;
      }
      else if (c0 == ',') {
        nextName = true;
        token = -1;
      }
      else if (c0 == '{') {
        appendObject(reader, false, b);
        token = -1;
      }
      else if (c0 == '[') {
        appendArray(reader, b);
        token = -1;
      }
      else {
        throw new IllegalStateException("Unexpected token: " + reader.bufToString(off, len));
      }
    }

    b.append("</o>");
  }

  /**
   * Converts a JSONx document from the specified {@link InputStream} to a JSON document.
   *
   * @implSpec This method is thread safe.
   * @param in The {@link InputStream} for the JSONx document to be converted.
   * @param validate If {@code true}, the JSONx document will be validated during the conversion process.
   * @return A JSON document equivalent of the JSONx document.
   * @throws IOException If an I/O error has occurred.
   * @throws SAXException If a SAX error has occurred.
   * @throws IllegalArgumentException If {@code in} is null.
   */
  public static String jsonxToJson(final InputStream in, final boolean validate) throws IOException, SAXException {
    final SAXParser parser = getParser(validate);
    final StringBuilder b = new StringBuilder();
    parser.parse(in, new DefaultHandler() {
      private final ArrayDeque<String> stack = new ArrayDeque<>();
      private StringBuilder characters;
      private StringBuilder prevWs;
      private String prevElem;

      @Override
      public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        final boolean hasMembers = processCharacters(true);
        stack.push(localName);
        if (!hasMembers && "m".equals(prevElem) || "p".equals(prevElem)) {
          // FIXME: Not sure why this check is necessary here. If we remove this check, we get
          // FIXME: situations with ...[,true] or ...{,"x":null}. Need to remove this
          // FIXME: check and debug to figure out what's going on.
          final char ch = b.charAt(b.length() - 1);
          if (ch != '[' && ch != '{')
            b.append(',');

          prevElem = null;
        }

        if (prevWs != null) {
          b.append(prevWs);
          prevWs = null;
        }

        if ("o".equals(localName)) {
          b.append('{');
        }
        else if ("a".equals(localName)) {
          b.append('[');
        }
        else if ("p".equals(localName)) {
          final int index = attributes.getIndex("n");
          if (index == -1)
            throw new SAXException("Missing attribute: \"n\"");

          b.append('"');
          b.append(attributes.getValue(index)); // The SAXParser automatically unescapes the text
          // CharacterDatas.unescapeFromAttr(b, attributes.getValue(index), '"');
          b.append("\":");
        }
        else {
          throw new SAXException("Unexpected element: " + qName);
        }
      }

      @Override
      public void endElement(final String uri, final String localName, final String qName) {
        processCharacters(false);
        stack.pop();
        if (prevWs != null) {
          b.append(prevWs);
          prevWs = null;
        }

        prevElem = stack.size() > 0 && "a".equals(stack.peek()) ? "m" : localName;
        if ("o".equals(localName)) {
          b.append('}');
        }
        else if ("a".equals(localName)) {
          b.append(']');
        }
      }

      @Override
      public void characters(final char[] ch, final int start, final int length) {
        final String value = new String(ch, start, length);
        if (characters == null)
          characters = new StringBuilder(value);
        else
          characters.append(value);
      }

      private boolean processCharacters(final boolean hasMore) {
        if (characters == null)
          return false;

        try {
          if (Strings.isWhitespace(characters)) {
            prevWs = characters;
            return false;
          }

          if ("m".equals(prevElem) || "p".equals(prevElem)) {
            // FIXME: Not sure why this check is necessary here. If we remove this check,
            // FIXME: we get situations with ...[,true] or ...{,"x":null}. Need to remove
            // FIXME: this check and debug to figure out what's going on.
            final char ch = b.charAt(b.length() - 1);
            if (ch != '[' && ch != '{')
              b.append(',');

            prevElem = null;
          }

          if (prevWs != null) {
            b.append(prevWs);
            prevWs = null;
          }

          if ("a".equals(stack.peek())) {
            final Matcher matcher = pattern.matcher(characters);
            String prevWs = null;
            int i = -1;
            while (matcher.find()) {
              if (++i > 0) {
                b.append(',');
              }
              else {
                final int len = characters.length();
                for (int j = 0; j < len; ++j) { // [N]
                  final char ch = characters.charAt(j);
                  if (Character.isWhitespace(ch))
                    b.append(ch);
                  else
                    break;
                }
              }

              if (prevWs != null) {
                b.append(prevWs.substring(1));
                prevWs = null;
              }

              prevElem = "m";
              final String value = matcher.group("value");
              if (value != null)
                b.append(value); // The SAXParser automatically unescapes the text
                // CharacterDatas.unescapeFromElem(b, value);

              final String ws = matcher.group("ws");
              if (ws != null && ws.length() > 0)
                prevWs = ws;
            }

            if (i == -1) {
              b.append(characters);
            }
            else if (prevWs != null) {
              if (hasMore) {
                b.append(',').append(prevWs.substring(1));
              }
              else {
                b.append(prevWs);
              }

              return true;
            }
          }
          else {
            b.append(characters);
          }

          return false;
        }
        finally {
          characters = null;
        }
      }
    });

    return b.toString();
  }

  /**
   * Converts a JSON document from the specified {@link JsonReader} to a JSONx
   * document without declaring the XML namespace.
   * <p>
   * This method is equivalent to calling
   *
   * <pre>
   * {@code jsonToJsonx(reader, false)}
   * </pre>
   *
   * @implSpec This method is thread safe.
   * @param reader The {@link JsonReader} for the JSON document to be converted.
   *          declare the {@code xmlns} and {@code xsi:schemaLocation}
   *          attributes in the root element.
   * @return A JSONx document equivalent of the JSON document.
   * @throws IOException If an I/O error has occurred.
   * @throws IllegalArgumentException If {@code reader} is null.
   */
  public static String jsonToJsonx(final JsonReader reader) throws IOException {
    return jsonToJsonx(reader, false);
  }

  /**
   * Converts a JSON document from the specified {@link JsonReader} to a JSONx document.
   *
   * @implSpec This method is thread safe.
   * @param reader The {@link JsonReader} for the JSON document to be converted.
   * @param declareNamespace If {@code true}, the resulting JSONx document will declare the {@code xmlns} and
   *          {@code xsi:schemaLocation} attributes in the root element.
   * @return A JSONx document equivalent of the JSON document.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} is null.
   */
  public static String jsonToJsonx(final JsonReader reader, final boolean declareNamespace) throws IOException {
    final StringBuilder b = new StringBuilder();
    for (long token = -1; (token = reader.readToken()) != -1;) { // [N]
      final int off = Composite.decodeInt(token, 0);
      final int len = Composite.decodeInt(token, 1);
      final char c0 = reader.bufToChar(off);
      if (Character.isWhitespace(c0))
        reader.bufToString(b, off, len);
      else if (c0 == '{')
        appendObject(reader, declareNamespace, b);
      else if (c0 == '[')
        appendArray(reader, b);
      else
        throw new IllegalStateException("Illegal token: " + token);
    }

    return b.toString();
  }

  private JxConverter() {
  }
}