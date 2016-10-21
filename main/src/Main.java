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

        Employee teamLead1 = new Employee(1, 1, true, manager, conferenceRoom, clock);
        Employee teamLead2 = new Employee(2, 1, true, manager, conferenceRoom, clock);
        Employee teamLead3 = new Employee(3, 1, true, manager, conferenceRoom, clock);
        Employee employee12 = new Employee(1, 2, false, manager, conferenceRoom, clock);
        Employee employee13 = new Employee(1, 3, false, manager, conferenceRoom, clock);
        Employee employee14 = new Employee(1, 4, false, manager, conferenceRoom, clock);
        Employee employee22 = new Employee(2, 2, false, manager, conferenceRoom, clock);
        Employee employee23 = new Employee(2, 3, false, manager, conferenceRoom, clock);
        Employee employee24 = new Employee(2, 4, false, manager, conferenceRoom, clock);
        Employee employee32 = new Employee(3, 2, false, manager, conferenceRoom, clock);
        Employee employee33 = new Employee(3, 3, false, manager, conferenceRoom, clock);
        Employee employee34 = new Employee(3, 4, false, manager, conferenceRoom, clock);
    }
}