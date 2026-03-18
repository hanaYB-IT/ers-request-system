package ers_request_system251;

import java.util.Scanner;

//This is Afrah's section
//------------------------
public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        SystemERS system = new SystemERS();

        // Ask the user to enter their ID
        System.out.print("Enter ID: ");
        String id = input.nextLine();

        // Ask the user to enter their password
        System.out.print("Enter Password: ");
        String password = input.nextLine();

        // Try to log in using the entered ID and password
        User user = User.login(id, password);

        // Check if login failed (user not found or incorrect credentials)
        if (user == null) {
            System.out.println("Login failed.");
            System.exit(0);
        }

        // User login succeeded — open the correct page
        switch (user.getRole()) {

            case "student": // student login page
                ((Student) user).openStudentPage(system);
                break;

            case "institute":   // staff login page
                ((InstituteStaff) user).openStaffPage(system);
                break;

            case "dean":   // dean login page
                ((AdmissionOffice) user).openAdminPage(system);
                break;

            default: 
                System.out.println("Unknown role.");
        }
    }
}
