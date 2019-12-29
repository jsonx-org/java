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
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jsonx.www.schema_0_3.xL0gluGCXAA;
import org.libj.lang.IllegalAnnotationException;
import org.libj.lang.Strings;
import org.libj.util.Classes;
import org.openjax.json.JsonUtil;
import org.w3.www._2001.XMLSchema.yAA.$IDREFS;

final class AnyModel extends Referrer<AnyModel> {
  private static xL0gluGCXAA.$Any property(final schema.AnyProperty jsd, final String name) {
    final xL0gluGCXAA.$Any xsb = new xL0gluGCXAA.$Any() {
      private static final long serialVersionUID = 650722913732574568L;

      @Override
      protected xL0gluGCXAA.$Member inherits() {
        return new xL0gluGCXAA.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setNames$(new xL0gluGCXAA.$Any.Names$(JsonUtil.unescape(name)));

    if (jsd.getTypes() != null)
      xsb.setTypes$(new xL0gluGCXAA.$Any.Types$(jsd.getTypes().split(" ")));

    if (jsd.getNullable() != null)
      xsb.setNullable$(new xL0gluGCXAA.$Any.Nullable$(jsd.getNullable()));

    if (jsd.getUse() != null)
      xsb.setUse$(new xL0gluGCXAA.$Any.Use$(xL0gluGCXAA.$Any.Use$.Enum.valueOf(jsd.getUse())));

    return xsb;
  }

  private static xL0gluGCXAA.$ArrayMember.Any element(final schema.AnyElement jsd) {
    final xL0gluGCXAA.$ArrayMember.Any xsb = new xL0gluGCXAA.$ArrayMember.Any();

    if (jsd.getTypes() != null)
      xsb.setTypes$(new xL0gluGCXAA.$ArrayMember.Any.Types$(jsd.getTypes().split(" ")));

    if (jsd.getNullable() != null)
      xsb.setNullable$(new xL0gluGCXAA.$ArrayMember.Any.Nullable$(jsd.getNullable()));

    if (jsd.getMinOccurs() != null)
      xsb.setMinOccurs$(new xL0gluGCXAA.$ArrayMember.Any.MinOccurs$(new BigInteger(jsd.getMinOccurs())));

    if (jsd.getMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL0gluGCXAA.$ArrayMember.Any.MaxOccurs$(jsd.getMaxOccurs()));

    return xsb;
  }

  static xL0gluGCXAA.$AnyMember jsdToXsb(final schema.Any jsd, final String name) {
    final xL0gluGCXAA.$AnyMember xsb;
    if (jsd instanceof schema.AnyProperty)
      xsb = property((schema.AnyProperty)jsd, name);
    else if (jsd instanceof schema.AnyElement)
      xsb = element((schema.AnyElement)jsd);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getDoc() != null && jsd.getDoc().length() > 0)
      xsb.setDoc$(new xL0gluGCXAA.$Documented.Doc$(jsd.getDoc()));

    return xsb;
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final AnyProperty property, final Field field) {
    final AnyModel model = new AnyModel(registry, referrer, property, field);
    final Id id = model.id;

    final AnyModel registered = (AnyModel)registry.getModel(id);
    return new Reference(registry, referrer, JsdUtil.getName(property.name(), field), property.nullable(), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final AnyElement element) {
    final AnyModel model = new AnyModel(registry, referrer, element);
    final Id id = model.id;

    final AnyModel registered = (AnyModel)registry.getModel(id);
    return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static AnyModel reference(final Registry registry, final Referrer<?> referrer, final xL0gluGCXAA.$Array.Any binding) {
    return registry.reference(new AnyModel(registry, referrer, binding), referrer);
  }

  static AnyModel reference(final Registry registry, final Referrer<?> referrer, final xL0gluGCXAA.$Any binding) {
    return registry.reference(new AnyModel(registry, referrer, binding), referrer);
  }

  private static final xL0gluGCXAA.$Reference anonymousReference = new xL0gluGCXAA.$Reference() {
    private static final long serialVersionUID = 7585066984559415750L;
    private final Name$ name = new Name$("");

    @Override
    protected xL0gluGCXAA.$Member inherits() {
      return null;
    }

    @Override
    public Name$ getName$() {
      return name;
    }

    @Override
    public Nullable$ getNullable$() {
      return null;
    }

    @Override
    public Use$ getUse$() {
      return null;
    }
  };

  private final List<Member> types;

  private AnyModel(final Registry registry, final Declarer declarer, final xL0gluGCXAA.$Any binding) {
    super(registry, declarer, binding.getDoc$(), binding.getNames$(), binding.getNullable$(), binding.getUse$(), null);
    this.types = getTypes(binding.getTypes$());
  }

  private AnyModel(final Registry registry, final Declarer declarer, final xL0gluGCXAA.$Array.Any binding) {
    super(registry, declarer, binding.getDoc$(), binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$(), null);
    this.types = getTypes(binding.getTypes$());
  }

  private AnyModel(final Registry registry, final Declarer declarer, final AnyProperty property, final Field field) {
    super(registry, declarer, property.nullable(), property.use(), null);
    this.types = getMemberTypes(property.types());
    final Class<?> requiredFieldType = property.types().length == 0 ? Object.class : getFieldType(property.types());
    final boolean isRegex = Strings.isRegex(property.name());
    if (isRegex && !Map.class.isAssignableFrom(field.getType()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + AnyProperty.class.getSimpleName() + " of type " + field.getType().getName() + " with regex name=\"" + property.name() + "\" must be of type that extends " + Map.class.getName());

    if (!isAssignable(field, requiredFieldType, isRegex, property.nullable(), property.use()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + AnyProperty.class.getSimpleName() + " of type " + field.getType().getName() + " is not assignable for the specified types attribute");
  }

  private AnyModel(final Registry registry, final Declarer declarer, final AnyElement element) {
    super(registry, declarer, element.nullable(), null, null);
    this.types = getMemberTypes(element.types());
  }

  private List<Member> getTypes(final $IDREFS refs) {
    final List<String> idrefs;
    if (refs == null || (idrefs = refs.text()).size() == 0)
      return null;

    final List<Member> types = new ArrayList<>(idrefs.size());
    for (final String idref : idrefs) {
      final Id id = Id.hashed(idref);
      types.add(Reference.defer(registry, this, anonymousReference, () -> {
        final Member model = registry.getModel(id);
        if (model == null)
          throw new IllegalStateException("Type id=\"" + id + "\" in <any> not found");

        return (Model)registry.reference(model, this);
      }));
    }

    return types;
  }

  private static final BooleanElement anonymousBooleanElement = new BooleanElement() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return BooleanElement.class;
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
    public int minOccurs() {
      return 1;
    }

    @Override
    public int maxOccurs() {
      return Integer.MAX_VALUE;
    }
  };

  private static Class<?> getFieldType(final t[] types) {
    if (types.length == 0)
      return null;

    final List<Class<?>> members = new ArrayList<>(types.length);
    for (final t type : types) {
      if (AnyType.isEnabled(type.arrays()))
        members.add(List.class);
      else if (type.booleans())
        members.add(Boolean.class);
      else if (AnyType.isEnabled(type.numbers()))
        members.add(Number.class);
      else if (AnyType.isEnabled(type.objects()))
        members.add(type.objects());
      else if (AnyType.isEnabled(type.strings()))
        members.add(String.class);
    }

    return Classes.getGreatestCommonSuperclass(members.toArray(new Class[members.size()]));
  }

  private List<Member> getMemberTypes(final t[] types) {
    if (types.length == 0)
      return null;

    final List<Member> members = new ArrayList<>(types.length);
    for (final t type : types) {
      Member member = null;
      if (AnyType.isEnabled(type.arrays()))
        member = ArrayModel.referenceOrDeclare(registry, this, type.arrays());

      if (type.booleans()) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " can only specify one type");

        member = BooleanModel.referenceOrDeclare(registry, this, anonymousBooleanElement).model;
      }

      if (AnyType.isEnabled(type.numbers())) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " can only specify one type");

        member = NumberModel.referenceOrDeclare(registry, this, type.numbers());
      }

      if (AnyType.isEnabled(type.objects())) {
        if (member != null)
          throw new ValidationException("@" + t.class.getName() + " can only specify one type");

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
          throw new ValidationException("@" + t.class.getName() + " can only specify one type");

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
            return type.strings();
          }

          @Override
          public int minOccurs() {
            return 1;
          }

          @Override
          public int maxOccurs() {
            return Integer.MAX_VALUE;
          }
        }).model;
      }

      if (member == null)
        throw new ValidationException("@" + t.class.getName() + " is empty");

      members.add(member);
    }

    return members;
  }

  private Registry.Type type;

  @Override
  Registry.Type type() {
    return type == null ? type = (this.types == null ? registry.getType(Object.class) : getGreatestCommonSuperType(this.types)) : type;
  }

  @Override
  String nameName() {
    return "names";
  }

  @Override
  String elementName() {
    return "any";
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
  Map<String,Object> toAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toAttributes(owner, packageName);
    if (types != null) {
      final StringBuilder builder = new StringBuilder();
      for (final Member type : types)
        builder.append(Registry.getSubName(type.id.toString(), packageName)).append(' ');

      builder.setLength(builder.length() - 1);
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
      else if (type instanceof BooleanModel) {
        values.add("@" + t.class.getName() + "(booleans=true)");
      }
      else if (type instanceof NumberModel) {
        final NumberModel model = (NumberModel)type;
        final AttributeMap numberAttrs = new AttributeMap();
        model.toAnnotationAttributes(numberAttrs, this);
        if (builder == null)
          builder = new StringBuilder();
        else
          builder.setLength(0);

        builder.append('@').append(NumberType.class.getName());
        if (numberAttrs.size() > 0) {
          builder.append('(');
          for (final Map.Entry<String,Object> entry : numberAttrs.entrySet())
            builder.append(entry.getKey()).append('=').append(entry.getValue()).append(", ");

          builder.setLength(builder.length() - 1);
          builder.setCharAt(builder.length() - 1, ')');
        }

        values.add("@" + t.class.getName() + "(numbers=" + builder.toString() + ")");
      }
      else if (type instanceof ObjectModel) {
        values.add("@" + t.class.getName() + "(objects=" + ((ObjectModel)type).classType().getCanonicalName() + ".class)");
      }
      else if (type instanceof StringModel) {
        final String pattern = ((StringModel)type).pattern;
        values.add("@" + t.class.getName() + "(strings=\"" + (pattern == null ? ".*" : pattern) + "\")");
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