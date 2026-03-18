import ers_request_system251.FileManager;
import ers_request_system251.Request;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/*
Lena:
Unit Test for Request class
Methods:
- setValidationStatus()
- testSetApprovalStatus()
*/
public class Request_UnitTest {
    private Request request;

    @Before
    public void setUp() {
        //Create a request object manually
        request = new Request(
                "103",          //requestId
                "2335678",      //studentId
                "lena turki",   //name
                "Computer Science", //major
                "434566",       //certificate code
                8.0f,           //score
                "Validated",    //validation status
                "Approved"       //approval status
        );
    }
    @Test
    public void testSetValidationStatus() {
        //Call the set with "Validated"
        request.setValidationStatus("Validated");
    
       //returns the updated value
       assertEquals("Validated", request.getValidationStatus());
       }
    
    @Test
    public void testSetApprovalStatus() {
        //Call the set with "Validated"
        request.setApprovalStatus("Approved");
        
        //returns the updated value
        assertEquals("Approved", request.getApprovalStatus());
    }
}