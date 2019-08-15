# JSONx Sample: Bank Message

> **JSON Schema for the enterprise**

[![Build Status](https://travis-ci.org/jsonx-org/java.svg?EKkC4CBk)](https://travis-ci.org/jsonx-org/java)
[![Coverage Status](https://coveralls.io/repos/github/jsonx-org/java/badge.svg?EKkC4CBk)](https://coveralls.io/github/jsonx-org/java)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/rs.svg?EKkC4CBk)](https://www.javadoc.io/doc/org.jsonx/rs)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/rs.svg?EKkC4CBk)](https://mvnrepository.com/artifact/org.jsonx/rs)

## Abstract

This document presents the <ins>Bank Message</ins> sample application.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Bank](#1-bank)<br>
<samp>&nbsp;&nbsp;</samp>2 [Contributing](#2-contributing)<br>
<samp>&nbsp;&nbsp;</samp>3 [License](#3-license)<br>

### <b>1</b> Bank

This sample is an introduction to the following JSONx technologies:
1. [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd].
1. [<ins>JSONx Binding API</ins>][#binding].

This example presents a simple schema that represents a message with a bank account number. The message can have one of three account number types: **SWIFT**, **IBAN**, and **ACH**. To help banking systems reduce software risk, organizations governing the **SWIFT**, **IBAN** and **ACH** code standards define simple test functions to determine the logical correctness of identifiers.

1. 𝒗<sub>SWIFT</sub>(id)   = regex<sub>id</sub>( `"[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?"` )
1. 𝒗<sub>IBAN</sub>   (id)   = regex<sub>id</sub>( `"[A-Z]{2}\d{2} ?\d{4} ?\d{4} ?\d{4} ?\d{4} ?\d{0,2}"` )
1. 𝒗<sub>ACH</sub>(id, rt) = regex<sub>id</sub>( `"\w{1,17}"` ) × regex<sub>rt</sub>( `"\d{9}"` )

The following schema presents a message declaration for each type of identifier with regex constraints.

1. Create a <ins>JSD</ins> in `src/test/resources/message.jsd`:

   ```json
   {
     "doc": "Schema describing bank transactions",
     "jx:ns": "http://www.jsonx.org/schema-0.3.jsd",
     "jx:schemaLocation": "http://www.jsonx.org/schema-0.3.jsd http://www.jsonx.org/schema.jsd",
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

1. **(Alternatively)** Create a <ins>JSDx</ins> in `src/test/resources/message.jsdx`:

   <sub>_**Note:** You can use the [Converter][#converter] utility to automatically convert between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

   ```xml
   <schema
     doc="Schema describing bank transactions"
     xmlns="http://www.jsonx.org/schema-0.3.xsd"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://www.jsonx.org/schema-0.3.xsd http://www.jsonx.org/schema.xsd">
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

1. With the `message.jsd` or `message.jsdx`, you can use the [`jsonx-maven-plugin`][jsonx-maven-plugin] to automatically generate the Java class files. In your POM, add:

   ```xml
   <plugin>
     <groupId>org.jsonx</groupId>
     <artifactId>jsonx-maven-plugin</artifactId>
     <version>0.3.1</version>
     <executions>
       <execution>
         <goals>
           <goal>generate</goal>
         </goals>
         <phase>generate-test-sources</phase>
         <configuration>
           <destDir>${project.build.directory}/generated-test-sources/jsonx</destDir>
           <prefix>com.example.bank.</prefix>
           <schemas>
             <schema>src/test/resources/message.jsd</schema> <!-- or message.jsdx -->
           </schemas>
         </configuration>
       </execution>
     </executions>
   </plugin>
   ```

1. **(Alternatively)** Create the Java class files by hand:

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

1. You can use these classes to represent `Message`s of type `Swift`, `Iban`, and `Ach`.

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

1. You can now <ins>marshal</ins> the Java objects to JSON:

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

1. You can also <ins>parse</ins> the JSON into Java objects:

   ```java
   for (Message message : messages) {
     String json = JxEncoder._2.marshal(message);
     System.out.println(json);

     Message json2 = JxDecoder.parseObject(message.getClass(), new JsonReader(new StringReader(json)));
     assertEquals(json, json2);
   }
   ```

_The code included in this module implements this example._

## <b>2</b> Contributing

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## <b>3</b> License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#binding]: ../../../../#4-jsonx-binding-api
[#converter]: ../../../../#532-converter
[#jsd]: ../../../../#3-json-schema-definition-language
[jsonx-maven-plugin]: ../../jsonx-maven-plugin/