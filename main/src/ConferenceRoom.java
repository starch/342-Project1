/**
 * Created by John King on 12-Oct-16.
 */
public class ConferenceRoom implements IResource{
    public boolean isHeld = false;

    @Override
    public void acquire() {
        while(isHeld) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED EXCEPTION");
            }
        }
        isHeld = true;
    }

    @Override
    public void release() {
        isHeld = false;
        notifyAll();
    }
}
