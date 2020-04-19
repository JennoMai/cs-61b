package gitlet;

import java.io.File;

/** A class to differentiate files representing branches from other files.
 *  @author Jenny Mei */
public class BranchFile extends File {
    BranchFile(File parent, String path) {
        super(parent, path);
    }
    BranchFile(String parent, String path) {
        super(parent, path);
    }
}
