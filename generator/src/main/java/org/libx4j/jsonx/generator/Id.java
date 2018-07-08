package org.libx4j.jsonx.generator;

import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Id {
  private static String hash(final Object ... variables) {
    final Checksum crc = new CRC32();
    crc.update(Arrays.toString(variables).getBytes());
    return Long.toString(crc.getValue(), 16);
  }

  private final String id;

  public Id(final String id) {
    this.id = id;
  }

  public Id(final ArrayModel model) {
    final Object[] variables = new Object[model.members().size() + 1];
    for (int i = 0; i < variables.length - 1; i++)
      variables[i] = model.members().get(i).id().toString();

    variables[variables.length - 1] = model.nullable();
    this.id = "a" + hash(variables);
  }

  public Id(final BooleanModel model) {
    this.id = "b" + hash(model.nullable());
  }

  public Id(final NumberModel model) {
    this.id = "n" + hash(model.form(), model.min(), model.max(), model.nullable());
  }

  public Id(final StringModel model) {
    this.id = "s" + hash(model.pattern(), model.urlDecode(), model.urlEncode(), model.nullable());
  }

  public Id(final Template model) {
    if (model.reference().id() == null)
      throw new NullPointerException();

    this.id = "t" + hash(model.reference().id());
  }

  @Override
  public String toString() {
    return id;
  }
}