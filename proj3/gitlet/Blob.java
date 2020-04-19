package gitlet;

import afu.org.checkerframework.checker.oigj.qual.O;

import java.io.File;
import java.io.Serializable;
import static gitlet.Utils.*;

/** A class representing a file in a specific instance.
 * @author Jenny Mei
 */
public class Blob implements Serializable {


    /** A constructor for the Blob class.
     * Creates a File object, stored in CURRENT_FILE, of the file indicated by filePath.*/
    Blob(String filePath) {
        CURRENT_FILE = new File(filePath);
        FILE_CONTENTS = readContents(CURRENT_FILE);
        BLOB_ID = blob_ID();
    }

    /** Returns the unique SHA-1 hash string for this blob. */
    public String blob_ID() {
        return sha1(FILE_CONTENTS, "GITLET_FILE");
    }

    /** Writes the contents of this blob to the working directory. */
    public void writeFile() {
        writeContents(CURRENT_FILE, (Object) FILE_CONTENTS);
    }

    /** The file address corresponding to this blob. */
    private final File CURRENT_FILE;
    /** The contents of the file corresponding to this blob, represented as a byte array. */
    private final byte[] FILE_CONTENTS;
    /** A unique string ID representing this blob. */
    private final String BLOB_ID;

}