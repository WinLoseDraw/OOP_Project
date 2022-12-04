import java.util.*;

public class PS_Station {
    private final String name;
    private int capacity;
    private String location;
    private String projectDescription;
    private ArrayList<String> branchPreference;
    private ArrayList<String> compulsorySubjects;

    public PS_Station(String name, int capacity, String location, String projectDescription, ArrayList<String> branchPreference, ArrayList<String> compulsorySubjects) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.projectDescription = projectDescription;
        this.branchPreference = branchPreference;
        this.compulsorySubjects = compulsorySubjects;
    }

    public void showDetailsOfStation() {
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Project Description: " + projectDescription);
        System.out.println("Branch preference: " + branchPreference);
        System.out.println("Compulsory Subjects: " + compulsorySubjects);
        System.out.println();
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<String> getBranchPreference() {
        return branchPreference;
    }

    public ArrayList<String> getCompulsorySubjects() {
        return compulsorySubjects;
    }

    public void incrementCapacity() { capacity++; }

    public void decrementCapacity() {
        capacity--;
    }
}
