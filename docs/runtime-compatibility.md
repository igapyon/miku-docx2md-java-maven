# Runtime Compatibility

## Current Policy

- Plugin artifact: `jp.igapyon:miku-docx2md-maven-plugin:1.0.0`
- Runtime artifact: `jp.igapyon:miku-docx2md:1.0.0`
- Version policy: keep the plugin version aligned with the compatible runtime
  version unless a future release explicitly documents a mismatch.

## Local Runtime Setup

Until the runtime artifact is available from a remote Maven repository, install
it from the local reference checkout:

```bash
mvn -f workplace/miku-docx2md-java/pom.xml install
```

When validating an unpublished runtime update, install that local checkout
instead:

```bash
mvn -f ../miku-docx2md-java/pom.xml install
```

This command writes Maven artifacts to the local Maven repository. It should
not be treated as a source change to the runtime repository.

## Verification

Use:

```bash
mvn test
sh scripts/smoke-maven-plugin.sh
```

The smoke script verifies full-coordinate invocation for both plugin goals.
