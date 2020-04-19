package gitlet;

import java.io.Serializable;
import java.io.File;
import java.time.Instant;
import java.util.HashSet;

import static gitlet.Utils.*;

/** This class stores information about the state of a project.
 * @author Jenny Mei
 */
public class Commit implements Serializable {

    /** Creates an initial empty commit with no files. */
    Commit() {
        this(new HashSet<String>(), "initial commit", Instant.EPOCH);
    }

    /** Creates a Commit containing the Blobs mapped to by blobs. */
    Commit(HashSet<String> blobs, String msg, Instant timestamp) {
        BLOBS.addAll(blobs);
        message = msg;
        TIMESTAMP = timestamp;
        COMMIT_ID = commit_ID();
    }

    /** Returns the unique ID associated with this commit. */
    public String commit_ID() {
        return sha1(getTime() + BLOBS.toString() + "GITLET_COMMIT");
    }

    /** Returns a string representing the time of this commit. */
    public String getTime() {
        return TIMESTAMP.toString();
    }

    /** Returns the message associated with this commit. */
    public String getMessage() {
        return message;
    }

    /** Changes the message for this commit to msg. */
    public void editMessage(String msg) {
        message = msg;
    }

    /** The log message associated with this commit. */
    private String message;
    /** A map to BLOB_IDs referenced by this commit. */
    private final HashSet<String> BLOBS = new HashSet<>();
    /** An integer representing the commit time in UNIX time. */
    private final Instant TIMESTAMP;
    /** A unique String ID representing this commit. */
    private final String COMMIT_ID;
    /** The file separator character. */
    private final String s = File.separator;
}
