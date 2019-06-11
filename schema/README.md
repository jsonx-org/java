# JSON Schema

[![XSD](https://img.shields.io/badge/schema.xsd-v0.2.3-blue.svg)](http://jsonx.org/schema-0.2.3.xsd)
[![JSDx](https://img.shields.io/badge/schema.jsdx-v0.2.3-blue.svg)](http://jsonx.org/schema-0.2.3.jsdx)
[![JSD](https://img.shields.io/badge/schema.jsd-v0.2.3-blue.svg)](http://jsonx.org/schema-0.2.3.jsd)<br>
[![XSD](https://img.shields.io/badge/schema.xsd-v0.2.2-orange.svg)](http://jsonx.org/schema-0.2.2.xsd)
[![JSDx](https://img.shields.io/badge/schema.jsdx-v0.2.2-orange.svg)](http://jsonx.org/schema-0.2.2.jsdx)
[![JSD](https://img.shields.io/badge/schema.jsd-v0.2.2-orange.svg)](http://jsonx.org/schema-0.2.2.jsd)<br>
[![XSD](https://img.shields.io/badge/schema.xsd-v0.1.0-yellow.svg)](http://jsonx.org/schema-0.1.0.xsd)
[![JSDx](https://img.shields.io/badge/schema.jsdx-v0.1.0-inactive.svg)]()
[![JSD](https://img.shields.io/badge/schema.jsd-v0.1.0-inactive.svg)]()

## Abstract

This document specifies the <ins>JSON Schema Definition Language</ins>, which offers facilities for describing the structure and constraining the contents of JSON documents. The schema language, which is itself represented in an JSON vocabulary, extends the capabilities found in JSON documents.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction](#1-introduction)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Dependencies on Other Specifications](#11-dependencies-on-other-specifications)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.2 [Conventions Used in This Document](#12-conventions-used-in-this-document)<br>
<samp>&nbsp;&nbsp;</samp>2 [Purpose](#2-purpose)<br>
<samp>&nbsp;&nbsp;</samp>3 [Requirements](#3-requirements)<br>
<samp>&nbsp;&nbsp;</samp>4 [Specification](#4-specification)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [Schema Document][#schema]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [Schema Value Types][#model]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1 [`boolean` Value Type][#boolean]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1.1 [`boolean` Usage][#boolean-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.2 [`number` Value Type][#number]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.2.1 [`number` Usage][#number-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.3 [`string` Value Type][#string]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.3.1 [`string` Usage][#string-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.4 [`object` Value Type][#object]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.4.1 [`object` Property Names][#property-names]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.4.2 [`object` Usage][#object-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.5 [`array` Value Type][#array]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.5.1 [`array` Usage][#array-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.6 [`reference` Value Type][#reference]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.6.1 [`reference` Usage][#reference-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.7 [`any` Value Type][#any]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.7.1 [`any` Usage][#any-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.3 [Type Declarations][#types]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.4 [Object Properties][#properties]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.5 [Array Elements][#elements]<br>
<samp>&nbsp;&nbsp;</samp>5 [Related Resources for JSON Schema](#5-related-resources-for-json-schema)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1 [Schemas for JSON Schema](#51-schemas-for-json-schema)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1.1 [Current](#511-current)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1.2 [Obsolete](#512-obsolete)<br>
<samp>&nbsp;&nbsp;</samp>6 [Sample Schemas](#6-sample-schemas)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.1 [`structure.jsdx`](#61-structurejsdx)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.2 [`structure.jsd`](#62-structurejsd)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.3 [`datatype.jsdx`](#63-datatypesjsdx)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.4 [`datatype.jsd`](#64-datatypesjsd)

## 1 Introduction

This document sets out the structural part of the <ins>JSON Schema Definition Language</ins>. It also contains a directory of links to these related resources.

The <ins>JSON Schema Definition Language</ins> is designed to describe JSON documents by using schema components to constrain and document the meaning, usage and relationships of their constituent parts: value types and their content. Schemas may also provide for the specification of additional document information, such as normalization and defaulting of values. Schemas have facilities for self-documentation. Thus, the <ins>JSON Schema Definition Language</ins> can be used to define, describe and catalogue JSON vocabularies for JSON documents.

Any application that consumes well-formed JSON can use the <ins>JSON Schema Definition Language</ins> formalism to express syntactic, structural and value constraints applicable to its document instances. The <ins>JSON Schema Definition Language</ins> formalism allows a useful level of constraint checking to be described and implemented for a wide spectrum of JSON applications. However, the language defined by this specification does not attempt to provide _all_ the facilities that might be needed by any application. Some applications may require constraint capabilities not expressible in this language, and so may need to perform their own additional validations.

### 1.1 Dependencies on Other Specifications

The definition of the <ins>JSON Schema Definition Language</ins> depends on the following specifications: [RFC4627][rfc4627] and [XMLSchema][xmlschema].

### 1.2 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Purpose

Provide a <ins>schema language</ins> to describe normative contracts between producer and consumer ends of a protocol exchanging JSON documents.

## 3 Requirements

1. The <ins>schema language</ins> MUST constrain and document the meaning, usage, constraints and relationships of the constituent parts of a JSON document.

1. The <ins>schema language</ins> MUST provide meaningful and useful constraint rules for the 5 JSON value types: `boolean`, `number`, `string`, `object`, and `array`.

1. The <ins>schema language</ins> MUST support schema descriptions for any and all legal JSON documents, as specified by [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

1. The <ins>schema language</ins> MUST be free-of and agnostic-to patterns specific to any particular programming language.

1. The <ins>schema language</ins> MUST be able to describe itself.

## 4 Specification

The <ins>JSON Schema Definition Language</ins> (JSD) is normatively defined in an <ins>XML Schema Document</ins>, with translations expressed in the <ins>JSON/XML Schema Definition Language</ins> (JSDx), as well as the <ins>JSON Schema Definition Language</ins> (JSD) itself.

The JSD is comprised of 5 structural abstractions:

<samp>&nbsp;&nbsp;</samp>4.1 [Schema Document][#schema]<br>
<samp>&nbsp;&nbsp;</samp>4.2 [Schema Value Types][#model]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1 [`boolean` Value Type][#boolean]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1.1 [`boolean` Usage][#boolean-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.2 [`number` Value Type][#number]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.2.1 [`number` Usage][#number-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.3 [`string` Value Type][#string]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.3.1 [`string` Usage][#string-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.4 [`object` Value Type][#object]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.4.1 [`object` Property Names][#property-names]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.4.2 [`object` Usage][#object-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.5 [`array` Value Type][#array]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.5.1 [`array` Usage][#array-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.6 [`reference` Value Type][#reference]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.6.1 [`reference` Usage][#reference-usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.7 [`any` Value Type][#any]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.7.1 [`any` Usage][#any-usage]<br>
<samp>&nbsp;&nbsp;</samp>4.3 [Type Declarations][#types]<br>
<samp>&nbsp;&nbsp;</samp>4.4 [Object Properties][#properties]<br>
<samp>&nbsp;&nbsp;</samp>4.5 [Array Elements][#elements]

### 4.1 Schema Document

The <samp>**schema**</samp> is the root object of the JSD, and contains [type][#types] definitions.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **schema** )</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | <samp>jx:ns</samp><br>&nbsp;<br>&nbsp;<br><samp>jx:schemaLocation</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br><samp>doc</samp><br><samp>[a-zA-Z_$][-a-zA-Z\\d_$]*</samp><br>&nbsp;<br>&nbsp; | _Namespace of the JSON Schema._ Required.<br>&nbsp;&nbsp;Used by schema processors to determine to which<br>&nbsp;&nbsp;version of the JSON Schema the JSD is written.<br>_Location URL of namespace._ Optional.<br>&nbsp;&nbsp;Specified as: `"%NAMESPACE_URI% %LOCATION_URL%"`<br>&nbsp;&nbsp;Used by schema processors to determine location of<br>&nbsp;&nbsp;schema definition for a namespace.<br>Text comments. Optional.<br>_[Type Declaration][#types]_. Optional.<br>&nbsp;&nbsp;Root object definitions that are referenceable<br>&nbsp;&nbsp;throughout the schema. |

1. <ins>Example</ins>: `jsd`
   ```json
   {
     "jx:ns": "http://www.jsonx.org/schema-0.2.3.jsd",
     "jx:schemaLocation": "http://www.jsonx.org/schema-0.2.3.jsd http://www.jsonx.org/schema-0.2.3.jsd",
     ...
   }
   ```

1. <ins>Example</ins>: `jsdx`
   ```xml
   <schema
      xmlns="http://www.jsonx.org/schema-0.2.3.xsd"
      xsi:schemaLocation="http://www.jsonx.org/schema-0.2.3.xsd http://www.jsonx.org/schema-0.2.3.xsd">
     ...
   </schema>
   ```

### 4.2 Schema Value Types

The <ins>Schema Value Types</ins> define the constraint properties for the five JSON value types: <samp>**boolean**</samp>, <samp>**number**</samp>, <samp>**string**</samp>, <samp>**object**</samp>, and <samp>**array**</samp>. Aditionally, the <ins>JSON Schema Definition Language</ins> defines two meta value types named <samp>**any**</samp> and <samp>**reference**</samp>.

Each <ins>Schema Value Type</ins> supports the `doc` attribute.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **boolean** \|&nbsp;</samp><br><samp>&nbsp;&nbsp;**number** \|</samp><br><samp>&nbsp;&nbsp;**string** \|</samp><br><samp>&nbsp;&nbsp;**object** \|</samp><br><samp>&nbsp;&nbsp;**array** \|</samp><br><samp>&nbsp;&nbsp;any \|</samp><br><samp>&nbsp;&nbsp;reference )</samp> | <samp>doc</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | Text comments. Optional.<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; |

* <sub>`JSD`</sub>
  ```json
  { "doc": "Comment for this element",
    ...
  }
  ```

* <sub>`JSDx`</sub>
  ```xml
  <any doc="Comment for this element">
    ...
  </any>
  ```

#### 4.2.1 `boolean` Value Type

The <samp>**boolean**</samp> value type is the only value type that lacks validation constraints.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **boolean** )</samp> | <samp>jx:type</samp> | <samp>boolean</samp> |

##### 4.2.1.1 `boolean` Usage

* **boolean**: Default<br>
  The **boolean** type is used for the boolean values: `true` and `false`.
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "boolean" }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <boolean/>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="boolean"/>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `true` | :white_check_mark: | The value `true`. |
    | `false` | :white_check_mark: | The value `false`. |
    | `TRUE` | :x: | `TRUE` is not a valid **boolean** type. |
    | `FALSE` | :x: | `FALSE` is not a valid **boolean** type. |
    | `0` | :x: | `0` is not a valid **boolean** type. |
    | `1` | :x: | `1` is not a valid **boolean** type. |
    | `"true"` | :x: | Boolean as string. |

#### 4.2.2 `number` Value Type

The <samp>**number**</samp> value type defines two validation constraints for <samp>**number**</samp> JSON value types: <samp>scale</samp>, and <samp>range</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **number** )</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | <samp>jx:type</samp><br><samp>scale</samp><br>&nbsp;<br>&nbsp;<br><samp>range</samp><br>&nbsp;<br>&nbsp; | <samp>number</samp><br><samp>(0\|1\|2\|...)</samp><br>&nbsp;&nbsp;The number of digits to the right of the decimal point.<br>&nbsp;&nbsp;**If a value is not specified, the scale is unbounded.**<br>_Numerical range_<br>&nbsp;&nbsp;Specifies the minimum and maximum limits in [interval<br>notation][interval-notation]. |

##### 4.2.2.1 `number` Usage

* **number**: Default<br>
  The **number** type is used for any numeric type, either integers or floating point numbers.
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "number" }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <number/>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="number"/>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `5` | :white_check_mark: | Integer. |
    | `-7.12` | :white_check_mark: | Floating point number. |
    | `12.332794E-5` | :white_check_mark: | Exponential notation. |
    | `"7"` | :x: | Number as string. |

* **number**: `scale`<br>
  The `scale` property specifies the maximum number of accepted digits after the decimal point.<br>A `scale` of `3` represents a number with a maximum of 3 digits after the decimal.<br>A `scale` of `0` represents an integer.
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "number", "scale": 2 }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <number scale="2"/>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="number" scale="2"/>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `5.12` | :white_check_mark: | Floating point number with 2 digits after the decimal point. |
    | `9.2E-1` | :white_check_mark: | Exponential notation representing a number with 2 digits after the decimal point. |
    | `-0.1` | :white_check_mark: | Floating point number with 1 digit after the decimal point. |
    | `8.123` | :x: | Floating point number with 3 digits after the decimal point. |
    | `8.3-2` | :x: | Exponential notation representing a number with 3 digits after the decimal point. |
    | `"7.65"` | :x: | Number as string. |

* **number**: `range`<br>
  The `range` property specifies the numerical range (min and max) of accepted values in [interval notation][interval-notation].<br>A `range` of `[0,10]` represents a number between `0` (inclusive) and `10` (inclusive).<br>A `range` of `(0,10)` represents a number between `0` (exclusive) and `10` (exclusive).<br>A `range` of `(1.2E1,)` represents a number greater than `1.2E1` (exclusive).<br>A `range` of `(,-9.8]` represents a number less than `9.8` (inclusive).
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "number", "range": "[-2,7.5)" }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <number range="[-2,7.5)"/>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="number" range="[-2,7.5)"/>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `5.12` | :white_check_mark: | Floating point number between `-2` (inclusive) and `7.5` (exclusive). |
    | `0.3E1` | :white_check_mark: | Exponential notation representing a number between `-2` (inclusive) and `7.5` (exclusive). |
    | `-2` | :white_check_mark: | Integer between `-2` (inclusive) and `7.5` (exclusive). |
    | `7.49999999999` | :white_check_mark: | Floating point number between `-2` (inclusive) and `7.5` (exclusive). |
    | `-2.0000000001` | :x: | Floating point number less than `-2` (inclusive). |
    | `7.5` | :x: | Floating point number greater than `7.5` (exclusive). |
    | `"6.65"` | :x: | Number as string. |

#### 4.2.3 `string` Value Type

The <samp>**string**</samp> value type defines one validation constraint for <samp>**string**</samp> JSON value types: <samp>pattern</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **string** )</samp><br>&nbsp;<br>&nbsp; | <samp>jx:type</samp><br><samp>pattern</samp><br>&nbsp; | <samp>string</samp><br>_Regular expression_<br>&nbsp;&nbsp;Specifies the regular expression. |

##### 4.2.3.1 `string` Usage

* **string**: Default<br>
  The **string** type is used for strings of text. It may contain Unicode characters.
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "string" }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <string/>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="string"/>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `"Déjà vu"` | :white_check_mark: | String with unicode. |
    | `"D\u00e9j\u00e0 vu"` | :white_check_mark: | String with escaped unicode. |
    | `""` | :white_check_mark: | Empty string. |
    | `"42"` | :white_check_mark: | String representing a number. |
    | `42` | :x: | A number. |

* **string**: `pattern`<br>
  The `pattern` property is used to restrict a string to a particular regular expression, as defined in JavaScript ([ECMA 262][ecma262]).<br>A `pattern` of `^[a-z]+$` represents a string of one or more lowercase latin characters.<br>A `pattern` of `^.{12}$` represents a string of any 12 characters.<br>A `pattern` of `^(\(\d{3}\) )?\d{3}-\d{4}$` represents a string matching a US phone number.
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "string", "pattern": "^(\\(\\d{3}\\) )?\\d{3}-\\d{4}$" }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <string pattern="^(\\(\\d{3}\\) )?\\d{3}-\\d{4}$"/>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="string" pattern="^(\\(\\d{3}\\) )?\\d{3}-\\d{4}$"/>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `"(800) 356-9377"` | :white_check_mark: | String with a 10 digit phone number. |
    | `"356-9377"` | :white_check_mark: | String with a 7 digit phone number. |
    | `"(888) 356-9377 ext. 111"` | :x: | Unmatched "ext. 111". |
    | `"(800) FLO-WERS"` | :x: | Phone number with letters. |
    | `""` | :x: | Empty string. |

#### 4.2.4 `object` Value Type

The <samp>**object**</samp> value type defines three validation constraints for <samp>**object**</samp> JSON value types: <samp>abstract</samp>, <samp>extends</samp>, and named <samp>properties</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <a name="objecttype"><samp>( **object** )</samp></a><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | <samp>jx:type</samp><br><samp>properties</samp><br>&nbsp;<br><samp>abstract</samp><br>&nbsp;<br><samp>extends</samp><br>&nbsp;<br>&nbsp; | <samp>object</samp><br>_[Property declarations][#properties]_<br>&nbsp;&nbsp;Map of object properties.<br><samp>(true\|**false**)</samp><br>&nbsp;&nbsp;Whether the object is not allowed to be instantiated.<br>_Name [<samp>( **object** )</samp> type](#objecttype)_<br>&nbsp;&nbsp;Name of root-level object type declaration specifying<br>&nbsp;&nbsp;object inheritence. |


##### 4.2.4.1 `object` Property Names

Names of object properties are considered as regular expressions. If an object declaration defines a property with the name <samp>"[a-z]+"</samp>, it means that this name matches any property whose name is one or more alpha characters. This also means that the name <samp>"foo"</samp> will only match "foo". If an object defines a property with a regular expression name that matches more than 1 string, the object will accept all the matching names (i.e. an object with multiple properties that match a single property definition with a regular expression name will be valid). If, however, there are multiple defined properties with regular expression name patterns that capture the same name, an associated value will be validated against the first matching property definition. The example below shows an <samp>**any**</samp> property that matches all names. Such a definition of an **object** type will match any object, except for `{}` (because at least one <samp>**any**</samp> property is required).

* <sub>`JSD`</sub>
  ```json
  { "jx:type": "object", "properties": {
    ".*": { "jx:type": "any" } }
  }
  ```
* <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
  ```xml
  <object>
    <property name=".*" xsi:type="any"/>
  </object>
  ```
* <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
  ```xml
  <property xsi:type="object">
    <property name=".*" xsi:type="any"/>
  </property>
  ```
  | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
  |-:|-|-|
  | `{"foo":"bar"}` | :white_check_mark: | Any property is valid. |
  | `{"foo":"bar","wow":true}` | :white_check_mark: | Any property is valid. |
  | `{"foo":"bar","wow":true,"cool":42}` | :white_check_mark: | Any property is valid. |
  | `{}` | :x: | At least one <samp>**any**</samp> property is required. |

##### 4.2.4.2 `object` Usage

* **object**: Default<br>
  The **object** type is used for mapping "keys" to "values".<br>_The default **object** is not very useful, because it has no property mappings._
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "object" }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <object/>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="object"/>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `{}` | :white_check_mark: | An object without properties. |
    | `{"foo":"bar"}` | :x: | An object with property "foo" mapping to value "bar". |

* **object**: `properties`<br>
  The `properties` property specifies a map of accepted properties for the object.<br>Keys in the `properties` map are considered as regular expressions (for more info, see [Property Names][#property-names]).<br>Values in the `properties` map are objects specifying accepted [value types][#properties].
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "object", "properties": {
      "foo": { "jx:type": "string", "pattern": "^[a-z]{,3}$", "nullable": true, "use": "optional" } }
    }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <object>
      <property name="foo" xsi:type="string" pattern="^[a-z]{,3}$" nullable="true" use="optional"/>
    </object>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="object">
      <property name="foo" xsi:type="string" pattern="^[a-z]{,3}$" nullable="true" use="optional"/>
    </property>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `{"foo":"bar"}` | :white_check_mark: | An object with property "foo" mapping to value "bar". |
    | `{"foo":""}` | :white_check_mark: | An object with property "foo" mapping to empty string. |
    | `{}` | :white_check_mark: | An object without properties is ok, because "foo" is optional. |
    | `{"foo":null}` | :white_check_mark: | An object with property "foo" mapping to `null` is ok, because "foo" is nullable. |
    | `{"foo":false}` | :x: | An object with property "foo" mapping to `false`. |
    | `{"other":""}` | :x: | An object with property "other" mapping to empty string. |

* **object**: `abstract`<br>
  The `abstract` property specifies whether the declared object is allowed to represent a value instance.<br>If `abstract` is true, a value of the declared type is allowed to exits.<br>If `abstract` is false, the declared type can only be used as a super-type of another object type declaration specifying the `extends` property that links to the name of the abstract type.<br>_**The `abstract` property is only allowed to appear on [type declarations][#types].**_
  * <sub>`JSD` <samp>([type declarations][#types])</samp></sub>
    ```json
    { "myAbstractObject":
        { "jx:type": "object", "abstract": true, "properties": {
          "foo": { "jx:type": "string", "pattern": "^[a-z]{,3}$", "nullable": true, "use": "optional" } } }
    }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types])</samp></sub>
    ```xml
    <object name="myAbstractObject" abstract="true">
      <property name="foo" xsi:type="string" pattern="^[a-z]{,3}$" nullable="true" use="optional"/>
    </object>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `{"foo":"bar"}` | :x: | Instances of `myAbstractObject` are not allowed.<br>Refer to `extends` property below. |

* **object**: `extends`<br>
  The `extends` property accepts a name of an **object** type declaration, which specifies object inheritence.<br>The `extends` property can only specify names of **object** [type declarations][#types].<br>The `extends` property can specify names of **object** [type declarations][#types] that are `abstract`, or not.

  **Type declarations**

  * <sub>`JSD` <samp>([type declarations][#types])</samp></sub>
    ```json
    { "myAbstractObject":
        { "jx:type": "object", "abstract": true, "properties": {
          "foo": { "jx:type": "string", "pattern": "^[a-z]{,3}$", "nullable": true, "use": "optional" } } },
      "myRealObject":
        { "jx:type": "object", "extends": "myAbstractObject", "properties": {
          "thisIsCool": { "jx:type": "boolean", "nullable": false } } }
    }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types])</samp></sub>
    ```xml
    <object name="myAbstractObject" abstract="true">
      <property name="foo" xsi:type="string" pattern="^[a-z]{,3}$" nullable="true" use="optional"/>
    </object>
    <object name="myRealObject" extends="myAbstractObject">
      <property name="thisIsCool" xsi:type="boolean" nullable="false"/>
    </object>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `{"foo":"bar"}` | :white_check_mark: | An instance of `myRealObject` object with property "foo" mapping to value "bar". |
    | `{"foo":"bar","thisIsCool":true}` | :white_check_mark: | An instance of `myRealObject` object with property "foo" mapping to value "bar", and "thisIsCool" to true. |
    | `{"foo":""}` | :white_check_mark: | An instance of `myRealObject` object with property "foo" mapping to empty string. |
    | `{"thisIsCool":true}` | :white_check_mark: | An instance of `myRealObject` object with property "thisIsCool" mapping to true. |
    | `{"thisIsCool":null}` | :x: | The property "thisIsCool" is not nullable. |

  **Object Properties**

  * <sub>`JSD` <samp>[(object properties)][#properties]</samp></sub>
    ```json
    { "myAbstractObject":
        { "jx:type": "object", "abstract": true, "properties": {
          "foo": { "jx:type": "string", "pattern": "^[a-z]{,3}$", "nullable": true, "use": "optional" } } },
      "rootObject":
        { "jx:type": "object", "abstract": true, "properties": {
          "myRealObject":
            { "jx:type": "object", "extends": "myAbstractObject", "properties": {
              "thisIsCool": { "jx:type": "boolean", "nullable": false } } }
    }
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <object name="myAbstractObject" abstract="true">
      <property name="foo" xsi:type="string" pattern="^[a-z]{,3}$" nullable="true" use="optional"/>
    </object>
    <object name="rootObject">
      <property xsi:type="object" extends="myAbstractObject">
        <property name="thisIsCool" xsi:type="boolean" nullable="false"/>
      </property>
    </object>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `{"myRealObject": {"foo":"bar"}}` | :white_check_mark: | A `rootObject` object with an instance of `myRealObject` object with property "foo" mapping to value "bar". |
    | `{"myRealObject": {"foo":"bar","thisIsCool":true}}` | :white_check_mark: | A `rootObject` object with an instance of `myRealObject` object with property "foo" mapping to value "bar", and "thisIsCool" to true. |
    | `{"myRealObject": {"foo":""}}` | :white_check_mark: | A `rootObject` object with an instance of `myRealObject` object with property "foo" mapping to empty string. |
    | `{"myRealObject": {"thisIsCool":true}}` | :white_check_mark: | A `rootObject` object with an instance of `myRealObject` object with property "thisIsCool" mapping to true. |
    | `{"myRealObject": {"thisIsCool":null}}` | :x: | The property "thisIsCool" is not nullable. |
    | `{"thisIsCool":null}` | :x: | The `rootObject` does not declare the "thisIsCool" property. |

#### 4.2.5 `array` Value Type

The <samp>**array**</samp> value type defines three validation constraints for <samp>**array**</samp> JSON value types: <samp>minIterate</samp>, <samp>maxIterate</samp>, and member <samp>elements</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <a name="arraytype"><samp>( **array** )</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;  | <samp>jx:type</samp><br><samp>elements</samp><br>&nbsp;<br><samp>minIterate</samp><br>&nbsp;<br>&nbsp;<br><samp>maxIterate</samp><br>&nbsp;<br>&nbsp; | <samp>array</samp><br><samp>\[</samp> [Element declaration][#elements]<samp> , ...\]</samp><br>&nbsp;&nbsp;Array of member element declarations.<br><samp>(**1**\|2\|...)</samp><br>&nbsp;&nbsp;Specifies the minimum inclusive number of iterations of<br>&nbsp;&nbsp;child member elements.<br><samp>(**1**\|2\|...\|unbounded)</samp><br>&nbsp;&nbsp;Specifies the maximum inclusive number of iterations of<br>&nbsp;&nbsp;child member elements. |

##### 4.2.5.1 `array` Usage

* **array**: Default<br>
  The **array** type is used for ordered member elements.<br>_The default **array** is not very useful, because it has no member elements._
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "array" }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <array/>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="array"/>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `[]` | :white_check_mark: | An array without member elements. |
    | `[null]` | :x: | An array with one `null` element. |

* **array**: `elements`<br>
  The `elements` property specifies a list of accepted member elements.
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "array", "elements": [
        { "jx:type": "boolean", "minOccurs": "0", "maxOccurs": "1" },
        { "jx:type": "string", "minOccurs": "1", "maxOccurs": "2" } ]
    }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <array>
      <boolean minOccurs="0" maxOccurs="1"/>
      <string minOccurs="1" maxOccurs="2"/>
    </array>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="array">
      <boolean minOccurs="0" maxOccurs="1"/>
      <string minOccurs="1" maxOccurs="2"/>
    </property>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `[true, "hello"]` | :white_check_mark: | An array with one boolean element, and one string element. |
    | `["hello"]` | :white_check_mark: | An array with only one string element, since zero boolean elements is allowed. |
    | `["hello", "world"]` | :white_check_mark: | An array with only two string elements, since zero boolean elements is allowed. |
    | `["hello", "world", "again"]` | :x: | A maximum of 2 string elements is allowed. |
    | `[true, "hello", "world"]` | :white_check_mark: | An array with one boolean element and two string elements. |
    | `[true, false, "hello"]` | :x: | A maximum of 1 boolean elements is allowed. |
    | `[true]` | :x: | A minimum of 1 string elements are required. |
    | `["hello", true]` | :x: | The boolean element must precede the string element. |
    | `[]` | :x: | At least one string element is required. |

* **array**: `minIterate` and `maxIterate`<br>
  The `minIterate` and `maxIterate` properties specify the cardinality of allowed iterations (repetitions) of the member elements.<br>By default, both `minIterate` and `maxIterate` are `1`, which allows a single iteration of the specified member elements.<br>If `minIterate` is `0`, an empty array is valid regardless of the member elements specified.<br>If `maxIterate` is `n`, the sequence of member elements can repeat `n` times.
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "array", "minIterate": "0", "maxIterate": "2", "elements": [
        { "jx:type": "boolean", "minOccurs": "0", "maxOccurs": "1" },
        { "jx:type": "string", "minOccurs": "1", "maxOccurs": "2" } ]
    }
    ```
  * <sub>`JSDx` <samp>([type declarations][#types] and [array elements][#elements])</samp></sub>
    ```xml
    <array minIterate="0" maxIterate="2">
      <boolean minOccurs="0" maxOccurs="1"/>
      <string minOccurs="1" maxOccurs="2"/>
    </array>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="array" minIterate="0" maxIterate="2">
      <boolean minOccurs="0" maxOccurs="1"/>
      <string minOccurs="1" maxOccurs="2"/>
    </property>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `[true, "hello"]` | :white_check_mark: | An array with one boolean element, and one string element. |
    | `["hello"]` | :white_check_mark: | An array with only one string element, since zero boolean elements is allowed. |
    | `["hello", "world"]` | :white_check_mark: | An array with only two string elements, since zero boolean elements is allowed. |
    | `["hello", "world", "again"]` | :white_check_mark: | The 3rd string element satisfies the 2nd iteration. |
    | `["hello", "world", "again", "and", "again"]` | :x: | The 5th string element would require 3 iterations, but `maxIterate` is `2`. |
    | `[true, "hello", "world", true, "and", "again"]` | :white_check_mark: | An array with two full iterations. |
    | `[true, false, "hello"]` | :x: | A maximum of 1 boolean elements is allowed to appear before a required string element. |
    | `[true]` | :x: | A minimum of 1 string elements are required. |
    | `["hello", true, "world"]` | :white_check_mark: | The boolean element and "world" satisfy the 2nd iteration. |
    | `[]` | :white_check_mark: | An empty array is allowed due to `minIterate` set to `0`. |

#### 4.2.6 `reference` Value Type

The <samp>**reference**</samp> value type defines one validation constraint: <samp>type</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **reference** )</samp><br>&nbsp;<br>&nbsp; | <samp>jx:type</samp><br><samp>type</samp><br>&nbsp; | <samp>reference</samp><br>_Name of [type declaration][#types]_<br>&nbsp;&nbsp;Name of root-level type declaration to reference. |

##### 4.2.6.1 `reference` Usage

* **reference**: `type`<br>
  The **reference** type is used to restrict content by specifying a single type declaration reference.<br>
  _The **reference** type is only allowed to appear as an object property or an array member._

  The `type` property specifies the accepted type declaration.

  **Object Properties**

  * <sub>`JSD`</sub>
    ```json
    { "myNumber": { "jx:type": "number" },
      "myObject": { "jx:type": "object", "properties": {
        "numOrStr": { "jx:type": "reference", "type": "myNumber" } } }
    }
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <schema>
      <number name="myNumber"/>
      <object name="myArray">
        <property name="numOrStr" xsi:type="reference" type="myNumber"/>
      </object>
    </schema>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `{"numOrStr":5.2}` | :white_check_mark: | A number is allowed. |
    | `{"numOrStr":"hello"}` | :x: | A string is not allowed. |
    | `{"numOrStr":false}` | :x: | A boolean is not allowed. |
    | `{}` | :x: | The `numOrStr` property is required (by default, all properties specify `use=required`). |

  **Array Elements**

  * <sub>`JSD`</sub>
    ```json
    { "myNumber": { "jx:type": "number" },
      "myString": { "jx:type": "string" },
      "myArray": { "jx:type": "array", "elements": [
        { "jx:type": "any", "types": "myNumber myString" } ] }
    }
    ```
  * <sub>`JSDx` <samp>([array elements][#elements])</samp></sub>
    ```xml
    <schema>
      <number name="myNumber"/>
      <array name="myArray">
        <reference type="myNumber"/>
      </string>
    </schema>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `[5.2]` | :white_check_mark: | A number is allowed. |
    | `["hello"]` | :x: | A string is not allowed. |
    | `[false]` | :x: | A boolean is not allowed. |
    | `[]` | :x: | The `any` element is required (by default, all [array elements][#elements] specify `minOccurs=1`). |
    | `[5.2,6,4,2]` | :white_check_mark: | Repeating occurrences of **myNumber** are allowed (by default, all [array elements][#elements] specify `maxOccurs=unbounded)`. |

#### 4.2.7 `any` Value Type

The <samp>**any**</samp> value type defines one validation constraint: <samp>types</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **any** )</samp><br>&nbsp;<br>&nbsp; | <samp>jx:type</samp><br><samp>types</samp><br>&nbsp; | <samp>any</samp><br><samp>\[</samp> Name of [type declaration][#elements]<samp> , ...\]</samp><br>&nbsp;&nbsp;Array of [type declarations][#types]. |

##### 4.2.7.1 `any` Usage

* **any**: Default<br>
  The **any** type is used to restrict content by specifying a list of accepted [type declarations][#types], or an empty list for wildcard.<br>
  _The **any** type is only allowed to appear as an object property or an array member._
  * <sub>`JSD`</sub>
    ```json
    { "jx:type": "any" }
    ```
  * <sub>`JSDx` <samp>([array elements][#elements])</samp></sub>
    ```xml
    <any/>
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <property xsi:type="any"/>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `true` | :white_check_mark: | Any value type is allowed. |
    | `"hello"` | :white_check_mark: | Any value type is allowed. |
    | `4.53` | :white_check_mark: | Any value type is allowed. |
    | `{"foo":"bar"}` | :white_check_mark: | Any value type is allowed. |
    | `[true,"world"]` | :white_check_mark: | Any value type is allowed. |
    | `[]` | :white_check_mark: | Any value type is allowed. |

* **any**: `types`<br>
  The `types` property specifies accepted [type declarations][#types].

  **Object Properties**

  * <sub>`JSD`</sub>
    ```json
    { "myNumber": { "jx:type": "number" },
      "myString": { "jx:type": "string" },
      "myObject": { "jx:type": "object", "properties": {
        "numOrStr": { "jx:type": "any", "types": "myNumber myString" } } }
    }
    ```
  * <sub>`JSDx` <samp>[(object properties)][#properties]</samp></sub>
    ```xml
    <schema>
      <number name="myNumber"/>
      <string name="myString"/>
      <object name="myArray">
        <property name="numOrStr" xsi:type="any" types="myNumber myArray"/>
      </object>
    </schema>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `{"numOrStr":5.2}` | :white_check_mark: | A number is allowed. |
    | `{"numOrStr":"hello"}` | :white_check_mark: | A string is allowed. |
    | `{"numOrStr":false}` | :x: | A boolean is not allowed. |
    | `{}` | :x: | The `numOrStr` property is required (by default, all properties specify `use=required`). |

  **Array Elements**

  * <sub>`JSD`</sub>
    ```json
    { "myNumber": { "jx:type": "number" },
      "myString": { "jx:type": "string" },
      "myArray": { "jx:type": "array", "elements": [
        { "jx:type": "any", "types": "myNumber myString" } ] }
    }
    ```
  * <sub>`JSDx` <samp>([array elements][#elements])</samp></sub>
    ```xml
    <schema>
      <number name="myNumber"/>
      <string name="myString"/>
      <array name="myArray">
        <any types="myNumber myArray"/>
      </string>
    </schema>
    ```
    | <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>Value | Pass | Description<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
    |-:|-|-|
    | `[5.2]` | :white_check_mark: | A number is allowed. |
    | `["hello"]` | :white_check_mark: | A string is allowed. |
    | `[false]` | :x: | A boolean is not allowed. |
    | `[]` | :x: | The `any` element is required (by default, all [array elements][#elements] specify `minOccurs=1`). |
    | `["hello",5.2,"world","foo","bar",6,4,2]` | :white_check_mark: | Repeating occurrences of **myNumber** or **myString** are allowed (by default, all [array elements][#elements] specify `maxOccurs=unbounded)`. |

### 4.3 Type Declarations

The declarative <samp>**type**</samp> objects are immediate children of the <samp>[**schema**][#schema]</samp> object, and represent type definitions that are referenceable throughout the schema, via `any.types`, `object.extends`, `array.elements.reference`, and `object.properties.reference`. The <samp>**type**</samp> objects inherit constraint properties from <samp>[**model**][#model]</samp> definitions with the following extensions: (Note that the <samp>**any**</samp> and <samp>**reference**</samp> value types are not available as a declarative <samp>**type**s</samp>).

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **boolean** \|&nbsp;&nbsp;</samp><br><samp>&nbsp;&nbsp;**number** \|</samp><br><samp>&nbsp;&nbsp;**string** \|</samp><br><samp>&nbsp;&nbsp;**object** \|</samp><br><samp>&nbsp;&nbsp;**array** \|</samp><br><samp>&nbsp;&nbsp;~~any~~ \|</samp><br><samp>&nbsp;&nbsp;~~reference~~ )</samp> | <samp>name</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | _Name of declared type_<br>&nbsp;&nbsp;Name of type declaration to be used as reference<br>&nbsp;&nbsp;throuthout the JSD.<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; |

1. <ins>Example</ins>: `jsd`
   ```json
   { "jx:ns": "http://www.jsonx.org/schema-0.2.3.jsd",
     ...
     "rootArray": { "jx:type": "array",
       "elements": [...] },
     "rootBoolean": { "jx:type": "boolean" },
     "rootNumber": { "jx:type": "number" },
     "rootString": { "jx:type": "string" },
     "rootObject": { "jx:type": "object",
       "properties": {...} }
     ...
   }
   ```

1. <ins>Example</ins>: `jsdx`
   ```xml
   <schema xmlns="http://www.jsonx.org/schema-0.2.3.xsd">
     ...
     <array name="rootArray">
       ...
     </array>
     <boolean name="rootBoolean"/>
     <number name="rootNumber"/>
     <string name="rootString"/>
     <object name="rootObject">
       ...
     </object>
     ...
   </schema>
   ```

### 4.4 Object Properties

The <samp>**property**</samp> objects define properties for the declarative objects that belong to an <samp>**[object](#objecttype)**</samp>. The <samp>**property**</samp> objects inherit constraint properties from <samp>[**model**][#model]</samp> definitions with the following extensions:

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **boolean** \|&nbsp;</samp><br><samp>&nbsp;&nbsp;**number** \|</samp><br><samp>&nbsp;&nbsp;**string** \|</samp><br><samp>&nbsp;&nbsp;**object** \|</samp><br><samp>&nbsp;&nbsp;**array** \|</samp><br><samp>&nbsp;&nbsp;any \|</samp><br><samp>&nbsp;&nbsp;reference )</samp> | <samp>use</samp><br>&nbsp;<br><samp>nullable</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | <samp>(**required**\|optional)</samp><br>&nbsp;&nbsp;Specifies whether the property use is required or optional.<br><samp>(**true**\|false)</samp><br>&nbsp;&nbsp;Specifies whether the property is nullable.<br>&nbsp;<br>&nbsp;<br>&nbsp; |

1. <ins>Example</ins>: `jsd`
   ```json
   { "jx:ns": "http://www.jsonx.org/schema-0.2.3.jsd",
     ...
     "rootObject": { "jx:type": "object",
       "properties": {
         "propArray": { "jx:type": "array", "nullable": true, "use": "required",
           "elements": [...] },
         "propBoolean": { "jx:type": "boolean", "nullable": true, "use": "required" },
         "propNumber": { "jx:type": "number", "nullable": true, "use": "required" },
         "propString": { "jx:type": "string", "nullable": true, "use": "required" },
         "propObject": { "jx:type": "object", "nullable": true, "use": "optional",
           "properties": {...} },
         "propReference": { "jx:type": "reference", "nullable": true, "use": "required", "type": "..." },
         ".*": { "jx:type": "any", "nullable": true, "use": "optional", "types": "..." }
       }
     }
     ...
   }
   ```

1. <ins>Example</ins>: `jsdx`
   ```xml
   <schema xmlns="http://www.jsonx.org/schema-0.2.3.xsd">
     ...
     <object name="rootObject">
       <property name="propArray" xsi:type="array" nullable="true" use="required">
         ...
       </property>
       <property name="propBoolean" xsi:type="boolean" nullable="true" use="required"/>
       <property name="propNumber" xsi:type="number" nullable="true" use="required"/>
       <property name="propString" xsi:type="string" nullable="true" use="required"/>
       <property name="propObject" xsi:type="object" nullable="true" use="optional">
         ...
       </property>
       <property name="propReference" xsi:type="reference" nullable="true" use="required" type="..."/>
       <property names=".*" xsi:type="any" nullable="true" use="optional" types="..."/>
     </object>
     ...
   </schema>
   ```

### 4.5 Array Elements

The <samp>**element**</samp> objects define properties for the declarative objects that belong to an <samp>**[array](#arraytype)**</samp>. The <samp>**element**</samp> objects inherit constraint properties from [value type][#model] definitions with the following extensions:

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **boolean** \|&nbsp;</samp><br><samp>&nbsp;&nbsp;**number** \|</samp><br><samp>&nbsp;&nbsp;**string** \|</samp><br><samp>&nbsp;&nbsp;**object** \|</samp><br><samp>&nbsp;&nbsp;**array** \|</samp><br><samp>&nbsp;&nbsp;any \|</samp><br><samp>&nbsp;&nbsp;reference )</samp><br>&nbsp; | <samp>nullable</samp><br>&nbsp;<br><samp>minOccurs</samp><br>&nbsp;<br>&nbsp;<br><samp>maxOccurs</samp><br>&nbsp;<br>&nbsp; | <samp>(**true**\|false)</samp><br>&nbsp;&nbsp;Specifies whether the property is nullable.<br><samp>(0\|**1**\|2\|...)</samp><br>&nbsp;&nbsp;Specifies the minimum inclusive number of occurrence of<br>&nbsp;&nbsp;the member element.<br><samp>(0\|1\|2\|...\|**unbounded**)</samp><br>&nbsp;&nbsp;Specifies the maximum inclusive number of occurrence of<br>&nbsp;&nbsp;the member element. |

1. <ins>Example</ins>: `jsd`
   ```json
   { "jx:ns": "http://www.jsonx.org/schema-0.2.3.jsd",
     ...
     "rootArray": {
       "jx:type": "array",
       "elements": [
         { "jx:type": "boolean", "minOccurs": "1", "maxOccurs": "unbounded", "nullable": true},
         { "jx:type": "number", "minOccurs": "1", "maxOccurs": "unbounded", "nullable": true },
         { "jx:type": "string", "minOccurs": "1", "maxOccurs": "unbounded", "nullable": true },
         { "jx:type": "array", "minOccurs": "1", "maxOccurs": "unbounded", "nullable": true,
           "elements": [...] },
         { "jx:type": "reference", "minOccurs": "1", "maxOccurs": "unbounded", "nullable": true, "type": "..." },
         { "jx:type": "any", "minOccurs": "1", "maxOccurs": "unbounded", "nullable": true, "types": "..." }
       ]
     }
     ...
   }
   ```

1. <ins>Example</ins>: `jsdx`
   ```xml
   <schema xmlns="http://www.jsonx.org/schema-0.2.3.xsd">
     ...
     <array name="rootArray">
       <boolean minOccurs="1" maxOccurs="unbounded" nullable="true"/>
       <number minOccurs="1" maxOccurs="unbounded" nullable="true"/>
       <string minOccurs="1" maxOccurs="unbounded" nullable="true"/>
       <array minOccurs="1" maxOccurs="unbounded" nullable="true">
         ...
       </array>
       <reference minOccurs="1" maxOccurs="unbounded" nullable="true" type="...">
       <any minOccurs="1" maxOccurs="unbounded" nullable="true" types="..."/>
     </array>
     ...
   </schema>
   ```

## 5 Related Resources for JSON Schema

### 5.1 Schemas for JSON Schema

#### 5.1.1 Current

* <ins>JSON Schema 0.2.3</ins> **[Current]**

  * A JSON Schema schema document XSD [schema-0.2.3.xsd](http://www.jsonx.org/schema-0.2.3.xsd) for JSON Schema documents. It incorporates an auxiliary XSD, [datatypes-0.9.2.xsd](http://www.openjax.org/xml/datatypes-0.9.2.xsd).

  * A JSON Schema schema document JSDx [schema-0.2.3.jsdx](http://www.jsonx.org/schema-0.2.3.jsdx) for JSON Schema documents.

  * A JSON Schema schema document JSD [schema-0.2.3.jsd](http://www.jsonx.org/schema-0.2.3.jsd) for JSON Schema documents.

#### 5.1.2 Obsolete

* <ins>JSON Schema 0.2.2</ins> **[Deprecated]**

  * A JSON Schema schema document XSD [schema-0.2.2.xsd](http://www.jsonx.org/schema-0.2.2.xsd) for JSON Schema documents. It incorporates an auxiliary XSD, [datatypes-0.9.2.xsd]( http://www.openjax.org/xml/datatypes-0.9.2.xsd).

  * A JSON Schema schema document JSDx [schema-0.2.2.jsdx](http://www.jsonx.org/schema-0.2.2.jsdx) for JSON Schema documents.

  * A JSON Schema schema document JSD [schema-0.2.2.jsd](http://www.jsonx.org/schema-0.2.2.jsd) for JSON Schema documents.

* <ins>JSON Schema 0.1.0</ins> **[Deprecated]**

  * A JSON Schema schema document XSD [schema-0.1.0.xsd](http://www.jsonx.org/schema-0.1.0.xsd) for JSON Schema documents. It incorporates an auxiliary XSD, [datatypes-0.9.2.xsd](http://www.openjax.org/xml/datatypes-0.9.2.xsd).

  * A JSON Schema schema document JSDx ~~schema-0.1.0.jsdx~~ for JSON Schema documents.

  * A JSON Schema schema document JSD ~~schema-0.1.0.jsd~~ for JSON Schema documents.

### 6 Sample Schemas

This section provides sample schemas in both `jsdx` and `jsd` representations.

#### 6.1 `structure.jsdx`

```xml
<schema
  xmlns="http://www.jsonx.org/schema-0.2.3.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/schema-0.2.3.xsd http://www.jsonx.org/schema-0.2.3.xsd">
  <array name="array">
    <boolean nullable="true"/>
    <number range="[-1,1)" nullable="true"/>
    <string pattern="pattern" nullable="true"/>
    <array nullable="true">
      <boolean nullable="true"/>
      <number range="[-1,1)" nullable="true"/>
      <string pattern="pattern" nullable="true"/>
      <any types="boolean number string array object"/>
    </array>
    <reference type="object"/>
    <any types="boolean number string array object" nullable="true"/>
  </array>
  <boolean name="boolean"/>
  <number name="number" range="[-1,1)"/>
  <string name="string" pattern="pattern"/>
  <object name="object">
    <property name="array" xsi:type="array" nullable="true" use="required">
      <boolean nullable="true"/>
      <number range="[-1,1)" nullable="true"/>
      <string pattern="pattern" nullable="true"/>
      <array nullable="true">
        <boolean nullable="true"/>
        <number range="[-1,1)" nullable="true"/>
        <string pattern="pattern" nullable="true"/>
        <any types="boolean number string array object"/>
      </array>
      <reference type="object"/>
      <any types="boolean number string array object" nullable="true"/>
    </property>
    <property name="boolean" xsi:type="boolean" nullable="true" use="required"/>
    <property name="number" xsi:type="number" range="[-1,1)" nullable="true" use="required"/>
    <property name="string" xsi:type="string" pattern="pattern" nullable="true" use="required"/>
    <property name="booleanRef" xsi:type="reference" type="boolean" nullable="true" use="required"/>
    <property name="subObject" xsi:type="object" extends="object" nullable="true" use="optional">
      <property name="subBoolean" xsi:type="boolean" nullable="true" use="required"/>
      <property name="subNumber" xsi:type="number" range="[-1,1)" nullable="true" use="required"/>
      <property name="subString" xsi:type="string" pattern="pattern" nullable="true" use="required"/>
      <property name="subBooleanRef" xsi:type="reference" type="boolean" nullable="true" use="required"/>
      <property name="subArray" xsi:type="array" nullable="true" use="required">
        <boolean nullable="true"/>
        <number range="[-1,1)" nullable="true"/>
        <string pattern="pattern" nullable="true"/>
        <array nullable="true">
          <boolean nullable="true"/>
          <number range="[-1,1)" nullable="true"/>
          <string pattern="pattern" nullable="true"/>
          <any types="boolean number string array object"/>
        </array>
        <reference type="object"/>
        <any types="boolean number string array object" nullable="true"/>
      </property>
    </property>
    <property names=".*" xsi:type="any" types="boolean number string array object" nullable="true" use="optional"/>
  </object>
</schema>
```

#### 6.2 `structure.jsd`

```json
{
  "jx:ns": "http://www.jsonx.org/schema-0.2.3.jsd",
  "jx:schemaLocation": "http://www.jsonx.org/schema-0.2.3.jsd http://www.jsonx.org/schema-0.2.3.jsd",
  "array": {
    "jx:type": "array",
    "elements": [{
      "jx:type": "boolean"
    }, {
      "jx:type": "number",
      "range": "[-1,1)"
    }, {
      "jx:type": "string",
      "pattern": "pattern"
    }, {
      "jx:type": "array",
      "elements": [{
        "jx:type": "boolean"
      }, {
        "jx:type": "number",
        "range": "[-1,1)"
      }, {
        "jx:type": "string",
        "pattern": "pattern"
      }, {
        "jx:type": "any",
        "types": "boolean number string array object"
      }]
    }, {
      "jx:type": "reference",
      "type": "object"
    }, {
      "jx:type": "any",
      "types": "boolean number string array object"
    }]
  },
  "boolean": {
    "jx:type": "boolean"
  },
  "number": {
    "jx:type": "number",
    "range": "[-1,1)"
  },
  "string": {
    "jx:type": "string",
    "pattern": "pattern"
  },
  "object": {
    "jx:type": "object",
    "properties": {
      "array": {
        "jx:type": "array",
        "elements": [{
          "jx:type": "boolean"
        }, {
          "jx:type": "number",
          "range": "[-1,1)"
        }, {
          "jx:type": "string",
          "pattern": "pattern"
        }, {
          "jx:type": "array",
          "elements": [{
            "jx:type": "boolean"
          }, {
            "jx:type": "number",
            "range": "[-1,1)"
          }, {
            "jx:type": "string",
            "pattern": "pattern"
          }, {
            "jx:type": "any",
            "types": "boolean number string array object"
          }]
        }, {
          "jx:type": "reference",
          "type": "object"
        }, {
          "jx:type": "any",
          "types": "boolean number string array object"
        }]
      },
      "boolean": {
        "jx:type": "boolean"
      },
      "number": {
        "jx:type": "number",
        "range": "[-1,1)"
      },
      "string": {
        "jx:type": "string",
        "pattern": "pattern"
      },
      "booleanRef": {
        "jx:type": "reference",
        "type": "boolean"
      },
      "subObject": {
        "jx:type": "object",
        "extends": "object",
        "use": "optional",
        "properties": {
          "subBoolean": {
            "jx:type": "boolean"
          },
          "subNumber": {
            "jx:type": "number",
            "range": "[-1,1)"
          },
          "subString": {
            "jx:type": "string",
            "pattern": "pattern"
          },
          "subBooleanRef": {
            "jx:type": "reference",
            "type": "boolean"
          },
          "subArray": {
            "jx:type": "array",
            "elements": [{
              "jx:type": "boolean"
            }, {
              "jx:type": "number",
              "range": "[-1,1)"
            }, {
              "jx:type": "string",
              "pattern": "pattern"
            }, {
              "jx:type": "array",
              "elements": [{
                "jx:type": "boolean"
              }, {
                "jx:type": "number",
                "range": "[-1,1)"
              }, {
                "jx:type": "string",
                "pattern": "pattern"
              }, {
                "jx:type": "any",
                "types": "boolean number string array object"
              }]
            }, {
              "jx:type": "reference",
              "type": "object"
            }, {
              "jx:type": "any",
              "types": "boolean number string array object"
            }]
          }
        }
      },
      ".*": {
        "jx:type": "any",
        "types": "boolean number string array object",
        "use": "optional"
      }
    }
  }
}
```

#### 6.3 `datatypes.jsdx`

```xml
<schema
  xmlns="http://www.jsonx.org/schema-0.2.3.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/schema-0.2.3.xsd http://www.jsonx.org/schema-0.2.3.xsd">
  <array name="arrayArr">
    <reference type="arrayBool" maxOccurs="1"/>
    <reference type="arrayNum" maxOccurs="1"/>
    <reference type="arrayObj" maxOccurs="1"/>
    <reference type="arrayObj" maxOccurs="1"/>
    <reference type="arrayStr" maxOccurs="1"/>
    <reference type="arrayStr" maxOccurs="1"/>
    <any types="bool num" minOccurs="0" maxOccurs="1"/>
    <any maxOccurs="1"/>
  </array>
  <array name="arrayBool">
    <reference type="bool" minOccurs="1" maxOccurs="1"/>
    <reference type="bool" minOccurs="0" maxOccurs="1" nullable="false"/>
  </array>
  <array name="arrayNum">
    <reference type="num" minOccurs="1" maxOccurs="1"/>
    <reference type="num" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="numInt" minOccurs="1" maxOccurs="1"/>
    <reference type="numInt" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="numIntRange1" minOccurs="1" maxOccurs="1"/>
    <reference type="numIntRange1" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="numIntRange2" minOccurs="1" maxOccurs="1"/>
    <reference type="numIntRange2" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="numRange1" minOccurs="1" maxOccurs="1"/>
    <reference type="numRange1" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="numRange2" minOccurs="1" maxOccurs="1"/>
    <reference type="numRange2" minOccurs="0" maxOccurs="1" nullable="false"/>
  </array>
  <array name="arrayObj">
    <reference type="objArr" maxOccurs="1"/>
    <reference type="objBool" maxOccurs="1"/>
    <reference type="objNum" maxOccurs="1"/>
    <reference type="objObj" maxOccurs="1"/>
    <reference type="objStr" maxOccurs="1"/>
  </array>
  <array name="arrayStr">
    <reference type="str" minOccurs="1" maxOccurs="1"/>
    <reference type="str" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="strDec" minOccurs="1" maxOccurs="1"/>
    <reference type="strDec" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="strDecEnc" minOccurs="1" maxOccurs="1"/>
    <reference type="strDecEnc" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="strEnc" minOccurs="1" maxOccurs="1"/>
    <reference type="strEnc" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="strPattern" minOccurs="1" maxOccurs="1"/>
    <reference type="strPattern" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="strPatternDec" minOccurs="1" maxOccurs="1"/>
    <reference type="strPatternDec" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="strPatternDecEnc" minOccurs="1" maxOccurs="1"/>
    <reference type="strPatternDecEnc" minOccurs="0" maxOccurs="1" nullable="false"/>
    <reference type="strPatternEnc" minOccurs="1" maxOccurs="1"/>
    <reference type="strPatternEnc" minOccurs="0" maxOccurs="1" nullable="false"/>
  </array>
  <boolean name="bool"/>
  <number name="num"/>
  <number name="numInt" scale="0"/>
  <number name="numIntRange1" scale="0" range="[1,]"/>
  <number name="numIntRange2" scale="0" range="[,4]"/>
  <number name="numRange1" range="[1,]"/>
  <number name="numRange2" range="[,4]"/>
  <string name="str"/>
  <string name="strDec"/>
  <string name="strDecEnc"/>
  <string name="strEnc"/>
  <string name="strPattern" pattern="[a-z]+"/>
  <string name="strPatternDec" pattern="[%0-9a-z]+"/>
  <string name="strPatternDecEnc" pattern="[%0-9a-z]+"/>
  <string name="strPatternEnc" pattern="[%0-9a-z]+"/>
  <object name="objArr">
    <property names=".*" xsi:type="any" nullable="false"/>
    <property name="arrayBool" xsi:type="reference" type="arrayBool"/>
    <property name="arrayBoolOptional" xsi:type="reference" type="arrayBool" use="optional"/>
    <property name="arrayBoolOptionalNotNullable" xsi:type="reference" type="arrayBool" use="optional" nullable="false"/>
    <property name="arrayNum" xsi:type="reference" type="arrayNum"/>
    <property name="arrayNumOptional" xsi:type="reference" type="arrayNum" use="optional"/>
    <property name="arrayNumOptionalNotNullable" xsi:type="reference" type="arrayNum" use="optional" nullable="false"/>
    <property name="arrayStr" xsi:type="reference" type="arrayStr"/>
    <property name="arrayStrOptional" xsi:type="reference" type="arrayStr" use="optional"/>
    <property name="arrayStrOptionalNotNullable" xsi:type="reference" type="arrayStr" use="optional" nullable="false"/>
    <property names="anyNumStr" types="num str" xsi:type="any" use="optional"/>
  </object>
  <object name="objBool">
    <property name="bo+l" xsi:type="reference" type="bool" nullable="false"/>
    <property names=".*" xsi:type="any" types="bool num"/>
    <property name="bo+lOptional" xsi:type="reference" type="bool" use="optional"/>
    <property name="boolOptionalNotNullable" xsi:type="reference" type="bool" use="optional" nullable="false"/>
  </object>
  <object name="objNum">
    <property name="num.+" xsi:type="reference" type="num"/>
    <property name="numOptional" xsi:type="reference" type="num" use="optional"/>
    <property name="numOptionalNotNullable" xsi:type="reference" type="num" use="optional" nullable="false"/>
    <property name="numInt" xsi:type="reference" type="numInt"/>
    <property names="any" xsi:type="any" types="num str" nullable="false"/>
    <property name="numIntOptional" xsi:type="reference" type="numInt" use="optional"/>
    <property name="numIntOptionalNotNullable" xsi:type="reference" type="numInt" use="optional" nullable="false"/>
    <property name="numIntRange1" xsi:type="reference" type="numIntRange1"/>
    <property name="numIntRange1Optional" xsi:type="reference" type="numIntRange1" use="optional"/>
    <property name="numIntRange1OptionalNotNullable" xsi:type="reference" type="numIntRange1" use="optional" nullable="false"/>
    <property name="numIntRange2" xsi:type="reference" type="numIntRange2"/>
    <property name="numIntRange2Optional" xsi:type="reference" type="numIntRange2" use="optional"/>
    <property name="numIntRange2OptionalNotNullable" xsi:type="reference" type="numIntRange2" use="optional" nullable="false"/>
    <property name="numRange1" xsi:type="reference" type="numRange1"/>
    <property name="numRange1Optional" xsi:type="reference" type="numRange1" use="optional"/>
    <property name="numRange1OptionalNotNullable" xsi:type="reference" type="numRange1" use="optional" nullable="false"/>
    <property name="numRange2" xsi:type="reference" type="numRange2"/>
    <property name="numRange2Optional" xsi:type="reference" type="numRange2" use="optional"/>
    <property name="numRange2OptionalNotNullable" xsi:type="reference" type="numRange2" use="optional" nullable="false"/>
  </object>
  <object name="objObj">
    <property name="objOptional" xsi:type="reference" type="objBool" use="optional"/>
    <property name="objOptionalNotNullable" xsi:type="reference" type="objNum" use="optional" nullable="false"/>
    <property name="objExtends1" xsi:type="object" extends="objObj" use="optional">
      <property name="objExtends2" xsi:type="object" extends="objObj" use="optional">
        <property name="objExtends3" xsi:type="object" extends="objObj" use="optional">
          <property name="objExtends4" xsi:type="object" extends="objObj" use="optional">
            <property name="num" xsi:type="reference" type="num" use="optional"/>
          </property>
        </property>
      </property>
    </property>
    <property name="objExtendsOptional" xsi:type="object" extends="objBool" use="optional">
      <property name="num" xsi:type="reference" type="num"/>
    </property>
    <property name="objExtendsOptionalNotNullable" xsi:type="object" extends="objBool" use="optional" nullable="false">
      <property name="num" xsi:type="reference" type="num"/>
    </property>
  </object>
  <object name="objStr">
    <property name="str" xsi:type="reference" type="str"/>
    <property name="strOptional" xsi:type="reference" type="str" use="optional"/>
    <property name="strOptionalNotNullable" xsi:type="reference" type="str" use="optional" nullable="false"/>
    <property name="strDec" xsi:type="reference" type="strDec"/>
    <property name="strDecOptional" xsi:type="reference" type="strDec" use="optional"/>
    <property name="strDecOptionalNotNullable" xsi:type="reference" type="strDec" use="optional" nullable="false"/>
    <property name="strDecEnc" xsi:type="reference" type="strDecEnc"/>
    <property name="strDecEncOptional" xsi:type="reference" type="strDecEnc" use="optional"/>
    <property name="strDecEncOptionalNotNullable" xsi:type="reference" type="strDecEnc" use="optional" nullable="false"/>
    <property name="strEnc" xsi:type="reference" type="strEnc"/>
    <property name="strEncOptional" xsi:type="reference" type="strEnc" use="optional"/>
    <property name="strEncOptionalNotNullable" xsi:type="reference" type="strEnc" use="optional" nullable="false"/>
    <property name="strPattern" xsi:type="reference" type="strPattern"/>
    <property name="strPatternOptional" xsi:type="reference" type="strPattern" use="optional"/>
    <property name="strPatternOptionalNotNullable" xsi:type="reference" type="strPattern" use="optional" nullable="false"/>
    <property name="strPatternDec" xsi:type="reference" type="strPatternDec"/>
    <property name="strPatternDecOptional" xsi:type="reference" type="strPatternDec" use="optional"/>
    <property name="strPatternDecOptionalNotNullable" xsi:type="reference" type="strPatternDec" use="optional" nullable="false"/>
    <property name="strPatternDecEnc" xsi:type="reference" type="strPatternDecEnc"/>
    <property name="strPatternDecEncOptional" xsi:type="reference" type="strPatternDecEnc" use="optional"/>
    <property name="strPatternDecEncOptionalNotNullable" xsi:type="reference" type="strPatternDecEnc" use="optional" nullable="false"/>
    <property name="strPatternEnc" xsi:type="reference" type="strPatternEnc"/>
    <property name="strPatternEncOptional" xsi:type="reference" type="strPatternEnc" use="optional"/>
    <property name="strPatternEncOptionalNotNullable" xsi:type="reference" type="strPatternEnc" use="optional" nullable="false"/>
  </object>
</schema>
```

#### 6.4 `datatypes.jsd`

```json
{
  "jx:ns": "http://www.jsonx.org/schema-0.2.3.jsd",
  "jx:schemaLocation": "http://www.jsonx.org/schema-0.2.3.jsd http://www.jsonx.org/schema-0.2.3.jsd",
  "arrayArr": {
    "jx:type": "array",
    "elements": [{
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "arrayBool"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "arrayNum"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "arrayObj"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "arrayObj"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "arrayStr"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "arrayStr"
    }, {
      "jx:type": "any",
      "maxOccurs": "1",
      "minOccurs": "0",
      "types": "bool num"
    }, {
      "jx:type": "any",
      "maxOccurs": "1"
    }]
  },
  "arrayBool": {
    "jx:type": "array",
    "elements": [{
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "bool"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "bool"
    }]
  },
  "arrayNum": {
    "jx:type": "array",
    "elements": [{
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "num"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "num"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "numInt"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "numInt"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "numIntRange1"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "numIntRange1"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "numIntRange2"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "numIntRange2"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "numRange1"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "numRange1"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "numRange2"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "numRange2"
    }]
  },
  "arrayObj": {
    "jx:type": "array",
    "elements": [{
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "objArr"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "objBool"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "objNum"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "objObj"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "objStr"
    }]
  },
  "arrayStr": {
    "jx:type": "array",
    "elements": [{
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "str"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "str"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "strDec"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "strDec"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "strDecEnc"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "strDecEnc"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "strEnc"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "strEnc"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "strPattern"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "strPattern"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "strPatternDec"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "strPatternDec"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "strPatternDecEnc"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "strPatternDecEnc"
    }, {
      "jx:type": "reference",
      "maxOccurs": "1",
      "type": "strPatternEnc"
    }, {
      "jx:type": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "type": "strPatternEnc"
    }]
  },
  "bool": {
    "jx:type": "boolean"
  },
  "num": {
    "jx:type": "number"
  },
  "numInt": {
    "jx:type": "number",
    "scale": 0
  },
  "numIntRange1": {
    "jx:type": "number",
    "scale": 0,
    "range": "[1,]"
  },
  "numIntRange2": {
    "jx:type": "number",
    "scale": 0,
    "range": "[,4]"
  },
  "numRange1": {
    "jx:type": "number",
    "range": "[1,]"
  },
  "numRange2": {
    "jx:type": "number",
    "range": "[,4]"
  },
  "str": {
    "jx:type": "string"
  },
  "strDec": {
    "jx:type": "string"
  },
  "strDecEnc": {
    "jx:type": "string"
  },
  "strEnc": {
    "jx:type": "string"
  },
  "strPattern": {
    "jx:type": "string",
    "pattern": "[a-z]+"
  },
  "strPatternDec": {
    "jx:type": "string",
    "pattern": "[%0-9a-z]+"
  },
  "strPatternDecEnc": {
    "jx:type": "string",
    "pattern": "[%0-9a-z]+"
  },
  "strPatternEnc": {
    "jx:type": "string",
    "pattern": "[%0-9a-z]+"
  },
  "objArr": {
    "jx:type": "object",
    "properties": {
      ".*": {
        "jx:type": "any",
        "nullable": false
      },
      "arrayBool": {
        "jx:type": "reference",
        "type": "arrayBool"
      },
      "arrayBoolOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "arrayBool"
      },
      "arrayBoolOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "arrayBool"
      },
      "arrayNum": {
        "jx:type": "reference",
        "type": "arrayNum"
      },
      "arrayNumOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "arrayNum"
      },
      "arrayNumOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "arrayNum"
      },
      "arrayStr": {
        "jx:type": "reference",
        "type": "arrayStr"
      },
      "arrayStrOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "arrayStr"
      },
      "arrayStrOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "arrayStr"
      },
      "anyNumStr": {
        "jx:type": "any",
        "types": "num str",
        "use": "optional"
      }
    }
  },
  "objBool": {
    "jx:type": "object",
    "properties": {
      "bo+l": {
        "jx:type": "reference",
        "nullable": false,
        "type": "bool"
      },
      ".*": {
        "jx:type": "any",
        "types": "bool num"
      },
      "bo+lOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "bool"
      },
      "boolOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "bool"
      }
    }
  },
  "objNum": {
    "jx:type": "object",
    "properties": {
      "num.+": {
        "jx:type": "reference",
        "type": "num"
      },
      "numOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "num"
      },
      "numOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "num"
      },
      "numInt": {
        "jx:type": "reference",
        "type": "numInt"
      },
      "any": {
        "jx:type": "any",
        "nullable": false,
        "types": "num str"
      },
      "numIntOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "numInt"
      },
      "numIntOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "numInt"
      },
      "numIntRange1": {
        "jx:type": "reference",
        "type": "numIntRange1"
      },
      "numIntRange1Optional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "numIntRange1"
      },
      "numIntRange1OptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "numIntRange1"
      },
      "numIntRange2": {
        "jx:type": "reference",
        "type": "numIntRange2"
      },
      "numIntRange2Optional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "numIntRange2"
      },
      "numIntRange2OptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "numIntRange2"
      },
      "numRange1": {
        "jx:type": "reference",
        "type": "numRange1"
      },
      "numRange1Optional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "numRange1"
      },
      "numRange1OptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "numRange1"
      },
      "numRange2": {
        "jx:type": "reference",
        "type": "numRange2"
      },
      "numRange2Optional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "numRange2"
      },
      "numRange2OptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "numRange2"
      }
    }
  },
  "objObj": {
    "jx:type": "object",
    "properties": {
      "objOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "objBool"
      },
      "objOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "objNum"
      },
      "objExtends1": {
        "jx:type": "object",
        "extends": "objObj",
        "use": "optional",
        "properties": {
          "objExtends2": {
            "jx:type": "object",
            "extends": "objObj",
            "use": "optional",
            "properties": {
              "objExtends3": {
                "jx:type": "object",
                "extends": "objObj",
                "use": "optional",
                "properties": {
                  "objExtends4": {
                    "jx:type": "object",
                    "extends": "objObj",
                    "use": "optional",
                    "properties": {
                      "num": {
                        "jx:type": "reference",
                        "use": "optional",
                        "type": "num"
                      }
                    }
                  }
                }
              }
            }
          }
        }
      },
      "objExtendsOptional": {
        "jx:type": "object",
        "extends": "objBool",
        "use": "optional",
        "properties": {
          "num": {
            "jx:type": "reference",
            "type": "num"
          }
        }
      },
      "objExtendsOptionalNotNullable": {
        "jx:type": "object",
        "extends": "objBool",
        "nullable": false,
        "use": "optional",
        "properties": {
          "num": {
            "jx:type": "reference",
            "type": "num"
          }
        }
      }
    }
  },
  "objStr": {
    "jx:type": "object",
    "properties": {
      "str": {
        "jx:type": "reference",
        "type": "str"
      },
      "strOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "str"
      },
      "strOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "str"
      },
      "strDec": {
        "jx:type": "reference",
        "type": "strDec"
      },
      "strDecOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "strDec"
      },
      "strDecOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "strDec"
      },
      "strDecEnc": {
        "jx:type": "reference",
        "type": "strDecEnc"
      },
      "strDecEncOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "strDecEnc"
      },
      "strDecEncOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "strDecEnc"
      },
      "strEnc": {
        "jx:type": "reference",
        "type": "strEnc"
      },
      "strEncOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "strEnc"
      },
      "strEncOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "strEnc"
      },
      "strPattern": {
        "jx:type": "reference",
        "type": "strPattern"
      },
      "strPatternOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "strPattern"
      },
      "strPatternOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "strPattern"
      },
      "strPatternDec": {
        "jx:type": "reference",
        "type": "strPatternDec"
      },
      "strPatternDecOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "strPatternDec"
      },
      "strPatternDecOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "strPatternDec"
      },
      "strPatternDecEnc": {
        "jx:type": "reference",
        "type": "strPatternDecEnc"
      },
      "strPatternDecEncOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "strPatternDecEnc"
      },
      "strPatternDecEncOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "strPatternDecEnc"
      },
      "strPatternEnc": {
        "jx:type": "reference",
        "type": "strPatternEnc"
      },
      "strPatternEncOptional": {
        "jx:type": "reference",
        "use": "optional",
        "type": "strPatternEnc"
      },
      "strPatternEncOptionalNotNullable": {
        "jx:type": "reference",
        "use": "optional",
        "nullable": false,
        "type": "strPatternEnc"
      }
    }
  }
}
```

## Contributing

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#schema]: #41-schema-document
[#model]: #42-schema-value-types
[#boolean]: #421-boolean-value-type
[#boolean-usage]: #4211-boolean-usage
[#number]: #422-number-value-type
[#number-usage]: #4221-number-usage
[#string]: #423-string-value-type
[#string-usage]: #4231-string-usage
[#object]: #424-object-value-type
[#property-names]: #4241-object-property-names
[#object-usage]: #4242-object-usage
[#array]: #425-array-value-type
[#array-usage]: #4251-array-usage
[#reference]: #426-reference-value-type
[#reference-usage]: #4261-reference-usage
[#any]: #427-any-value-type
[#any-usage]: #4271-any-usage
[#types]: #43-type-declarations
[#properties]: #44-object-properties
[#elements]: #45-array-elements

[ecma262]: http://www.ecma-international.org/publications/standards/Ecma-262.htm
[interval-notation]: https://en.wikipedia.org/wiki/Interval_(mathematics)#Classification_of_intervals
[rfc4627]: https://www.ietf.org/rfc/rfc4627.txt
[xmlschema]: http://www.w3.org/2001/XMLSchema