# JSONx Binding Generator

> **JSON Schema for the enterprise**

[![Build Status](https://travis-ci.org/jsonx-org/java.svg?EKkC4CBk)](https://travis-ci.org/jsonx-org/java)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg?EKkC4CBk)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/generator.svg?EKkC4CBk)](https://www.javadoc.io/doc/org.jsonx/generator)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/generator.svg?EKkC4CBk)](https://mvnrepository.com/artifact/org.jsonx/generator)

## Abstract

This document specifies the <ins>JSONx Binding Generator</ins>, which offers facilities for generating Java binding classes from a [JSD schema][schema].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction](#1-introduction)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document](#11-conventions-used-in-this-document)<br>
<samp>&nbsp;&nbsp;</samp>2 [Purpose](#2-purpose)<br>
<samp>&nbsp;&nbsp;</samp>3 [Requirements](#3-requirements)<br>
<samp>&nbsp;&nbsp;</samp>4 [Specification](#4-specification)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [`Generator`](#41-generator)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [`Converter`](#42-converter)<br>
<samp>&nbsp;&nbsp;</samp>5 [Contributing](#5-contributing)<br>
<samp>&nbsp;&nbsp;</samp>6 [License](#6-license)

## 1 Introduction

This document presents the functionality of the <ins>JSONx Binding Generator</ins>. It also contains a directory of links to these related resources.

The <ins>JSONx Binding Generator</ins> consumes a JSD schema, and produces Java classes in the form of `.java` files. The generated classes have code that relies on the <ins>JSONx Binding API</ins> to achieve binding between JSON documents conforming to a JSD schema, and Java object represetations of these documents.

### 1.1 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Purpose

Provide a <ins>binding generator</ins> utility for automatic generation of binding classes from a <ins>schema document</ins>.

## 3 Requirements

1. The <ins>binding generator</ins> MUST be able to consume a <ins>schema document</ins>, and produce Java class definitions (`.java` files) that use the <ins>binding API</ins>.

1. The <ins>binding generator</ins> MUST be able to consume Java class definitions (`.class` files) utilizing the <ins>binding API</ins>, and produce a <ins>schema document</ins>.

1. The <ins>binding generator</ins> MUST create Java classes (`.java` files) that encode the full normative scope of the <ins>schema document</ins>.

1. The <ins>binding generator</ins> MUST represent the constituent parts of a <ins>schema document</ins> with Java type bindings that are as strongly-typed as possible, but not limiting in any way with regard to the definition of the respective constituent part.

1. The <ins>binding generator</ins> MUST be able to validate a <ins>schema document</ins>.

## 4 Specification

The <ins>JSONx Binding Generator</ins> generates Java binding classes. The generated classes contain the full scope of specification of the JSD schema from which they are generated. This means that the generated classes can be converted back to the JSD from which they were created, and the normative scope of the schema will be preserved.

A distinction has to be made between "normative scope" and "non-normative scope". When referring to a JSD schema, the term "normative scope" represents the structural scope, which isolates the part of the schema document that defines validation criteria for facets of JSON documents. The term "non-normative scope" includes the information in the JSD schema that does not have significance unto the validation for facets of JSON documents. An example of a "non-normative scope" is the name of a `<string name="someName" ...>` type as the root element of the schema. The <ins>JSONx Binding Generator</ins> discards this information when creating the Java binding classes.

### 4.1 `Generator`

The `Generator` is a utility class that can be used on the CLI to generate Java binding classes from a JSD schema. The `Generator` class has the following usage specification:

```
Usage: Generator [OPTIONS] <-d DEST_DIR> <SCHEMA_FILE>

Mandatory arguments:
  -d <destDir>       Specify the destination directory.

Optional arguments:
  --prefix <PREFIX>  Package prefix for generated classes.

Supported SCHEMA_FILE formats:
                 <JSD|JSDx>
```

### 4.2 `Converter`

The `Converter` is a utility class that can be used on the CLI to convert JSD files to JSDx, and vice versa. The `Converter` class has the following usage specification:

```
Usage: Converter <SCHEMA_IN> [SCHEMA_OUT]

Supported SCHEMA_IN|OUT formats:
                 <JSD|JSDx>
```

If a `SCHEMA_OUT` argument is not provided, the `Converter` will output the converted content to stdout.

## 5 Contributing

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## 6 License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[schema]: ../../../../schema