package gitlet;

import java.io.File;
import java.io.IOException;
import static gitlet.Utils.*;

/** Instances of this class track and manage commits, branching, and merging.
 *  @author Jenny Mei
 */
public class Repository {

    /** Initializes a new Gitlet repository in the working directory. */
    Repository() throws IOException {
        CWD = System.getProperty("user.dir");
        GITLET = CWD + S + ".gitlet";
        File dir = new File(GITLET);
        if (dir.exists()) {
            throw new GitletException("A Gitlet version-control system " +
                "already exists in the current directory.");
        }
        dir.mkdir();

        BRANCHES = GITLET + S + "branches";
        File branches = new File(BRANCHES);
        branches.mkdir();

        OBJECTS = GITLET + S + "objects";
        File objects = new File(OBJECTS);
        objects.mkdir();

        _currBranch = MASTER = BRANCHES + S + "master";
        branch("master");
        Commit initial = new Commit();
        writeContents(new File(MASTER), initial.commit_ID());
    }

    /** Creates a reference to the pre-existing repository at path.
     *  Sets branch to master by default. */
    Repository(String path) {
        this(path, "master");
    }

    /** Creates a reference to the pre-existing repository at path.
     *  Sets branch to the specified branch. */
    Repository(String path, String branch) {
        CWD = System.getProperty("user.dir");
        GITLET = CWD + S + ".gitlet";
        BRANCHES = GITLET + S + "branches";
        OBJECTS = GITLET + S + "objects";
        MASTER = BRANCHES + S + "master";
        _currBranch = BRANCHES + S + branch;

        File dir = new File(path);
        if (!dir.exists()) {
            throw new GitletException("A Gitlet repository has not been "
            + "initialized at " + path);
        }

        File currBranch = new File(_currBranch);
        if (!currBranch.exists()) {
            throw new GitletException("The specified branch does not exist.");
        }
    }

    /** Creates a branch in the commit tree under the folder .gitlet/branches. */
    public void branch(String name) throws IOException {
        File newBranch = new File(BRANCHES + S + name);
        if (newBranch.exists()) {
            throw new GitletException("A branch with that name already exists.");
        }
        newBranch.createNewFile();
        writeContents(newBranch, getHeadID(_currBranch));
    }

    public void switchBranch(String name) {
        _currBranch = name;
    }

    /** Returns the commit ID of the head of the specified path.
     *  @param branch The file path to the desired path. */
    private String getHeadID(String branch) {
        return readContentsAsString(new File(branch));
    }

    /** The path of the current working directory. */
    private final String CWD;
    /** The path of the .gitlet folder. */
    private final String GITLET;
    /** The path to the master branch file containing the latest head. */
    private final String MASTER;
    /** The path of the .gitlet/branches folder.
     *  Contains files representing branches of the same name.
     *  Files hold the SHA-1 ID of their last commit. */
    private final String BRANCHES;
    /** The path of the .gitlet/objects folder.
     *  Contains serialized files representing blobs with SHA-1 ID names. */
    private final String OBJECTS;
    /** A system-independent file separator character. */
    private final String S = File.separator;

    /** The path to the current branch, a file containing
     *  the commit ID of its head. */
    private String _currBranch;
}
