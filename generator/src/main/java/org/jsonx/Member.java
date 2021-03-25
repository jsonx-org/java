/* Copyright (c) 2017 JSONx
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
import java.lang.reflect.Executable;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.PatternSyntaxException;

import org.jaxsb.runtime.Attribute;
import org.jaxsb.runtime.Bindings;
import org.jsonx.Registry.Wildcard;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Binding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Boolean;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$FieldIdentifier;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$MaxOccurs;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Number;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Object;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Reference;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$String;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$TypeBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.libj.lang.Classes;
import org.libj.lang.Strings;
import org.libj.util.CollectionUtil;
import org.libj.util.Patterns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3.www._2001.XMLSchema.yAA;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;

abstract class Member extends Element {
  private static final Logger logger = LoggerFactory.getLogger(Model.class);

  static <T extends $Binding>T getBinding(final List<T> bindings) {
    if (bindings == null)
      return null;

    for (final T binding : bindings)
      if ("java".equals(binding.getLang$().text()))
        return binding;

    return null;
  }

  static Binding.Type getBinding(final Registry registry, final $TypeBinding binding) {
    return binding == null ? null : Binding.Type.from(registry, binding.getType$(), binding.getDecode$(), binding.getEncode$());
  }

  static Binding.Type getBinding(final Registry registry, final List<? extends $TypeBinding> bindings) {
    final $TypeBinding binding = getBinding(bindings);
    return getBinding(registry, binding);
  }

  static final Function<$AnySimpleType,String> elementXPath = t -> {
    final String name;
    if (t instanceof $Array)
      name = (($Array)t).getName$().text();
    else if (t instanceof $Boolean)
      name = (($Boolean)t).getName$().text();
    else if (t instanceof $Number)
      name = (($Number)t).getName$().text();
    else if (t instanceof $Object)
      name = (($Object)t).getName$().text();
    else if (t instanceof $String)
      name = (($String)t).getName$().text();
    else if (t instanceof $Reference)
      name = (($Reference)t).getName$().text();
    else if (t instanceof Schema.Array)
      name = ((Schema.Array)t).getName$().text();
    else if (t instanceof Schema.Boolean)
      name = ((Schema.Boolean)t).getName$().text();
    else if (t instanceof Schema.Number)
      name = ((Schema.Number)t).getName$().text();
    else if (t instanceof Schema.Object)
      name = ((Schema.Object)t).getName$().text();
    else if (t instanceof Schema.String)
      name = ((Schema.String)t).getName$().text();
    else
      name = null;

    if (name == null)
      return t.name().getLocalPart();

    return t.name().getLocalPart() + "[@" + "name=\"" + Strings.escapeForJava(name) + "\"]";
  };

  static Integer parseMaxCardinality(final BigInteger minCardinality, final $MaxOccurs maxCardinality, final String name, final Integer dflt) {
    final Integer max = "unbounded".equals(maxCardinality.text()) ? Integer.MAX_VALUE : Integer.parseInt(maxCardinality.text());
    if (minCardinality.intValue() > max)
      throw new ValidationException("min" + name + "=\"" + minCardinality + "\" > max" + name + "=\"" + max + "\"\n" + Bindings.getXPath(((Attribute)maxCardinality).owner(), elementXPath) + "[@min" + name + "=" + minCardinality + " and @max" + name + "=" + maxCardinality.text() + "]");

    return max.equals(dflt) ? null : max;
  }

  private static void checkMinMaxOccurs(final String source, final Integer minOccurs, final Integer maxOccurs) {
    if (minOccurs != null && maxOccurs != null && minOccurs > maxOccurs)
      throw new ValidationException(source + ": minOccurs=\"" + minOccurs + "\" > maxOccurs=\"" + maxOccurs + "\"");
  }

  static boolean isMultiRegex(final String str) {
    try {
      return Patterns.unescape(str) == null;
    }
    catch (final PatternSyntaxException e) {
      return false;
    }
  }

  static String fullyQualifiedDisplayName(final Declarer member) {
    final StringBuilder builder = new StringBuilder();
    if (!(member.declarer() instanceof SchemaElement)) {
      builder.append(JsdUtil.flipName(member.declarer().id().toString()));
      if (!member.declarer().displayName().isEmpty())
        builder.append(" (" + member.declarer().displayName() + ")");
    }

    if (builder.length() > 0)
      builder.append('.');

    builder.append(member.displayName());
    return builder.toString();
  }

  final Registry registry;
  final Declarer declarer;
  private final Id id;
  final Spec<Boolean> nullable;
  final Spec<Use> use;
  final Spec<Integer> minOccurs;
  final Spec<Integer> maxOccurs;
  final Binding.Field fieldBinding;
  final Binding.Type typeBinding;

  Member(final Registry registry, final Declarer declarer, final boolean isFromSchema, final Id id, final $Documented.Doc$ doc, final String name, final Boolean nullable, final Use use, final Integer minOccurs, final Integer maxOccurs, final String fieldName, final Binding.Type typeBinding) {
    super(name, doc);
    this.registry = registry;
    this.declarer = declarer;
    this.id = id;
    final Boolean nullableNormalized = nullable == null || nullable ? null : nullable;
    this.nullable = Spec.from(isFromSchema ? nullable : nullableNormalized, nullableNormalized);
    final Use useNormalized = use == Use.REQUIRED ? null : use;
    this.use = Spec.from(isFromSchema ? use : useNormalized, useNormalized);
    this.minOccurs = Spec.from(minOccurs, minOccurs == null || minOccurs == 1 ? null : minOccurs);
    this.maxOccurs = Spec.from(maxOccurs, maxOccurs == null || maxOccurs == Integer.MAX_VALUE ? null : maxOccurs);
    checkMinMaxOccurs(name, minOccurs, maxOccurs);
    this.fieldBinding = Binding.Field.from(name, fieldName);
    this.typeBinding = typeBinding;
  }

  Member(final Registry registry, final Declarer declarer, final boolean isFromSchema, final Id id, final $Documented.Doc$ doc, final yAA.$Boolean nullable, final yAA.$NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs, final Binding.Type typeBinding) {
    this(registry, declarer, isFromSchema, id, doc, null, nullable == null || nullable.isDefault() ? null : nullable.text(), null, minOccurs.text().intValue(), parseMaxCardinality((BigInteger)minOccurs.text(), maxOccurs, "Occurs", Integer.MAX_VALUE), null, typeBinding);
  }

  Member(final Registry registry, final Declarer declarer, final boolean isFromSchema, final Id id, final $Documented.Doc$ doc, final yAA.$AnySimpleType name, final yAA.$Boolean nullable, final yAA.$String use, final $FieldIdentifier fieldName, final Binding.Type typeBinding) {
    this(registry, declarer, isFromSchema, id, doc, (String)name.text(), nullable == null || nullable.isDefault() ? null : nullable.text(), use == null || use.isDefault() ? null : Use.valueOf(use.text().toUpperCase()), null, null, fieldName == null ? null : fieldName.text(), typeBinding);
  }

  final void validateTypeBinding() {
    if (typeBinding == null || typeBinding.type == null)
      return;

    final boolean hasDecodeBinding = typeBinding != null && typeBinding.decode != null;
    if (typeBinding.type.isPrimitive() && !typeBinding.type.isArray() && !hasDecodeBinding) {
      if (use.set == Use.OPTIONAL)
        throw new ValidationException("\"" + fullyQualifiedDisplayName(declarer) + "\" cannot declare \"" + displayName() + "\" (" + elementName() + ") with primitive type \"" + typeBinding.type.getCompoundName() + "\" and use=optional: Either change to an Object type, or declare a \"decode\" binding to handle null values.");

      if (!(declarer instanceof SchemaElement) && nullable.get == null && !hasDecodeBinding)
        throw new ValidationException("\"" + fullyQualifiedDisplayName(declarer) + "\" cannot declare \"" + displayName() + "\" (" + elementName() + ") with primitive type \"" + typeBinding.type.getCompoundName() + "\" and nullable=true: Either change to an Object type, or declare a \"decode\" binding to handle null values.");
    }

    // Check that we have: ? super CharSequence -> decode -> [type] -> encode -> ? extends CharSequence
    final Class<?> cls = Classes.forNameOrNull(typeBinding.type.getNativeName(), false, getClass().getClassLoader());
    Executable decodeMethod = null;
    boolean preventDefault = false;
    if (typeBinding.decode != null) {
      try {
        decodeMethod = JsdUtil.parseExecutable(typeBinding.decode, String.class);
      }
      catch (final ValidationException e) {
        preventDefault = true;
        if (e.getCause() instanceof ClassNotFoundException)
          logger.warn("Unable to validate \"decode\": " + typeBinding.decode + " due to: " + e.getCause().getMessage());
        else
          throw e;
      }
    }

    Executable encodeMethod = null;
    if (cls != null && typeBinding.encode != null) {
      try {
        encodeMethod = JsdUtil.parseExecutable(typeBinding.encode, cls);
      }
      catch (final ValidationException e) {
        preventDefault = true;
        if (e.getCause() instanceof ClassNotFoundException)
          logger.warn("Unable to validate \"encode\": " + typeBinding.encode + " due to: " + e.getCause().getMessage());
        else
          throw e;
      }
    }

    if (preventDefault)
      return;

    String error = null;

    if (cls != null) {
      if (decodeMethod != null) {
        if (!Classes.isAssignableFrom(cls, JsdUtil.getReturnType(decodeMethod))) {
          error = "The return type of \"decode\" method \"" + decodeMethod + "\" in " + JsdUtil.flipName(id().toString()) + " is not assignable to: " + typeBinding.type.getName();
        }
      }
      else if (encodeMethod == null) {
        if (!Classes.isAssignableFrom(defaultClass(), cls) && !Classes.isAssignableFrom(CharSequence.class, cls)) {
          error = "The type binding \"" + typeBinding.type.getName() + "\" in " + JsdUtil.flipName(id().toString()) + " is not \"encode\" compatible with " + defaultClass().getName() + " or " + CharSequence.class.getName();
        }
      }
    }
    else if (encodeMethod != null && !Classes.isAssignableFrom(CharSequence.class, JsdUtil.getReturnType(encodeMethod))) {
      error = "The return type of \"encode\" method \"" + encodeMethod + "\" in " + JsdUtil.flipName(id().toString()) + " is not assignable to:" + CharSequence.class.getName();
    }
    else if (decodeMethod != null && !Classes.isAssignableFrom(defaultClass(), JsdUtil.getReturnType(decodeMethod))) {
      error = "The return type of \"decode\" method \"" + decodeMethod + "\" in " + JsdUtil.flipName(id().toString()) + " is not assignable to: " + defaultClass().getName();
    }

    if (error != null)
      throw new ValidationException(error);

    error = isValid(typeBinding);
    if (error != null)
      throw new ValidationException(error);
  }

  public final Declarer declarer() {
    return declarer;
  }

  public String displayName() {
    if (declarer instanceof SchemaElement)
      return id().toString();

    return name() != null && !name().isEmpty() ? name() : elementName();
  }

  @Override
  Map<String,Object> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toXmlAttributes(owner, packageName);
    if (name() != null)
      attributes.put(nameName(), name());
    else if (owner instanceof SchemaElement && !(this instanceof Referrer))
      attributes.put(nameName(), id.toString());

    if (!(owner instanceof SchemaElement)) {
      if (nullable.get != null)
        attributes.put("nullable", nullable.get);

      if (use.get != null)
        attributes.put("use", use.get.toString().toLowerCase());
    }

    if (minOccurs.get != null)
      attributes.put("minOccurs", String.valueOf(minOccurs.get));

    if (maxOccurs.get != null)
      attributes.put("maxOccurs", String.valueOf(maxOccurs.get));

    return attributes;
  }

  /**
   * Intended to be overridden by each concrete subclass, this method populates
   * the target {@code attributes} parameter with annotation attributes
   * pertaining to this {@link Member}.
   *
   * @param attributes The target {@code attributes} parameter.
   * @param owner The {@link Member} that owns this {@link Member}.
   */
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    if (nullable.get != null)
      attributes.put("nullable", nullable.get);

    if (use.get != null)
      attributes.put("use", Use.class.getName() + "." + use.get);

    if (minOccurs.get != null)
      attributes.put("minOccurs", String.valueOf(minOccurs.get));

    if (maxOccurs.get != null)
      attributes.put("maxOccurs", String.valueOf(maxOccurs.get));

    if (typeBinding != null && typeBinding.decode != null)
      attributes.put("decode", "\"" + typeBinding.decode + "\"");

    if (typeBinding != null && typeBinding.encode != null)
      attributes.put("encode", "\"" + typeBinding.encode + "\"");

    // Only put "type" in root object definitions, array members, and not references
    if ((declarer instanceof SchemaElement || declarer instanceof ArrayModel) && !(owner instanceof Reference) && typeBinding() != null)
      attributes.put("type", typeBinding().toCanonicalString() + ".class");
  }

  private static boolean isArrayOverride(final Member override) {
    return override instanceof ArrayModel || override instanceof Reference && ((Reference)override).model instanceof ArrayModel;
  }

  final String toField(final Member override) {
    final StringBuilder builder = new StringBuilder();
    if (override == null || isArrayOverride(override)) {
      final List<AnnotationType> elementAnnotations = toElementAnnotations();
      if (elementAnnotations != null && elementAnnotations.size() > 0)
        builder.append(CollectionUtil.toString(elementAnnotations, '\n')).append('\n');
    }

    final String classCase = (override != null ? override.fieldBinding : fieldBinding).classCase;
    final String instanceCase = (override != null ? override.fieldBinding : fieldBinding).instanceCase;
    final boolean isRegex = this instanceof AnyModel && isMultiRegex(name());
    final Registry.Type type;
    if (isRegex)
      type = registry.getType(LinkedHashMap.class, registry.getType(String.class).asGeneric(null), type().asGeneric(Wildcard.EXTENDS));
    else
      type = nullable.get == null && use.get == Use.OPTIONAL ? registry.getOptionalType(type().asGeneric(null)) : type();

    final String typeName = type.toCanonicalString();

    final String doc = this.doc != null ? "/** " + this.doc + " **/" : null;
    if (doc != null)
      builder.append(doc).append('\n');

    final AttributeMap attributes = new AttributeMap();
    toAnnotationAttributes(attributes, this);
    if (!attributes.containsKey("name"))
      attributes.put("name", "\"" + Strings.escapeForJava(name()) + "\"");

    final AnnotationType annotationType = new AnnotationType(propertyAnnotation(), attributes);
    if (override == null || isArrayOverride(override))
      builder.append(annotationType).append('\n');

    final String arrayOverrideSafeTypeName = override != null && isArrayOverride(override) ? override.type().toCanonicalString() : typeName;

    builder.append("public ").append(arrayOverrideSafeTypeName).append(" get").append(classCase).append("() {\n  return ");
    if (override == null)
      builder.append(instanceCase);
    else if (isArrayOverride(override))
      builder.append("super.get").append(classCase).append("()");
    else
      builder.append("(").append(arrayOverrideSafeTypeName).append(")super.get").append(classCase).append("()");

    builder.append(";\n}\n\n");

    if (override == null || !isArrayOverride(override)) {
      if (doc != null)
        builder.append(doc).append('\n');
      builder.append("public ").append(declarer.classType().getSimpleName()).append(" set").append(classCase).append("(final ").append(typeName).append(' ').append(instanceCase).append(") {\n  ");
      if (override != null)
        builder.append("super.set").append(classCase).append('(').append(instanceCase).append(")");
      else
        builder.append("this.").append(instanceCase).append(" = ").append(instanceCase);
      builder.append(";\n");
      builder.append("  return this;\n}");
    }

    if (override == null)
      builder.append("\n\nprivate ").append(typeName).append(' ').append(instanceCase).append(";");

    return builder.toString();
  }

  /**
   * Returns a list of {@link AnnotationType} objects representing the
   * annotations of this {@link Member}.
   * <p>
   * Intended to be overridden by each concrete subclass, this method
   *
   * @return A list of {@link AnnotationType} objects representing the
   *         annotations of this {@link Member}.
   */
  List<AnnotationType> toElementAnnotations() {
    return null;
  }

  /**
   * Intended to be overridden by each concrete subclass, this method collects
   * all {@link Registry.Type} declarations of elements that are members of
   * this {@link Member}.
   *
   * @param types The {@link Set} into which the {@link Registry.Type}
   *          declarations must be added.
   */
  void getDeclaredTypes(final Set<? super Registry.Type> types) {
  }

  public final Id id() {
    return id;
  }

  String nameName() {
    return "name";
  }

  Registry.Type typeBinding() {
    return typeBinding == null ? null : typeBinding.type;
  }

  final Registry.Type type() {
    final Registry.Type typeBinding = typeBinding();
    return typeBinding != null ? typeBinding : typeDefault();
  }

  public final boolean isAssignableFrom(final Member m) {
    final boolean shouldCheckNullable = !(declarer instanceof SchemaElement);
    if (shouldCheckNullable && m.nullable.set != null && !m.nullable.set.equals(nullable.set))
      return false;

    final boolean shouldCheckUse = declarer instanceof ObjectModel;
    if (shouldCheckUse && m.use.set != null && m.use.set != use.set)
      return false;

    final boolean shouldCheckArray = declarer instanceof ArrayModel;
    if (shouldCheckArray) {
      if (m.minOccurs.set != null && m.minOccurs.set != minOccurs.set)
        return false;

      if (m.maxOccurs.get != null && m.maxOccurs.get != maxOccurs.get)
        return false;
    }

    if (m.fieldBinding != null && !m.fieldBinding.isAssignableFrom(fieldBinding))
      return false;

    // FIXME: Proper OO here please!
    // FIXME: For now, only allow object references to be overridden
    if (this instanceof Reference && (((Reference)this).model instanceof ArrayModel))
      return ((Reference)this).model.isAssignableFrom(m);

    if (this instanceof ArrayModel) {
      if (m instanceof Reference)
        return isAssignableFrom(((Reference)m).model);

      if (!(m instanceof ArrayModel))
        return false;

      final ArrayModel a = (ArrayModel)this;
      final ArrayModel b = (ArrayModel)m;
      if (a.members.size() != b.members.size())
        return false;

      for (int i = 0; i < a.members.size(); ++i)
        if (!a.members.get(i).isAssignableFrom(b.members.get(i)))
          return false;

      return true;
    }

    return type().isAssignableFrom(m.type());
  }

  abstract Registry.Type typeDefault();
  abstract String isValid(Binding.Type typeBinding);
  abstract String elementName();
  abstract Class<?> defaultClass();
  abstract Class<? extends Annotation> propertyAnnotation();
  abstract Class<? extends Annotation> elementAnnotation();
  abstract Class<? extends Annotation> typeAnnotation();
}