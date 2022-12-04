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
		try {
            forward();
        } 
	    catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
    
    public void setStopThread(boolean stopThread) {
		this.stopThread = stopThread;
	}
	

	public void forward() throws Exception {
        System.out.println("User Mode");
        Scanner in = new Scanner(System.in);
        System.out.println("Press 1 to Submit preferences.");
        System.out.println("Press 2 to Get Current Allotment.");
        System.out.println("Press 3 to Accept Allotment.");
        System.out.println("Press 4 to Reject Allotment.");
        String s = in.nextLine().trim();
        if (s.equals("1")) {
            submitPreferences();
        } else if (s.equals("2")) {
           viewDetailsOfCurrentAllotment();
        } else if (s.equals("3")) {
             acceptAllotment();
        } else if (s.equals("4")) {
           // rejectAllotment();
        }
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

    public void submitPreferences() throws FileNotFoundException { // TODO: COMPLETE
        System.out.println("Enter the name of file");
        Scanner sc=new Scanner(System.in);
        String file=sc.next();
        sc.close();
        Scanner in=new Scanner(new FileInputStream(file));
        ArrayList<PS_Station> pref=new ArrayList<>();
        while (in.hasNextLine())
        {
            String name = in.nextLine().trim();
            int cap = Integer.parseInt(in.nextLine().trim());
            String loc = in.nextLine().trim();
            String desc = in.nextLine().trim();
            String branch= in.nextLine().trim();
            ArrayList<String> branchP = new ArrayList<>(Arrays.asList(branch.split(",")));
            String comps= in.nextLine().trim();
            ArrayList<String> compS = new ArrayList<>(Arrays.asList(comps.split(",")));
            PS_Station station = new PS_Station(name, cap, loc, desc, branchP, compS);
            preferences.add(station);
        }

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
            if((sc.nextLine().equals(user1)&&(sc.nextLine().equals(pswd))))
            {
                b=true;
                break;
            }

        }
        return b;
    }

}
