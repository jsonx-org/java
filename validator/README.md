# JSONx Validator

> **JSON Schema for the enterprise**

[![Build Status](https://github.com/jsonx-org/java/actions/workflows/build.yml/badge.svg)](https://github.com/jsonx-org/java/actions/workflows/build.yml)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/validator.svg)](https://www.javadoc.io/doc/org.jsonx/validator)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/validator.svg)](https://mvnrepository.com/artifact/org.jsonx/validator)
![Snapshot Version](https://img.shields.io/nexus/s/org.jsonx/validator?label=maven-snapshot&server=https%3A%2F%2Foss.sonatype.org)

## Abstract

This document specifies the <ins>JSONx Validator</ins>, which offers facilities for validating JSON documents agains a [JSD schema][schema].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [<ins>Introduction</ins>](#1-introduction)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document](#11-conventions-used-in-this-document)<br>
<samp>&nbsp;&nbsp;</samp>2 [<ins>Purpose</ins>](#2-purpose)<br>
<samp>&nbsp;&nbsp;</samp>3 [<ins>Requirements</ins>](#3-requirements)<br>
<samp>&nbsp;&nbsp;</samp>4 [<ins>Getting Started</ins>](#4-getting-started)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [Valid Case](#41-valid-case)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [Invalid Case](#42-invalid-case)<br>
<samp>&nbsp;&nbsp;</samp>5 [<ins>Specification</ins>](#5-specification)<br>
<samp>&nbsp;&nbsp;</samp>6 [<ins>Contributing</ins>](#6-contributing)<br>
<samp>&nbsp;&nbsp;</samp>7 [<ins>Special Thanks</ins>](#7-special-thanks)<br>
<samp>&nbsp;&nbsp;</samp>8 [<ins>License</ins>](#8-license)

## <b>1</b> <ins>Introduction</ins>

This document presents the functionality of the <ins>JSONx Validator</ins>. It also contains a directory of links to these related resources.

The <ins>JSONx Validator</ins> consumes a JSD schema and a JSON document, and returns successfully if the specified documents are valid against the provided schema, or fails with an exception specifying the validation error if the specified document is invalid against the provided schema. The <ins>JSONx Validator</ins> utilizes the <ins>JSONx Binding Generator</ins> to perform its validation.

### <b>1.1</b> Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## <b>2</b> <ins>Purpose</ins>

Provide a <ins>validator</ins> utility for automatic validation of JSON documents against a <ins>schema document</ins>.

## <b>3</b> <ins>Requirements</ins>

1. The <ins>validator</ins> MUST be able to consume a <ins>JSON document</ins>, and validate its contents agains a provided <ins>schema document</ins>.

1. The <ins>validator</ins> MUST return successfully if a <ins>JSON document</ins> conforms to the provided <ins>schema document</ins>.

1. The <ins>validator</ins> MUST throw an exception specifying the validation error if a <ins>JSON document</ins> does not conform to the provided <ins>schema document</ins>.

## <b>4</b> <ins>Getting Started</ins>

The <ins>JSONx Validator</ins> provides convenience utilities for validating a <ins>JSON document</ins> agains a <ins>schema document</ins>. The following illustrates example usage of the `Validator`.

### <b>4.1</b> Valid Case

The following example uses the `Validator` to validate a <i>valid</i> <ins>JSON document</ins> against a <ins>schema document</ins>.

```bash
$ java -cp ... org.jsonx.Validator src/main/resources/example.jsd src/main/resources/valid.json
$ echo $?
0
```

### <b>4.2</b> Invalid Case

The following example uses the `Validator` to validate an <i>invalid</i> <ins>JSON document</ins> against a <ins>schema document</ins>.

```bash
$ java -cp ... org.jsonx.Validator src/main/resources/example.jsd src/main/resources/invalid.json
Invalid content was found in empty array: @org.jsonx.StringElement(id=0, pattern="[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"): Content is not complete
$ echo $?
1
```

## <b>5</b> <ins>Specification</ins>

The `Validator` is a utility class that can be used on the CLI to validate a <ins>JSON document</ins> against a <ins>schema document</ins>. The `Validator` class has the following usage specification:

```
Usage: Usage: Validator <SCHEMA> <JSON...>
Supported SCHEMA formats:
                 <JSD|JSDx>
```

## <b>6</b> <ins>Contributing</ins>

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <b>7</b> <ins>Special Thanks</ins>

[![Java Profiler](https://www.ej-technologies.com/images/product_banners/jprofiler_small.png)](https://www.ej-technologies.com/products/jprofiler/overview.html)
<br><sub>_Special thanks to [EJ Technologies](https://www.ej-technologies.com/) for providing their award winning Java Profiler ([JProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html)) for development of the JSONx Framework._</sub>

## <b>8</b> <ins>License</ins>

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[schema]: ../../../../schema/