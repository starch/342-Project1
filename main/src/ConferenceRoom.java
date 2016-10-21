import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by John King on 12-Oct-16.
 */
public class ConferenceRoom{
    public boolean isHeld;
    Clock clock;
    Queue<Employee> statusMeetingQueue = new LinkedList<>();
    Queue<Employee> team1MeetingQueue = new LinkedList<>();
    Queue<Employee> team2MeetingQueue = new LinkedList<>();
    Queue<Employee> team3MeetingQueue = new LinkedList<>();

    public ConferenceRoom(Clock c){
        isHeld = false;
        clock = c;
    }

    /////////////    TEAM MEETINGS    //////////////////////////////////////

    public synchronized void addTeamMeetingQueue(Employee t, int team){
        if(team == 1) {
            team1MeetingQueue.add(t);
        }
        else if(team == 2){
            team2MeetingQueue.add(t);
        }
        else{
            team3MeetingQueue.add(t);
        }
        notifyAll();
    }

    public synchronized boolean allTeamArrived(int team){
        if(team == 1) {
            return team1MeetingQueue.size()==4;
        }
        else if(team == 2){
            return team2MeetingQueue.size()==4;
        }
        else{
            return team3MeetingQueue.size()==4;
        }
    }

    public void runTeamMeeting(){
        int[] currentTime = clock.getTime();
        int[] endTime = new int[2];
        endTime[1] = currentTime[1]+15;
        if(endTime[1]>=60){
            endTime[1] = endTime[1]%60;
            endTime[0] += 1;
        }
        else{
            endTime[0] = currentTime[0];
        }

        boolean rightTime = false;
        while(!rightTime){
            if(clock.getTime() == endTime){
                rightTime = true;
            }
        }
    }

    /////////////    END TEAM MEETINGS    //////////////////////////////////////

    /////////////    STATUS MEETING    //////////////////////////////////////

    public synchronized void addStatusMeetingQueue(Employee t){
        statusMeetingQueue.add(t);
        notifyAll();
    }

    public synchronized boolean allStatusArrived(){
        return statusMeetingQueue.size()==13;
    }

    public void runStatusMeeting(){
        int[] currentTime = clock.getTime();
        int[] endTime = new int[2];
        endTime[1] = currentTime[1]+15;
        if(endTime[1]>=60){
            endTime[1] = endTime[1]%60;
            endTime[0] += 1;
        }
        else{
            endTime[0] = currentTime[0];
        }

        boolean rightTime = false;
        while(!rightTime){
            if(clock.getTime() == endTime){
                rightTime = true;
            }
        }
    }

    /////////////    END STATUS MEETING    //////////////////////////////////////

    public synchronized void acquire() {
        while(isHeld) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED EXCEPTION");
            }
        }
        isHeld = true;
    }

    public synchronized void release() {
        isHeld = false;
        notifyAll();
    }
}
