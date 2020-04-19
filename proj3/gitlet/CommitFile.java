package gitlet;


import java.io.File;

/** A class to differentiate files representing commits from other files.
 *  @author Jenny Mei */
public class CommitFile extends File {
    CommitFile(File parent, String path) {
        super(parent, path);
    }
    CommitFile(String parent, String path) {
        super(parent, path);
    }
}