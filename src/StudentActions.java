import java.io.FileNotFoundException;

public interface StudentActions {

    void acceptAllotment();

    void rejectAllotment() throws FileNotFoundException;
}
