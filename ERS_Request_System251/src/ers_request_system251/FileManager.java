package ers_request_system251;

import java.io.*;
import java.util.*;

/**
 * Hana:
 * FileManager is a helper utility class responsible for handling file operations
 * within the IELTS Request System.
 *
 * It provides two main functions:
 * 1) Reading file contents line-by-line.
 * 2) Appending new lines to an existing file.
 *
 */
public class FileManager {

    /**
     * Hana:
     * Reads all lines from the given file path and returns them as a List<String>.
     * If the file does not exist, it automatically creates a new empty file.
     *
     * @param path The target file path to read from.
     * @return A list of all lines in the file.
     */
    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(path);

            // Create the file if it doesn't exist
            if (!file.exists()) file.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String l;

            while ((l = br.readLine()) != null)
                lines.add(l);

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lines;
    }

    /**
     * Hana:
     * Appends a single line of text to the end of the specified file.
     * This function is used when creating a new student request, ensuring
     * the request is added as a new line without modifying existing data.
     *
     * @param path The target file to write into.
     * @param text The text line to append.
     */
    public static void appendLine(String path, String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            bw.write(text);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   /*
 *   Raghad 
 *   This method overwrites the file with new content.
 *   Steps:
 *   1- Open file in overwrite mode
 *   2- Write each line from the list
 *   3- Close writer
 */
public static void writeAll(String path, List<String> lines) {
    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path, false));
        for (String s : lines) {
            bw.write(s);
            bw.newLine();
        }
        bw.close();
    } catch (Exception e) { 
        e.printStackTrace(); 
    }
}

    
}
