# JSON Schema

[![Released Version](https://img.shields.io/maven-central/v/org.openjax.jsonx/schema.svg)](https://mvnrepository.com/artifact/org.openjax.jsonx/schema)

## Abstract

This document specifies the <ins>JSON Schema Definition Language</ins>, which offers facilities for describing the structure and constraining the contents of JSON documents. The schema language, which is itself represented in an JSON vocabulary, extends the capabilities found in JSON documents.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Dependencies on Other Specifications][#dependencies]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.2 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [Purpose][#purpose]<br>
<samp>&nbsp;&nbsp;</samp>3 [Requirements][#requirements]<br>
<samp>&nbsp;&nbsp;</samp>4 [Specification][#specification]<br>
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
<samp>&nbsp;&nbsp;</samp>5 [Related Resources for JSON Schema][#resources]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1 [Schemas for JSON Schema][#json-schemas]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1.1 [JSON Schema 0.9.8][#schema-098]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1.2 [JSON Schema 0.9.7][#schema-097]<br>
<samp>&nbsp;&nbsp;</samp>6 [Sample Schemas][#samples]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.1 [`structure.jsdx`][#structurejsdx]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.2 [`structure.jsd`][#structurejsd]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.3 [`datatype.jsdx`][#datatypejsdx]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>6.4 [`datatype.jsd`][#datatypejsd]

## 1 Introduction

This document sets out the structural part of the <ins>JSON Schema Definition Language</ins>. It also contains a directory of links to these related resources.

The <ins>JSON Schema Definition Language</ins> is designed to describe JSON documents by using schema components to constrain and document the meaning, usage and relationships of their constituent parts: value types and their content. Schemas may also provide for the specification of additional document information, such as normalization and defaulting of values. Schemas have facilities for self-documentation. Thus, the <ins>JSON Schema Definition Language</ins> can be used to define, describe and catalogue JSON vocabularies for JSON documents.

Any application that consumes well-formed JSON can use the <ins>JSON Schema Definition Language</ins> formalism to express syntactic, structural and value constraints applicable to its document instances. The <ins>JSON Schema Definition Language</ins> formalism allows a useful level of constraint checking to be described and implemented for a wide spectrum of JSON applications. However, the language defined by this specification does not attempt to provide _all_ the facilities that might be needed by any application. Some applications may require constraint capabilities not expressible in this language, and so may need to perform their own additional validations.

### 1.1 Dependencies on Other Specifications

The definition of the <ins>JSON Schema Definition Language</ins> depends on the following specifications: [\[RFC4627\]][rfc4627] and [\[XMLSchema\]][xmlschema].

### 1.2 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Purpose

Provide a <ins>schema language</ins> to describe normative contracts between producer and consumer ends of a protocol exchanging JSON documents.

## 3 Requirements

1. The <ins>schema language</ins> MUST constrain and document the meaning, usage, constraints and relationships of the constituent parts of a JSON document.

1. The <ins>schema language</ins> MUST provide meaningful and useful constraint rules for the 5 JSON value types: `boolean`, `number`, `string`, `object`, and `array`.

1. The <ins>schema language</ins> MUST support schema descriptions for any and all legal JSON documents, as specified by [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

1. The <ins>schema language</ins> MUST be free-of and agnostic-to patterns specific to any particular programming language.

1. The <ins>schema language</ins> MUST be able to describe itself.

## 4 Specification

The <ins>JSON Schema Definition Language</ins> (JSD) is normatively defined in an <ins>XML Schema Document</ins>, with translations expressed in the <ins>JSON/XML Schema Definition Language</ins> (JSDX), as well as the <ins>JSON Schema Definition Language</ins> (JSD) itself.

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
| <samp>( **schema** )</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | <samp>jsd:ns</samp><br>&nbsp;<br>&nbsp;<br><samp>[a-zA-Z_$][-a-zA-Z\\d_$]*</samp><br>&nbsp;<br>&nbsp; | _Namespace of the JSON Schema_<br>&nbsp;&nbsp;Used by schema processors to determine to which<br>&nbsp;&nbsp;version of the JSON Schema the JSD is written.<br>_[Type Declaration][#types]_<br>&nbsp;&nbsp;Root object definitions that are referenceable<br>&nbsp;&nbsp;throughout the schema. |

1. <ins>Example</ins>: `jsd`
   ```json
   { "jsd:ns": "http://jsonx.openjax.org/schema-0.9.8.jsd",
     ...
   }
   ```

1. <ins>Example</ins>: `jsdx`
   ```xml
   <schema xmlns="http://jsonx.openjax.org/schema-0.9.8.xsd">
     ...
   </schema>
   ```

### 4.2 Model for JSON Values

The <samp>**model**</samp> objects define the constraint properties of the five JSON value classes: <samp>**boolean**</samp>, <samp>**number**</samp>, <samp>**string**</samp>, <samp>**object**</samp>, and <samp>**array**</samp>. The <ins>JSON Schema Definition Language</ins> defines two additional meta value classes named <samp>**any**</samp> and <samp>**reference**</samp>.

#### 4.2.1 `boolean` Model

The <samp>**boolean**</samp> model is the only model that lacks validation constraints.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **boolean** )</samp> | <samp>jsd:class</samp> | <samp>boolean</samp> |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jsd:class": "boolean" }
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

The <samp>**number**</samp> model defines two validation constraints for <samp>**number**</samp> JSON value types: <samp>form</samp>, and <samp>range</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **number** )</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | <samp>jsd:class</samp><br><samp>jsd:form</samp><br>&nbsp;<br><samp>jsd:range</samp><br>&nbsp;<br>&nbsp; | <samp>number</samp><br><samp>(integer\|**real**)</samp><br>&nbsp;&nbsp;Specifies whether a decimal character is allowed. <br>_Numerical range_<br>&nbsp;&nbsp;Specifies the minimum and maximum limits in [interval<br>notation][interval-notation]. |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jsd:class": "number", "form": "real", "range": "[-1,1)" }
   ```

1. <ins>Example</ins>: `jsdx` <samp>(type declarations and array members)</samp>
   ```xml
   <number form="real" range="[-1,1)"/>
   ```

1. <ins>Example</ins>: `jsdx` <samp>(object properties)</samp>
   ```xml
   <property xsi:type="number" form="real" range="[-1,1)"/>
   ```

#### 4.2.3 `string` Model

The <samp>**string**</samp> model defines one validation constraint for <samp>**string**</samp> JSON value types: <samp>pattern</samp>.

| <samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Name**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> | **Value**<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp> |
|:-|:-|:-|
| <samp>( **string** )</samp><br>&nbsp;<br>&nbsp; | <samp>jsd:class</samp><br><samp>jsd:pattern</samp><br>&nbsp; | <samp>string</samp><br>_Regular expression_<br>&nbsp;&nbsp;Specifies the regular expression. |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jsd:class": "string", "pattern": "pattern" }
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
| <a name="objecttype"><samp>( **object** )</samp></a><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | <samp>jsd:class</samp><br><samp>jsd:abstract</samp><br>&nbsp;<br><samp>jsd:extends</samp><br>&nbsp;<br>&nbsp;<br><samp>.*</samp><br>&nbsp; | <samp>object</samp><br><samp>(true\|**false**)</samp><br>&nbsp;&nbsp;Whether the object is not allowed to be instantiated.<br>_Name [<samp>( **object** )</samp> type](#objecttype)_<br>&nbsp;&nbsp;Name of root-level object type declaration specifying<br>&nbsp;&nbsp;object inheritence.<br>_[Property declaration][#properties]_<br>&nbsp;&nbsp;Declaration of object property. |


##### 4.2.4.1 Property Names

Names of object properties are considered as regular expressions. If an object declaration defines a property with the name <samp>"[a-z]+"</samp>, it means that this name matches any property whose name is one or more alpha characters. This also means that the name <samp>"foo"</samp> will only match "foo". If multiple defined property name patterns capture the same name, the associated value will be validated against the top-most property definition that matched the name. The example below shows an <samp>**any**</samp> property that matches all names (except those that are defined above the <samp>**any**</samp> property definition).

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jsd:class": "object",
     "jsd:properties": {
       "propArray": { "jsd:class": "array",
         "jsd:elements": [...] },
       "propBoolean": { "jsd:class": "boolean" },
       "propNumber": { "jsd:class": "number" },
       "propString": { "jsd:class": "string" },
       "propObject": { "jsd:class": "object",
         "jsd:properties": {...} },
       "propReference": { "jsd:class": "reference", "jsd:type": "..." },
       ".*": { "jsd:class": "any", "jsd:types": "..." }
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
| <a name="arraytype"><samp>( **array** )</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;  | <samp>jsd:class</samp><br><samp>jsd:minIterate</samp><br>&nbsp;<br>&nbsp;<br><samp>jsd:maxIterate</samp><br>&nbsp;<br>&nbsp;<br><samp>jsd:elements</samp><br>&nbsp; | <samp>array</samp><br><samp>(**1**\|2\|...)</samp><br>&nbsp;&nbsp;Specifies the minimum inclusive number of iterations of<br>&nbsp;&nbsp;child members.<br><samp>(**1**\|2\|...\|unbounded)</samp><br>&nbsp;&nbsp;Specifies the maximum inclusive number of iterations of<br>&nbsp;&nbsp;child members.<br><samp>\[</samp> [Element declaration][#elements]<samp> , ...\]</samp><br>&nbsp;&nbsp;Array of member element declarations. |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jsd:class": "array",
     "jsd:elements": [
       { "jsd:class": "array",
         "jsd:elements": [...] },
       { "jsd:class": "boolean" },
       { "jsd:class": "number" },
       { "jsd:class": "string" },
       { "jsd:class": "reference", "jsd:type": "..." },
       { "jsd:class": "any", "jsd:types": "..." }
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
| <samp>( **any** )</samp><br>&nbsp;<br>&nbsp; | <samp>jsd:class</samp><br><samp>jsd:types</samp><br>&nbsp; | <samp>any</samp><br><samp>\[</samp> Name of [type declaration][#elements]<samp> , ...\]</samp><br>&nbsp;&nbsp;Array of type declarations. |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jsd:class": "any", "jsd:types": "..." }
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
| <samp>( **reference** )</samp><br>&nbsp;<br>&nbsp; | <samp>jsd:class</samp><br><samp>jsd:type</samp><br>&nbsp; | <samp>reference</samp><br>_Name of [type declaration][#types]_<br>&nbsp;&nbsp;Name of root-level type declaration to reference. |

1. <ins>Example</ins>: `jsd` <samp>(object properties and array members)</samp>
   ```json
   { "jsd:class": "reference", "jsd:type": "..." }
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
| <samp>( **boolean** \|&nbsp;&nbsp;</samp><br><samp>&nbsp;&nbsp;**number** \|</samp><br><samp>&nbsp;&nbsp;**string** \|</samp><br><samp>&nbsp;&nbsp;**object** \|</samp><br><samp>&nbsp;&nbsp;**array** \|</samp><br><samp>&nbsp;&nbsp;~~any~~ \|</samp><br><samp>&nbsp;&nbsp;~~reference~~ )</samp> | <samp>jsd:name</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | _Name of declared type_<br>&nbsp;&nbsp;Name of type declaration to be used as reference<br>&nbsp;&nbsp;throuthout the JSD.<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; |

1. <ins>Example</ins>: `jsd`
   ```json
   { "jsd:ns": "http://jsonx.openjax.org/schema-0.9.8.jsd",
     ...
     "rootArray": { "jsd:class": "array",
       "jsd:elements": [...] },
     "rootBoolean": { "jsd:class": "boolean" },
     "rootNumber": { "jsd:class": "number" },
     "rootString": { "jsd:class": "string" },
     "rootObject": { "jsd:class": "object",
       "jsd:properties": {...} }
     ...
   }
   ```

1. <ins>Example</ins>: `jsdx`
   ```xml
   <schema xmlns="http://jsonx.openjax.org/schema-0.9.8.xsd">
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
| <samp>( **boolean** \|&nbsp;</samp><br><samp>&nbsp;&nbsp;**number** \|</samp><br><samp>&nbsp;&nbsp;**string** \|</samp><br><samp>&nbsp;&nbsp;**object** \|</samp><br><samp>&nbsp;&nbsp;**array** \|</samp><br><samp>&nbsp;&nbsp;any \|</samp><br><samp>&nbsp;&nbsp;reference )</samp> | <samp>jsd:use</samp><br>&nbsp;<br><samp>jsd:nullable</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | <samp>(**required**\|optional)</samp><br>&nbsp;&nbsp;Specifies whether the property use is required or optional.<br><samp>(**true**\|false)</samp><br>&nbsp;&nbsp;Specifies whether the property is nullable.<br>&nbsp;<br>&nbsp;<br>&nbsp; |

1. <ins>Example</ins>: `jsd`
   ```json
   { "jsd:ns": "http://jsonx.openjax.org/schema-0.9.8.jsd",
     ...
     "rootObject": { "jsd:class": "object",
       "jsd:properties": {
         "propArray": { "jsd:class": "array", "jsd:nullable": true, "jsd:use": "required",
           "jsd:elements": [...] },
         "propBoolean": { "jsd:class": "boolean", "jsd:nullable": true, "jsd:use": "required" },
         "propNumber": { "jsd:class": "number", "jsd:nullable": true, "jsd:use": "required" },
         "propString": { "jsd:class": "string", "jsd:nullable": true, "jsd:use": "required" },
         "propObject": { "jsd:class": "object", "jsd:nullable": true, "jsd:use": "optional",
           "jsd:properties": {...} },
         "propReference": { "jsd:class": "reference", "jsd:nullable": true, "jsd:use": "required", "jsd:type": "..." },
         ".*": { "jsd:class": "any", "jsd:nullable": true, "jsd:use": "optional", "jsd:types": "..." }
       }
     }
     ...
   }
   ```

1. <ins>Example</ins>: `jsdx`
   ```xml
   <schema xmlns="http://jsonx.openjax.org/schema-0.9.8.xsd">
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
| <samp>( **boolean** \|&nbsp;</samp><br><samp>&nbsp;&nbsp;**number** \|</samp><br><samp>&nbsp;&nbsp;**string** \|</samp><br><samp>&nbsp;&nbsp;**object** \|</samp><br><samp>&nbsp;&nbsp;**array** \|</samp><br><samp>&nbsp;&nbsp;any \|</samp><br><samp>&nbsp;&nbsp;reference )</samp><br>&nbsp; | <samp>jsd:nullable</samp><br>&nbsp;<br><samp>jsd:minOccurs</samp><br>&nbsp;<br>&nbsp;<br><samp>jsd:maxOccurs</samp><br>&nbsp;<br>&nbsp; | <samp>(**true**\|false)</samp><br>&nbsp;&nbsp;Specifies whether the property is nullable.<br><samp>(0\|**1**\|2\|...)</samp><br>&nbsp;&nbsp;Specifies the minimum inclusive number of occurrence of<br>&nbsp;&nbsp;the member element.<br><samp>(0\|1\|2\|...\|**unbounded**)</samp><br>&nbsp;&nbsp;Specifies the maximum inclusive number of occurrence of<br>&nbsp;&nbsp;the member element. |

1. <ins>Example</ins>: `jsd`
   ```json
   { "jsd:ns": "http://jsonx.openjax.org/schema-0.9.8.jsd",
     ...
     "rootArray": {
       "jsd:class": "array",
       "jsd:elements": [
         { "jsd:class": "boolean", "jsd:minOccurs": "1", "jsd:maxOccurs": "unbounded", "jsd:nullable": true},
         { "jsd:class": "number", "jsd:minOccurs": "1", "jsd:maxOccurs": "unbounded", "jsd:nullable": true },
         { "jsd:class": "string", "jsd:minOccurs": "1", "jsd:maxOccurs": "unbounded", "jsd:nullable": true },
         { "jsd:class": "array", "jsd:minOccurs": "1", "jsd:maxOccurs": "unbounded", "jsd:nullable": true,
           "jsd:elements": [...] },
         { "jsd:class": "reference", "jsd:minOccurs": "1", "jsd:maxOccurs": "unbounded", "jsd:nullable": true, "jsd:type": "..." },
         { "jsd:class": "any", "jsd:minOccurs": "1", "jsd:maxOccurs": "unbounded", "jsd:nullable": true, "jsd:types": "..." }
       ]
     }
     ...
   }
   ```

1. <ins>Example</ins>: `jsdx`
   ```xml
   <schema xmlns="http://jsonx.openjax.org/schema-0.9.8.xsd">
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

#### 5.1.1 JSON Schema 0.9.8

* A JSON Schema schema document XSD [schema-0.9.8.xsd](http://jsonx.openjax.org/schema-0.9.8.xsd) for JSON Schema documents. It incorporates an auxiliary XSD, [datatypes-0.9.2.xsd](http://standard.openjax.org/xml/datatypes-0.9.2.xsd).

* A JSON Schema schema document JSDX [schema-0.9.8.jsdx](http://jsonx.openjax.org/schema-0.9.8.jsdx) for JSON Schema documents.

* A JSON Schema schema document JSD [schema-0.9.8.jsd](http://jsonx.openjax.org/schema-0.9.8.jsd) for JSON Schema documents.

#### 5.1.2 JSON Schema 0.9.7

* A JSON Schema schema document XSD [schema-0.9.7.xsd](http://jsonx.openjax.org/schema-0.9.7.xsd) for JSON Schema documents. It incorporates an auxiliary XSD, [datatypes-0.9.2.xsd](http://standard.openjax.org/xml/datatypes-0.9.2.xsd).

### 6 Sample Schemas

This section provides sample schemas in both `jsdx` and `jsd` forms.

#### 6.1 `structure.jsdx`

```xml
<schema
  xmlns="http://jsonx.openjax.org/schema-0.9.8.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://jsonx.openjax.org/schema-0.9.8.xsd http://jsonx.openjax.org/schema-0.9.8.xsd">
  <array name="array">
    <boolean nullable="true"/>
    <number form="real" range="[-1,1)" nullable="true"/>
    <string pattern="pattern" nullable="true"/>
    <array nullable="true">
      <boolean nullable="true"/>
      <number form="real" range="[-1,1)" nullable="true"/>
      <string pattern="pattern" nullable="true"/>
      <any types="boolean number string array object"/>
    </array>
    <reference type="object"/>
    <any types="boolean number string array object" nullable="true"/>
  </array>
  <boolean name="boolean"/>
  <number name="number" form="real" range="[-1,1)"/>
  <string name="string" pattern="pattern"/>
  <object name="object">
    <property name="array" xsi:type="array" nullable="true" use="required">
      <boolean nullable="true"/>
      <number form="real" range="[-1,1)" nullable="true"/>
      <string pattern="pattern" nullable="true"/>
      <array nullable="true">
        <boolean nullable="true"/>
        <number form="real" range="[-1,1)" nullable="true"/>
        <string pattern="pattern" nullable="true"/>
        <any types="boolean number string array object"/>
      </array>
      <reference type="object"/>
      <any types="boolean number string array object" nullable="true"/>
    </property>
    <property name="boolean" xsi:type="boolean" nullable="true" use="required"/>
    <property name="number" xsi:type="number" form="real" range="[-1,1)" nullable="true" use="required"/>
    <property name="string" xsi:type="string" pattern="pattern" nullable="true" use="required"/>
    <property name="booleanRef" xsi:type="reference" type="boolean" nullable="true" use="required"/>
    <property name="subObject" xsi:type="object" extends="object" nullable="true" use="optional">
      <property name="subBoolean" xsi:type="boolean" nullable="true" use="required"/>
      <property name="subNumber" xsi:type="number" form="real" range="[-1,1)" nullable="true" use="required"/>
      <property name="subString" xsi:type="string" pattern="pattern" nullable="true" use="required"/>
      <property name="subBooleanRef" xsi:type="reference" type="boolean" nullable="true" use="required"/>
      <property name="subArray" xsi:type="array" nullable="true" use="required">
        <boolean nullable="true"/>
        <number form="real" range="[-1,1)" nullable="true"/>
        <string pattern="pattern" nullable="true"/>
        <array nullable="true">
          <boolean nullable="true"/>
          <number form="real" range="[-1,1)" nullable="true"/>
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
  "jsd:ns": "http://jsonx.openjax.org/schema-0.9.8.jsd",
  "jsd:schemaLocation": "http://jsonx.openjax.org/schema-0.9.8.jsd http://jsonx.openjax.org/schema-0.9.8.jsd",
  "array": {
    "jsd:class": "array",
    "jsd:elements": [{
      "jsd:class": "boolean"
    }, {
      "jsd:class": "number",
      "jsd:range": "[-1,1)"
    }, {
      "jsd:class": "string",
      "jsd:pattern": "pattern"
    }, {
      "jsd:class": "array",
      "jsd:elements": [{
        "jsd:class": "boolean"
      }, {
        "jsd:class": "number",
        "jsd:range": "[-1,1)"
      }, {
        "jsd:class": "string",
        "jsd:pattern": "pattern"
      }, {
        "jsd:class": "any",
        "jsd:types": "boolean number string array object"
      }]
    }, {
      "jsd:class": "reference",
      "jsd:type": "object"
    }, {
      "jsd:class": "any",
      "jsd:types": "boolean number string array object"
    }]
  },
  "boolean": {
    "jsd:class": "boolean"
  },
  "number": {
    "jsd:class": "number",
    "jsd:range": "[-1,1)"
  },
  "string": {
    "jsd:class": "string",
    "jsd:pattern": "pattern"
  },
  "object": {
    "jsd:class": "object",
    "jsd:properties": {
      "array": {
        "jsd:class": "array",
        "jsd:elements": [{
          "jsd:class": "boolean"
        }, {
          "jsd:class": "number",
          "jsd:range": "[-1,1)"
        }, {
          "jsd:class": "string",
          "jsd:pattern": "pattern"
        }, {
          "jsd:class": "array",
          "jsd:elements": [{
            "jsd:class": "boolean"
          }, {
            "jsd:class": "number",
            "jsd:range": "[-1,1)"
          }, {
            "jsd:class": "string",
            "jsd:pattern": "pattern"
          }, {
            "jsd:class": "any",
            "jsd:types": "boolean number string array object"
          }]
        }, {
          "jsd:class": "reference",
          "jsd:type": "object"
        }, {
          "jsd:class": "any",
          "jsd:types": "boolean number string array object"
        }]
      },
      "boolean": {
        "jsd:class": "boolean"
      },
      "number": {
        "jsd:class": "number",
        "jsd:range": "[-1,1)"
      },
      "string": {
        "jsd:class": "string",
        "jsd:pattern": "pattern"
      },
      "booleanRef": {
        "jsd:class": "reference",
        "jsd:type": "boolean"
      },
      "subObject": {
        "jsd:class": "object",
        "jsd:extends": "object",
        "jsd:use": "optional",
        "jsd:properties": {
          "subBoolean": {
            "jsd:class": "boolean"
          },
          "subNumber": {
            "jsd:class": "number",
            "jsd:range": "[-1,1)"
          },
          "subString": {
            "jsd:class": "string",
            "jsd:pattern": "pattern"
          },
          "subBooleanRef": {
            "jsd:class": "reference",
            "jsd:type": "boolean"
          },
          "subArray": {
            "jsd:class": "array",
            "jsd:elements": [{
              "jsd:class": "boolean"
            }, {
              "jsd:class": "number",
              "jsd:range": "[-1,1)"
            }, {
              "jsd:class": "string",
              "jsd:pattern": "pattern"
            }, {
              "jsd:class": "array",
              "jsd:elements": [{
                "jsd:class": "boolean"
              }, {
                "jsd:class": "number",
                "jsd:range": "[-1,1)"
              }, {
                "jsd:class": "string",
                "jsd:pattern": "pattern"
              }, {
                "jsd:class": "any",
                "jsd:types": "boolean number string array object"
              }]
            }, {
              "jsd:class": "reference",
              "jsd:type": "object"
            }, {
              "jsd:class": "any",
              "jsd:types": "boolean number string array object"
            }]
          }
        }
      },
      ".*": {
        "jsd:class": "any",
        "jsd:types": "boolean number string array object",
        "jsd:use": "optional"
      }
    }
  }
}
```

#### 6.3 `datatypes.jsdx`

```xml
<schema
  xmlns="http://jsonx.openjax.org/schema-0.9.8.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://jsonx.openjax.org/schema-0.9.8.xsd http://jsonx.openjax.org/schema-0.9.8.xsd">
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
  <number name="numInt" form="integer"/>
  <number name="numIntRange1" form="integer" range="[1,]"/>
  <number name="numIntRange2" form="integer" range="[,4]"/>
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
  "jsd:ns": "http://jsonx.openjax.org/schema-0.9.8.jsd",
  "jsd:schemaLocation": "http://jsonx.openjax.org/schema-0.9.8.jsd http://jsonx.openjax.org/schema-0.9.8.jsd",
  "arrayArr": {
    "jsd:class": "array",
    "jsd:elements": [{
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "arrayBool"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "arrayNum"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "arrayObj"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "arrayObj"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "arrayStr"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "arrayStr"
    }, {
      "jsd:class": "any",
      "jsd:maxOccurs": "1",
      "jsd:minOccurs": "0",
      "jsd:types": "bool num"
    }, {
      "jsd:class": "any",
      "jsd:maxOccurs": "1"
    }]
  },
  "arrayBool": {
    "jsd:class": "array",
    "jsd:elements": [{
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "bool"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "bool"
    }]
  },
  "arrayNum": {
    "jsd:class": "array",
    "jsd:elements": [{
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "num"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "num"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "numInt"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "numInt"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "numIntRange1"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "numIntRange1"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "numIntRange2"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "numIntRange2"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "numRange1"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "numRange1"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "numRange2"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "numRange2"
    }]
  },
  "arrayObj": {
    "jsd:class": "array",
    "jsd:elements": [{
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "objArr"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "objBool"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "objNum"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "objObj"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "objStr"
    }]
  },
  "arrayStr": {
    "jsd:class": "array",
    "jsd:elements": [{
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "str"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "str"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "strDec"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "strDec"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "strDecEnc"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "strDecEnc"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "strEnc"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "strEnc"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "strPattern"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "strPattern"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "strPatternDec"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "strPatternDec"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "strPatternDecEnc"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "strPatternDecEnc"
    }, {
      "jsd:class": "reference",
      "maxOccurs": "1",
      "jsd:type": "strPatternEnc"
    }, {
      "jsd:class": "reference",
      "minOccurs": "0",
      "maxOccurs": "1",
      "nullable": false,
      "jsd:type": "strPatternEnc"
    }]
  },
  "bool": {
    "jsd:class": "boolean"
  },
  "num": {
    "jsd:class": "number"
  },
  "numInt": {
    "jsd:class": "number",
    "jsd:form": "integer"
  },
  "numIntRange1": {
    "jsd:class": "number",
    "jsd:form": "integer",
    "jsd:range": "[1,]"
  },
  "numIntRange2": {
    "jsd:class": "number",
    "jsd:form": "integer",
    "jsd:range": "[,4]"
  },
  "numRange1": {
    "jsd:class": "number",
    "jsd:range": "[1,]"
  },
  "numRange2": {
    "jsd:class": "number",
    "jsd:range": "[,4]"
  },
  "str": {
    "jsd:class": "string"
  },
  "strDec": {
    "jsd:class": "string"
  },
  "strDecEnc": {
    "jsd:class": "string"
  },
  "strEnc": {
    "jsd:class": "string"
  },
  "strPattern": {
    "jsd:class": "string",
    "jsd:pattern": "[a-z]+"
  },
  "strPatternDec": {
    "jsd:class": "string",
    "jsd:pattern": "[%0-9a-z]+"
  },
  "strPatternDecEnc": {
    "jsd:class": "string",
    "jsd:pattern": "[%0-9a-z]+"
  },
  "strPatternEnc": {
    "jsd:class": "string",
    "jsd:pattern": "[%0-9a-z]+"
  },
  "objArr": {
    "jsd:class": "object",
    "jsd:properties": {
      ".*": {
        "jsd:class": "any",
        "jsd:nullable": false
      },
      "arrayBool": {
        "jsd:class": "reference",
        "jsd:type": "arrayBool"
      },
      "arrayBoolOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "arrayBool"
      },
      "arrayBoolOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "arrayBool"
      },
      "arrayNum": {
        "jsd:class": "reference",
        "jsd:type": "arrayNum"
      },
      "arrayNumOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "arrayNum"
      },
      "arrayNumOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "arrayNum"
      },
      "arrayStr": {
        "jsd:class": "reference",
        "jsd:type": "arrayStr"
      },
      "arrayStrOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "arrayStr"
      },
      "arrayStrOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "arrayStr"
      },
      "anyNumStr": {
        "jsd:class": "any",
        "jsd:types": "num str",
        "jsd:use": "optional"
      }
    }
  },
  "objBool": {
    "jsd:class": "object",
    "jsd:properties": {
      "bo+l": {
        "jsd:class": "reference",
        "nullable": false,
        "jsd:type": "bool"
      },
      ".*": {
        "jsd:class": "any",
        "jsd:types": "bool num"
      },
      "bo+lOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "bool"
      },
      "boolOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "bool"
      }
    }
  },
  "objNum": {
    "jsd:class": "object",
    "jsd:properties": {
      "num.+": {
        "jsd:class": "reference",
        "jsd:type": "num"
      },
      "numOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "num"
      },
      "numOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "num"
      },
      "numInt": {
        "jsd:class": "reference",
        "jsd:type": "numInt"
      },
      "any": {
        "jsd:class": "any",
        "jsd:nullable": false,
        "jsd:types": "num str"
      },
      "numIntOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "numInt"
      },
      "numIntOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "numInt"
      },
      "numIntRange1": {
        "jsd:class": "reference",
        "jsd:type": "numIntRange1"
      },
      "numIntRange1Optional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "numIntRange1"
      },
      "numIntRange1OptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "numIntRange1"
      },
      "numIntRange2": {
        "jsd:class": "reference",
        "jsd:type": "numIntRange2"
      },
      "numIntRange2Optional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "numIntRange2"
      },
      "numIntRange2OptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "numIntRange2"
      },
      "numRange1": {
        "jsd:class": "reference",
        "jsd:type": "numRange1"
      },
      "numRange1Optional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "numRange1"
      },
      "numRange1OptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "numRange1"
      },
      "numRange2": {
        "jsd:class": "reference",
        "jsd:type": "numRange2"
      },
      "numRange2Optional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "numRange2"
      },
      "numRange2OptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "numRange2"
      }
    }
  },
  "objObj": {
    "jsd:class": "object",
    "jsd:properties": {
      "objOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "objBool"
      },
      "objOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "objNum"
      },
      "objExtends1": {
        "jsd:class": "object",
        "jsd:extends": "objObj",
        "jsd:use": "optional",
        "jsd:properties": {
          "objExtends2": {
            "jsd:class": "object",
            "jsd:extends": "objObj",
            "jsd:use": "optional",
            "jsd:properties": {
              "objExtends3": {
                "jsd:class": "object",
                "jsd:extends": "objObj",
                "jsd:use": "optional",
                "jsd:properties": {
                  "objExtends4": {
                    "jsd:class": "object",
                    "jsd:extends": "objObj",
                    "jsd:use": "optional",
                    "jsd:properties": {
                      "num": {
                        "jsd:class": "reference",
                        "use": "optional",
                        "jsd:type": "num"
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
        "jsd:class": "object",
        "jsd:extends": "objBool",
        "jsd:use": "optional",
        "jsd:properties": {
          "num": {
            "jsd:class": "reference",
            "jsd:type": "num"
          }
        }
      },
      "objExtendsOptionalNotNullable": {
        "jsd:class": "object",
        "jsd:extends": "objBool",
        "jsd:nullable": false,
        "jsd:use": "optional",
        "jsd:properties": {
          "num": {
            "jsd:class": "reference",
            "jsd:type": "num"
          }
        }
      }
    }
  },
  "objStr": {
    "jsd:class": "object",
    "jsd:properties": {
      "str": {
        "jsd:class": "reference",
        "jsd:type": "str"
      },
      "strOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "str"
      },
      "strOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "str"
      },
      "strDec": {
        "jsd:class": "reference",
        "jsd:type": "strDec"
      },
      "strDecOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "strDec"
      },
      "strDecOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "strDec"
      },
      "strDecEnc": {
        "jsd:class": "reference",
        "jsd:type": "strDecEnc"
      },
      "strDecEncOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "strDecEnc"
      },
      "strDecEncOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "strDecEnc"
      },
      "strEnc": {
        "jsd:class": "reference",
        "jsd:type": "strEnc"
      },
      "strEncOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "strEnc"
      },
      "strEncOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "strEnc"
      },
      "strPattern": {
        "jsd:class": "reference",
        "jsd:type": "strPattern"
      },
      "strPatternOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "strPattern"
      },
      "strPatternOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "strPattern"
      },
      "strPatternDec": {
        "jsd:class": "reference",
        "jsd:type": "strPatternDec"
      },
      "strPatternDecOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "strPatternDec"
      },
      "strPatternDecOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "strPatternDec"
      },
      "strPatternDecEnc": {
        "jsd:class": "reference",
        "jsd:type": "strPatternDecEnc"
      },
      "strPatternDecEncOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "strPatternDecEnc"
      },
      "strPatternDecEncOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "strPatternDecEnc"
      },
      "strPatternEnc": {
        "jsd:class": "reference",
        "jsd:type": "strPatternEnc"
      },
      "strPatternEncOptional": {
        "jsd:class": "reference",
        "use": "optional",
        "jsd:type": "strPatternEnc"
      },
      "strPatternEncOptionalNotNullable": {
        "jsd:class": "reference",
        "use": "optional",
        "nullable": false,
        "jsd:type": "strPatternEnc"
      }
    }
  }
}
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#introduction]: #1-introduction
[#dependencies]: #11-dependencies-on-other-specifications
[#conventions]: #12-conventions-used-in-this-document
[#purpose]: #2-purpose
[#requirements]: #3-requirements
[#specification]: #4-specification
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
[#samples]: #5-sample-schemas
[#resources]: #5-related-resources-for-json-schema
[#json-schemas]: #51-schemas-for-json-schema
[#schema-098]: #511-json-schema-098
[#schema-097]: #512-json-schema-097
[#structurejsdx]: #51-structurejsdx
[#structurejsd]: #52-structurejsd
[#datatypejsdx]: #53-datatypejsdx
[#datatypejsd]: #54-datatypejsd
[interval-notation]: https://en.wikipedia.org/wiki/Interval_(mathematics#Including_or_excluding_endpoints)
[jdk8-download]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[json]: http://www.json.org/
[jsonx-jsonx]: /generator/src/main/resources/schema.jsonx
[jsonx-maven-plugin]: /maven-plugin
[jsonx-xsd]: /generator/src/main/resources/schema.xsd
[maven-archetype-quickstart]: http://maven.apache.org/archetypes/maven-archetype-quickstart/
[maven]: https://maven.apache.org/
[rfc4627]: https://www.ietf.org/rfc/rfc4627.txt
[xmlschema]: http://www.w3.org/2001/XMLSchema