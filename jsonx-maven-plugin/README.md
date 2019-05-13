# JSONX Maven Plugin

[![Build Status](https://travis-ci.org/jsonxorg/jsonx.png)](https://travis-ci.org/jsonxorg/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/jsonxorg/jsonx/badge.svg)](https://coveralls.io/github/jsonxorg/jsonx)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/jsonx-maven-plugin.svg)](https://www.javadoc.io/doc/org.jsonx/jsonx-maven-plugin)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/jsonx-maven-plugin.svg)](https://mvnrepository.com/artifact/org.jsonx/jsonx-maven-plugin)

## Abstract

This document specifies the <ins>JSONX Maven Plugin</ins>, which offers facilities for for generating JSONX and JSD bindings with the [JSON/Java Binding API][api].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [Purpose][#purpose]<br>
<samp>&nbsp;&nbsp;</samp>3 [Requirements][#requirements]<br>
<samp>&nbsp;&nbsp;</samp>4 [Specification][#specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [Goals Overview][#goals]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [Usage][#usage]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1 [`jsonx:generate`][#generate]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1.1 [Example][#example]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.2 [Configuration Parameters][#parameters]

### 1 Introduction

The `jsonx-maven-plugin` plugin is used to generate JSONX and JSD bindings with the [JSONX][jsonx] framework.

### 1.1 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Purpose

Provide schema validation, code generation, and other convenience utlities in a <ins>Maven plugin</ins>.

## 3 Requirements

1. The <ins>Maven plugin</ins> MUST offer utilities for the generation of binding classes from a specified <ins>schema document</ins>.

1. The <ins>Maven plugin</ins> MUST offer utilities for validation of <ins>schema document</ins>s and binding classes.

1. The <ins>Maven plugin</ins> MUST present clear and informative errors and warnings that arise during parsing and validation of <ins>schema document</ins>s and JSON documents with an associated schema.

## 4 Specification

### 4.1 Goals Overview

* [`jsonx:generate`](#jsonxgenerate) generates JSD bindings.

### 4.2 Usage

#### 4.2.1 `jsonx:generate`

The `jsonx:generate` goal is bound to the `generate-sources` phase, and is used to generate Java bindings for JSD schemas specified in the `configuration`. To configure the generation of JSD bindings for desired JSD schemas, add a `configuration` element to the plugin's specification.

##### 4.2.1.1 Example

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
          <schema>src/main/resources/schema.jsd</schema>
        </schemas>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### 4.2.2 Configuration Parameters

| Name                          | Type    | Use      | Description                             |
|:------------------------------|:--------|:---------|:----------------------------------------|
| <samp>/destDir¹</samp>        | String  | Required | Destination path of generated bindings. |
| <samp>/prefix¹</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | String<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;  | Required<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | Prefix to be prepended to the class names of generated bindings.<br>The prefix represents a:<ul><li>Package name if it ends with an unescaped <samp>.</samp> character.</li><li>Declaring class name if it ends with an unescaped <samp>$</samp> character.</li></ul> |
| <samp>/schemas¹</samp>        | List    | Required | List of <samp>schema</samp> elements.   |
| <samp>/schemas/schemaⁿ</samp> | String  | Required | File path of JSD or JSDX schema.        |

## Contributing

Pull requests are welcome. For major changes, please [open an issue](../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#introduction]: #1-introduction
[#conventions]: #11-conventions-used-in-this-document
[#purpose]: #2-purpose
[#requirements]: #3-requirements
[#specification]: #4-specification
[#goals]: #41-goals-overview
[#usage]: #42-usage
[#generate]: #421-jsonxgenerate
[#example]: #4211-example
[#parameters]: #422-configuration-parameters

[api]: /../../binding/
[jsonx]: /../..