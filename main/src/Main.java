import java.util.ArrayList;

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
        office.setManager(manager);

        for(int x=1; x<=3; x++){
            ArrayList<Employee> employees = new ArrayList<>();
            for(int y=4; y>=1; y--){
                if(y==1){
                    Employee tempLead = new Employee(x, y, true, manager, conferenceRoom, clock);
                    tempLead.addSubordinate(employees.get(1));
                    tempLead.addSubordinate(employees.get(0));
                    tempLead.addSubordinate(employees.get(2));
                    employees.get(0).setLead(tempLead);
                    employees.get(1).setLead(tempLead);
                    employees.get(2).setLead(tempLead);
                    employees.get(0).start();
                    employees.get(1).start();
                    employees.get(2).start();
                    tempLead.start();
                }
                else{
                    employees.add(new Employee(x, y, false, manager, conferenceRoom, clock));
                }
            }
        }

        clock.startClock();
        new Thread(manager).start();
    }
}