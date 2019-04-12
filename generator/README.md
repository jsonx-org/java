# JSD Binding Generator

## Abstract

This document specifies the <ins>JSD Binding Generator</ins>, which offers facilities for generating Java binding classes from a JSD schema.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;</samp>2 [Java Bindings][#javabindings]<br>
<samp>&nbsp;&nbsp;</samp>3 [`Generator`][#generator]<br>
<samp>&nbsp;&nbsp;</samp>4 [`Converter`][#converter]<br>

## 1 Introduction

This document presents the functionality of the <ins>JSD Binding Generator</ins>. It also contains a directory of links to these related resources.

The <ins>JSD Binding Generator</ins> consumes a JSD schema, and produces Java classes in the form of `.java` files. The generated classes have code that relies on the <ins>JSON/Java Binding API</ins> to achieve binding between JSON documents conforming to a JSD schema, and Java object represetations of these documents.

## 2 Java Bindings

The <ins>JSD Binding Generator</ins> generates Java binding classes. The generated classes contain the full scope of specification of the JSD schema from which they are generated. This means that the generated classes can be converted back to the JSD from which they were created, and the normative scope of the schema will be preserved.

A distinction has to be made between "normative scope" and "non-normative scope". When referring to a JSD schema, the term "normative scope" represents the structural scope, which isolates the part of the schema document that defines validation criteria for facets of JSON documents. The term "non-normative scope" includes the information in the JSD schema that does not have significance unto the validation for facets of JSON documents. An example of a "non-normative scope" is the name of a `<string name="someName" ...>` type as the root element of the schema. The <ins>JSD Binding Generator</ins> discards this information when creating the Java binding classes.

## 3 `Generator`

The `Generator` is a utility class that can be used on the CLI to generate Java binding classes from a JSD schema. The `Generator` class has the following usage specification:

```bash
Usage: Generator [OPTIONS] <-d DEST_DIR> <SCHEMA_FILE>

Mandatory arguments:
  -d <destDir>       Specify the destination directory.

Optional arguments:
  --prefix <PREFIX>  Package prefix for generated classes.

Supported SCHEMA_FILE formats:
                 <JSD|JSDX>
```

## 4 `Converter`

The `Converter` is a utility class that can be used on the CLI to convert JSD files to JSDX, and vice versa. The `Converter` class has the following usage specification:

```bash
Usage: Converter <SCHEMA_IN> [SCHEMA_OUT]

Supported SCHEMA_IN|OUT formats:
                 <JSD|JSDX>
```

If a `SCHEMA_OUT` argument is not provided, the `Converter` will output the converted content to stdout.

[#introduction]: #1-introduction
[#javabindings]: #2-java-bindings
[#generator]: #3-generator
[#converter]: #4-converter