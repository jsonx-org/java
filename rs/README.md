# JAX-RS Integration for JSONX

[![Build Status](https://travis-ci.org/openjax/jsonx.png)](https://travis-ci.org/openjax/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/openjax/jsonx/badge.svg)](https://coveralls.io/github/openjax/jsonx)
[![Javadocs](https://www.javadoc.io/badge/org.openjax.jsonx/rs.svg)](https://www.javadoc.io/doc/org.openjax.jsonx/rs)
[![Released Version](https://img.shields.io/maven-central/v/org.openjax.jsonx/rs.svg)](https://mvnrepository.com/artifact/org.openjax.jsonx/rs)

## Abstract

This document specifies the <ins>JAX-RS Integration for JSONX</ins>, which offers facilities for reading and writing JSON documents from a JAX-RS runtime via the [JSON/Java Binding API][api].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [Purpose][#purpose]<br>
<samp>&nbsp;&nbsp;</samp>3 [Requirements][#requirements]<br>
<samp>&nbsp;&nbsp;</samp>4 [Specification][#specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [`JxObjectProvider`][#jxobjectprovider]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [`BadRequestExceptionMapper`][#badrequestexceptionmapper]<br>
<samp>&nbsp;&nbsp;</samp>5 [Usage][#usage]

## 1 Introduction

This document sets out the structural part of the <ins>JAX-RS Integration for JSONX</ins>. It also contains a directory of links to these related resources.

<ins>JAX-RS Integration for JSONX</ins> is implemented to the specification of the JAX-RS API. <ins>JAX-RS Integration for JSONX</ins> implements the `MessageBodyReader` and `MessageBodyWriter` interfaces in `JxObjectProvider` to integrate with JAX-RS server runtimes.

### 1.1 Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

## 2 Purpose

Provide an encoding of JSON documents in an analogous form that uses XML semantics, referred to as <ins>JSONX documents</ins>.

## 3 Requirements

1. The <ins>JSONX documents</ins> MUST be able to represent any and all legal JSON documents, as specified by [\[RFC2119\]](https://www.ietf.org/rfc/rfc2119.txt).

1. The <ins>JSONX documents</ins> MUST be translatable to JSON documents, and vice versa, preserving all normative and non-normative features of the original document.

1. The <ins>JSONX documents</ins> MUST provide meaningful and useful validation features via XSD validation.

## 4 Specification

### 4.1 `JxObjectProvider`

A JAX-RS `Provider` that implements `MessageBodyReader` and `MessageBodyWriter` support for reading and writing JSON documents with the JSONX API.

### 4.2 `BadRequestExceptionMapper`

A JAX-RS `Provider` that implements an `ExceptionMapper` to present a JSON error body in case of a `BadRequestException`.

## 5 Usage

The JAX-RS API requires `Provider`s to be decalred as either "singleton" instances, or by providing their class names for per-requests instantiations. The following example illustrates how to specify the `JxObjectProvider` and `BadRequestExceptionMapper` as singleton instances.

```java
@ApplicationPath("/*")
public class MyApplication extends javax.ws.rs.core.Application {
  @Override
  public Set<Object> getSingletons() {
    final Set<Object> singletons = new HashSet<>();
    singletons.add(new JxObjectProvider(JxEncoder._2));
    singletons.add(new BadRequestExceptionMapper());
    return singletons;
  }

  @Override
  public Set<Class<?>> getClasses() {
    return null;
  }
}
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#introduction]: #1-introduction
[#conventions]: #11-conventions-used-in-this-document
[#purpose]: #2-purpose
[#requirements]: #3-requirements
[#specification]: #4-specification
[#jxobjectprovider]: #41-jxobjectprovider
[#badrequestexceptionmapper]: #42-badrequestexceptionmapper
[#usage]: #5-usage

[api]: ../binding