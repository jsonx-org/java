# JSD Binding Generator

[![Build Status](https://travis-ci.org/openjax/jsonx.png)](https://travis-ci.org/openjax/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/openjax/jsonx/badge.svg)](https://coveralls.io/github/openjax/jsonx)
[![Javadocs](https://www.javadoc.io/badge/org.openjax.jsonx/generator.svg)](https://www.javadoc.io/doc/org.openjax.jsonx/generator)
[![Released Version](https://img.shields.io/maven-central/v/org.openjax.jsonx/generator.svg)](https://mvnrepository.com/artifact/org.openjax.jsonx/generator)

## Abstract

This document specifies the <ins>JSD Binding Generator</ins>, which offers facilities for generating Java binding classes from a JSD schema.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [Purpose][#purpose]<br>
<samp>&nbsp;&nbsp;</samp>3 [Requirements][#requirements]<br>
<samp>&nbsp;&nbsp;</samp>4 [Specification][#specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [`Generator`][#generator]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [`Converter`][#converter]

## 1 Introduction

This document presents the functionality of the <ins>JSD Binding Generator</ins>. It also contains a directory of links to these related resources.

The <ins>JSD Binding Generator</ins> consumes a JSD schema, and produces Java classes in the form of `.java` files. The generated classes have code that relies on the <ins>JSON/Java Binding API</ins> to achieve binding between JSON documents conforming to a JSD schema, and Java object represetations of these documents.

### 1.1 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Purpose

Provide a <ins>binding generator</ins> utility for automatic generation of binding classes from a <ins>schema document</ins>.

## 3 Requirements

1. The <ins>binding generator</ins> MUST be able to consume a <ins>schema document</ins>, and produce Java class definitions (`.java` files) that use the <ins>binding API</ins>.

1. The <ins>binding generator</ins> MUST be able to consume Java class definitions (`.class` files) utilizing the <ins>binding API</ins>, and produce a <ins>schema document</ins>.

1. The <ins>binding generator</ins> MUST create Java classes (`.java` files) that encode the full normative scope of the <ins>schema document</ins>.

1. The <ins>binding generator</ins> MUST represent the constituent parts of a <ins>schema document</ins> with Java type bindings that are as strongly-typed as possible, but not limiting in any way with regard to the definition of the respective constituent part.

1. The <ins>binding generator</ins> MUST be able to validate a <ins>schema document</ins>.

## 4 Specification

The <ins>JSD Binding Generator</ins> generates Java binding classes. The generated classes contain the full scope of specification of the JSD schema from which they are generated. This means that the generated classes can be converted back to the JSD from which they were created, and the normative scope of the schema will be preserved.

A distinction has to be made between "normative scope" and "non-normative scope". When referring to a JSD schema, the term "normative scope" represents the structural scope, which isolates the part of the schema document that defines validation criteria for facets of JSON documents. The term "non-normative scope" includes the information in the JSD schema that does not have significance unto the validation for facets of JSON documents. An example of a "non-normative scope" is the name of a `<string name="someName" ...>` type as the root element of the schema. The <ins>JSD Binding Generator</ins> discards this information when creating the Java binding classes.

### 4.1 `Generator`

The `Generator` is a utility class that can be used on the CLI to generate Java binding classes from a JSD schema. The `Generator` class has the following usage specification:

```
Usage: Generator [OPTIONS] <-d DEST_DIR> <SCHEMA_FILE>

Mandatory arguments:
  -d <destDir>       Specify the destination directory.

Optional arguments:
  --prefix <PREFIX>  Package prefix for generated classes.

Supported SCHEMA_FILE formats:
                 <JSD|JSDX>
```

### 4.2 `Converter`

The `Converter` is a utility class that can be used on the CLI to convert JSD files to JSDX, and vice versa. The `Converter` class has the following usage specification:

```
Usage: Converter <SCHEMA_IN> [SCHEMA_OUT]

Supported SCHEMA_IN|OUT formats:
                 <JSD|JSDX>
```

If a `SCHEMA_OUT` argument is not provided, the `Converter` will output the converted content to stdout.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#introduction]: #1-introduction
[#conventions]: #11-conventions
[#purpose]: #2-purpose
[#requirements]: #3-requirements
[#specification]: #4-specification
[#generator]: #41-generator
[#converter]: #42-converter