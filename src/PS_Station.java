import java.util.*;

public class PS_Station {
    private final String name;
    private int capacity;
    private String location;
    private String projectDescription;
    private ArrayList<Student> studentsRegistered;
    private ArrayList<String> branchPreference;

    public PS_Station(String name, int capacity, String location, String projectDescription, ArrayList<String> branchPreference) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.projectDescription = projectDescription;
        this.branchPreference = branchPreference;
        studentsRegistered = new ArrayList<Student>();
    }

    public boolean isAllowed(Student student) {
        return branchPreference.contains(student.getBranch());
    }

    public void setCapacity() {
        capacity--;
    }
}
