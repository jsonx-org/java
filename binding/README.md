# JSON/Java Binding API

## Abstract

This document specifies the <ins>JSON/Java Binding API</ins>, which offers facilities for binding Java classes to JSON objects whose structure is expressed in the [<ins>JSON Schema Definition Language</ins>][jsd].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Dependencies on Other Specifications][#dependencies]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.2 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [<ins>JSON/Java Binding API</ins>][#bindingapi]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1 [Structural][#structural]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.1 [JSON objects (`JxObject`)][#jxobject]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.2 [Property Annotations][#properties]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.2.1 [Special Considerations][#specialconsiderations]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.3 [JSON arrays (`@ArrayType`)][#arraytype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.3.1 [Special Considerations][#specialconsiderations]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.4 [Element Annotations][#elements]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5 [JSON Value Annotations][#values]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.1 [<samp>**object**</samp> Type][#object]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.1.1 [`@ObjectProperty`][#objectproperty]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.1.2 [`@ObjectElement`][#objectelement]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.2 [<samp>**array**</samp> Type][#array]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.2.1 [`@ArrayProperty`][#arrayproperty]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.2.2 [`@ArrayElement`][#arrayelement]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.3 [<samp>**boolean**</samp> Type][#boolean]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.3.1 [`@BooleanProperty`][#booleanproperty]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.3.2 [`@BooleanElement`][#booleanelement]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.4 [<samp>**number**</samp> Type][#number]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.4.1 [`@NumberProperty`][#numberproperty]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.4.2 [`@NumberElement`][#numberelement]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.5 [<samp>**string**</samp> Type][#string]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.5.1 [`@StringProperty`][#stringproperty]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.5.2 [`@StringElement`][#stringelement]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.6 [<samp>**any**</samp> Type][#any]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.6.1 [`@AnyProperty`][#anyproperty]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.6.2 [`@AnyElement`][#anyelement]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.7 [`AnyObject`][#anyobject]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.5.8 [`@AnyArray`][#anyarray]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2 [Functional][#functional]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2.1 [`ValidationException`][#validationexception]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2.2 [`JxEncoder`][#jxencoder]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2.2.1 [`EncodeException`][#encodeexception]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2.3 [`JxDecoder`][#jxdecoder]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2.3.1 [`DecodeException`][#decodeexception]

## 1 Introduction

This document sets out the structural part of the <ins>JSON/Java Binding API</ins>. It also contains a directory of links to these related resources.

The <ins>JSON/Java Binding API</ins> is designed to bind JSON documents to Java objects. More specifically, the <ins>JSON/Java Binding API</ins> provides a way for JSON objects whose structure is expressed in the [<ins>JSON Schema Definition Language</ins>][jsd] to be parsed and marshaled, to and from Java objects of strongly-typed classes. The <ins>JSON/Java Binding API</ins> can also be used to validate JSON documents as they are parsed from text or marshaled from Java objects against a JSD. Thus, the <ins>JSON/Java Binding API</ins> is a reference implementation of the validation and binding functionalities of the [<ins>JSON Schema Definition Language</ins>][jsd].

Any application that consumes well-formed JSON can use the <ins>JSON/Java Binding API</ins> to interface with JSON with Java classes. The <ins>JSON/Java Binding API</ins> supports _all_ facilities of the structural and logical features of the [<ins>JSON Schema Definition Language</ins>][jsd].

### 1.1 Dependencies on Other Specifications

The definition of <ins>JSON/Java Binding API</ins> depends on the following specifications: [<ins>JSON Schema Definition Language</ins>][jsd].

### 1.2 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

## 2 <ins>JSON/Java Binding API</ins>

The <ins>JSON/Java Binding API</ins> is comprised of Java classes, interfaces, and annotations that belong to the `org.openjax.jsonx` package. The API consists of two logical parts: <ins>Structural</ins> and <ins>Functional</ins>.

#### 2.1 Structural

The <ins>Structural</ins> part of the <ins>JSON/Java Binding API</ins> is used to define rules that bind Java classes to JSON Schemas. The primitive JSON value types <samp>**boolean**</samp>, <samp>**number**</samp> and <samp>**string**</samp> are represented by their analogous Java types of `Boolean`, `Number`, and `String`. The <samp>**object**</samp> value type is represented by the `JxObject` interface, with properties defined with [Property Annotations][#properties]. The <samp>**array**</samp> value type is represented by the `@ArrayType`, `@ArrayProperty` and `@ArrayElement` annotations, with elements defined with [Element Annotations][#elements].

##### 2.1.1 JSON objects (`JxObject`)

An <samp>**object**</samp> definition in a JSD can be bound to a Java class that implements the `JxObject` interface. The `JxObject` interface does not define any methods, and is used to specify to the <ins>JSON/Java Binding API</ins> that the class supports JSON binding.

```java
public class MyObject implements JxObject {
  @BooleanProperty
  public Boolean prop1;

  @NumberProperty
  public BigDecimal prop2;

  @StringProperty
  public String prop3;
}
```

##### 2.1.2 Property Annotations

Property annotations are used to define property bindings in JSON objects. Property annotations annotate fields in a class to bind to JSON properties in a JSON object. There are 5 property annotations:

1. [`@ObjectProperty`][#objectproperty]
1. [`@BooleanProperty`][#booleanproperty]
1. [`@NumberProperty`][#numberproperty]
1. [`@StringProperty`][#stringproperty]
1. [`@AnyProperty`][#anyproperty]

Property annotations define 3 common attributes:
  
1. `name()`

   The name of the property. If omitted, the name of the property is the field's name.

1. `nullable()`

   Whether the property is allowed to be `null`. Default: `true`.

1. `use()`

   Whether the property is required or optional (`Use.REQUIRED` or `Use.OPTIONAL`). Default: `Use.REQUIRED`.

###### 2.1.2.1 Special Considerations

Different combinations of `nullable()` and `use()` have different requirements regarding field's declared type:

* `(nullable=false, use=Use.REQUIRED)`
  
  Declared type is unrestricted, and allows `boolean` primitive for <samp>**boolean**</samp> JSON values, and numerical primitives (`byte`, `short`, `int`, `long`, `float`, and `double`) for <samp>**number**</samp> JSON values.

* `(nullable=true, use=Use.REQUIRED)`

  Declared type must support `null` to represent a `null` JSON value. Primitive types are therefore not allowed.

* `(nullable=false, use=Use.OPTIONAL)`

  Declared type must support `null` to represent an absent property. Primitive types are therefore not allowed.

* `(nullable=true, use=Use.OPTIONAL)`

  Declared type must be `Optional<>`, so as to support the presence of the property with a `null` value, as well as its absence.

###### 2.1.3 JSON arrays (`@ArrayType`)

The `@ArrayType` annotation is used to annotate an annotation class that represents binding rules for a JSON array. The `@ArrayType` annotation describes the manifest of element members that are allowed to appear in the JSON array. The manifest of element members is represented by [Element Annotations][#elements].

The `@ArrayType`, `@ArrayProperty` and `@ArrayElement` annotations define 3 common attributes that specify rules for the manifest of element members:

1. `elementIds()`

   Specifies the `id`s of element annotations declared in the same annotation context.

1. `minIterate()`

   The minimum inclusive number of iterations of child elements. Default: 1.

1. `maxIterate()`

   The maximum inclusive number of iterations of child elements. Default: 1.

_**Note**: In order to support validation based on the aforementioned attributes, the <ins>JSONx</ins> framework relies on a "Breadth First Search" algorithm to attempt to match each member in a JSON array to an element definition. <ins>Loosely defined elements can result in more costly validation times. Elements defined with strict attributes, however, will result in optimal performance.</ins> When matching member elements of an array, the array validator has a worst case performance of `O(N * E!)`, where `N` is the number of elements in an array, and `E` is the number of element classes in the array definition._

<ins>Example</ins>: The annotation `@Staff` defines a JSON array that is comprised of zero or more `Contractor` and `Employee` member types in any order.

```java
@ObjectElement(id=1, type=Contractor.class, minOccurs=0)
@ObjectElement(id=0, type=Employee.class, minOccurs=0)
@ArrayType(elementIds={0, 1}, maxIterate=Integer.MAX_VALUE)
public @interface Staff {
}
```

###### 2.1.3.1 Special Considerations

The `minIterate()` and `maxIterate()` attributes define the repetition cardinality of element types in a JSON array. The default for both attributes is `1`, which effectively removes the computational complexity of multi-iterable array definitions during parsing, marshaling, and validating JSON arrays. It is important to note that when set to non-`1` values, the computational complexity increases by one dimension. The value of `minIterate()` must be less than or equal to `maxIterate()`.

##### 2.1.4 Element Annotations

Element annotations are used to define element bindings in JSON arrays. Element annotations specify the rules for parsing, marshaling and validating JSON arrays in string or Java object form. There are 5 element annotations:

1. [`@ObjectElement`][#objectelement]
1. [`@BooleanElement`][#booleanelement]
1. [`@NumberElement`][#numberelement]
1. [`@StringElement`][#stringelement]
1. [`@AnyElement`][#anyelement]

Element annotations define 4 common attributes:

1. `id()`

   The `id` of the element, to be referenced in the `elementIds()` attribute of `@ArrayType`, `@ArrayProperty` and `@ArrayElement` annotations. The `id` must be unique in relation to other elements defined in the context of a particular JSON array type, property, or element.

   One can define JSON array bindings of one or more member types of arbitrary cardinality rules.

   <ins>Example</ins>: A JSON array allowing alternating members of `boolean` and `number` would define 2 element annotations

   ```java
   @NumberElement(id=2)
   @BooleanElement(id=1)
   ```

   The `id`s `1` and `2` would thereafter be used in one of:

   * `@ArrayType(elementIds={1, 2})`
   * `@ArrayProperty(elementIds={1, 2})`
   * `@ArrayElement(elementIds={1, 2})`

   The `elementIds()` attribute specifies the relationality rules of the member elements.

1. `nullable()`

   Whether the member element is allowed to be `null`. Default: `true`.

1. `minOccurs()`

   The minimum inclusive number of occurrence of this element. Default: `1`.

1. `maxOccurs()`

   The maximum inclusive number of occurrence of this element. Default: `Integer.MAX_VALUE`.

##### 2.1.5 JSON Value Annotations

In addition to the default attributes defined for [Property Annotations][#properties] and [Element Annotations][#elements], the <ins>JSON/Java Binding API</ins> defines specific attributes for JSON Value Types that map to analogous contstraint properties in the [<ins>JSON Schema Definition Language</ins>][jsd].

##### 2.1.5.1 <samp>object</samp> Type

A JSON <samp>**object**</samp> is represented by Java objects that implement the [`JxObject`][#jxobject] interface.

###### 2.1.5.1.1 `@ObjectProperty`

The `@ObjectProperty` annotation is used to bind a JSON object type to a field in a `JxObject` subclass. The field's class type specifies the JSON binding class, which must be a subclass of the `JxObject` interface. If the field's class type does not inherit from the `JxObject` interface, the <ins>JSON/Java Binding API</ins> will throw a `ValidationException` during parsing or marshaling.

<ins>Example</ins>: The class `MyObject` defines an object property that is of its own type (i.e. `MyObject.object` is of type `MyObject`).

```java
public class MyObject implements JxObject {
  @ObjectProperty
  public MyObject object;
}
```

###### 2.1.5.1.2 `@ObjectElement`

The `@ObjectElement` annotation is used to bind a JSON object type as a member type of a JSON array. The `@ObjectElement` defines the following additional attributes:

1. `type()`

   The JSON binding class of type `Class<? extends JxObject>`.

<ins>Example</ins>: The class `Company` describes a JSON object with a JSON array that is comprised of `Employee` members.

```java
public class Company implements JxObject {
  @ObjectElement(id=0, type=Employee.class)
  @ArrayProperty(elementIds={0})
  public List<Employee> employees;
}
```

##### 2.1.5.2 <samp>array</samp> Type

A JSON <samp>**array**</samp> is represented by instances of `java.util.List`.

###### 2.1.5.2.1 `@ArrayProperty`

The `@ArrayProperty` annotation is used to bind a JSON array definition to a field defined in a subclass of a `JxObject`. The `@ArrayProperty` annotation allows one to declare JSON array binding rules specific to the field on which they are defined.

_**Note**: The field's type is required to be `java.util.List`. The generic type is checked during parsing and marshaling._

<ins>Example</ins>: The class `Company` describes a JSON object with a JSON array that is comprised of zero or more `Contractor` and `Employee` members in any order.

```java
public class Company implements JxObject {
  @ObjectElement(id=1, type=Contractor.class, minOccurs=0)
  @ObjectElement(id=0, type=Employee.class, minOccurs=0)
  @ArrayProperty(elementIds={0, 1}, maxIterate=Integer.MAX_VALUE)
  public List<? extends Person> staff;
}
```

An alternative way to define the same structural rules is by using the `@Staff` annotation defined as a [`@ArrayType`][#arraytype]:

```java
public class Company implements JxObject {
  @ArrayProperty(type=Staff.class, maxIterate=Integer.MAX_VALUE)
  public List<? extends Person> staff;
}
```

###### 2.1.5.2.2 `@ArrayElement`

The `@ArrayElement` annotation is used to bind a JSON array definition as a member type of a JSON array. The `@ArrayElement` annotation allows one to declare JSON array binding rules that are specific only to the member element on which they are defined.

<ins>Example</ins>: The class `Company` describes a JSON object with a JSON array that is comprised of arrays of `Contractor` and `Employee` members.

```java
public class Company implements JxObject {
  @ObjectElement(id=3, type=Contractor.class, minOccurs=0)
  @ObjectElement(id=2, type=Employee.class, minOccurs=0)
  @ArrayElement(id=1, elementIds={3})
  @ArrayElement(id=0, elementIds={2})
  @ArrayProperty(elementIds={0, 1})
  public List<List<? extends Person>> object;
}
```

##### 2.1.5.3 <samp>boolean</samp> Type

A JSON <samp>**boolean**</samp> is represented by instances of `java.lang.Boolean` and `boolean` (refer to [Special Considerations][#specialconsiderations] to determine the appropriate declared type).

###### 2.1.5.3.1 `@BooleanProperty`

```java
public class Company implements JxObject {
  @BooleanProperty(nullable=false, use=Use.REQUIRED)
  public boolean foo;

  @BooleanProperty(nullable=true, use=Use.REQUIRED)
  public Boolean bar;

  @BooleanProperty(nullable=true, use=Use.OPTIONAL)
  public Optional<Boolean> optional;
}
```

###### 2.1.5.3.2 `@BooleanElement`

```java
public class Company implements JxObject {
  @BooleanElement(id=1)
  @ArrayProperty(elementIds={1})
  public List<Boolean> booleans;
}
```

##### 2.1.5.4 <samp>number</samp> Type

A JSON <samp>**number**</samp> is represented by instances of `java.lang.Number` and the primitive numerical types (`byte`, `short`, `int`, `long`, `float`, and `double`-- refer to [Special Considerations][#specialconsiderations] to determine the appropriate declared type).

The `@NumberProperty` and `@NumberElement` annotations define the following additional attributes:

1. `form()`

   Specifies the numeric form of the number (`Form.REAL` or `Form.INTEGER`). Default: `Form.REAL`.

1. `range()`

   Specifies the value range in interval notation:
   * Open (exclusive) interval: `(min,max)`
   * Closed (inclusive) interal: `[min,max]`
   * Half-open or half-closed interval: `[min,max)`
   * Degenerate interval: `[val]`

###### 2.1.5.4.1 `@NumberProperty`

```java
public class Company implements JxObject {
  @NumberProperty(form=Form.REAL, range="[-1,1)", nullable=false, use=Use.REQUIRED)
  public BigDecimal real;

  @NumberProperty(form=Form.INTEGER, range="[0,]", nullable=true, use=Use.REQUIRED)
  public BigInteger integer;

  @NumberProperty(range="[,0]", nullable=true, use=Use.OPTIONAL)
  public Optional<? extends Number> optional;
}
```

###### 2.1.5.4.2 `@NumberElement`

```java
public class Company implements JxObject {
  @NumberElement(id=1, form=Form.REAL, range="[-1,1)")
  @ArrayProperty(elementIds={1})
  public List<? extends Number> numbers;
}
```

##### 2.1.5.5 <samp>string</samp> Type

A JSON <samp>**string**</samp> is represented by instances of `java.lang.String`.

The `@StringProperty` and `@StringElement` annotations define the following additional attributes:

1. `pattern()`

   Specifies the regex pattern.

###### 2.1.5.5.1 `@StringProperty`

```java
public class Company implements JxObject {
  @StringProperty(pattern="[a-z]+", nullable=false, use=Use.REQUIRED)
  public BigDecimal real;

  @StringProperty(pattern="[0-9]+", nullable=true, use=Use.REQUIRED)
  public BigInteger integer;

  @StringProperty(pattern="\\S+", nullable=true, use=Use.OPTIONAL)
  public Optional<String> optional;
}
```

###### 2.1.5.5.2 `@StringElement`

```java
public class Company implements JxObject {
  @StringElement(id=1, pattern="[a-z0-9]+", range="[-1,1)")
  @ArrayProperty(elementIds={1})
  public List<String> strings;
}
```

##### 2.1.5.6 <samp>any</samp> Type

<samp>**any**</samp> represents a meta value type that is used to refer to actual JSON value types.

The `@AnyProperty` and `@AnyElement` annotations define the following additional attributes:

1. `types()`

   Specifies the list of accepted type definitions. If omitted, the property or element will accept object of any type.

   The `types()` attribute utilizes an annotation named `@t` for the type specification, which defines the following attributes:

   1. `arrays()`

      An annotation class that specifies a `@ArrayType` annotation.

   1. `booleans()`

      A `boolean` value specifying whether boolean properties or element members are allowed.

   1. `numbers()`

      A `@NumberType` annotation that declares `form()` and `range()` attributes specifying acceptable number values.

   1. `strings()`

      A regex specifying accepted `String` instances.

   1. `objects()`

      A `Class<? extends JxObject>` specifying acceptable object classes.

###### 2.1.5.6.1 `@AnyProperty`

```java
public class Company implements JxObject {
  @AnyProperty(types={@t(booleans=true)}, nullable=false, use=Use.REQUIRED)
  public Boolean booleans;

  @AnyProperty(types={@t(numbers=@NumebrType(form=Form.INTEGER, range="[,100]"))}, nullable=true, use=Use.REQUIRED)
  public BigInteger integers;

  @AnyProperty(types={@t(numbers=@NumebrType(form=Form.INTEGER, range="[,100]"), @t(strings="[a-z]+"))}, nullable=true, use=Use.OPTIONAL)
  public Optional<Object> optional;
}
```

###### 2.1.5.6.2 `@AnyElement`

```java
public class Company implements JxObject {
  @AnyElement(id=1, types={@t(numbers=@NumebrType(form=Form.INTEGER, range="[,100]"), @t(strings="[a-z]+"))}, nullable=false)
  @ArrayProperty(elementIds={1})
  public List<Object> any;
}
```

###### 2.1.5.7 `AnyObject`

The `AnyObject` class is a JSON/Java binding that utilizes the `@AnyProperty` annotation to be able to represent any JSON object.

###### 2.1.5.8 `@AnyArray`

The `AnyArray` annotation is a JSON/Java binding that utilizes the `@AnyElement` annotation to be able to represent any JSON array.

#### 2.2 Functional

The functional part of the <ins>JSON/Java Binding API</ins> is responsible for parsing, marshaling, and validating JSON documents in string or object form.

##### 2.2.1 `ValidationException`

The `ValidationException` represents an error in the use of the [Structural][#structural] part of the <ins>JSON/Java Binding API</ins>. The `ValidationException` is thrown when the binding model is evaluated during the process of parsing a JSON document string to binding classes, or marshaling binding classes to a JSON document string.

##### 2.2.2 `JxEncoder`

The `JxEncoder` serializes Jx objects (that extend `JxObject`) and Jx arrays (with a provided annotation class that declares an `@ArrayType` annotation) to JSON documents. `JxEncoder` instances are differentiated by the `indent` value, which represents the number of spaces to use (as meaningless whitespace) when encoding JSON documents. The `JxEncoder` has a `protected` constructor, and exposes `static` fields and methods to obtain a `JxEncoder` instance so that instances can be cached.

1. `JxEncoder#_0`

   The `JxEncoder` instance with `indent=0`.
   
1. `JxEncoder#_1`

   The `JxEncoder` instance with `indent=1`.
   
1. `JxEncoder#_2`

   The `JxEncoder` instance with `indent=2`.
   
1. `JxEncoder#_3`

   The `JxEncoder` instance with `indent=3`.
   
1. `JxEncoder#_4`

   The `JxEncoder` instance with `indent=4`.
   
1. `JxEncoder#_8`

   The `JxEncoder` instance with `indent=8`.
   
1. `JxEncoder#get(int)`

   Gets the the `JxEncoder` instance for the specified `indent` value. The specified value must be a non-negative integer.
   
1. `JxEncoder#get()`

   Gets the the global `JxEncoder` instance. The default global `JxEncoder` has `indent=0`.
   
1. `JxEncoder#set(JxEncoder)`

   Sets the the global `JxEncoder` instance to the specified `JxEncoder`.

Once a `JxEncoder` instance is obtained, it can be used to marshal binding objects to JSON document strings.

1. `JxEncoder#marshal(List<?>, Class<? extends Annotation>)`

   Marshals the supplied `list` to the specification of the provided annotation type. The provided annotation type must declare an annotation of type `@ArrayType` that specifies the model of the list being marshaled.

1. `JxEncoder#marshal(JxObject)`

   Marshals the specified `JxObject`.

##### 2.2.2.1 `EncodeException`

Signals that an error has occurred while encoding a JSON document.

##### 2.2.3 `JxDecoder`

The `JxDecoder` deserializes JSON documents to objects of `JxObject` classes, or to lists conforming to a provided annotation class that declares an `@ArrayType` annotation. The `JxDecoder` is an uninstantiable class that provides `static` methods for parsing JSON document strings.

1. `JxDecoder#parseObject(Class<? extends JxObject>, JsonReader)`

   Parses a JSON object from the supplied `JsonReader` as per the specification of the provided `JxObject` class.

1. `JxDecoder#parseArray(Class<? extends Annotation>, JsonReader)`

   Parses a JSON array from the supplied `JsonReader` as per the specification of the provided annotation class that declares an `ArrayType` annotation.

##### 2.2.3.1 `DecodeException`

Signals that an error has occurred while decoding a JSON document.

[#introduction]: #1-introduction
[#dependencies]: #11-dependencies
[#conventions]: #12-conventions
[#bindingapi]: #2-bindingapi
[#structural]: #21-structural
[#jxobject]: #211-jxobject
[#properties]: #212-properties
[#specialconsiderations]: #2121-specialconsiderations
[#arraytype]: #213-arraytype
[#specialconsiderations]: #2131-specialconsiderations
[#elements]: #214-elements
[#values]: #215-values
[#object]: #2151-object
[#objectproperty]: #21511-objectproperty
[#objectelement]: #21512-objectelement
[#array]: #2152-array
[#arrayproperty]: #21521-arrayproperty
[#arrayelement]: #21522-arrayelement
[#boolean]: #2153-boolean
[#booleanproperty]: #21531-booleanproperty
[#booleanelement]: #21532-booleanelement
[#number]: #2154-number
[#numberproperty]: #21541-numberproperty
[#numberelement]: #21542-numberelement
[#string]: #2155-string
[#stringproperty]: #21551-stringproperty
[#stringelement]: #21552-stringelement
[#any]: #2156-any
[#anyproperty]: #21561-anyproperty
[#anyelement]: #21562-anyelement
[#anyobject]: #2157-anyobject
[#anyarray]: #2158-anyarray
[#functional]: #22-functional
[#validationexception]: #221-validationexception
[#jxencoder]: #222-jxencoder
[#encodeexception]: #2221-encodeexception
[#jxdecoder]: #223-jxdecoder
[#decodeexception]: #2231-decodeexception

[jsd]: ../schema