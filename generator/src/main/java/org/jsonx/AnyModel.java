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
import org.openjax.json.JsonUtil;
import org.w3.www._2001.XMLSchema.yAA.$IDREFS;

final class AnyModel extends Referrer<AnyModel> {
  private static $Any property(final schema.AnyProperty jsd, final String name) {
    final $Any xsb = new $Any() {
      private static final long serialVersionUID = 650722913732574568L;

      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setNames$(new $Any.Names$(JsonUtil.unescape(name)));

    if (jsd.getTypes() != null)
      xsb.setTypes$(new $Any.Types$(jsd.getTypes().split(" ")));

    if (jsd.getNullable() != null)
      xsb.setNullable$(new $Any.Nullable$(jsd.getNullable()));

    if (jsd.getUse() != null)
      xsb.setUse$(new $Any.Use$($Any.Use$.Enum.valueOf(jsd.getUse())));

    if (jsd.getBindings() != null) {
      for (final schema.FieldBinding binding : jsd.getBindings()) {
        final $Any.Binding bin = new $Any.Binding();
        bin.setLang$(new $Binding.Lang$(binding.getLang()));
        bin.setField$(new $Any.Binding.Field$(binding.getField()));
        xsb.addBinding(bin);
      }
    }

    return xsb;
  }

  private static $ArrayMember.Any element(final schema.AnyElement jsd) {
    final $ArrayMember.Any xsb = new $ArrayMember.Any();

    if (jsd.getTypes() != null)
      xsb.setTypes$(new $ArrayMember.Any.Types$(jsd.getTypes().split(" ")));

    if (jsd.getNullable() != null)
      xsb.setNullable$(new $ArrayMember.Any.Nullable$(jsd.getNullable()));

    if (jsd.getMinOccurs() != null)
      xsb.setMinOccurs$(new $ArrayMember.Any.MinOccurs$(new BigInteger(jsd.getMinOccurs())));

    if (jsd.getMaxOccurs() != null)
      xsb.setMaxOccurs$(new $ArrayMember.Any.MaxOccurs$(jsd.getMaxOccurs()));

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

    if (jsd.getDoc() != null && jsd.getDoc().length() > 0)
      xsb.setDoc$(new $Documented.Doc$(jsd.getDoc()));

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
      private static final long serialVersionUID = 7585066984559415750L;

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

  private final List<Member> types;

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
    this.types = getMemberTypes(property.types());
    final Class<?> requiredFieldType = property.types().length == 0 ? defaultClass() : getFieldType(property.types());
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

  private List<Member> getTypes(final Boolean nullable, Use use, final $IDREFS refs) {
    final List<String> idrefs;
    if (refs == null || (idrefs = refs.text()).size() == 0)
      return null;

    final List<Member> types = new ArrayList<>(idrefs.size());
    for (final String idref : idrefs) {
      final Id id = Id.hashed(idref);
      types.add(Reference.defer(registry, this, newRnonymousReference(nullable, use), () -> {
        final Member model = registry.getModel(id);
        if (model == null)
          throw new IllegalStateException("Type id=\"" + id + "\" in <any> not found");

        return (Model)registry.reference(model, this);
      }));
    }

    return types;
  }

  private static Class<?> getFieldType(final t[] types) {
    if (types.length == 0)
      return null;

    final ArrayList<Class<?>> members = new ArrayList<>(types.length);
    for (final t type : types) {
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

  private List<Member> getMemberTypes(final t[] types) {
    if (types.length == 0)
      return null;

    final ArrayList<Member> members = new ArrayList<>(types.length);
    for (final t type : types) {
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
      final StringBuilder builder = new StringBuilder();
      final Iterator<Member> iterator = types.iterator();
      for (int i = 0; iterator.hasNext(); ++i) {
        if (i > 0)
          builder.append(' ');

        builder.append(Registry.getSubName(iterator.next().id().toString(), packageName));
      }

      attributes.put("types", builder.toString());
    }

    return attributes;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (types == null)
      return;

    final List<String> values = new ArrayList<>();
    attributes.put("types", values);

    StringBuilder builder = null;
    for (final Member type : types) {
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
        if (builder == null)
          builder = new StringBuilder();
        else
          builder.setLength(0);

        builder.append('@').append(type.typeAnnotation().getName());
        if (attrs.size() > 0) {
          builder.append('(');
          final Iterator<Map.Entry<String,Object>> iterator = attrs.entrySet().iterator();
          for (int i = 0; iterator.hasNext(); ++i) {
            if (i > 0)
              builder.append(", ");

            final Map.Entry<String,Object> entry = iterator.next();
            builder.append(entry.getKey()).append('=').append(entry.getValue());
          }

          builder.append(')');
        }

        values.add("@" + t.class.getName() + "(" + type.elementName() + "s=" + builder.toString() + ")");
      }
      else {
        throw new UnsupportedOperationException("Unsupported type: " + type.getClass().getName());
      }
    }
  }

  @Override
  List<AnnotationType> getClassAnnotation() {
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