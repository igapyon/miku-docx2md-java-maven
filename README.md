# miku-docx2md-java-maven

`miku-docx2md-java-maven` is the separated Maven plugin adapter for
[`miku-docx2md-java`](https://github.com/igapyon/miku-docx2md-java).

The plugin exposes DOCX to Markdown conversion through Maven goals while
keeping product conversion behavior in the Java runtime artifact
`jp.igapyon:miku-docx2md`.

## Goals

Single-file conversion:

```bash
mvn jp.igapyon:miku-docx2md-maven-plugin:0.9.0:convert \
  -Dmiku-docx2md.inputFile=path/to/input.docx \
  -Dmiku-docx2md.outputFile=path/to/output.md
```

Directory conversion:

```bash
mvn jp.igapyon:miku-docx2md-maven-plugin:0.9.0:convert-directory \
  -Dmiku-docx2md.inputDirectory=path/to/docx \
  -Dmiku-docx2md.outputDirectory=path/to/markdown \
  -Dmiku-docx2md.recursive=false
```

Short-form invocation such as `mvn miku-docx2md:convert` requires Maven plugin
group configuration for `jp.igapyon`. Use full coordinates for reliable local
verification.

## Development

Install the compatible runtime artifact first when it is not already available
from a Maven repository:

```bash
mvn -f workplace/miku-docx2md-java/pom.xml install
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
