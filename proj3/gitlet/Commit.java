package gitlet;

import java.io.Serializable;
import java.io.File;
import java.time.Instant;
import java.util.HashSet;

import static gitlet.Utils.*;

/** This class stores information about the state of a project. */
public class Commit implements Serializable {

    /** Creates a Commit containing the Blobs mapped to by blobs. */
    Commit(HashSet<String> blobs, String msg) {
        BLOBS.addAll(blobs);
        message = msg;
        TIMESTAMP = Instant.now();
        COMMIT_ID = commit_ID();
    }

    /** Returns the unique ID associated with this commit. */
    public String commit_ID() {
        return sha1(getTime() + "GITLET_COMMIT");
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
}
