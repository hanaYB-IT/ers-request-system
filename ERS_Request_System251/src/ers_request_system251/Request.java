package ers_request_system251;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;


public class Request {

    private String requestId;
    private String studentId;
    private String studentName;
    private String studentMajor;
    private String certificateCode;
    private float score;
    private Date requestDate;

    private String validationStatus = "Submitted";;
    private String approvalStatus = "Pending";


    /**
     * Hana:
     * Constructor for creating a NEW request.
     * It generates a new request ID, stores all student/request information,
     * and records the current date as the request creation time.
     */
    public Request(String studentId, String name, String major, String code, float score) {
        this.requestId = String.valueOf(generateRequestID());
        this.studentId = studentId;
        this.studentName = name;
        this.studentMajor = major;
        this.certificateCode = code;
        this.score = score;
        this.requestDate = new Date();
    }

    /**
     * Hana:
     * Constructor used when loading existing request data from a file.
     * This allows the system to recreate Request objects that were saved earlier.
     */
    public Request(String reqId, String stdId, String name, String major, String code,
                   float score, String vStatus, String aStatus) {

        this.requestId = reqId;
        this.studentId = stdId;
        this.studentName = name;
        this.studentMajor = major;
        this.certificateCode = code;
        this.score = score;
        this.requestDate = new Date();  // In real use, you can load the actual saved timestamp.
        this.validationStatus = vStatus;
        this.approvalStatus = aStatus;
          }

    /**
     * Hana:
     * Generates a unique request ID starting from 100.
     * The ID is stored inside "lastRequestId.txt" so the system remembers
     * the last used number even after restarting the program.
     *
     * Steps:
     * 1. Read last ID from file (or start from 100 if no file exists)
     * 2. Increase the ID by 1
     * 3. Save the new ID back to the file
     * 4. Return the previous ID to be used as the request ID
     */
    private static int generateRequestID() {

        int nextID = 100;

        try {
            File file = new File("data" + File.separator + "lastRequestId.txt");

            if (file.exists()) {
                Scanner sc = new Scanner(file);
                if (sc.hasNextInt()) {
                    nextID = sc.nextInt();
                }
                sc.close();
            }

            int newID = nextID + 1;

            FileWriter fw = new FileWriter("data" + File.separator + "lastRequestId.txt");
            fw.write(String.valueOf(newID));
            fw.close();

            return nextID;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nextID;
    }



    /**
     * Hana:
     * Returns the unique request ID.
     */
    public String getRequestId() { return requestId; }

    /**
     * Hana:
     * Returns the student ID associated with the request.
     */
    public String getStudentId() { return studentId; }

    /**
     * Hana:
     * Returns the student's full name.
     */
    public String getStudentName() { return studentName; }

    /**
     * Hana:
     * Returns the student major (track/specialization).
     */
    public String getStudentMajor() { return studentMajor; }

    /**
     * Hana:
     * Returns the certificate code (IELTS, TOEFL, etc.).
     */
    public String getCertificateCode() { return certificateCode; }

    /**
     * Hanaa:
     * Returns the score submitted by the student.
     */
    public float getScore() { return score; }

    /**
     * Hana:
     * Returns the validation status ("Submitted", "Valid", "Invalid").
     */
    public String getValidationStatus() { return validationStatus; }

    /**
     * Hana:
     * Returns the approval status ("Pending", "Approved", "Rejected").
     */
    public String getApprovalStatus() { return approvalStatus; }

    /**
     * Hana:
     * Returns the general status of the request (based on validation + approval).
     */
    public String getStatus() { return validationStatus; }

    /**
     * Hana:
     * Returns the request creation date as milliseconds (used for table display).
     */
    public long getRequestDate() {
        return requestDate.getTime();
    }



    /**
     * Hana:
     * Updates the validation status and synchronizes it with the general status.
     * Used by Institute Staff.
     */
    public void setValidationStatus(String status) {
        this.validationStatus = status;
     
    }

    /**
     * Hana:
     * Updates the approval status and synchronizes it with the general status.
     * Used by Dean/Administrator.
     */
   public void setApprovalStatus(String status) {
        this.approvalStatus = status;
    
    }

    /**
     * Hana:
     * Converts all request information into a single line.
     * This format is used when saving the request into the file request.txt.
     */
  public String toFileFormat() {
    return requestId + "," +
           studentId + "," +
           studentName + "," +
           studentMajor + "," +
           certificateCode + "," +
           score + "," +
           new SimpleDateFormat("dd/MM/yyyy").format(requestDate) + "," +
           validationStatus + "," +
           approvalStatus ;
}
}
