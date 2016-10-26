/**
 * Created by John King on 12-Oct-16.
 */

public class Clock extends Thread{
    private static Clock instance = null;
    private int minute = 0;

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
     * @return the current time in ms
     */
    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * Calcuates the total elapsed time in minutes from the current time (ms) - input time (ms)
     * @param startTime
     * @return elaspsedTime in minutes
     */
    public static int elapsedTime(long startTime) {
        return (int) ((System.currentTimeMillis() - startTime)/10L);
    }

    public int getMinute(){
        return minute;
    }

    @Override
    public void run() {
        minute = 480;
        while(minute<=1020){
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            minute+=1;
        }
    }
}

