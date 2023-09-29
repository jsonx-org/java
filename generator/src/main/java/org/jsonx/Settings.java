/* Copyright (c) 2018 JSONx
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

import static org.libj.lang.Assertions.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

import org.libj.lang.Identifiers;

public class Settings {
  public static final Settings DEFAULT = new Settings(new NamespaceToPackage(), 1, true);

  private static class NamespaceToPackage {
    private static String validatePackage(final String pkg) {
      if (pkg == null || pkg.length() == 0)
        return pkg;

      final char lastChar = pkg.length() == 0 ? '\0' : pkg.charAt(pkg.length() - 1);
      if (!Identifiers.isValid(lastChar == '$' || lastChar == '.' ? pkg.substring(0, pkg.length() - 1) : pkg))
        throw new IllegalArgumentException("Illegal base path parameter: " + pkg);

      return pkg;
    }

    private HashMap<String,String> namespaceToPackage;
    private Function<String,String> namespaceToPackageFunction;
    private String defaultPackage;

    private void set(final String namespace, final String pkg) {
      if (namespaceToPackage == null)
        namespaceToPackage = new HashMap<>();

      final String value = namespaceToPackage.put(assertNotNull(namespace), validatePackage(assertNotNull(pkg)));
      if (value != null)
        throw new IllegalArgumentException("Key \"" + namespace + "\" maps to multiple values: {\"" + value + "\", \"" + pkg + "\"}");
    }

    private void set(final String defaultPackage) {
      this.defaultPackage = validatePackage(assertNotNull(defaultPackage));
    }

    private void set(final Function<String,String> namespaceToPackage) {
      this.namespaceToPackageFunction = assertNotNull(namespaceToPackage);
    }

    private String get(final String namespace) {
      Objects.requireNonNull(namespace);

      String pkg = null;
      if (namespaceToPackage != null)
        pkg = namespaceToPackage.get(namespace);

      if (pkg != null)
        return pkg;

      if (namespaceToPackageFunction != null)
        pkg = validatePackage(namespaceToPackageFunction.apply(namespace));

      if (pkg != null)
        return pkg;

      if (namespace.length() == 0)
        return defaultPackage != null ? defaultPackage : "";

      throw new IllegalStateException("Missing package binding for namespace \"" + namespace + "\"");
    }
  }

  public static class Builder {
    private NamespaceToPackage namespaceToPackage;

    private NamespaceToPackage getNamespaceToPackage() {
      return namespaceToPackage == null ? namespaceToPackage = new NamespaceToPackage() : namespaceToPackage;
    }

    /**
     * Sets the class name base path to be prepended to the names of generated bindings for the provided namespace, and returns
     * {@code this} builder.
     *
     * @param namespace The namespace for which the base path is to be applied.
     * @param pkg The class name base path to be prepended to the names of generated bindings.
     * @return {@code this} builder.
     * @throws IllegalArgumentException If {@code namespace} or {@code pkg} is null.
     * @throws IllegalArgumentException If {@code pkg} maps to multiple values.
     * @throws IllegalArgumentException If {@code pkg} is not a valid Java identifier.
     */
    public Builder withNamespacePackage(final String namespace, final String pkg) {
      getNamespaceToPackage().set(namespace, pkg);
      return this;
    }

    /**
     * Sets the default class name base path to be prepended to the names of generated bindings for base paths not specified via
     * {@link #withNamespacePackage(String,String)}, and returns {@code this} builder.
     *
     * @param defaultPackage The class name base path to be prepended to the names of generated bindings for base paths not
     *          specified via {@link #withNamespacePackage(String,String)}.
     * @return {@code this} builder.
     * @throws IllegalArgumentException If {@code defaultPackage} is null.
     * @throws IllegalArgumentException If {@code defaultPackage} is not a valid Java identifier.
     */
    public Builder withDefaultPackage(final String defaultPackage) {
      getNamespaceToPackage().set(defaultPackage);
      return this;
    }

    /**
     * Sets the {@link Function} to dereference a provided namespace to a class name base path to be prepended to the names of
     * generated bindings, and returns {@code this} builder.
     *
     * @param namespaceToPackage The {@link Function} to dereference a provided namespace to a class name base path to be prepended
     *          to the names of generated bindings.
     * @return {@code this} builder.
     * @throws IllegalArgumentException If {@code namespaceToPackage} is null.
     */
    public Builder withNamespacePackage(final Function<String,String> namespaceToPackage) {
      getNamespaceToPackage().set(namespaceToPackage);
      return this;
    }

    private int templateThreshold = 1;

    public Builder withTemplateThreshold(final int templateThreshold) {
      this.templateThreshold = templateThreshold;
      return this;
    }

    private boolean setBuilder = true;

    public Builder withSetBuilder(final boolean setBuilder) {
      this.setBuilder = setBuilder;
      return this;
    }

    public Settings build() {
      return new Settings(namespaceToPackage, templateThreshold, setBuilder);
    }
  }

  private final NamespaceToPackage namespaceToPackage;
  private final int templateThreshold;
  private final boolean setBuilder;

  Settings(final NamespaceToPackage namespaceToPackage, final int templateThreshold, final boolean setBuilder) {
    this.namespaceToPackage = namespaceToPackage;
    this.templateThreshold = assertNotNegative(templateThreshold, "templateThreshold (" + templateThreshold + ") must be non-negative");
    this.setBuilder = setBuilder;
  }

  public String getPackage(final String namespace) {
    return namespaceToPackage != null ? namespaceToPackage.get(namespace) : "";
  }

  /**
   * Returns the non-negative number of referrers needed for a {@link Model} to be declared as a template root member of the jsonx
   * element.
   *
   * @return The non-negative number of referrers needed for a {@link Model} to be declared as a template root member of the jsonx
   *         element.
   */
  public int getTemplateThreshold() {
    return templateThreshold;
  }

  public boolean getSetBuilder() {
    return setBuilder;
  }
}