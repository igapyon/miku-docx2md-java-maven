package jp.igapyon.mikudocx2md.mavenplugin;

import java.io.File;
import java.nio.file.Path;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import jp.igapyon.mikudocx2md.core.MikuDocx2mdConversionException;
import jp.igapyon.mikudocx2md.core.MikuDocx2mdConversionListener;
import jp.igapyon.mikudocx2md.core.MikuDocx2mdFileConverter;
import jp.igapyon.mikudocx2md.core.MikuDocx2mdFileOptions;
import jp.igapyon.mikudocx2md.core.MikuDocx2mdFileResult;

@Mojo(name = "convert", threadSafe = true)
public class MikuDocx2mdMojo extends AbstractMojo {
    @Parameter(property = "miku-docx2md.inputFile", required = true)
    private File inputFile;

    @Parameter(property = "miku-docx2md.outputFile")
    private File outputFile;

    @Parameter(property = "miku-docx2md.summaryFile")
    private File summaryFile;

    @Parameter(property = "miku-docx2md.assetsDirectory")
    private File assetsDirectory;

    @Parameter(property = "miku-docx2md.includeUnsupportedComments", defaultValue = "false")
    private boolean includeUnsupportedComments;

    @Parameter(property = "miku-docx2md.skip", defaultValue = "false")
    private boolean skip;

    @Parameter(property = "miku-docx2md.verbose", defaultValue = "false")
    private boolean verbose;

    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("miku-docx2md skipped.");
            return;
        }
        if (inputFile == null) {
            throw new MojoExecutionException("inputFile is required.");
        }
        final File actualOutputFile = outputFile == null ? new File(stripDocxExtension(inputFile.getName()) + ".md") : outputFile;
        final MikuDocx2mdFileOptions options = new MikuDocx2mdFileOptions();
        options.inputFile = inputFile.toPath();
        options.outputFile = actualOutputFile.toPath();
        options.summaryFile = summaryFile == null ? null : summaryFile.toPath();
        options.assetsDirectory = assetsDirectory == null ? null : assetsDirectory.toPath();
        options.includeUnsupportedComments = includeUnsupportedComments;
        options.listener = new MavenLogListener();
        try {
            final MikuDocx2mdFileResult result = new MikuDocx2mdFileConverter().convertFile(options);
            getLog().info("miku-docx2md wrote " + result.outputFile);
        } catch (final MikuDocx2mdConversionException ex) {
            throw new MojoExecutionException(formatDocumentError(ex), ex);
        } catch (final RuntimeException ex) {
            throw new MojoExecutionException(formatDocumentError(inputFile.toPath(), "convert failed", ex), ex);
        }
    }

    static String stripDocxExtension(final String fileName) {
        return MikuDocx2mdFileConverter.stripDocxExtension(fileName);
    }

    static String formatDocumentError(final MikuDocx2mdConversionException error) {
        return formatDocumentError(error.getInputFile(), error.getStage(), error);
    }

    static String formatDocumentError(final Path inputFile, final String stage, final Throwable error) {
        final String documentName = inputFile == null || inputFile.getFileName() == null ? "input.docx" : inputFile.getFileName().toString();
        final String message = error.getMessage() == null ? String.valueOf(error) : error.getMessage();
        return "[" + documentName + "] " + stage + ": " + message;
    }

    private class MavenLogListener implements MikuDocx2mdConversionListener {
        @Override
        public void onEvent(final String message) {
            if (verbose) {
                getLog().info("miku-docx2md " + message);
            }
        }
    }

    void setInputFile(final File inputFile) {
        this.inputFile = inputFile;
    }

    void setOutputFile(final File outputFile) {
        this.outputFile = outputFile;
    }

    void setSummaryFile(final File summaryFile) {
        this.summaryFile = summaryFile;
    }

    void setAssetsDirectory(final File assetsDirectory) {
        this.assetsDirectory = assetsDirectory;
    }

    void setIncludeUnsupportedComments(final boolean includeUnsupportedComments) {
        this.includeUnsupportedComments = includeUnsupportedComments;
    }

    void setSkip(final boolean skip) {
        this.skip = skip;
    }

    void setVerbose(final boolean verbose) {
        this.verbose = verbose;
    }
}
