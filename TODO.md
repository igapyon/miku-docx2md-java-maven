# TODO

## Follow-up Items

- After `miku-docx2md-java` is updated in a separate work item, remove the
  plugin module from that runtime repository and update its README references.
- Add broader plugin smoke coverage when additional real-world DOCX fixtures
  are available.
- Decide the publication workflow for `miku-docx2md-maven-plugin` after the
  runtime/plugin repository split is finalized.
- Reconsider the best local runtime reference strategy for this Maven plugin
  repository. Compare keeping the current artifact-only dependency boundary
  with helper scripts for installing `../miku-docx2md-java` or
  `workplace/miku-docx2md-java`, versus heavier options such as a `runtime/`
  checkout, Git submodule/subtree, or reactor module. The decision should
  preserve clear runtime/plugin ownership while making local builds and smoke
  tests easy to reproduce.
