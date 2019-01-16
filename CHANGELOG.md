# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
- Multiline parsing
- Empty/blank before value parsing

## [0.1.0] - 2019-01-15
### Added
- This CHANGELOG file to hopefully serve as an standardized open source project CHANGELOG.
- `KYAMLType.INT` should represent Integer values

### Changed
- Moved to individual files:
  - `KYAMLException`
  - `KYAMLType`
  - `values.Empty`
  - `values.Value`
- Package renamed from `net.sierisimo` -> `net.sierisimo.kyaml`
- All test passing now. We should try to keep it like that for the future.

### Fixed
- Some test were moved to `@ParameterizedTest` for simple repetitive tests

[Unreleased]: https://github.com/sierisimo/kyaml/compare/v0.1.0...develop
[0.1.0]: https://github.com/sierisimo/kyaml/compare/v0.0.0...v0.1.0
