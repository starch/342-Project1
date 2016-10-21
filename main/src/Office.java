import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by John King on 12-Oct-16.
 */
public class Office{
    public boolean isHeld;
    public boolean managerPresent;
    public Queue<Employee> queue;
    public Queue<Employee> morningMeetingQueue;
    Clock clock;

    public Office(Clock c){
        isHeld = false;
        managerPresent = true;
        queue = new LinkedList<>();
        morningMeetingQueue = new LinkedList<>();
        clock = c;
    }

    public synchronized void addMorningQueue(Employee t){
        morningMeetingQueue.add(t);
        notifyAll();
    }

    public synchronized boolean allArrived(){
        return morningMeetingQueue.size()==3;
    }

    /**
     * Called only by the manager class
     */
    public void waitForTeamLeads(){
        while(allArrived()){ // wait until all team leads arrive
            try {
                wait();
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }


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
     */
    public void runMeeting(int[] start, int[] end, boolean isGone){


        while (!clock.isSameTime(start)) {  // manager waits till his meeting begins
            try {
                wait();
            } catch (InterruptedException e) {e.printStackTrace();}
        }

        if(isGone) {
            managerLeft();  // i'ts now time for the meeting, so the manager leaves
        }


        while(!clock.isSameTime(end)){
            try {
                wait();
            } catch (InterruptedException e) {e.printStackTrace();}
        }

        managerReturns(); // manager returns from his  meeting

    }

    public void leaveOffice(){
        while(clock.getState() != State.CLOSED){
            try {
                wait();
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }




    public void managerLeft(){
        managerPresent = false;
    }

    public void managerReturns(){
        managerPresent = true;
    }

    public synchronized void acquire(Employee t) {
        queue.add(t);

        while(isHeld || !managerPresent || queue.peek()!= t) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED EXCEPTION");
            }
        }
        isHeld = true;
        queue.remove(t);
    }

    public synchronized void release() {
        isHeld = false;
        notifyAll();
    }
}
