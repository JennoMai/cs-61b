package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/** This class tracks and manages commits, branching, and merging.
 *  @author Jenny Mei
 */
public class Repository implements Serializable {

    /** Initializes a new Gitlet repository in the working directory. */
    Repository() {
        CWD = System.getProperty("user.dir");
        File dir = new File(CWD + s + ".gitlet");
        if (dir.exists()) {
            throw new GitletException("A Gitlet version-control system " +
                "already exists in the current directory.");
        }
        dir.mkdir();

        branches.put("master", new Branch());
        head = branches.get("master").getEnd();
        detached = false;
    }

    private Commit head;
    private boolean detached;
    private HashMap<String, Branch> branches = new HashMap<>();
    private final String CWD;

    String s = File.separator;
}
