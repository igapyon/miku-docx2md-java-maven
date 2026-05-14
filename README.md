# miku-docx2md-java-maven

`miku-docx2md-java-maven` is the separated Maven plugin adapter for
[`miku-docx2md-java`](https://github.com/igapyon/miku-docx2md-java).

The plugin exposes DOCX to Markdown conversion through Maven goals while
keeping product conversion behavior in the Java runtime artifact
`jp.igapyon:miku-docx2md`.

## Usage

Run the plugin from a Maven project directory. Use full plugin coordinates so
the execution does not depend on local Maven plugin group configuration.

If both artifacts are available from a Maven repository, no project POM change
is required for direct invocation.

Single-file conversion:

```bash
mvn jp.igapyon:miku-docx2md-maven-plugin:1.0.0:convert \
  -Dmiku-docx2md.inputFile=path/to/input.docx \
  -Dmiku-docx2md.outputFile=path/to/output.md
```

Directory conversion:

```bash
mvn jp.igapyon:miku-docx2md-maven-plugin:1.0.0:convert-directory \
  -Dmiku-docx2md.inputDirectory=path/to/docx \
  -Dmiku-docx2md.outputDirectory=path/to/markdown \
  -Dmiku-docx2md.recursive=false
```

Short-form invocation such as `mvn miku-docx2md:convert` requires Maven plugin
group configuration for `jp.igapyon`. Use full coordinates for reliable local
verification.

## Local Unreleased Setup

When the runtime or plugin has not been published yet, install both artifacts
into the local Maven repository before using the plugin from another project.

Install the compatible runtime artifact:

```bash
mvn -f ../miku-docx2md-java/pom.xml install
```

This installs the CLI/runtime jar as the Maven artifact
`jp.igapyon:miku-docx2md:1.0.0` in the local Maven repository. The plugin uses
that jar as a library dependency while the same jar can also be run with
`java -jar` as the CLI runtime.

Install this Maven plugin artifact:

```bash
mvn install
```

Then move to the Maven project that contains the DOCX inputs and run the full
coordinate commands shown in Usage.

The plugin depends on the runtime artifact by Maven coordinates:

```text
jp.igapyon:miku-docx2md:1.0.0
```

It does not use a source-tree dependency on `miku-docx2md-java`.

## Parameters

`convert`:

- `miku-docx2md.inputFile`: required DOCX input file
- `miku-docx2md.outputFile`: Markdown output file; defaults to
  `<input basename>.md`
- `miku-docx2md.summaryFile`: optional summary text output file
- `miku-docx2md.assetsDirectory`: optional image asset output directory
- `miku-docx2md.includeUnsupportedComments`: include unsupported-structure
  diagnostic comments
- `miku-docx2md.skip`: skip plugin execution
- `miku-docx2md.verbose`: log selected runtime progress messages

`convert-directory`:

- `miku-docx2md.inputDirectory`: required directory scanned for `.docx` files
- `miku-docx2md.outputDirectory`: Markdown output directory; defaults to
  writing next to each input file
- `miku-docx2md.assetsDirectory`: optional root directory for per-document
  asset directories
- `miku-docx2md.recursive`: scan input directory recursively
- `miku-docx2md.includeUnsupportedComments`: include unsupported-structure
  diagnostic comments
- `miku-docx2md.skip`: skip plugin execution
- `miku-docx2md.verbose`: log selected runtime progress messages

## Development

For development of this plugin repository, install the compatible runtime
artifact first when it is not already available from a Maven repository:

```bash
mvn -f workplace/miku-docx2md-java/pom.xml install
```

When testing against a local runtime change that has not been pushed to GitHub
yet, install that checkout instead:

```bash
mvn -f ../miku-docx2md-java/pom.xml install
```

Then build and test this plugin:

```bash
mvn test
mvn package
sh scripts/smoke-maven-plugin.sh
```

## Repository Operation

`workplace/` is a local scratch area for reference checkouts, generated smoke
outputs, and temporary verification artifacts. Only `workplace/.gitkeep` is
tracked.

`.mvn/jvm.config` is tracked for repository-local Maven JVM settings.

## License

Apache License 2.0. See [LICENSE](./LICENSE).
