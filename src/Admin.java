import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

public class Admin extends User implements AdminActions, Runnable {
    private ArrayList<PS_Station> PS_StationsList;
    private PriorityQueue<Student> studentList;
    private int iterationNumber = 1;
    boolean running = true;
    Thread adminThread;
    private boolean stopThread = false;

    public PriorityQueue<Student> getStudentList() {
        return studentList;
    }

    public void makePSList() throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream("src/PS_STATION.txt"));
        while (sc.hasNextLine()) {
            String name = sc.nextLine();
            int capacity = Integer.parseInt(sc.nextLine().trim());
            String location = sc.nextLine();
            String projectD = sc.nextLine();
            String bp = sc.nextLine().trim();
            String[] branchP = bp.split(",");
            ArrayList<String> branchPreference = new ArrayList<>(Arrays.asList(branchP));
            String sub = sc.nextLine().trim();
            String[] subjects = sub.split(",");
            ArrayList<String> subjectsCompleted = new ArrayList<>(Arrays.asList(subjects));


            PS_Station p = new PS_Station(name, capacity, location, projectD, branchPreference, subjectsCompleted);
            PS_StationsList.add(p);

        }
    }



    public void start() {
        if (adminThread == null) {
            this.adminThread = new Thread(this, "admin");
            this.adminThread.start();
        }
    }
        public void run() {
                while (true) {
                    if (stopThread) {System.out.println("Ending admin"); break;}               
               
                  try {
                        forward();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                  }
            }
        }
        public void setStopThread ( boolean stopThread){
            this.stopThread = stopThread;
        }
    
    public void forward() throws Exception {
        System.out.println("Admin Mode");
        Scanner in = new Scanner(System.in);
        System.out.println("Press 1 to Add Students.");
        System.out.println("Press 2 to Update PS Station Details.");
        System.out.println("Press 3 to Add PS Station.");
        System.out.println("Press 4 to Show PS Station.");
        System.out.println("Press 5 to Perform Iteration.");
        String s = in.nextLine().trim();
        if (s.equals("1")) {
            addStudentToStudentsList();
        } else if (s.equals("2")) {
            updatePSStationDetails();
        } else if (s.equals("3")) {
            addPS_Station();
        } else if (s.equals("4")) {
            showPSStationsList();
        } else if (s.equals("5")) {
            performIteration();
    }
    }

     public Admin(String emailId, String password) throws FileNotFoundException {
            super(emailId, password);
            PS_StationsList = new ArrayList<>();
            studentList = new PriorityQueue<>((o1, o2) -> (int) (o2.getCgpa() - o1.getCgpa()));
            makePQ();
            makePSList();
        }

        public void addStudentToStudentsList () throws FileNotFoundException {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter Student Details below : ");
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
            double cg= Double.parseDouble(in.nextLine().trim());
            System.out.print("Subjects Completed: ");
            //TODO: take subjects completed input
            String sub=in.nextLine().trim();
            String[] subjects=sub.split(",");
            HashSet<String> subjectsCompleted = new HashSet<>(Arrays.asList(subjects));
            System.out.println("Student added Successfully!");
            Student user = new Student(id, name, branch, cg,subjectsCompleted, email, pswd);
            studentList.offer(user);

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

        public synchronized void updatePSStationDetails (){
        

        }

        public void addPS_Station () throws FileNotFoundException { // TODO: Input from file handling
            Scanner in = new Scanner(System.in);
            System.out.println("Enter PS Station Details below : ");
            System.out.print("Name: ");
            String name = in.nextLine().trim();
            System.out.print("Capacity: ");
            int cap = Integer.parseInt(in.nextLine().trim());
            System.out.print("Location: ");
            String loc = in.nextLine().trim();
            System.out.print("Project Description: ");
            String desc = in.nextLine().trim();
            System.out.print("Branch Preference: ");
            String branch= in.nextLine().trim();
            ArrayList<String> branchP = new ArrayList<>(Arrays.asList(branch.split(",")));
            System.out.print("Compulsory Subjects: ");
            String comps= in.nextLine().trim();
            ArrayList<String> compS = new ArrayList<>(Arrays.asList(comps.split(",")));
            System.out.println("PS Station Added Successfully!");
            PS_Station station = new PS_Station(name, cap, loc, desc, branchP, compS);
            PS_StationsList.add(station);

            PrintWriter ab=new PrintWriter(new FileOutputStream("src/PS_STATION.txt",true));
            ab.println(name);
            ab.println(cap);
            ab.println(loc);
            ab.println(desc);
            ab.println(branch);
            ab.println(comps);
            ab.close();
        }

        public void showPSStationsList () {
            for (PS_Station station : PS_StationsList) {
                station.showDetailsOfStation();
            }
        }

        public synchronized void performIteration () {

            System.out.println("Iteration " + iterationNumber + " taking place...");

            iterationNumber++;

            PriorityQueue<Student> tempStudentList = new PriorityQueue<>((o1, o2) -> (int) (o2.getCgpa() - o1.getCgpa()));

            for (Student student : studentList) {
                tempStudentList.offer(student);
            }

            while (!tempStudentList.isEmpty()) {

                Student highestCgStudent = tempStudentList.poll();
                ArrayList<PS_Station> highestCgStudentPreferences = highestCgStudent.getPreferences();
                int counter = 0;

                if (highestCgStudentPreferences == null) {
                    System.out.println(highestCgStudent.getName() + " has not updated their preferences.");
                    continue;
                }

                while (!highestCgStudent.getAllotted() && counter < highestCgStudentPreferences.size()) {
                    PS_Station highestCgStudentTopPreference = highestCgStudentPreferences.get(counter);
                    if (highestCgStudentTopPreference.getCapacity() > 0) {
                        if (highestCgStudent.getSubjectsCompleted().containsAll(highestCgStudentTopPreference.getCompulsorySubjects())) {
                            if (highestCgStudentTopPreference.getBranchPreference().contains(highestCgStudent.getBranch())) {
                                studentList.remove(highestCgStudent);
                                highestCgStudent.setAllotted(true);
                                highestCgStudent.setCurrentAllotment(highestCgStudentTopPreference);
                                studentList.offer(highestCgStudent);
                                for (PS_Station station : PS_StationsList) {
                                    if (station.getName().equals(highestCgStudentTopPreference.getName())) {
                                        station.decrementCapacity();
                                    }
                                }
//                        highestCgStudentTopPreference.setCapacity();
//                        highestCgStudentPreferences.set(counter, highestCgStudentTopPreference);
                            } else {
                                counter++;
                            }
                        }
                    }
                }

                if (!highestCgStudent.getAllotted()) {
                    System.out.println("No PS Station available for " + highestCgStudent.getName());
                }
            }

            System.out.println("Iteration " + (iterationNumber - 1) + " completed.");
            System.out.println();
        }


    public static boolean VerifyAdminLogin (String user1, String pswd) throws FileNotFoundException {
        boolean t = false;
        Scanner sc = new Scanner(new FileInputStream("src/AdminLoginDetails.txt"));
        while (sc.hasNextLine()) {
            if ((sc.next().equals(user1) && (sc.next().equals(pswd)))) {
                t = true;
                break;
            }

        }
        return t;
    }
    public void makePQ() throws FileNotFoundException {
        Scanner p=new Scanner(new FileInputStream("StudentDetails.txt"));
        while(p.hasNextLine()) {
            String em = p.nextLine().trim();
            String pswd = p.nextLine().trim();
            String name = p.nextLine().trim();
            String id = p.nextLine().trim();
            String branch = p.nextLine().trim();
            double cg = Double.parseDouble(p.nextLine().trim());
            String sub = p.nextLine().trim();
            String[] subjects = sub.split(",");
            HashSet<String> subjectsCompleted = new HashSet<>(Arrays.asList(subjects));
            Student user = new Student(id, name, branch, cg, subjectsCompleted, em, pswd);
            studentList.offer(user);
        }

    }
}
