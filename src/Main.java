import java.util.*;
import java.io.*;

public class Main {

    public static String login(String email, String pass, ArrayList<User> users) { // TODO: Exception handling
        for (User value : users) {
            if (email.equals(value.getEmailId()) && pass.equals(value.getPassword())) {
                // login successful
                // TODO: Redirect to home page
                return value.getEmailId();
            }
        }
        return "Invalid email or password";
    }

    public static void registerStudent(String id, String name, String branch, double cgpa, HashSet<String> subjectsCompleted, String emailId, String password, ArrayList<User> users) {
        try {
            users.add(new Student(id, name, branch, cgpa, subjectsCompleted, emailId, password));
        } catch (Exception e) {
            System.out.println("Student could not be registered");
        }
    }
    public static void main(String[] args) throws Exception {

        ArrayList<User> users = new ArrayList<>();

//        String email = ""; // temporary, get from user or file
//        String pass = ""; // temporary, get from user or file
//
//        users.add(new User(email, pass)); // To register
//
//        login(email, pass, users); // To login

        Admin admin = new Admin("admin@admin.com", "admin");

        Student student1 = new Student("2021A7PS0555P", "Meet Vithalani", "CS", 7.24, new HashSet<>(Arrays.asList("OOP", "DSA")), "f20210555@pilani.bits-pilani.ac.in", "meet");

        Student student2 = new Student("2021A7PS2420P", "Nishant Luthra", "EEE", 9.73, new HashSet<>(Arrays.asList("OOP", "DBMS")), "f20212420@pilani.bits-pilani.ac.in", "nishant");

        Student student3 = new Student("2021A7PS2223P", "Yash Sejpal", "BPharm", 6.76, new HashSet<>(Arrays.asList("OOP", "DSA")), "f20212223@pilani.bits-pilani.ac.in", "nishant");

        users.add(admin);
        users.add(student1);
        users.add(student2);
        users.add(student3);

//        System.out.println(login("f20212420@pilani.bits-pilani.ac.in", "nishant", users));
//
//        System.out.println(login("f20212420@pilani.bits-pilani.ac.in", "niserfefhant", users));
//
//        System.out.println(login("f202343420@pilani.bits-pilani.ac.in", "nishant", users));

        PS_Station JioStation = new PS_Station("Jio", 2, "Mumbai", "None", new ArrayList<>(Arrays.asList("CS", "EEE")), new ArrayList<>(List.of("OOP")));
        Scanner sc=new Scanner(new FileInputStream("PS_STATION.txt"));
        while(sc.hasNextLine())
        {
            String name=sc.next();
            int capacity=sc.nextInt();
            String location=sc.next();
            String projectD=sc.next();
            ArrayList<String> branchP=new ArrayList<>();
            while(sc.hasNext())
            {
                branchP.add(sc.next());
            }
            ArrayList<String> compS=new ArrayList<>();
            while(sc.hasNext())
            {
                compS.add(sc.next());
            }
            PS_Station p=new PS_Station(name,capacity,location,projectD,branchP,compS);
            admin.addPS_Station(p);

        }
        sc.close();

        admin.addPS_Station(JioStation);

        // admin.showPSStationsList();

        student1.submitPreferences(new ArrayList<>(List.of(JioStation)));
        student2.submitPreferences(new ArrayList<>(List.of(JioStation)));
        student3.submitPreferences(new ArrayList<>(List.of(JioStation)));

        admin.addStudentToStudentsList(student1);
        admin.addStudentToStudentsList(student2);
        admin.addStudentToStudentsList(student3);

        admin.performIteration();

        for (User user : users) {
            if (user instanceof Student) {
                ((Student) user).viewDetailsOfCurrentAllotment();
            }
        }

        admin.updatePSStationDetails(student1.rejectAllotment(student1.getCurrentAllotment()));

        admin.performIteration();

        for (User user : users) {
            if (user instanceof Student) {
                ((Student) user).viewDetailsOfCurrentAllotment();
            }
        }
        Scanner br=new Scanner(new FileInputStream("preferences.txt"));


    }
}
