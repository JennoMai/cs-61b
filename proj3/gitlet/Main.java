package gitlet;

import java.io.File;
import java.io.IOException;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Jenny Mei
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) throws IOException {
        // FILL THIS IN
        switch (args[0]) {
        case ("init"):
            new Repository();
            break;
        default:
            System.out.println(args[0] + " NOT IMPLEMENTED YET.");
        }


    }
}
