import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Student extends User implements StudentActions, Runnable {

    private final String id;
    private final String name;
    private final String branch;
    private final double cgpa;
    private boolean isAllotted = false;
    private PS_Station currentAllotment = null;
    private final HashSet<String> subjectsCompleted;
    private ArrayList<PS_Station> preferences;
    boolean running = true;
	Thread studThread;
    private boolean stopThread = false;

    private static String userio = "RegStudents.txt";

    public Student(String id, String name, String branch, double cgpa, HashSet<String> subjectsCompleted, String emailId, String password) {
        super(emailId, password);
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.cgpa = cgpa;
        this.subjectsCompleted = subjectsCompleted;
        //this.running =true;
    }
    
    public void start() {
		if(studThread==null) {
			this.studThread = new Thread(this,this.name);
			this.studThread.start();
		}
	}
    //Overriding 
    public void run() {
		while(true) {
			if(stopThread) {System.out.println("Ending student"); break;}
			
			Scanner studentScanner = new Scanner(System.in);
				System.out.println("Welcome " + this.name);
				Main.studentMenu(studentScanner,this);	
		}
	}
    
    public void setStopThread(boolean stopThread) {
		this.stopThread = stopThread;
	}
	

    public synchronized PS_Station getCurrentAllotment() {
        return currentAllotment;
    }

    public synchronized void setCurrentAllotment(PS_Station currentAllotment) {
        this.currentAllotment = currentAllotment;
    }

    public synchronized boolean getAllotted() {
        return isAllotted;
    }

    public void setAllotted(boolean allotted) {
        isAllotted = allotted;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCgpa() {
        return this.cgpa;
    }

    public String getBranch() {
        return this.branch;
    }

    public ArrayList<PS_Station> getPreferences() {
        return this.preferences;
    }

    public HashSet<String> getSubjectsCompleted() {
        return this.subjectsCompleted;
    }

    public void submitPreferences(ArrayList<PS_Station> preferences) { // TODO: COMPLETE
        this.preferences = preferences;
    }

    public synchronized void acceptAllotment() {
        this.isAllotted = true;
    }

    public synchronized PS_Station rejectAllotment(PS_Station station) {
        this.preferences.remove(0);
        station.incrementCapacity();
        this.isAllotted = false;
        this.currentAllotment = null;
        return station;
    }

    public void viewDetailsOfCurrentAllotment() {
        try {
            this.currentAllotment.showDetailsOfStation();
        } catch (Exception e) {
            System.out.println("No PS allotted currently");
            System.out.println();
        }
    }
    public static boolean VerifyStudentLogin(String user1,String pswd) throws FileNotFoundException {
        boolean b=false;
        Scanner sc=new Scanner(new FileInputStream("StudentLoginDetails.txt"));
        while(sc.hasNextLine()){
            if((sc.nextLine()==user1)&&(sc.nextLine()==pswd))
            {
                b=true;
                break;
            }

        }
        return b;
    }

}
