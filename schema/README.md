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
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [Model for JSON Values][#model]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1 [`boolean` Model][#boolean]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.2 [`number` Model][#number]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.3 [`string` Model][#string]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.4 [`object` Model][#object]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.4.1 [Property Names][#property-names]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.5 [`array` Model][#array]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.6 [`any` Model][#any]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.7 [`reference` Model][#reference]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.3 [Root Declarative Types][#types]<br>
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

<samp>&nbsp;&nbsp;</samp>4.1 [Schema][#schema]<br>
<samp>&nbsp;&nbsp;</samp>4.2 [Model for JSON Values][#model]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1 [`boolean` Model][#boolean]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.2 [`number` Model][#number]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.3 [`string` Model][#string]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.4 [`object` Model][#object]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.4.1 [Property Names][#property-names]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.5 [`array` Model][#array]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.6 [`any` Model][#any]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.7 [`reference` Model][#reference]<br>
<samp>&nbsp;&nbsp;</samp>4.3 [Root Declarative Types][#types]<br>
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

### 4.2 Model for JSON Values

The <samp>**model**</samp> objects define the constraint properties of the five JSON value types: <samp>**boolean**</samp>, <samp>**number**</samp>, <samp>**string**</samp>, <samp>**object**</samp>, and <samp>**array**</samp>. The <ins>JSON Schema Definition Language</ins> defines two additional meta value types named <samp>**any**</samp> and <samp>**reference**</samp>.

Each <samp>**model**</samp> object supports the `doc` attribute.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **boolean** \|&nbsp;</samp><br><samp>&nbsp;&nbsp;**number** \|</samp><br><samp>&nbsp;&nbsp;**string** \|</samp><br><samp>&nbsp;&nbsp;**object** \|</samp><br><samp>&nbsp;&nbsp;**array** \|</samp><br><samp>&nbsp;&nbsp;any \|</samp><br><samp>&nbsp;&nbsp;reference )</samp> | <samp>doc</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | Text comments. Optional.<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; |

1. <ins>Example</ins>: `jsd`
   ```json
   { "doc": "Comment for this element",
     ...
   }
   ```

1. <ins>Example</ins>: `jsdx`
   ```xml
   <any doc="Comment for this element">
      ...
   </any>
   ```

#### 4.2.1 `boolean` Model

The <samp>**boolean**</samp> model is the only model that lacks validation constraints.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **boolean** )</samp> | <samp>jx:type</samp> | <samp>boolean</samp> |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jx:type": "boolean" }
   ```

1. <ins>Example</ins>: `jsdx` <samp>(type declarations and array members)</samp>
   ```xml
   <boolean/>
   ```

1. <ins>Example</ins>: `jsdx` <samp>(object properties)</samp>
   ```xml
   <property xsi:type="boolean"/>
   ```

#### 4.2.2 `number` Model

The <samp>**number**</samp> model defines two validation constraints for <samp>**number**</samp> JSON value types: <samp>scale</samp>, and <samp>range</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **number** )</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | <samp>jx:type</samp><br><samp>scale</samp><br>&nbsp;<br>&nbsp;<br><samp>range</samp><br>&nbsp;<br>&nbsp; | <samp>number</samp><br><samp>(0\|1\|2\|...)</samp><br>&nbsp;&nbsp;The number of digits to the right of the decimal point.<br>&nbsp;&nbsp;**If a value is not specified, the scale is unbounded.**<br>_Numerical range_<br>&nbsp;&nbsp;Specifies the minimum and maximum limits in [interval<br>notation][interval-notation]. |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jx:type": "number", "scale": 0, "range": "[-1,1)" }
   ```

1. <ins>Example</ins>: `jsdx` <samp>(type declarations and array members)</samp>
   ```xml
   <number range="[-1,1)"/>
   ```

1. <ins>Example</ins>: `jsdx` <samp>(object properties)</samp>
   ```xml
   <property xsi:type="number" range="[-1,1)"/>
   ```

#### 4.2.3 `string` Model

The <samp>**string**</samp> model defines one validation constraint for <samp>**string**</samp> JSON value types: <samp>pattern</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **string** )</samp><br>&nbsp;<br>&nbsp; | <samp>jx:type</samp><br><samp>pattern</samp><br>&nbsp; | <samp>string</samp><br>_Regular expression_<br>&nbsp;&nbsp;Specifies the regular expression. |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jx:type": "string", "pattern": "pattern" }
   ```

1. <ins>Example</ins>: `jsdx` <samp>(type declarations and array members)</samp>
   ```xml
   <string pattern="pattern"/>
   ```

1. <ins>Example</ins>: `jsdx` <samp>(object properties)</samp>
   ```xml
   <property xsi:type="string" pattern="pattern"/>
   ```

#### 4.2.4 `object` Model

The <samp>**object**</samp> model defines three validation constraints for <samp>**object**</samp> JSON value types: <samp>abstract</samp>, <samp>extends</samp>, and named <samp>properties</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <a name="objecttype"><samp>( **object** )</samp></a><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | <samp>jx:type</samp><br><samp>abstract</samp><br>&nbsp;<br><samp>extends</samp><br>&nbsp;<br>&nbsp;<br><samp>.*</samp><br>&nbsp; | <samp>object</samp><br><samp>(true\|**false**)</samp><br>&nbsp;&nbsp;Whether the object is not allowed to be instantiated.<br>_Name [<samp>( **object** )</samp> type](#objecttype)_<br>&nbsp;&nbsp;Name of root-level object type declaration specifying<br>&nbsp;&nbsp;object inheritence.<br>_[Property declaration][#properties]_<br>&nbsp;&nbsp;Declaration of object property. |


##### 4.2.4.1 Property Names

Names of object properties are considered as regular expressions. If an object declaration defines a property with the name <samp>"[a-z]+"</samp>, it means that this name matches any property whose name is one or more alpha characters. This also means that the name <samp>"foo"</samp> will only match "foo". If multiple defined property name patterns capture the same name, the associated value will be validated against the top-most property definition that matched the name. The example below shows an <samp>**any**</samp> property that matches all names (except those that are defined above the <samp>**any**</samp> property definition).

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jx:type": "object",
     "properties": {
       "propArray": { "jx:type": "array",
         "elements": [...] },
       "propBoolean": { "jx:type": "boolean" },
       "propNumber": { "jx:type": "number" },
       "propString": { "jx:type": "string" },
       "propObject": { "jx:type": "object",
         "properties": {...} },
       "propReference": { "jx:type": "reference", "type": "..." },
       ".*": { "jx:type": "any", "types": "..." }
     }
   }
   ```

1. <ins>Example</ins>: `jsdx` <samp>(type declarations and ~~array members~~)</samp>
   ```xml
   <object>
     <property name="propArray" xsi:type="array">
       ...
     </property>
     <property name="propBoolean" xsi:type="boolean"/>
     <property name="propNumber" xsi:type="number"/>
     <property name="propString" xsi:type="string"/>
     <property name="propObject" xsi:type="object">
       ...
     </property>
     <property name="propReference" xsi:type="reference" type="..."/>
     <property names=".*" xsi:type="any" types="..."/>
   </object>
   ```

1. <ins>Example</ins>: `jsdx` <samp>(object properties)</samp>
   ```xml
   <property xsi:type="object">
     <property name="propArray" xsi:type="array">
       ...
     </property>
     <property name="propBoolean" xsi:type="boolean"/>
     <property name="propNumber" xsi:type="number"/>
     <property name="propString" xsi:type="string"/>
     <property name="propObject" xsi:type="object">
       ...
     </property>
     <property name="propReference" xsi:type="reference" type="..."/>
     <property names=".*" xsi:type="any" types="..."/>
   </object>
   ```

#### 4.2.5 `array` Model

The <samp>**array**</samp> model defines three validation constraints for <samp>**array**</samp> JSON value types: <samp>minIterate</samp>, <samp>maxIterate</samp>, and member <samp>elements</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <a name="arraytype"><samp>( **array** )</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;  | <samp>jx:type</samp><br><samp>minIterate</samp><br>&nbsp;<br>&nbsp;<br><samp>maxIterate</samp><br>&nbsp;<br>&nbsp;<br><samp>elements</samp><br>&nbsp; | <samp>array</samp><br><samp>(**1**\|2\|...)</samp><br>&nbsp;&nbsp;Specifies the minimum inclusive number of iterations of<br>&nbsp;&nbsp;child members.<br><samp>(**1**\|2\|...\|unbounded)</samp><br>&nbsp;&nbsp;Specifies the maximum inclusive number of iterations of<br>&nbsp;&nbsp;child members.<br><samp>\[</samp> [Element declaration][#elements]<samp> , ...\]</samp><br>&nbsp;&nbsp;Array of member element declarations. |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jx:type": "array",
     "elements": [
       { "jx:type": "array",
         "elements": [...] },
       { "jx:type": "boolean" },
       { "jx:type": "number" },
       { "jx:type": "string" },
       { "jx:type": "reference", "type": "..." },
       { "jx:type": "any", "types": "..." }
     ]
   }
   ```

1. <ins>Example</ins>: `jsdx` <samp>(type declarations and array members)</samp>
   ```xml
   <array>
     <boolean/>
     <number/>
     <string/>
     <array>
       ...
     </array>
     <reference type="...">
     <any types="..."/>
   </array>
   ```

1. <ins>Example</ins>: `jsdx` <samp>(object properties)</samp>
   ```xml
   <property xsi:type="array">
     <boolean/>
     <number/>
     <string/>
     <array>
       ...
     </array>
     <reference type="...">
     <any types="..."/>
   </array>
   ```

#### 4.2.6 `any` Model

The <samp>**any**</samp> model defines one validation constraint: <samp>types</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **any** )</samp><br>&nbsp;<br>&nbsp; | <samp>jx:type</samp><br><samp>types</samp><br>&nbsp; | <samp>any</samp><br><samp>\[</samp> Name of [type declaration][#elements]<samp> , ...\]</samp><br>&nbsp;&nbsp;Array of type declarations. |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jx:type": "any", "types": "..." }
   ```

1. <ins>Example</ins>: `jsdx` <samp>(type declarations and array members)</samp>
   ```xml
   <any types="..."/>
   ```

1. <ins>Example</ins>: `jsdx` <samp>(object properties)</samp>
   ```xml
   <property xsi:type="any" types="..."/>
   ```

#### 4.2.7 `reference` Model

The <samp>**reference**</samp> model defines one validation constraint: <samp>type</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **reference** )</samp><br>&nbsp;<br>&nbsp; | <samp>jx:type</samp><br><samp>type</samp><br>&nbsp; | <samp>reference</samp><br>_Name of [type declaration][#types]_<br>&nbsp;&nbsp;Name of root-level type declaration to reference. |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jx:type": "reference", "type": "..." }
   ```

1. <ins>Example</ins>: `jsdx` <samp>(type declarations and array members)</samp>
   ```xml
   <reference type="..."/>
   ```

1. <ins>Example</ins>: `jsdx` <samp>(object properties)</samp>
   ```xml
   <property xsi:type="reference" type="..."/>
   ```

### 4.3 Root Declarative Types

The <samp>**type**</samp> objects are immediate children of the <samp>[**schema**][#schema]</samp> object, and represent type definitions that are referenceable throughout the schema, via `any.types`, `object.extends`, `array.elements.reference`, and `object.properties.reference`. The <samp>**type**</samp> objects inherit constraint properties from <samp>[**model**][#model]</samp> definitions with the following extensions: (Note that the <samp>**any**</samp> and <samp>**reference**</samp> models are not available as a declarative <samp>**type**s</samp>).

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

The <samp>**element**</samp> objects define properties for the declarative objects that belong to an <samp>**[array](#arraytype)**</samp>. The <samp>**element**</samp> objects inherit constraint properties from <samp>[**model**][#model]</samp> definitions with the following extensions:

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

* <ins>JSON Schema 0.2.3</ins> **[Deprecated]**

  * A JSON Schema schema document XSD [schema-0.2.3.xsd](http://www.jsonx.org/schema-0.2.3.xsd) for JSON Schema documents. It incorporates an auxiliary XSD, [datatypes-0.9.2.xsd]( http://www.openjax.org/xml/datatypes-0.9.2.xsd).

  * A JSON Schema schema document JSDx [schema-0.2.3.jsdx](http://www.jsonx.org/schema-0.2.3.jsdx) for JSON Schema documents.

  * A JSON Schema schema document JSD [schema-0.2.3.jsd](http://www.jsonx.org/schema-0.2.3.jsd) for JSON Schema documents.

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
[#model]: #42-model-for-json-values
[#boolean]: #421-boolean-model
[#number]: #422-number-model
[#string]: #423-string-model
[#object]: #424-object-model
[#property-names]: #4241-property-names
[#array]: #425-array-model
[#any]: #426-any-model
[#reference]: #427-reference-model
[#types]: #43-root-declarative-types
[#properties]: #44-object-properties
[#elements]: #45-array-elements

[interval-notation]: https://en.wikipedia.org/wiki/Interval_(mathematics#Including_or_excluding_endpoints)
[rfc4627]: https://www.ietf.org/rfc/rfc4627.txt
[xmlschema]: http://www.w3.org/2001/XMLSchema