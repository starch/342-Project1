import java.util.ArrayList;

/**
 * Created by John King on 12-Oct-16.
 */
public class Main {
    public static void main(String args[]) {
        Clock clock = Clock.createClock();

        ConferenceRoom conferenceRoom = new ConferenceRoom();

        // List of all threads in the system
        ArrayList<Thread> threadList = new ArrayList<Thread>();

        // List of all team leads
        ArrayList<Employee> leads = new ArrayList<Employee>();

        Manager manager = new Manager(clock);
        threadList.add(manager);

        for (int x = 1; x <= 3; x++) {
            Employee tempLead = new Employee(x, 1, true, manager, conferenceRoom, clock);
            leads.add(tempLead);
            for (int y = 2; y <= 4; y++) {
                Employee dev = new Employee(x, y, false, manager, conferenceRoom, clock);
                tempLead.addSubordinate(dev);
                threadList.add(dev);
            }
            threadList.add(tempLead);
            tempLead.provideSubordinatesBarrier();
        }

        // The manager needs to know about the team leads to be able to synchronize with them
        manager.insertLeads(leads);

        // Start all the threads
        for (Thread t : threadList) {
            t.start();
        }

        // Join the threads
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e.getStackTrace());
            }
        }

        // Print out each thread's stats
        System.out.println();
        System.out.println(manager.getStats());

        for (Employee lead : leads) {
            System.out.println(lead.getStats());
            for (Employee dev : lead.getSubordinates()) {
                System.out.println(dev.getStats());
            }
        }
    }
}
