#!/bin/sh
set -eu

VERSION="${1:-0.9.0}"
ROOT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
WORK_DIR="$ROOT_DIR/workplace/smoke-maven-plugin"

mvn -q -f "$ROOT_DIR/pom.xml" install

rm -rf "$WORK_DIR"
mkdir -p "$WORK_DIR/input" "$WORK_DIR/output"
cp "$ROOT_DIR/examples/smoke-project/pom.xml" "$WORK_DIR/pom.xml"
cp "$ROOT_DIR/src/test/resources/docx/word-bullet-list-basic.docx" "$WORK_DIR/input/single.docx"
cp "$ROOT_DIR/src/test/resources/docx/word-headings-basic.docx" "$WORK_DIR/input/word-headings-basic.docx"

cd "$WORK_DIR"

mvn -q -N "jp.igapyon:miku-docx2md-maven-plugin:$VERSION:convert" \
  -Dmiku-docx2md.inputFile=input/single.docx \
  -Dmiku-docx2md.outputFile=output/single.md

test -f output/single.md

mvn -q -N "jp.igapyon:miku-docx2md-maven-plugin:$VERSION:convert-directory" \
  -Dmiku-docx2md.inputDirectory=input \
  -Dmiku-docx2md.outputDirectory=output/directory \
  -Dmiku-docx2md.recursive=false

test -f output/directory/single.md
test -f output/directory/word-headings-basic.md

echo "miku-docx2md Maven plugin smoke test passed."
