import ers_request_system251.SystemERS;
import ers_request_system251.Request;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class SystemERSIT {

    // HANA: Tests that getMajorName returns correct values and handles invalid indexes safely
    @Test
    public void testGetMajorName() {
        SystemERS s = new SystemERS();

        // Check known valid major names
        assertEquals("Computer Science", s.getMajorName(6));
        assertEquals("History", s.getMajorName(1));

        // Check behavior when index does not exist
        assertNull(s.getMajorName(999));
    }

    // HANA: Tests that getTrack returns the correct IELTS track for each major
    @Test
    public void testGetTrack() {
        SystemERS s = new SystemERS();

        // Valid major-to-track mappings
        assertEquals("ELIS", s.getTrack("Computer Science"));
        assertEquals("ELIA", s.getTrack("History"));
        assertEquals("ELIE", s.getTrack("English Language"));
        assertEquals("ELIH", s.getTrack("Nursing"));

        // Unknown major should return null
        assertNull(s.getTrack("Unknown Major"));
    }

    // HANA: Tests that minimum IELTS scores for each track are accurate
    @Test
    public void testGetMinScore() {
        SystemERS s = new SystemERS();

        // Valid track minimum score checks
        assertEquals(5f, s.getMinScore("ELIS"), 0.0);
        assertEquals(4f, s.getMinScore("ELIA"), 0.0);
        assertEquals(5.5f, s.getMinScore("ELIE"), 0.0);
        assertEquals(5.5f, s.getMinScore("ELIH"), 0.0);
        assertEquals(4f, s.getMinScore("ELIC"), 0.0);
    }

    // HANA: Tests the verification logic to ensure correct acceptance or rejection based on score
    @Test
    public void testVerifyScore() {
        SystemERS s = new SystemERS();

        // Score above minimum → accepted
        assertTrue(s.verifyScore("ELIS", 5.5f));

        // Score equal to minimum → accepted
        assertTrue(s.verifyScore("ELIS", 5f));

        // Score below minimum → rejected
        assertFalse(s.verifyScore("ELIS", 3f));
    }
    //Raghad 
    
    
 @Test
public void testFetchAllRequests() {
    System.out.println("Testing fetchAllRequests() method...");
    
    SystemERS system = new SystemERS();
    List<Request> allRequests = system.fetchAllRequests();
    
    // Verify method returns a list (not null)
    assertNotNull("Method should return non-null list", allRequests);
    
    // Print total count for verification
    System.out.println("Total requests in system: " + allRequests.size());
    
    // If requests exist, verify data integrity
    if (!allRequests.isEmpty()) {
        Request firstRequest = allRequests.get(0);
        
        // Verify request object and key fields
        assertNotNull("First request object should not be null", firstRequest);
        assertNotNull("Request ID should not be null", firstRequest.getRequestId());
        assertNotNull("Student name should not be null", firstRequest.getStudentName());
        
        // Display sample request details
        System.out.println("Sample request - ID: " + firstRequest.getRequestId() + 
                         ", Student: " + firstRequest.getStudentName() +
                         ", Major: " + firstRequest.getStudentMajor());
    } else {
        System.out.println("No requests found in the system");
    }
}
//Raghad 
@Test
public void testFetchVerifiedRequests() {
    System.out.println("Testing fetchVerifiedRequests() method...");
    
    SystemERS system = new SystemERS();
    List<Request> verifiedRequests = system.fetchVerifiedRequests();
    
    // Verify method returns a list (not null)
    assertNotNull("Method should return non-null list", verifiedRequests);
    
    // Verify ALL requests have "Validated" status
    for (Request request : verifiedRequests) {
        assertEquals("All requests must have 'Validated' status", 
                    "Validated", 
                    request.getValidationStatus());
    }
    
    // Print count of validated requests
    System.out.println("Validated requests count: " + verifiedRequests.size());
    
    // Display details of each validated request
    for (Request request : verifiedRequests) {
        System.out.println("Validated Request - ID: " + request.getRequestId() + 
                         ", Student: " + request.getStudentName() +
                         ", Status: " + request.getValidationStatus());
    }
  }
}
