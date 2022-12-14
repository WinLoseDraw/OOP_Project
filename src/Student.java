import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
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
        this.preferences = new ArrayList<>();
    }

    public void addToPreferences(PS_Station station) {
        this.preferences.add(station);
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
        switch (s) {
            case "1" -> submitPreferences();
            case "2" -> viewDetailsOfCurrentAllotment();
            case "3" -> acceptAllotment();
            case "4" -> rejectAllotment();
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
        PrintWriter out = new PrintWriter(new FileOutputStream("src/StudentPreferences.txt", true));
        out.println(this.getName());
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
            out.println(name);
        }
        out.println("-----");
        out.close();
    }

    public synchronized void acceptAllotment() {
        this.isAllotted = true;
        System.out.println("Allotment accepted.");
    }

    public synchronized void rejectAllotment() throws FileNotFoundException {
        this.preferences.remove(0);
        // station.incrementCapacity();
        this.isAllotted = false;
        PrintWriter pr = new PrintWriter(new FileOutputStream("temp.txt"));
        Scanner sc = new Scanner(new FileInputStream("src/Allotments.txt"));
        while (sc.hasNextLine()) {
            pr.println(sc.nextLine().trim());
        }
        sc.close();
        pr.close();
        PrintWriter a = new PrintWriter(new FileOutputStream("src/Allotments.txt"));
        Scanner c = new Scanner(new FileInputStream("temp.txt"));
        while(c.hasNextLine()) {
            String line = c.nextLine();
            if (!line.equals(this.getName()) && !line.equals(this.currentAllotment.getName())) {
                a.println(line);
            }
        }
        a.close();
        c.close();
        System.out.println("Allotment rejected.");
        this.currentAllotment = null;
        // return station;
    }

    public void viewDetailsOfCurrentAllotment() throws FileNotFoundException {
        boolean flag = true;
        Scanner sc = new Scanner(new FileInputStream("src/Allotments.txt"));
        while (sc.hasNextLine() && flag) {
            String name = sc.nextLine().trim();
            String allotment = sc.nextLine().trim();
            if (this.getName().equals(name)) {
                System.out.println(name + " was allotted " + allotment);
                flag = false;
            }
        }
        if (flag) {
            System.out.println("No PS Station allotted currently");
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
