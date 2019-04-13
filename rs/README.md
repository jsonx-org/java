# JAX-RS Integration for JSONX

## Abstract

This document specifies the <ins>JAX-RS Integration for JSONX</ins>, which offers facilities for reading and writing JSON documents from a JAX-RS runtime via the [JSON/Java Binding API][api].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Introduction][#introduction]<br>
<samp>&nbsp;&nbsp;</samp>2 [`JxObjectProvider`][#jxobjectprovider]<br>
<samp>&nbsp;&nbsp;</samp>3 [`BadRequestExceptionMapper`][#badrequestexceptionmapper]<br>
<samp>&nbsp;&nbsp;</samp>4 [Usage][#usage]

## 1 Introduction

This document sets out the structural part of the <ins>JAX-RS Integration for JSONX</ins>. It also contains a directory of links to these related resources.

<ins>JAX-RS Integration for JSONX</ins> is implemented to the specification of the JAX-RS API. <ins>JAX-RS Integration for JSONX</ins> implements the `MessageBodyReader` and `MessageBodyWriter` interfaces in `JxObjectProvider` to integrate with JAX-RS server runtimes.

## 2 `JxObjectProvider`

A JAX-RS `Provider` that implements `MessageBodyReader` and `MessageBodyWriter` support for reading and writing JSON documents with the JSONX API.

## 3 `BadRequestExceptionMapper`

A JAX-RS `Provider` that implements an `ExceptionMapper` to present a JSON error body in case of a `BadRequestException`.

## 4 Usage

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

[#introduction]: #1-introduction
[#jxobjectprovider]: #2-jxobjectprovider
[#badrequestexceptionmapper]: #3-badrequestexceptionmapper
[#usage]: #4-usage

[api]: ../binding