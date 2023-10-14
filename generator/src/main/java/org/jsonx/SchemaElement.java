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

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Generated;
import javax.xml.namespace.QName;

import org.jaxsb.runtime.BindingList;
import org.jsonx.binding.TypeFieldBinding;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$FieldBinding;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$FieldBindings;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$Path;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$TypeFieldBinding;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$TypeFieldBindings;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.Binding;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.Schema;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.Schema.TargetNamespace$;
import org.libj.lang.PackageLoader;
import org.libj.lang.PackageNotFoundException;
import org.libj.lang.WrappedArrayList;
import org.libj.util.Iterators;
import org.openjax.json.JsonReader;
import org.openjax.xml.api.CharacterDatas;
import org.openjax.xml.api.XmlElement;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;
import org.w3.www._2001.XMLSchema.yAA.$AnyType;

/**
 * The root {@link Element} of a JSON Schema Document.
 */
public final class SchemaElement extends Element implements Declarer {
  private static final String GENERATED = "(value = \"" + Generator.class.getName() + "\", date = \"" + LocalDateTime.now().toString() + "\")";
  private static final Id ID = Id.named(SchemaElement.class);

  private static <T extends $FieldBindings> T jsdToXsbField(final T xsb, final String path, final Map<String,binding.FieldBinding> bindings) {
    xsb.setPath$(path);
    if (bindings.size() > 0) {
      for (final Map.Entry<String,binding.FieldBinding> entry : bindings.entrySet()) { // [S]
        final $FieldBindings.Bind bind = new $FieldBindings.Bind();
        bind.setLang$(entry.getKey());
        bind.setField$(entry.getValue().get40field());
        xsb.addBind(bind);
      }
    }

    return xsb;
  }

  private static <T extends $TypeFieldBindings> T jsdToXsbTypeField(final T xsb, final String path, final Map<String,binding.TypeFieldBinding> bindings) {
    xsb.setPath$(path);
    if (bindings.size() > 0) {
      for (final Map.Entry<String,binding.TypeFieldBinding> entry : bindings.entrySet()) { // [S]
        final $TypeFieldBindings.Bind bind = new $TypeFieldBindings.Bind();
        bind.setLang$(entry.getKey());
        final TypeFieldBinding value = entry.getValue();
        bind.setField$(value.get40field());
        bind.setDecode$(value.get40decode());
        bind.setEncode$(value.get40encode());
        bind.setType$(value.get40type());
        xsb.addBind(bind);
      }
    }

    return xsb;
  }

  static $AnyType<?> jxToXsb(final URL location, final JxObject jsb) throws DecodeException, IOException {
    if (jsb instanceof schema.Schema)
      return jxToXsb((schema.Schema)jsb);

    if (jsb instanceof binding.Binding)
      return jxToXsb(location, (binding.Binding)jsb);

    throw new IllegalArgumentException("Unsupported object of class: " + jsb.getClass().getName());
  }

  private static Binding jxToXsb(final URL location, final binding.Binding jsb) throws DecodeException, IOException {
    final Binding xsb = new Binding();
    final JxObject jx = jsb.get40schema();
    final schema.Schema schema;
    if (jx instanceof schema.Schema) {
      schema = (schema.Schema)jx;
    }
    else if (jx instanceof include.Include) {
      final include.Include include = (include.Include)jx;
      final URL url = Generator.getLocation(location, include.getHref());
      try (final JsonReader in = new JsonReader(new InputStreamReader(url.openStream()))) {
        schema = JxDecoder.VALIDATING.parseObject(in, schema.Schema.class);
      }
    }
    else {
      throw new ValidationException("Unknown member type: " + jx.getClass().getName());
    }

    xsb.setJxSchema(jxToXsb(schema));

    final LinkedHashMap<String,JxObject> bindings = jsb.get5cS7c5cS2e2a5cS();
    if (bindings.size() > 0) {
      for (final Map.Entry<String,JxObject> entry : bindings.entrySet()) { // [S]
        final String path = CharacterDatas.escapeForAttr(entry.getKey().replace("\\\\", "\\"), '"').toString();
        final JxObject member = entry.getValue();
        if (member instanceof binding.Any)
          xsb.addAny(jsdToXsbField(new Binding.Any(), path, ((binding.FieldBindings)member).get5cS7c5cS2e2a5cS()));
        else if (member instanceof binding.Reference)
          xsb.addReference(jsdToXsbField(new Binding.Reference(), path, ((binding.FieldBindings)member).get5cS7c5cS2e2a5cS()));
        else if (member instanceof binding.Array)
          xsb.addArray(jsdToXsbField(new Binding.Array(), path, ((binding.FieldBindings)member).get5cS7c5cS2e2a5cS()));
        else if (member instanceof binding.Object)
          xsb.addObject(jsdToXsbField(new Binding.Object(), path, ((binding.FieldBindings)member).get5cS7c5cS2e2a5cS()));
        else if (member instanceof binding.Boolean)
          xsb.addBoolean(jsdToXsbTypeField(new Binding.Boolean(), path, ((binding.TypeFieldBindings)member).get5cS7c5cS2e2a5cS()));
        else if (member instanceof binding.Number)
          xsb.addNumber(jsdToXsbTypeField(new Binding.Number(), path, ((binding.TypeFieldBindings)member).get5cS7c5cS2e2a5cS()));
        else if (member instanceof binding.String)
          xsb.addString(jsdToXsbTypeField(new Binding.String(), path, ((binding.TypeFieldBindings)member).get5cS7c5cS2e2a5cS()));
        else
          throw new UnsupportedOperationException("Unsupported type: " + member.getClass().getName());
      }
    }

    return xsb;
  }

  private static Schema jxToXsb(final schema.Schema jsd) {
    final Schema xsb = new Schema();
    final LinkedHashMap<String,? extends schema.Member> declarations = jsd.get5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a();
    if (declarations != null && declarations.size() > 0) {
      for (final Map.Entry<String,? extends schema.Member> entry : declarations.entrySet()) { // [S]
        final String name = entry.getKey();
        final schema.Member declaration = entry.getValue();
        if (declaration instanceof schema.Array)
          xsb.addArray((Schema.Array)ArrayModel.jsdToXsb((schema.Array)declaration, name));
        else if (declaration instanceof schema.Boolean)
          xsb.addBoolean((Schema.Boolean)BooleanModel.jsdToXsb((schema.Boolean)declaration, name));
        else if (declaration instanceof schema.Number)
          xsb.addNumber((Schema.Number)NumberModel.jsdToXsb((schema.Number)declaration, name));
        else if (declaration instanceof schema.ObjectType)
          xsb.addObject((Schema.Object)ObjectModel.jsdToXsb((schema.Object)declaration, name));
        else if (declaration instanceof schema.String)
          xsb.addString((Schema.String)StringModel.jsdToXsb((schema.String)declaration, name));
        else
          throw new UnsupportedOperationException("Unsupported type: " + declaration.getClass().getName());
      }
    }

    final String targetNamespace = jsd.get40targetNamespace();
    if (targetNamespace != null && targetNamespace.length() > 0)
      xsb.setTargetNamespace$(targetNamespace);

    final String doc = jsd.get40doc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    return xsb;
  }

  private static IdentityHashMap<$AnyType<?>,$FieldBinding> initBindingMap(final Schema schema, final Binding binding) {
    final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding = new IdentityHashMap<>();
    final Iterator<$AnyType<?>> iterator = binding.elementIterator();
    iterator.next(); // Skip schema element
    while (iterator.hasNext()) {
      final $Path bindings = ($Path)iterator.next();
      final $Documented element = new JsonPath(bindings.getPath$().text()).resolve(schema);
      if (bindings instanceof $FieldBindings) {
        final BindingList<$FieldBinding> b = (($FieldBindings)bindings).getBind();
        for (int i = 0, i$ = b.size(); i < i$; ++i) { // [RA]
          final $FieldBinding fieldBinding = b.get(i);
          if ("java".equals(fieldBinding.getLang$().text())) {
            if (xsbToBinding.put(element, fieldBinding) != null)
              throw new IllegalStateException();

            break;
          }
        }
      }
      else if (bindings instanceof $TypeFieldBindings) {
        final BindingList<$TypeFieldBinding> b = (($TypeFieldBindings)bindings).getBind();
        for (int i = 0, i$ = b.size(); i < i$; ++i) { // [RA]
          final $FieldBinding fieldBinding = b.get(i);
          if ("java".equals(fieldBinding.getLang$().text())) {
            if (xsbToBinding.put(element, fieldBinding) != null)
              throw new IllegalStateException();

            break;
          }
        }
      }
      else {
        throw new ValidationException("Unexpected element type: " + bindings.name());
      }
    }

    return xsbToBinding;
  }

  private static HashMap<String,String> initPrefixNamespaceMap(final Iterator<$AnySimpleType<?>> attributes) {
    final HashMap<String,String> prefixToNamespace = new HashMap<>();
    while (attributes.hasNext()) {
      final $AnySimpleType<?> attribute = attributes.next();
      final QName name = attribute.name();
      if ("jxns".equals(name.getPrefix()))
        prefixToNamespace.put(name.getLocalPart(), attribute.text().toString());
    }

    return prefixToNamespace;
  }

  private static String getName(final $Member obj) {
    if (obj instanceof Schema.Array)
      return ((Schema.Array)obj).getName$().text();

    if (obj instanceof Schema.Boolean)
      return ((Schema.Boolean)obj).getName$().text();

    if (obj instanceof Schema.Number)
      return ((Schema.Number)obj).getName$().text();

    if (obj instanceof Schema.String)
      return ((Schema.String)obj).getName$().text();

    if (obj instanceof Schema.Object)
      return ((Schema.Object)obj).getName$().text();

    throw new UnsupportedOperationException("Unsupported member type: " + obj.getClass().getName());
  };

  private static void assertNoCycle(final Schema schema) throws ValidationException {
    final StrictRefDigraph<$Member,String> digraph = new StrictRefDigraph<>("Object cannot inherit from itself", SchemaElement::getName);

    final Iterator<? super $Member> elementIterator = Iterators.filter(schema.elementIterator(), m -> m instanceof $Member);
    while (elementIterator.hasNext()) {
      final $Member member = ($Member)elementIterator.next();
      if (member instanceof Schema.Object) {
        final Schema.Object object = (Schema.Object)member;
        final $ObjectMember.Extends$ extends$ = object.getExtends$();
        if (extends$ != null)
          digraph.add(object, extends$.text());
        else
          digraph.add(object);
      }
      else {
        digraph.add(member);
      }
    }

    final List<$Member> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Cycle detected in object hierarchy: " + cycle.stream().map(SchemaElement::getName).collect(Collectors.joining(" -> ")));
  }

  static SchemaElement parse(final HashMap<String,Registry> namespaceToRegistry, final URL location, final $AnyType<?> obj, final Settings settings) {
    return new SchemaElement(namespaceToRegistry, location, obj, settings);
  }

  static SchemaElement parse(final HashMap<String,Registry> namespaceToRegistry, final URL location, final JxObject obj, final Settings settings) throws DecodeException, IOException, ValidationException {
    return new SchemaElement(namespaceToRegistry, location, obj, settings);
  }

  private static HashSet<Class<?>> findClasses(final Package pkg, final ClassLoader classLoader, final Predicate<? super Class<?>> filter) throws IOException, PackageNotFoundException {
    final HashSet<Class<?>> classes = new HashSet<>();
    PackageLoader.getPackageLoader(classLoader).loadPackage(pkg, c -> {
      if ((JxObject.class.isAssignableFrom(c) || c.isAnnotationPresent(ArrayType.class)) && (filter == null || filter.test(c))) {
        classes.add(c);
        return true;
      }

      return false;
    });

    return classes;
  }

  private final URL location;
  private final Registry registry;
  private final String version;

  /**
   * Creates a new {@link SchemaElement} from the specified XML binding.
   *
   * @param namespaceToRegistry Mapping of namespace to {@link Registry}.
   * @param location The {@link URL} of the resource providing the contents of the document.
   * @param object JSDx or JSBx as a JAX-SB object.
   * @param settings The {@link Settings} to be used for the parsed {@link SchemaElement}.
   * @throws DecodeException If a decode error has occurred.
   * @throws IOException If an I/O error has occurred.
   * @throws ValidationException If a cycle is detected in the object hierarchy.
   * @throws NullPointerException If {@code schema} or {@code settings} is null.
   */
  SchemaElement(final HashMap<String,Registry> namespaceToRegistry, final URL location, final JxObject object, final Settings settings) throws DecodeException, IOException, ValidationException {
    this(namespaceToRegistry, location, object instanceof schema.Schema ? jxToXsb((schema.Schema)object) : null, object instanceof binding.Binding ? jxToXsb(location, (binding.Binding)object) : null, settings);
  }

  /**
   * Creates a new {@link SchemaElement} from the specified XML binding.
   *
   * @param namespaceToRegistry Mapping of namespace to {@link Registry}.
   * @param location The {@link URL} of the resource providing the contents of the document.
   * @param object JSDx or JSBx as a JAX-SB object.
   * @param settings The {@link Settings} to be used for the parsed {@link SchemaElement}.
   * @throws ValidationException If a cycle is detected in the object hierarchy.
   * @throws NullPointerException If {@code schema} or {@code settings} is null.
   */
  SchemaElement(final HashMap<String,Registry> namespaceToRegistry, final URL location, final $AnyType<?> object, final Settings settings) {
    this(namespaceToRegistry, location, object instanceof Schema ? (Schema)object : null, object instanceof Binding ? (Binding)object : null, settings);
  }

  /**
   * Creates a new {@link SchemaElement} by scanning the specified package in the provided class loader, filtered with the given class
   * predicate.
   *
   * @param namespaceToRegistry Mapping of namespace to {@link Registry}.
   * @param pkg The package to be scanned for JSD bindings.
   * @param classLoader The {@link ClassLoader} containing the defined package.
   * @param filter The class {@link Predicate} allowing filtration of scanned classes.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   * @throws NullPointerException If {@code pkg} or {@code settings} is null.
   */
  SchemaElement(final HashMap<String,Registry> namespaceToRegistry, final Package pkg, final ClassLoader classLoader, final Predicate<? super Class<?>> filter) throws IOException, PackageNotFoundException {
    this(namespaceToRegistry, findClasses(pkg, classLoader, filter));
  }

  private SchemaElement(final HashMap<String,Registry> namespaceToRegistry, final URL location, Schema schema, final Binding binding, final Settings settings) throws ValidationException {
    super(null, (schema == null ? schema = binding.getJxSchema() : schema).getDoc$());
    this.location = location;
    final TargetNamespace$ targetNamespace = schema.getTargetNamespace$();
    this.registry = new Registry(namespaceToRegistry, initPrefixNamespaceMap(binding != null ? binding.attributeIterator() : schema.attributeIterator()), targetNamespace == null ? "" : targetNamespace.text(), settings);
    final String namespaceURI = schema.name().getNamespaceURI();
    this.version = namespaceURI.substring(namespaceURI.lastIndexOf('-') + 1, namespaceURI.lastIndexOf('.'));

    assertNoCycle(schema);

    final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding = binding == null ? null : initBindingMap(schema, binding);
    final Iterator<? super $Member> elementIterator = Iterators.filter(schema.elementIterator(), m -> m instanceof $Member);
    while (elementIterator.hasNext()) {
      final $Member member = ($Member)elementIterator.next();
      if (member instanceof Schema.Array)
        ArrayModel.declare(registry, this, (Schema.Array)member, xsbToBinding);
      else if (member instanceof Schema.Boolean)
        BooleanModel.declare(registry, this, (Schema.Boolean)member, xsbToBinding);
      else if (member instanceof Schema.Number)
        NumberModel.declare(registry, this, (Schema.Number)member, xsbToBinding);
      else if (member instanceof Schema.String)
        StringModel.declare(registry, this, (Schema.String)member, xsbToBinding);
      else if (member instanceof Schema.Object)
        ObjectModel.declare(registry, this, (Schema.Object)member, xsbToBinding);
      else
        throw new UnsupportedOperationException("Unsupported member type: " + member.getClass().getName());
    }

    final Collection<Model> models = registry.getModels();
    final int size = models.size();
    if (size > 0)
      for (final Model model : models) // [C]
        if (model instanceof Referrer)
          ((Referrer<?>)model).resolveReferences();

    registry.resolveReferences();

    if (size > 0)
      for (final Model model : models) // [C]
        if (model instanceof Referrer)
          ((Referrer<?>)model).resolveOverrides();
  }

  /**
   * Creates a new {@link SchemaElement} by scanning the provided classes.
   *
   * @param classes The classes to scan.
   */
  private SchemaElement(final HashMap<String,Registry> namespaceToRegistry, final HashSet<Class<?>> classes) {
    super(null, null);
    this.location = null;
    this.registry = new Registry(namespaceToRegistry, this, classes);
    namespaceToRegistry.put(registry.targetNamespace, registry);
    final org.jaxsb.runtime.QName name = Schema.class.getAnnotation(org.jaxsb.runtime.QName.class);
    final String namespaceURI = name.namespaceURI();
    this.version = namespaceURI.substring(namespaceURI.lastIndexOf('-') + 1, name.namespaceURI().lastIndexOf('.'));

    this.registry.resolveReferences();
    final Collection<Model> models = registry.getModels();
    final int len = models.size();
    final Model[] array = models.toArray(new Model[len]);
    for (int i = 0; i < len; ++i) // [A]
      if (array[i] instanceof Referrer)
        ((Referrer<?>)array[i]).resolveOverrides();

    this.registry.resolveReferences();
  }

  private ArrayList<Model> rootMembers() {
    final Collection<Model> models = registry.getModels();
    if (models.size() == 0)
      return WrappedArrayList.EMPTY_LIST;

    final ArrayList<Model> members = new ArrayList<>();
    for (final Model model : models) // [C]
      if (registry.isRootMember(model))
        members.add(model);

    if (!registry.isFromJsd)
      members.sort(Comparator.naturalOrder());

    return members;
  }

  @Override
  public Id id() {
    return ID;
  }

  URL getLocation() {
    return location;
  }

  @Override
  XmlElement toXml(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
    final ArrayList<XmlElement> schemaElems;
    final ArrayList<Model> members = rootMembers();
    final int i$ = members.size();
    if (i$ > 0) {
      schemaElems = new ArrayList<>();
      int i = 0;
      do // [RA]
        schemaElems.add(members.get(i).toXml(this, packageName, cursor, pathToBinding));
      while (++i < i$);
    }
    else {
      schemaElems = null;
    }

    final Map<String,Object> schemaAttrs = super.toSchemaAttributes(owner, packageName, false);
    schemaAttrs.put("xmlns", "http://www.jsonx.org/schema-" + version + ".xsd");
    schemaAttrs.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    schemaAttrs.put("xsi:schemaLocation", "http://www.jsonx.org/schema-" + version + ".xsd http://www.jsonx.org/schema-" + version + ".xsd");
    final String targetNamespace = registry.targetNamespace;
    if (targetNamespace != null)
      schemaAttrs.put("targetNamespace", targetNamespace.endsWith(".jsd") ? targetNamespace + "x" : targetNamespace);

    final int noBindings = pathToBinding.size();
    final XmlElement schema = new XmlElement("schema", schemaAttrs, schemaElems);
    if (noBindings == 0)
      return schema;

    final PropertyMap<Object> bindingAttrs = new PropertyMap<>();
    bindingAttrs.put("xmlns", "http://www.jsonx.org/binding-" + version + ".xsd");

    schemaAttrs.remove("xmlns:xsi");
    bindingAttrs.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

    schemaAttrs.remove("xsi:schemaLocation");
    bindingAttrs.put("xsi:schemaLocation", "http://www.jsonx.org/binding-" + version + ".xsd http://www.jsonx.org/binding-" + version + ".xsd");

    final ArrayList<XmlElement> bindingElems = new ArrayList<>(noBindings + 1);
    bindingElems.add(schema);

    for (final Map.Entry<String,AttributeMap> entry : pathToBinding.entrySet()) { // [S]
      final PropertyMap<Object> bind = new PropertyMap<>(entry.getValue());
      bindingElems.add(new XmlElement((String)bind.remove("@"), Collections.singletonMap("path", CharacterDatas.escapeForAttr(entry.getKey(), '"').toString()), Collections.singletonList(new XmlElement("bind", bind, null))));
    }

    return new XmlElement("binding", bindingAttrs, bindingElems);
  }

  public XmlElement toXml() {
    return toXml(this, registry.packageName, new JsonPath.Cursor(), new PropertyMap<>());
  }

  @Override
  PropertyMap<Object> toJson(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
    final List<Model> members = rootMembers();
    final int size = members.size();
    if (size == 0)
      return null;

    final PropertyMap<Object> schema = new PropertyMap<>();
    schema.put("@ns", "http://www.jsonx.org/schema-" + version + ".jsd");
    schema.put("@schemaLocation", "http://www.jsonx.org/schema-" + version + ".jsd http://www.jsonx.org/schema-" + version + ".jsd");
    schema.putAll(toSchemaAttributes(owner, packageName, true));
    final String targetNamespace = registry.targetNamespace;
    if (targetNamespace != null)
      schema.put("@targetNamespace", targetNamespace.endsWith(".jsdx") ? targetNamespace.substring(0, targetNamespace.length() - 1) : targetNamespace);

    final int len = packageName.length();
    for (int i = 0; i < size; ++i) { // [RA]
      final Model member = members.get(i);
      final String id = member.id().toString();
      final String name = len > 0 && id.startsWith(packageName) ? id.substring(len + 1) : id;
      schema.put(name, member.toJson(this, packageName, cursor, pathToBinding));
    }

    if (pathToBinding.size() == 0)
      return schema;

    schema.remove("@schemaLocation");
    final PropertyMap<Object> binding = new PropertyMap<>();
    binding.put("@ns", "http://www.jsonx.org/binding-" + version + ".jsd");
    binding.put("@schemaLocation", "http://www.jsonx.org/binding-" + version + ".jsd http://www.jsonx.org/binding-" + version + ".jsd");
    binding.put("@schema", schema);
    for (final Map.Entry<String,AttributeMap> entry : pathToBinding.entrySet()) { // [S]
      final PropertyMap<Object> bind = new PropertyMap<>(entry.getValue());
      final PropertyMap<Object> value = new PropertyMap<>();
      value.put("@", bind.remove("@"));
      value.put((String)bind.remove("@lang"), bind);
      binding.put(entry.getKey(), value);
    }

    return binding;
  }

  public PropertyMap<Object> toJson() {
    return toJson(this, registry.packageName, new JsonPath.Cursor(), new PropertyMap<>());
  }

  private static void addParents(final Registry.Type type, final ClassSpec classSpec, final Map<Registry.Type,ClassSpec> typeToJavaClass, final Map<Registry.Type,ClassSpec> all) {
    final Registry.Type declaringType = type.getDeclaringType();
    if (declaringType == null) {
      typeToJavaClass.put(type, classSpec);
      return;
    }

    ClassSpec parent = all.get(declaringType);
    if (parent == null) {
      parent = new ClassSpec(classSpec, declaringType);
      addParents(declaringType, parent, typeToJavaClass, all);
      all.put(declaringType, parent);
    }

    parent.add(classSpec);
  }

  public Map<String,String> toSource() {
    final HashMap<Registry.Type,ClassSpec> all = new HashMap<>();
    final HashMap<Registry.Type,ClassSpec> typeToJavaClass = new HashMap<>();
    final Collection<Model> models = registry.getModels();
    if (models.size() > 0) {
      for (final Model member : models) { // [C]
        final Referrer<?> referrer;
        final Registry.Type type;
        if (member instanceof Referrer && (type = (referrer = (Referrer<?>)member).classType()) != null) {
          final ClassSpec classSpec = new ClassSpec(referrer);
          addParents(type, classSpec, typeToJavaClass, all);
          all.put(type, classSpec);
        }
      }
    }

    final HashMap<String,String> sources = new HashMap<>();
    final StringBuilder b = new StringBuilder();
    if (typeToJavaClass.size() > 0) {
      for (final Map.Entry<Registry.Type,ClassSpec> entry : typeToJavaClass.entrySet()) { // [S]
        final Registry.Type type = entry.getKey();
        final ClassSpec classSpec = entry.getValue();
        final String canonicalPackageName = type.getCanonicalPackage();
        if (canonicalPackageName != null)
          b.append("package ").append(canonicalPackageName).append(";\n");

        final StringBuilder annotation = classSpec.getAnnotation();
        if (annotation != null)
          b.append('\n').append(annotation);

        final String doc = classSpec.getDoc();
        if (doc != null)
          b.append('\n').append(doc);

        if (canonicalPackageName != null)
          b.append("\n@").append(SuppressWarnings.class.getName()).append("(\"all\")");

        if (classSpec.referrer != null)
          b.append("\n@").append(JxBinding.class.getName()).append("(targetNamespace = \"").append(registry.targetNamespace).append("\")");

        b.append("\n@").append(Generated.class.getName()).append(GENERATED);
        b.append("\npublic ").append(classSpec);
        sources.put(type.getName(), b.toString());
        b.setLength(0);
      }
    }

    return sources;
  }

  @Override
  public Registry.Type classType() {
    throw new UnsupportedOperationException();
  }

  public Map<String,String> toSource(final File dir) throws IOException {
    final Map<String,String> sources = toSource();
    if (sources.size() > 0) {
      for (final Map.Entry<String,String> entry : sources.entrySet()) { // [S]
        final File file = new File(dir, entry.getKey().replace('.', '/') + ".java");
        file.getParentFile().mkdirs();
        Files.write(file.toPath(), entry.getValue().getBytes());
      }
    }

    return sources;
  }

  @Override
  public String elementName() {
    return "schema";
  }

  @Override
  public Declarer declarer() {
    return null;
  }

  @Override
  public String displayName() {
    return "";
  }
}