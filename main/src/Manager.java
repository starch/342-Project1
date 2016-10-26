import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by John King on 12-Oct-16.
 */
public class Manager extends Thread {

    /**
     *  Clock, to reference time of the system
     */
    private Clock clock;

    /**
     * The current minute of the simulation
     */
    private int minute;

    /**
     * Barrier for morning stand-up
     */
    private CyclicBarrier morningBarrier;

    /**
     * Barrier for status meeting
     */
    private CyclicBarrier statusBarrier;

    /**
     * Time spent for lunch
     */
    private int lunchTime = 0;

    /**
     * Time spent in meetings and waiting for meetings
     */
    private int meetingTime = 0;

    /**
     * Time spent working
     */
    private int workingTime = 0;

    /**
     * String that represents the stats of the manager
     */
    private String managerStats;

    /**
     * Blocking Queue for employees
     */
    private BlockingQueue<Employee> blockQueue;

    // Set times for the Manager's tasks during the day
    private static final int START = 480;

    private static final int START_MORN_EXEC = 600;
    private static final int END_MORN_EXEC = 660;

    private static final int START_LUNCH = 720;
    private static final int END_LUNCH = 780;

    private static final int START_AFTER_EXEC = 840;
    private static final int END_AFTER_EXEC = 900;

    private static final int SETUP_STATUS = 960;
    private static final int START_STATUS = 975;
    private static final int END_STATUS = 990;

    private static final int END = 1020;

    public Manager( Clock clock){
        this.clock = clock;
        blockQueue = new LinkedBlockingQueue<Employee>();
    }

    /**
     * Sets the team leads and gives the barriers to those instances.
     *
     * @param leads
     */
    public void insertLeads(List<Employee> leads)
    {
        morningBarrier = new CyclicBarrier(1 + leads.size());

        List<Employee> employees = new ArrayList<Employee>();

        for (Employee lead : leads) {
            lead.setMorningBarrier(morningBarrier);

            employees.add(lead);
            employees.addAll(lead.getSubordinates());
        }

        statusBarrier = new CyclicBarrier(1 + employees.size());

        for (Employee employee : employees) {
            employee.setStatusBarrier(statusBarrier);
        }
    }

    /**
     * This static helper method encapsulates all the error states of
     * waiting on a barrier
     */
    private static int await(CyclicBarrier b)
    {
        long timeStamp = System.currentTimeMillis();

        try {
            b.await();
        } catch (InterruptedException e) {
            System.out.println("Exception: " + e);
        } catch (BrokenBarrierException e) {
            System.out.println("Exception: " + e);
        }

        return (int) ((System.currentTimeMillis()-timeStamp)/10L);
    }

    /**
     * The Manager calls this method when he is available to answer questions
     *
     * @param until
     *        The time to block until
     */
    private void answerQuestions(int until)
    {
        long timeStamp;
        int diff;

        // If there's a difference between the current time and the next event, block for the difference
        while ((diff = until - minute) > 0) {
            try {
                timeStamp = clock.getTimeStamp();

                int m = diff*10;
                Employee e = blockQueue.poll(m, TimeUnit.MILLISECONDS);

                if (e == null) {
                    // No questions were asked
                } else {
                    String answer = String.format("%d: The Manager begins answering Employee %d%d's question.", minute, e.getTeamNumber(), e.getEmployeeNumber());
                    System.out.println(answer);

                    // Sleep the 10 minutes it takes to answer the question
                    Thread.sleep(10*10);

                    // Manager thread needs lock to notify employee threads
                    synchronized (this) {
                        notifyAll();
                    }
                }
                int elapsed = clock.elapsedTime(timeStamp);

                minute += elapsed;
                workingTime += elapsed;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run()
    {
        minute = START;

        String arrive = String.format("%d: The Manager arrives at work.", minute);
        System.out.println(arrive);

        String administrivia = String.format("%d: The Manager begins doing administrivia.", minute);
        System.out.println(administrivia);

        // Wait for leads to gather around door
        minute += await(morningBarrier);
        morningBarrier.reset();

        // The leads enter the manager's office
        minute += await(morningBarrier);
        morningBarrier.reset();

        // Morning lead stand-up
        String morningStandup = String.format("%d: The Manager participates in the morning lead stand-up.", minute);
        System.out.println(morningStandup);
        try {
            sleep(15*10);
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e);
        }
        minute += 15;
        meetingTime += 15;

        // Synchronize end of stand-up
        minute += await(morningBarrier);
        morningBarrier.reset();

        // Answer questions until the morning executive meeting
        answerQuestions(START_MORN_EXEC);

        // Go to the morning executive meeting
        String execMeeting = String.format("%d: The Manager participates in the morning executive meeting.", minute);
        System.out.println(execMeeting);

        try {
            sleep((END_MORN_EXEC - minute) * 10);
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e);
        }
        meetingTime += END_MORN_EXEC - minute;
        minute += END_MORN_EXEC - minute;

        // Answer questions until lunch
        answerQuestions(START_LUNCH);

        // Go to lunch (and end lunch on the dot)
        long lunchTimeStamp = clock.getTimeStamp();
        String lunch = String.format("%d: The Manager goes to lunch.", minute);
        System.out.println(lunch);
        try {
            sleep(60*10);
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e);
        }
        minute += 60;
        lunchTime += clock.elapsedTime(lunchTimeStamp);

        // Answer questions until the afternoon executive meeting
        answerQuestions(START_AFTER_EXEC);

        // Go to the afternoon executive meeting
        String execMeeting2 = String.format("%d: The Manager participates in the afternoon executive meeting.", minute);
        System.out.println(execMeeting2);
        try {
            sleep((END_AFTER_EXEC - minute) * 10);
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e);
        }
        meetingTime += END_AFTER_EXEC - minute;
        minute += END_AFTER_EXEC - minute;

        // Answer questions until the status meeting
        answerQuestions(SETUP_STATUS);

        // Begin the status meeting
        String statusMeeting = String.format("%d: The Manager begins to get ready for the status meeting.", minute);
        System.out.println(statusMeeting);

        try {
            sleep((START_STATUS - minute) * 10);
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e);
        }
        meetingTime += START_STATUS - minute;
        minute += START_STATUS - minute;

        // Wait for everyone to attend the status meeting
        System.out.println(minute);
        minute += await(statusBarrier);
        statusBarrier.reset();

        // Conduct the status meeting
        String statusStart = String.format("%d: The Manager starts the status meeting.", minute);
        System.out.println(statusStart);
        System.out.println(minute);
        try {
            sleep((END_STATUS - minute) * 10);
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e);
        }
        meetingTime += END_STATUS - minute;
        minute += END_STATUS - minute;

        // Synchronize the end of the status meeting
        minute += await(statusBarrier);
        statusBarrier.reset();

        // Answer questions until the day is over
        answerQuestions(END);

        // Leave work
        String depart = String.format("%d: The Manager leaves work.", minute);
        System.out.println(depart);
        managerStats =  String.format("Manager: \nTime worked: %d minutes\n Time in meetings: %d minutes\n Time for lunch:" +
                " %d minutes\n", workingTime, meetingTime, lunchTime);

    }

    /**
     * @return the Manager's stats
     */
    public String getStats()
    {
        return managerStats;
    }

    /**
     * Employees will ues this method to queue their questions
     *
     * @param employee
     */
    public synchronized void askQuestion(Employee employee)
    {
        String question = String.format("%d: Employee %d%d gets in line to have their question answered.", minute, employee.getTeamNumber(), employee.getEmployeeNumber());
        System.out.println(question);
        blockQueue.add(employee);

        while (blockQueue.contains(employee)) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e);
            }
        }
    }
}

