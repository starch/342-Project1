#Requirements

* Opens at **8:00**
* Manager arrives at **8:00**
* Employees arrive between **8:00-8:30**
* Manager does adminstrivia until all team leads arrive at his office
* Once all team leads arrive at Managers Office - **15 minute standup meeting**
* After the standup, the team leads wait for all members of their team to arrive
* After all team members have arrived the team lead **waits for the conference room to become available** and holds a **15 minute team standup**
* At any point a team member may ask a question, there is a **50%** chance the team lead can answer the question
* If the team lead is the one with the question or the team lead can't answer the question the **lead AND the member** asking the question go to the manager's office to have the question answered. They may have to **wait** for another team to get their question answered first
* It always takes **10 minutes** to answer a question
* The manager has two meetings, one from **10:00-11:00** and another from **2:00-3:00**
* The manager eats lunch for **one hour** starting **as close to 12:00 as possible**
* Employees take lunch for **30-60 minutes**
* At **4:00** all team leads and members begin gathering in the conference room for a project status meeting.
* All members are expected to arrive by **4:15**
* The project manager then has a **15 minute** meeting on the status of the project
* Employees leave between **4:30-5:00** as they complete their **8 hour days**
* Manager leaves at **5:00**
* Closes at **5:00**

#Rules
* When not doing anything else the Manager is doing manager stuff
* If the lead can answer a question, it is instantaneous 
* Devs cannot ask questions during the morning standup or the project status meeting
* Manager is in charge of 3 teams
* A Team consistes of 1 Team Lead and 3 Developers 
* Simulated time should be 10ms to 1 min so 4.8 sec for an 8 hour period.

#Responsibility
You are to design and implement a concurrent program to simulate one day's activities at the firm. All significant events should be logged with the simulated time at which they occur, e.g., 12:02 Manager goes to lunch. Identify individual developers, where significant, by the notation Deverloper NM, where N is the team number (1-3) and M is the employee's number on the team (1-4, where 1 is the team lead). Teams should be identified as Team N, where N is the team number as specified previously.

Obviously, multiple runs should result in different mixes of meetings, questions, etc.

All persons must be represented by independent threads; you should use passive resources to synchronize these threads as necessary (say, to ensure everyone is in the conference room before the manager starts reporting status). In addition, you should accumulate statistics on the total amount of time across the manager and all his developers (a) working, (b) at lunch, (c) in meetings, and (d) waiting for the manager to be free to answer a question.

We strongly suggest that you implement a test bed allowing you close control over the activities described above.

You should make and document plausible assumptions about the time between questions for a team, average lunch break, arrival and departure times, etc. Experiment with different assumptions to see their effect, and document the results. In addition to the well-structured and well-documented Java source code, you must write a report that details your design (especially decisions related to concurrency, use of data structures, special synchronization, etc.), alternatives you considered, and the results of your experiments (e.g., were any race conditions uncovered? were you able to correct these?).