# Samples for JSONx Framework

[![Build Status](https://travis-ci.org/jsonx-org/java.svg?EKkC4CBk)](https://travis-ci.org/jsonx-org/java)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg?EKkC4CBk)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/rs.svg?EKkC4CBk)](https://www.javadoc.io/doc/org.jsonx/rs)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/rs.svg?EKkC4CBk)](https://mvnrepository.com/artifact/org.jsonx/rs)

## Abstract

This document presents sample applications for the <ins>JSONx Framework</ins>.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Samples](#1-samples)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Invoice](#11-invoice)<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.2 [Consumer Driven Contracts](#12-consumer-driven-contracts)<br>
<samp>&nbsp;&nbsp;</samp>2 [Contributing](#2-contributing)<br>
<samp>&nbsp;&nbsp;</samp>3 [License](#3-license)<br>

## 1 Samples

The following are sample applications for the <ins>JSONx Framework</ins>.

### 1.1 Invoice

This sample showcases the familiar **invoice** use-case, and introduces the following JSONx technologies:
1. [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd].
1. [<ins>JSONx Binding API</ins>][#binding].

_For the application code, see **[<ins>Invoice</ins>](invoice)**._

### 1.2 Consumer Driven Contracts

The <ins>JSONx Framework</ins> was created specifically for [<ins>Consumer Driven Contracts</ins>][cdc]. With the [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd], one can create a <ins>Consumer Driven Contract (CDC)</ins> with an evolution model based on schema versioning. The <ins>JSD</ins> can be used by producers and consumers to validate documents in a communication protocol.

_For the application code, see **[<ins>Consumer Driven Contracts</ins>](cdc)**._

## 2 Contributing

Pull requests are welcome. For major changes, please [open an issue](../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## 3 License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#binding]: ../../#4-jsonjava-binding-api
[#jsd]: ../../#3-json-schema-definition-language
[cdc]: http://martinfowler.com/articles/consumerDrivenContracts.html