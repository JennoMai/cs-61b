package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/** The suite of all JUnit tests for the gitlet package.
 *  @author Jenny Mei
 */
public class UnitTest {

    /** Run the JUnit tests in the loa package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(UnitTest.class));
    }

    /** Recursively deletes fucking everything. */
    public static void DELETE_EVERYTHING(File file) {
        if (file.exists()) {
            if (file.isDirectory() && file.list().length != 0) {
                String[] files = file.list();
                for (String temp : files) {
                    DELETE_EVERYTHING(new File(file, temp));
                }
            }
            file.delete();
        } else {
            return;
        }
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testInit() throws IOException {
        File repo = new File(CWD, ".gitlet");
        DELETE_EVERYTHING(repo);
        Main.main("init");
        assertTrue(repo.exists());
    }

    /** The current working directory. */
    public static final String CWD = System.getProperty("user.dir");
    /** System-independent file separator. */
    public static final String s = File.separator;

}


