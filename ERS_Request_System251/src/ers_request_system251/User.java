package ers_request_system251;

import java.io.BufferedReader;
import java.io.FileReader;

// This is Afrah's section
//------------------------

public class User {

    // ------------------------------
    // Class fields
    // ------------------------------
    protected String role;       // The role of the user (student, institute, admission)
    protected String userId;     // User ID
    protected String password;   // User password

    // ------------------------------
    // Constructor
    // ------------------------------
    public User(String role, String userId, String password) {
        this.role = role;         // Initialize role
        this.userId = userId;     // Initialize userId
        this.password = password; // Initialize password
    }

    // ------------------------------
    // Getters
    // ------------------------------
    public String getRole() { return role; }          // Returns the user's role
    public String getUserId() { return userId; }      // Returns the user's ID
    public String getPassword() { return password; }  // Returns the user's password

    // ------------------------------
    // Static login method
    // ------------------------------
    public static User login(String id, String pass) {
        // Attempt to read the users file
        try (BufferedReader br = new BufferedReader(new FileReader("data/users.txt"))) {

            String line;
            // Read the file line by line
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");        // Split each line into parts
                // Skip the line if it doesn't contain exactly 3 parts
                if (parts.length != 3) continue;

                String role = parts[0].trim();           // Extract role from file
                String fileId = parts[1].trim();         // Extract user ID from file
                String filePass = parts[2].trim();       // Extract password from file

                // If ID or password do not match, skip this user
                if (!fileId.equals(id) || !filePass.equals(pass))
                    continue;

                // Create and return the correct user type based on the role
                switch (role) {
                    case "student":
                        return new Student(role, fileId, filePass);

                    case "institute":
                        return new InstituteStaff(role, fileId, filePass);

                    case "dean": 
                        return new AdmissionOffice(role, fileId, filePass);
                }
            }

        } catch (Exception e) {
            // Handle any errors that occur while reading the file
            System.out.println("Error reading file: " + e.getMessage());
        }

        // No matching user found
        return null;
    }
   //=============================================================================
  
    //Jory 
   //method for User login test
        public static User login(BufferedReader reader, String id, String pass) {
        try {
            String line;
            // Read the file line by line
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");        // Split each line into parts
                // Skip the line if it doesn't contain exactly 3 parts
                if (parts.length != 3) continue;

                String role = parts[0].trim();           // Extract role from file
                String fileId = parts[1].trim();         // Extract user ID from file
                String filePass = parts[2].trim();       // Extract password from file

                // If ID or password do not match, skip this user
                if (!fileId.equals(id) || !filePass.equals(pass))
                    continue;

                // Create and return the correct user type based on the role
                switch (role) {
                    case "student":
                        return new Student(role, fileId, filePass);

                    case "institute":
                        return new InstituteStaff(role, fileId, filePass);

                    case "dean": 
                        return new AdmissionOffice(role, fileId, filePass);
                }
            }

        } catch (Exception e) {
            // Handle any errors that occur while reading the file
            System.out.println("Error reading file: " + e.getMessage());
        }

        // No matching user found
        return null;
    }
           
}
