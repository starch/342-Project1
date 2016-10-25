import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by John King on 12-Oct-16.
 */
public class Office{
    public boolean isOfficeFree;
    public boolean managerPresent;
    public Queue<Employee> queue;
    public Queue<Employee> morningMeetingQueue;
    Clock clock;

    public Office(Clock c){
        isOfficeFree = false;
        managerPresent = true;
        queue = new LinkedList<>();
        morningMeetingQueue = new LinkedList<>();
        clock = c;
    }

    public synchronized int addMorningQueue(Employee t){
        morningMeetingQueue.add(t);
        notifyAll();
        return morningMeetingQueue.size();
    }

    /**
     * Returns a boolean determining whether or not all 3 team leads have arrived at the
     * office
     * @return
     */
    public synchronized boolean allArrived(){
        return morningMeetingQueue.size()==3;
    }

    /**
     * Called only by the manager class
     * This method waits until all team leads have arrived in the building
     * before starting the morning meeting.
     */
    public synchronized void waitForTeamLeads(){
        while(allArrived()){ // wait until all team leads arrive
            try {
                wait();
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }


    /**
     * Called by the Manager class, the manager runs the meeting for 15 minutes
     * and is available during the meeting.
     */
    public void runMorningMeeting(){
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

    /**
     * Called by the manager class and simulates the manager "waiting" until the given
     * start of the meeting and retuns once the meeting is over, which is given by the
     * "end" parameter.
     *
     * The boolean flag 'isGone' just detrmines whether or not the Manager is available
     * during the meeting or not
     */
    public synchronized void runMeeting(int[] start, int[] end, boolean isGone, String message){

        while (!clock.isSameTime(start)) {  // manager waits till his meeting begins
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {e.printStackTrace();}
        }

        System.out.println(message + clock.getTime()[0] + ":" + clock.getTime()[1]);

        if(isGone) {
            managerLeft();  // i'ts now time for the meeting, so the manager leaves
        }


        while(!clock.isSameTime(end)){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {e.printStackTrace();}
        }

        managerReturns(); // manager returns from his  meeting

    }

    /**
     * Manager calls this method after running the project status meeting
     * at the end of the day. This method just waits until it's closing time
     * and then the Manager thread terminates
     */
    public synchronized void leaveOffice(){
        while(clock.getState() != State.CLOSED){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }




    public void managerLeft(){
        managerPresent = false;
    }

    public void managerReturns(){
        managerPresent = true;
    }

    /**
     * This method is called by an Employee when they wish to ask the manager a
     * question.
     * @param t
     */
    public synchronized void acquire(Employee t) {
        queue.add(t);

        while(isOfficeFree || !managerPresent || queue.peek()!= t) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED EXCEPTION");
            }
        }
        isOfficeFree = true;
        queue.remove(t);
    }

    public synchronized void release() {
        isOfficeFree = false;
        notifyAll();
    }
}
