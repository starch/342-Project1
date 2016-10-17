import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by John King on 12-Oct-16.
 */
public class TeamLead {
    private ArrayList<Employee> employees;
    private Manager manager;
    private LinkedList<Employee> questionQueue = new LinkedList<>();

    public TeamLead(Manager manager, ArrayList<Employee> employees){
        this.employees = employees;
        this.manager = manager;
    }

}
