/**
 * Created by John King on 12-Oct-16.
 */
public class Clock {
    private long startime = 0;
    private static Clock instance = null;

    // this Enum represents all the states the system can be in
    public enum States{
        ARRIVAL_TIME, LUNCHTIME, MANDATORY_MEETING, WORK_TIME
    }

    /**
     * This private method exists, just so that a developer cannot
     * create a clock object the traditional way with "new Clock();"
     * but instead is forced to use the createClock() method
     */
    private Clock(){}

    /**
     * Ensures that our clock class is a singleton. If there is no clock created
     * method will return a new clock object, otherwise it will return null
     * @return
     */
    public static Clock createClock(){
        if(instance == null){
            instance = new Clock();
        }
        else{
            System.err.println("There can only be one clock instance");
        }
        return instance;
    }


    /**
     * When the Clock object is started, the current time in milliseconds
     * is logged and used later to calculate the current time in the simulation
     */
    public void start(){
        startime = System.currentTimeMillis();
    }


    /**
     * is called by an employee and the current time and state is calculated
     * and the enum representing which block of time it is is returned
     */
    public void getTime(){ // TODO: change signature

    }




}
