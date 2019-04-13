# JSONX Framework

> JSON Schema, Validation, Java Binding

[![Build Status](https://travis-ci.org/openjax/jsonx.png)](https://travis-ci.org/openjax/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/openjax/jsonx/badge.svg)](https://coveralls.io/github/openjax/jsonx)

## Abstract

The <ins>JSONX Framework</ins> is a collection of specifications and reference implementations based on the <ins>JSON Schema Definition Language</ins>. This document describes the <ins>JSONX Framework</ins>, and presents a directory of links to its constituent parts and related resources.

## Table of Contents

## 1 Introduction

The <ins>JSONX Framework</ins> helps developers work with JSON, offering functionalities intended to systematically reduce errors and pain-points commonly encountered when parsing, marshaling, asserting the correctness of JSON messages, and even developing software that interfaces with JSON in one way or another. The <ins>JSONX Framework</ins> leverages the <ins>JSON Schema Definition Language</ins> to represent the structure, usage, constraints and relationships of constituent parts of a JSON document. The <ins>JSONX Framework</ins> provides reference implementations of validation and Java class binding capabilities for JSON documents conforming to a respective schema document.

### 1.1 Dependencies on Other Specifications

The definition of the <ins>JSON Schema Definition Language</ins> depends on the following specifications: [\[RFC4627\]][rfc4627] and [\[XMLSchema\]][xmlschema].

### 1.2 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Purpose

The <ins>JSONX Framework</ins> was created to help developers solve common problems related to various use-cases regarding JSON. The following list identifies several common problems for which the <ins>JSONX Framework</ins> provides solutions. The sub-lists define requirements related to the respective problem.

1. Provide a schema language to describe normative contracts between producer and consumer ends of a JSON document protocol.
   
   1. The schema language must constrain and document the meaning, usage, constraints and relationships of the constituent parts of a JSON document.

   1. The schema language must provide meaningful and useful constraint rules for the 5 JSON value types: boolean, number, string, object, and array.

   1. The schema language must support schema descriptions for the full scope of variability of JSON documents, as defined in [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

   1. The schema language must be free of and agnostic to patterns specific to any particular programming languages.

   1. The schema language must be able to describe itself.

1. Provide a binding API for parsing and marshaling JSON documents to and from strongly-typed Java classes.

   1. The binding API must be able to model the full scope of constraints defineable in the schema language.

   1. The binding API must enforce (via validation) the constraint rules defined in the schema document.

   1. The binding API must constrain the 5 JSON value types to lightweight Java type bindings.

   1. The binding API must produce clear and useful error messages upon exception of schema document constraints during validation of JSON documents.

   1. The binding API must use light coupling, not imposing requirements for exclusionary patterns onto a class model.

   1. The binding API must offer easy patterns for manual description of binding relationships to schema documents.

   1. The binding API must be straightforward, intuitive, and resilient to potential human error.

1. Provide a code generation utility for automatic creation of binding classes from a schema document.

   1. The binding generator must be able to consume a schema document and produce Java class definitions utilizing the binding API.

   1. The binding generator must be able to consume Java class definitions utilizing the binding API and produce a schema document.

   1. The binding generator must create Java classes that encode the full normative scope of the schema document.

   1. The binding generator must be able to validate a schema document.

1. Provide an analogous encoding of JSON documents in XML semantics, named JSONX.

   1. The JSONX documents must be able to support the full scope of variability of JSON documents, as defined in [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

   1. The JSONX documents must be translatable to JSON document and back, preserving all normative and non-normative features of the original document.

   1. The JSONX documents must provide meaningful and useful validation features via XSD validation.

1. Provide direct integration for parsing and marshaling binding objects for JAX-RS runtimes.

   1. The JAX-RS integration must support any JAX-RS application as defined in [JAX-RS 2.1 Spec](http://download.oracle.com/otn-pub/jcp/jaxrs-2_1-pfd-spec/jaxrs-2_1-pfd-spec.pdf).

   1. The JAX-RS integration must be automatic and free of requirement for configuration that would couple an application to the <ins>JSONX Framework</ins>.

1. Provide schema validation and code generation facilities in a Maven plugin.

   1. The Maven plugin must present errors and warnings that arise during parsing and validation of schema documents, and JSON documents with an associated schema.

   1. The Maven plugin must offer facilities to generate binding classes conforming to specified schema documents.

## 3 <ins>JSONX Framework</ins>

The <ins>JSONX Framework</ins> is comprised of 6 constituent parts:

### 3.1 JSON Schema Definition Language

   Describe JSON documents by using schema components to constrain and document the meaning, usage and relationships of their constituent parts: value types and their content.

   For a detailed specification of the schema language, see [<ins>JSON Schema Definition Language</ins>](/schema).

### 3.2 JSON/Java Binding API

   Provides a way for JSON objects whose structure is expressed in the [<ins>JSON Schema Definition Language</ins>][jsd] to be validated, and parsed and marshaled, to and from Java objects of strongly-typed classes.

   For a detailed specification of the binding API, see [<ins>JSON/Java Binding API</ins>](/binding).

### 3.3 JSD Binding Generator

   Consumes a JSD schema, and generates classes that use the <ins>JSON/Java Binding API</ins> to achieve binding between JSON documents conforming to a JSD schema, and Java object represetations of these documents.

   For a detailed specification of the binding generator, see [<ins>JSD Binding Generator</ins>](/generator).

### 3.4 JSONX-JSON

   Offers facilities for validating and converting JSON and JSONX documents (JSONX is JSON expressed in XML syntax).

   For a detailed specification of JSONX, see [<ins>JSONX-JSON</ins>](/json).

### 3.5 JAX-RS Integration for JSONX

   Implements the `MessageBodyReader` and `MessageBodyWriter` interfaces in the JAX-RS API to integrate with JAX-RS server runtimes.

   For a detailed specification of JAX-RS integration, see [<ins>JAX-RS Integration for JSONX</ins>](/rs).

### 3.6 JSONX Maven Plugin

   A Maven plugin for generating JSONX and JSD bindings.

   For a detailed specification of the Maven plugin, see [<ins>JSONX Maven Plugin</ins>](/maven-plugin).

## Usage

### Getting Started

The following example presents a use-case involving a schema document that defines JSON messages consumed and produced by binding classes in Java.

#### Prerequisites

* [Java 8][jdk8-download] - The minimum required JDK version.
* [Maven][maven] - The dependency management system.

#### Example

1. In your preferred development directory, create a [`maven-archetype-quickstart`][maven-archetype-quickstart] project.

  ```bash
  mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
  ```

2. Create an `example.jsonx` and put it in `src/main/resources/`.

  ```xml
  <schema
    xmlns="http://jsonx.openjax.org/schema-0.9.8.xsd"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://jsonx.openjax.org/schema-0.9.8.xsd http://jsonx.openjax.org/schema.xsd">

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

6. The generated classes can be instantiated as any other Java objects. They are strongly typed, and will guide you in proper construction of a JSON message. The following APIs can be used for parsing and marshalling <ins>JSONX</ins> to and from JSON:

  To parse JSON to <ins>JSONX</ins> Bindings:

  ```java
  String json = "{\"email\":\"john@doe\",\"password\":\"066b91577bc547e21aa329c74d74b0e53e29534d4cc0ad455abba050121a9557\"}";
  json.Credentials credentials = JxDecoder.parseObject(json.Credentials.class, new JsonReader(new StringReader(json)));
  ```
  
  To marshal <ins>JSONX</ins> Bindings to JSON:

  ```java
  String json2 = JxEncoder.get().marshal(credentials);
  assertEquals(json, json2);
  ```

### JavaDocs

JavaDocs are available [here](https://jsonx.openjax.org/apidocs/).

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[jdk8-download]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[jsd]: ../schema
[jsonx-maven-plugin]: /maven-plugin
[maven-archetype-quickstart]: http://maven.apache.org/archetypes/maven-archetype-quickstart/
[maven]: https://maven.apache.org/