# Changes by Version

## v0.3.1 (2019-07-21)
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