package org.libx4j.jsonx.generator;

import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.ObjectProperty;
import org.w3.www._2001.XMLSchema.yAA.$String;

public class Id {
  private static String hash(final Object ... variables) {
    final Checksum crc = new CRC32();
    crc.update(Arrays.toString(variables).getBytes());
    return Long.toString(crc.getValue(), 16);
  }

  private final String id;

  public Id(final $String id) {
    this.id = id.text();
  }

  public Id(final ArrayModel model) {
    final Object[] variables = new Object[model.members().size()];
    for (int i = 0; i < variables.length; i++) {
      final Element member = model.members().get(i);
      variables[i] = member.id().toString() + member.nullable();
    }

    this.id = "a" + hash(variables);
  }

  public Id(final BooleanModel model) {
    this.id = "b";
  }

  public Id(final NumberModel model) {
    this.id = "n" + hash(model.form(), model.min(), model.max());
  }

  public Id(final StringModel model) {
    this.id = "s" + hash(model.pattern(), model.urlDecode(), model.urlEncode());
  }

  public Id(final ObjectModel model) {
    this.id = model.type().toString();
  }

  public Id(final ObjectProperty property) {
    this.id = property.type().getName();
  }

  public Id(final ObjectElement element) {
    this.id = element.type().getName();
  }

  public Id(final Class<?> type) {
    this.id = type.getName();
  }

  public Id(final String className) {
    this.id = className;
  }

  public Id(final Template model) {
    if (model.reference().id() == null)
      throw new NullPointerException();

    this.id = "t" + hash(model.reference().id(), model.nullable(), model.minOccurs(), model.maxOccurs(), model.required());
  }

  @Override
  public String toString() {
    return id;
  }
}