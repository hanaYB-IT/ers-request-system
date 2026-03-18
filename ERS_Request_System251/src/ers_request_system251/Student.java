package ers_request_system251;

import java.util.Scanner;

/**
 *   Jory
 *   (Student class)
 *    This class handles the student actions:
 *    1- opening student page
 *    2- submitting a new request
 */
 
public class Student extends User {

    public Student(String role, String userId, String password) {
        super(role, userId, password);
    }

/*
    Display the student interface
*/
    public void openStudentPage(SystemERS system) {
        Scanner input = new Scanner(System.in);

        System.out.println("\n===== Student Page =====");
        
        // Get student name
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        
        // Display majors from systemERS
        system.printMajors();
        System.out.print("Choose major number: ");
        int majorNum = input.nextInt();
        input.nextLine();

        String majorName = system.getMajorName(majorNum);

        if (majorName == null) {
            System.out.println("Invalid major number.");
            return;
        }
        
        String track = system.getTrack(majorName);
        
        // Get certificate code
        System.out.print("Enter certificate code: ");
        String code = input.nextLine();

        // Get IELTS score
        System.out.print("Enter IELTS score: ");
        float score = input.nextFloat();
        
        // Submit request
        Request req = submitRequest(name, majorName, track, code, score, system);

        if (req != null)
            System.out.println("Request submitted successfully!");
    }
    
/*
    Validate score, creates request object, and saves it into the system
*/
    public Request submitRequest(String name, String major, String track,
                                 String cert, float score, SystemERS system) {
        //validate score
        if (!system.verifyScore(track, score)) {
            System.out.println("Score too low! Minimum required: " + system.getMinScore(track));
            return null;
        }
        //create a new Request
        Request r = new Request(userId, name, major, cert, score);
        
        //save request into file
        system.saveNewRequest(r);

        return r;
    }

}
