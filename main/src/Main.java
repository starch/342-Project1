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

        for(int x=1; x<=3; x++){
            for(int y=1; y<=4; y++){
                if(y==1){
                    new Employee(x, y, true, manager, conferenceRoom, clock).start();
                }
                else{
                    new Employee(x, y, false, manager, conferenceRoom, clock).start();
                }
            }
        }

        clock.startClock();
    }
}