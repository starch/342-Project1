import java.util.ArrayList;

/**
 * Created by John King on 12-Oct-16.
 */
public class Manager implements Runnable{
    private ArrayList<TeamLead> teamLeads;
    private Clock clock;
    private Office office;

    public Manager(ArrayList<TeamLead> teamLeads, Clock clock, Office office){
        this.teamLeads = teamLeads;
        this.clock = clock;
        this.office = office;
    }


    @Override
    public void run() {


       while(office.allArrived()){ // wait until all team leads arrive
           try {
               wait();
           } catch (InterruptedException e) {e.printStackTrace();}
       }

       office.runMorningMeeting(); // run the morning meeting

       int[] startMorningMeeting = {10,0};   // start and end time of morning meeting
       int[] endMorningMeeting = {11,0};

        boolean morningMeeting = false;
        while(!morningMeeting){
            if(startMorningMeeting.equals(clock.getTime())){

            }
        }



    }
}
