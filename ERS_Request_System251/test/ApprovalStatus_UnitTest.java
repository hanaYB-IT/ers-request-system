import ers_request_system251.Request;
import ers_request_system251.Student;
import ers_request_system251.SystemERS;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.*; // JUnit 4 assert

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before; 
import org.junit.After; 
import org.junit.Test;   

import ers_request_system251.FileManager;

// This is Afrah's Unit Test Section
//-----------------------------------------------------------

public class ApprovalStatus_UnitTest {
    // ----------------------------
    // Constants for test file paths (used to create test files)
    // ----------------------------
    private static final String READ_TEST_FILE  = "test_readFile.txt"; // File used for readFile() test
    private static final String WRITE_FILE = "test_writeFile.txt";     // File used for writeAll() test
    private static final String TEST_REQUEST_FILE = "test_requests.txt"; // File used for approval update test
    
    // --------------------------------
    // Setup method runs before each test
    // Creates required files for test cases
    // --------------------------------
    @Before
    public void setUp() throws IOException {
        // Create a file containing 3 lines for readFile() test
        FileWriter writer = new FileWriter(READ_TEST_FILE);
        writer.write("Line1\nLine2\nLine3");
        writer.close();

        // Create a requests file to simulate updating approval status
        FileManager.writeAll(TEST_REQUEST_FILE, Arrays.asList(
            "REQ001,John,Doe,Dept1,2025-11-27,Desc1,TypeA,Cat1,Pending,Pending", // First request (will be updated)
            "REQ002,Jane,Smith,Dept2,2025-11-28,Desc2,TypeB,Cat2,Pending,Pending"  // Second request (will remain)
        ));
    }
    
    //-----------------------------------------------------------------
    // Test for readFile method
    // Ensures file content is read correctly line by line
    //-----------------------------------------------------------------
    @Test
    public void testReadFile() {
        List<String> lines = FileManager.readFile(READ_TEST_FILE);

        assertEquals(3, lines.size());          // Check number of lines
        assertEquals("Line1", lines.get(0));    // First line
        assertEquals("Line2", lines.get(1));    // Second line
        assertEquals("Line3", lines.get(2));    // Third line
    }

    //-----------------------------------------------------------------
    // Test for writeAll method
    // Writes a list of strings to a file, then reads it back and compares
    //-----------------------------------------------------------------
    @Test
    public void testWriteAll() throws IOException {
        List<String> linesToWrite = Arrays.asList("Hello", "World", "JUnit");

        FileManager.writeAll(WRITE_FILE, linesToWrite); // Write test data to file

        List<String> readLines = FileManager.readFile(WRITE_FILE); // Read file content
        assertEquals(linesToWrite, readLines); // Check if written == read
    }

    //-----------------------------------------------------------------
    // Test for manually updating approval status
    // Edits the status of REQ001 to "Approved" then verifies file changes
    //-----------------------------------------------------------------
    @Test
    public void updateApprovalStatus() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(TEST_REQUEST_FILE)); // Read original file
        List<String> updated = new ArrayList<>(); // Store updated lines

        for (String line : lines) {
            if (line.startsWith("REQ001,")) { // Find the request that needs updating
                String[] parts = line.split(","); // Split into columns

                // Update last two fields (status fields)
                parts[parts.length - 1] = "Approved"; // Final status
                parts[parts.length - 2] = "Approved"; // Initial status

                updated.add(String.join(",", parts)); // Rebuild updated line
            } else {
                updated.add(line); // Keep other lines the same
            }
        }

        Files.write(Paths.get(TEST_REQUEST_FILE), updated); // Save updated data

        List<String> newLines = Files.readAllLines(Paths.get(TEST_REQUEST_FILE)); // Read updated file
        assertTrue(newLines.get(0).contains("Approved")); // First request updated
        assertTrue(newLines.get(1).contains("Pending"));  // Second request unchanged
    }

    //-----------------------------------------------------------------
    // fileDelete method runs after each test
    // Cleans up and removes temporary test files
    //-----------------------------------------------------------------
    @After
    public void fileDelete() throws IOException {
        Files.deleteIfExists(Paths.get(READ_TEST_FILE));
        Files.deleteIfExists(Paths.get(WRITE_FILE));
        Files.deleteIfExists(Paths.get(TEST_REQUEST_FILE));
    }
}
