package jp.igapyon.mikudocx2md.mavenplugin;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import jp.igapyon.mikudocx2md.core.MikuDocx2mdBatchOptions;
import jp.igapyon.mikudocx2md.core.MikuDocx2mdBatchResult;
import jp.igapyon.mikudocx2md.core.MikuDocx2mdConversionException;
import jp.igapyon.mikudocx2md.core.MikuDocx2mdConversionListener;
import jp.igapyon.mikudocx2md.core.MikuDocx2mdFileConverter;
import jp.igapyon.mikudocx2md.core.MikuDocx2mdFileResult;

@Mojo(name = "convert-directory", threadSafe = true)
public class ConvertDirectoryMojo extends AbstractMojo {
    @Parameter(property = "miku-docx2md.inputDirectory", required = true)
    private File inputDirectory;

    @Parameter(property = "miku-docx2md.outputDirectory")
    private File outputDirectory;

    @Parameter(property = "miku-docx2md.assetsDirectory")
    private File assetsDirectory;

    @Parameter(property = "miku-docx2md.recursive", defaultValue = "false")
    private boolean recursive;

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
        if (inputDirectory == null) {
            throw new MojoExecutionException("inputDirectory is required.");
        }
        final MikuDocx2mdBatchOptions options = new MikuDocx2mdBatchOptions();
        options.inputDirectory = inputDirectory.toPath();
        options.outputDirectory = outputDirectory == null ? null : outputDirectory.toPath();
        options.assetsDirectory = assetsDirectory == null ? null : assetsDirectory.toPath();
        options.recursive = recursive;
        options.includeUnsupportedComments = includeUnsupportedComments;
        options.listener = new MavenLogListener();
        try {
            final MikuDocx2mdBatchResult result = new MikuDocx2mdFileConverter().convertBatch(options);
            if (result.getConvertedCount() == 0) {
                getLog().info("No .docx files found under " + inputDirectory.getPath());
                return;
            }
            for (final MikuDocx2mdFileResult file : result.files) {
                getLog().info("miku-docx2md wrote " + file.outputFile);
            }
        } catch (final IOException ex) {
            throw new MojoExecutionException("input scan failed: " + ex.getMessage(), ex);
        } catch (final MikuDocx2mdConversionException ex) {
            throw new MojoExecutionException(MikuDocx2mdMojo.formatDocumentError(ex), ex);
        } catch (final IllegalArgumentException ex) {
            throw new MojoExecutionException(ex.getMessage(), ex);
        }
    }

    private class MavenLogListener implements MikuDocx2mdConversionListener {
        @Override
        public void onEvent(final String message) {
            if (verbose) {
                getLog().info("miku-docx2md " + message);
            }
        }
    }

    void setInputDirectory(final File inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    void setOutputDirectory(final File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    void setAssetsDirectory(final File assetsDirectory) {
        this.assetsDirectory = assetsDirectory;
    }

    void setRecursive(final boolean recursive) {
        this.recursive = recursive;
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
