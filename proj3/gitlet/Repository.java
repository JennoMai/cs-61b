package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static gitlet.Utils.*;

/** Instances of this class track and manage commits, branching, and merging.
 *  @author Jenny Mei
 */
public class Repository implements Serializable {

    /** Initializes a new Gitlet repository in the working directory. */
    Repository() throws IOException {
        CWD = System.getProperty("user.dir");
        GITLET = new File(CWD, ".gitlet");
        OBJECTS = new File(GITLET, "objects");
        COMMITS = new File(GITLET, "commits");
        BRANCHES = new File(GITLET, "branches");
        MASTER = _currBranch = new BranchFile(BRANCHES, "master");

        if (GITLET.exists()) {
            throw new GitletException("A Gitlet version-control system " +
                "already exists in the current directory.");
        }

        GITLET.mkdir();
        BRANCHES.mkdir();
        OBJECTS.mkdir();
        COMMITS.mkdir();

        branch("master");

        Commit initial = new Commit();
        File commitFile = new CommitFile(COMMITS, initial.commit_ID());
        commitFile.createNewFile();
        writeContents(MASTER, initial.commit_ID());
        writeObject(commitFile, initial);

        saveSettings();
    }

    private void saveSettings() throws IOException {
        File repo = new File(GITLET, "repo");
        if (!repo.exists()) {
            repo.createNewFile();
        }
        writeObject(repo, this);
    }

    /** Creates a branch in the commit tree under the folder .gitlet/branches. */
    public void branch(String name) throws IOException {
        BranchFile newBranch = new BranchFile(BRANCHES, name);
        if (newBranch.exists()) {
            throw new GitletException("A branch with that name already exists.");
        }
        newBranch.createNewFile();
        writeContents(newBranch, getHeadID(_currBranch));
    }

    public void switchBranch(String name) {
        _currBranch = new BranchFile(BRANCHES, name);
    }

    /** Returns the commit ID of the head of the specified branch. */
    private String getHeadID(File branch) {
        if (branch.getClass() != BranchFile.class) {
            throw error("The specified file is not a branch.");
        }
        return readContentsAsString(branch);
    }

    /** The path of the current working directory. */
    private final String CWD;
    /** The .gitlet folder. */
    private final File GITLET;

    /** The .gitlet/branches folder.
     *  Contains files representing branches of the same name.
     *  Files hold the SHA-1 ID of their last commit. */
    private final File BRANCHES;
    /** The .gitlet/commits folder. Contains serialized commits. */
    private final File COMMITS;
    /** The .gitlet/objects folder.
     *  Contains serialized files representing blobs with SHA-1 ID names. */
    private final File OBJECTS;

    /** The master branch file containing the latest head. */
    private final BranchFile MASTER;

    /** The current branch, a file containing
     *  the commit ID of its head. */
    private BranchFile _currBranch;
}
