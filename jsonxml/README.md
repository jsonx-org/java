# JSONx <-> JSON

[![Build Status](https://travis-ci.org/jsonx-org/java.svg?EKkC4CBk)](https://travis-ci.org/jsonx-org/java)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg?EKkC4CBk)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/json.svg?EKkC4CBk)](https://www.javadoc.io/doc/org.jsonx/json)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/json.svg?EKkC4CBk)](https://mvnrepository.com/artifact/org.jsonx/json)

## Abstract

This document specifies the <ins>JsonXml API</ins>, which offers utilities for converting JSON documents to XML, and vice-versa.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Dependencies on Other Specifications][#dependencies]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.2 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [Purpose][#purpose]<br>
<samp>&nbsp;&nbsp;</samp>3 [Requirements][#requirements]<br>
<samp>&nbsp;&nbsp;</samp>4 [Specification][#specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [JsonXml Schema][#jsonxschema]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.1 [`boolean` type][#booleantype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.2 [`string` type][#stringtype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.3 [`number` type][#numbertype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.4 [`object` type][#objecttype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.5 [`array` type][#arraytype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.6 [`null` value][#nullvalue]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [JsonXml API][#jsonxmlapi]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1 [`JxConverter`][#jxconverter]<br>
<samp>&nbsp;&nbsp;</samp>5 [Sample Documents][#samples]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1 [`paypal.json`][#paypaljson]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2 [`paypal.xml`][#paypalxml]<br>
<samp>&nbsp;&nbsp;</samp>6 [Related Resources for JsonXml][#resources]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.1 [Schemas for JSONx Schema][#json-schemas]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.1.1 [JSONx Schema 0.3.1][#jsonxml-023]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.1.2 [JSONx Schema 0.2.2][#jsonxml-022]<br>
<samp>&nbsp;&nbsp;</samp>7 [Contributing](#7-contributing)<br>
<samp>&nbsp;&nbsp;</samp>8 [License](#8-license)

## 1 Introduction

JsonXml is JSON expressed in XML. This document presents the structural part of JsonXml, as well as the normative specification of the <ins>JsonXml API</ins>. It also contains a directory of links to these related resources.

### 1.1 Dependencies on Other Specifications

The definition of the <ins>JsonXml API</ins> depends on the following specifications: [RFC4627][rfc4627] and [XMLSchema][xmlschema].

### 1.2 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Purpose

Provide an encoding of JSON documents in an analogous form that uses XML semantics, referred to as <ins>JsonXml documents</ins>.

## 3 Requirements

1. The <ins>JsonXml documents</ins> MUST be able to represent any and all legal JSON documents, as specified by [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

1. The <ins>JsonXml documents</ins> MUST be translatable to JSON documents, and vice versa, preserving all normative and non-normative features of the original document.

1. The <ins>JsonXml documents</ins> MUST provide meaningful and useful validation features via XSD validation.

## 4 Specification

### 4.1 <ins>JsonXml Schema</ins>

The JsonXml Schema defines XML elements that represent an XML-equivalent of a JSON document. The JsonXml Schema represents the JSON value types as follows:

#### 4.1.1 `boolean` Type

The `false` and `true` string literals.

<ins>Example</ins>: `true`

#### 4.1.2 `string` Type

A double-quoted string. A JSON string may require to be escaped to a string that is legal for XML. The escaped characters are [Predefined Entities in XML][xmlentities].

<ins>Example</ins>: `"string"`

#### 4.1.3 `number` Type

A number that conforms to the [RFC4627][rfc4627] section 2.4.

<ins>Example</ins>: `6.626E-34`

#### 4.1.4 `object` Type

A JSON object is represented by the `<o>` element. The `<o>` element does not have attributes, and may contain 0 or more `<p>` elements.

A JSON object property is represented by the `<p>` element. The `<p>` element contains an attribute `n` that specifies the property's name. The content of the `<p>` element can be one of [`boolean` type][#booleantype], [`string` type][#stringtype], [`number` type][#numbertype], [`object` type][#objecttype], or [`array` type][#arraytype].

#### 4.1.5 `array` Type

A JSON array is represented by the `<a>` element. The `<a>` element does not have attributes, and may contain 0 or more space delimited members conforming to [`boolean` type][#booleantype], [`string` type][#stringtype], [`number` type][#numbertype], [`object` type][#objecttype], or [`array` type][#arraytype].

#### 4.1.6 `null` Value

The `null` JSON value is represented by the string `null`.

### 4.2 <ins>JsonXml API</ins>

The <ins>JsonXml API</ins> offers facilities for validating and converting JSON and JSONx documents.

#### 4.2.1 `JxConverter`

The `JxConverter` class contains utility methods for conversion of JSON documents to XML documents, and vice versa.

<ins>Example</ins>: Convert a JSON document to a JSONx document

```java
JxConverter.jsonToXml(new JsonReader(new StringReader(json), false), true);
```

<ins>Example</ins>: Convert a JSONx document to a JSON document

```java
JxConverter.xmlToJson(new ByteArrayInputStream(xml.getBytes()), true);
```

## 5 Sample Documents

This section provides a sample JSON and its JSONx alternative.

### 5.1 `paypal.json`

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

### 5.2 `paypal.xml`

```xml
<o
  xmlns="http://www.jsonx.org/jsonxml-0.2.2.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/jsonxml-0.3.1.xsd http://www.jsonx.org/jsonxml-0.3.1.xsd">
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

## 6 Related Resources for JsonXml

### 6.1 Schemas for JsonXml

#### 6.1.1 JsonXml Schema 0.3.1

* A JSONx Schema schema document XSD [jsonxml-0.3.1.xsd][jsonxml-023] for JSONx Schema documents.

#### 6.1.2 JsonXml Schema 0.2.2

* A JSONx Schema schema document XSD [jsonxml-0.2.2.xsd][jsonxml-022] for JSONx Schema documents.

## 7 Contributing

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## 8 License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#introduction]: #1-introduction
[#dependencies]: #11-dependencies-on-other-specifications
[#conventions]: #12-conventions-used-in-this-document
[#purpose]: #2-purpose
[#requirements]: #3-requirements
[#specification]: #4-specification
[#jsonxmlschema]: #41-jsonxml-schema
[#booleantype]: #411-boolean-type
[#stringtype]: #412-string-type
[#numbertype]: #413-number-type
[#objecttype]: #414-object-type
[#arraytype]: #415-array-type
[#nullvalue]: #416-null-value
[#jsonxmlmlapi]: #42-jsonxml-api
[#jxconverter]: #421-jxconverter
[#samples]: #5-sample-documents
[#paypaljson]: #51-paypaljson
[#paypalxml]: #52-paypalxml
[#resources]: #6-related-resources-for-jsonxml
[#json-schemas]: #61-schemas-for-jsonxml
[#jsonxml-022]: #611-jsonxml-schema-022

[api]: ../binding/
[jsonxml-022]: http://www.jsonx.org/jsonxml-0.2.2.xsd
[jsonxml-023]: http://www.jsonx.org/jsonxml-0.3.1.xsd
[rfc4627]: https://www.ietf.org/rfc/rfc4627.txt
[xmlschema]: http://www.w3.org/2001/XMLSchema
[xmlentities]: https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references#Predefined_entities_in_XML