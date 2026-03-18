import ers_request_system251.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
/**
 * Jory
 * Test class for User login functionality.
 */

public class UserLogInTest {
    
    // -------------------------------
    // Mocked user data for testing
    // -------------------------------
    private BufferedReader testData(){
        // Sample users: student, institute staff, dean
        String data = 
                "student,2335678,yara2008\n" +
                "institute,3111122,instA998\n" +
                "dean,4110001,deanA665\n";
        return new BufferedReader(new StringReader(data));
    }
    
    // -------------------------------
    // Test student login
    // -------------------------------
    @Test
    public void studentLogin(){
        System.out.println("Testing Student Login...");
        
        User user = User.login(testData(), "2335678", "yara2008");
        
        assertNotNull("Student login returned null", user);
        assertTrue("User should be instance of Student", user instanceof Student);
        assertEquals("student", user.getRole());
    }
    
    // -------------------------------
    // Test institute staff login
    // -------------------------------
    @Test
    public void staffLogin(){
        System.out.println("Testing Staff Login...");
        
        User user = User.login(testData(), "3111122", "instA998");
        
        assertNotNull("Staff login returned null", user);
        assertTrue("User should be instance of InstituteStaff", user instanceof InstituteStaff);
        assertEquals("institute", user.getRole());
    }
   
    // -------------------------------
    // Test dean login
    // -------------------------------
    @Test
    public void deanLogin(){
        System.out.println("Testing Dean Login...");
        
        User user = User.login(testData(), "4110001", "deanA665");
        
        assertNotNull("Dean login returned null", user);
        assertTrue("User should be instance of AdmissionOffice", user instanceof AdmissionOffice);
        assertEquals("dean", user.getRole());
    }
    
    // -------------------------------
    // Test invalid login (wrong password)
    // -------------------------------
    @Test
    public void invalidLogin(){
        System.out.println("Testing Invalid Password...");
        
        User user = User.login(testData(), "2335678", "invalid");
        
        assertNull("Login should fail (return null) with wrong password", user);
    }
    
}
