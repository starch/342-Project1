/**
 * Created by John King on 12-Oct-16.
 */

public class Clock {
    private static Clock instance = null;
    private State state = null;
    private int hour = 8;
    private int minute = 0;


    // this Enum represents all the states the system can be in


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
    public void startClock(){
        new Thread( new Timer(this) ).start(); // creates and runs a Timer object
    }

    /**
     * returns a list in the format of [hour,minute].
     * @return
     */
    public int[] getTime(){
        int[] currentTime = {hour, minute};
        return currentTime;
    }

    /**
     * These two update methods are only used by the Timer class, which updates
     * our "hour" and "minute" variables in the Clock class
     * @param minutes
     */
    public void updateMinutes(int minutes){
        this.minute = minutes;
    }
    public void updateHour(int hour){
        this.hour = hour;
    }

    /**
     * This method returns one of the basic states our system can be in.
     * @return
     */
    public State getState(){


        if (hour == 8 && minute <= 30) {
            state = State.ARRIVAL_TIME;
        } else if (hour == 4) {
            if (minute >= 30)
                state = State.DEPARTURE_TIME;
            else
                state = State.MANDATORY_MEETING;
        } else if (hour == 5) {
            state = State.CLOSED;
        } else {
            state = State.WORK_TIME;  // default value is work time
        }

        return state;

    }



}

