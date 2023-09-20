# Changes by Version

## [v0.5.0-SNAPSHOT](https://github.com/libj/util/compare/fe32a081eeb07903591c08b35f3791040d2679de..HEAD)

## [v0.4.0](https://github.com/jsonx-org/java/compare/4b757400a236b064e1623de9fd72f3896d6559d6..fe32a081eeb07903591c08b35f3791040d2679de) (2023-09-20)
* #45 Add jx:targetNamespace to JSD(x) spec
* #44 Implement standalone Validator
* #43 Configurable default \"number\" type bindings
* #42 JxDecoder try parse multiple classes
* #41 Support non-strict compliance to JSON spec
* #40 Improve memory and runtime performance
* #39 Use JsonReader.<init>(String)
* #38 jdk19: The Security Manager is deprecated and will be removed in a future release
* #37 Upgrade Maven dependencies
* #36 ArrayValidator previous()/next() logic flaw for nested arrays
* #35 Configurable builder pattern
* #34 Convert to JsonReader composite long point instead of String
* #33 Implement JxDecoder.Builder with \"messageFunction\"
* #32 Missing Pattern.DOTALL on Pattern.compile(...)
* #31 Generate return overrides on inherited \"set\" methods
* #30 IllegalArgumentException if Range cannot be represented by type
* #28 VALIDATING and NON_VALIDATING JxEncoder
* #27 Dump JSON in verbose mode of BadRequestExceptionMapper
* #26 Return this from setters
* #25 Rename JxEncoder.marshal(...) to JxEncoder.toString(...)
* #24 Add convenience methods to parse JSON strings from JxDecoder
* #23 Support global JxDecoder
* #22 Transition to GitHub Actions
* #21 Incorrect association of generated source across disparate parent ClassSpec(s)
* #20 JxEncoder.marshal(List,Class) not reporting errors for encoding of member items
* #19 Handle \"Unexpected end of document\" error in JxDecoder
* #17 Inherit Maven dependency versions from root POM
* #16 Parameterize JUnit tests
* #15 Incorrect order of properties when decompiling Java -> JSD(x) on jdk13
* #14 Remove unnecessary calls to Classes.sortDeclarativeOrder(Method[])

## [v0.3.2](https://github.com/jsonx-org/java/compare/3700771ef1a771f12b0f0ba92b93d9601be5ba2c..4b757400a236b064e1623de9fd72f3896d6559d6) (2020-05-23)
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

## [v0.2.2](https://github.com/entinae/pom/compare/473e4be8f59ea603507a71e069753d917796632b..54e5e19f9540b68baebffbc380fbc266c3367cd2) (2019-05-13)
* Initial public release.