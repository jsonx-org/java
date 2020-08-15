# JsonXml

> **JSON Schema for the enterprise**

[![Build Status](https://travis-ci.org/jsonx-org/java.svg?branch=master)](https://travis-ci.org/jsonx-org/java)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/json.svg)](https://www.javadoc.io/doc/org.jsonx/json)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/json.svg)](https://mvnrepository.com/artifact/org.jsonx/json)
![Snapshot Version](https://img.shields.io/nexus/s/org.jsonx/json?label=maven-snapshot&server=https%3A%2F%2Foss.sonatype.org)

## Abstract

This document specifies the <ins>JsonXml API</ins>, which offers utilities for converting JSON documents to XML, and vice-versa.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [<ins>Introduction</ins>][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Dependencies on Other Specifications][#dependencies]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.2 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [<ins>Purpose</ins>][#purpose]<br>
<samp>&nbsp;&nbsp;</samp>3 [<ins>Requirements</ins>][#requirements]<br>
<samp>&nbsp;&nbsp;</samp>4 [<ins>Getting Started</ins>](#4-getting-started)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [JSON-to-XML](#41-json-to-xml)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [XML-to-JSON](#42-xml-to-json)<br>
<samp>&nbsp;&nbsp;</samp>5 [<ins>Specification</ins>][#specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1 [JsonXml Schema][#jsonxmlschema]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1.1 [`boolean`][#booleantype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1.2 [`string`][#stringtype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1.3 [`number`][#numbertype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1.4 [`object`][#objecttype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1.5 [`array`][#arraytype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1.6 [`null`][#nullvalue]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2 [JsonXml API][#jsonxmlapi]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2.1 [`JxConverter`][#jxconverter]<br>
<samp>&nbsp;&nbsp;</samp>6 [<ins>Sample Documents</ins>][#samples]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.1 [`paypal.json`][#paypaljson]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.2 [`paypal.xml`][#paypalxml]<br>
<samp>&nbsp;&nbsp;</samp>7 [<ins>Related Resources for JsonXml</ins>][#resources]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>7.1 [Schemas for JsonXml][#jsonxml-schemas]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>7.1.1 [JsonXml 0.3][#jsonxml-03]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>7.1.2 [JsonXml 0.2][#jsonxml-02]<br>
<samp>&nbsp;&nbsp;</samp>8 [<ins>Contributing</ins>](#8-contributing)<br>
<samp>&nbsp;&nbsp;</samp>9 [<ins>License</ins>](#9-license)

## <b>1</b> <ins>Introduction</ins>

JsonXml is JSON expressed in XML. This document presents the structural part of JsonXml, as well as the normative specification of the <ins>JsonXml API</ins>. It also contains a directory of links to these related resources.

### <b>1.1</b> Dependencies on Other Specifications

The definition of the <ins>JsonXml API</ins> depends on the following specifications: [RFC4627<sup>❐</sup>][rfc4627] and [XMLSchema<sup>❐</sup>][xmlschema].

### <b>1.2</b> Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## <b>2</b> <ins>Purpose</ins>

Provide an encoding of JSON documents in an analogous form that uses XML semantics, referred to as <ins>JsonXml documents</ins>.

## <b>3</b> <ins>Requirements</ins>

1. The <ins>JsonXml documents</ins> MUST be able to represent any and all legal JSON documents, as specified by [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

1. The <ins>JsonXml documents</ins> MUST be translatable to JSON documents, and vice versa, preserving all normative and non-normative features of the original document.

1. The <ins>JsonXml documents</ins> MUST provide meaningful and useful validation features via XSD validation.

## <b>4</b> <ins>Getting Started</ins>

The <ins>JsonXml</ins> sub-project provides convenience utilities for converting JSON documents to XML. The following illustrates example usage of the `JxConverter` class.

### <b>4.1</b> JSON-to-XML

```java
String xml = JxConverter.jsonToXml(new JsonReader(new FileReader("example.json")));
```

### <b>4.2</b> XML-to-JSON

```java
String json = JxConverter.xmlToJson(new FileInputStream("example.xml"));
```

## <b>5</b> <ins>Specification</ins>

### <b>5.1</b> <ins>JsonXml Schema</ins>

The JsonXml Schema defines XML elements that represent an XML-equivalent of a JSON document. The JsonXml Schema represents the JSON value types as follows:

#### <b>5.1.1</b> `boolean` Type

The `false` and `true` string literals.

<ins>Example</ins>: `true`

#### <b>5.1.2</b> `string` Type

A double-quoted string. A JSON string may require to be escaped to a string that is legal for XML. The escaped characters are [Predefined Entities in XML<sup>❐</sup>][xmlentities].

<ins>Example</ins>: `"string"`

#### <b>5.1.3</b> `number` Type

A number that conforms to the [RFC4627<sup>❐</sup>][rfc4627] section 2.4.

<ins>Example</ins>: `6.626E-34`

#### <b>5.1.4</b> `object` Type

A JSON object is represented by the `<o>` element. The `<o>` element does not have attributes, and may contain 0 or more `<p>` elements.

A JSON object property is represented by the `<p>` element. The `<p>` element contains an attribute `n` that specifies the property's name. The content of the `<p>` element can be one of [`boolean` type][#booleantype], [`string` type][#stringtype], [`number` type][#numbertype], [`object` type][#objecttype], or [`array` type][#arraytype].

#### <b>5.1.5</b> `array` Type

A JSON array is represented by the `<a>` element. The `<a>` element does not have attributes, and may contain 0 or more space delimited members conforming to [`boolean` type][#booleantype], [`string` type][#stringtype], [`number` type][#numbertype], [`object` type][#objecttype], or [`array` type][#arraytype].

#### <b>5.1.6</b> `null` Value

The `null` JSON value is represented by the string `null`.

### <b>5.2</b> <ins>JsonXml API</ins>

The <ins>JsonXml API</ins> offers facilities for validating and converting JSON and JSONx documents.

#### <b>5.2.1</b> `JxConverter`

The `JxConverter` class contains utility methods for conversion of JSON documents to XML documents, and vice versa.

<ins>Example</ins>: Convert a JSON document to a JSONx document

```java
JxConverter.jsonToXml(new JsonReader(new StringReader(json), false), true);
```

<ins>Example</ins>: Convert a JSONx document to a JSON document

```java
JxConverter.xmlToJson(new ByteArrayInputStream(xml.getBytes()), true);
```

## <b>6</b> <ins>Sample Documents</ins>

This section provides a sample JSON and its JSONx alternative.

### <b>6.1</b> `paypal.json`

```json
{
  "id": "WH-7YX49823S2290830K-0JE13296W68552352",
  "event_version": "1.0",
  "create_time": "2016-05-31T17:53:29Z",
  "resource_type": "refund",
  "event_type": "PAYMENT.SALE.REFUNDED",
  "summary": "A $ 14.99 USD sale payment was refunded",
  "resource": {
    "id": "29B43325P49508437",
    "state": "completed",
    "amount": {
      "total": "14.99",
      "currency": "USD"
    },
    "refund_to_payer": {
      "value": "14.99",
      "currency": "USD"
    },
    "parent_payment": "PAY-9MX90473V8752831HK5G4RDI",
    "sale_id": "02399573KS095945W",
    "create_time": "2016-05-31T17:52:18Z",
    "update_time": "2016-05-31T17:52:18Z",
    "links": [
      {"href": "https://api.paypal.com/v1/payments/refund/29B43325P49508437", "rel": "self", "method": "GET"},
      {"href": "https://api.paypal.com/v1/payments/payment/PAY-9MX90473V8752831HK5G4RDI", "rel": "parent_payment", "method": "GET"},
      {"href": "https://api.paypal.com/v1/payments/sale/02399573KS095945W", "rel": "sale", "method": "GET"}
    ]
  },
  "links": [
    {"href": "https://api.paypal.com/v1/notifications/webhooks-events/WH-7YX49823S2290830K-0JE13296W68552352", "rel": "self", "method": "GET"},
    {"href": "https://api.paypal.com/v1/notifications/webhooks-events/WH-7YX49823S2290830K-0JE13296W68552352/resend", "rel": "resend", "method": "POST"}
  ]
}
```

### <b>6.2</b> `paypal.xml`

```xml
<o
  xmlns="http://www.jsonx.org/jsonxml-0.3.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/jsonxml-0.3.xsd http://www.jsonx.org/jsonxml.xsd">
  <p n="id"> "WH-7YX49823S2290830K-0JE13296W68552352"</p>
  <p n="event_version"> "1.0"</p>
  <p n="create_time"> "2016-05-31T17:53:29Z"</p>
  <p n="resource_type"> "refund"</p>
  <p n="event_type"> "PAYMENT.SALE.REFUNDED"</p>
  <p n="summary"> "A $ 14.99 USD sale payment was refunded"</p>
  <p n="resource"> <o>
    <p n="id"> "29B43325P49508437"</p>
    <p n="state"> "completed"</p>
    <p n="amount"> <o>
      <p n="total"> "14.99"</p>
      <p n="currency"> "USD"
    </p></o></p>
    <p n="refund_to_payer"> <o>
      <p n="value"> "14.99"</p>
      <p n="currency"> "USD"
    </p></o></p>
    <p n="parent_payment"> "PAY-9MX90473V8752831HK5G4RDI"</p>
    <p n="sale_id"> "02399573KS095945W"</p>
    <p n="create_time"> "2016-05-31T17:52:18Z"</p>
    <p n="update_time"> "2016-05-31T17:52:18Z"</p>
    <p n="links"> <a>
      <o><p n="href"> "https://api.paypal.com/v1/payments/refund/29B43325P49508437"</p> <p n="rel"> "self"</p> <p n="method"> "GET"</p></o>
      <o><p n="href"> "https://api.paypal.com/v1/payments/payment/PAY-9MX90473V8752831HK5G4RDI"</p> <p n="rel"> "parent_payment"</p> <p n="method"> "GET"</p></o>
      <o><p n="href"> "https://api.paypal.com/v1/payments/sale/02399573KS095945W"</p> <p n="rel"> "sale"</p> <p n="method"> "GET"</p></o>
    </a>
  </p></o></p>
  <p n="links"> <a>
    <o><p n="href"> "https://api.paypal.com/v1/notifications/webhooks-events/WH-7YX49823S2290830K-0JE13296W68552352"</p> <p n="rel"> "self"</p> <p n="method"> "GET"</p></o>
    <o><p n="href"> "https://api.paypal.com/v1/notifications/webhooks-events/WH-7YX49823S2290830K-0JE13296W68552352/resend"</p> <p n="rel"> "resend"</p> <p n="method"> "POST"</p></o>
  </a>
</p></o>
```

## <b>7</b> <ins>Related Resources for JsonXml</ins>

### <b>7.1</b> Schemas for JsonXml

#### <b>7.1.1</b> JsonXml Schema 0.3

* A JsonXml schema document XSD [jsonxml-0.3.xsd<sup>❐</sup>][jsonxml-03] for JsonXml documents.

#### <b>7.1.2</b> JsonXml Schema 0.2

* A JsonXml schema document XSD [jsonxml-0.2.xsd<sup>❐</sup>][jsonxml-02] for JsonXml documents.

## <b>8</b> <ins>Contributing</ins>

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <b>9</b> <ins>License</ins>

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#introduction]: #1-introduction
[#dependencies]: #11-dependencies-on-other-specifications
[#conventions]: #12-conventions-used-in-this-document
[#purpose]: #2-purpose
[#requirements]: #3-requirements
[#specification]: #5-specification
[#jsonxmlschema]: #51-jsonxml-schema
[#booleantype]: #511-boolean-type
[#stringtype]: #512-string-type
[#numbertype]: #513-number-type
[#objecttype]: #514-object-type
[#arraytype]: #515-array-type
[#nullvalue]: #516-null-value
[#jsonxmlapi]: #52-jsonxml-api
[#jxconverter]: #521-jxconverter
[#samples]: #6-sample-documents
[#paypaljson]: #61-paypaljson
[#paypalxml]: #62-paypalxml
[#resources]: #7-related-resources-for-jsonxml
[#jsonxml-schemas]: #71-schemas-for-jsonxml
[#jsonxml-03]: #711-jsonxml-schema-03
[#jsonxml-02]: #712-jsonxml-schema-02

[api]: ../binding/

[jsonxml-02]: http://www.jsonx.org/jsonxml-0.2.xsd
[jsonxml-03]: http://www.jsonx.org/jsonxml-0.3.xsd
[rfc4627]: https://www.ietf.org/rfc/rfc4627.txt
[xmlschema]: http://www.w3.org/2001/XMLSchema
[xmlentities]: https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references#Predefined_entities_in_XML