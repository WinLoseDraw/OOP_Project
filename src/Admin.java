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


    public void start() {
        if (adminThread == null) {
            this.adminThread = new Thread(this, "admin");
            this.adminThread.start();
        }
    }
        public void run() {
                while (true) {
                    if (stopThread) {System.out.println("Ending admin"); break;}               
               
                    System.out.println("Welcome Admin");
                    Scanner adminScanner = new Scanner(System.in);      
                             Main.adminMenu(adminScanner);
                
            }
        }
        public void setStopThread ( boolean stopThread){
            this.stopThread = stopThread;
        }

     public Admin(String emailId, String password) {
            super(emailId, password);
            PS_StationsList = new ArrayList<>();
            studentList = new PriorityQueue<>((o1, o2) -> (int) (o2.getCgpa() - o1.getCgpa()));
        }

        public void addStudentToStudentsList (Student student){
            studentList.offer(student);
        }

        public void updatePSStationDetails (PS_Station updatedStation){
            synchronized (update) {
                for (int i = 0; i < PS_StationsList.size(); i++) {
                    if (updatedStation.getName().equals(PS_StationsList.get(i).getName())) {
                        PS_StationsList.set(i, updatedStation);
                    }
                }
            }
        }

        public void addPS_Station (PS_Station station){ // TODO: Input from file handling
            PS_StationsList.add(station);
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

    }
    public static boolean VerifyAdminLogin (String user1, String pswd) throws FileNotFoundException {
        boolean t = false;
        Scanner sc = new Scanner(new FileInputStream("AdminLoginDetails.txt"));
        while (sc.hasNextLine()) {
            if ((sc.nextLine() == user1) && (sc.nextLine() == pswd)) {
                t = true;
                break;
            }

        }
        return t;
    }
}
