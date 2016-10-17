/**
 * Created by John King on 12-Oct-16.
 */
public interface IResource {
    /*
     * Someone (attempts to) acquire the resource.
     */
    public void acquire();

    /*
     * Someone releases the fork.
     */
    public void release();
}
