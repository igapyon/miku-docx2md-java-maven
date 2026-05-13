# Maven Plugin Separation Worklog

This document records the local separation work for `miku-docx2md-java-maven`
so the same pattern can be reused for other miku Java products.

## Scope

- Target repository changed in this work: `miku-docx2md-java-maven`
- Reference repository: <https://github.com/igapyon/miku-docx2md-java>
- Local reference checkout: `workplace/miku-docx2md-java`
- Source branch checked out: `devel`
- Runtime repository policy: do not edit `miku-docx2md-java` in this work

## Intended Separation

- Keep DOCX conversion behavior, CLI behavior, parsing, rendering, summaries,
  image asset semantics, and batch product behavior in `miku-docx2md-java`.
- Keep this repository focused on the Maven plugin adapter:
  - Maven plugin coordinates
  - Mojo classes
  - goal names
  - Maven parameters
  - plugin descriptor generation
  - plugin unit tests
  - plugin smoke commands and documentation
- Depend on the runtime artifact `jp.igapyon:miku-docx2md`, instead of keeping
  a parent reactor relationship with `miku-docx2md-java`.

## Steps Performed

1. Confirmed that this repository initially contained only `LICENSE`.
2. Created `workplace/` and cloned the reference repository into
   `workplace/miku-docx2md-java`.
3. Inspected the reference repository's existing
   `miku-docx2md-maven-plugin` module.
4. Copied the existing Mojo implementation into this repository:
   - `MikuDocx2mdMojo.java`
   - `ConvertDirectoryMojo.java`
5. Copied the existing plugin unit test as the starting point.
6. Copied only the DOCX fixtures needed by the plugin tests into
   `src/test/resources/docx/`.

## Changes Still Being Applied

- Replaced the copied module POM with a standalone Maven plugin POM.
- Added repository documentation, development notes, compatibility notes, and a
  smoke script for full-coordinate plugin execution.
- Added `examples/smoke-project/pom.xml` because Maven plugin goals execute in
  a Maven project context.
- Added repository convention files such as `.gitignore`, `.mvn/jvm.config`,
  and `workplace/.gitkeep`.
- Adjusted tests so fixtures are loaded from this repository instead of
  relying on the old reactor layout.

## Pattern To Reuse

For another product, repeat this order:

1. Clone the Java runtime repository into `workplace/<product>-java`.
2. Identify the existing plugin module or plugin-like adapter code.
3. Copy only Maven adapter code into `<product>-java-maven`.
4. Change the plugin POM from reactor child to standalone Maven plugin project.
5. Depend on the runtime artifact by normal Maven coordinates.
6. Move test fixtures into the plugin repository only when needed for plugin
   tests.
7. Keep runtime/core behavior out of the plugin repository.
8. Document full-coordinate invocation and local runtime install requirements.

## Verification Performed

- `mvn install` in `workplace/miku-docx2md-java`
  - Purpose: install `jp.igapyon:miku-docx2md:0.9.0` for this plugin's normal
    Maven dependency resolution.
  - Result: passed.
- `mvn test` in this repository
  - Purpose: verify this repository builds and tests without the old reactor
    layout.
  - Result: passed.
- `sh scripts/smoke-maven-plugin.sh`
  - Purpose: verify full-coordinate `convert` and `convert-directory`
    execution from a minimal Maven project.
  - Result: passed after adding `examples/smoke-project/pom.xml`.

## Notes For The Runtime Repository Follow-Up

`miku-docx2md-java` still contains its original `miku-docx2md-maven-plugin`
module at the time of this work. Removing that module and updating the runtime
repository README are intentionally left for the separate runtime repository
work item.
