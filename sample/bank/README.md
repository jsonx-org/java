# JSONx Sample: Bank Message

> **JSON Schema for the enterprise**

[![Build Status](https://github.com/jsonx-org/java/actions/workflows/build.yml/badge.svg)](https://github.com/jsonx-org/java/actions/workflows/build.yml)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/rs.svg)](https://www.javadoc.io/doc/org.jsonx/rs)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/rs.svg)](https://mvnrepository.com/artifact/org.jsonx/rs)
![Snapshot Version](https://img.shields.io/nexus/s/org.jsonx/rs?label=maven-snapshot&server=https%3A%2F%2Foss.sonatype.org)

## Abstract

This document presents the <ins>Bank Message</ins> sample application.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [<ins>Bank</ins>](#1-bank)<br>
<samp>&nbsp;&nbsp;</samp>2 [<ins>Contributing</ins>](#2-contributing)<br>
<samp>&nbsp;&nbsp;</samp>3 [<ins>Special Thanks</ins>](#3-special-thanks)<br>
<samp>&nbsp;&nbsp;</samp>4 [<ins>License</ins>](#4-license)<br>

### <b>1</b> <ins>Bank</ins>

This sample is an introduction to the following JSONx technologies:
1. [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd].
1. [<ins>JSONx Runtime API</ins>][#runtime-api].

This example presents a simple schema that represents a message with a bank account number. The message can have one of three account number types: **SWIFT**, **IBAN**, and **ACH**. To help banking systems reduce software risk, organizations governing the **SWIFT**, **IBAN** and **ACH** code standards define simple test functions to determine the logical correctness of identifiers.

1. 𝒗<sub>SWIFT</sub>(id)   = regex<sub>id</sub>( `"[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?"` )
1. 𝒗<sub>IBAN</sub>   (id)   = regex<sub>id</sub>( `"[A-Z]{2}\d{2} ?\d{4} ?\d{4} ?\d{4} ?\d{4} ?\d{0,2}"` )
1. 𝒗<sub>ACH</sub>(id, rt) = regex<sub>id</sub>( `"\w{1,17}"` ) × regex<sub>rt</sub>( `"\d{9}"` )

The following schema presents a message declaration for each type of identifier with regex constraints.

&nbsp;&nbsp;1.&nbsp;Create `message.jsd` or `message.jsdx` in `src/test/resources/`:

<!-- tabs:start -->

###### **JSD**

```json
{
  "doc": "Schema describing bank transactions",
  "jx:ns": "http://www.jsonx.org/schema-0.4.jsd",
  "jx:schemaLocation": "http://www.jsonx.org/schema-0.4.jsd http://www.jsonx.org/schema.jsd",
  "message": { "jx:type": "object", "abstract": true },
  "swift": {
    "jx:type": "object", "extends": "message", "properties": {
      "type": { "jx:type": "string", "pattern": "swift", "nullable": false },
      "code": { "jx:type": "string", "pattern": "[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?", "nullable": false }
    }
  },
  "iban": {
    "jx:type": "object", "extends": "message", "properties": {
      "type": { "jx:type": "string", "pattern": "iban", "nullable": false },
      "code": { "jx:type": "string", "pattern": "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{0,2}", "nullable": false }
    }
  },
  "ach": {
    "jx:type": "object", "extends": "message", "properties": {
      "type": { "jx:type": "string", "pattern": "ach", "nullable": false },
      "code": { "jx:type": "string", "pattern": "\\w{1,17}", "nullable": false },
      "routing": { "jx:type": "string", "pattern": "\\d{9}", "nullable": false }
    }
  }
}
```

###### **JSDx**

```xml
<schema
  doc="Schema describing bank transactions"
  xmlns="http://www.jsonx.org/schema-0.4.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.jsonx.org/schema-0.4.xsd http://www.jsonx.org/schema.xsd">
  <object name="message" abstract="true"/>
  <object name="swift" extends="message">
    <property name="type" xsi:type="string" pattern="swift" nullable="false"/>
    <property name="code" xsi:type="string" pattern="[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?" nullable="false"/>
  </object>
  <object name="iban" extends="message">
    <property name="type" xsi:type="string" pattern="iban" nullable="false"/>
    <property name="code" xsi:type="string" pattern="[A-Z]{2}\d{2} ?\d{4} ?\d{4} ?\d{4} ?\d{4} ?\d{0,2}" nullable="false"/>
  </object>
  <object name="ach" extends="message">
    <property name="type" xsi:type="string" pattern="ach" nullable="false"/>
    <property name="code" xsi:type="string" pattern="\w{1,17}" nullable="false"/>
    <property name="routing" xsi:type="string" pattern="\d{9}" nullable="false"/>
  </object>
</schema>
```

<!-- tabs:end -->

<sub>_**Note:** You can use the [Converter][#converter] utility to automatically convert between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

&nbsp;&nbsp;2.&nbsp;With the `message.jsd` or `message.jsdx`, you can use the [`jsonx-maven-plugin`][jsonx-maven-plugin] to automatically generate the Java class files. In your POM, add:

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
      <phase>generate-test-sources</phase>
      <configuration>
        <destDir>${project.build.directory}/generated-test-sources/jsonx</destDir>
        <namespacePackages>
          <namespacePackage package="com.example.bank."/>
        </namespacePackages>
        <schemas>
          <schema>src/test/resources/message.jsd</schema> <!-- or message.jsdx -->
        </schemas>
      </configuration>
    </execution>
  </executions>
</plugin>
```

&nbsp;&nbsp;3.&nbsp;**(Alternatively)** Create the Java class files by hand:

<sup>_**Note:** Set-ters and get-ters have been replaced with public fields for conciseness._</sup>

```java
import org.jsonx.*;

public abstract class Message implements JxObject {
}
```

```java
import org.jsonx.*;

public class Swift extends Message {
  @StringProperty(pattern="swift", nullable=false)
  public String type;

  @StringProperty(pattern="[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?", nullable=false)
  public String code;
}
```

```java
import org.jsonx.*;

public class Iban extends Message {
  @StringProperty(pattern="iban", nullable=false)
  public String type;

  @StringProperty(pattern="[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{0,2}", nullable=false)
  public String code;
}
```

```java
import org.jsonx.*;

public class Ach extends Message {
  @StringProperty(pattern="ach", nullable=false)
  public String type;

  @StringProperty(pattern="\\w{1,17}", nullable=false)
  public String code;

  @StringProperty(pattern="\\d{9}", nullable=false)
  public String routing;
}
```

&nbsp;&nbsp;4.&nbsp;You can use these classes to represent `Message`s of type `Swift`, `Iban`, and `Ach`.

```java
List<Message> messages = new ArrayList<>();

Swift swift = new Swift();
swift.setType("swift");
swift.setCode("CTBAAU2S");
messages.add(swift);

Iban iban = new Iban();
iban.setType("iban");
iban.setCode("DE91 1000 0000 0123 4567 89");
messages.add(iban);

Ach ach = new Ach();
ach.setType("ach");
ach.setCode("379272957729384");
ach.setRouting("021000021");
messages.add(ach);
```

&nbsp;&nbsp;5.&nbsp;You can now <ins>marshal</ins> the Java objects to JSON:

```java
for (Message message : messages) {
  String json = JxEncoder._2.marshal(message);
  System.out.println(json);
}
```

... will produce:

```json
{
  "type": "swift",
  "code": "CTBAAU2S"
}
{
  "type": "iban",
  "code": "DE91 1000 0000 0123 4567 89"
}
{
  "type": "ach",
  "code": "379272957729384",
  "routing": "021000021"
}
```

&nbsp;&nbsp;6.&nbsp;You can also <ins>parse</ins> the JSON into Java objects:

```java
for (Message message : messages) {
  String json = JxEncoder._2.marshal(message);
  System.out.println(json);

  Message json2 = JxDecoder.parseObject(message.getClass(), json);
  assertEquals(json, json2);
}
```

_The code included in this module implements this example._

## <b>2</b> <ins>Contributing</ins>

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <b>3</b> <ins>Special Thanks</ins>

[![Java Profiler](https://www.ej-technologies.com/images/product_banners/jprofiler_small.png)](https://www.ej-technologies.com/products/jprofiler/overview.html)
<br><sub>_Special thanks to [EJ Technologies](https://www.ej-technologies.com/) for providing their award winning Java Profiler ([JProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html)) for development of the JSONx Framework._</sub>

## <b>4</b> <ins>License</ins>

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#runtime-api]: ../../../../#4-jsonx-runtime-api
[#converter]: ../../../../#532-converter
[#jsd]: ../../../../#3-json-schema-definition-language
[jsonx-maven-plugin]: ../../jsonx-maven-plugin/