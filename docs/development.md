# Development

## Reference Checkout

This repository uses `workplace/miku-docx2md-java` as a local read-only
reference checkout for the runtime repository.

Do not make runtime repository changes as part of this plugin separation work.

## Commands

Install the runtime artifact into the local Maven repository when needed:

```bash
mvn -f workplace/miku-docx2md-java/pom.xml install
```

Build and test this separated Maven plugin:

```bash
mvn test
mvn package
sh scripts/smoke-maven-plugin.sh
```

## Smoke Test

`scripts/smoke-maven-plugin.sh` runs this plugin through full Maven
coordinates for both `convert` and `convert-directory`.

The smoke script assumes:

- `jp.igapyon:miku-docx2md` is available in the local Maven repository
- this plugin can be installed from the current repository
- required test DOCX fixtures exist under `src/test/resources/docx/`
- the minimal consumer project template exists at `examples/smoke-project/`
