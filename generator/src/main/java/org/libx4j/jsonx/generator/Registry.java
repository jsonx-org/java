/* Copyright (c) 2017 lib4j
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

package org.libx4j.jsonx.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;

class Registry {
  class Value {
    private final String name;

    public Value(final String name) {
      if (refToModel.containsKey(name))
        throw new IllegalArgumentException("Value name=\"" + name + "\" already registered");

      refToModel.put(name, null);
      this.name = name;
    }

    public <T extends Model>T value(final T model, final ComplexModel referrer) {
      refToModel.put(name, model);
      return reference(model, referrer);
    }
  }

  private static String getKey(final Element element) {
    final String key = element.id().toString();
    if (key == null)
      throw new NullPointerException("key == null");

    return key;
  }

  private final LinkedHashMap<String,Model> refToModel;
  private final LinkedHashMap<String,List<ComplexModel>> references;

  private Registry(final LinkedHashMap<String,Model> refToModel, final LinkedHashMap<String,List<ComplexModel>> references) {
    this.refToModel = refToModel;
    this.references = references;
  }

  public Registry() {
    this(new LinkedHashMap<String,Model>(), new LinkedHashMap<String,List<ComplexModel>>());
  }

  public Value declare(final Jsonx.Boolean binding) {
    return new Value(binding.getTemplate$().text());
  }

  public Value declare(final Jsonx.Number binding) {
    return new Value(binding.getTemplate$().text());
  }

  public Value declare(final Jsonx.String binding) {
    return new Value(binding.getTemplate$().text());
  }

  public Value declare(final Jsonx.Array binding) {
    return new Value(binding.getTemplate$().text());
  }

  public Value declare(final Jsonx.Object binding) {
    return new Value(binding.getClass$().text());
  }

  public Value declare(final Class<?> clazz) {
    return new Value(clazz.getName());
  }

  public Value declare(final Id id) {
    return new Value(id.toString());
  }

  public Value declare(final $Object binding) {
    return new Value(ObjectModel.getFullyQualifiedName(binding));
  }

  public <T extends Element>T reference(final T model, final ComplexModel referrer) {
    if (referrer == null)
      return model;

    final String key = getKey(model);
    List<ComplexModel> referrers = references.get(key);
    if (referrers == null)
      references.put(key, referrers = new ArrayList<ComplexModel>());

    referrers.add(referrer);
    return model;
  }

  public Element getElement(final $Object binding) {
    return refToModel.get(ObjectModel.getFullyQualifiedName(binding));
  }

  public Element getElement(final Id id) {
    return refToModel.get(id.toString());
  }

  public boolean isRegistered(final Id id) {
    return getElement(id) != null;
  }

  public Collection<Model> rootElements() {
    return refToModel.values();
  }

  public int size() {
    return refToModel.size();
  }

  public int getNumReferrers(final Element element) {
    final List<ComplexModel> referrers = references.get(element.id().toString());
    return referrers == null ? 0 : referrers.size();
  }
}