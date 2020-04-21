package gitlet;

import java.io.File;
import java.io.IOException;
import static gitlet.Utils.*;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Jenny Mei
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) throws IOException {
        // FILL THIS IN
        String CWD = System.getProperty("user.dir");
        File repo = new File(CWD, ".gitlet");

        if (args[0].equals("init")) {
            if (repo.exists()) {
                throw new GitletException("A Gitlet version-control system"
                        + "already exists in the current directory.");
            }
            new Repository();
            return;
        } else {
            if (!repo.exists()) {
                throw new GitletException("A Gitlet repository does not exist"
                + "in the current directory.");
            }
        }

        Repository currentRepo = readObject(repo, Repository.class);

        switch (args[0]) {
        case ("add"):
            if (args.length != 2) {
                throw new GitletException("File improperly specified.");
            }
            currentRepo.stage(args[1]);
        default:
            System.out.println(args[0] + " NOT IMPLEMENTED YET.");
        }


    }
}
