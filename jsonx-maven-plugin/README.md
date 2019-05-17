# JSONx Maven Plugin

[![Build Status](https://travis-ci.org/jsonxorg/jsonx.png)](https://travis-ci.org/jsonxorg/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/jsonxorg/jsonx/badge.svg)](https://coveralls.io/github/jsonxorg/jsonx)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/jsonx-maven-plugin.svg)](https://www.javadoc.io/doc/org.jsonx/jsonx-maven-plugin)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/jsonx-maven-plugin.svg)](https://mvnrepository.com/artifact/org.jsonx/jsonx-maven-plugin)

## Abstract

This document specifies the <ins>JSONx Maven Plugin</ins>, which offers facilities for for generating JSONx and JSD bindings with the [JSON/Java Binding API][api].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction](#1-introduction)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document](#11-conventions-used-in-this-document)<br>
<samp>&nbsp;&nbsp;</samp>2 [Purpose](#2-purpose)<br>
<samp>&nbsp;&nbsp;</samp>3 [Requirements](#3-requirements)<br>
<samp>&nbsp;&nbsp;</samp>4 [Specification](#4-specification)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [Goals Overview](#41-goals-overview)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [Usage](#42-usage)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1 [`jsonx:generate`](#421-jsonxgenerate)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1.1 [Configuration Parameters](#4211-configuration-parameters)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.1.2 [Example](#4212-example)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.2 [`jsonx:convert`](#422-jsonxconvert)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.2.1 [Configuration Parameters](#4221-configuration-parameters)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.2.2 [Example](#4222-example)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.3 [`jsonx:validate`](#423-jsonxvalidate)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.3.1 [Configuration Parameters](#4231-configuration-parameters)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2.3.2 [Example](#4232-example)<br>
<samp>&nbsp;&nbsp;</samp>5 [Contributing](#5-contributing)<br>
<samp>&nbsp;&nbsp;</samp>6 [License](#6-license)

### 1 Introduction

The `jsonx-maven-plugin` plugin is used to generate JSONx and JSD bindings with the [JSONx][jsonx] framework.

### 1.1 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Purpose

Provide schema validation, code generation, and other convenience utlities in a <ins>Maven plugin</ins>.

## 3 Requirements

1. The <ins>Maven plugin</ins> MUST offer utilities for the generation of binding classes from a specified <ins>schema document</ins>.

1. The <ins>Maven plugin</ins> MUST offer utilities for validation of <ins>schema document</ins>s and binding classes.

1. The <ins>Maven plugin</ins> MUST present clear and informative errors and warnings that arise during parsing and validation of <ins>schema document</ins>s and JSON documents with an associated schema.

## 4 Specification

### 4.1 Goals Overview

* [`jsonx:generate`](#jsonxgenerate) generates Java binding sources from <ins>JSD</ins> or <ins>JSDx</ins> schemas.
* [`jsonx:convert`](#jsonxconvert) converts <ins>JSD</ins> schemas to <ins>JSDx</ins>, and vice versa.
* [`jsonx:valdate`](#jsonxvalidate) validates <ins>JSD</ins> and <ins>JSDx</ins> schemas.

### 4.2 Usage

#### 4.2.1 `jsonx:generate`

The `jsonx:generate` goal is bound to the `generate-sources` phase, and is used to generate Java binding sources for <ins>JSD</ins> or <ins>JSDx</ins> schemas specified in the `configuration`.

##### 4.2.1.1 Configuration Parameters

| Name                          | Type    | Use      | Description                             |
|:------------------------------|:--------|:---------|:----------------------------------------|
| <samp>/destDir¹</samp>        | String  | Required | Destination path of generated bindings. |
| <samp>/prefix¹</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | String<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;  | Required<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | Prefix to be prepended to the class names of generated bindings.<br>The prefix represents a:<ul><li>Package name if it ends with an unescaped <samp>.</samp> character.</li><li>Declaring class name if it ends with an unescaped <samp>$</samp> character.</li></ul> |
| <samp>/schemas¹</samp>        | List    | Required | List of <samp>schema</samp> elements.   |
| <samp>/schemas/schemaⁿ</samp> | String  | Required | File path or URL of JSD or JSDx schema. |

##### 4.2.1.2 Example

```xml
<plugin>
  <groupId>org.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.2.2</version>
  <executions>
    <execution>
      <phase>generate-sources</phase>
      <goals>
        <goal>generate</goal>
      </goals>
      <configuration>
        <destDir>${project.build.directory}/generated-sources/jsonx</destDir>
        <prefix>com.example.json.</prefix>
        <schemas>
          <schema>src/main/resources/schema1.jsd</schema>
          <schema>src/main/resources/schema2.jsdx</schema>
        </schemas>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### 4.2.2 `jsonx:convert`

The `jsonx:convert` goal is bound to the `generate-resources` phase, and is used to convert <ins>JSD</ins> schemas to <ins>JSDx</ins>, and vice versa.

##### 4.2.2.1 Configuration Parameters

| Name                          | Type    | Use      | Description                             |
|:------------------------------|:--------|:---------|:----------------------------------------|
| <samp>/destDir¹</samp>        | String  | Required | Destination path of converted schemas.  |
| <samp>/schemas¹</samp>        | List    | Required | List of <samp>schema</samp> elements.   |
| <samp>/schemas/schemaⁿ</samp> | String  | Required | File path or URL of JSD or JSDx schema. |

##### 4.2.2.2 Example

```xml
<plugin>
  <groupId>org.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.2.3-SNAPSHOT</version>
  <executions>
    <execution>
      <phase>generate-resources</phase>
      <goals>
        <goal>convert</goal>
      </goals>
      <configuration>
        <destDir>${project.build.directory}/generated-resources/jsonx</destDir>
        <schemas>
          <schema>src/main/resources/schema1.jsd</schema>
          <schema>src/main/resources/schema2.jsdx</schema>
        </schemas>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### 4.2.3 `jsonx:validate`

The `jsonx:validate` goal is bound to the `compile` phase, and is used to validate <ins>JSD</ins> or <ins>JSDx</ins> schemas.

##### 4.2.3.1 Configuration Parameters

| Name                          | Type    | Use      | Description                             |
|:------------------------------|:--------|:---------|:----------------------------------------|
| <samp>/schemas¹</samp>        | List    | Required | List of <samp>schema</samp> elements.   |
| <samp>/schemas/schemaⁿ</samp> | String  | Required | File path or URL of JSD or JSDx schema. |

##### 4.2.3.2 Example

```xml
<plugin>
  <groupId>org.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.2.3-SNAPSHOT</version>
  <executions>
    <execution>
      <phase>generate-resources</phase>
      <goals>
        <goal>validate</goal>
      </goals>
      <configuration>
        <destDir>${project.build.directory}/generated-resources/jsonx</destDir>
        <schemas>
          <schema>src/main/resources/schema1.jsd</schema>
          <schema>src/main/resources/schema2.jsdx</schema>
        </schemas>
      </configuration>
    </execution>
  </executions>
</plugin>
```

## 5 Contributing

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## 6 License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[api]: /../../binding/
[jsonx]: /../..