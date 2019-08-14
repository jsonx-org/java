# JSONx Integration for JAX-RS

> **JSON Schema for the enterprise**

[![Build Status](https://travis-ci.org/jsonx-org/java.svg?EKkC4CBk)](https://travis-ci.org/jsonx-org/java)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg?EKkC4CBk)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/rs.svg?EKkC4CBk)](https://www.javadoc.io/doc/org.jsonx/rs)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/rs.svg?EKkC4CBk)](https://mvnrepository.com/artifact/org.jsonx/rs)

## Abstract

This document specifies the <ins>JSONx Integration for JAX-RS</ins>, which offers facilities for reading and writing JSON documents from a JAX-RS runtime via the [JSONx Binding API][binding].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [Purpose][#purpose]<br>
<samp>&nbsp;&nbsp;</samp>3 [Requirements][#requirements]<br>
<samp>&nbsp;&nbsp;</samp>4 [Specification][#specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.1 [`JxObjectProvider`][#jxobjectprovider]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>4.2 [`BadRequestExceptionMapper`][#badrequestexceptionmapper]<br>
<samp>&nbsp;&nbsp;</samp>5 [Usage][#usage]<br>
<samp>&nbsp;&nbsp;</samp>6 [Contributing](#6-contributing)<br>
<samp>&nbsp;&nbsp;</samp>7 [License](#7-license)

## <b>1</b> Introduction

This document sets out the structural part of the <ins>JSONx Integration for JAX-RS</ins>. It also contains a directory of links to these related resources.

The <ins>JSONx Integration for JAX-RS</ins> is implemented to the specification of the JAX-RS API. <ins>JSONx Integration for JAX-RS</ins> implements the `MessageBodyReader` and `MessageBodyWriter` interfaces in `JxObjectProvider` to integrate with JAX-RS server runtimes.

### <b>1.1</b> Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

### <b>2</b> Purpose

Provide <ins>JSONx Integration for JAX-RS</ins> for parsing and marshaling Java object instances of binding classes in a JAX-RS runtime.

### <b>3</b> Requirements

1. The <ins>JSONx Integration for JAX-RS</ins> MUST support validation of JSON upon the consumption and production of documents in a JAX-RS runtime.

1. The <ins>JSONx Integration for JAX-RS</ins> MUST support any JAX-RS application that implements the facets relevant to parsing and marshaling of entity object, as defined in the [JAX-RS 2.0 Specification](https://download.oracle.com/otn-pub/jcp/jaxrs-2_0-fr-eval-spec/jsr339-jaxrs-2.0-final-spec.pdf).

1. The <ins>JSONx Integration for JAX-RS</ins> MUST be automatic and free of any configuration that would couple an application to the <ins>JSONx Framework for Java</ins>.

## <b>4</b> Specification

### <b>4.1</b> `JxObjectProvider`

A JAX-RS `Provider` that implements `MessageBodyReader` and `MessageBodyWriter` support for reading and writing JSON documents with the JSONx API.

### <b>4.2</b> `BadRequestExceptionMapper`

A JAX-RS `Provider` that implements an `ExceptionMapper` to present a JSON error body in case of a `BadRequestException`.

## <b>5</b> Usage

The JAX-RS API requires `Provider`s to be decalred as either "singleton" instances, or by providing their class names for per-requests instantiations. The following example illustrates how to specify the `JxObjectProvider` and `BadRequestExceptionMapper` as singleton instances.

```java
@ApplicationPath("/*")
public class MyApplication extends javax.ws.rs.core.Application {
  @Override
  public Set<Object> getSingletons() {
    Set<Object> singletons = new HashSet<>();
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

## <b>6</b> Contributing

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <b>7</b> License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#introduction]: #1-introduction
[#conventions]: #11-conventions-used-in-this-document
[#purpose]: #2-purpose
[#requirements]: #3-requirements
[#specification]: #4-specification
[#jxobjectprovider]: #41-jxobjectprovider
[#badrequestexceptionmapper]: #42-badrequestexceptionmapper
[#usage]: #5-usage

[binding]: ../binding