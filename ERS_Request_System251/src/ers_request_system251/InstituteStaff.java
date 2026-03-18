package ers_request_system251;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 *   Raghad 
 
 *   This class handles staff actions:
 *   1- Displaying all student requests
 *   2- Allowing staff to update validation status
 */
public class InstituteStaff extends User {

    private String staffID;

    public InstituteStaff(String role, String userId, String password) {
        super(role, userId, password);
        this.staffID = userId;
    }

    /*
     * Open staff interface:
     * - Display all requests
     * - Allow staff to choose a request and update its validation status
     */
    public void openStaffPage(SystemERS system) {
        Scanner input = new Scanner(System.in);

        System.out.println("\n===== Institute Staff Page =====");

        while (true) {

            // Load and display all requests
            List<Request> list = system.fetchAllRequests();
            displayTable(system, list);

            System.out.print("\nEnter Request ID to update status (or type 'exit'): ");
            String reqID = input.nextLine().trim();

            if (reqID.equalsIgnoreCase("exit")) {
                System.out.println("Returning to main menu...");
                return;
            }

            // Find request by ID
            Request r = system.findRequestByID(reqID);

            if (r == null) {
                System.out.println("✗ Request not found.");
                continue;
            }

            // If request already has validation status, ask to overwrite
            if (!r.getValidationStatus().equalsIgnoreCase("Submitted")) {
                System.out.println("\n This request already has validation status: "
                        + r.getValidationStatus());
                System.out.print("Do you want to change it? (yes/no): ");

                if (!input.nextLine().equalsIgnoreCase("yes")) {
                    System.out.println("No changes made.");
                    continue;
                }
            }

            // Ask staff for new status
            String newStatus;
            while (true) {
                System.out.print("Enter new status (Validated / Rejected): ");
                newStatus = input.nextLine().trim();

                if (newStatus.equalsIgnoreCase("Validated") ||
                    newStatus.equalsIgnoreCase("Rejected")) {
                    break;
                }

                System.out.println("Invalid input. Try again.");
            }

            // Save updated status
            system.updateValidationStatus(reqID, newStatus);
            System.out.println("✓ Status updated successfully!");
        }
    }

    /*
     * Display all requests in table format for staff
     */
private void displayTable(SystemERS system, List<Request> list) {

    System.out.println("\n================================================================================================================================================");
    System.out.println("| ReqID | StdID   | Name            | Major               | CertCode | Score | Track | Validation | Approval | Date       |");
    System.out.println("================================================================================================================================================");

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    for (Request r : list) {

        String dateStr = df.format(new Date(r.getRequestDate()));

        System.out.printf(
            "| %-5s | %-7s | %-15s | %-18s | %-8s | %-5.1f | %-5s | %-10s | %-8s | %-10s |\n",
            r.getRequestId(),
            r.getStudentId(),
            r.getStudentName(),
            r.getStudentMajor(),
            r.getCertificateCode(),
            r.getScore(),
            system.getTrack(r.getStudentMajor()),
            r.getValidationStatus(),
            r.getApprovalStatus(),
            dateStr
        );
    }

    System.out.println("================================================================================================================================================");
}

}
