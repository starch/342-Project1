import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by John King on 12-Oct-16.
 */
public class Office{
    public boolean isHeld;
    public boolean managerPresent;
    public Queue<TeamLead> queue;
    public Queue<TeamLead> morningMeetingQueue;
    Clock clock;

    public Office(Clock c){
        isHeld = false;
        managerPresent = true;
        queue = new LinkedList<>();
        morningMeetingQueue = new LinkedList<>();
        clock = c;
    }

    public synchronized void addMorningQueue(TeamLead t){
        morningMeetingQueue.add(t);
        if(morningMeetingQueue.size()==3){
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

            while(clock.getTime() != endTime){

            }
        }
    }

    public void managerLeft(){
        managerPresent = false;
    }

    public void managerReturns(){
        managerPresent = true;
    }

    public synchronized void acquire(TeamLead t) {
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
