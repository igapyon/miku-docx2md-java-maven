# Release

## Release Shape

This repository publishes the Maven plugin artifact:

```text
jp.igapyon:miku-docx2md-maven-plugin
```

The Java runtime artifact remains owned by `miku-docx2md-java`:

```text
jp.igapyon:miku-docx2md
```

## Pre-Release Checks

Run:

```bash
mvn test
mvn package
sh scripts/smoke-maven-plugin.sh
```

Check that:

- plugin version matches the intended runtime compatibility version
- `maven-plugin-plugin` generates the plugin descriptor
- full-coordinate invocation works for `convert`
- full-coordinate invocation works for `convert-directory`

Publishing is a human-owned GitHub and Maven repository operation.
