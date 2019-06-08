# JSONx Framework

[![Build Status](https://travis-ci.org/jsonxorg/jsonx.svg?Yrn6JXVz)](https://travis-ci.org/jsonxorg/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/jsonxorg/jsonx/badge.svg?Yrn6JXVz)](https://coveralls.io/github/jsonxorg/jsonx)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/jsonx.svg?Yrn6JXVz)](https://www.javadoc.io/doc/org.jsonx/jsonx)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/jsonx.svg?Yrn6JXVz)](https://mvnrepository.com/artifact/org.jsonx/jsonx)

## Abstract

The <ins>JSONx Framework</ins> is a collection of specifications and reference implementations that provide <ins>structural</ins> and <ins>functional</ins> patterns intended to help developers work with JSON. The <ins>JSONx Framework</ins> defines the [<ins>JSON Schema Definition Language</ins>][schema], which is a <ins>schema language</ins> inspired by the [XMLSchema][xmlschema] specification.

This document introduces the <ins>JSONx Framework</ins>, and presents a directory of links to its constituent parts and related resources.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction](#1-introduction)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document](#11-conventions-used-in-this-document)<br>
<samp>&nbsp;&nbsp;</samp>2 [Use-Cases](#2-use-cases)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>2.1 [Consumer Driven Contracts](#21-consumer-driven-contracts)<br>
<samp>&nbsp;&nbsp;</samp>3 [<ins>JSON Schema Definition Language</ins>][#jsd]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>3.1 [Purpose](#31-purpose)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>3.2 [Requirements](#32-requirements)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>3.3 [**Getting Started**](#33-getting-started)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>3.4 [Specification](#34-specification)<br>
<samp>&nbsp;&nbsp;</samp>4 [<ins>JSON/Java Binding API</ins>](#4-jsonjava-binding-api)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [Purpose](#41-purpose)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [Requirements](#42-requirements)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.3 [**Getting Started**][#invoice-example]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.4 [Specification](#44-specification)<br>
<samp>&nbsp;&nbsp;</samp>5 [<ins>JSD Binding Generator</ins>](#5-jsd-binding-generator)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1 [Purpose](#51-purpose)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2 [Requirements](#52-requirements)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.3 [**Getting Started**](#53-getting-started)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.3.1 [Generator](#531-generator)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.3.2 [Converter][#converter]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.4 [Specification](#54-specification)<br>
<samp>&nbsp;&nbsp;</samp>6 [<ins>JSONx-JSON</ins>](#6-jsonx-json)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.1 [Purpose](#61-purpose)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.2 [Requirements](#62-requirements)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.3 [**Getting Started**](#63-getting-started)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.3.1 [JSON-JSONx](#631-json-jsonx)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.3.2 [JSONx-JSON](#632-jsonx-json)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.4 [Specification](#64-specification)<br>
<samp>&nbsp;&nbsp;</samp>7 [<ins>JAX-RS Integration for JSONx</ins>](#7-jax-rs-integration-for-jsonx)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>7.1 [Purpose](#71-purpose)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>7.2 [Requirements](#72-requirements)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>7.3 [**Getting Started**](#73-getting-started)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>7.4 [Specification](#74-specification)<br>
<samp>&nbsp;&nbsp;</samp>8 [<ins>JSONx Maven Plugin</ins>](#8-jsonx-maven-plugin)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>8.1 [Purpose](#81-purpose)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>8.2 [Requirements](#82-requirements)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>8.3 [**Getting Started**](#83-getting-started)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>8.4 [Specification](#84-specification)<br>
<samp>&nbsp;&nbsp;</samp>9 [Contributing](#9-contributing)<br>
<samp>&nbsp;&nbsp;</samp>10 [License](#10-license)

## 1 Introduction

The <ins>JSONx Framework</ins> was created to help developers solve common problems related to common use-cases regarding JSON. The <ins>JSONx Framework</ins> offers <ins>structural</ins> and <ins>functional</ins> patterns that systematically reduce errors and pain-points commonly encountered when developing software that interfaces with JSON. The <ins>structural</ins> patterns are defined in the [<ins>JSON Schema Definition Language</ins>][schema], which is a programming-language-agnostic <ins>schema language</ins> to describe constraints and document the meaning, usage and relationships of the constituent parts of JSON documents. The <ins>functional</ins> patterns are reference implementations (on the Java platform) of the specification of the <ins>schema language</ins>, providing utilities that address common use-cases for applications that use JSON in one way or another. Common use-cases include:

1. Definition of a normative contract between a producer and consumer of JSON documents.
1. Validation of JSON documents conforming to a respective <ins>schema document</ins>.
1. Java class binding capabilities for JSON documents conforming to a respective <ins>schema document</ins>.

### 1.1 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Use-Cases

The following sections lists common use-cases that the <ins>JSONx Framework</ins> fulfills.

### 2.1 Consumer Driven Contracts

The <ins>JSONx Framework</ins> was created specifically with [<ins>Consumer Driven Contracts</ins>][cdc] in mind. With the [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd], one can create a <ins>Consumer Driven Contract (CDC)</ins> with an evolution model based on schema versioning. The <ins>JSD</ins> can be used by producers and consumers to validate documents in a communication protocol.

The following example illustrates a simple protocol that uses the CDC approach, and consists of the actors:

1. **Producer**: Representing the provider of the <ins>ProductSearch</ins> service.
1. **Consumer1**: The first consumer of the <ins>ProductSearch</ins> service.
1. **Consumer2**: The second consumer of the <ins>ProductSearch</ins> service.

Consider a simple <ins>ProductSearch</ins> service, which allows consumer applications to search a product catalogue.

Version **v1** of the protocol defines the contract:

* **Request**

  ```
  GET /ProductSearch?name=<name>
  ```

* **Response**

  ```diff
  {
    "Version": "v1",
    "CatalogueID": <number>,
    "Name": <string>,
    "Price": <string>,
    "Manufacturer": <string>,
    "InStock": <boolean>
  }
  ```

The <ins>JSD</ins> that describes the **Response** contract is:

```json
{
  "jsd:ns": "http://www.jsonx.org/schema-0.2.2.jsd",
  "jsd:schemaLocation": "http://www.jsonx.org/schema-0.2.2.jsd http://www.jsonx.org/schema-0.2.2.jsd",

  "product": { "jsd:type": "object", "abstract": true, "properties": {
    "CatalogueID": { "jsd:type": "number", "form": "integer", "nullable": false, "range": "[1,]" },
    "Name": { "jsd:type": "string", "nullable": false, "pattern": "\\S|\\S.*\\S" },
    "Price": { "jsd:type": "string", "nullable": false, "pattern": "\\$\\d+\\.\\d{2}" },
    "Manufacturer": { "jsd:type": "string", "nullable": false, "pattern": "\\S|\\S.*\\S" },
    "InStock": { "jsd:type": "boolean", "nullable": false} } },

  "product1": { "jsd:type": "object", "extends": "product", "properties": {
    "Version": { "jsd:type": "string", "nullable": false, "pattern": "v1"} } }
}
```

**(Alternatively)** The <ins>JSDx</ins> form of the **Response** contract is:

<sub>_**Note:** The [Converter][#converter] utility automatically converts between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

```xml
<schema
  xmlns="http://www.jsonx.org/schema-0.2.2.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/schema-0.2.2.xsd http://www.jsonx.org/schema-0.2.2.xsd">

  <object name="product" abstract="true">
    <property name="CatalogueID" xsi:type="number" form="integer" range="[1,]" nullable="false"/>
    <property name="Name" xsi:type="string" pattern="\S|\S.*\S" nullable="false"/>
    <property name="Price" xsi:type="string" pattern="\$\d+\.\d{2}" nullable="false"/>
    <property name="Manufacturer" xsi:type="string" pattern="\S|\S.*\S" nullable="false"/>
    <property name="InStock" xsi:type="boolean" nullable="false"/>
  </object>

  <object name="product1" extends="product">
    <property name="Version" xsi:type="string" pattern="v1" nullable="false"/>
  </object>

</schema>
```

All actors -- **Producer**, **Consumer1**, and **Consumer2** -- agree on the contract, and implement and integrate the protocol into their systems. To assert receipt of contract-compliant documents, all actors use the contract definition to automatically validate received and sent messages.

After many months of running in production, **Consumer2** issues a request to the **Producer** to provide additional information in the response. Specifically, **Consumer2** requests for the addition of another field in the JSON response:

```diff
{
- "Version": "v1.0",
+ "Version": "v2.0",
  "CatalogueID": <number>,
  "Name": <string>,
  "Price": <string>,
  "Manufacturer": <string>,
  "InStock": <boolean>,
+ "Description": <string>
}
```

To satisfy **Consumer2**'s request, the contract is updated to support version **v2** of the **Response**:


```diff
{
  "jsd:ns": "http://www.jsonx.org/schema-0.2.2.jsd",
  "jsd:schemaLocation": "http://www.jsonx.org/schema-0.2.2.jsd http://www.jsonx.org/schema-0.2.2.jsd",

  "product": { "jsd:type": "object", "abstract": true, "properties": {
    "CatalogueID": { "jsd:type": "number", "form": "integer", "nullable": false, "range": "[1,]" },
    "Name": { "jsd:type": "string", "nullable": false, "pattern": "\\S|\\S.*\\S" },
    "Price": { "jsd:type": "string", "nullable": false, "pattern": "\\$\\d+\\.\\d{2}" },
    "Manufacturer": { "jsd:type": "string", "nullable": false, "pattern": "\\S|\\S.*\\S" },
    "InStock": { "jsd:type": "boolean", "nullable": false} } },

  "product1": { "jsd:type": "object", "extends": "product", "properties": {
    "Version": { "jsd:type": "string", "nullable": false, "pattern": "v1"} } },

+ "product2": { "jsd:type": "object", "extends": "product", "properties": {
+   "Version": { "jsd:type": "string", "nullable": false, "pattern": "v2" },
+   "Description": { "jsd:type": "string", "nullable": false, "pattern": "\\S|\\S.*\\S" } } }
}
```

**(Alternatively)** The <ins>JSDx</ins> form of the change to the **Response** contract is:

<sub>_**Note:** The [Converter][#converter] utility automatically converts between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

```diff
<schema
  xmlns="http://www.jsonx.org/schema-0.2.2.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/schema-0.2.2.xsd http://www.jsonx.org/schema-0.2.2.xsd">

  <object name="product" abstract="true">
    <property name="CatalogueID" xsi:type="number" form="integer" range="[1,]" nullable="false"/>
    <property name="Name" xsi:type="string" pattern="\S|\S.*\S" nullable="false"/>
    <property name="Price" xsi:type="string" pattern="\$\d+\.\d{2}" nullable="false"/>
    <property name="Manufacturer" xsi:type="string" pattern="\S|\S.*\S" nullable="false"/>
    <property name="InStock" xsi:type="boolean" nullable="false"/>
  </object>

  <object name="product1" extends="product">
    <property name="Version" xsi:type="string" pattern="v1" nullable="false"/>
  </object>

+ <object name="product2" extends="product">
+   <property name="Version" xsi:type="string" pattern="v2" nullable="false"/>
+   <property name="Description" xsi:type="string" pattern="\S|\S.*\S" nullable="false"/>
+ </object>

</schema>
```

With this approach, the **v2** evolution of the contract satisfies **Customer2**. And, since the contract also retains support for **v1**, integration with **Customer1** is unaffected.

_For the application code, see **[<ins>Consumer Driven Contracts</ins>](/sample/cdc)**._

## 3 <ins>JSON Schema Definition Language</ins>

Describes JSON documents using schema components to constrain and document the meaning, usage and relationships of their constituent parts: value types and their content.

### 3.1 Purpose

Provide a <ins>schema language</ins> to describe normative contracts between producer and consumer ends of a protocol exchanging JSON documents.

### 3.2 Requirements

1. The <ins>schema language</ins> MUST constrain and document the meaning, usage, constraints and relationships of the constituent parts of a JSON document.

1. The <ins>schema language</ins> MUST provide meaningful and useful constraint rules for the 5 JSON value types:

   `boolean`, `number`, `string`, `object`, `array`.

1. The <ins>schema language</ins> MUST support schema descriptions for any and all legal JSON documents, as specified by [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

1. The <ins>schema language</ins> MUST be free-of and agnostic-to patterns specific to any particular programming language.

1. The <ins>schema language</ins> MUST be able to describe itself.

### 3.3 Getting Started

The <ins>JSON Schema Definition Language</ins> can be expressed in 2 forms: <ins>JSD (Json Schema Document)</ins>, and <ins>JSDx (JSD in XML semantics)</ins>.

1. Create `schema.jsd` with the following content:

   ```json
   {
     "jsd:ns": "http://www.jsonx.org/schema-0.2.2.jsd",
     "jsd:schemaLocation": "http://www.jsonx.org/schema-0.2.2.jsd http://www.jsonx.org/schema-0.2.2.jsd",

     "myNumber": { "jsd:type": "number", "range": "[-1,1)" },
     "myString": { "jsd:type": "string", "pattern": "[a-z]+" },
     "myObject": {
       "jsd:type": "object", "properties": {
         "myArray": {
           "jsd:type": "array", "elements": [
             { "jsd:type": "boolean" },
             { "jsd:type": "reference", "type": "myNumber" },
             { "jsd:type": "reference", "type": "myString" },
             { "jsd:type": "array", "elements": [
               { "jsd:type": "boolean" },
               { "jsd:type": "number", "form": "integer", "range": "[0,100]" },
               { "jsd:type": "string", "pattern": "[0-9]+" },
               { "jsd:type": "any", "types": "myNumber myString" } ]},
           { "jsd:type": "reference", "type": "myObject" },
           { "jsd:type": "any", "types": "myString myObject" }]
         }
       }
     }
   }
   ```

   This example defines a schema with 3 types that express the following structure:

   1. Type **`myNumber`**: A `number` between the range -1 (inclusive) and 1 (exclusive).
   1. Type **`myString`**: A `string` with the regex patter "[a-z]+".
   1. Type **`myObject`**: An `object` that has one property:
      1. "myArray": An `array` that defines a sequence of elements:
         1. `boolean`
         1. **`myNumber`**
         1. **`myString`**
         1. An `array` with the following elements:
            1. `boolean`
            1. An integer `number` between 0 and 100.
            1. A `string` of pattern "[0-9]+"
            1. Either **`myNumber`** or **`myString`**.
         1. `myObject`
         1. Either **`myString`** or **`myObject`**.

1. **(Alternatively)** Can create an equivalent `schema.jsdx`:

   <sub>_**Note:** You can use the [Converter][#converter] utility to automatically convert between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

   ```xml
   <schema
     xmlns="http://www.jsonx.org/schema-0.2.2.xsd"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://www.jsonx.org/schema-0.2.2.xsd http://www.jsonx.org/schema-0.2.2.xsd">

     <number name="myNumber" range="[-1,1)"/>
     <string name="myString" pattern="[a-z]+"/>
     <object name="myObject">
       <property name="myArray" xsi:type="array">
         <boolean/>
         <reference type="myNumber"/>
         <reference type="myString"/>
         <array>
           <boolean/>
           <number form="integer" range="[0,100]"/>
           <string pattern="[0-9]+"/>
           <any types="myNumber myString"/>
         </array>
         <reference type="myObject"/>
         <any types="myString myObject"/>
       </property>
     </object>

   </schema>
   ```

   The <ins>JSDx</ins> format offers XML validation, and using [oXygen XML Editor][oxygenxml] offers edit-time XML validation, such as:

   <img src="https://user-images.githubusercontent.com/1258414/57699744-b8bb6800-7658-11e9-93bb-8d606c9727cf.png" width="75%">

   When using the <ins>JSDx</ins> format with the [oXygen XML Editor][oxygenxml], the auto-completion features of the editor will guide you in writing the schema. With the <ins>JSDx</ins> format, the XML editor will also validate keys and keyrefs to ensure that declared types are referenced correctly.

### 3.4 Specification

_For a detailed specification of the <ins>schema language</ins>, see **[<ins>JSON Schema Definition Language</ins>](/schema)**._

## 4 <ins>JSON/Java Binding API</ins>

Provides a way for JSON objects whose structure is expressed in the [<ins>JSON Schema Definition Language</ins>][schema] to be <ins>validated</ins>, <ins>parsed</ins> and <ins>marshaled</ins>, to and from Java objects of strongly-typed classes.

### 4.1 Purpose

Provide a <ins>binding API</ins> for parsing and marshaling JSON documents to and from strongly-typed Java classes.

### 4.2 Requirements

1. The <ins>binding API</ins> MUST be able to model the full scope of normative meaning, usage, constraints and relationships of the constituent parts of a JSON document as specifiable with the <ins>schema language</ins>.

1. The <ins>binding API</ins> MUST enforce (via validation) the full scope of normative meaning, usage, constraints and relationships of the constituent parts of a JSON document as specifiable in the <ins>schema language</ins>.

1. The <ins>binding API</ins> MUST produce clear and useful error messages when exception of <ins>schema document</ins> constraints are encountered during validation of JSON documents.

1. The <ins>binding API</ins> MUST constrain the constituent parts of a <ins>schema document</ins> to Java type bindings that are as lightweight as necessary to retain the full normative scope of specification of the <ins>schema language</ins>.

1. The <ins>binding API</ins> MUST use light coupling, not imposing requirements for exclusionary patterns onto a class model of binding classes.

1. The <ins>binding API</ins> MUST offer easy patterns for manual description of bindings.

1. The <ins>binding API</ins> MUST be straightforward, intuitive, and resilient to human error.

### 4.3 Getting Started

The <ins>JSON/Java Binding API</ins> uses annotations to bind class definitions to usage, constraints and relationships specifiable in the <ins>schema language</ins>.

The following illustrates usage of the <ins>binding API</ins> with an example of an **invoice**.

1. Create a <ins>JSD</ins> in `src/main/resources/invoice.jsd`:

   ```json
   {
     "jsd:ns": "http://www.jsonx.org/schema-0.2.2.jsd",
     "jsd:schemaLocation": "http://www.jsonx.org/schema-0.2.2.jsd http://www.jsonx.org/schema-0.2.2.jsd",

     "positiveDecimal": { "jsd:type": "number", "range": "[1,]" },
     "positiveInteger": { "jsd:type": "number", "form": "integer", "range": "[1,]" },
     "date": { "jsd:type": "string", "pattern": "-?\\d{4}-((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01])|(02-(0[1-9]|1\\d|2\\d))|((0[469]|11)-(0[1-9]|[12]\\d|30)))" },
     "nonEmptyString": { "jsd:type": "string", "pattern": "\\S|\\S.*\\S" },
     "address": { "jsd:type": "object", "properties": {
       "name": { "jsd:type": "reference", "nullable": false, "type": "nonEmptyString" },
       "address": { "jsd:type": "reference", "nullable": false, "type": "nonEmptyString" },
       "city": { "jsd:type": "reference", "nullable": false, "type": "nonEmptyString" },
       "postalCode": { "jsd:type": "reference", "nullable": false, "type": "nonEmptyString", "use": "optional" },
       "country": { "jsd:type": "reference", "type": "nonEmptyString" } }
     },
     "invoice": { "jsd:type": "object", "properties": {
       "number": { "jsd:type": "reference", "type": "positiveInteger" },
       "date": { "jsd:type": "reference", "type": "date" },
       "billingAddress": { "jsd:type": "reference", "type": "address" },
       "shippingAddress": { "jsd:type": "reference", "type": "address" },
       "billedItems": { "jsd:type": "array", "nullable": false, "elements": [
         { "jsd:type": "reference", "type": "item" } ] } }
     },
     "item": { "jsd:type": "object", "properties": {
       "description": { "jsd:type": "reference", "nullable": false, "type": "nonEmptyString" },
       "code": { "jsd:type": "reference", "nullable": false, "type": "positiveInteger" },
       "quantity": { "jsd:type": "reference", "nullable": false, "type": "positiveInteger" },
       "price": { "jsd:type": "reference", "nullable": false, "type": "positiveDecimal" } }
     }
   }
   ```

1. **(Alternatively)** Create a <ins>JSDx</ins> in `src/main/resources/invoice.jsdx`:

   <sub>_**Note:** You can use the [Converter][#converter] utility to automatically convert between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

   ```xml
   <schema
     xmlns="http://www.jsonx.org/schema-0.2.2.xsd"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://www.jsonx.org/schema-0.2.2.xsd http://www.jsonx.org/schema-0.2.2.xsd">

     <number name="positiveDecimal" range="[1,]"/>
     <number name="positiveInteger" form="integer" range="[1,]"/>
     <string name="date" pattern="-?\d{4}-((0[13578]|1[02])-(0[1-9]|[12]\d|3[01])|(02-(0[1-9]|1\d|2\d))|((0[469]|11)-(0[1-9]|[12]\d|30)))"/>
     <string name="nonEmptyString" pattern="\S|\S.*\S"/>
     <object name="address">
       <property name="name" xsi:type="reference" type="nonEmptyString" nullable="false"/>
       <property name="address" xsi:type="reference" type="nonEmptyString" nullable="false"/>
       <property name="city" xsi:type="reference" type="nonEmptyString" nullable="false"/>
       <property name="postalCode" xsi:type="reference" type="nonEmptyString" nullable="false" use="optional"/>
       <property name="country" xsi:type="reference" type="nonEmptyString"/>
     </object>
     <object name="invoice">
       <property name="number" xsi:type="reference" type="positiveInteger"/>
       <property name="date" xsi:type="reference" type="date"/>
       <property name="billingAddress" xsi:type="reference" type="address"/>
       <property name="shippingAddress" xsi:type="reference" type="address"/>
       <property name="billedItems" xsi:type="array" nullable="false">
         <reference type="item"/>
       </property>
     </object>
     <object name="item">
       <property name="description" xsi:type="reference" type="nonEmptyString" nullable="false"/>
       <property name="code" xsi:type="reference" type="positiveInteger" nullable="false"/>
       <property name="quantity" xsi:type="reference" type="positiveInteger" nullable="false"/>
       <property name="price" xsi:type="reference" type="positiveDecimal" nullable="false"/>
     </object>

   </schema>
   ```

1. With the `invoice.jsd` or `invoice.jsdx`, you can use the [`jsonx-maven-plugin`][jsonx-maven-plugin] to automatically generate the Java class files. In your POM, add:

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
           <prefix>com.example.invoice.</prefix>
           <schemas>
             <schema>src/main/resources/invoice.jsd</schema> <!-- or invoice.jsdx -->
           </schemas>
         </configuration>
       </execution>
     </executions>
   </plugin>
   ```

1. **(Alternatively)** Create the Java class files by hand:

   <sup>_**Note:** Set-ters and get-ters have been replaced with public fields for conciseness._</sup>

   ```java
   import org.jsonx.*;

   public class Address implements JxObject {
     @StringProperty(pattern="\\S|\\S.*\\S", nullable=false)
     public String name;

     @StringProperty(pattern="\\S|\\S.*\\S", nullable=false)
     public String address;

     @StringProperty(pattern="\\S|\\S.*\\S", nullable=false)
     public String city;

     @StringProperty(pattern="\\S|\\S.*\\S", use=Use.OPTIONAL, nullable=false)
     public String postalCode;

     @StringProperty(pattern="\\S|\\S.*\\S")
     public String country;
   }
   ```

   ```java
   import org.jsonx.*;

   public class Item implements JxObject {
     @StringProperty(pattern="\\S|\\S.*\\S", nullable=false)
     public String description;

     @NumberProperty(form=Form.INTEGER, range="[1,]", nullable=false)
     public java.math.BigInteger code;

     @NumberProperty(form=Form.INTEGER, range="[1,]", nullable=false)
     public java.math.BigInteger quantity;

     @NumberProperty(range="[1,]", nullable=false)
     public java.math.BigDecimal price;
    }
   ```

   ```java
   import org.jsonx.*;

   public class Invoice implements JxObject {
     @NumberProperty(form=Form.INTEGER, range="[1,]")
     public java.math.BigInteger number;

     @StringProperty(pattern="-?\\d{4}-((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01])|(02-(0[1-9]|1\\d|2\\d))|((0[469]|11)-(0[1-9]|[12]\\d|30)))")
     public String date;

     @ObjectProperty
     public Address billingAddress;

     @ObjectProperty
     public Address shippingAddress;

     @ObjectElement(id=0, type=Item.class)
     @ArrayProperty(elementIds={0}, nullable=false)
     public java.util.List<Item> billedItems;
   }
   ```

1. You can use these classes to represent `Address`es, `Item`s, and `Invoice`s.

   ```java
   Address address = new Address();
   address.name = "John Doe";
   address.address = "111 Wall St.";
   address.city = "New York";
   address.postalCode = "10043";
   address.country = "USA";

   Item item = new Item();
   item.code = BigInteger.valueOf(123);
   item.description = "Pocket Protector";
   item.price = new BigDecimal("14.99");
   item.quantity = BigInteger.valueOf(5);

   Invoice invoice = new Invoice();
   invoice.number = BigInteger.valueOf(14738);
   invoice.date = "2019-05-13";
   invoice.billingAddress = address;
   invoice.shippingAddress = address;
   invoice.billedItems = Collections.singletonList(item);
   ```

1. You can now <ins>marshal</ins> the Java objects to JSON:

   ```java

   String json = JxEncoder._2.marshal(invoice);
   System.out.println(json);
   ```

   ... will produce:

   ```json
   {
     "number": 14738,
     "date": "2019-05-13",
     "billingAddress": {
       "name": "John Doe",
       "address": "111 Wall St.",
       "city": "New York",
       "postalCode": "10043",
       "country": "USA"
     },
     "shippingAddress": {
       "name": "John Doe",
       "address": "111 Wall St.",
       "city": "New York",
       "postalCode": "10043",
       "country": "USA"
     },
     "billedItems": [{
       "description": "Pocket Protector",
       "code": 123,
       "quantity": 5,
       "price": 14.99
     }]
   }
   ```

1. You can also <ins>parse</ins> the JSON into Java objects:

   ```java
   Invoice invoice2 = JxDecoder.parseObject(Invoice.class, new JsonReader(new StringReader(json)));
   assertEquals(invoice, invoice2);
   ```

### 4.4 Specification

_For a detailed specification of the <ins>binding API</ins>, see **[<ins>JSON/Java Binding API</ins>](/binding)**._

## 5 <ins>JSD Binding Generator</ins>

Consumes a JSD schema, and generates classes that use the <ins>JSON/Java Binding API</ins> to achieve binding between JSON documents conforming to a JSD schema, and Java object represetations of these documents.

### 5.1 Purpose

Provide a <ins>binding generator</ins> utility for automatic generation of binding classes from a <ins>schema document</ins>.

### 5.2 Requirements

1. The <ins>binding generator</ins> MUST be able to consume a <ins>schema document</ins>, and produce Java class definitions (`.java` files) that use the <ins>binding API</ins>.

1. The <ins>binding generator</ins> MUST be able to consume Java class definitions (`.class` files) utilizing the <ins>binding API</ins>, and produce a <ins>schema document</ins>.

1. The <ins>binding generator</ins> MUST create Java classes (`.java` files) that encode the full normative scope of the <ins>schema document</ins>.

1. The <ins>binding generator</ins> MUST represent the constituent parts of a <ins>schema document</ins> with Java type bindings that are as strongly-typed as possible, but not limiting in any way with regard to the definition of the respective constituent part.

1. The <ins>binding generator</ins> MUST be able to validate a <ins>schema document</ins>.

### 5.3 Getting Started

The <ins>JSD Binding Generator</ins> provides convenience utilities for generating bindings and converting <ins>schema document</ins>s. The following illustrates example usage of the `Generator` and `Converter` executable classes.

#### 5.3.1 `Generator`

The following example generates binding classes (`.java` files) in `target/generated-sources/jsonx` for the <ins>schema document</ins> at `src/main/resources/example.jsd`, with prefix `org.example$`.

```bash
java -cp ... org.jsonx.Generator --prefix org.example$ -d target/generated-sources/jsonx src/main/resources/example.jsd
```

#### 5.3.2 `Converter`

The following example converts the JSD file at `src/main/resources/example.jsd` to a <ins>JSDx</ins> file in `target/generated-resources`.

```bash
java -cp ... org.jsonx.Converter src/main/resources/example.jsd target/generated-resources/example.jsdx
```

### 5.4 Specification

_For a detailed specification of the <ins>binding generator</ins>, see **[<ins>JSD Binding Generator</ins>](/generator)**._

## 6 <ins>JSONx-JSON</ins>

Offers facilities for validating and converting JSON and JSONx documents (JSONx is JSON expressed in XML syntax).

### 6.1 Purpose

Provide an encoding of JSON documents in an analogous form that uses XML semantics, referred to as <ins>JSONx documents</ins>.

### 6.2 Requirements

1. The <ins>JSONx documents</ins> MUST be able to represent any and all legal JSON documents, as specified by [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

1. The <ins>JSONx documents</ins> MUST be translatable to JSON documents, and vice versa, preserving all normative and non-normative features of the original document.

1. The <ins>JSONx documents</ins> MUST provide meaningful and useful validation features via XSD validation.

### 6.3 Getting Started

The <ins>JSONx-JSON</ins> sub-project provides convenience utilities for converting and validating JSON and JSONx documents. The following illustrates example usage of the `JxConverter` class.

#### 6.3.1 JSON->JSONx

```java
String jsonx = JxConverter.jsonToJsonx(new JsonReader(new FileReader("example.json")));
```

#### 6.3.2 JSONx->JSON

```java
String json = JxConverter.jsonxToJson(new FileInputStream("example.jsonx"));
```

### 6.4 Specification

_For a detailed specification of JSONx, see **[<ins>JSONx-JSON</ins>](/json)**._

## 7 <ins>JAX-RS Integration for JSONx</ins>

Implements the `MessageBodyReader` and `MessageBodyWriter` interfaces in the JAX-RS API to integrate with JAX-RS server runtimes.

### 7.1 Purpose

Provide <ins>JAX-RS integration</ins> for parsing and marshaling Java object instances of binding classes in a JAX-RS runtime.

### 7.2 Requirements

1. The <ins>JAX-RS integration</ins> MUST support any JAX-RS application that implements the facets relevant to parsing and marshaling of entity object, as defined in the [JAX-RS 2.0 Specification](https://download.oracle.com/otn-pub/jcp/jaxrs-2_0-fr-eval-spec/jsr339-jaxrs-2.0-final-spec.pdf).

1. The <ins>JAX-RS integration</ins> MUST be automatic and free of any configuration that would couple an application to the <ins>JSONx Framework</ins>.

### 7.3 Getting Started

The <ins>JAX-RS Integration for JSONx</ins> sub-project provides `MessageBodyReader` and `MessageBodyWriter` providers that can be registered with a JAX-RS runtime. The following illustrates example usage.

1. Create a <ins>JSD</ins> in `src/main/resources/account.jsd`.

   ```json
   {
     "jsd:ns": "http://www.jsonx.org/schema-0.2.2.jsd",
     "jsd:schemaLocation": "http://www.jsonx.org/schema-0.2.2.jsd http://www.jsonx.org/schema-0.2.2.jsd",

     "uuid": { "jsd:type": "string", "pattern": "[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}" },
     "id": { "jsd:type": "object", "abstract": true, "properties": {
       "id": { "jsd:type": "reference", "nullable": false, "type": "uuid" } } },
     "credentials": { "jsd:type": "object", "properties": {
       "email": { "jsd:type": "string", "nullable": false, "pattern": "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}" },
       "password": { "jsd:type": "string", "nullable": false, "pattern": "[0-9a-f]{64}", "use": "optional" } } },
     "account": { "jsd:type": "object", "extends": "credentials", "properties": {
       "id": { "jsd:type": "reference", "nullable": false, "type": "uuid", "use": "optional" },
       "firstName": { "jsd:type": "string", "nullable": false },
       "lastName": { "jsd:type": "string", "nullable": false } } }
   }
   ```

1. **(Alternatively)** Create a <ins>JSDx</ins> in `src/main/resources/account.jsdx`.

   <sub>_**Note:** You can use the [Converter][#converter] utility to automatically convert between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

   ```xml
   <schema
     xmlns="http://www.jsonx.org/schema-0.2.2.xsd"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://www.jsonx.org/schema-0.2.2.xsd http://www.jsonx.org/schema-0.2.2.xsd">

     <string name="uuid" pattern="[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}"/>
     <object name="id" abstract="true">
       <property name="id" xsi:type="reference" type="uuid" nullable="false"/>
     </object>
     <object name="credentials">
       <property xsi:type="string" name="email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}" nullable="false"/>
       <property xsi:type="string" name="password" pattern="[0-9a-f]{64}" use="optional" nullable="false"/>
     </object>
     <object name="account" extends="credentials">
       <property name="id" xsi:type="reference" type="uuid" nullable="false" use="optional"/>
       <property name="firstName" xsi:type="string" nullable="false"/>
       <property name="lastName" xsi:type="string" nullable="false"/>
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
           <prefix>com.example.jsonx.</prefix>
           <schemas>
             <schema>src/main/resources/account.jsonx</schema>
           </schemas>
         </configuration>
       </execution>
     </executions>
   </plugin>
   ```

1. Upon successful execution of the [`jsonx-maven-plugin`][jsonx-maven-plugin] plugin, Java class files will be generated in `generated-sources/jsonx`. Add this path to your Build Paths in your IDE to integrate into your project.

   The generated classes can be instantiated as any other Java objects. They are strongly typed, and will guide you in proper construction of a JSON message. The following APIs can be used for parsing and marshalling <ins>JSONx</ins> to and from JSON:

   To <ins>parse</ins> JSON to <ins>JSONx</ins> Bindings:

   ```java
   String json = "{\"email\":\"john@doe\",\"password\":\"066b91577bc547e21aa329c74d74b0e53e29534d4cc0ad455abba050121a9557\"}";
   Credentials credentials = JxDecoder.parseObject(Credentials.class, new JsonReader(new StringReader(json)));
   ```

   To <ins>marshal</ins> <ins>JSONx</ins> Bindings to JSON:

   ```java
   String json2 = JxEncoder.get().marshal(credentials);
   assertEquals(json, json2);
   ```

1. For JAX-RS integration, register the `JxObjectProvider` provider in the JAX-RS appilcation singletons, and implement the `AccountService`:

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

### 7.4 Specification

_For a detailed specification of <ins>JAX-RS integration</ins>, see **[<ins>JAX-RS Integration for JSONx</ins>](/rs)**._

## 8 <ins>JSONx Maven Plugin</ins>

A Maven plugin for generating JSONx and JSD bindings.

### 8.1 Purpose

Provide schema <ins>validation</ins>, schema <ins>conversion</ins>, and Java binding source <ins>generation</ins> in a <ins>Maven plugin</ins>.

### 8.2 Requirements

1. The <ins>Maven plugin</ins> MUST offer utilities for the generation of Java binding sources from a specified <ins>schema document</ins>.

1. The <ins>Maven plugin</ins> MUST offer utilities for validation of <ins>schema document</ins>s.

1. The <ins>Maven plugin</ins> MUST offer utilities for conversion of <ins>schema document</ins>s from <ins>JSD</ins> to <ins>JSDx</ins>, and vice versa.

1. The <ins>Maven plugin</ins> MUST present clear and informative errors and warnings that arise during parsing and validation of <ins>schema document</ins>s and JSON documents with an associated schema.

### 8.3 Getting Started

The <ins>JSONx Maven Plugin</ins> implements a Maven MOJO that can be used in a `pom.xml`. The following illustrates an example usage.

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
          <schema>src/main/resources/schema.jsd</schema> <!-- or schema.jsdx -->
        </schemas>
      </configuration>
    </execution>
  </executions>
</plugin>
```

### 8.4 Specification

_For a detailed specification of the Maven plugin, see **[<ins>JSONx Maven Plugin</ins>][jsonx-maven-plugin]**._

## 9 Contributing

Pull requests are welcome. For major changes, please [open an issue](../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## 10 License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#converter]: #532-converter
[#invoice-example]: #43-getting-started
[#jsd]: #3-json-schema-definition-language

[cdc]: http://martinfowler.com/articles/consumerDrivenContracts.html
[jsonx-maven-plugin]: /jsonx-maven-plugin
[maven]: https://maven.apache.org/
[oxygenxml]: https://www.oxygenxml.com/xml_editor/download_oxygenxml_editor.html
[schema]: /schema/
[xmlschema]: http://www.w3.org/2001/XMLSchema