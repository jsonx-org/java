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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.jsonx.schema.Object._40properties;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$FieldBinding;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Any;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Boolean;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Number;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Object;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Reference;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$String;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.Schema;
import org.libj.lang.Classes;
import org.libj.lang.IllegalAnnotationException;
import org.libj.lang.ObjectUtil;
import org.libj.lang.Strings;
import org.libj.util.Iterators;
import org.openjax.xml.api.XmlElement;
import org.w3.www._2001.XMLSchema.yAA.$AnyType;

final class ObjectModel extends Referrer<ObjectModel> {
  private static Schema.Object type(final schema.ObjectType jsd, final String name) {
    final Schema.Object xsb = new Schema.Object();
    if (name != null)
      xsb.setName$(new Schema.Object.Name$(name));

    if (jsd.get40abstract() != null)
      xsb.setAbstract$(new Schema.Object.Abstract$(jsd.get40abstract()));

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

    final Boolean nullable = jsd.get40nullable();
    if (nullable != null)
      xsb.setNullable$(new $Object.Nullable$(nullable));

    final String use = jsd.get40use();
    if (use != null)
      xsb.setUse$(new $Object.Use$($Object.Use$.Enum.valueOf(use)));

    final String extends$ = jsd.get40extends();
    if (extends$ != null)
      xsb.setExtends$(new $ObjectMember.Extends$(extends$));

    return xsb;
  }

  static $ObjectMember jsdToXsb(final schema.Object jsd, final String name) {
    final $ObjectMember xsb;
    if (jsd instanceof schema.ObjectType)
      xsb = type((schema.ObjectType)jsd, name);
    else if (jsd instanceof schema.ObjectProperty)
      xsb = property((schema.ObjectProperty)jsd, name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    final String doc = jsd.get40doc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    final String extends$ = jsd.get40extends();
    if (extends$ != null)
      xsb.setExtends$(new $ObjectMember.Extends$(extends$));

    final _40properties properties$ = jsd.get40properties();
    if (properties$ != null) {
      final LinkedHashMap<String,? extends schema.Member> properties = properties$.get2e2a();
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

  static ObjectModel declare(final Registry registry, final Declarer declarer, final Schema.Object xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    return registry.declare(xsb).value(new ObjectModel(registry, declarer, xsb, xsbToBinding), null);
  }

  static ObjectModel declare(final Registry registry, final ObjectModel referrer, final $Object xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    return registry.declare(xsb).value(new ObjectModel(registry, referrer, xsb, xsbToBinding), referrer);
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
    if (!isAssignable(getMethod, true, false, property.nullable(), property.use(), true, JxObject.class))
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
    final StringBuilder b = new StringBuilder();
    $Object owner = xsb;
    do
      b.insert(0, '$').insert(1, JsdUtil.toIdentifier(Strings.flipFirstCap(owner.getName$().text())));
    while (owner.owner() instanceof $Object && (owner = ($Object)owner.owner()) != null);
    return b.insert(0, JsdUtil.toIdentifier(JsdUtil.flipName(((Schema.Object)owner.owner()).getName$().text()))).toString();
  }

  private static void checkJSObject(final Class<?> cls) {
    if (!JxObject.class.isAssignableFrom(cls))
      throw new IllegalArgumentException("Class " + cls.getName() + " does not implement " + JxObject.class.getName());
  }

  private class Property {
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

    private XmlElement toXml(final ObjectModel owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
      final XmlElement element = member.toXml(owner, packageName, cursor, pathToBinding);
      if (getOverride() != null) {
        final Map<?,?> attrs = element.getAttributes();
        attrs.remove("nullable");
        attrs.remove("use");
        attrs.remove("minOccurs");
        attrs.remove("maxOccurs");
      }

      return element;
    }

    @SuppressWarnings("unchecked")
    private Object toJson(final ObjectModel owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
      final PropertyMap<Object> object = (PropertyMap<Object>)member.toJson(owner, packageName, cursor, pathToBinding);
      if (getOverride() != null) {
        object.remove("@nullable");
        object.remove("@use");
        object.remove("@minOccurs");
        object.remove("@maxOccurs");
      }

      return object;
    }
  }

  private final LinkedHashMap<String,Property> properties;
  private Member superObject;
  final boolean isAbstract;

  private final Class<?> cls;
  private final Map<String,PropertySpec> getMethodToPropertySpec;

  private ObjectModel(final Registry registry, final Declarer declarer, final Schema.Object xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    super(registry, declarer, registry.getType(registry.packageName, registry.classBasePath + JsdUtil.flipName(xsb.getName$().text()), xsb.getExtends$() != null ? registry.classBasePath + JsdUtil.flipName(xsb.getExtends$().text()) : null), xsb.getDoc$(), xsb.getName$().text(), getType(registry, xsbToBinding, xsb));
    this.isAbstract = xsb.getAbstract$().text();
    this.superObject = getReference(xsb.getExtends$());
    this.properties = parseProperties(xsb, xsbToBinding);

    this.cls = null;
    this.getMethodToPropertySpec = null;

    validateTypeBinding();
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final Class<?> cls, final String fieldName, final ObjectProperty property) {
    this(registry, declarer, cls, property.nullable(), property.use(), fieldName);
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final ObjectElement element) {
    this(registry, declarer, element.type(), element.nullable(), null, null);
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final $Object xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    super(registry, declarer, xsb.getDoc$(), xsb.getName$(), xsb.getNullable$(), xsb.getUse$(), registry.getType(registry.packageName, registry.classBasePath + getFullyQualifiedName(xsb), xsb.getExtends$() != null ? registry.classBasePath + JsdUtil.flipName(xsb.getExtends$().text()) : null), getField(xsbToBinding, xsb), getType(registry, xsbToBinding, xsb));
    this.superObject = getReference(xsb.getExtends$());
    this.isAbstract = false;
    this.properties = parseProperties(xsb, xsbToBinding);

    this.cls = null;
    this.getMethodToPropertySpec = null;

    validateTypeBinding();
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final Class<?> cls, final Boolean nullable, final Use use, final String fieldName) {
    super(registry, declarer, nullable, use, fieldName, registry.getType(cls), getTypeBinding(registry, cls));
    final Class<?> superClass = cls.getSuperclass();
    this.isAbstract = Modifier.isAbstract(cls.getModifiers());
    this.superObject = superClass == null || !JxObject.class.isAssignableFrom(superClass) ? null : referenceOrDeclare(registry, declarer, superClass);
    this.properties = new LinkedHashMap<>(); // FIXME: Does this need to be a LinkedHashMap?

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

  private void recurseInnerClasses(final Registry registry, final Class<?> cls) {
    for (final Class<?> innerClass : cls.getClasses()) { // [A]
      if (!JxObject.class.isAssignableFrom(innerClass))
        recurseInnerClasses(registry, innerClass);
      else
        referenceOrDeclare(registry, this, innerClass);
    }
  }

  private LinkedHashMap<String,Property> parseProperties(final $ObjectMember xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    final LinkedHashMap<String,Member> members = parseMembers(xsb, this, xsbToBinding);
    if (members == null)
      return null;

    final int size = members.size();
    final LinkedHashMap<String,Property> properties = new LinkedHashMap<>(size); // FIXME: Does this need to be a LinkedHashMap?
    if (size > 0)
      for (final Map.Entry<String,Member> entry : members.entrySet()) // [S]
        properties.put(entry.getKey(), new Property(entry.getValue()));

    return properties;
  }

  private LinkedHashMap<String,Member> parseMembers(final $ObjectMember xsb, final ObjectModel objectModel, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    final LinkedHashMap<String,Member> members = new LinkedHashMap<>(); // FIXME: Does this need to be a LinkedHashMap?
    final Iterator<? super $Member> iterator = Iterators.filter(xsb.elementIterator(), (final $AnyType<?> m) -> m instanceof $Member);
    while (iterator.hasNext()) {
      final $Member next = ($Member)iterator.next();
      if (next instanceof $Any) {
        final $Any member = ($Any)next;
        members.put(member.getNames$().text(), AnyModel.reference(registry, objectModel, member, xsbToBinding));
      }
      else if (next instanceof $Array) {
        final $Array member = ($Array)next;
        members.put(member.getName$().text(), ArrayModel.reference(registry, objectModel, member, xsbToBinding));
      }
      else if (next instanceof $Boolean) {
        final $Boolean member = ($Boolean)next;
        members.put(member.getName$().text(), BooleanModel.reference(registry, objectModel, member, xsbToBinding));
      }
      else if (next instanceof $Number) {
        final $Number member = ($Number)next;
        members.put(member.getName$().text(), NumberModel.reference(registry, objectModel, member, xsbToBinding));
      }
      else if (next instanceof $Object) {
        final $Object member = ($Object)next;
        members.put(member.getName$().text(), declare(registry, objectModel, member, xsbToBinding));
      }
      else if (next instanceof $Reference) {
        final $Reference member = ($Reference)next;
        final Id id = Id.named(member.getType$());
        final Member child = registry.getModel(id);
        members.put(member.getName$().text(), child instanceof Reference ? child : Reference.defer(registry, objectModel, member, xsbToBinding == null ? null : xsbToBinding.get(member), () -> registry.reference(registry.getModel(id), objectModel)));
      }
      else if (next instanceof $String) {
        final $String member = ($String)next;
        members.put(member.getName$().text(), StringModel.reference(registry, objectModel, member, xsbToBinding));
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + next.getClass().getSimpleName() + " member type: " + next.getClass().getName());
      }
    }

    return members;
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
        else
          throw new ValidationException("Object " + (name() != null ? name() : JsdUtil.flipName(id().toString())) + "." + entry.getKey() + " overrides " + property.member.name() + "." + entry.getKey() + " with incompatible type");
      }
    }
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
      try {
        Classes.sortDeclarativeOrder(methods, true);
      }
      catch (final ClassNotFoundException e) {
        System.err.println("Cannot sort methods in declarative order because Javassist is not present on the system classpath");
      }
    }
  };

  private static Bind.Type getTypeBinding(final Registry registry, final Class<?> cls) {
    final Class<?>[] interfaces = cls.getInterfaces();
    final int length = interfaces.length;
    if (length == 0)
      return null;

    final Class<?> superClass = cls.getSuperclass();
    if (superClass == null || Object.class.equals(superClass))
      return null;

    final Class<?> interface0 = interfaces[0];
    if (interface0 != JxObject.class)
      return Bind.Type.from(registry, interface0, null, null);

    return Bind.Type.from(registry, superClass, null, null);
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
        final Annotation superPropertySpecAnnotation = superPropertySpec.annotation;
        if (superPropertySpecAnnotation instanceof ObjectProperty) {
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
        else if (superPropertySpecAnnotation instanceof ArrayProperty) {
          // Create a default `ArrayProperty`, and just match the elementIds() and type()
          final ArrayProperty arrayProperty = (ArrayProperty)superPropertySpecAnnotation;
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
          throw new UnsupportedOperationException("Unsupported annotation type: " + superPropertySpecAnnotation.annotationType().getName());
        }

        properties.put(superPropertySpec.propertyName, new Property(member));
      }
    }

    resolveProperties();
  }

  private PropertySpec getSuperObjectProperty(final Method method) {
    final String methodName = method.getName();
    for (ObjectModel parent = (ObjectModel)superObject; parent != null; parent = (ObjectModel)parent.superObject) { // [X]
      final PropertySpec propertySpec = parent.getMethodToPropertySpec.get(methodName);
      if (propertySpec != null)
        return propertySpec;
    }

    return null;
  }

  @Override
  Registry.Type type() {
    return classType();
  }

  @Override
  Registry.Type typeDefault(final boolean primitive) {
    return classType();
  }

  @Override
  String isValid(final Bind.Type typeBinding) {
    return null;
  }

  @Override
  public String elementName() {
    return "object";
  }

  @Override
  public String displayName() {
    return name() != null && name().length() > 0 ? name() : JsdUtil.flipName(id().toString());
  }

  @Override
  Class<?> defaultClass() {
    return Object.class;
  }

  @Override
  String sortKey() {
    return "z" + type().name;
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

    if (properties.size() > 0) {
      for (final Map.Entry<String,Property> entry : properties.entrySet()) { // [S]
        final Member member = entry.getValue().member;
        if (member instanceof Deferred)
          entry.setValue(new Property(((Deferred<?>)member).resolve()));
      }
    }

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
  @SuppressWarnings({"rawtypes", "unchecked"})
  XmlElement toXml(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
    final XmlElement element = super.toXml(owner, packageName, cursor, pathToBinding);
    final int size;
    if (properties != null && (size = properties.size()) > 0) {
      final Collection superElements = element.getElements();
      final ArrayList<XmlElement> elements = new ArrayList<>(size + (superElements != null ? superElements.size() : 0));
      for (final Property property : properties.values()) // [C]
        elements.add(property.toXml(this, packageName, cursor, pathToBinding));

      if (superElements != null)
        elements.addAll(superElements);

      element.setElements(elements);
    }

    cursor.popName();
    return element;
  }

  @Override
  PropertyMap<Object> toJson(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
    final PropertyMap<Object> element = super.toJson(owner, packageName, cursor, pathToBinding);
    final int size;
    if (properties != null && (size = properties.size()) > 0) {
      final PropertyMap<Object> properties = new PropertyMap<>(size);
      element.put("@properties", properties);
      for (final Property property : this.properties.values()) // [C]
        properties.put(property.member.name(), property.toJson(this, packageName, cursor, pathToBinding));
    }

    cursor.popName();
    return element;
  }

  @Override
  AttributeMap toSchemaAttributes(final Element owner, final String packageName, final boolean jsd) {
    final AttributeMap attributes = super.toSchemaAttributes(owner, packageName, jsd);
    final String name = name();
    attributes.put(owner instanceof ArrayModel ? "type" : "name", name != null ? name : JsdUtil.flipName(owner instanceof ObjectModel ? classType().getSubName(((ObjectModel)owner).type().name) : classType().getSubName(packageName)));

    if (superObject != null)
      attributes.put(jsd(jsd, "extends"), JsdUtil.flipName(superObject.type().getRelativeName(packageName)));

    if (isAbstract)
      attributes.put(jsd(jsd, "abstract"), isAbstract);

    return attributes;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (owner instanceof ArrayModel)
      attributes.put("type", classType().canonicalName + ".class");
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
    final StringBuilder b = new StringBuilder();
    final HashSet<String> overridden = superObject == null ? null : new HashSet<>();
    boolean appended = false;
    final Registry.Type classType = classType();
    final boolean setBuilder = settings.getSetBuilder();
    if (properties != null && properties.size() > 0) {
      for (final Property property : properties.values()) { // [C]
        if (appended)
          b.append("\n\n");

        final Member override = property.getOverride();
        if (superObject != null && override != null)
          overridden.add(override.name());

        final int len = b.length();
        b.append(property.member.toField(classType, override, setBuilder, ClassSpec.Scope.values));
        appended = b.length() > len;
      }
    }

    for (ObjectModel parent = (ObjectModel)superObject; parent != null; parent = (ObjectModel)parent.superObject) { // [X]
      final LinkedHashMap<String,Property> properties = parent.properties;
      if (properties != null && properties.size() > 0) {
        for (final Property property : properties.values()) { // [C]
          final Member member = property.member;
          if (!overridden.contains(member.name())) {
            if (appended)
              b.append("\n\n");

            final int len = b.length();
            final Member override = property.getOverride();
            b.append(member.toField(classType, override != null ? override : member, setBuilder, ClassSpec.Scope.SET));
            appended = b.length() > len;
          }
        }
      }
    }

    if (b.length() > 0 && b.charAt(b.length() - 1) != '\n')
      b.append("\n\n");

    b.append('@').append(Override.class.getName());
    b.append("\npublic boolean equals(final ").append(Object.class.getName()).append(" obj) {");
    b.append("\n  if (obj == this)");
    b.append("\n    return true;");
    b.append("\n\n  if (!(obj instanceof ").append(classType.canonicalName).append(')');
    final boolean hasJxSuperType = classType.hasJxSuperType();
    if (hasJxSuperType)
      b.append(" || !super.equals(obj)");

    b.append(")\n    return false;\n");

    final boolean hasMembers = numNonOverrideMembers() > 0;
    if (hasMembers) {
      b.append("\n  final ").append(classType.canonicalName).append(" that = (").append(classType.canonicalName).append(")obj;");
      if (properties.size() > 0) {
        for (final Property property : properties.values()) { // [C]
          if (property.getOverride() != null)
            continue;

          final Member member = property.member;
          final boolean isOptionalType = member.use.get == Use.OPTIONAL && member.nullable.get == null;
          final Registry.Type memberType = member.type();
          final String instanceCase = member.fieldBinding.instanceCase;
          if (!isOptionalType && memberType.isArray)
            b.append("\n  if (!").append(Arrays.class.getName()).append(".equals(").append(instanceCase).append(", that.").append(instanceCase).append(')');
          else if (!isOptionalType && memberType.isPrimitive)
            b.append("\n  if (").append(instanceCase).append(" != that.").append(instanceCase);
          else
            b.append("\n  if (!").append(ObjectUtil.class.getName()).append(".equals(").append(instanceCase).append(", that.").append(instanceCase).append(')');

          b.append(")\n    return false;\n");
        }
      }
    }
    b.append("\n  return true;");
    b.append("\n}");

    b.append("\n\n@").append(Override.class.getName());
    b.append("\npublic int hashCode() {");
    if (hasMembers) {
      b.append("\n  int hashCode = ").append(classType.name.hashCode()).append(hasJxSuperType ? " * 31 + super.hashCode()" : "").append(';');
      if (properties.size() > 0) {
        for (final Property property : properties.values()) { // [C]
          if (property.getOverride() != null)
            continue;

          final Member member = property.member;
          final Registry.Type memberType = member.type();
          final boolean isPrimitive = memberType.isPrimitive;
          final boolean isOptionalType = member.use.get == Use.OPTIONAL && member.nullable.get == null;

          final boolean isArray = memberType.isArray;
          final String indent = !isPrimitive || isArray ? "    " : "  ";
          final String header = "\n" + indent + "hashCode = 31 * hashCode + ";

          final String instanceCase = member.fieldBinding.instanceCase;
          if (!isPrimitive || isArray)
            b.append("\n  if (").append(instanceCase).append(" != null)");

          b.append(header);
          if (!isOptionalType && isArray)
            b.append(Arrays.class.getName()).append(".hashCode(").append(instanceCase).append(");");
          else if (!isOptionalType && isPrimitive)
            b.append(memberType.wrapper).append(".hashCode(").append(instanceCase).append(");");
          else
            b.append(ObjectUtil.class.getName()).append(".hashCode(").append(instanceCase).append(");");

          if (!isPrimitive || isArray)
            b.append('\n');
        }
      }

      b.append("\n  return hashCode;");
    }
    else {
      b.append("\n  return ").append(classType.name.hashCode());
      if (hasJxSuperType)
        b.append(" * 31 + super.hashCode()");

      b.append(';');
    }
    b.append("\n}");

    if (!hasJxSuperType) {
      b.append("\n\n@").append(Override.class.getName());
      b.append("\npublic ").append(String.class.getName()).append(" toString() {");
      b.append("\n  return ").append(JxEncoder.class.getName()).append(".get().toString(this);");
      b.append("\n}");
    }

    return b.toString();
  }
}