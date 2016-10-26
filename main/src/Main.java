import java.util.ArrayList;

/**
 * Created by John King on 12-Oct-16.
 */
public class Main {
    public static void main(String args[]) {
        final int NUM_EMPLOYEES = 9;
        final int NUM_TEAM_LEADS = 3;
        int teamLeadCount = 0;
        int employeeCount = 0;
        Clock clock = Clock.createClock();

        ConferenceRoom conferenceRoom = new ConferenceRoom();

        // List of all domain threads in the system
        ArrayList<Thread> threadList = new ArrayList<Thread>();

        // List of employees acting as team leads
        ArrayList<Employee> leads = new ArrayList<Employee>();

        Manager manager = new Manager(clock, conferenceRoom);
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

        // The manager needs to know about the team leads to synchronize
        // the initial meeting
        manager.injLeads(leads);

        // Start all the threads
        for (Thread t : threadList) {
            t.start();
        }

        // Join the threads
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }

        // Once all threads have finished, print out each thread's
        // statistics report
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
