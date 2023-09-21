# JSONx Integration for JAX-RS

> **JSON Schema for the enterprise**

[![Build Status](https://github.com/jsonx-org/java/actions/workflows/build.yml/badge.svg)](https://github.com/jsonx-org/java/actions/workflows/build.yml)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/rs.svg)](https://www.javadoc.io/doc/org.jsonx/rs)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/rs.svg)](https://mvnrepository.com/artifact/org.jsonx/rs)
![Snapshot Version](https://img.shields.io/nexus/s/org.jsonx/rs?label=maven-snapshot&server=https%3A%2F%2Foss.sonatype.org)

## Abstract

This document specifies the <ins>JSONx Integration for JAX-RS</ins>, which offers facilities for reading and writing JSON documents from a JAX-RS runtime via the [JSONx Binding API][binding].

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [<ins>Introduction</ins>][#introduction]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>1.1 [Conventions Used in This Document][#conventions]<br>
<samp>&nbsp;&nbsp;</samp>2 [<ins>Purpose</ins>][#purpose]<br>
<samp>&nbsp;&nbsp;</samp>3 [<ins>Requirements</ins>][#requirements]<br>
<samp>&nbsp;&nbsp;</samp>4 [<ins>Getting Started</ins>](#4-getting-started)<br>
<samp>&nbsp;&nbsp;</samp>5 [<ins>Specification</ins>][#specification]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.1 [`JxObjectProvider`][#jxobjectprovider]<br>
<samp>&nbsp;&nbsp;&nbsp;&nbsp;</samp>5.2 [`BadRequestExceptionMapper`][#badrequestexceptionmapper]<br>
<samp>&nbsp;&nbsp;</samp>6 [<ins>Usage</ins>][#usage]<br>
<samp>&nbsp;&nbsp;</samp>7 [<ins>Contributing</ins>](#7-contributing)<br>
<samp>&nbsp;&nbsp;</samp>8 [<ins>Special Thanks</ins>](#8-special-thanks)<br>
<samp>&nbsp;&nbsp;</samp>9 [<ins>License</ins>](#9-license)

## <b>1</b> <ins>Introduction</ins>

This document sets out the structural part of the <ins>JSONx Integration for JAX-RS</ins>. It also contains a directory of links to related resources.

The <ins>JSONx Integration for JAX-RS</ins> is implemented to the specification of the JAX-RS API. <ins>JSONx Integration for JAX-RS</ins> implements the `MessageBodyReader` and `MessageBodyWriter` interfaces in `JxObjectProvider` to integrate with JAX-RS server runtimes.

### <b>1.1</b> Conventions Used in This Document

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## <b>2</b> <ins>Purpose</ins>

Provide <ins>JSONx Integration for JAX-RS</ins> for parsing and marshaling Java object instances of binding classes in a JAX-RS runtime.

## <b>3</b> <ins>Requirements</ins>

1. The <ins>JSONx Integration for JAX-RS</ins> MUST support validation of JSON upon the consumption and production of documents in a JAX-RS runtime.

1. The <ins>JSONx Integration for JAX-RS</ins> MUST support any JAX-RS application that implements the facets relevant to parsing and marshaling of entity object, as defined in the [JAX-RS 2.0 Specification](https://download.oracle.com/otn-pub/jcp/jaxrs-2_0-fr-eval-spec/jsr339-jaxrs-2.0-final-spec.pdf).

1. The <ins>JSONx Integration for JAX-RS</ins> MUST be automatic and free of any configuration that would couple an application to the <ins>JSONx Framework for Java</ins>.

## <b>4</b> <ins>Getting Started</ins>

The <ins>JSONx Integration for JAX-RS</ins> sub-project provides a `Provider` implementing the `MessageBodyReader` and `MessageBodyWriter` interfaces that can be registered with a JAX-RS runtime.

The following illustrates example usage.

&nbsp;&nbsp;1.&nbsp;Create `account.jsd` or `account.jsdx` in `src/main/resources/`.

<!-- tabs:start -->

###### **JSD**

```json
{
  "jx:ns": "http://www.jsonx.org/schema-0.4.jsd",
  "jx:schemaLocation": "http://www.jsonx.org/schema-0.4.jsd http://www.jsonx.org/schema-0.4.jsd",
  "id": { "jx:type": "string", "pattern": "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}" },
  "email": { "jx:type": "string", "pattern": "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}" },
  "sha256": {"jx:type": "string", "pattern": "[0-9a-f]{64}" },
  "ids": { "jx:type": "array", "elements": [{ "jx:type": "reference", "type": "id" }] },
  "credentials": { "jx:type": "object", "properties": {
    "email": { "jx:type": "reference", "type": "email", "nullable": false },
    "password": { "jx:type": "reference", "type": "sha256", "use": "optional", "nullable": false }}
  },
  "account": { "jx:type": "object", "extends": "credentials", "properties": {
    "id": { "jx:type": "reference", "type": "id", "use": "optional", "nullable": false },
    "firstName": { "jx:type": "string", "nullable": false },
    "lastName": { "jx:type": "string", "nullable": false }}
  },
  "accounts": { "jx:type": "array", "elements": [{ "jx:type": "reference", "type": "account" }]}
}
```

###### **JSDx**

```xml
<schema
  xmlns="http://www.jsonx.org/schema-0.4.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/schema-0.4.xsd http://www.jsonx.org/schema.xsd">

  <string name="id" pattern="[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"/>
  <string name="email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}"/>
  <string name="sha256" pattern="[0-9a-f]{64}"/>
  
  <array name="ids">
    <reference type="id"/>
  </array>

  <object name="credentials">
    <property name="email" xsi:type="reference" type="email" nullable="false"/>
    <property name="password" xsi:type="reference" type="sha256" use="optional" nullable="false"/>
  </object>

  <object name="account" extends="credentials">
    <property name="id" xsi:type="reference" type="id" nullable="false" use="optional"/>
    <property name="firstName" xsi:type="string" nullable="false"/>
    <property name="lastName" xsi:type="string" nullable="false"/>
  </object>
  <array name="accounts">
    <reference type="account"/>
  </array>

</schema>
```

<!-- tabs:end -->

<sub>_**Note:** You can use the [Converter][#converter] utility to automatically convert between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

&nbsp;&nbsp;2.&nbsp;Add the [`org.jsonx:jsonx-maven-plugin`][jsonx-maven-plugin] to the POM.

```xml
<plugin>
  <groupId>org.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.4.0</version>
  <executions>
    <execution>
      <goals>
        <goal>generate</goal>
      </goals>
      <configuration>
        <destDir>${project.build.directory}/generated-sources/jsonx</destDir>
        <prefix>com.example.jsonx.</prefix>
        <schemas>
          <schema>src/main/resources/account.jsonx</schema>
        </schemas>
      </configuration>
    </execution>
  </executions>
</plugin>
```

&nbsp;&nbsp;3.&nbsp;Upon successful execution of the [`jsonx-maven-plugin`][jsonx-maven-plugin] plugin, Java class files will be generated in `generated-sources/jsonx`. Add this path to your Build Paths in your IDE to integrate into your project.

The generated classes can be instantiated as any other Java objects. They are strongly typed, and will guide you in proper construction of a JSON message. The following APIs can be used for parsing and marshalling <ins>JSONx</ins> to and from JSON:

To <ins>parse</ins> JSON to <ins>JSONx</ins> Bindings:

```java
String json = "{\"email\":\"john@doe\",\"password\":\"066b91577bc547e21aa329c74d74b0e53e29534d4cc0ad455abba050121a9557\"}";
Credentials credentials = JxDecoder.parseObject(Credentials.class, json);
```

To <ins>marshal</ins> <ins>JSONx</ins> Bindings to JSON:

```java
String json2 = JxEncoder.get().marshal(credentials);
assertEquals(json, json2);
```

&nbsp;&nbsp;4.&nbsp;Next, register the `JxObjectProvider` provider in the JAX-RS appilcation singletons, and implement the `AccountService`:

```java
public class MyApplication extends javax.ws.rs.core.Application {
  @Override
  public Set<Object> getSingletons() {
    return Collections.singleton(new JxObjectProvider(JxEncoder._2));
  }
}

@Path("account")
@RolesAllowed("registered")
public class AccountService {
  @GET
  @Produces("application/vnd.example.v1+json")
  public Account get(@Context SecurityContext securityContext) {
    Account account = new Account();
    ...
    return account;
  }

  @POST
  @Consumes("application/vnd.example.v1+json")
  public void post(@Context SecurityContext securityContext, Account account) {
    ...
  }
}
```

## <b>5</b> <ins>Specification</ins>

### <b>5.1</b> `JxObjectProvider`

A JAX-RS `Provider` that implements `MessageBodyReader` and `MessageBodyWriter` support for reading and writing JSON documents with the JSONx API.

### <b>5.2</b> `BadRequestExceptionMapper`

A JAX-RS `Provider` that implements an `ExceptionMapper` to present a JSON error body in case of a `BadRequestException`.

## <b>6</b> <ins>Usage</ins>

The JAX-RS API requires `Provider`s to be declared as either "singleton" instances, or by providing their class names for per-requests instantiations. The following example illustrates how to specify the `JxObjectProvider` and `BadRequestExceptionMapper` as singleton instances.

```java
@ApplicationPath("/")
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

## <b>7</b> <ins>Contributing</ins>

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <b>8</b> <ins>Special Thanks</ins>

[![Java Profiler](https://www.ej-technologies.com/images/product_banners/jprofiler_small.png)](https://www.ej-technologies.com/products/jprofiler/overview.html)
<br><sub>_Special thanks to [EJ Technologies](https://www.ej-technologies.com/) for providing their award winning Java Profiler ([JProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html)) for development of the JSONx Framework._</sub>

## <b>9</b> <ins>License</ins>

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#introduction]: #1-introduction
[#conventions]: #11-conventions-used-in-this-document
[#purpose]: #2-purpose
[#requirements]: #3-requirements
[#specification]: #5-specification
[#jxobjectprovider]: #51-jxobjectprovider
[#badrequestexceptionmapper]: #52-badrequestexceptionmapper
[#usage]: #6-usage

[binding]: ../binding