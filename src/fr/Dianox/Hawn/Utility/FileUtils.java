package fr.Dianox.Hawn.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

/**
 * A collection of file-related utilities.
 */
public class FileUtils {

    /**
     * Is the specified file a directory?
     *
     * @param file The file
     * @return Returns true if the file is a directory. Otherwise, returns false.
     */
    public static boolean isDirectory(File file) {
        return file.isDirectory();
    }

    /**
     * Convert the specified string to an array of bytes in UTF-8 format.
     *
     * @param s The string
     * @return The array of bytes
     */
    public static byte[] toByteArray(String s) {
        return s.getBytes(Charset.forName("UTF-8"));
    }

    @SuppressWarnings("resource")
	public static int read(File file) throws FileNotFoundException, IOException {
        return new FileReader(file).read();
    }

    public static String toString(File file) {
        try {
            return Files.toString(file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    /**
     * Write the specified array of bytes to a file.
     *
     * @param data The data to be written
     * @param to The file
     */
    public static void write(byte[] data, File to) {
        try {
            if (!to.exists()) {
                to.createNewFile();
            }
            Files.write(data, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the specified data to a file.
     *
     * @param data The data to write
     * @param to The file
     */
    public static void write(String data, File to) {
        write(toByteArray(data), to);
    }

    /**
     * Write the specified lines to a file.
     *
     * @param lines The list of lines
     * @param to The file
     */
    public static void write(List<String> lines, File to) {
        String data = lines.isEmpty() ? "\n" : lines.stream().collect(Collectors.joining("\n", "", "\n"));
        write(toByteArray(data), to);
    }

    /**
     * Get the name of the specified file without the extension.
     *
     * @param file The file
     * @return The name of the file without the file extension
     */
    public static String getNameWithoutExtension(String file) {
        return Files.getNameWithoutExtension(file);
    }

    /**
     * Get the extension of the specified file.
     *
     * @param file The file
     * @return The extension of the file
     */
    public static String getFileExtension(String file) {
        return Files.getFileExtension(file);
    }

    /**
     * Reads all lines from a file and get them as a list of strings.
     *
     * @param file The file
     * @return The list of lines
     */
    public static List<String> readLines(File file) {
        try {
            return Files.readLines(file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Move the specified file to the specified location.
     *
     * @param from The original file
     * @param to The destination file for the output
     */
    public static void move(File from, File to) {
        try {
            Files.move(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create any parent directories that do not exist in the specified file's path.
     *
     * @param file The file
     */
    public static void createParentDirs(File file) {
        try {
            Files.createParentDirs(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Append the specified string of data to the end of the specified file.
     *
     * @param data The string to append
     * @param to The file to write to
     */
    public static void append(String data, File to) {
        try {
            Files.append(data, to, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Append the specified lines of text to the end of a specified file.
     *
     * @param lines The list of lines
     * @param to The file to write to
     */
    public static void append(List<String> lines, File to) {
        String data = lines.isEmpty() ? "\n" : lines.stream().collect(Collectors.joining("\n", "", "\n"));
        append(data, to);
    }

    public static boolean compare(File file1, File file2) {
        try {
            return Files.equal(file1, file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("resource")
	public static boolean copy(InputStream source, File out) {
        if (source == null || out == null) {
            return false;
        }
        try {
            if (!out.exists() && !out.createNewFile()) {
                return false;
            }
            if (out.exists() && copy(source, new FileOutputStream(out))) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean copy(InputStream source, FileOutputStream output) {
        boolean result = true;
        try {
            ByteStreams.copy(source, output);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                source.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    /**
     * Creates the specified file (and any parent directories) if it doesn't already exist.
     *
     * @param file The file
     */
    public static void createFile(File file) {
        if (file == null || file.exists()) {
            return;
        }
        createParentDirs(file);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}