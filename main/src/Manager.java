import java.util.ArrayList;

/**
 * Created by John King on 12-Oct-16.
 */
public class Manager implements Runnable{

    private Clock clock;
    private Office office;
    private boolean isAnswerQuestion = false;

    public Manager( Clock clock, Office office){
        this.clock = clock;
        this.office = office;
    }


    @Override
    public void run() {
        office.managerReturns(); // manager is present in the office

       while(office.allArrived()){ // wait until all team leads arrive
           try {
               wait();
           } catch (InterruptedException e) {e.printStackTrace();}
       }

       office.runMorningMeeting(); // run the morning meeting




        /////////////    MORNING MEETING    //////////////////////////////////////


       int[] startMorningMeeting = {10,0};   // start and end time of morning meeting

        while (!clock.isSameTime(startMorningMeeting)) {
            try {
                wait();
            } catch (InterruptedException e) {e.printStackTrace();}
        }

        office.managerLeft(); // leaves for morning meeting 10:00 - 11:00


       int[] endMorningMeeting = {11,0};

        while(!clock.isSameTime(endMorningMeeting)){
            try {
                wait();
            } catch (InterruptedException e) {e.printStackTrace();}
        }

        office.managerReturns(); // manager returns from his morning meeting

        ///////////////// END OF MORNING MEETING ////////////////////////////////


    }
}
