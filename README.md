# OpenJAX JSONx

> Java <-> JSON Binding

[![Build Status](https://travis-ci.org/openjax/jsonx.png)](https://travis-ci.org/openjax/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/openjax/jsonx/badge.svg)](https://coveralls.io/github/openjax/jsonx)

## Introduction

<ins>JSONx</ins> is a framework, providing the **JSON** E**x**tensible Schema Specification, supporting the following main features:

1. [**<ins>JSONx</ins> Schema**](#jsonx-schema): Schema language in XML and in <ins>JSONx</ins> (can be converted from one to the other):
    1. [XML Schema Document][jsonx-xsd]: A <ins>JSONx</ins> Schema as an XSD, leveraging XML for schema validation.
    2. [JSONx Document][jsonx-jsonx]: A <ins>JSONx</ins> Schema as a JSON document, leveraging <ins>JSONx</ins> for schema validation.
2. [**Binding Generator**](#binding-generator): Generate Java binding classes from a <ins>JSONx</ins> Schema. Generated classes are used to parse and marshal JSON documents to and from binding classes.
3. [**Schema Generator**](#schema-generator): Generate a <ins>JSONx</ins> Schema from binding classes.
4. [**Validation**](#validation): Validation of a JSON document against a <ins>JSONx</ins> Schema. Element validation includes:
    1. **array**: Validation of declared elements and their order specified via: `minOccurs`, `maxOccurs`, `nullable`.
    2. **boolean**: No properties to validate.
    3. **number**: Validation of `form` (integer/real) and `range` (min/max inclusive/exclusive).
    4. **object**: Validation of object inheritence hierarchy (`abstract`, `extends`), and declared properties, involving: `use` (optional/required), `nullable`.
    5. **string**: Validation of `pattern` (regex pattern).
5. [**JAX-RS Provider**](#jax-rs-provider): Content provider as MessageBodyReader and MessageBodyWriter.

## <ins>JSONx</ins> Schema

The [JSONx Schema][jsonx-xsd] is the foundation of the <ins>JSONx</ins> Framework. The <ins>JSONx</ins> Schema defines [template](#templates) classes that are enforced by <ins>JSONx</ins> during validation (parsing or marshalling), both in the XML and <ins>JSONx</ins> forms of schema documents. The schema allows for the definition of [Types](#types), [Properties](#properties), and [Elements](#elements) representing the five JSON value classes: **string**, **number**, **boolean**, **array**, and **object**.

### Templates

The **template** _abstractions_ define the base properties for JSON values.

| **Type** | **Attributes** | **Children** |
|----------|--|--|
| **_boolean_**          |  | |
| **_number_**<br>&nbsp; | <ins>form</ins>: &lt; integer \| **real** &gt; <br><ins>range</ins>: Numerical range ([interval notation][interval-notation]) | |
| **_string_**           | <ins>pattern</ins>: Regular expression | |
| **_object_**<br>&nbsp; | <ins>abstract</ins>: &lt; true \| **false** &gt;<br><ins>extends</ins>: Name of **object** [type](#types) | **[Properties](#properties)**<br>&nbsp; |
| **_array_**<br>&nbsp;  | <ins>minIterate</ins>: &lt; **1** \| 2 \| ... &gt;<br><ins>maxIterate</ins>: &lt; **1** \| 2 \| ... \| unbounded &gt; | **[Elements](#elements)**<br>&nbsp; |

### Schema

The **schema** is root element of the Schema Document, and contains [type](#types) definitions.

| **Type** | **Attributes** | **Children** |
|----------|--|--|
| **schema** | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | **[Types](#types)** |

### Types

The **type** definitions belong to a **[schema](#schema)**, and inherit from [Template](#templates) definitions with the following extensions:

| **Type** | **Attributes** | **Children** |
|----------|--|--|
| **_\*\*all\*\*_** | <ins>name</ins>: Name of type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | |

### Properties

Properties belong to an **object**, and inherit from [type](#types) definitions with the following extensions:

| **Type** | **Attributes** | **Children** |
|----------|--|--|
| **_\*\*all\*\*_**<br>&nbsp; | <ins>use</ins>: &lt; **required** \| optional &gt;<br><ins>nullable</ins>: &lt; **true** \| false &gt;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | |
| **reference** | <ins>type</ins>: Name of reference [type](#types) | |

### Elements

The **element** definitions belong to an **array**, and inherit from [Template](#templates) definitions with the following extensions:

| **Type** | **Attributes** | **Children** |
|----------|--|--|
| **_\*\*all\*\*_**<br>&nbsp;<br>&nbsp; | <ins>nullable</ins>: &lt; **true** \| false &gt;<br><ins>minOccurs</ins>: &lt; 0 \| **1** \| 2 \| ... &gt;<br><ins>maxOccurs</ins>: &lt; 0 \| 1 \| 2 \| ... \| **unbounded** &gt;&nbsp;&nbsp; | |
| **reference** | <ins>type</ins>: Name of reference [type](#types) | |

## Bindings

The <ins>JSONx</ins> framework implements an API for the definition of binding rules between JSON and Java classes, providing the compile- and run-time equivalent version of the full scope of the <ins>JSONx</ins> Schema. <ins>JSONx</ins> bindings allow a class to parse and marshal JSON to and from their Java object representations. The binding API is expressed as Java annotations, allowing any class to bind to <ins>JSONx</ins> with light coupling.

### Binding Generator

The <ins>JSONx</ins> framework implements a Java class generator that reads a <ins>JSONx</ins> Schema and writes `.java` files. The generated classes are strongly-typed, providing compile-time enforcement of the structural definitions of a <ins>JSONx</ins> Schema. The generated classes can be used to parse and marshal JSON to and from Java objects, providing runtime enforcement of the validation properties of elements of a <ins>JSONx</ins> Schema.

### Schema Generator

The <ins>JSONx</ins> framework implements a <ins>JSONx</ins> Schema generator that reads `.class` files and writes <ins>JSONx</ins> Schemas. If <ins>JSONx</ins> bindings were used directly to implement a <ins>JSONx</ins> model in Java, the schema generator can be used to reverse engineer the schema from Java classes. This is effectively the inverse of the [Binding Generator](#binding-generator).

### Validation

The <ins>JSONx</ins> framework implements a validation engine that enforces the definitions in a <ins>JSONx</ins> Schema. Validation attributes are defined for each element class in the [**<ins>JSONx</ins> Schema**](#jsonx-schema):

1. **array**

    Array classes define the spec of member elements that are allowed to appear in the array. Elements specify the folliwng attributes to define validation rules:

    * `minOccurs`: Minimum (inclusive) number of occurrences expected of the member element.
    * `maxOccurs`: Maximum (inclusive) number of occurrences allowed of the member element.
    * `nullable`: Whether the member element can be represented as `null`.

    Additionally, the array class defines the following validation rules:

    * `minIterate`: Minimum (inclusive) number of iterations expected of the member element spec.
    * `maxIterate`: Maximum (inclusive) number of iterations allowed of the member element spec.

    _**Note**: In order to support validation based on the aforementioned attributes, the <ins>JSONx</ins> framework relies on a "Breadth First Search" algorithm to attempt to match each member in a JSON array to an element definition. <ins>Loosely defined elements can result in more costly validation times. Elements defined with strict attributes, however, will result in optimal performance.</ins> When matching member elements of an array, the array validator has a worst case performance of `O(N * E!)`, where `N` is the number of elements in an array, and `E` is the number of element classes in the array definition._

    <ins>Example</ins>: _(Exerpt from [arrays.jsonx](/generator/src/test/resources/array.jsonx))_
    ```xml
    <array minIterate="2" maxIterate="9" nullable="false">
      <string maxOccurs="3" nullable="false"/>
      <reference type="rootString" minOccurs="2"/>
      <boolean nullable="false"/>
      <array minIterate="0" maxIterate="3" minOccurs="3" maxOccurs="5">
        <string nullable="false" minOccurs="2" maxOccurs="7"/>
        <reference type="rootNumber"/>
        <array maxIterate="2" maxOccurs="1">
          <reference type="bar$rootArray2"/>
          <array maxOccurs="2">
            <array nullable="false">
              <string/>
            </array>
          </array>
          <boolean minOccurs="5"/>
        </array>
        <reference type="rootBoolean"/>
        <number maxOccurs="7"/>
      </array>
      <reference type="templateArray"/>
      <number nullable="false"/>
    </array>
    ```

2. **boolean**

    No attributes to validate.

3. **number**

    Number classes define the following validation rules:

    * `form`: Either "real" or "integer".
    * `range`: Numerical range in [interval notation][interval-notation].

    <ins>Example</ins>: _(Exerpt from [datatype.jsonx](/generator/src/test/resources/datatype.jsonx))_
    ```xml
    <number name="num"/>
    <number name="numInt" form="integer"/>
    <number name="numIntRange1" form="integer" range="[1,]"/>
    <number name="numIntRange2" form="integer" range="[,4]"/>
    <number name="numRange1" form="real" range="(1.2,]"/>
    <number name="numRange2" form="real" range="[,4.35)"/>
    ```

4. **object**

    Object classes define the spec of properties that are allowed ot appear in the object. Properties specify the following attributes to define validation rules:

    * `use`: Whether the member property is **"optional"** or "required".
    * `nullable`: Whether the member property can be represented as `null`.

    Additionally, the object class defines the following validation rules:

    * `abstract`: Whether the definition represents an abstract class (that cannot be instantiated).
    * `extends`: Object class inheritence.

    <ins>Example</ins>: _(Exerpt from [complete.jsonx](/generator/src/test/resources/complete.jsonx))_
    ```xml
    <objectType name="xyz$objects">
      <property name="objectDefault" xsi:type="object"/>
      <property name="objectOptional" xsi:type="object" use="optional"/>
      <property name="objectOptionalNotNullable" xsi:type="object" use="optional" nullable="false"/>
      <property name="objectExtendsAbstract" xsi:type="object" extends="cls$abstractObject">
        <property name="objectStringOptional" xsi:type="reference" type="rootString" use="optional"/>
        <property name="objectExtendsBooleans" xsi:type="object" extends="simple$booleans"/>
          <property name="objectStringOptional" xsi:type="reference" type="rootString" use="optional"/>
          <property name="objectNumber" xsi:type="number"/>
        </property>
        <property name="objectOptionalNotNullableExtendsAbstract" xsi:type="object" extends="cls$abstractObject" use="optional" nullable="false">
          <property name="objectNumber" xsi:type="number"/>
        </property>
      </property>
      <property name="objectExtendsStrings" xsi:type="object" extends="simple$strings">
        <property name="additionalString" xsi:type="string"/>
      </property>
    </objectType>
    ```

5. **string**

    String classes define the following validation rules:

    * `pattern`: A regex pattern for value validation.

    <ins>Example</ins>: _(Exerpt from [datatype.jsonx](/generator/src/test/resources/datatype.jsonx))_
    ```xml
    <string name="str"/>
    <string name="strPattern" pattern="[%0-9a-z]+"/>
    ```

### JAX-RS Provider

The <ins>JSONx</ins> framework implements the `MessageBodyReader` and `MessageBodyWriter` interfaces in [`JxObjectProvider`](/rs/src/main/java/org/openjax/jsonx/rs/JxObjectProvider.java) to integrate with JAX-RS servers.

## Usage

### Getting Started

#### Prerequisites

* [Java 8][jdk8-download] - The minimum required JDK version.
* [Maven][maven] - The dependency management system.

#### Example

1. In your preferred development directory, create a [`maven-archetype-quickstart`][maven-archetype-quickstart] project.

  ```tcsh
  mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
  ```

2. Create an `example.jsonx` and put it in `src/main/resources/`.

  ```xml
  <schema
    xmlns="http://jsonx.openjax.org/schema-0.9.8.xsd"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://jsonx.openjax.org/schema-0.9.8.xsd http://jsonx.openjax.org/schema.xsd">

    <objectType name="id" abstract="true">
      <property xsi:type="string" name="id" pattern="[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}" nullable="false"/>
    </objectType>

    <objectType name="idVersion" abstract="true" extends="id">
      <property xsi:type="number" name="version" nullable="false"/>
    </objectType>

    <objectType name="ids">
      <property xsi:type="array" name="id" nullable="false">
        <string pattern="[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}" nullable="false"/>
      </property>
    </objectType>

    <objectType name="credentials">
      <property xsi:type="string" name="email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}" nullable="false"/>
      <property xsi:type="string" name="password" pattern="[0-9a-f]{64}" use="optional" nullable="false"/>
    </objectType>

    <objectType name="account" extends="credentials">
      <property xsi:type="string" name="id" pattern="[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}" nullable="false" use="optional"/>
      <property xsi:type="string" name="firstName" nullable="false"/>
      <property xsi:type="string" name="lastName" nullable="false"/>
    </objectType>

  </schema>
  ```

3. Add the [`org.openjax.jsonx:jsonx-maven-plugin`][jsonx-maven-plugin] to the POM.

  ```xml
  <plugin>
    <groupId>org.openjax.jsonx</groupId>
    <artifactId>jsonx-maven-plugin</artifactId>
    <version>0.9.8</version>
    <executions>
      <execution>
        <goals>
          <goal>generate</goal>
        </goals>
        <configuration>
          <destDir>${project.build.directory}/generated-sources/jsonx</destDir>
          <prefix>com.example.json$</prefix>
          <schemas>
            <schema>src/main/resources/example.jsonx</schema>
          </schemas>
        </configuration>
      </execution>
    </executions>
  </plugin>
  ```

4. Add the `org.openjax.jsonx:jsonx-generator` dependency to the POM.

  ```xml
  <dependency>
    <groupId>org.openjax.jsonx</groupId>
    <artifactId>jsonx-generator</artifactId>
    <version>0.9.8</version>
  </dependency>
  ```

5. Upon successful execution of the [`jsonx-maven-plugin`][jsonx-maven-plugin] plugin, a class by the name of `json` (as was specified in the `<prefix>` element of the `<configuration>` in the `jsonx-maven-plugin` definition) will be generated in `generated-sources/jsonx`. Add this path to your Build Paths in your IDE to integrate into your project.

6. The generated classes can be instantiated as any other Java objects. They are strongly typed, and will guide you in proper construction of a JSON message. The following APIs can be used for parsing and marshalling <ins>JSONx</ins> to and from JSON:

  To parse JSON to <ins>JSONx</ins> Bindings:

  ```java
  String json = "{\"email\":\"john@doe\",\"password\":\"066b91577bc547e21aa329c74d74b0e53e29534d4cc0ad455abba050121a9557\"}";
  json.Credentials credentials = JxDecoder.parseObject(json.Credentials.class, new JsonReader(new StringReader(json)));
  ```
  
  To marshal <ins>JSONx</ins> Bindings to JSON:

  ```java
  String json2 = new JxEncoder().marshal(credentials);
  assertEquals(json, json2);
  ```

### JavaDocs

JavaDocs are available [here](https://jsonx.openjax.org/apidocs/).

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[interval-notation]: https://en.wikipedia.org/wiki/Interval_(mathematics#Including_or_excluding_endpoints)
[jdk8-download]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[json]: http://www.json.org/
[jsonx-jsonx]: /generator/src/main/resources/schema.jsonx
[jsonx-maven-plugin]: /maven-plugin
[jsonx-xsd]: /generator/src/main/resources/schema.xsd
[maven-archetype-quickstart]: http://maven.apache.org/archetypes/maven-archetype-quickstart/
[maven]: https://maven.apache.org/