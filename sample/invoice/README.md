# JSONx Sample: Invoice

[![Build Status](https://travis-ci.org/jsonxorg/jsonx.svg?EKkC4CBk)](https://travis-ci.org/jsonxorg/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/jsonxorg/jsonx/badge.svg?EKkC4CBk)](https://coveralls.io/github/jsonxorg/jsonx)
[![Javadocs](https://www.javadoc.io/badge/org.jsonx/rs.svg?EKkC4CBk)](https://www.javadoc.io/doc/org.jsonx/rs)
[![Released Version](https://img.shields.io/maven-central/v/org.jsonx/rs.svg?EKkC4CBk)](https://mvnrepository.com/artifact/org.jsonx/rs)

## Abstract

This document presents the <ins>Invoice</ins> sample application.

## Table of Contents

<samp>&nbsp;&nbsp;</samp>1 [Invoice](#1-invoice)<br>
<samp>&nbsp;&nbsp;</samp>2 [Contributing](#2-contributing)<br>
<samp>&nbsp;&nbsp;</samp>3 [License](#3-license)<br>

### 1 Invoice

This sample is an introduction to the following JSONx technologies:
1. [<ins>JSON Schema Definition Language (JSD)</ins>][#jsd].
1. [<ins>JSON/Java Binding API</ins>][#binding].

The following illustrates usage of the <ins>binding API</ins> with an example of an **invoice**.

1. Create a <ins>JSD</ins> in `src/main/resources/invoice.jsd`:

   ```json
   {
     "jx:ns": "http://www.jsonx.org/schema-0.2.3.jsd",
     "jx:schemaLocation": "http://www.jsonx.org/schema-0.2.3.jsd http://www.jsonx.org/schema-0.2.3.jsd",

     "positiveDecimal": { "jx:type": "number", "range": "[1,]" },
     "positiveInteger": { "jx:type": "number", "form": "integer", "range": "[1,]" },
     "date": { "jx:type": "string", "pattern": "-?\\d{4}-((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01])|(02-(0[1-9]|1\\d|2\\d))|((0[469]|11)-(0[1-9]|[12]\\d|30)))" },
     "nonEmptyString": { "jx:type": "string", "pattern": "\\S|\\S.*\\S" },
     "address": { "jx:type": "object", "properties": {
       "name": { "jx:type": "reference", "nullable": false, "type": "nonEmptyString" },
       "address": { "jx:type": "reference", "nullable": false, "type": "nonEmptyString" },
       "city": { "jx:type": "reference", "nullable": false, "type": "nonEmptyString" },
       "postalCode": { "jx:type": "reference", "nullable": false, "type": "nonEmptyString", "use": "optional" },
       "country": { "jx:type": "reference", "type": "nonEmptyString" } }
     },
     "invoice": { "jx:type": "object", "properties": {
       "number": { "jx:type": "reference", "type": "positiveInteger" },
       "date": { "jx:type": "reference", "type": "date" },
       "billingAddress": { "jx:type": "reference", "type": "address" },
       "shippingAddress": { "jx:type": "reference", "type": "address" },
       "billedItems": { "jx:type": "array", "nullable": false, "elements": [
         { "jx:type": "reference", "type": "item" } ] } }
     },
     "item": { "jx:type": "object", "properties": {
       "description": { "jx:type": "reference", "nullable": false, "type": "nonEmptyString" },
       "code": { "jx:type": "reference", "nullable": false, "type": "positiveInteger" },
       "quantity": { "jx:type": "reference", "nullable": false, "type": "positiveInteger" },
       "price": { "jx:type": "reference", "nullable": false, "type": "positiveDecimal" } }
     }
   }
   ```

1. **(Alternatively)** Create a <ins>JSDx</ins> in `src/main/resources/invoice.jsdx`:

   <sub>_**Note:** You can use the [Converter][#converter] utility to automatically convert between <ins>JSD</ins> and <ins>JSDx</ins>._</sub>

   ```xml
   <schema
     xmlns="http://www.jsonx.org/schema-0.2.3.xsd"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://www.jsonx.org/schema-0.2.3.xsd http://www.jsonx.org/schema-0.2.3.xsd">

     <number name="positiveDecimal" range="[1,]"/>
     <number name="positiveInteger" form="integer" range="[1,]"/>
     <string name="date" pattern="-?\d{4}-((0[13578]|1[02])-(0[1-9]|[12]\d|3[01])|(02-(0[1-9]|1\d|2\d))|((0[469]|11)-(0[1-9]|[12]\d|30)))"/>
     <string name="nonEmptyString" pattern="\S|\S.*\S"/>
     <object name="address">
       <property name="name" xsi:type="reference" type="nonEmptyString" nullable="false"/>
       <property name="address" xsi:type="reference" type="nonEmptyString" nullable="false"/>
       <property name="city" xsi:type="reference" type="nonEmptyString" nullable="false"/>
       <property name="postalCode" xsi:type="reference" type="nonEmptyString" nullable="false" use="optional"/>
       <property name="country" xsi:type="reference" type="nonEmptyString"/>
     </object>
     <object name="invoice">
       <property name="number" xsi:type="reference" type="positiveInteger"/>
       <property name="date" xsi:type="reference" type="date"/>
       <property name="billingAddress" xsi:type="reference" type="address"/>
       <property name="shippingAddress" xsi:type="reference" type="address"/>
       <property name="billedItems" xsi:type="array" nullable="false">
         <reference type="item"/>
       </property>
     </object>
     <object name="item">
       <property name="description" xsi:type="reference" type="nonEmptyString" nullable="false"/>
       <property name="code" xsi:type="reference" type="positiveInteger" nullable="false"/>
       <property name="quantity" xsi:type="reference" type="positiveInteger" nullable="false"/>
       <property name="price" xsi:type="reference" type="positiveDecimal" nullable="false"/>
     </object>

   </schema>
   ```

1. With the `invoice.jsd` or `invoice.jsdx`, you can use the [`jsonx-maven-plugin`][jsonx-maven-plugin] to automatically generate the Java class files. In your POM, add:

   ```xml
   <plugin>
     <groupId>org.jsonx</groupId>
     <artifactId>jsonx-maven-plugin</artifactId>
     <version>0.2.2</version>
     <executions>
       <execution>
         <goals>
           <goal>generate</goal>
         </goals>
         <phase>generate-sources</phase>
         <configuration>
           <destDir>${project.build.directory}/generated-sources/jsonx</destDir>
           <prefix>com.example.invoice.</prefix>
           <schemas>
             <schema>src/main/resources/invoice.jsd</schema> <!-- or invoice.jsdx -->
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

   public class Address implements JxObject {
     @StringProperty(pattern="\\S|\\S.*\\S", nullable=false)
     public String name;

     @StringProperty(pattern="\\S|\\S.*\\S", nullable=false)
     public String address;

     @StringProperty(pattern="\\S|\\S.*\\S", nullable=false)
     public String city;

     @StringProperty(pattern="\\S|\\S.*\\S", use=Use.OPTIONAL, nullable=false)
     public String postalCode;

     @StringProperty(pattern="\\S|\\S.*\\S")
     public String country;
   }
   ```

   ```java
   import org.jsonx.*;

   public class Item implements JxObject {
     @StringProperty(pattern="\\S|\\S.*\\S", nullable=false)
     public String description;

     @NumberProperty(form=Form.INTEGER, range="[1,]", nullable=false)
     public java.math.BigInteger code;

     @NumberProperty(form=Form.INTEGER, range="[1,]", nullable=false)
     public java.math.BigInteger quantity;

     @NumberProperty(range="[1,]", nullable=false)
     public java.math.BigDecimal price;
    }
   ```

   ```java
   import org.jsonx.*;

   public class Invoice implements JxObject {
     @NumberProperty(form=Form.INTEGER, range="[1,]")
     public java.math.BigInteger number;

     @StringProperty(pattern="-?\\d{4}-((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01])|(02-(0[1-9]|1\\d|2\\d))|((0[469]|11)-(0[1-9]|[12]\\d|30)))")
     public String date;

     @ObjectProperty
     public Address billingAddress;

     @ObjectProperty
     public Address shippingAddress;

     @ObjectElement(id=0, type=Item.class)
     @ArrayProperty(elementIds={0}, nullable=false)
     public java.util.List<Item> billedItems;
   }
   ```

1. You can use these classes to represent `Address`es, `Item`s, and `Invoice`s.

   ```java
   Address address = new Address();
   address.name = "John Doe";
   address.address = "111 Wall St.";
   address.city = "New York";
   address.postalCode = "10043";
   address.country = "USA";

   Item item = new Item();
   item.code = BigInteger.valueOf(123);
   item.description = "Pocket Protector";
   item.price = new BigDecimal("14.99");
   item.quantity = BigInteger.valueOf(5);

   Invoice invoice = new Invoice();
   invoice.number = BigInteger.valueOf(14738);
   invoice.date = "2019-05-13";
   invoice.billingAddress = address;
   invoice.shippingAddress = address;
   invoice.billedItems = Collections.singletonList(item);
   ```

1. You can now <ins>marshal</ins> the Java objects to JSON:

   ```java

   String json = JxEncoder._2.marshal(invoice);
   System.out.println(json);
   ```

   ... will produce:

   ```json
   {
     "number": 14738,
     "date": "2019-05-13",
     "billingAddress": {
       "name": "John Doe",
       "address": "111 Wall St.",
       "city": "New York",
       "postalCode": "10043",
       "country": "USA"
     },
     "shippingAddress": {
       "name": "John Doe",
       "address": "111 Wall St.",
       "city": "New York",
       "postalCode": "10043",
       "country": "USA"
     },
     "billedItems": [{
       "description": "Pocket Protector",
       "code": 123,
       "quantity": 5,
       "price": 14.99
     }]
   }
   ```

1. You can also <ins>parse</ins> the JSON into Java objects:

   ```java
   Invoice invoice2 = JxDecoder.parseObject(Invoice.class, new JsonReader(new StringReader(json)));
   assertEquals(invoice, invoice2);
   ```

_The code included in this module implements this example._

## 2 Contributing

Pull requests are welcome. For major changes, please [open an issue](../../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## 3 License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[#binding]: ../../../jsonx/#4-jsonjava-binding-api
[#converter]: ../../../jsonx/#532-converter
[#jsd]: ../../../jsonx/#3-json-schema-definition-language
[jsonx-maven-plugin]: ../../../jsonx-maven-plugin/