import java.util.*;
import java.io.*;
import java.util.ArrayList;


public class Main extends Thread {

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

    public synchronized static void registerStudent(String id, String name, String branch, double cgpa, HashSet<String> subjectsCompleted, String emailId, String password, ArrayList<User> users) {
        try {
            users.add(new Student(id, name, branch, cgpa, subjectsCompleted, emailId, password));
        } catch (Exception e) {
            System.out.println("Student could not be registered");
        }
    }

    public static void startApp() throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter 1 for Admin Mode, Enter 2 for Student Mode, Enter 3 to Exit");
        String s = in.nextLine().trim();
        if (s.equals("1")) {
            adminLogin();
        } else if (s.equals("2")) {
            System.out.println("Enter 1 to Login, Enter 2 to Register");
            String ch = in.nextLine().trim();
            if (ch.equals("1")) {
                studentLogin();
            } else if (ch.equals("2")) {
                studentRegister();
            }
        } else if (s.equals("3")) {
            System.exit(0);
        } else {
            System.out.println("Invalid Option. Program Terminating!!!");
        }
    }

    public static void adminLogin() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your UserID and Password ->");
        System.out.print("UserID : ");
        String user = in.nextLine().trim();
        System.out.print("Password: ");
        String pwd = in.nextLine().trim();
        boolean can = Admin.VerifyAdminLogin(user, pwd);//TODO: Need to write method to verify predeclared admin email and pwd
        if (can) {
            Admin admin = new Admin(user,pwd);
            Thread thread = new Thread(admin);
            thread.start();
            Thread.currentThread().interrupt();
           
        } else {
            System.out.println("Invalid Credentials!");
            adminLogin();
        }
    }

    public static void studentLogin() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your UserID and Password ->");
        System.out.print("UserID : ");
        String user1 = in.nextLine().trim();
        System.out.print("Password: ");
        String pwd = in.nextLine().trim();
        boolean can = Student.VerifyStudentLogin(user1, pwd);//TODO: Need to write method to verify already registered student from file.
        if (can) {
            User user = new User(user1, pwd);
            Thread thread = new Thread((Runnable) user);
            thread.start();
            Thread.currentThread().interrupt();
        } else {
            System.out.println("Invalid Credentials!");
            studentLogin();
        }
    }

    public static void studentRegister() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your Details below : ");
        System.out.print("Email-ID : ");
        String email = in.nextLine().trim();
        System.out.print("Password: ");
        String pswd = in.nextLine().trim();
        System.out.print("Name: ");
        String name = in.nextLine().trim();
        System.out.print("BITS-ID: ");
        String id = in.nextLine().trim();
        System.out.print("Branch: ");
        String branch= in.nextLine().trim();
        System.out.print("CGPA: ");
        Double cg= Double.parseDouble(in.nextLine().trim());
        System.out.print("Subjects Completed: ");
        //TODO: take subjects completed input
        String sub=in.nextLine().trim();
        String[] subjects=sub.split(",");
        HashSet<String> subjectsCompleted = new HashSet<>(Arrays.asList(subjects));
        System.out.println("Registered Successfully!");
        Student user = new Student(id, name, branch, cg,subjectsCompleted, email, pswd);

       // admin.addStudenttoStudentList(user)
        Thread thread = new Thread(user);
        thread.start();
        Thread.currentThread().interrupt();



        PrintWriter ab=new PrintWriter(new FileOutputStream("StudentLoginDetails.txt",true));
        ab.println(email);
        ab.println(pswd);
        ab.close();
        PrintWriter cd=new PrintWriter(new FileOutputStream("StudentDetails.txt",true));
        cd.println(email);
        cd.println(pswd);
        cd.println(name);
        cd.println(id);
        cd.println(branch);
        cd.println(cg);
        cd.println(sub);
        cd.close();
    }



    public static void main(String[] args) throws Exception {

        ArrayList<User> users = new ArrayList<>();
        startApp();
//        String email = ""; // temporary, get from user or file
//        String pass = ""; // temporary, get from user or file
//
//        users.add(new User(email, pass)); // To register
//
//        login(email, pass, users); // To login

//        Admin admin = new Admin("admin@admin.com", "admin");
//
//        Student student1 = new Student("2021A7PS0555P", "Meet Vithalani", "CS", 7.24, new HashSet<>(Arrays.asList("OOP", "DSA")), "f20210555@pilani.bits-pilani.ac.in", "meet");
//
//        Student student2 = new Student("2021A7PS2420P", "Nishant Luthra", "EEE", 9.73, new HashSet<>(Arrays.asList("OOP", "DBMS")), "f20212420@pilani.bits-pilani.ac.in", "nishant");
//
//        Student student3 = new Student("2021A7PS2223P", "Yash Sejpal", "BPharm", 6.76, new HashSet<>(Arrays.asList("OOP", "DSA")), "f20212223@pilani.bits-pilani.ac.in", "nishant");
//
//        users.add(admin);
//        users.add(student1);
//        users.add(student2);
//        users.add(student3);

//        System.out.println(login("f20212420@pilani.bits-pilani.ac.in", "nishant", users));
//
//        System.out.println(login("f20212420@pilani.bits-pilani.ac.in", "niserfefhant", users));
//
//        System.out.println(login("f202343420@pilani.bits-pilani.ac.in", "nishant", users));

        // PS_Station JioStation = new PS_Station("Jio", 2, "Mumbai", "None", new ArrayList<>(Arrays.asList("CS", "EEE")), new ArrayList<>(List.of("OOP")));




       // admin.addPS_Station(JioStation);

        // admin.showPSStationsList();

       // student1.submitPreferences(new ArrayList<>(List.of(JioStation)));
        //student2.submitPreferences(new ArrayList<>(List.of(JioStation)));
        //student3.submitPreferences(new ArrayList<>(List.of(JioStation)));

       // admin.addStudentToStudentsList(student1);
       // admin.addStudentToStudentsList(student2);
       // admin.addStudentToStudentsList(student3);

        // admin.performIteration();

//        for (User user : users) {
//            if (user instanceof Student) {
//                ((Student) user).viewDetailsOfCurrentAllotment();
//            }
//        }

      //  admin.updatePSStationDetails(student1.rejectAllotment(student1.getCurrentAllotment()));

        // admin.performIteration();

//        for (User user : users) {
//            if (user instanceof Student) {
//                ((Student) user).viewDetailsOfCurrentAllotment();
//            }
//        }
      /*  Scanner br = new Scanner(new FileInputStream("preferences.txt"));
        ArrayList<PS_Station> pref = new ArrayList<>();
        while (br.hasNextLine()) {
            String name = br.next();
            int capacity = br.nextInt();
            String location = br.next();
            String projectD = br.next();
            ArrayList<String> branchP = new ArrayList<>();
            while (br.hasNext()) {
                branchP.add(br.next());
            }
            ArrayList<String> compS = new ArrayList<>();
            while (br.hasNext()) {
                compS.add(br.next());
            }
            PS_Station p = new PS_Station(name, capacity, location, projectD, branchP, compS);

            ((ArrayList<PS_Station>) pref).add(p);
        }*/

    }
}

