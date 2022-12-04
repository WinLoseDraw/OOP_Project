import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Admin extends User implements AdminActions, Runnable {
    private ArrayList<PS_Station> PS_StationsList;
    private PriorityQueue<Student> studentList;
    private int iterationNumber = 1;
    boolean running = true;
    Thread adminThread;
    private boolean stopThread = false;

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
        System.out.println("Press 3 to Add PS Station .");
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

        public void addStudentToStudentsList (){

        }

        public synchronized void updatePSStationDetails (){
        

        }

        public void addPS_Station (){ // TODO: Input from file handling

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
                    System.out.println("No PS Station available");
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
