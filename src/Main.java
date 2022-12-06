import java.util.*;
import java.io.*;
import java.util.ArrayList;


public class Main extends Thread {



    public static void startApp() throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter 1 for Admin Mode, Enter 2 for Student Mode, Enter 3 to Exit");
        String s = in.nextLine().trim();
        switch (s) {
            case "1" -> adminLogin();
            case "2" -> {
                System.out.println("Enter 1 to Login, Enter 2 to Register");
                String ch = in.nextLine().trim();
                if (ch.equals("1")) {
                    studentLogin();
                } else if (ch.equals("2")) {
                    studentRegister();
                }
            }
            case "3" -> System.exit(0);
            default -> System.out.println("Invalid Option. Program Terminating!!!");
        }
    }

    public static void adminLogin() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your UserID and Password ->");
        System.out.print("UserID: ");
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
            Admin admin = new Admin("admin", "admin");
            for (Student stu : admin.getStudentList()) {
                if (stu.getEmailId().equals(user1) && stu.getPassword().equals(pwd)) {
                    Thread thread = new Thread(stu);
                    thread.start();
                    Thread.currentThread().interrupt();
                }
            }
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


    }
}

