# OpenJAX JSONX Maven Plugin

> Maven Plugin for [JSONX][jsonx] framework

[![Build Status](https://travis-ci.org/openjax/jsonx.png)](https://travis-ci.org/openjax/jsonx)
[![Coverage Status](https://coveralls.io/repos/github/openjax/jsonx/badge.svg)](https://coveralls.io/github/openjax/jsonx)

### Introduction

The `jsonx-maven-plugin` plugin is used to generate JSON bindings with the [JSONX][jsonx] framework.

### Goals Overview

* [`jsonx:generate`](#jsonxgenerate) generates JSONX bindings.

### Usage

#### `jsonx:generate`

The `jsonx:generate` goal is bound to the `generate-sources` phase, and is used to generate JSONX bindings for JSONX documents in the `manifest`. To configure the generation of JSONX bindings for desired JSONX schemas, add a `manifest` element to the plugin's configuration.

##### Example

```xml
<plugin>
  <groupId>org.openjax.jsonx</groupId>
  <artifactId>jsonx-maven-plugin</artifactId>
  <version>0.9.8-SNAPSHOT</version>
  <configuration>
    <destDir>${project.build.directory}/generated-sources/jsonx</destDir>
    <schemas>
      <schema>src/main/resources/json.jsonx</schema>
    </schemas>
  </configuration>
</plugin>
```

#### Configuration Parameters

| Name              | Type    | Use      | Description                                                                   |
|:------------------|:--------|:---------|:------------------------------------------------------------------------------|
| `/`               | Object  | Required | Manifest descriptor.                                                          |
| `/destDir`        | String  | Required | Destination path of generated bindings.                                       |
| `/schemas`        | List    | Required | List of `resource` elements.                                                  |
| `/schemas/schema` | String  | Required | File path of XML Schema.                                                      |

### JavaDocs

JavaDocs are available [here](https://jsonx.openjax.org/apidocs/).

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[mvn-plugin]: https://img.shields.io/badge/mvn-plugin-lightgrey.svg
[jsonx]: /