/**
 * Created by nate on 10/16/16.
 */
public class Timer implements Runnable{

    private Clock clock;
    private final int lengthOfDay = 540; // 540 minutes in a 9 hour day (8am - 5pm)

    public Timer(Clock clock){
        this.clock = clock;
    }


    @Override
    public void run() {
            int currHour = 8; // our day starts at 8:00am
        System.out.println("---------------- START OF DAY ---------------- \n");
            for(int minute=0;minute<=lengthOfDay;minute++){
            try {
                Thread.sleep(10);
                if(minute % 60 == 0){  //update hour every 60 minutes
                    if(currHour == 13){
                        currHour = 1; // so we are not dealing with military time
                    }
                    clock.updateHour(currHour);
                    System.out.println(" \n ---------------- "+ currHour + ":00" +" ---------------- \n");
                    currHour++;
                }
                clock.updateMinutes(minute%60); // update minute ever 10 milliseconds

            } catch (InterruptedException e) {e.printStackTrace();}
        }

    }
}
