import java.util.*;

public class Admin extends User implements Sort {
    private ArrayList<PS_Station> PS_StationsList;
    private ArrayList<Student> studentList;

    public Admin(String emailId, String password) {
        super(emailId, password);
        PS_StationsList = new ArrayList<PS_Station>();
        studentList = new ArrayList<Student>();
    }

    public void updatePSStationDetails(PS_Station station) {

    }

    public void addPS_Station(PS_Station station) { // TODO: Input from file handling
        PS_StationsList.add(station);
    }

    public void performAllotment() {
    }

    @Override
    public void sortByCgpa() {
        studentList.sort((o1, o2) -> (int) (o1.getCgpa() - o2.getCgpa()));
    }
}
