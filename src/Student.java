import java.util.*;

public class Student extends User {

    private final String id;
    private final String name;
    private final String branch;
    private final double cgpa;
    private final ArrayList<String> subjectsCompleted;
    private ArrayList<PS_Station> preferences;

    public Student(String id, String name, String branch, double cgpa, ArrayList<String> subjectsCompleted, String emailId, String password) {
        super(emailId, password);
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.cgpa = cgpa;
        this.subjectsCompleted = subjectsCompleted;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCgpa() {
        return this.cgpa;
    }

    public String getBranch() {
        return this.branch;
    }

    public ArrayList<PS_Station> getPreferences() {
        return this.preferences;
    }

    public ArrayList<String> getSubjectsCompleted() {
        return this.subjectsCompleted;
    }

    public ArrayList<PS_Station> getPSStationsDetails() { // TODO: COMPLETE
        return null;
    }

    public void submitPreferences(ArrayList<PS_Station> preferences) { // TODO: COMPLETE
        this.preferences = preferences;
    }

    public void acceptAllotment() {
    }

    public void rejectAllotment() {
    }

    public void viewDetailsOfCurrentAllotment() {
    }
}
