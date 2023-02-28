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
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;

import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Any;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$AnyMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ArrayMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Binding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$FieldBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Reference;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Reference.Name$;
import org.libj.lang.Classes;
import org.libj.lang.IllegalAnnotationException;
import org.libj.lang.Strings;
import org.openjax.json.JsonUtil;
import org.w3.www._2001.XMLSchema.yAA.$IDREFS;

final class AnyModel extends Referrer<AnyModel> {
  private static $Any property(final schema.AnyProperty jsd, final String name) {
    final $Any xsb = new $Any() {
      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setNames$(new $Any.Names$(JsonUtil.unescape(name).toString()));

    final String types = jsd.getTypes();
    if (types != null)
      xsb.setTypes$(new $Any.Types$(Strings.split(types, ' ')));

    final Boolean nullable = jsd.getNullable();
    if (nullable != null)
      xsb.setNullable$(new $Any.Nullable$(nullable));

    final String use = jsd.getUse();
    if (use != null)
      xsb.setUse$(new $Any.Use$($Any.Use$.Enum.valueOf(use)));

    final List<schema.FieldBinding> bindings = jsd.getBindings();
    final int i$;
    if (bindings != null && (i$ = bindings.size()) > 0) {
      if (bindings instanceof RandomAccess) {
        int i = 0; do // [RA]
          addBinding(xsb, bindings.get(i));
        while (++i < i$);
      }
      else {
        final Iterator<schema.FieldBinding> i = bindings.iterator(); do // [I]
          addBinding(xsb, i.next());
        while (i.hasNext());
      }
    }

    return xsb;
  }

  private static void addBinding(final $Any xsb, final schema.FieldBinding binding) {
    final $Any.Binding bin = new $Any.Binding();
    bin.setLang$(new $Binding.Lang$(binding.getLang()));
    bin.setField$(new $Any.Binding.Field$(binding.getField()));
    xsb.addBinding(bin);
  }

  private static $ArrayMember.Any element(final schema.AnyElement jsd) {
    final $ArrayMember.Any xsb = new $ArrayMember.Any();

    final String types = jsd.getTypes();
    if (types != null)
      xsb.setTypes$(new $ArrayMember.Any.Types$(Strings.split(types, ' ')));

    final Boolean nullable = jsd.getNullable();
    if (nullable != null)
      xsb.setNullable$(new $ArrayMember.Any.Nullable$(nullable));

    final String minOccurs = jsd.getMinOccurs();
    if (minOccurs != null)
      xsb.setMinOccurs$(new $ArrayMember.Any.MinOccurs$(new BigInteger(minOccurs)));

    final String maxOccurs = jsd.getMaxOccurs();
    if (maxOccurs != null)
      xsb.setMaxOccurs$(new $ArrayMember.Any.MaxOccurs$(maxOccurs));

    // There is no element binding for "any"

    return xsb;
  }

  static $AnyMember jsdToXsb(final schema.Any jsd, final String name) {
    final $AnyMember xsb;
    if (jsd instanceof schema.AnyProperty)
      xsb = property((schema.AnyProperty)jsd, name);
    else if (jsd instanceof schema.AnyElement)
      xsb = element((schema.AnyElement)jsd);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    final String doc = jsd.getDoc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    return xsb;
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final AnyProperty property, final Method getMethod, final String fieldName) {
    final AnyModel model = new AnyModel(registry, referrer, property, getMethod, fieldName);
    final Id id = model.id();

    final AnyModel registered = (AnyModel)registry.getModel(id);
    return new Reference(registry, referrer, property.name(), property.nullable(), property.use(), fieldName, model.typeBinding, registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final AnyElement element) {
    final AnyModel model = new AnyModel(registry, referrer, element);
    final Id id = model.id();

    final AnyModel registered = (AnyModel)registry.getModel(id);
    return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static AnyModel reference(final Registry registry, final Referrer<?> referrer, final $Array.Any xsb) {
    return registry.reference(new AnyModel(registry, referrer, xsb), referrer);
  }

  static AnyModel reference(final Registry registry, final Referrer<?> referrer, final $Any xsb) {
    return registry.reference(new AnyModel(registry, referrer, xsb, getBinding(xsb.getBinding())), referrer);
  }

  private static final Name$ anonymousReferenceName = new Name$("");

  private static $Reference newRnonymousReference(final Boolean nullable, Use use) {
    return new $Reference() {
      @Override
      protected $Member inherits() {
        return null;
      }

      @Override
      public Name$ getName$() {
        return anonymousReferenceName;
      }

      @Override
      public Nullable$ getNullable$() {
        return nullable == null ? null : new Nullable$(nullable);
      }

      @Override
      public Use$ getUse$() {
        return use == null ? null : new Use$(use == Use.REQUIRED ? Use$.required : Use$.optional);
      }
    };
  }

  private final ArrayList<Member> types;

  private AnyModel(final Registry registry, final Declarer declarer, final $Any xsb, final $FieldBinding binding) {
    super(registry, declarer, xsb.getDoc$(), xsb.getNames$(), xsb.getNullable$(), xsb.getUse$(), null, binding == null ? null : binding.getField$(), null);
    this.types = getTypes(nullable.get, use.get, xsb.getTypes$());
    validateTypeBinding();
  }

  private AnyModel(final Registry registry, final Declarer declarer, final $Array.Any xsb) {
    super(registry, declarer, xsb.getDoc$(), xsb.getNullable$(), xsb.getMinOccurs$(), xsb.getMaxOccurs$(), null);
    this.types = getTypes(nullable.get, use.get, xsb.getTypes$());
    validateTypeBinding();
  }

  private AnyModel(final Registry registry, final Declarer declarer, final AnyProperty property, final Method getMethod, final String fieldName) {
    super(registry, declarer, property.nullable(), property.use(), null, null);
    final t[] types = property.types();
    this.types = getMemberTypes(types);
    final Class<?> requiredFieldType = types.length == 0 ? defaultClass() : getFieldType(types);
    final boolean isRegex = isMultiRegex(property.name());
    if (isRegex && !Map.class.isAssignableFrom(getMethod.getReturnType()))
      throw new IllegalAnnotationException(property, getMethod.getDeclaringClass().getName() + "." + fieldName + ": @" + AnyProperty.class.getSimpleName() + " of type " + Binding.Type.getClassName(getMethod, property.nullable(), property.use()) + " with regex name=\"" + property.name() + "\" must be of type that extends " + Map.class.getName());

    if (!isAssignable(getMethod, true, requiredFieldType, isRegex, property.nullable(), property.use()))
      throw new IllegalAnnotationException(property, getMethod.getDeclaringClass().getName() + "." + fieldName + ": @" + AnyProperty.class.getSimpleName() + " of type " + Binding.Type.getClassName(getMethod, property.nullable(), property.use()) + " is not assignable for the specified types attribute");

    validateTypeBinding();
  }

  private AnyModel(final Registry registry, final Declarer declarer, final AnyElement element) {
    super(registry, declarer, element.nullable(), null, null, null);
    this.types = getMemberTypes(element.types());
    validateTypeBinding();
  }

  // FIXME: This can be converted to recursive algo
  private ArrayList<Member> getTypes(final Boolean nullable, Use use, final $IDREFS refs) {
    final List<String> idrefs;
    final int i$;
    if (refs == null || (i$ = (idrefs = refs.text()).size()) == 0)
      return null;

    final ArrayList<Member> types = new ArrayList<>(i$);
    if (idrefs instanceof RandomAccess) {
      int i = 0; do // [RA]
        addReference(types, idrefs.get(i), nullable, use);
      while (++i < i$);
    }
    else {
      final Iterator<String> i = idrefs.iterator(); do // [I]
        addReference(types, i.next(), nullable, use);
      while (i.hasNext());
    }

    return types;
  }

  private void addReference(final ArrayList<Member> types, final String idref, final Boolean nullable, Use use) {
    final Id id = Id.hashed(idref);
    types.add(Reference.defer(registry, this, newRnonymousReference(nullable, use), () -> {
      final Member model = registry.getModel(id);
      if (model == null)
        throw new IllegalStateException("Type id=\"" + id + "\" in <any> not found");

      return (Model)registry.reference(model, this);
    }));
  }

  private static Class<?> getFieldType(final t[] types) {
    if (types.length == 0)
      return null;

    final ArrayList<Class<?>> members = new ArrayList<>(types.length);
    for (final t type : types) { // [A]
      if (AnyType.isEnabled(type.arrays()))
        members.add(List.class);
      else if (AnyType.isEnabled(type.booleans()))
        members.add(Boolean.class);
      else if (AnyType.isEnabled(type.numbers()))
        members.add(type.numbers().type());
      else if (AnyType.isEnabled(type.objects()))
        members.add(type.objects());
      else if (AnyType.isEnabled(type.strings()))
        members.add(type.strings().type());
    }

    return Classes.getGreatestCommonSuperclass(members.toArray(new Class[members.size()]));
  }

  private ArrayList<Member> getMemberTypes(final t[] types) {
    if (types.length == 0)
      return null;

    final ArrayList<Member> members = new ArrayList<>(types.length);
    for (final t type : types) { // [A]
      Member member = null;
      if (AnyType.isEnabled(type.arrays()))
        member = ArrayModel.referenceOrDeclare(registry, this, type.arrays());

      if (AnyType.isEnabled(type.booleans())) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " specifies 2 types: \"booleans\" and \"" + member.elementName() + "s\"");

        member = BooleanModel.referenceOrDeclare(registry, this, type.booleans());
      }

      if (AnyType.isEnabled(type.numbers())) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " specifies 2 types: \"numbers\" and \"" + member.elementName() + "s\"");

        member = NumberModel.referenceOrDeclare(registry, this, type.numbers());
      }

      if (AnyType.isEnabled(type.objects())) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " specifies 2 types: \"objects\" and \"" + member.elementName() + "s\"");

        member = ObjectModel.referenceOrDeclare(registry, this, new ObjectElement() {
          @Override
          public Class<? extends Annotation> annotationType() {
            return ObjectElement.class;
          }

          @Override
          public Class<? extends JxObject> type() {
            return type.objects();
          }

          @Override
          public boolean nullable() {
            return true;
          }

          @Override
          public int minOccurs() {
            return 1;
          }

          @Override
          public int maxOccurs() {
            return Integer.MAX_VALUE;
          }

          @Override
          public int id() {
            return -1;
          }
        });
      }

      if (AnyType.isEnabled(type.strings())) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " specifies 2 types: \"strings\" and \"" + member.elementName() + "s\"");

        member = StringModel.referenceOrDeclare(registry, this, new StringElement() {
          @Override
          public Class<? extends Annotation> annotationType() {
            return StringElement.class;
          }

          @Override
          public int id() {
            return -1;
          }

          @Override
          public boolean nullable() {
            return true;
          }

          @Override
          public String pattern() {
            return type.strings().pattern();
          }

          @Override
          public int minOccurs() {
            return 1;
          }

          @Override
          public int maxOccurs() {
            return Integer.MAX_VALUE;
          }

          @Override
          public Class<?> type() {
            return type.strings().type();
          }

          @Override
          public String decode() {
            return type.strings().decode();
          }

          @Override
          public String encode() {
            return type.strings().encode();
          }
        }).model;
      }

      if (member == null)
        throw new ValidationException("@" + t.class.getName() + " does not specify a type");

      members.add(member);
    }

    return members;
  }

  @Override
  void resolveOverrides() {
  }

  private Registry.Type type;

  @Override
  Registry.Type typeDefault() {
    return type == null ? type = (this.types == null ? registry.OBJECT : getGreatestCommonSuperType(this.types)) : type;
  }

  @Override
  String isValid(final Binding.Type typeBinding) {
    return typeBinding.type == null ? null : "Cannot override the type for \"any\"";
  }

  @Override
  String nameName() {
    return "names";
  }

  @Override
  public String elementName() {
    return "any";
  }

  @Override
  Class<?> defaultClass() {
    return Object.class;
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return AnyProperty.class;
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return AnyElement.class;
  }

  @Override
  Map<String,Object> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toXmlAttributes(owner, packageName);
    if (types != null) {
      final StringBuilder b = new StringBuilder();
      for (int i = 0, i$ = types.size(); i < i$; ++i) { // [RA]
        if (i > 0)
          b.append(' ');

        b.append(Registry.getSubName(types.get(i).id().toString(), packageName));
      }

      attributes.put("types", b.toString());
    }

    return attributes;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (types == null)
      return;

    final ArrayList<String> values = new ArrayList<>();
    attributes.put("types", values);

    StringBuilder b = null;
    for (int i = 0, i$ = types.size(); i < i$; ++i) { // [RA]
      final Member type = types.get(i);
      if (type instanceof ArrayModel) {
        values.add("@" + t.class.getName() + "(arrays=" + ((ArrayModel)type).classType().getCanonicalName() + ".class)");
      }
      else if (type instanceof ObjectModel) {
        values.add("@" + t.class.getName() + "(objects=" + ((ObjectModel)type).classType().getCanonicalName() + ".class)");
      }
      else if (type instanceof BooleanModel || type instanceof NumberModel || type instanceof StringModel) {
        final Model model = (Model)type;
        final AttributeMap attrs = new AttributeMap();
        model.toAnnotationAttributes(attrs, this);
        if (b == null)
          b = new StringBuilder();
        else
          b.setLength(0);

        b.append('@').append(type.typeAnnotation().getName());
        if (attrs.size() > 0) {
          b.append('(');
          final Iterator<Map.Entry<String,Object>> iterator = attrs.entrySet().iterator();
          for (int j = 0; iterator.hasNext(); ++j) { // [I]
            if (j > 0)
              b.append(", ");

            final Map.Entry<String,Object> entry = iterator.next();
            b.append(entry.getKey()).append('=').append(entry.getValue());
          }

          b.append(')');
        }

        values.add("@" + t.class.getName() + "(" + type.elementName() + "s=" + b + ")");
      }
      else {
        throw new UnsupportedOperationException("Unsupported type: " + type.getClass().getName());
      }
    }
  }

  @Override
  ArrayList<AnnotationType> getClassAnnotation() {
    return null;
  }

  @Override
  String toSource(final Settings settings) {
    return null;
  }

  private boolean referencesResolved;

  @Override
  void resolveReferences() {
    if (referencesResolved)
      return;

    referencesResolved = true;
    if (types == null)
      return;

    final ListIterator<Member> iterator = types.listIterator();
    while (iterator.hasNext()) {
      Member member = iterator.next();
      if (member instanceof Deferred)
        member = ((Deferred<?>)member).resolve();

      if (member instanceof Reference)
        member = ((Reference)member).model;

      iterator.set(member);
    }
  }
}