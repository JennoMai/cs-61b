package gitlet;

import java.io.File;
import java.io.Serializable;

/** This class represents a branch in the commit tree.
 *  @author Jenny Mei
 */
public class Branch implements Serializable {

    /** Creates a master branch for a new repository. */
    Branch() {
        this("master", new Commit());
    }
    /** Creates a new branch with the specified name from root. */
    Branch(String name, Commit root) {
        NAME = name;
        end = root;
    }

    /** Returns the end of this branch. */
    public Commit getEnd() {
        return end;
    }

    private final String NAME;
    private Commit end;
    /** The file separator character. */
    private final String s = File.separator;
}
