<img src="https://www.cohesionfirst.org/logo.png" align="right">

## JSONX<br>![java-enterprise][java-enterprise] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Java <-> JSON Binding

### Introduction

**JSONX** is a lightweight framework based on a XSD [JSONx Schema][jsonx-xsd] that allows one to create a schema for JSON classes. To its disadvantage, JavaScript is compiler-less, and a highly non-cohesive language that results in errors being realized in runtime. As there does not exist a formalized schema specification for JSON, developers often make repeated mistakes when designing JSON messages, encoding JSON objects, and decoding JSON strings. **JSONX** presents a schema model that can be used to bring errors to edit-time and compile-time, greatly reducing the risk of the same errors to appear in run-time.

### Why **JSONX**?

#### CohesionFirst™

Developed with the CohesionFirst™ approach, **JSONX** is the cohesive alternative to the creation of JSON classes that offers validation and fail-fast execution. Made possible by the rigorous conformance to design patterns and best practices in every line of its implementation, **JSONX** is a complete solution for the creation and management of a JSON interface model, both for consumers and producers.

#### Cohesive Binding Between JSON and Java Classes

**JSONX** uses a `json.jsonx` file that conforms to the [JSONx Schema][jsonx-xsd] to generate Java beans to bind the JSON classes to Java. The generated classes are strongly typed and offer the full benefits of a cohesive interface to JSON objects in Java. The generated classes can be used to parse and marshal JSON messages, confident that all messages conform to the definition in the **JSONX**.

#### Support Complete JSON Spec and Abstract Types

The [JSONx Schema][jsonx-xsd] has constructs that allow for the definition of [the entire range of possible JSON structures][json]. Additionally, the schema offers abstract types, which provides one the ability to use the OO principles of inheritance and polymorphism for JSON -- powerful paradigms which are not used in JSON as it is based on the "loosely Object Oriented" language of JavaScript.

#### Support Check Constraint Triggers on Encode and Decode

The [JSONx Schema][jsonx-xsd] offers semantics for the definition of check constraints on properties. Properties of different types have different check constraints -- the `String` property has a `PatternValidator` that allows a developer to assert the property to conform to a regex pattern. Additionally, all properties have semantics for `nullable` and `required`, where the former states whether a property can be `null`, and the latter states whether the property can altogether be omitted from the message. Validation of messages occurs in the **JSONX** parser and marshaller, on encode of a message to be sent out, and the decode of a message coming in.

#### Validating and Fail-Fast

**JSONX** is based on the [JSONx Schema][jsonx-xsd] that allows one to define JSON classes in XML. The **JSONX** XSD uses the full power of XML Validation to provide immediate feedback of errors or inconsistencies in the model. Cross-object and cross-property relations are checked using the `key`, `keyref` and `unique` facets of the XML Schema specification. Once a `json.jsonx` passes the validation checks, it is guaranteed to produce JSON-compliant objects.

### Getting Started

#### Prerequisites

* [Java 8][jdk8-download] - The minimum required JDK version.
* [Maven][maven] - The dependency management system.

#### Example

1. In your preferred development directory, create a [`maven-archetype-quickstart`][maven-archetype-quickstart] project.

  ```tcsh
  mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
  ```

2. Add the `mvn.repo.openjax.org` Maven repositories to the POM.

  ```xml
  <repositories>
    <repository>
      <id>mvn.repo.openjax.org</id>
      <url>http://mvn.repo.openjax.org/m2</url>
    </repository>
  </repositories>
  <pluginRepositories>
    <pluginRepository>
      <id>mvn.repo.openjax.org</id>
      <url>http://mvn.repo.openjax.org/m2</url>
    </pluginRepository>
  </pluginRepositories>
  ```

3. Create a `json.jsonx` and put it in `src/main/resources/`.

  ```xml
  <json name="json"
    xmlns="http://jsonx.openjax.org/jsonx-0.9.8.xsd"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://jsonx.openjax.org/jsonx-0.9.8.xsd http://jsonx.openjax.org/jsonx.xsd">

    <description>JSON class definitions for communication protocol of server API</description>

    <object name="id" abstract="true">
      <property xsi:type="string" name="id" pattern="[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}" null="false"/>
    </object>

    <object name="idVersion" abstract="true" extends="id">
      <property xsi:type="number" name="version" null="false"/>
    </object>

    <object name="ids">
      <property xsi:type="string" name="id" pattern="[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}" array="true" null="false"/>
    </object>

    <object name="credentials">
      <property xsi:type="string" name="email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}" null="false"/>
      <property xsi:type="string" name="password" pattern="[0-9a-f]{64}" required="false" null="false"/>
    </object>

    <object name="account" extends="credentials">
      <property xsi:type="string" name="id" pattern="[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}" null="false" required="false"/>
      <property xsi:type="string" name="firstName" null="false"/>
      <property xsi:type="string" name="lastName" null="false"/>
    </object>

  </json>
  ```

4. Add the [`org.openjax.maven.plugin:jsonx-maven-plugin`][jsonx-maven-plugin] to the POM.

  ```xml
  <plugin>
    <groupId>org.openjax.maven.plugin</groupId>
    <artifactId>jsonx-maven-plugin</artifactId>
    <version>0.9.8-SNAPSHOT</version>
    <executions>
      <execution>
        <goals>
          <goal>generate</goal>
        </goals>
        <configuration>
          <destDir>${project.build.directory}/generated-sources/jsonx</destDir>
          <schemas>
            <schema>src/main/resources/json.jsonx</schema>
          </schemas>
        </configuration>
      </execution>
    </executions>
  </plugin>
  ```

5. Add the `org.openjax.jsonx:jsonx-generator` dependency to the POM.

  ```xml
  <dependency>
    <groupId>org.openjax.jsonx</groupId>
    <artifactId>jsonx-generator</artifactId>
    <version>0.9.8-SNAPSHOT</version>
  </dependency>
  ```

6. Upon successful execution of the [`jsonx-maven-plugin`][jsonx-maven-plugin] plugin, a class by the name of `json` (as was specified in the `name` attribute of the `<json>` element in `json.jsonx`) will be generated in `generated-sources/jsonx`. Add this path to your Build Paths in your IDE to integrate into your project.

7. The generated classes can be instantiated as any other Java objects. They are strongly typed, and will guide you in proper construction of a JSON message. The following patterns can be used for parsing and marshalling **JSONX** to and from JSON:

  To parse JSON to **JSONX**:

  ```java
  final json.Credentials credentials = (json.Credentials)JSObject.parse(rawType, new StringReader("{email: 'john@doe', password: '066b91577bc547e21aa329c74d74b0e53e29534d4cc0ad455abba050121a9557'}"))`
  ```
  
  To marshal **JSONX** to JSON:

  ```java
  System.out.println(credentials.toString());
  ```

#### Integration with XRS

**JSONX** can be used as the `MessageBodyReader` and `MessageBodyWriter` to marshal and parse JSON objects in a JAX-RS 2.0 server. Please [see here][xrs-getting-started] for an example of how to initiate **JSONX** as a Provider for your JAX-RS 2.0 application. The [XRS implementation][xrs] offers a CohesionFirst™ alternative to JAX-RS 2.0.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[java-enterprise]: https://img.shields.io/badge/java-enterprise-blue.svg
[jdk8-download]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[jsonx-maven-plugin]: https://github.com/openjax/jsonx-maven-plugin
[json]: http://www.json.org/
[jsonx-xsd]: https://github.com/openjax/jsonx/blob/master/generator/src/main/resources/jsonx.xsd
[maven-archetype-quickstart]: http://maven.apache.org/archetypes/maven-archetype-quickstart/
[maven]: https://maven.apache.org/
[xrs-getting-started]: https://github.com/openjax/xrs#getting-started
[xrs]: https://github.com/openjax/xrs