import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * The Employee represents a team lead or a basic developer.
 * This is determined by the isLead boolean.
 * The employee arrives at a random time between 8 and 8:30, attends stand up
 * meetings, works, and asks questions throughout the day.
 *
 * Created by John King on 12-Oct-16.
 */
public class Employee extends Thread {

	/**
	 * Barrier for morning stand-up
	 */
	private CyclicBarrier morningBarrier;

	/**
	 * Barrier for team stand-up
	 */
	private CyclicBarrier leadBarrier;

	/**
	 * Barrier for status meeting
	 */
	private CyclicBarrier statusBarrier;

	/**
	 *  Clock, to reference time of the system
	 */
    private Clock clock;

	/**
	 * The object for mutually exclusive access to the conference room
	 */
	private ConferenceRoom conferenceRoom;

	/**
	 * A reference to the manager
	 */
	private Manager manager;

	/**
	 * A reference to the team lead
	 */
	private Employee lead;

    /**
	 * List of devs the lead is responsible for
	 */
	private ArrayList<Employee> subordinateList = new ArrayList<Employee>();

	/**
	 * Is this employee a lead?
	 */
	private boolean isLead = false;

	/**
	 * The employee's team number
	 */
	private int teamNumber;

	/**
	 * The employee's number
	 */
	private int employeeNumber;

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
	 * Time spent waiting for answers to questions
	 */
	private int waitingTime = 0;

	/**
	 * The employee's current minute
	 */
	private int minute;

	/**
	 * String that represents the stats of this employee
	 */
	private String employeeStats;

	/**
	 * @return the Employee's current minute
	 */
	public int getMinute()
	{
		return minute;
	}


	/**
	 * Constructor for a new employee
	 * 
	 * @param _teamNumber
	 *        the number of the team
	 * @param _employeeNumber
	 *        the number of the employee
	 * @param _manager
	 *        a reference to the manager
	 * @param _conferenceRoom
	 *        a reference to the ConferenceRoom
	 */
	public Employee(int _teamNumber, int _employeeNumber, boolean lead, Manager _manager,
		ConferenceRoom _conferenceRoom, Clock _clock)
	{
		teamNumber = _teamNumber;
		employeeNumber = _employeeNumber;
		manager = _manager;
		conferenceRoom = _conferenceRoom;
		clock = _clock;

		if (lead) {
			isLead = true;
		} else {
			isLead = false;
		}
	}

    @Override
	public synchronized void run() {
		long timeStamp;
		Random random = new Random();

		boolean lunchTaken = false;
		boolean didStatus = false;

		int arrivalDelay = random.nextInt(31);

		// TODO: Make sure the offset is correct
		int arrivalTime = 480 + arrivalDelay;


		// Set what minute the employee is currently on and log the arrival event
		minute = arrivalTime;

		// Determine when the employee will take for lunch the day
		int whenToEat = minute + decideLunchTime();

		// Based on the amount of time taken for lunch, determine when they should leave
		// TODO: Make sure the offset is correct
		int departTime = arrivalTime + 480;

		waitUntil(arrivalTime);
		minute = clock.getMinute();

		String employeeArrived = String.format("%d: Employee %d%d has arrived at work.", minute, getTeamNumber(), getEmployeeNumber());
		System.out.println(employeeArrived);

		// If the employee is a lead, go to the Lead Standup
		if (isLead) {
			try {
				// Log what time it is and that the lead for team X is waiting
				// outside the Manager's office
				timeStamp = clock.getTimeStamp();
				String leadArrived = String.format("%d: Lead %d has arrived at the PM's office.", minute, getTeamNumber());
				System.out.println(leadArrived);

				// TODO: Wait for all the leads to arrive
				// Mark the current time and measure elapsed time until the meeting starts
				morningBarrier.await();

				String leadEnter = String.format("%d: Lead %d enters the PM's office.", minute, getTeamNumber());
				System.out.println(leadEnter);
				morningBarrier.await();

				int waitTime = clock.elapsedTime(timeStamp);
				minute = clock.getMinute();
				// Start the meeting
				String managerStandup = String.format("%d: Lead %d participates in the lead stand-up.", minute, getTeamNumber());
				System.out.println(managerStandup);

				// Wait the amount of time the meeting took
				waitUntil(clock.getMinute()+15);
				minute = clock.getMinute();

				// Wait to leave the PM's office
				timeStamp = clock.getTimeStamp();
				morningBarrier.await();

				// TODO: Implement wait for team members to arrive for morning standup
				leadBarrier.await();
				leadBarrier.reset();

				minute = clock.getMinute();
				meetingTime += 15;

			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace(System.out);
			}

		} else {
			// You're a developer
			timeStamp = clock.getTimeStamp();

			// Wait to do the stand-up
			try {
				 // Dev waits for the other devs
				leadBarrier.await();

			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace(System.out);
			}

			minute = clock.getMinute();
			meetingTime += 15;
		}

		// Once the whole team has arrived, attempt to acquire the conference room
		timeStamp = clock.getTimeStamp();

		if (isLead) {
			// TODO: Aquire conference room
			String getConf = String.format("%d: Lead %d attempts to acquire the conference room", minute, getTeamNumber());
			System.out.println(getConf);

			conferenceRoom.request(this);
		}

		// TODO: Once the conference room is acquired, have the meeting
		// Wait for everyone to get into the conference room
		try {
			leadBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace(System.out);
		}

		// Adjust employee's minute
		minute = clock.getMinute();
		meetingTime += 15;

		// Wait the amount of time the meeting took
		timeStamp = clock.getTimeStamp();

		String teamStandup = String.format("%d: Employee %d%d contributes to the morning stand-up meeting.", minute, getTeamNumber(), getEmployeeNumber());
		System.out.println(teamStandup);
		waitUntil(clock.getMinute()+15);
		minute = clock.getMinute();


		// Free the conference room for other teams
		if (isLead) {
			// Leave conference room
			conferenceRoom.leave(this);
		}

		meetingTime += 15;

		while (true) {
			// Status meeting
			// If it's 4 and we haven't done the status meeting, do it
			if (minute >= 960 && !didStatus) {
				didStatus = true;
				timeStamp = clock.getTimeStamp();

				try {
					String statusWaiting = String.format("%d: Employee %d%d heads to the conference room for the status meaning.", minute, getTeamNumber(), getEmployeeNumber());
					System.out.println(statusWaiting);

					// Wait for everyone to arrive
					statusBarrier.await();
					statusBarrier.await();

					// Simulate meeting
					waitUntil(clock.getMinute()+15);
				} catch(Exception e) {
					e.printStackTrace(System.out);
				}

				minute = clock.getMinute();
				meetingTime += 15;
			} else if (minute >= departTime) {
				String statusWaiting = String.format("%d: Employee %d%d leaves work.", minute, getTeamNumber(), getEmployeeNumber());
				System.out.println(statusWaiting);

				employeeStats = String.format("Employee %d%d\n Time worked: %d minutes\n Time in meetings:" +
								" %d minutes\n Time for lunch: %d minutes\n Waited on the manager for: %d minutes\n",
						getTeamNumber(), getEmployeeNumber(), workingTime, meetingTime, lunchTime, waitingTime);

				break;
			} else {
				// Otherwise we're working
				// Sometimes a dev will ask a question
				if (random.nextInt(750) == 1) {
					String hasQuestion = String.format("%d: Employee %d%d has a question.", minute, getTeamNumber(), getEmployeeNumber());
					System.out.println(hasQuestion);

					// Half of the time the team lead can answer the question
					if ((!isLead) && (random.nextInt(2) == 0)) {
						String answerQuestion = String.format("%d: Team lead answers Employee %d%d's question.", minute, getTeamNumber(), getEmployeeNumber());
						System.out.println(answerQuestion);
						// Otherwise go ask the Manager
					} else {
						// TODO: Queue question for manager
						timeStamp = clock.getTimeStamp();
						int tempMinute = clock.getMinute();
						manager.askQuestion(this);
						minute = clock.getMinute();
						waitingTime += (minute-tempMinute);
					}
				}

				// Devs need to eat
				if (!lunchTaken && (minute >= whenToEat)) {
					lunchTaken = true;
					timeStamp = clock.getTimeStamp();
					int tempMinute = clock.getMinute();

					// Decide how long to take lunch for
					int extraTime = random.nextInt(31);
					int totalLunchTime = 30 + extraTime;

					departTime += extraTime;

					String lunch = String.format("%d: Employee %d%d's takes their lunch for %d minutes.", minute, getTeamNumber(), getEmployeeNumber(), totalLunchTime);
					System.out.println(lunch);

					//Simulate lunch time
						waitUntil(clock.getMinute()+totalLunchTime);
						minute = clock.getMinute();

					lunchTime += (minute - tempMinute);
				}

				// Sleep 1 minute
				int tempMinute = clock.getMinute();
				waitUntil(clock.getMinute()+1);
				// Increment the working minute and minute
				minute = clock.getMinute();
				workingTime += (minute - tempMinute);
			}
		}
	}

	/**
	 * Set up barriers
	 */
	public void setMorningBarrier(CyclicBarrier _barrier)
	{
		morningBarrier = _barrier;
	}

	public void setLeadBarrier(CyclicBarrier _barrier)
	{
		leadBarrier = _barrier;
	}

	public void setStatusBarrier(CyclicBarrier _barrier)
	{
		statusBarrier = _barrier;
	}

	/**
	 * Method used in Main to provide employees with their respective barriers
	 */
	public void provideSubordinatesBarrier()
	{
		this.leadBarrier = new CyclicBarrier(1 + subordinateList.size(), new Runnable() {
			@Override
			public void run() {}
		});

		for (Employee employee : subordinateList) {
			employee.setLeadBarrier(leadBarrier);
		}
	}

	/**
	 * @return the list of this employee's subordinates (if any)
	 */
	public List<Employee> getSubordinates()
	{
		return subordinateList;
	}

	/**
	 * @param e
	 * the employee to assign to this lead
	 */
	public synchronized void addSubordinate(Employee e)
	{
		subordinateList.add(e);
	}

	/**
	 * @return The team number
	 */
	public int getTeamNumber()
	{
		return teamNumber;
	}

	/**
	 * Sets the reference to the team members lead
	 */
	public void setLead(Employee e) {
		this.lead = e;
	}

	/**
	 * @return The employee number
	 */
	public int getEmployeeNumber()
	{
		return employeeNumber;
	}

	/**
	 * @return The employee's lunch time for the day
	 */
	private int decideLunchTime()
	{
		Random r = new Random();

		// Number of minutes to wait before taking lunch
		// should be between 9-2
		return 60 + r.nextInt(300);
	}

	/**
	 * @return this employee's stats
	 */
	public String getStats()
	{
		return employeeStats;
	}

	public void waitUntil(int finalTime){
		while(clock.getMinute()!=finalTime){
			try {
				sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
