package ers_request_system251;

import java.io.*;
import java.util.*;

/**
 * Hana:
 * This class represents the main system controller for the IELTS Exemption Request System (ERS).
 * It handles loading majors, tracks, minimum scores.
 * This class is used by Student, Staff, and Admin components.
 *
 * Jory:
 * - Handles saving and retrieving student requests.
 * - Methods:
 *      • saveNewRequest(Request r)
 *      • fetchAllRequests()
 *      • findRequestByID(String reqID)
 */
 
public class SystemERS {

    // ====== DATA STRUCTURES ======
    // Stores all majors (1 → History, 2 → Islamic Studies, ...)
    private Map<Integer, String> majors = new LinkedHashMap<>();

    // Stores the track code for each major (e.g., "Computer Science" → "ELIS")
    private Map<String, String> majorTrack = new HashMap<>();

    // Stores the minimum IELTS score required for each track
    private Map<String, Float> minimumScores = new HashMap<>();

    // Path to the requests file
    private final String REQUEST_FILE = "data" + File.separator + "requests.txt";

    /**
     * Hana:
     * Constructor for SystemERS.
     * When the system starts, it loads majors, tracks, and minimum scores into memory.
     */
    public SystemERS() {
        loadMajors();
        loadTracks();
        loadMinimumScores();
    }

    /**
     * Hana:
     * Loads a list of predefined majors into the system.
     * This list is used in the Student page when selecting a major.
     */
    private void loadMajors() {
        majors.put(1, "History");
        majors.put(2, "Islamic Studies");
        majors.put(3, "Psychology");
        majors.put(4, "English Language");
        majors.put(5, "Nursing");
        majors.put(6, "Computer Science");
        majors.put(7, "Media & Communication");
        majors.put(8, "Architecture");
        majors.put(9, "Mathematics");
        majors.put(10, "Physics");
    }

    /**
     * Hana:
     * Prints all available majors to the console.
     * This is mainly used in the Student interface during request creation.
     */
    public void printMajors() {
        for (Map.Entry<Integer, String> e : majors.entrySet()) {
            System.out.println(e.getKey() + ") " + e.getValue());
        }
    }

    /**
     * Hana:
     * Retrieves the major name given the major number.
     * Example: getMajorName(6) → "Computer Science"
     *
     * @param n major number selected by the student
     * @return major name
     */
    public String getMajorName(int n) { 
        return majors.get(n); 
    }

    /**
     * Hana:
     * Loads the IELTS track group for each major.
     * Each track represents a category with a specific minimum IELTS requirement.
     */
    private void loadTracks() {
        majorTrack.put("History", "ELIA");
        majorTrack.put("Islamic Studies", "ELIA");
        majorTrack.put("Psychology", "ELIA");
        majorTrack.put("English Language", "ELIE");
        majorTrack.put("Nursing", "ELIH");
        majorTrack.put("Computer Science", "ELIS");
        majorTrack.put("Media & Communication", "ELIC");
        majorTrack.put("Architecture", "ELIS");
        majorTrack.put("Mathematics", "ELIS");
        majorTrack.put("Physics", "ELIS");
    }

    /**
     * Hana:
     * Returns the IELTS track code for a specific major.
     *
     * @param major the major selected by the student
     * @return track code (ELIS, ELIA, ELIE...)
     */
    public String getTrack(String major) { 
        return majorTrack.get(major); 
    }

    /**
     * Hana:
     * Loads the minimum IELTS score required for each track.
     * This is used to validate whether the student's certificate score is acceptable.
     */
    private void loadMinimumScores() {
        minimumScores.put("ELIA", 4f);
        minimumScores.put("ELIS", 5f);
        minimumScores.put("ELIE", 5.5f);
        minimumScores.put("ELIH", 5.5f);
        minimumScores.put("ELIC", 4f);
    }

    /**
     * Hana:
     * Validates whether the student's score meets the minimum score for their track.
     *
     * @param t IELTS track code
     * @param s student's certificate score
     * @return true if score ≥ required minimum
     */
    public boolean verifyScore(String t, float s) {
        return s >= minimumScores.get(t);
    }

    /**
     * Hana:
     * Retrieves the minimum required score for a given IELTS track.
     *
     * @param t IELTS track code
     * @return minimum required score
     */
    public float getMinScore(String t) { 
        return minimumScores.get(t); 
    }
    
    /**
     * Jory:
     * Saves a new student request to the file.
     */
    public Request saveNewRequest(Request req) {
        FileManager.appendLine(REQUEST_FILE, req.toFileFormat());
        return req;
    }
    
    /**
     * Jory:
     * Reads all request entries from the file. 
     * converts each line into Request objects.
     */
public List<Request> fetchAllRequests() {
    List<String> lines = FileManager.readFile(REQUEST_FILE);
    List<Request> list = new ArrayList<>();

    for (String line : lines) {
        String[] parts = line.split(",");

        // We expect EXACTLY 9 parts (request format)
        // 0=reqId, 1=studentId, 2=name, 3=major, 4=certificate
        // 5=score, 6=requestDate(ms), 7=validation, 8=approval
        if (parts.length < 9) continue;

        Request req = new Request(
            parts[0],                                // reqId
            parts[1],                                // studentId
            parts[2],                                // student name
            parts[3],                                // major
            parts[4],                                // certificate
            Float.parseFloat(parts[5]),              // score
            parts[7],                                // validation status
            parts[8]                                 // approval status
        );

        list.add(req);
    }

    return list;
}


    /**
     * Jory:
     * Searches for a specific request by its Request ID.
     * Used when staff/admin need to open or update a request.
     *
     * @param reqID ID of the request
     * @return Request object if found, otherwise null
     */
    public Request findRequestByID(String reqID) {
        for (Request req : fetchAllRequests()){
            if (req.getRequestId().equals(reqID)){
                return req;
            }
        }
        return null;    //not found
    }
/*  
 *   Raghad
 *   This method updates the validation status of a specific request.
 *   Steps:
 *   1- Read all request lines from file
 *   2- Locate the request by ID
 *   3- Update validation column + general status column
 *   4- Rewrite updated content back to file
 */
public void updateValidationStatus(String reqID, String newStatus) {
    List<String> lines = FileManager.readFile(REQUEST_FILE);
    List<String> updated = new ArrayList<>();

    for (String line : lines) {
        String[] p = line.split(",");

        // Ensure the line has the correct number of columns
        if (p.length < 9) continue;

        // Find the matching request
        if (p[0].equals(reqID)) {
            p[7] = newStatus;  // Update validation status column
             line = String.join(",", p);
        }

        updated.add(line);
    }

    // Save changes
    FileManager.writeAll(REQUEST_FILE, updated);
}


     /* Lena:
    Returns all the requests that is validated.
    -Load all requests from file
    -Filter requests where validation = "Validated"
    -Return the filtered list
    */
 
    public List<Request> fetchVerifiedRequests() {
        List<Request> list = fetchAllRequests(); //Load all requests from file
        List<Request> verified = new ArrayList<>();
        
        for (Request valid : list) {
            //Check if the request has status = "Validated"
            if (valid.getValidationStatus().equalsIgnoreCase("Validated")) {
                verified.add(valid); //Add to verified list
                }
            }
        return verified; //Return only validated requests
        }
    
    /* Lena:
    Updates the approval status of a request.
    Steps:
    -Read file content
    -Find request by ID
    -Update approval column + general status column
    -Write updated result back to file
    */
    
    public void updateApprovalStatus(String reqID, String status) {
        
        List<String> fileLines = FileManager.readFile(REQUEST_FILE);  //Read all lines
        List<String> updatedLines = new ArrayList<>(); //Store updated lines
        
        for (String line : fileLines) {
            String[] fields = line.split(","); //Split the line
            
            if (fields.length < 9) continue;
            
            if (fields[0].equals(reqID)) {
                fields[8] = status;  //Approval status
                line = String.join(",", fields);
                }
             
            updatedLines.add(line);
            }
        
        //Saving all lines back to the file
        FileManager.writeAll(REQUEST_FILE, updatedLines);
        }
    }
