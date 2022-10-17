# JSONx Maven Plugin

> **JSON Schema for the enterprise**

[![Build Status](https://github.com/jsonx-org/java/actions/workflows/build.yml/badge.svg)](https://github.com/jsonx-org/java/actions/workflows/build.yml)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/jsonx-maven-plugin.svg)](https://www.javadoc.io/doc/org.jsonx/jsonx-maven-plugin)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/jsonx-maven-plugin.svg)](https://mvnrepository.com/artifact/org.jsonx/jsonx-maven-plugin)
![Snapshot Version](https://img.shields.io/nexus/s/org.jsonx/jsonx-maven-plugin?label=maven-snapshot&server=https%3A%2F%2Foss.sonatype.org)

## Abstract

This document specifies the <ins>JSONx Maven Plugin</ins>, which offers facilities for for generating JSONx and JSD bindings with the [JSONx Binding API][binding].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [<ins>Introduction</ins>](#1-introduction)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document](#11-conventions-used-in-this-document)<br>
<samp>&nbsp;&nbsp;</samp>2 [<ins>Purpose</ins>](#2-purpose)<br>
<samp>&nbsp;&nbsp;</samp>3 [<ins>Requirements</ins>](#3-requirements)<br>
<samp>&nbsp;&nbsp;</samp>4 [<ins>Getting Started</ins>](#4-getting-started)<br>
<samp>&nbsp;&nbsp;</samp>5 [<ins>Specification</ins>](#5-specification)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1 [Goals Overview](#51-goals-overview)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2 [Usage](#52-usage)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2.1 [`jsonx:generate`](#521-jsonxgenerate)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2.1.1 [Configuration Parameters](#5211-configuration-parameters)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2.1.2 [Example](#5212-example)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2.2 [`jsonx:convert`](#522-jsonxconvert)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2.2.1 [Configuration Parameters](#5221-configuration-parameters)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2.2.2 [Example](#5222-example)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2.3 [`jsonx:validate`](#523-jsonxvalidate)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2.3.1 [Configuration Parameters](#5231-configuration-parameters)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2.3.2 [Example](#5232-example)<br>
<samp>&nbsp;&nbsp;</samp>6 [<ins>Contributing</ins>](#6-contributing)<br>
<samp>&nbsp;&nbsp;</samp>7 [<ins>Special Thanks</ins>](#7-special-thanks)<br>
<samp>&nbsp;&nbsp;</samp>8 [<ins>License</ins>](#8-license)

### <b>1</b> <ins>Introduction</ins>

The `jsonx-maven-plugin` plugin is used to generate JSONx and JSD bindings with the [JSONx Framework for Java][jsonx].

### <b>1.1</b> Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## <b>2</b> <ins>Purpose</ins>

Provide schema validation, code generation, and other convenience utlities in a <ins>Maven plugin</ins>.

## <b>3</b> <ins>Requirements</ins>

1. The <ins>JSONx Maven plugin</ins> MUST offer utilities for the generation of binding classes from a specified <ins>schema document</ins>.

1. The <ins>JSONx Maven plugin</ins> MUST offer utilities for validation of <ins>schema document</ins>s and binding classes.

1. The <ins>JSONx Maven plugin</ins> MUST present clear and informative errors and warnings that arise during parsing and validation of <ins>schema document</ins>s and JSON documents with an associated schema.

## <b>4</b> <ins>Getting Started</ins>

The <ins>JSONx Maven Plugin</ins> implements a Maven MOJO that can be used in a `pom.xml`. The following illustrates an example usage.

```xml
<plugin>
  <groupId>org.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.3.2</version>
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
          <schema>src/main/resources/schema.jsd</schema> <!-- or schema.jsdx -->
        </schemas>
      </configuration>
    </execution>
  </executions>
</plugin>
```

## <b>5</b> <ins>Specification</ins>

### <b>5.1</b> Goals Overview

* [`jsonx:generate`](#jsonxgenerate) generates Java binding sources from <ins>JSD</ins> or <ins>JSDx</ins> schemas.
* [`jsonx:convert`](#jsonxconvert) converts <ins>JSD</ins> schemas to <ins>JSDx</ins>, and vice versa.
* [`jsonx:valdate`](#jsonxvalidate) validates <ins>JSD</ins> and <ins>JSDx</ins> schemas.

### <b>5.2</b> Usage

#### <b>5.2.1</b> `jsonx:generate`

The `jsonx:generate` goal is bound to the `generate-sources` phase, and is used to generate Java binding sources for <ins>JSD</ins> or <ins>JSDx</ins> schemas specified in the `configuration`.

##### <b>5.2.1.1</b> Configuration Parameters

| Name                          | Type    | Use      | Description                             |
|:------------------------------|:--------|:---------|:----------------------------------------|
| <samp>/destDir¹</samp>        | String  | Required | Destination path of generated bindings. |
| <samp>/prefix¹</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | String<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;  | Required<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp; | Prefix to be prepended to the class names of generated bindings.<br>The prefix represents a:<ul><li>Package name if it ends with an unescaped <samp>.</samp> character.</li><li>Declaring class name if it ends with an unescaped <samp>$</samp> character.</li></ul> |
| <samp>/schemas¹</samp>        | List    | Required | List of <samp>schema</samp> elements.   |
| <samp>/schemas/schemaⁿ</samp> | String  | Required | File path or URL of JSD or JSDx schema. |

##### <b>5.2.1.2</b> Example

```xml
<plugin>
  <groupId>org.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.3.2</version>
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

#### <b>5.2.2</b> `jsonx:convert`

The `jsonx:convert` goal is bound to the `generate-resources` phase, and is used to convert <ins>JSD</ins> schemas to <ins>JSDx</ins>, and vice versa.

##### <b>5.2.2.1</b> Configuration Parameters

| Name                          | Type    | Use      | Description                             |
|:------------------------------|:--------|:---------|:----------------------------------------|
| <samp>/destDir¹</samp>        | String  | Required | Destination path of converted schemas.  |
| <samp>/schemas¹</samp>        | List    | Required | List of <samp>schema</samp> elements.   |
| <samp>/schemas/schemaⁿ</samp> | String  | Required | File path or URL of JSD or JSDx schema. |

##### <b>5.2.2.2</b> Example

```xml
<plugin>
  <groupId>org.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.3.2</version>
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

#### <b>5.2.3</b> `jsonx:validate`

The `jsonx:validate` goal is bound to the `compile` phase, and is used to validate <ins>JSD</ins> or <ins>JSDx</ins> schemas.

##### <b>5.2.3.1</b> Configuration Parameters

| Name                          | Type    | Use      | Description                             |
|:------------------------------|:--------|:---------|:----------------------------------------|
| <samp>/schemas¹</samp>        | List    | Required | List of <samp>schema</samp> elements.   |
| <samp>/schemas/schemaⁿ</samp> | String  | Required | File path or URL of JSD or JSDx schema. |

##### <b>5.2.3.2</b> Example

```xml
<plugin>
  <groupId>org.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.3.2</version>
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

## <b>6</b> <ins>Contributing</ins>

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <b>7</b> <ins>Special Thanks</ins>

[![Java Profiler](https://www.ej-technologies.com/images/product_banners/jprofiler_small.png)](https://www.ej-technologies.com/products/jprofiler/overview.html)
<br><sub>_Special thanks to [EJ Technologies](https://www.ej-technologies.com/) for providing their award winning Java Profiler ([JProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html)) for development of the JSONx Framework._</sub>

## <b>8</b> <ins>License</ins>

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[binding]: ../../binding/
[jsonx]: ../../