# JSONX Maven Plugin

> Maven Plugin for [JSONX][jsonx] framework

[![Build Status](https://travis-ci.org/openjax/jsonx.png)](https://travis-ci.org/openjax/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/openjax/jsonx/badge.svg)](https://coveralls.io/github/openjax/jsonx)

### Introduction

The `jsonx-maven-plugin` plugin is used to generate JSONX and JSD bindings with the [JSONX][jsonx] framework.

### Goals Overview

* [`jsonx:generate`](#jsonxgenerate) generates JSD bindings.

### Usage

#### `jsonx:generate`

The `jsonx:generate` goal is bound to the `generate-sources` phase, and is used to generate Java bindings for JSD schemas specified in the `configuration`. To configure the generation of JSD bindings for desired JSD schemas, add a `configuration` element to the plugin's specification.

##### Example

```xml
<plugin>
  <groupId>org.openjax.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.9.8-SNAPSHOT</version>
  <executions>
    <execution>
      <goals>
        <goal>generate</goal>
      </goals>
      <phase>generate-sources</phase>
      <configuration>
        <destDir>${project.build.directory}/generated-sources/jsonx</destDir>
        <prefix>com.example.json.</prefix>
        <schemas>
          <schema>src/main/resources/schema.jsd</schema>
        </schemas>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### Configuration Parameters

| Name                          | Type    | Use      | Description                                                               |
|:------------------------------|:--------|:---------|:--------------------------------------------------------------------------|
| <samp>/destDir¹</samp>        | String  | Required | Destination path of generated bindings.                                   |
| <samp>/prefix¹</samp><br>&nbsp;<br>&nbsp;<br>&nbsp;         | String<br>&nbsp;<br>&nbsp;<br>&nbsp;  | Required<br>&nbsp;<br>&nbsp;<br>&nbsp; | Prefix to be prepended to the class names of generated bindings.<br>The prefix represents a:<ul><li>Package name if it ends with an unescaped <samp>.</samp> character</li><li>Declaring class name if it ends with an unescaped <samp>$</samp> character.</li></ul> |
| <samp>/schemas¹</samp>        | List    | Required | List of <samp>schema</samp> elements.                                     |
| <samp>/schemas/schemaⁿ</samp> | String  | Required | File path of JSD or JSDX schema.                                          |

### JavaDocs

JavaDocs are available [here](https://jsonx.openjax.org/apidocs/).

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[mvn-plugin]: https://img.shields.io/badge/mvn-plugin-lightgrey.svg
[jsonx]: /../..