# JSONX-JSON

[![Build Status](https://travis-ci.org/openjax/jsonx.png)](https://travis-ci.org/openjax/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/openjax/jsonx/badge.svg)](https://coveralls.io/github/openjax/jsonx)
[![Javadocs](https://www.javadoc.io/badge/org.openjax.jsonx/json.svg)](https://www.javadoc.io/doc/org.openjax.jsonx/json)
[![Released Version](https://img.shields.io/maven-central/v/org.openjax.jsonx/json.svg)](https://mvnrepository.com/artifact/org.openjax.jsonx/json)

## Abstract

This document specifies the <ins>JSONX-JSON API</ins>, which offers facilities for validating and converting JSON and JSONX documents.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Dependencies on Other Specifications][#dependencies]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.2 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [Purpose][#purpose]<br>
<samp>&nbsp;&nbsp;</samp>3 [Requirements][#requirements]<br>
<samp>&nbsp;&nbsp;</samp>4 [Specification][#specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [JSONX Schema][#jsonxschema]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.1 [`boolean` type][#booleantype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.2 [`string` type][#stringtype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.3 [`number` type][#numbertype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.4 [`object` type][#objecttype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.5 [`array` type][#arraytype]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1.6 [`null` value][#nullvalue]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [JSONX-JSON API][#jsonxjsonapi]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1 [`JxConverter`][#jxconverter]<br>
<samp>&nbsp;&nbsp;</samp>5 [Sample Documents][#samples]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1 [`paypal.json`][#paypaljson]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2 [`paypal.jsonx`][#paypaljsonx]<br>
<samp>&nbsp;&nbsp;</samp>6 [Related Resources for JSONX-JSON][#resources]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.1 [Schemas for JSONX Schema][#json-schemas]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.1.1 [JSONX Schema 0.2.2][#jsonx-098]

## 1 Introduction

JSONX is JSON expressed in XML syntax. This document presents the structural part of JSONX, as well as the normative specification of the <ins>JSONX-JSON API</ins>. It also contains a directory of links to these related resources.

### 1.1 Dependencies on Other Specifications

The definition of the <ins>JSONX-JSON API</ins> depends on the following specifications: [\[RFC4627\]][rfc4627] and [\[XMLSchema\]][xmlschema].

### 1.2 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Purpose

Provide an encoding of JSON documents in an analogous form that uses XML semantics, referred to as <ins>JSONX documents</ins>.

## 3 Requirements

1. The <ins>JSONX documents</ins> MUST be able to represent any and all legal JSON documents, as specified by [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

1. The <ins>JSONX documents</ins> MUST be translatable to JSON documents, and vice versa, preserving all normative and non-normative features of the original document.

1. The <ins>JSONX documents</ins> MUST provide meaningful and useful validation features via XSD validation.

## 4 Specification

### 4.1 <ins>JSONX Schema</ins>

The JSONX Schema defines XML elements that represent an XML-equivalent of a JSON document. The JSONX Schema represents the JSON value types as follows:

#### 4.1.1 `boolean` Type

The `false` and `true` string literals.

<ins>Example</ins>: `true`

#### 4.1.2 `string` Type

A double-quoted string. A JSON string may require to be escaped to a string that is legal for XML. The escaped characters are [Predefined Entities in XML][xmlentities].

<ins>Example</ins>: `"string"`

#### 4.1.3 `number` Type

A number that conforms to the [\[RFC4627\]][rfc4627] section 2.4.

<ins>Example</ins>: `6.626E-34`

#### 4.1.4 `object` Type

A JSON object is represented by the `<o>` element. The `<o>` element does not have attributes, and may contain 0 or more `<p>` elements.

A JSON object property is represented by the `<p>` element. The `<p>` element contains an attribute `name` that specifies the property's name. The content of the `<p>` element can be one of [`boolean` type][#booleantype], [`string` type][#stringtype], [`number` type][#numbertype], [`object` type][#objecttype], or [`array` type][#arraytype].

#### 4.1.5 `array` Type

A JSON array is represented by the `<a>` element. The `<a>` element does not have attributes, and may contain 0 or more space delimited members conforming to [`boolean` type][#booleantype], [`string` type][#stringtype], [`number` type][#numbertype], [`object` type][#objecttype], or [`array` type][#arraytype].

#### 4.1.6 `null` Value

The `null` JSON value is represented by the string `null`.

### 4.2 <ins>JSONX-JSON API</ins>

The <ins>JSONX-JSON API</ins> offers facilities for validating and converting JSON and JSONX documents.

#### 4.2.1 `JxConverter`

The `JxConverter` class contains utility methods for conversion of JSON documents to JSONX documents, and vice versa.

<ins>Example</ins>: Convert a JSON document to a JSONX document

```java
JxConverter.jsonToJsonx(new JsonReader(new StringReader(json), false), true);
```

<ins>Example</ins>: Convert a JSONX document to a JSON document

```java
JxConverter.jsonxToJson(new ByteArrayInputStream(jsonx.getBytes()), true);
```

## 5 Sample Documents

This section provides a sample JSON and its JSONX alternative.

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

### 5.2 `paypal.jsonx`

```xml
<o
  xmlns="http://jsonx.openjax.org/jsonx-0.2.2.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://jsonx.openjax.org/jsonx-0.2.2.xsd http://jsonx.openjax.org/jsonx-0.2.2.xsd">
  <p name="id"> "WH-7YX49823S2290830K-0JE13296W68552352"</p>
  <p name="event_version"> "1.0"</p>
  <p name="create_time"> "2016-05-31T17:53:29Z"</p>
  <p name="resource_type"> "refund"</p>
  <p name="event_type"> "PAYMENT.SALE.REFUNDED"</p>
  <p name="summary"> "A $ 14.99 USD sale payment was refunded"</p>
  <p name="resource"> <o>
    <p name="id"> "29B43325P49508437"</p>
    <p name="state"> "completed"</p>
    <p name="amount"> <o>
      <p name="total"> "14.99"</p>
      <p name="currency"> "USD"
    </p></o></p>
    <p name="refund_to_payer"> <o>
      <p name="value"> "14.99"</p>
      <p name="currency"> "USD"
    </p></o></p>
    <p name="parent_payment"> "PAY-9MX90473V8752831HK5G4RDI"</p>
    <p name="sale_id"> "02399573KS095945W"</p>
    <p name="create_time"> "2016-05-31T17:52:18Z"</p>
    <p name="update_time"> "2016-05-31T17:52:18Z"</p>
    <p name="links"> <a>
      <o><p name="href"> "https://api.paypal.com/v1/payments/refund/29B43325P49508437"</p> <p name="rel"> "self"</p> <p name="method"> "GET"</p></o>
      <o><p name="href"> "https://api.paypal.com/v1/payments/payment/PAY-9MX90473V8752831HK5G4RDI"</p> <p name="rel"> "parent_payment"</p> <p name="method"> "GET"</p></o>
      <o><p name="href"> "https://api.paypal.com/v1/payments/sale/02399573KS095945W"</p> <p name="rel"> "sale"</p> <p name="method"> "GET"</p></o>
    </a>
  </p></o></p>
  <p name="links"> <a>
    <o><p name="href"> "https://api.paypal.com/v1/notifications/webhooks-events/WH-7YX49823S2290830K-0JE13296W68552352"</p> <p name="rel"> "self"</p> <p name="method"> "GET"</p></o>
    <o><p name="href"> "https://api.paypal.com/v1/notifications/webhooks-events/WH-7YX49823S2290830K-0JE13296W68552352/resend"</p> <p name="rel"> "resend"</p> <p name="method"> "POST"</p></o>
  </a>
</p></o>
```

## 6 Related Resources for JSONX-JSON

### 6.1 Schemas for JSONX-JSON

#### 6.1.1 JSONX Schema 0.2.2

* A JSONX Schema schema document XSD [jsonx-0.2.2.xsd][jsonxxsd] for JSONX Schema documents.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#introduction]: #1-introduction
[#dependencies]: #11-dependencies
[#conventions]: #12-conventions-used-in-this-document
[#purpose]: #2-purpose
[#requirements]: #3-requirements
[#specification]: #4-specification
[#jsonxschema]: #41-jsonxschema
[#booleantype]: #411-booleantype
[#stringtype]: #412-stringtype
[#numbertype]: #413-numbertype
[#objecttype]: #414-objecttype
[#arraytype]: #415-arraytype
[#nullvalue]: #416-nullvalue
[#jsonxjsonapi]: #42-jsonxjsonapi
[#jxconverter]: #421-jxconverter
[#samples]: #5-samples
[#paypaljson]: #51-paypaljson
[#paypaljsonx]: #52-paypaljsonx
[#resources]: #6-resources
[#json-schemas]: #61-json-schemas
[#jsonx-098]: #62-jsonx-098]

[api]: ../binding
[jsonxxsd]: http://jsonx.openjax.org/jsonx-0.2.2.xsd
[rfc4627]: https://www.ietf.org/rfc/rfc4627.txt
[xmlschema]: http://www.w3.org/2001/XMLSchema
[xmlentities]: https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references#Predefined_entities_in_XML