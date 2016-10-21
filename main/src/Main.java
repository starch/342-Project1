import static sun.misc.Version.println;

/**
 * Created by John King on 12-Oct-16.
 */
public class Main {
    public static void main(String args[]){
        final int NUM_EMPLOYEES = 9;
        final int NUM_TEAM_LEADS = 3;
        int teamLeadCount = 0;
        int employeeCount = 0;
        Clock clock = Clock.createClock();

        Office office = new Office(clock);
        ConferenceRoom conferenceRoom = new ConferenceRoom(clock);

        Manager manager = new Manager(clock, office, conferenceRoom);
    }
}
