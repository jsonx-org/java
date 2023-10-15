# JSONx Binding Generator

> **JSON Schema for the enterprise**

[![Build Status](https://github.com/jsonx-org/java/actions/workflows/build.yml/badge.svg)](https://github.com/jsonx-org/java/actions/workflows/build.yml)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/generator.svg)](https://www.javadoc.io/doc/org.jsonx/generator)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/generator.svg)](https://mvnrepository.com/artifact/org.jsonx/generator)
![Snapshot Version](https://img.shields.io/nexus/s/org.jsonx/generator?label=maven-snapshot&server=https%3A%2F%2Foss.sonatype.org)

## Abstract

This document specifies the <ins>JSONx Binding Generator</ins>, which offers facilities for generating Java binding classes from a [JSD schema][schema].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [<ins>Introduction</ins>](#1-introduction)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document](#11-conventions-used-in-this-document)<br>
<samp>&nbsp;&nbsp;</samp>2 [<ins>Purpose</ins>](#2-purpose)<br>
<samp>&nbsp;&nbsp;</samp>3 [<ins>Requirements</ins>](#3-requirements)<br>
<samp>&nbsp;&nbsp;</samp>4 [<ins>Getting Started</ins>](#4-getting-started)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [Generator](#41-generator)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [Converter](#42-converter)<br>
<samp>&nbsp;&nbsp;</samp>5 [<ins>Specification</ins>](#5-specification)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1 [`Generator`](#51-generator)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2 [`Converter`](#52-converter)<br>
<samp>&nbsp;&nbsp;</samp>6 [<ins>Contributing</ins>](#6-contributing)<br>
<samp>&nbsp;&nbsp;</samp>7 [<ins>Special Thanks</ins>](#7-special-thanks)<br>
<samp>&nbsp;&nbsp;</samp>8 [<ins>License</ins>](#8-license)

## <b>1</b> <ins>Introduction</ins>

This document presents the functionality of the <ins>JSONx Binding Generator</ins>. It also contains a directory of links to related resources.

The <ins>JSONx Binding Generator</ins> consumes a JSD schema, and produces Java classes in the form of `.java` files. The generated classes have code that relies on the <ins>JSONx Runtime API</ins> to achieve binding between JSON documents conforming to a JSD schema, and Java object represetations of these documents.

### <b>1.1</b> Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## <b>2</b> <ins>Purpose</ins>

Provide a <ins>binding generator</ins> utility for automatic generation of binding classes from a <ins>schema document</ins>.

## <b>3</b> <ins>Requirements</ins>

1. The <ins>binding generator</ins> MUST be able to consume a <ins>schema document</ins>, and produce Java class definitions (`.java` files) that use the <ins>runtime API</ins>.

1. The <ins>binding generator</ins> MUST be able to consume Java class definitions (`.class` files) utilizing the <ins>runtime API</ins>, and produce a <ins>schema document</ins>.

1. The <ins>binding generator</ins> MUST create Java classes (`.java` files) that encode the full normative scope of the <ins>schema document</ins>.

1. The <ins>binding generator</ins> MUST represent the constituent parts of a <ins>schema document</ins> with Java type bindings that are as strongly-typed as possible, but not limiting in any way with regard to the definition of the respective constituent part.

1. The <ins>binding generator</ins> MUST be able to validate a <ins>schema document</ins>.

## <b>4</b> <ins>Getting Started</ins>

The <ins>JSONx Binding Generator</ins> provides convenience utilities for generating bindings and converting <ins>schema document</ins>s. The following illustrates example usage of the `Generator` and `Converter` executable classes.

### <b>4.1</b> `Generator`

The following example generates binding classes (`.java` files) in `target/generated-sources/jsonx` for the <ins>schema document</ins> at `src/main/resources/example.jsd`, with package prefix `org.example$`.

```bash
java -cp ... org.jsonx.Generator -p org.example$ -d target/generated-sources/jsonx src/main/resources/example.jsd
```

### <b>4.2</b> `Converter`

The following example converts the JSD file at `src/main/resources/example.jsd` to a <ins>JSDx</ins> file in `target/generated-resources`.

```bash
java -cp ... org.jsonx.Converter src/main/resources/example.jsd target/generated-resources/example.jsdx
```

## <b>5</b> <ins>Specification</ins>

The <ins>JSONx Binding Generator</ins> generates Java binding classes. The generated classes contain the full scope of specification of the JSD schema from which they are generated. This means that the generated classes can be converted back to the JSD from which they were created, and the normative scope of the schema will be preserved.

A distinction has to be made between "normative scope" and "non-normative scope". When referring to a JSD schema, the term "normative scope" represents the structural scope, which isolates the part of the schema document that defines validation criteria for facets of JSON documents. The term "non-normative scope" includes the information in the JSD schema that does not have significance unto the validation for facets of JSON documents. An example of a "non-normative scope" is the name of a `<string name="someName" ...>` type as the root element of the schema. The <ins>JSONx Binding Generator</ins> discards this information when creating the Java binding classes.

### <b>5.1</b> `Generator`

The `Generator` is a utility class that can be used on the CLI to generate Java binding classes from a JSD schema. The `Generator` class has the following usage specification:

```
Usage: Generator <-p [NAMESPACE] PREFIX>... <-d DEST_DIR> <SCHEMA.jsd|SCHEMA.jsdx|BINDING.jsb|BINDING.jsbx>...

Mandatory arguments:
  -p [NAMESPACE] <PREFIX>  Package prefix of generated classes for provided namespace, recurrable.
  -d <DEST_DIR>            The destination directory.

Supported SCHEMA_FILE formats:
                 <JSD|JSDx|JSB|JSBx>
```

### <b>5.2</b> `Converter`

The `Converter` is a utility class that can be used on the CLI to convert JSD files to JSDx, and vice versa. The `Converter` class has the following usage specification:

```
Usage: Converter <SCHEMA_IN.jsd|SCHEMA_IN.jsdx> [SCHEMA_OUT.jsd|SCHEMA_OUT.jsdx]
            (or) <BINDING_IN.jsb|BINDING_IN.jsbx> [BINDING_OUT.jsb|BINDING_OUT.jsbx]

Supported SCHEMA_IN|OUT formats:
                 <JSD|JSDx|JSB|JSBx>
```

If a `SCHEMA_OUT` argument is not provided, the `Converter` will output the converted content to stdout.

## <b>6</b> <ins>Contributing</ins>

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <b>7</b> <ins>Special Thanks</ins>

[![Java Profiler](https://www.ej-technologies.com/images/product_banners/jprofiler_small.png)](https://www.ej-technologies.com/products/jprofiler/overview.html)
<br><sub>_Special thanks to [EJ Technologies](https://www.ej-technologies.com/) for providing their award winning Java Profiler ([JProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html)) for development of the JSONx Framework._</sub>

## <b>8</b> <ins>License</ins>

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[schema]: ../../../../schema/