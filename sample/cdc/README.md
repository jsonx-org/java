# JSONx Sample: Consumer Driven Contracts

[![Build Status](https://travis-ci.org/jsonxorg/jsonx.svg?1)](https://travis-ci.org/jsonxorg/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/jsonxorg/jsonx/badge.svg?1)](https://coveralls.io/github/jsonxorg/jsonx)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/rs.svg?1)](https://www.javadoc.io/doc/org.jsonx/rs)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/rs.svg?1)](https://mvnrepository.com/artifact/org.jsonx/rs)

## Abstract

This document presents the [<ins>Consumer Driven Contracts</ins>][cdc] sample application.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Consumer Driven Contracts](#1-consumer-driven-contracts)<br>
<samp>&nbsp;&nbsp;</samp>2 [Contributing](#2-contributing)<br>
<samp>&nbsp;&nbsp;</samp>3 [License](#3-license)<br>

### 1 Consumer Driven Contracts

The <ins>JSONx Framework</ins> was created specifically for [<ins>Consumer Driven Contracts</ins>][cdc]. With the [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd], one can create a <ins>Consumer Driven Contract (CDC)</ins> with an evolution model based on schema versioning. The <ins>JSD</ins> can be used by producers and consumers to validate documents in a communication protocol.

The following example illustrates a simple protocol that uses the [<ins>Consumer Driven Contracts</ins>][cdc] approach, and consists of the actors:

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

  "product": { "jsd:class": "object", "jsd:abstract": true, "jsd:properties": {
    "CatalogueID": { "jsd:class": "number", "jsd:form": "integer", "jsd:nullable": false, "jsd:range": "[1,]" },
    "Name": { "jsd:class": "string", "jsd:nullable": false, "jsd:pattern": "\\S|\\S.*\\S" },
    "Price": { "jsd:class": "string", "jsd:nullable": false, "jsd:pattern": "\\$\\d+\\.\\d{2}" },
    "Manufacturer": { "jsd:class": "string", "jsd:nullable": false, "jsd:pattern": "\\S|\\S.*\\S" },
    "InStock": { "jsd:class": "boolean", "jsd:nullable": false} } },

  "product1": { "jsd:class": "object", "jsd:extends": "product", "jsd:properties": {
    "Version": { "jsd:class": "string", "jsd:nullable": false, "jsd:pattern": "v1"} } }
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

  "product": { "jsd:class": "object", "jsd:abstract": true, "jsd:properties": {
    "CatalogueID": { "jsd:class": "number", "jsd:form": "integer", "jsd:nullable": false, "jsd:range": "[1,]" },
    "Name": { "jsd:class": "string", "jsd:nullable": false, "jsd:pattern": "\\S|\\S.*\\S" },
    "Price": { "jsd:class": "string", "jsd:nullable": false, "jsd:pattern": "\\$\\d+\\.\\d{2}" },
    "Manufacturer": { "jsd:class": "string", "jsd:nullable": false, "jsd:pattern": "\\S|\\S.*\\S" },
    "InStock": { "jsd:class": "boolean", "jsd:nullable": false} } },

  "product1": { "jsd:class": "object", "jsd:extends": "product", "jsd:properties": {
    "Version": { "jsd:class": "string", "jsd:nullable": false, "jsd:pattern": "v1"} } },

+ "product2": { "jsd:class": "object", "jsd:extends": "product", "jsd:properties": {
+   "Version": { "jsd:class": "string", "jsd:nullable": false, "jsd:pattern": "v2" },
+   "Description": { "jsd:class": "string", "jsd:nullable": false, "jsd:pattern": "\\S|\\S.*\\S" } } }
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

_The code included in this module implements this example._

## 2 Contributing

Pull requests are welcome. For major changes, please [open an issue](../../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## 3 License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#jsd]: ../../../#3-json-schema-definition-language

[cdc]: http://martinfowler.com/articles/consumerDrivenContracts.html