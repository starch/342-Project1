/**
 * Created by John King on 12-Oct-16.
 */
public class ConferenceRoom{
    public boolean isHeld;
    Clock clock;

    public ConferenceRoom(Clock c){
        isHeld = false;
        clock = c;
    }

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
