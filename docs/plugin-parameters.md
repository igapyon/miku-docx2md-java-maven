# Plugin Parameters

## `convert`

Goal:

```text
miku-docx2md:convert
```

Parameters:

- `miku-docx2md.inputFile`:
  required DOCX input file
- `miku-docx2md.outputFile`:
  Markdown output file; defaults to `<input basename>.md`
- `miku-docx2md.summaryFile`:
  optional summary text output file
- `miku-docx2md.assetsDirectory`:
  optional image asset output directory
- `miku-docx2md.includeUnsupportedComments`:
  include diagnostic comments for unsupported DOCX structures
- `miku-docx2md.skip`:
  skip plugin execution
- `miku-docx2md.verbose`:
  log selected inputs before processing

## `convert-directory`

Goal:

```text
miku-docx2md:convert-directory
```

Parameters:

- `miku-docx2md.inputDirectory`:
  required directory scanned for `.docx` files
- `miku-docx2md.outputDirectory`:
  Markdown output directory; defaults to writing next to each input file
- `miku-docx2md.assetsDirectory`:
  optional root directory for per-document asset directories
- `miku-docx2md.recursive`:
  scan input directory recursively
- `miku-docx2md.includeUnsupportedComments`:
  include diagnostic comments for unsupported DOCX structures
- `miku-docx2md.skip`:
  skip plugin execution
- `miku-docx2md.verbose`:
  log selected inputs before processing
