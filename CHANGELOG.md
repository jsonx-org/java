# Changes by Version

## [v0.3.2](https://github.com/jsonx-org/java/compare/3700771ef1a771f12b0f0ba92b93d9601be5ba2c..HEAD) (2020-05-23)
* Improve handling of `InvocationTargetException`.
* Improve position correctness in `Error` instances.
* Add condition to skip validation in `ArrayValidator.validate(...)`.
* Better handling logic edges when parsing arrays in `ArrayValidator`.
* Provide subject `JsonReader` to `DecodeException`.
* Provide reference to `JsonReader` in `Error` instances.
* Add `OnFieldEncode` callback to `JxEncoder`.
* Improve `ParseException`.
* Improve `BadRequestExceptionMapper`.
* Use revised `JsonUtil.parseNumber(Class,String)`.
* Improve code format of generated Jx bindings.
* Improve performance of `Generator`.
* General API improvements across the codebase.
* Improve tests.
* Improve javadocs and xmldocs.

## [v0.3.1](https://github.com/jsonx-org/java/compare/54e5e19f9540b68baebffbc380fbc266c3367cd2..3700771ef1a771f12b0f0ba92b93d9601be5ba2c) (2019-07-21)
* Fix unescape property name when converting from **JSD** to **JSDx**.
* Fix `mkdirs` for parent dir of output file in `JxConverter`.
* Implement `convert` & `validate` goals in `jsonx-maven-plugin`.
* Maintain order of root elements during schema conversion.
* Upgrade to `schema-0.3`.
* Change from `number.format` to `number.scale`.
* Support exponential notation in `range` spec.
* Add `doc` attribute to all elements.
* Switch to `jx:` prefix.
* Add `cdc` sample.
* Add `invoice` sample.
* Add `bank` sample.
* Improve unit test coverage.
* Rename `org.jsonx:rs` to `org.jsonx:jaxrs`.
* Skip generation of `#equals`, `#hashCode`, and `#toString` for bindings without properties.
* Change to [JAX-SB](https://github.com/jaxsb/jaxsb) binding framework.
* Upgrade `org.libj:math:0.6.4` to `0.6.5`.
* Upgrade `org.libj:net:0.5.0` to `0.5.1`.
* Upgrade `org.libj:logging:0.4.1` to `0.4.2`.
* Upgrade `org.openjax:json:0.9.1` to `0.9.2`.

## v0.2.2 (2019-05-13)
* Initial public release.