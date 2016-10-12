#Requirements

* Opens at **8:00** and closes at **5:00**. 

* Employees may arrive anytime between **8:00 and 8:30**, take lunch for **at least 30 minutes but no more than an hour**, and leave anytime from **4:30 to 5:00** as long as they work **at least 8 hours**. 

* One minute of simulated time takes 10ms of real time, so **8 hours takes 4800ms**.

* A software project manager is in charge of 3 teams of developers, each team consisting of a team lead and 3 additional developers (12 developers in all).

* The manager arrives at **8:00** each day, the manager engages in daily planning activites and then **waits until all the team leads arrive at his office**. When all the leads have arrived, they have a daily **15 minute** standup meeting.

* After the meeting, the team leads **wait for all the members of their team to arrive**. When all members of a team are present, the team lead **waits for the one-and-only team conference room to become available**, enters the room with all team members, and holds a holds a team-based standup meeting for **15 minutes**.

* At **any point** during the day, a team member may ask a question of his or her team lead. There is an **50% chance** that the team lead can answer the question, at which time the lead and the team member return to work.

* If the team lead is the one with the question, or if the question is one of the 50% the lead cannot answer, the lead and the member asking the question go to the project manager's office to have the question answered. When let into the manager's office, the team lead asks the question, the manager provides the answer, and the team lead (and possibly the member with the question) return to work. Of course, the team **may have to wait** for one or more other teams to have their questions answered first. We will also assume it always takes **10 minutes** to ask and answer a question.

* The manager has two daily executive meetings, each lasting **one hour**, one from **10:00 to 11:00** and one from **2:00 to 3:00**. In addition, the manager eats lunch for **one hour starting as close to from 12:00 as possible to 1:00**. If in the middle of answering a question when a meeting or lunch begins, the manager finishes answering the question and then goes to the meeting or lunch. Any other teams with questions simply wait for the manager to return.

* At **4:00 every day** leaders and members of all teams start assembling in the conference room for a project status update. It is expected that members will **finish arriving by 4:15, allowing 15 minutes to clean up any work in progress**. When all members have gathered, the manager spends **15 minutes** dicussing the project status.

* Employees start leaving from **4:30 to 5:00** as they complete their **8 hour days**. The project manager is the last to leave at **5:00**.

* When not asking questions, at meetings, or at lunch, team leads and developers are hard at work designing, coding and testing.

* When not answering questions, at meetings, or at lunch, the manager does whatever it is managers do (looking for deals on Woot!, reading blogs, thinking of ways to make the developers' lives miserable, etc.)

* When developers ask the team lead a question that the lead can answer no time elapses (i.e., **response is instantaneous**).

* Developers may **not** ask questions during the morning standup meeting or the afternoon project status meeting.

#Responsibility
You are to design and implement a concurrent program to simulate one day's activities at the firm. All significant events should be logged with the simulated time at which they occur, e.g., 12:02 Manager goes to lunch. Identify individual developers, where significant, by the notation Deverloper NM, where N is the team number (1-3) and M is the employee's number on the team (1-4, where 1 is the team lead). Teams should be identified as Team N, where N is the team number as specified previously.

Obviously, multiple runs should result in different mixes of meetings, questions, etc.

All persons must be represented by independent threads; you should use passive resources to synchronize these threads as necessary (say, to ensure everyone is in the conference room before the manager starts reporting status). In addition, you should accumulate statistics on the total amount of time across the manager and all his developers (a) working, (b) at lunch, (c) in meetings, and (d) waiting for the manager to be free to answer a question.

We strongly suggest that you implement a test bed allowing you close control over the activities described above.

You should make and document plausible assumptions about the time between questions for a team, average lunch break, arrival and departure times, etc. Experiment with different assumptions to see their effect, and document the results. In addition to the well-structured and well-documented Java source code, you must write a report that details your design (especially decisions related to concurrency, use of data structures, special synchronization, etc.), alternatives you considered, and the results of your experiments (e.g., were any race conditions uncovered? were you able to correct these?).