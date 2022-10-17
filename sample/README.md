# Samples for JSONx Framework

[![Build Status](https://github.com/jsonx-org/java/actions/workflows/build.yml/badge.svg)](https://github.com/jsonx-org/java/actions/workflows/build.yml)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/rs.svg)](https://www.javadoc.io/doc/org.jsonx/rs)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/rs.svg)](https://mvnrepository.com/artifact/org.jsonx/rs)
![Snapshot Version](https://img.shields.io/nexus/s/org.jsonx/rs?label=maven-snapshot&server=https%3A%2F%2Foss.sonatype.org)

## Abstract

This document presents sample applications for the <ins>JSONx Framework</ins>.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [<ins>Samples</ins>](#1-samples)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Invoice](#11-invoice)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.2 [Bank](#12-bank)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.3 [Consumer Driven Contracts](#13-consumer-driven-contracts)<br>
<samp>&nbsp;&nbsp;</samp>2 [<ins>Contributing</ins>](#2-contributing)<br>
<samp>&nbsp;&nbsp;</samp>3 [<ins>Special Thanks</ins>](#3-special-thanks)<br>
<samp>&nbsp;&nbsp;</samp>4 [<ins>License</ins>](#4-license)<br>

## <b>1</b> <ins>Samples</ins>

The following are sample applications for the <ins>JSONx Framework</ins>.

### <b>1.1</b> Invoice

This sample showcases the familiar **invoice** use-case, and introduces the following JSONx technologies:
1. [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd].
1. [<ins>JSONx Binding API</ins>][#binding-api].

_For the application code, see **[<ins>Invoice</ins>](invoice)**._

### <b>1.2</b> Bank

This sample showcases JSON object inheritence in a use-case regarding bank account transactions, and introduces the following JSONx technologies:
1. [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd].
1. [<ins>JSONx Binding API</ins>][#binding-api].

_For the application code, see **[<ins>Bank</ins>](bank)**._

### <b>1.3</b> Consumer Driven Contracts

The <ins>JSONx Framework</ins> was created specifically for [<ins>Consumer Driven Contracts</ins><sup>❐</sup>][cdc]. With the [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd], one can create a <ins>Consumer Driven Contract (CDC)</ins> with an evolution model based on schema versioning. The <ins>JSD</ins> can be used by producers and consumers to validate documents in a communication protocol.

_For the application code, see **[<ins>Consumer Driven Contracts</ins>](cdc)**._

## <b>2</b> <ins>Contributing</ins>

Pull requests are welcome. For major changes, please [open an issue](../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <b>3</b> <ins>Special Thanks</ins>

[![Java Profiler](https://www.ej-technologies.com/images/product_banners/jprofiler_small.png)](https://www.ej-technologies.com/products/jprofiler/overview.html)
<br><sub>_Special thanks to [EJ Technologies](https://www.ej-technologies.com/) for providing their award winning Java Profiler ([JProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html)) for development of the JSONx Framework._</sub>

## <b>4</b> <ins>License</ins>

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#binding-api]: ../../#4-jsonx-binding-api
[#jsd]: ../../#3-json-schema-definition-language

[cdc]: http://martinfowler.com/articles/consumerDrivenContracts.html