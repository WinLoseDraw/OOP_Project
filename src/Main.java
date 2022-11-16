import java.util.*;

public class Main {

    public static boolean login(String email, String pass, ArrayList<User> users) { // TODO: Exception handling
        for (User value : users) {
            if (email.equals(value.getEmailId()) && pass.equals(value.getPassword())) {
                // login successful
                // TODO: Redirect to home page
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {

        ArrayList<User> users = new ArrayList<>();

        String email = ""; // temporary, get from user or file
        String pass = ""; // temporary, get from user or file

        users.add(new User(email, pass)); // To register

        login(email, pass, users); // To login
    }
}
