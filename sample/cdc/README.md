# JSONx Sample: Consumer Driven Contracts

> **JSON Schema for the enterprise**

[![Build Status](https://github.com/jsonx-org/java/actions/workflows/build.yml/badge.svg)](https://github.com/jsonx-org/java/actions/workflows/build.yml)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/rs.svg)](https://www.javadoc.io/doc/org.jsonx/rs)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/rs.svg)](https://mvnrepository.com/artifact/org.jsonx/rs)
![Snapshot Version](https://img.shields.io/nexus/s/org.jsonx/rs?label=maven-snapshot&server=https%3A%2F%2Foss.sonatype.org)

## Abstract

This document presents the [<ins>Consumer Driven Contracts</ins><sup>❐</sup>][cdc] sample application.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [<ins>Consumer Driven Contracts</ins>](#1-consumer-driven-contracts)<br>
<samp>&nbsp;&nbsp;</samp>2 [<ins>Contributing</ins>](#2-contributing)<br>
<samp>&nbsp;&nbsp;</samp>3 [<ins>License</ins>](#3-license)<br>

### <b>1</b> <ins>Consumer Driven Contracts</ins>

The <ins>JSONx Framework</ins> was created specifically for [<ins>Consumer Driven Contracts</ins><sup>❐</sup>][cdc]. With the [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd], one can create a <ins>Consumer Driven Contract (CDC)</ins> with an evolution model based on schema versioning. The <ins>JSD</ins> can be used by producers and consumers to validate documents in a communication protocol.

The following example illustrates a simple protocol that uses the [<ins>Consumer Driven Contracts</ins><sup>❐</sup>][cdc] approach, and consists of the actors:

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

The schema that describes the **Response** contract is:

<!-- tabs:start -->

###### **JSD**

```json
{
  "jx:ns": "http://www.jsonx.org/schema-0.3.jsd",
  "jx:schemaLocation": "http://www.jsonx.org/schema-0.3.jsd http://www.jsonx.org/schema.jsd",

  "product": { "jx:type": "object", "abstract": true, "properties": {
    "CatalogueID": { "jx:type": "number", "range": "[1,]", "scale": 0, "nullable": false},
    "Name": { "jx:type": "string", "pattern": "\\S|\\S.*\\S", "nullable": false },
    "Price": { "jx:type": "string", "pattern": "\\$\\d+\\.\\d{2}", "nullable": false },
    "Manufacturer": { "jx:type": "string", "pattern": "\\S|\\S.*\\S", "nullable": false },
    "InStock": { "jx:type": "boolean", "nullable": false} } },

  "product1": { "jx:type": "object", "extends": "product", "properties": {
    "Version": { "jx:type": "string", "pattern": "v1", "nullable": false } } }
}
```

###### **JSDx**

```xml
<schema
  xmlns="http://www.jsonx.org/schema-0.3.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/schema-0.3.xsd http://www.jsonx.org/schema.xsd">

  <object name="product" abstract="true">
    <property name="CatalogueID" xsi:type="number" range="[1,]" scale="0" nullable="false"/>
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

<!-- tabs:end -->

<sub>_**Note:** The [Converter][#converter] utility automatically converts between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

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

<!-- tabs:start -->

###### **JSD**

```diff
{
  "jx:ns": "http://www.jsonx.org/schema-0.3.jsd",
  "jx:schemaLocation": "http://www.jsonx.org/schema-0.3.jsd http://www.jsonx.org/schema.jsd",

  "product": { "jx:type": "object", "abstract": true, "properties": {
    "CatalogueID": { "jx:type": "number", "range": "[1,]", "scale": 0, "nullable": false},
    "Name": { "jx:type": "string", "pattern": "\\S|\\S.*\\S", "nullable": false },
    "Price": { "jx:type": "string", "pattern": "\\$\\d+\\.\\d{2}", "nullable": false },
    "Manufacturer": { "jx:type": "string", "pattern": "\\S|\\S.*\\S", "nullable": false },
    "InStock": { "jx:type": "boolean", "nullable": false} } },

  "product1": { "jx:type": "object", "extends": "product", "properties": {
    "Version": { "jx:type": "string", "pattern": "v1", "nullable": false } } }

+ "product2": { "jx:type": "object", "extends": "product", "properties": {
+   "Version": { "jx:type": "string", "pattern": "v2", "nullable": false },
+   "Description": { "jx:type": "string", "pattern": "\\S|\\S.*\\S", "nullable": false } } }
}
```

###### **JSDx**

```diff
<schema
  xmlns="http://www.jsonx.org/schema-0.3.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/schema-0.3.xsd http://www.jsonx.org/schema.xsd">

  <object name="product" abstract="true">
    <property name="CatalogueID" xsi:type="number" range="[1,]" scale="0" nullable="false"/>
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

<!-- tabs:end -->

<sub>_**Note:** The [Converter][#converter] utility automatically converts between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

With this approach, the **v2** evolution of the contract satisfies **Customer2**. And, since the contract also retains support for **v1**, integration with **Customer1** is unaffected.

_The code included in this module implements this example._

## <b>2</b> <ins>Contributing</ins>

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <b>3</b> <ins>License</ins>

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#converter]: ../../../../#532-converter
[#jsd]: ../../../../#3-json-schema-definition-language
[jsonx-maven-plugin]: ../../jsonx-maven-plugin/

[cdc]: http://martinfowler.com/articles/consumerDrivenContracts.html