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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsonx.schema.FieldBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Any;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Binding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Boolean;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$FieldBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Number;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Object;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Reference;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$String;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.libj.lang.Classes;
import org.libj.lang.IllegalAnnotationException;
import org.libj.lang.ObjectUtil;
import org.libj.lang.Strings;
import org.libj.util.CollectionUtil;
import org.libj.util.Iterators;
import org.openjax.xml.api.XmlElement;

final class ObjectModel extends Referrer<ObjectModel> {
  private static Schema.Object type(final schema.ObjectType jsd, final String name) {
    final Schema.Object xsb = new Schema.Object();
    if (name != null)
      xsb.setName$(new Schema.Object.Name$(name));

    if (jsd.getAbstract() != null)
      xsb.setAbstract$(new Schema.Object.Abstract$(jsd.getAbstract()));

    return xsb;
  }

  private static $Object property(final schema.ObjectProperty jsd, final String name) {
    final $Object xsb = new $Object() {
      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new $Object.Name$(name));

    if (jsd.getNullable() != null)
      xsb.setNullable$(new $Object.Nullable$(jsd.getNullable()));

    if (jsd.getUse() != null)
      xsb.setUse$(new $Object.Use$($Object.Use$.Enum.valueOf(jsd.getUse())));

    if (jsd.getExtends() != null)
      xsb.setExtends$(new $ObjectMember.Extends$(jsd.getExtends()));

    final int i$;
    final List<FieldBinding> bindings = jsd.getBindings();
    if (bindings != null && (i$ = bindings.size()) > 0) {
      if (CollectionUtil.isRandomAccess(bindings)) {
        for (int i = 0; i < i$; ++i) // [RA]
          addBinding(bindings.get(i), xsb);
      }
      else {
        for (final schema.FieldBinding binding : bindings) // [L]
          addBinding(binding, xsb);
      }
    }

    return xsb;
  }

  private static void addBinding(final schema.FieldBinding binding, final $Object xsb) {
    final $Object.Binding bin = new $Object.Binding();
    bin.setLang$(new $Binding.Lang$(binding.getLang()));
    bin.setField$(new $Object.Binding.Field$(binding.getField()));
    xsb.addBinding(bin);
  }

  static $ObjectMember jsdToXsb(final schema.Object jsd, final String name) {
    final $ObjectMember xsb;
    if (jsd instanceof schema.ObjectType)
      xsb = type((schema.ObjectType)jsd, name);
    else if (jsd instanceof schema.ObjectProperty)
      xsb = property((schema.ObjectProperty)jsd, name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getDoc() != null && jsd.getDoc().length() > 0)
      xsb.setDoc$(new $Documented.Doc$(jsd.getDoc()));

    if (jsd.getExtends() != null)
      xsb.setExtends$(new $ObjectMember.Extends$(jsd.getExtends()));

    if (jsd.getProperties() != null) {
      final LinkedHashMap<String,? extends schema.Member> properties = jsd.getProperties().getProperties();
      if (properties != null && properties.size() > 0) {
        for (final Map.Entry<String,? extends schema.Member> entry : properties.entrySet()) { // [S]
          final String propertyName = entry.getKey();
          final schema.Member property = entry.getValue();
          if (property instanceof schema.AnyProperty)
            xsb.addProperty(AnyModel.jsdToXsb((schema.AnyProperty)property, propertyName));
          else if (property instanceof schema.ArrayProperty)
            xsb.addProperty(ArrayModel.jsdToXsb((schema.ArrayProperty)property, propertyName));
          else if (property instanceof schema.BooleanProperty)
            xsb.addProperty(BooleanModel.jsdToXsb((schema.BooleanProperty)property, propertyName));
          else if (property instanceof schema.NumberProperty)
            xsb.addProperty(NumberModel.jsdToXsb((schema.NumberProperty)property, propertyName));
          else if (property instanceof schema.ReferenceProperty)
            xsb.addProperty(Reference.jsdToXsb((schema.ReferenceProperty)property, propertyName));
          else if (property instanceof schema.StringProperty)
            xsb.addProperty(StringModel.jsdToXsb((schema.StringProperty)property, propertyName));
          else if (property instanceof schema.ObjectProperty)
            xsb.addProperty(ObjectModel.jsdToXsb((schema.ObjectProperty)property, propertyName));
          else
            throw new UnsupportedOperationException("Unsupported type: " + entry.getValue().getClass().getName());
        }
      }
    }

    return xsb;
  }

  static ObjectModel declare(final Registry registry, final Declarer declarer, final Schema.Object xsb) {
    return registry.declare(xsb).value(new ObjectModel(registry, declarer, xsb), null);
  }

  static ObjectModel declare(final Registry registry, final ObjectModel referrer, final $Object xsb) {
    return registry.declare(xsb).value(new ObjectModel(registry, referrer, xsb, getBinding(xsb.getBinding())), referrer);
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ObjectProperty property, final Class<?> cls, final String name, final String fieldName) {
    if (!JxObject.class.isAssignableFrom(cls))
      throw new IllegalAnnotationException(property, fieldName + ": @" + ObjectProperty.class.getSimpleName() + " can only be applied to fields of JxObject type with use=\"required\" or nullable=false, or of Optional<? extends JxObject> type with use=\"optional\" and nullable=true");

    final Id id = Id.named(cls);
    if (registry.isPending(id))
      return Reference.defer(registry, referrer, name, property.nullable(), property.use(), fieldName, null, () -> registry.reference(registry.getModel(id), referrer));

    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return new Reference(registry, referrer, name, property.nullable(), property.use(), fieldName, null, model == null ? registry.declare(id).value(new ObjectModel(registry, referrer, cls, fieldName, property), referrer) : registry.reference(model, referrer));
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ObjectProperty property, final Method getMethod, final String fieldName) {
    if (!isAssignable(getMethod, true, JxObject.class, false, property.nullable(), property.use()))
      throw new IllegalAnnotationException(property, getMethod.getDeclaringClass().getName() + "." + fieldName + ": @" + ObjectProperty.class.getSimpleName() + " can only be applied to fields of JxObject type with use=\"required\" or nullable=false, or of Optional<? extends JxObject> type with use=\"optional\" and nullable=true");

    final Class<?> cls = JsdUtil.getRealType(getMethod);
    final Id id = Id.named(cls);
    if (registry.isPending(id))
      return Reference.defer(registry, referrer, property.name(), property.nullable(), property.use(), fieldName, null, () -> registry.reference(registry.getModel(id), referrer));

    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return new Reference(registry, referrer, property.name(), property.nullable(), property.use(), fieldName, null, model == null ? registry.declare(id).value(new ObjectModel(registry, referrer, cls, fieldName, property), referrer) : registry.reference(model, referrer));
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ObjectElement element) {
    final Id id = Id.named(element.type());
    if (registry.isPending(id))
      return Reference.defer(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), () -> registry.reference(registry.getModel(id), referrer));

    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), model == null ? registry.declare(id).value(new ObjectModel(registry, referrer, element), referrer) : registry.reference(model, referrer));
  }

  static Member referenceOrDeclare(final Registry registry, final Declarer declarer, final Class<?> cls) {
    checkJSObject(cls);
    final Id id = Id.named(cls);
    if (registry.isPending(id))
      return new Deferred<>(false, null, null, () -> registry.reference(registry.getModel(id), null));

    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return model != null ? registry.reference(model, null) : registry.declare(id).value(new ObjectModel(registry, declarer, cls, null, null, null), null);
  }

  @SuppressWarnings("null")
  static String getFullyQualifiedName(final $Object xsb) {
    final StringBuilder builder = new StringBuilder();
    $Object owner = xsb;
    do
      builder.insert(0, '$').insert(1, JsdUtil.toIdentifier(Strings.flipFirstCap(owner.getName$().text())));
    while (owner.owner() instanceof $Object && (owner = ($Object)owner.owner()) != null);
    return builder.insert(0, JsdUtil.toIdentifier(JsdUtil.flipName(((Schema.Object)owner.owner()).getName$().text()))).toString();
  }

  private static void checkJSObject(final Class<?> cls) {
    if (!JxObject.class.isAssignableFrom(cls))
      throw new IllegalArgumentException("Class " + cls.getName() + " does not implement " + JxObject.class.getName());
  }

  private void recurseInnerClasses(final Registry registry, final Class<?> cls) {
    for (final Class<?> innerClass : cls.getClasses()) { // [A]
      if (!JxObject.class.isAssignableFrom(innerClass))
        recurseInnerClasses(registry, innerClass);
      else
        referenceOrDeclare(registry, this, innerClass);
    }
  }

  private LinkedHashMap<String,Member> parseMembers(final $ObjectMember xsb, final ObjectModel objectModel) {
    final LinkedHashMap<String,Member> members = new LinkedHashMap<>();
    final Iterator<? super $Member> iterator = Iterators.filter(xsb.elementIterator(), m -> m instanceof $Member);
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof $Any) {
        final $Any any = ($Any)member;
        members.put(any.getNames$().text(), AnyModel.reference(registry, objectModel, any));
      }
      else if (member instanceof $Array) {
        final $Array array = ($Array)member;
        final ArrayModel child = ArrayModel.reference(registry, objectModel, array);
        members.put(array.getName$().text(), child);
      }
      else if (member instanceof $Boolean) {
        final $Boolean bool = ($Boolean)member;
        members.put(bool.getName$().text(), BooleanModel.reference(registry, objectModel, bool));
      }
      else if (member instanceof $Number) {
        final $Number number = ($Number)member;
        members.put(number.getName$().text(), NumberModel.reference(registry, objectModel, number));
      }
      else if (member instanceof $Object) {
        final $Object object = ($Object)member;
        final ObjectModel child = declare(registry, objectModel, object);
        members.put(object.getName$().text(), child);
      }
      else if (member instanceof $Reference) {
        final $Reference reference = ($Reference)member;
        final Id id = Id.named(reference.getType$());
        final Member child = registry.getModel(id);
        members.put(reference.getName$().text(), child instanceof Reference ? child : Reference.defer(registry, objectModel, reference, () -> registry.reference(registry.getModel(id), objectModel)));
      }
      else if (member instanceof $String) {
        final $String string = ($String)member;
        members.put(string.getName$().text(), StringModel.reference(registry, objectModel, string));
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + member.getClass().getSimpleName() + " member type: " + member.getClass().getName());
      }
    }

    return members;
  }

  class Property {
    private final Member member;
    private boolean overrideSet;
    private Member override;

    private Property(final Member member) {
      this.member = member;
    }

    private void setOverride(final Member override) {
      overrideSet = true;
      this.override = override;
    }

    private Member getOverride() {
      if (!overrideSet)
        throw new IllegalStateException("Override was not set for: " + member.declarer.id() + " -> " + member.name() + " (" + member.id() + ")");

      return override;
    }

    public XmlElement toXml(final Settings settings, final ObjectModel owner, final String packageName) {
      final XmlElement element = member.toXml(settings, owner, packageName);
      if (getOverride() != null) {
        // FIXME: Removing the whole binding element... but what about "decode" and "encode"? Should these be overridable?
        if (element.getElements() != null) {
          final Iterator<?> iterator = element.getElements().iterator();
          while (iterator.hasNext()) {
            final XmlElement child = (XmlElement)iterator.next();
            if ("binding".equals(child.getName()))
              iterator.remove();
          }
        }

        element.getAttributes().remove("nullable");
        element.getAttributes().remove("use");
        element.getAttributes().remove("minOccurs");
        element.getAttributes().remove("maxOccurs");
      }

      return element;
    }

    @SuppressWarnings("unchecked")
    public Object toJson(final Settings settings, final ObjectModel owner, final String packageName) {
      final Map<String,Object> object = (Map<String,Object>)member.toJson(settings, owner, packageName);
      if (getOverride() != null) {
        // FIXME: Removing the whole binding element... but what about "decode" and "encode"? Should these be overridable?
        object.remove("bindings");
        object.remove("nullable");
        object.remove("use");
        object.remove("minOccurs");
        object.remove("maxOccurs");
      }

      return object;
    }
  }

  final LinkedHashMap<String,Property> properties;
  private Member superObject;
  final boolean isAbstract;

  private final Class<?> cls;
  private final Map<String,PropertySpec> getMethodToPropertySpec;

  private ObjectModel(final Registry registry, final Declarer declarer, final Schema.Object xsb) {
    super(registry, declarer, registry.getType(registry.packageName, registry.classPrefix + JsdUtil.flipName(xsb.getName$().text()), xsb.getExtends$() != null ? registry.classPrefix + JsdUtil.flipName(xsb.getExtends$().text()) : null), xsb.getDoc$(), xsb.getName$().text());
    this.isAbstract = xsb.getAbstract$().text();
    this.superObject = getReference(xsb.getExtends$());
    this.properties = parseProperties(xsb);

    this.cls = null;
    this.getMethodToPropertySpec = null;

    validateTypeBinding();
  }

  private LinkedHashMap<String,Property> parseProperties(final $ObjectMember xsb) {
    final LinkedHashMap<String,Member> members = parseMembers(xsb, this);
    if (members == null)
      return null;

    final int size = members.size();
    final LinkedHashMap<String,Property> properties = new LinkedHashMap<>(size);
    if (size > 0)
      for (final Map.Entry<String,Member> entry : members.entrySet()) // [S]
        properties.put(entry.getKey(), new Property(entry.getValue()));

    return properties;
  }

  /**
   * Returns the {@link Property} matching the provided name in the inheritance chain of this {@link ObjectModel}.
   *
   * @implNote This method is intended to be called after {@link #resolveReferences()} is called. Otherwise, {@link #superObject}
   *           values may be instances of {@link Deferred}.
   * @param name The name of {@link Property} to get.
   * @return The {@link Property} matching the provided name in the inheritance chain of this {@link ObjectModel}.
   * @throws IllegalStateException If this method is called before {@link #resolveReferences()}.
   */
  private Property getSuperProperty(final String name) {
    if (referencesResolved != null && !referencesResolved)
      throw new IllegalStateException();

    for (ObjectModel parent = (ObjectModel)superObject; parent != null; parent = (ObjectModel)parent.superObject) { // [X]
      final Property property = parent.properties.get(name);
      if (property != null)
        return property;
    }

    return null;
  }

  private void resolveProperties() {
    if (properties.size() > 0) {
      for (final Map.Entry<String,Property> entry : properties.entrySet()) { // [S]
        final Property superProperty = superObject == null ? null : getSuperProperty(entry.getKey());
        final Property property = entry.getValue();
        if (superProperty == null) {
          property.setOverride(null);
          continue;
        }

        if (superProperty.member.isAssignableFrom(property.member))
          property.setOverride(superProperty.member);
        else {
          superProperty.member.isAssignableFrom(property.member);
          throw new ValidationException("Object " + (name() != null ? name(): JsdUtil.flipName(id().toString())) + "." + entry.getKey() + " overrides " + property.member.name() + "." + entry.getKey() + " with incompatible type");
        }
      }
    }
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final Class<?> cls, final String fieldName, final ObjectProperty property) {
    this(registry, declarer, cls, property.nullable(), property.use(), fieldName);
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final ObjectElement element) {
    this(registry, declarer, element.type(), element.nullable(), null, null);
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final $Object xsb, final $FieldBinding binding) {
    super(registry, declarer, xsb.getDoc$(), xsb.getName$(), xsb.getNullable$(), xsb.getUse$(), registry.getType(registry.packageName, registry.classPrefix + getFullyQualifiedName(xsb), xsb.getExtends$() != null ? registry.classPrefix + JsdUtil.flipName(xsb.getExtends$().text()) : null), binding == null ? null : binding.getField$(), null);
    this.superObject = getReference(xsb.getExtends$());
    this.isAbstract = false;
    this.properties = parseProperties(xsb);

    this.cls = null;
    this.getMethodToPropertySpec = null;

    validateTypeBinding();
  }

  private class PropertySpec {
    final Annotation annotation;
    final String propertyName;
    final String fieldName;

    private PropertySpec(final Annotation annotation, final String propertyName, final String fieldName) {
      this.annotation = annotation;
      this.propertyName = propertyName;
      this.fieldName = fieldName;
    }
  }

  private static final ClassToGetMethods classToOrderedMethods = new ClassToGetMethods() {
    @Override
    Method[] getMethods(final Class<? extends JxObject> cls) {
      return cls.getDeclaredMethods();
    }

    @Override
    public boolean test(final Method method) {
      return Modifier.isPublic(method.getModifiers()) && !method.isSynthetic() && method.getReturnType() != void.class && method.getParameterCount() == 0;
    }

    @Override
    void beforePut(final Method[] methods) {
      Classes.sortDeclarativeOrder(methods);
    }
  };

  private ObjectModel(final Registry registry, final Declarer declarer, final Class<?> cls, final Boolean nullable, final Use use, final String fieldName) {
    super(registry, declarer, nullable, use, fieldName, registry.getType(cls));
    final Class<?> superClass = cls.getSuperclass();
    this.isAbstract = Modifier.isAbstract(cls.getModifiers());
    this.superObject = superClass == null || !JxObject.class.isAssignableFrom(superClass) ? null : referenceOrDeclare(registry, declarer, superClass);
    this.properties = new LinkedHashMap<>();

    this.cls = cls;
    this.getMethodToPropertySpec = new HashMap<>();
    for (final Method getMethod : classToOrderedMethods.get(cls)) { // [A]
      Member reference = null;
      Annotation annotation = null;
      final Annotation[] annotations = Classes.getAnnotations(getMethod);
      for (int i = 0, i$ = annotations.length; i < i$; ++i) { // [A]
        annotation = annotations[i];
        Member next = null;
        final String field = JsdUtil.getFieldName(getMethod);
        if (AnyProperty.class.equals(annotation.annotationType()))
          next = AnyModel.referenceOrDeclare(registry, this, (AnyProperty)annotation, getMethod, field);
        else if (ArrayProperty.class.equals(annotation.annotationType()))
          next = ArrayModel.referenceOrDeclare(registry, this, (ArrayProperty)annotation, getMethod, field);
        else if (BooleanProperty.class.equals(annotation.annotationType()))
          next = BooleanModel.referenceOrDeclare(registry, this, (BooleanProperty)annotation, getMethod, field);
        else if (NumberProperty.class.equals(annotation.annotationType()))
          next = NumberModel.referenceOrDeclare(registry, this, (NumberProperty)annotation, getMethod, field);
        else if (ObjectProperty.class.equals(annotation.annotationType()))
          next = ObjectModel.referenceOrDeclare(registry, this, (ObjectProperty)annotation, getMethod, field);
        else if (StringProperty.class.equals(annotation.annotationType()))
          next = StringModel.referenceOrDeclare(registry, this, (StringProperty)annotation, getMethod, field);

        if (reference == null)
          reference = next;
        else if (next != null)
          throw new ValidationException(getMethod.getDeclaringClass().getName() + "." + field + " specifies multiple parameter annotations: [" + reference.elementName() + ", " + next.elementName() + "]");
      }

      if (reference != null) {
        properties.put(reference.name(), new Property(reference));
        if (annotation instanceof ObjectProperty || annotation instanceof ArrayProperty) {
          final String getMethodName = "get" + reference.fieldBinding.classCase;
          final Method method = Classes.getDeclaredMethod(cls, getMethodName);
          if (method != null)
            getMethodToPropertySpec.put(getMethodName, new PropertySpec(annotation, reference.name(), fieldName));
        }
      }
    }

    recurseInnerClasses(registry, cls);

    validateTypeBinding();
  }

  @Override
  void resolveOverrides() {
    if (cls != null) {
      for (final Method getMethod : classToOrderedMethods.get(cls)) { // [A]
        // Find the "get" methods that do not have fields declared in `cls`
        final PropertySpec superPropertySpec = getSuperObjectProperty(getMethod);
        if (superPropertySpec == null)
          continue;

        final Member member;
        if (superPropertySpec.annotation instanceof ObjectProperty) {
          // Create a default `ObjectProperty`, and just match the name
          final ObjectProperty annotation = new ObjectProperty() {
            @Override
            public Class<? extends Annotation> annotationType() {
              return ObjectProperty.class;
            }

            @Override
            public Use use() {
              return Use.REQUIRED;
            }

            @Override
            public boolean nullable() {
              return false;
            }

            @Override
            public String name() {
              return superPropertySpec.propertyName;
            }
          };

          member = ObjectModel.referenceOrDeclare(registry, this, annotation, JsdUtil.getRealType(getMethod), superPropertySpec.propertyName, superPropertySpec.fieldName);
        }
        else if (superPropertySpec.annotation instanceof ArrayProperty) {
          // Create a default `ArrayProperty`, and just match the elementIds() and type()
          final ArrayProperty arrayProperty = (ArrayProperty)superPropertySpec.annotation;
          final ArrayProperty annotation = new ArrayProperty() {
            @Override
            public Class<? extends Annotation> annotationType() {
              return ArrayProperty.class;
            }

            @Override
            public String name() {
              return arrayProperty.name();
            }

            @Override
            public boolean nullable() {
              return true;
            }

            @Override
            public Use use() {
              return Use.REQUIRED;
            }

            @Override
            public int minIterate() {
              return 1;
            }

            @Override
            public int maxIterate() {
              return 1;
            }

            @Override
            public int[] elementIds() {
              return arrayProperty.elementIds();
            }

            @Override
            public Class<? extends Annotation> type() {
              return arrayProperty.type();
            }
          };
          member = ArrayModel.referenceOrDeclare(registry, this, annotation, getMethod, superPropertySpec.fieldName);
        }
        else {
          throw new UnsupportedOperationException("Unsupported annotation type: " + superPropertySpec.annotation.annotationType().getName());
        }

        properties.put(superPropertySpec.propertyName, new Property(member));
      }
    }

    resolveProperties();
  }

  private PropertySpec getSuperObjectProperty(final Method method) {
    final String methodName = method.getName();
    for (ObjectModel parent = (ObjectModel)superObject; parent != null; parent = (ObjectModel)parent.superObject) { // [X]
      final PropertySpec annotation = parent.getMethodToPropertySpec.get(methodName);
      if (annotation != null)
        return annotation;
    }

    return null;
  }

  @Override
  Registry.Type typeDefault() {
    return classType();
  }

  @Override
  String isValid(final Binding.Type typeBinding) {
    return typeBinding.type == null ? null : "Cannot override the type for \"object\"";
  }

  @Override
  public String elementName() {
    return "object";
  }

  @Override
  public String displayName() {
    return name() != null && !name().isEmpty() ? name() : JsdUtil.flipName(id().toString());
  }

  @Override
  Class<?> defaultClass() {
    return JxObject.class;
  }

  @Override
  String sortKey() {
    return "z" + type().getName();
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return ObjectProperty.class;
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return ObjectElement.class;
  }

  private Boolean referencesResolved = false;

  @Override
  void resolveReferences() {
    if (referencesResolved != null && referencesResolved)
      return;

    referencesResolved = null;
    if (superObject instanceof Deferred)
      superObject = ((Deferred<?>)superObject).resolve();

    if (properties.size() > 0)
      for (final Map.Entry<String,Property> entry : properties.entrySet()) // [S]
        if (entry.getValue().member instanceof Deferred)
          entry.setValue(new Property(((Deferred<?>)entry.getValue().member).resolve()));

    referencesResolved = true;
  }

  @Override
  void getDeclaredTypes(final Set<? super Registry.Type> types) {
    types.add(type());
    if (superObject != null)
      superObject.getDeclaredTypes(types);

    if (properties != null && properties.size() > 0)
      for (final Property property : properties.values()) // [C]
        property.member.getDeclaredTypes(types);
  }

  @Override
  Map<String,Object> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toXmlAttributes(owner, packageName);
    attributes.put(owner instanceof ArrayModel ? "type" : "name", name() != null ? name() : JsdUtil.flipName(owner instanceof ObjectModel ? classType().getSubName(((ObjectModel)owner).type().getName()) : classType().getSubName(packageName)));

    if (superObject != null)
      attributes.put("extends", JsdUtil.flipName(superObject.type().getRelativeName(packageName)));

    if (isAbstract)
      attributes.put("abstract", isAbstract);

    return attributes;
  }

  @Override
  XmlElement toXml(final Settings settings, final Element owner, final String packageName) {
    final XmlElement element = super.toXml(settings, owner, packageName);
    final int i$;
    if (properties == null || (i$ = properties.size()) == 0)
      return element;

    final ArrayList<XmlElement> elements = new ArrayList<>(i$ + (element.getElements() != null ? element.getElements().size() : 0));
    for (final Property property : properties.values()) // [C]
      elements.add(property.toXml(settings, this, packageName));

    if (element.getElements() != null)
      elements.addAll(element.getElements());

    element.setElements(elements);
    return element;
  }

  @Override
  Map<String,Object> toJson(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Object> element = super.toJson(settings, owner, packageName);
    if (properties == null || properties.size() == 0)
      return element;

    final LinkedHashMap<String,Object> properties = new LinkedHashMap<>();
    for (final Property property : this.properties.values()) // [C]
      properties.put(property.member.name(), property.toJson(settings, this, packageName));

    element.put("properties", properties);
    return element;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (owner instanceof ArrayModel)
      attributes.put("type", classType().getCanonicalName() + ".class");
  }

  @Override
  ArrayList<AnnotationType> getClassAnnotation() {
    return null;
  }

  private int numNonOverrideMembers() {
    if (properties == null || properties.size() == 0)
      return 0;

    int count = 0;
    for (final Property property : properties.values()) // [C]
      if (property.getOverride() == null)
        ++count;

    return count;
  }

  @Override
  @SuppressWarnings("null")
  String toSource(final Settings settings) {
    final StringBuilder builder = new StringBuilder();
    final HashSet<String> overridden = superObject == null ? null : new HashSet<>();
    boolean appended = builder.length() > 0;
    if (properties != null && properties.size() > 0) {
      for (final Property property : properties.values()) { // [C]
        if (appended)
          builder.append("\n\n");

        if (superObject != null && property.getOverride() != null)
          overridden.add(property.getOverride().name());

        final int len = builder.length();
        builder.append(property.member.toField(classType(), property.getOverride(), settings.getSetBuilder(), ClassSpec.Scope.values()));
        appended = builder.length() > len;
      }
    }

    for (ObjectModel parent = (ObjectModel)superObject; parent != null; parent = (ObjectModel)parent.superObject) { // [X]
      final LinkedHashMap<String,Property> properties = parent.properties;
      if (properties != null && properties.size() > 0) {
        for (final Property property : properties.values()) { // [C]
          if (!overridden.contains(property.member.name())) {
            if (appended)
              builder.append("\n\n");

            final int len = builder.length();
            builder.append(property.member.toField(classType(), property.getOverride() != null ? property.getOverride() : property.member, settings.getSetBuilder(), ClassSpec.Scope.SET));
            appended = builder.length() > len;
          }
        }
      }
    }

    if (builder.length() > 0 && builder.charAt(builder.length() - 1) != '\n')
      builder.append("\n\n");

    final Registry.Type type = classType();
    builder.append('@').append(Override.class.getName());
    builder.append("\npublic boolean equals(final ").append(Object.class.getName()).append(" obj) {");
    builder.append("\n  if (obj == this)");
    builder.append("\n    return true;");
    builder.append("\n\n  if (!(obj instanceof ").append(type.getCanonicalName()).append(')');
    if (type.getSuperType() != null)
      builder.append(" || !super.equals(obj)");

    builder.append(")\n    return false;\n");

    final boolean hasMembers = numNonOverrideMembers() > 0;
    if (hasMembers) {
      builder.append("\n  final ").append(type.getCanonicalName()).append(" that = (").append(type.getCanonicalName()).append(")obj;");
      if (properties.size() > 0) {
        for (final Property property : properties.values()) { // [C]
          if (property.getOverride() != null)
            continue;

          final Member member = property.member;
          final boolean isOptionalType = member.use.get == Use.OPTIONAL && member.nullable.get == null;
          if (!isOptionalType && member.type().isArray())
            builder.append("\n  if (!").append(Arrays.class.getName()).append(".equals(").append(member.fieldBinding.instanceCase).append(", that.").append(member.fieldBinding.instanceCase).append(')');
          else if (!isOptionalType && member.type().isPrimitive())
            builder.append("\n  if (").append(member.fieldBinding.instanceCase).append(" != that.").append(member.fieldBinding.instanceCase);
          else
            builder.append("\n  if (!").append(ObjectUtil.class.getName()).append(".equals(").append(member.fieldBinding.instanceCase).append(", that.").append(member.fieldBinding.instanceCase).append(')');

          builder.append(")\n    return false;\n");
        }
      }
    }
    builder.append("\n  return true;");
    builder.append("\n}");

    builder.append("\n\n@").append(Override.class.getName());
    builder.append("\npublic int hashCode() {");
    if (hasMembers) {
      builder.append("\n  int hashCode = ").append(type.getName().hashCode()).append(type.getSuperType() != null ? " * 31 + super.hashCode()" : "").append(';');
      if (properties.size() > 0) {
        for (final Property property : properties.values()) { // [C]
          if (property.getOverride() != null)
            continue;

          final Member member = property.member;
          final boolean isPrimitive = member.type().isPrimitive();
          final boolean isOptionalType = member.use.get == Use.OPTIONAL && member.nullable.get == null;

          final String indent = !isPrimitive || member.type().isArray() ? "    " : "  ";
          final String header = "\n" + indent + "hashCode = 31 * hashCode + ";

          if (!isPrimitive || member.type().isArray())
            builder.append("\n  if (").append(member.fieldBinding.instanceCase).append(" != null)");

          builder.append(header);
          if (!isOptionalType && member.type().isArray())
            builder.append(Arrays.class.getName()).append(".hashCode(").append(member.fieldBinding.instanceCase).append(");");
          else if (!isOptionalType && isPrimitive)
            builder.append(member.type().getWrapper()).append(".hashCode(").append(member.fieldBinding.instanceCase).append(");");
          else
            builder.append(ObjectUtil.class.getName()).append(".hashCode(").append(member.fieldBinding.instanceCase).append(");");

          if (!isPrimitive || member.type().isArray())
            builder.append('\n');
        }
      }

      builder.append("\n  return hashCode;");
    }
    else {
      builder.append("\n  return ").append(type.getName().hashCode());
      if (type.getSuperType() != null)
        builder.append(" * 31 + super.hashCode()");

      builder.append(';');
    }
    builder.append("\n}");

    if (type.getSuperType() == null) {
      builder.append("\n\n@").append(Override.class.getName());
      builder.append("\npublic ").append(String.class.getName()).append(" toString() {");
      builder.append("\n  return ").append(JxEncoder.class.getName()).append(".get().toString(this);");
      builder.append("\n}");
    }

    return builder.toString();
  }
}