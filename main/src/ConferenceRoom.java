import java.util.LinkedList;

/**
 * Created by John King on 12-Oct-16.
 */
public class ConferenceRoom{
    private LinkedList<Employee> employees;

    /**
     * Conference room that contains the employees holding it
     */
    public ConferenceRoom()
    {
        employees = new LinkedList<Employee>();
    }

    /**
     * Attempt to enter, if you can't then wait until it becomes free
     *
     * @param lead
     *        The lead requesting the room
     */
    public synchronized void request(Employee lead)
    {
        employees.offer(lead);

        while (!employees.peek().equals(lead)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    /**
     * Free the conference room
     *
     * @param lead
     *        The team lead freeing the room
     */
    public synchronized void leave(Employee lead)
    {
        employees.poll();
        notifyAll();
    }
}
