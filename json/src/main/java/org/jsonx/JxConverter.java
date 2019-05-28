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
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;

import org.libj.util.Strings;
import org.openjax.json.JsonReader;
import org.openjax.xml.api.CharacterDatas;
import org.openjax.xml.sax.Parsers;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Utility class that provides functions to convert JSON document to JSONx
 * documents, and vice versa.
 */
public final class JxConverter {
  @SuppressWarnings("unchecked")
  private static final ThreadLocal<WeakReference<SAXParser>>[] weakParsers = new ThreadLocal[2];
  private static final Pattern pattern = Pattern.compile("(?<value>null|false|true|-?(([0-9])|([1-9][0-9]+))(\\.[\\.0-9]+)?([eE][+-]?(([0-9])|([1-9][0-9]+)))?|\"(\\\\.|[^\"])*\")(?<ws>\\s+|$)");

  private static SAXParser getParser(final boolean validating) throws SAXException {
    final ThreadLocal<WeakReference<SAXParser>> threadLocal = weakParsers[validating ? 0 : 1];
    final WeakReference<SAXParser> reference = threadLocal == null ? null : threadLocal.get();
    SAXParser parser = reference == null ? null : reference.get();
    if (parser == null) {
      parser = Parsers.newParser(validating);
      final WeakReference<SAXParser> newReference = new WeakReference<>(parser);
      if (threadLocal != null) {
        threadLocal.set(newReference);
      }
      else {
        weakParsers[validating ? 0 : 1] = new ThreadLocal<WeakReference<SAXParser>>() {
          @Override
          protected WeakReference<SAXParser> initialValue() {
            return newReference;
          }
        };
      }
    }

    parser.reset();
    return parser;
  }

  /**
   * Converts a JSONx document from the specified {@code InputStream} to a JSON
   * document.
   *
   * @param in The {@code InputStream} for the JSONx document to be converted.
   * @param validate If {@code true}, the JSONx document will be validated
   *          during the conversion process.
   * @return A JSON document equivalent of the JSONx document.
   * @throws IOException If an I/O error has occurred.
   * @throws SAXException If a SAX error has occurred.
   * @throws NullPointerException If {@code in} is null.
   */
  public static String jsonxToJson(final InputStream in, final boolean validate) throws IOException, SAXException {
    final SAXParser parser = getParser(validate);
    final StringBuilder builder = new StringBuilder();
    parser.parse(Objects.requireNonNull(in), new DefaultHandler() {
      private final ArrayDeque<String> stack = new ArrayDeque<>();
      private StringBuilder characters = null;
      private StringBuilder prevWs = null;
      private String prevElem = null;

      @Override
      public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        final boolean hasMembers = processCharacters(true);
        stack.push(localName);
        if (!hasMembers && "m".equals(prevElem) || "p".equals(prevElem)) {
          builder.append(',');
          prevElem = null;
        }

        if (prevWs != null) {
          builder.append(prevWs);
          prevWs = null;
        }

        if ("o".equals(localName)) {
          builder.append('{');
        }
        else if ("a".equals(localName)) {
          builder.append('[');
        }
        else if ("p".equals(localName)) {
          final int index = attributes.getIndex("name");
          if (index == -1)
            throw new SAXException("Missing attribute: \"name\"");

          builder.append('"').append(CharacterDatas.unescapeFromAttr(attributes.getValue(index), '"')).append("\":");
        }
        else {
          throw new SAXException("Unexpected element: " + qName);
        }
      }

      @Override
      public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        processCharacters(false);
        stack.pop();
        if (prevWs != null) {
          builder.append(prevWs);
          prevWs = null;
        }

        prevElem = !stack.isEmpty() && "a".equals(stack.peek()) ? "m" : localName;
        if ("o".equals(localName)) {
          builder.append('}');
        }
        else if ("a".equals(localName)) {
          builder.append(']');
        }
      }

      @Override
      public void characters(final char[] ch, final int start, final int length) throws SAXException {
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
            builder.append(',');
            prevElem = null;
          }

          if (prevWs != null) {
            builder.append(prevWs);
            prevWs = null;
          }

          if ("a".equals(stack.peek())) {
            final Matcher matcher = pattern.matcher(characters);
            String prevWs = null;
            int i = -1;
            while (matcher.find()) {
              if (++i > 0) {
                builder.append(',');
              }
              else {
                final int len = characters.length();
                for (int j = 0; j < len; ++j) {
                  final char ch = characters.charAt(j);
                  if (Character.isWhitespace(ch))
                    builder.append(ch);
                  else
                    break;
                }
              }

              if (prevWs != null) {
                builder.append(prevWs.substring(1));
                prevWs = null;
              }

              prevElem = "m";
              final String value = matcher.group("value");
              if (value != null)
                builder.append(CharacterDatas.unescapeFromElem(value));

              final String ws = matcher.group("ws");
              if (ws != null)
                prevWs = ws;
            }

            if (i == -1) {
              builder.append(characters);
            }
            else if (prevWs != null) {
              if (hasMore) {
                builder.append(',').append(prevWs.substring(1));
              }
              else {
                builder.append(prevWs);
              }

              return true;
            }
          }
          else {
            builder.append(characters);
          }

          return false;
        }
        finally {
          characters = null;
        }
      }
    });

    return builder.toString();
  }

  /**
   * Converts a JSON document from the specified {@code JsonReader} to a JSONx
   * document without declaring the XML namespace.
   * <p>
   * This method is equivalent to calling
   * <p>
   * <blockquote>
   * {@code jsonToJsonx(reader, false)}
   * </blockquote>
   *
   * @param reader The {@code JsonReader} for the JSON document to be converted.
   *          declare the {@code xmlns} and {@code xsi:schemaLocation}
   *          attributes in the root element.
   * @return A JSONx document equivalent of the JSON document.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} is null.
   */
  public static String jsonToJsonx(final JsonReader reader) throws IOException {
    return jsonToJsonx(reader, false);
  }

  /**
   * Converts a JSON document from the specified {@code JsonReader} to a JSONx
   * document.
   *
   * @param reader The {@code JsonReader} for the JSON document to be converted.
   * @param declareNamespace If {@code true}, the resulting JSONx document will
   *          declare the {@code xmlns} and {@code xsi:schemaLocation}
   *          attributes in the root element.
   * @return A JSONx document equivalent of the JSON document.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} is null.
   */
  public static String jsonToJsonx(final JsonReader reader, final boolean declareNamespace) throws IOException {
    final StringBuilder builder = new StringBuilder();
    for (String token; (token = reader.readToken()) != null;) {
      if (Character.isWhitespace(token.charAt(0)))
        builder.append(token);
      else if ("{".equals(token))
        appendObject(reader, declareNamespace, builder);
      else if ("[".equals(token))
        appendArray(reader, builder);
      else
        throw new IllegalStateException("Illegal token: " + token);
    }

    return builder.toString();
  }

  private static void appendValue(final JsonReader reader, final String token, final StringBuilder builder) throws IOException {
    final char c0 = token.charAt(0);
    if (c0 == '{')
      appendObject(reader, false, builder);
    else if (c0 == '[')
      appendArray(reader, builder);
    else
      builder.append(CharacterDatas.escapeForElem(token));
  }

  private static void appendArray(final JsonReader reader, final StringBuilder builder) throws IOException {
    builder.append("<a>");
    for (String token = null, prev = null; (token == null ? token = reader.readToken() : token) != null;) {
      if (Character.isWhitespace(token.charAt(0))) {
        builder.append(token);
        token = null;
      }
      else if ("]".equals(token)) {
        break;
      }
      else if ("{".equals(token)) {
        appendObject(reader, false, builder);
        prev = token;
        token = null;
      }
      else if ("[".equals(token)) {
        appendArray(reader, builder);
        prev = token;
        token = null;
      }
      else if (",".equals(token)) {
        token = reader.readToken();
        String ws = null;
        if (Character.isWhitespace(token.charAt(0))) {
          ws = token;
          token = reader.readToken();
        }

        final char c0 = token.charAt(0);
        if (!"{".equals(prev) && !"[".equals(prev) || c0 != '{' && c0 != '[')
          builder.append(' ');

        if (ws != null)
          builder.append(ws);
      }
      else {
        appendValue(reader, token, builder);
        prev = token;
        token = null;
      }
    }

    builder.append("</a>");
  }

  private static void appendObject(final JsonReader reader, final boolean declareNamespace, final StringBuilder builder) throws IOException {
    builder.append("<o");
    if (declareNamespace) {
      builder.append(" xmlns=\"http://www.jsonx.org/jsonx-0.2.2.xsd\"");
      builder.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
      builder.append(" xsi:schemaLocation=\"http://www.jsonx.org/jsonx-0.2.2.xsd http://www.jsonx.org/jsonx-0.2.2.xsd\"");
    }

    builder.append('>');
    Boolean nextName = true;
    for (String token = null; (token == null ? token = reader.readToken() : token) != null;) {
      final char ch = token.charAt(0);
      if (Character.isWhitespace(ch)) {
        builder.append(token);
        token = null;
      }
      else if ("}".equals(token)) {
        break;
      }
      else if (nextName != null && nextName) {
        if (":".equals(token)) {
          nextName = false;
          token = null;
        }
        else {
          builder.append("<p name=").append(CharacterDatas.escapeForAttr(token, '"', 1, token.length() - 1));
          token = reader.readToken();
          if (Character.isWhitespace(token.charAt(0))) {
            builder.append(token);
            token = null;
          }

          builder.append('>');
        }
      }
      else if (nextName != null && !nextName) {
        appendValue(reader, token, builder);
        token = reader.readToken();
        if (Character.isWhitespace(token.charAt(0))) {
          builder.append(token);
          token = null;
        }

        builder.append("</p>");
        nextName = null;
      }
      else if (",".equals(token)) {
        nextName = true;
        token = null;
      }
      else if ("{".equals(token)) {
        appendObject(reader, false, builder);
        token = null;
      }
      else if ("[".equals(token)) {
        appendArray(reader, builder);
        token = null;
      }
      else {
        throw new IllegalStateException("Unexpected token: " + token);
      }
    }

    builder.append("</o>");
  }

  private JxConverter() {
  }
}