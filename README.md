# JSONX Framework

> JSON Schema, Validation, Java Binding, and more

[![Build Status](https://travis-ci.org/jsonxorg/jsonx.png)](https://travis-ci.org/jsonxorg/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/jsonxorg/jsonx/badge.svg)](https://coveralls.io/github/jsonxorg/jsonx)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/jsonx.svg)](https://www.javadoc.io/doc/org.jsonx/jsonx)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/jsonx.svg)](https://mvnrepository.com/artifact/org.jsonx/jsonx)

## Abstract

The <ins>JSONX Framework</ins> is a collection of specifications and reference implementations that provide <ins>structural</ins> and <ins>functional</ins> patterns intended to help developers work with JSON. The <ins>JSONX Framework</ins> is based on the [<ins>JSON Schema Definition Language</ins>][schema], which is a <ins>schema language</ins> inspired by the [\[XMLSchema\]][xmlschema] specification. This document introduces the <ins>JSONX Framework</ins>, and presents a directory of links to its constituent parts and related resources.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [<ins>JSONX Framework</ins>][#jsonxframework]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1 [<ins>JSON Schema Definition Language</ins>][#jsd]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.1 [Purpose][#1purpose]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.2 [Requirements][#1requirements]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.3 [Specification][#1specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1.4 [Example Usage][#1example]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2 [<ins>JSON/Java Binding API</ins>][#binding]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2.1 [Purpose][#2purpose]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2.2 [Requirements][#2requirements]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2.3 [Specification][#2specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.2.4 [Example Usage][#2example]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.3 [<ins>JSD Binding Generator</ins>][#generator]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.3.1 [Purpose][#3purpose]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.3.2 [Requirements][#3requirements]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.3.3 [Specification][#3specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.3.4 [Example Usage][#3example]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.4 [<ins>JSONX-JSON</ins>][#jsonx-json]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.4.1 [Purpose][#4purpose]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.4.2 [Requirements][#4requirements]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.4.3 [Specification][#4specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.4.4 [Example Usage][#4example]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.5 [<ins>JAX-RS Integration for JSONX</ins>][#jaxrs]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.5.1 [Purpose][#5purpose]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.5.2 [Requirements][#5requirements]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.5.3 [Specification][#5specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.5.4 [Example Usage][#5example]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.6 [<ins>JSONX Maven Plugin</ins>][#maven-plugin]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.6.1 [Purpose][#6purpose]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.6.2 [Requirements][#6requirements]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.6.3 [Specification][#6specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.6.4 [Example Usage][#6example]<br>
<samp>&nbsp;&nbsp;</samp>3 [Getting Started][#gettingstarted]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>3.1 [Prerequisites][#prerequisites]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>3.2 [Example][#example]

## 1 Introduction

The <ins>JSONX Framework</ins> is created for developers, offering <ins>structural</ins> and <ins>functional</ins> patterns that systematically reduce errors and pain-points commonly encountered when developing software that interfaces with JSON. The <ins>structural</ins> patterns are defined in the [<ins>JSON Schema Definition Language</ins>][schema], which is a programming-language-agnostic <ins>schema language</ins> to describe constraints and document the meaning, usage and relationships of the constituent parts of JSON documents. The <ins>functional</ins> patterns are reference implementations (on the Java platform) of the specification of the <ins>schema language</ins>, providing utilities that address common use-cases for applications that use JSON in one way or another. Common use-cases include:

1. Definition of a normative contract between a producer and consumer of JSON documents.
1. Validation of JSON documents conforming to a respective <ins>schema document</ins>.
1. Java class binding capabilities for JSON documents conforming to a respective <ins>schema document</ins>.

### 1.1 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

## 2 <ins>JSONX Framework</ins>

The <ins>JSONX Framework</ins> was created to help developers solve common problems related to various use-cases regarding JSON. The following sections present the constituent sub-projects of the <ins>JSONX Framework</ins>, and provide the purpose and requirements for each.

### 2.1 <ins>JSON Schema Definition Language</ins>

Describe JSON documents by using schema components to constrain and document the meaning, usage and relationships of their constituent parts: value types and their content.

#### 2.1.1 Purpose

Provide a <ins>schema language</ins> to describe normative contracts between producer and consumer ends of a protocol exchanging JSON documents.

#### 2.1.2 Requirements

1. The <ins>schema language</ins> MUST constrain and document the meaning, usage, constraints and relationships of the constituent parts of a JSON document.

1. The <ins>schema language</ins> MUST provide meaningful and useful constraint rules for the 5 JSON value types: `boolean`, `number`, `string`, `object`, and `array`.

1. The <ins>schema language</ins> MUST support schema descriptions for any and all legal JSON documents, as specified by [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

1. The <ins>schema language</ins> MUST be free-of and agnostic-to patterns specific to any particular programming language.

1. The <ins>schema language</ins> MUST be able to describe itself.

#### 2.1.3 Specification

For a detailed specification of the <ins>schema language</ins>, see [<ins>JSON Schema Definition Language</ins>](/schema).

#### 2.1.4 Example Usage

The <ins>JSON Schema Definition Language</ins> can be expressed in 2 forms: JSD (Json Schema Document), and JSDX (JSD in XML semantics). The following illustrates an example JSD and JSDX. Expressing the <ins>schema document</ins> in JSDX allows a developer to leverage the edit-time validation features of XML IDEs such as [oXygen XML Editor](https://www.oxygenxml.com/).

##### 2.1.4.1 JSD

```json
{
  "jsd:ns": "http://www.jsonx.org/schema-0.2.2.jsd",
  "jsd:schemaLocation": "http://www.jsonx.org/schema-0.2.2.jsd http://www.jsonx.org/schema-0.2.2.jsd",
  "boolean": { "jsd:class": "boolean" },
  "number": { "jsd:class": "number", "jsd:range": "[-1,1)" },
  "string": { "jsd:class": "string", "jsd:pattern": "pattern" },
  "object": { "jsd:class": "object", "jsd:properties": {
    "array": { "jsd:class": "array", "jsd:elements": [
      { "jsd:class": "boolean" },
      { "jsd:class": "number", "jsd:range": "[-1,1)" },
      { "jsd:class": "string", "jsd:pattern": "pattern" },
      { "jsd:class": "array", "jsd:elements": [
        { "jsd:class": "boolean"},
        { "jsd:class": "number", "jsd:range": "[-1,1)"},
        { "jsd:class": "string", "jsd:pattern": "pattern" },
        { "jsd:class": "any", "jsd:types": "boolean number string array object" }]
      },
      { "jsd:class": "reference", "jsd:type": "object" },
      { "jsd:class": "any", "jsd:types": "boolean number string array object" }]
    }
  }}
}
```

##### 2.1.4.2 JSDX

```xml
<schema
  xmlns="http://www.jsonx.org/schema-0.2.2.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/schema-0.2.2.xsd http://www.jsonx.org/schema-0.2.2.xsd">
  <boolean name="boolean"/>
  <number name="number" form="real" range="[-1,1)"/>
  <string name="string" pattern="pattern"/>
  <object name="object">
    <property name="array" xsi:type="array">
      <boolean/>
      <number form="real" range="[-1,1)"/>
      <string pattern="pattern"/>
      <array>
        <boolean/>
        <number form="real" range="[-1,1)"/>
        <string pattern="pattern"/>
        <any types="boolean number string array object"/>
      </array>
      <reference type="object"/>
      <any types="boolean number string array object"/>
    </property>
  </object>
</schema>
```

### 2.2 <ins>JSON/Java Binding API</ins>

Provides a way for JSON objects whose structure is expressed in the [<ins>JSON Schema Definition Language</ins>][schema] to be validated, parsed and marshaled, to and from Java objects of strongly-typed classes.

#### 2.2.1 Purpose

Provide a <ins>binding API</ins> for parsing and marshaling JSON documents to and from strongly-typed Java classes.

#### 2.2.2 Requirements

1. The <ins>binding API</ins> MUST be able to model the full scope of normative meaning, usage, constraints and relationships of the constituent parts of a JSON document as specifiable with the <ins>schema language</ins>.

1. The <ins>binding API</ins> MUST enforce (via validation) the full scope of normative meaning, usage, constraints and relationships of the constituent parts of a JSON document as specifiable in the <ins>schema language</ins>.

1. The <ins>binding API</ins> MUST produce clear and useful error messages when exception of <ins>schema document</ins> constraints are encountered during validation of JSON documents.

1. The <ins>binding API</ins> MUST constrain the constituent parts of a <ins>schema document</ins> to Java type bindings that are as lightweight as necessary to retain the full normative scope of specification of the <ins>schema language</ins>.

1. The <ins>binding API</ins> MUST use light coupling, not imposing requirements for exclusionary patterns onto a class model of binding classes.

1. The <ins>binding API</ins> MUST offer easy patterns for manual description of bindings.

1. The <ins>binding API</ins> MUST be straightforward, intuitive, and resilient to human error.

#### 2.2.3 Specification

For a detailed specification of the <ins>binding API</ins>, see [<ins>JSON/Java Binding API</ins>](/binding).

#### 2.2.4 Example Usage

The <ins>JSON/Java Binding API</ins> uses annotations to bind class definitions to usage, constraints and relationships specifiable in the <ins>schema language</ins>. The following example illustrates simple usage of the <ins>binding API</ins>. Set-ters and get-ters have been replaced with public fields for conciseness.

```java
public class Book {
  @StringProperty(pattern="(\\S)|(\\S.*\\S)", nullable=false)
  public String title;

  @StringElement(id=1, pattern="\\S+ \\S+", nullable=false)
  @ArrayProperty(use=Use.OPTIONAL, elementIds=1)
  public Optional<List<String>> authors;

  @StringElement(id=1, pattern="\\S+ \\S+", nullable=false)
  @ArrayProperty(use=Use.OPTIONAL, elementIds=1)
  public Optional<List<String>> editors;

  @ObjectElement(id=1, type=Publishing.class, nullable=false)
  @ArrayProperty(elementIds=1)
  public List<Publishing> publishings;

  @StringProperty(pattern="\\d{3}-\\d-\\d{2}-\\d{6}-\\d")
  public String isbn;

  /**
   * [[1, "Part 1, Chapter 1"], [2, "Part 1, Chapter 2"], [3, "Part 1, Chapter 3"],
   *  [1, "Part 2, Chapter 1"], [2, "Part 2, Chapter 2"], [3, "Part 2, Chapter 3"]...]
   */
  @StringElement(id=3, pattern="(\\S)|(\\S.*\\S)", nullable=false, maxOccurs=1)
  @NumberElement(id=2, range="[1,]", nullable=false, maxOccurs=1)
  @ArrayElement(id=1, nullable=false, elementIds={2, 3})
  @ArrayProperty(elementIds={1})
  public List<Object> index;
}
```

### 2.3 <ins>JSD Binding Generator</ins>

Consumes a JSD schema, and generates classes that use the <ins>JSON/Java Binding API</ins> to achieve binding between JSON documents conforming to a JSD schema, and Java object represetations of these documents.

#### 2.3.1 Purpose

Provide a <ins>binding generator</ins> utility for automatic generation of binding classes from a <ins>schema document</ins>.

#### 2.3.2 Requirements

1. The <ins>binding generator</ins> MUST be able to consume a <ins>schema document</ins>, and produce Java class definitions (`.java` files) that use the <ins>binding API</ins>.

1. The <ins>binding generator</ins> MUST be able to consume Java class definitions (`.class` files) utilizing the <ins>binding API</ins>, and produce a <ins>schema document</ins>.

1. The <ins>binding generator</ins> MUST create Java classes (`.java` files) that encode the full normative scope of the <ins>schema document</ins>.

1. The <ins>binding generator</ins> MUST represent the constituent parts of a <ins>schema document</ins> with Java type bindings that are as strongly-typed as possible, but not limiting in any way with regard to the definition of the respective constituent part.

1. The <ins>binding generator</ins> MUST be able to validate a <ins>schema document</ins>.

#### 2.3.3 Specification

For a detailed specification of the <ins>binding generator</ins>, see [<ins>JSD Binding Generator</ins>](/generator).

#### 2.3.4 Example Usage

The <ins>JSD Binding Generator</ins> provides convenience utilities for generating bindings and converting <ins>schema document</ins>s. The following illustrates example usage of the `Generator` and `Converter` executable classes.

##### 2.3.4.1 `Generator`

The following example generates binding classes (`.java` files) in `target/generated-sources/jsonx` for the <ins>schema document</ins> at `src/main/resources/example.jsd`, with prefix `org.example$`.

```bash
java -cp ... org.jsonx.Generator --prefix org.example$ -d target/generated-sources/jsonx src/main/resources/example.jsd
```

##### 2.3.4.2 `Converter`

The following example converts the JSD file at `src/main/resources/example.jsd` to a JSDX file in `target/generated-resources`.

```bash
java -cp ... org.jsonx.Converter src/main/resources/example.jsd target/generated-resources/example.jsdx
```

### 2.4 <ins>JSONX-JSON</ins>

Offers facilities for validating and converting JSON and JSONX documents (JSONX is JSON expressed in XML syntax).

#### 2.4.1 Purpose

Provide an encoding of JSON documents in an analogous form that uses XML semantics, referred to as <ins>JSONX documents</ins>.

#### 2.4.2 Requirements

1. The <ins>JSONX documents</ins> MUST be able to represent any and all legal JSON documents, as specified by [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

1. The <ins>JSONX documents</ins> MUST be translatable to JSON documents, and vice versa, preserving all normative and non-normative features of the original document.

1. The <ins>JSONX documents</ins> MUST provide meaningful and useful validation features via XSD validation.

#### 2.4.3 Specification

For a detailed specification of JSONX, see [<ins>JSONX-JSON</ins>](/json).

#### 2.4.4 Example Usage

The <ins>JSONX-JSON</ins> sub-project provides convenience utilities for converting and validating JSON and JSONX documents. The following illustrates example usage of the `JxConverter` class.

##### 2.4.4.1 JSON->JSONX

```java
String jsonx = JxConverter.jsonToJsonx(new JsonReader(new FileReader("example.json")));
```

##### 2.4.4.1 JSONX->JSON

```java
String json = JxConverter.jsonxToJson(new FileInputStream("example.jsonx"));
```

### 2.5 <ins>JAX-RS Integration for JSONX</ins>

Implements the `MessageBodyReader` and `MessageBodyWriter` interfaces in the JAX-RS API to integrate with JAX-RS server runtimes.

#### 2.5.1 Purpose

Provide <ins>JAX-RS integration</ins> for parsing and marshaling Java object instances of binding classes in a JAX-RS runtime.

#### 2.5.2 Requirements

1. The <ins>JAX-RS integration</ins> MUST support any JAX-RS application that implements the facets relevant to parsing and marshaling of entity object, as defined in the [JAX-RS 2.0 Specification](https://download.oracle.com/otn-pub/jcp/jaxrs-2_0-fr-eval-spec/jsr339-jaxrs-2.0-final-spec.pdf).

1. The <ins>JAX-RS integration</ins> MUST be automatic and free of any configuration that would couple an application to the <ins>JSONX Framework</ins>.

#### 2.5.3 Specification

For a detailed specification of <ins>JAX-RS integration</ins>, see [<ins>JAX-RS Integration for JSONX</ins>](/rs).

#### 2.5.4 Example Usage

The <ins>JAX-RS Integration for JSONX</ins> sub-project provides `MessageBodyReader` and `MessageBodyWriter` providers that can be registered with a JAX-RS runtime. The following illustrates example usage.

##### 2.5.4.1 Schema in JSDX

```xml
<schema
  xmlns="http://www.jsonx.org/schema-0.2.2.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/schema-0.2.2.xsd http://www.jsonx.org/schema.xsd">
  <object name="account">
    <property xsi:type="string" name="email" use="optional" nullable="false" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}"/>
    <property xsi:type="string" name="token" use="optional" nullable="false" pattern="(\S)|(\S.*\S)"/>
    <property xsi:type="string" name="lastLogin" use="optional" nullable="false" pattern="[1-9][0-9]{3}-[01]?[0-9]-[0-3]?[0-9]T[0-2][0-9]:[0-5][0-9]:[0-5][0-9][\+-](([01][0-9])|(2[0-3])):[0-5][0-9]"/>
  </object>
</schema>
```

##### 2.5.4.2 Application

```java
public class MyApplication extends javax.ws.rs.core.Application {
  @Override
  public Set<Object> getSingletons() {
    return Collections.singleton(new JxObjectProvider(JxEncoder._2));
  }
}

@Path("/account")
@RolesAllowed("registered")
public class AccountService {
  @GET
  @Produces("application/vnd.example.v1+json")
  public Account get(@Context SecurityContext securityContext) {
    Account account = new Account();
    ...
    return account;
  }

  @POST
  @Consumes("application/vnd.example.v1+json")
  public void post(@Context SecurityContext securityContext, Account account) {
    ...
  }
}

```

### 2.6 <ins>JSONX Maven Plugin</ins>

A Maven plugin for generating JSONX and JSD bindings.

#### 2.6.1 Purpose

Provide schema validation, code generation, and other convenience utlities in a <ins>Maven plugin</ins>.

#### 2.6.2 Requirements

1. The <ins>Maven plugin</ins> MUST offer utilities for the generation of binding classes from a specified <ins>schema document</ins>.

1. The <ins>Maven plugin</ins> MUST offer utilities for validation of <ins>schema document</ins>s and binding classes.

1. The <ins>Maven plugin</ins> MUST present clear and informative errors and warnings that arise during parsing and validation of <ins>schema document</ins>s and JSON documents with an associated schema.

#### 2.6.3 Specification

For a detailed specification of the Maven plugin, see [<ins>JSONX Maven Plugin</ins>](/maven-plugin).

#### 2.6.4 Example Usage

The <ins>JSONX Maven Plugin</ins> implements a Maven MOJO that can be used in a `pom.xml`. The following illustrates an example usage.

```xml
<plugin>
  <groupId>org.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.2.2</version>
  <executions>
    <execution>
      <goals>
        <goal>generate</goal>
      </goals>
      <phase>generate-sources</phase>
      <configuration>
        <destDir>${project.build.directory}/generated-sources/jsonx</destDir>
        <prefix>com.example.json.</prefix>
        <schemas>
          <schema>src/main/resources/schema.jsd</schema>
        </schemas>
      </configuration>
    </execution>
  </executions>
</plugin>
```

## 3 Getting Started

The following example presents a use-case involving a <ins>schema document</ins> that defines JSON messages consumed and produced by binding classes in Java.

### 3.1 Prerequisites

* [Java 8][jdk8-download] - The minimum required JDK version.
* [Maven][maven] - The dependency management system.

### 3.2 Example

1. Create an `example.jsonx` and put it in `src/main/resources/`.

  ```xml
  <schema
    xmlns="http://www.jsonx.org/schema-0.2.2.xsd"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.jsonx.org/schema-0.2.2.xsd http://www.jsonx.org/schema.xsd">

    <object name="id" abstract="true">
      <property xsi:type="string" name="id" pattern="[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}" nullable="false"/>
    </object>

    <object name="idVersion" abstract="true" extends="id">
      <property xsi:type="number" name="version" nullable="false"/>
    </object>

    <object name="ids">
      <property xsi:type="array" name="id" nullable="false">
        <string pattern="[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}" nullable="false"/>
      </property>
    </object>

    <object name="credentials">
      <property xsi:type="string" name="email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}" nullable="false"/>
      <property xsi:type="string" name="password" pattern="[0-9a-f]{64}" use="optional" nullable="false"/>
    </object>

    <object name="account" extends="credentials">
      <property xsi:type="string" name="id" pattern="[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}" nullable="false" use="optional"/>
      <property xsi:type="string" name="firstName" nullable="false"/>
      <property xsi:type="string" name="lastName" nullable="false"/>
    </object>

  </schema>
  ```

1. Add the [`org.jsonx:jsonx-maven-plugin`][jsonx-maven-plugin] to the POM.

  ```xml
  <plugin>
    <groupId>org.jsonx</groupId>
    <artifactId>jsonx-maven-plugin</artifactId>
    <version>0.2.2</version>
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

1. Add the `org.jsonx:jsonx-generator` dependency to the POM.

  ```xml
  <dependency>
    <groupId>org.jsonx</groupId>
    <artifactId>jsonx-generator</artifactId>
    <version>0.2.2</version>
  </dependency>
  ```

1. Upon successful execution of the [`jsonx-maven-plugin`][jsonx-maven-plugin] plugin, a class by the name of `json` (as was specified in the `<prefix>` element of the `<configuration>` in the `jsonx-maven-plugin` definition) will be generated in `generated-sources/jsonx`. Add this path to your Build Paths in your IDE to integrate into your project.

1. The generated classes can be instantiated as any other Java objects. They are strongly typed, and will guide you in proper construction of a JSON message. The following APIs can be used for parsing and marshalling <ins>JSONX</ins> to and from JSON:

  To parse JSON to <ins>JSONX</ins> Bindings:

  ```java
  String json = "{\"email\":\"john@doe\",\"password\":\"066b91577bc547e21aa329c74d74b0e53e29534d4cc0ad455abba050121a9557\"}";
  Credentials credentials = JxDecoder.parseObject(Credentials.class, new JsonReader(new StringReader(json)));
  ```

  To marshal <ins>JSONX</ins> Bindings to JSON:

  ```java
  String json2 = JxEncoder.get().marshal(credentials);
  assertEquals(json, json2);
  ```

## Contributing

Pull requests are welcome. For major changes, please [open an issue](../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#introduction]: #1-introduction
[#conventions]: #11-conventions-used-in-this-document
[#jsonxframework]: #2-jsonx-framework
[#jsd]: #21-json-schema-definition-language
[#1purpose]: #211-purpose
[#1requirements]: #212-requirements
[#1specification]: #213-specification
[#1example]: #214-example-usage
[#binding]: #22-jsonjava-binding-api
[#2purpose]: #221-purpose
[#2requirements]: #222-requirements
[#2specification]: #223-specification
[#2example]: #224-example-usage
[#generator]: #23-generator
[#3purpose]: #231-purpose
[#3requirements]: #232-requirements
[#3specification]: #233-specification
[#3example]: #234-example-usage
[#jsonx-json]: #24-jsonx-json
[#4purpose]: #241-purpose
[#4requirements]: #242-requirements
[#4specification]: #243-specification
[#4example]: #244-example-usage
[#jaxrs]: #25-jax-rs-integration-for-jsonx
[#5purpose]: #251-purpose
[#5requirements]: #252-requirements
[#5specification]: #253-specification
[#5example]: #254-example-usage
[#maven-plugin]: #26-jsonx-maven-plugin
[#6purpose]: #261-purpose
[#6requirements]: #262-requirements
[#6specification]: #263-specification
[#6example]: #264-example-usage
[#gettingstarted]: #3-getting-started
[#prerequisites]: #31-prerequisites
[#example]: #32-example

[jdk8-download]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[jsonx-maven-plugin]: /maven-plugin/
[maven-archetype-quickstart]: http://maven.apache.org/archetypes/maven-archetype-quickstart/
[maven]: https://maven.apache.org/
[schema]: /schema/
[xmlschema]: http://www.w3.org/2001/XMLSchema