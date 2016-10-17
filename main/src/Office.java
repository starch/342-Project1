import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by John King on 12-Oct-16.
 */
public class Office{
    public boolean isHeld;
    public boolean managerPresent;
    public Queue<TeamLead> queue;

    public Office(){
        isHeld = false;
        managerPresent = true;
        queue = new LinkedList<>();
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
