import java.util.*;

public class Student extends User implements StudentActions {

    private final String id;
    private final String name;
    private final String branch;
    private final double cgpa;
    private boolean isAllotted = false;
    private PS_Station currentAllotment = null;
    private final HashSet<String> subjectsCompleted;
    private ArrayList<PS_Station> preferences;

    public Student(String id, String name, String branch, double cgpa, HashSet<String> subjectsCompleted, String emailId, String password) {
        super(emailId, password);
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.cgpa = cgpa;
        this.subjectsCompleted = subjectsCompleted;
    }

    public PS_Station getCurrentAllotment() {
        return currentAllotment;
    }

    public void setCurrentAllotment(PS_Station currentAllotment) {
        this.currentAllotment = currentAllotment;
    }

    public boolean getAllotted() {
        return isAllotted;
    }

    public void setAllotted(boolean allotted) {
        isAllotted = allotted;
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

    public HashSet<String> getSubjectsCompleted() {
        return this.subjectsCompleted;
    }

    public void submitPreferences(ArrayList<PS_Station> preferences) { // TODO: COMPLETE
        this.preferences = preferences;
    }

    public void acceptAllotment() {
        this.isAllotted = true;
    }

    public PS_Station rejectAllotment(PS_Station station) {
        this.preferences.remove(0);
        station.incrementCapacity();
        this.isAllotted = false;
        this.currentAllotment = null;
        return station;
    }

    public void viewDetailsOfCurrentAllotment() {
        try {
            this.currentAllotment.showDetailsOfStation();
        } catch (Exception e) {
            System.out.println("No PS allotted currently");
            System.out.println();
        }
    }
}
