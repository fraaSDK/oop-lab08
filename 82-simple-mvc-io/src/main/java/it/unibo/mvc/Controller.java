package it.unibo.mvc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Application controller. Performs the I/O.
 */
public class Controller {

    private static final String HOME_DIR = System.getProperty("user.home");
    private static final String SEPARATOR = System.getProperty("file.separator");
    private File currentFile = new File(HOME_DIR + SEPARATOR + "output.txt");

    /**
     * @param file of type {@code File} to set as the current file in the {@link Controller}.
     */
    public void setCurrentFile(final File file) {
        this.currentFile = file;
    }

    /**
     * @return the {@code currentFile} with type {@code File}.
     * If no file was set, it returns the default file.
     */
    public File getCurrentFile() {
        return this.currentFile;
    }

    /**
     * @return the {@code currentFile} path as a {@code String}.
     */
    public String getCurrentFilePath() {
        return this.currentFile.getPath();
    }

    /**
     * @param content to write in the file.
     * @throws IOException
     */
    public void saveToFile(final String content) throws IOException {
        try (PrintStream ps = new PrintStream(currentFile, StandardCharsets.UTF_8)) {
            ps.println(content);
        }
    }
}
