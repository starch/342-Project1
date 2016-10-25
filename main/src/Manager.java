import java.util.ArrayList;

/**
 * Created by John King on 12-Oct-16.
 */
public class Manager implements Runnable{

    private Clock clock;
    public Office office;
    private ConferenceRoom conferenceRoom;
    private boolean isAnswerQuestion = false;

    public Manager( Clock clock, Office office, ConferenceRoom c){
        this.clock = clock;
        this.office = office;
        conferenceRoom = c;
    }


    @Override
    public void run() {
        clock.waitForOpening();

        office.managerReturns(); // manager is present in the office
        System.out.printf("Manager -> Enters the office at %d:%d \n", clock.getTime()[0], clock.getTime()[1]);

        System.out.println("Manager -> waits for team leads");
        office.waitForTeamLeads(); // wait for team leads to arrive before morning meeting


        String m1 = "Manager -> runs standup meeting at ";
        office.runMeeting(clock.getTime(),clock.getEndOfMeeting(15),false,m1); // runs the morning meeting for 15 minutes


        /////////////    MORNING MEETING
        String m2 = "Manager -> runs morning meeting at ";
        int[] startAM = {10,0};
        int[] endAM = {11,0};
        office.runMeeting(startAM,endAM,true,m2);
        System.out.printf("Manager -> returns from morning meeting at %d:%d \n",clock.getTime()[0], clock.getTime()[1]);

        ////////////////// Beginning of Lunch

        String m3 = "Manager -> goes out for lunch at ";

        int[] starLunch = {12,0};
        int[] endLunch = {1,0};
        office.runMeeting(starLunch,endLunch,true,m3);
        System.out.printf("Manager -> returns from lunch at %d:%d \n",clock.getTime()[0], clock.getTime()[1]);


        ////////////////   Beginning of Afternoon Meeting
        String m4 = "Manager -> leaves for afternoon meeting at ";

        int[] startPM = {2,0};
        int[] endPM = {3,0};
        office.runMeeting(startPM,endPM,true,m4);
        System.out.printf("Manager -> returns from afternoon meeting at %d:%d \n",clock.getTime()[0], clock.getTime()[1]);

        /////////////////   project status meeting

        String m5 = "Manager -> leads project status meeting at ";

        int[] startProjectStatus = {4,15};
        int[] endProjectStatus = {4,30};
        office.runMeeting(startProjectStatus,endProjectStatus,false,m5);
        System.out.printf("Manager -> finishes project status meeting at %d:%d \n",clock.getTime()[0], clock.getTime()[1]);

        ////////////////// leaving the office

        office.leaveOffice();
        System.out.printf("Manager -> leaves the office at %d:%d \n",clock.getTime()[0], clock.getTime()[1]);


    }

}
