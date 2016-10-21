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
        office.managerReturns(); // manager is present in the office

        office.waitForTeamLeads(); // wait for team leads to arrive before morning meeting

        office.runMorningMeeting(); // run the morning meeting

        /////////////    MORNING MEETING

        int[] startAM = {10,0};
        int[] endAM = {11,0};
        office.runMeeting(startAM,endAM,true);

        ////////////////// Beginning of Lunch

        int[] starLunch = {12,0};
        int[] endLunch = {1,0};
        office.runMeeting(starLunch,endLunch,true);



        ////////////////   Beginning of Afternoon Meeting

        int[] startPM = {2,0};
        int[] endPM = {3,0};
        office.runMeeting(startPM,endPM,true);

        /////////////////   project status meeeting

        int[] startProjectStatus = {4,15};
        int[] endProjectStatus = {4,30};
        office.runMeeting(startProjectStatus,endProjectStatus,false);

        ////////////////// leaving the office

        office.leaveOffice();

    }

}
