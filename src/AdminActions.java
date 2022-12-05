import java.io.FileNotFoundException;

public interface AdminActions {

    void addStudentToStudentsList() throws FileNotFoundException;

    void updatePSStationDetails();

    void addPS_Station() throws FileNotFoundException;

    void showPSStationsList();

    void performIteration() throws FileNotFoundException;
}
