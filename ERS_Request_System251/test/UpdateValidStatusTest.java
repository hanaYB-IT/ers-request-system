import ers_request_system251.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
/**
 *  Jory
 *  Test class for SystemERS.updateValidationStatus
 */

public class UpdateValidStatusTest {
    public SystemERS system;
    
    private final File realFile = new File("data/requests.txt");
    private final File backupFile = new File("data/requests_backup.bak");

    @Before
    public void setUp() throws IOException {
        //backup existing file
        if (realFile.exists()) {
            realFile.renameTo(backupFile);
        }
        
        new File("data").mkdirs();
        
        // Test file data (requests.txt)
        List<String> testData = Arrays.asList(
          "101,2335678,Yara,Computer Science,88797,8.0,01/12/2025,Submitted,Pending",
          "102,2339911,Sara,History,756,9.0,01/12/2025,Submitted,Pending"
    );
        
        FileManager.writeAll(realFile.getPath(), testData);

        system = new SystemERS();
        
    }

    // -----------------------------------------------------------------
    // Test: Update to Validated
    // -----------------------------------------------------------------
    @Test
    public void testUpdateValidationStatus_Validated() {
        System.out.println("Running Test: Update to Validated");
        
        //Update request 101 to "Validated"
        system.updateValidationStatus("101", "Validated");
        
        //ASSERTION 
        Request result = system.findRequestByID("101");
        
        assertNotNull("Request should exist", result);
        assertEquals("Validated", result.getValidationStatus());
    }

    // -----------------------------------------------------------------
    // Test: Update to Rejected
    // -----------------------------------------------------------------
    @Test
    public void testUpdateValidationStatus_Rejected() {
        System.out.println("Running Test: Update to Rejected");
        
        //Update request 102 to "Rejected"
        system.updateValidationStatus("102", "Rejected");
        
        //ASSERTION 
        Request result = system.findRequestByID("102");
        
        assertNotNull("Request should exist", result);
        assertEquals("Rejected", result.getValidationStatus());
    }

    // -----------------------------------------------------------------
    // Test: Invalid ID
    // -----------------------------------------------------------------
    @Test
    public void testUpdateValidationStatus_Invalid() {
        System.out.println("Running Test: Invalid ID");
        
        //update non-existent ID
        system.updateValidationStatus("999", "Validated");
        
        //ASSERTION 
        Request result = system.findRequestByID("999");
        assertNull("Invalid ID should return null", result);
    }

    // -----------------------------------------------------------------
    @After
    public void tearDown() {
        if (realFile.exists()) {
            realFile.delete();
        }

        //Restore the original file from backup
        if (backupFile.exists()) {
            backupFile.renameTo(realFile);
        }
    }
}
    
    

