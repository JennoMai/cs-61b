# Gitlet Design Document


**Name**: Jenny Mei


## Classes and Data Structures

### Blob
*This class stores the content of files.*
#### Fields
- `private final File CURRENT_FILE`: The file corresponding to this blob.
- `private final int BLOB_ID`: A unique integer ID representing this blob.


### Commit
*This class stores information about the state of a project.*
#### Fields
- `private String message`: The log message associated with this commit.
- `private final Map<Integer, Blob> BLOBS`: A map of blobs referenced by this commit.
- `private final Commit PARENT`: The parent of this commit.
- `private final long TIMESTAMP`: An integer representing the commit time in Unix time.
- `private final int COMMIT_ID`: A unique integer ID representing this commit.


### Branch
*This class represents a branch in the commit tree.*
#### Fields
- `private String name`: The name of this branch.
- `private Commit end`: A pointer to the most recent commit in the branch; the commit at the end of the branch. 


### Repository
*This class tracks and manages commits, branching, and merging.*
#### Fields
- `private Commit head`: A pointer to the currently checked out commit.
- `private boolean detached`: A boolean representing whether the head points to a commit or a branch; true for a commit, false for a branch.
- `private Map<String, Branch> branches`: A Map of pointers to every branch of this `Repository`.
- `private Map<String, Blob> tracked`: A Map of all currently tracked blobs.


## Algorithms

### Blob Methods
- `Blob(String filePath)`: A constructor for the Blob class. Creates a `File` object, stored in `CURRENT_FILE`, of the file indicated by `filePath`.
- `hashcode()`: Returns a unique hashcode for this Blob using SHA-1.

### Commit Methods
- `Commit(Map<Integer, Blob> blobs)`: Creates a `Commit` containing the `Blob`s mapped to by `blobs`.
- `hashcode()`: Returns a unique hashcode for this `Commit` using SHA-1.
- `editMessage(String message)`: Changes the message for this `Commit`.

### Branch Methods
- `Branch(String name, Commit root)`: Creates a new `Branch` with the specified `name` from `root`, setting `root` to `end` as its initial value.

### Repository Methods
- `Repository()`: Constructor for the `Repository` class. Creates an initial `Commit` with no files, the message `"initial commit"`, and the Unix Epoch timestamp. Also creates an initial `Branch` named `"master"` and sets it as the current branch.
- `stage(Blob blob)`: Adds the blob to `tracked`.
- `commit()`: Creates a new `Commit` with all the blobs in `tracked` and clears `tracked`.
- `remove(Blob blob)`: Removes the blob from `tracked`.
- `log()`: Displays information about commits beginning from the `head`, moving backwards.
- `globalLog()`: Displays information about all commits in the `Repository`.   
- `find(String message)`: Returns the hashcodes of all commits containing the given `message`.
- `status()`: Displays the current branches and lists blobs that have been staged for addition or removal. Also lists modifications to blobs not yet staged for committing.
- `checkout(String fileName)`: Reverts file with name `fileName` in the working directory to the file of the same name stored in the `head`. Does not stage the new file.
- `checkout(int id, String fileName)`: Reverts file with name `fileName` in the working directory to the file of the same name stored in the `Commit` with the hashcode `id`. Does not stage the new file.
- `checkout(Branch branch)`: Overwrites all files in the working directory with the files in the `end` of `branch`. Places the `head` at the `end` of `branch`.
- `branch(String name)`: Creates a new `Branch` called `name` with its `end` set to the current `head`.
- `removeBranch(Branch branch)`: Removes a `Branch`.
- `reset(Commit commit)`: Checks out all the files tracked by `commit`. Removes tracked files that are not present in that commit. Also moves the current branch's head to `commit`. The staging area `tracked` is also cleared.
- `merge(Branch branch)`: Merges the `branch` into the current branch.


## Persistence

### Storage
`Blob`, `Commit`, `Branch`, and `Repository` objects will be stored by serializing. These classes should implement Serializable.

### .gitlet Directory
Past files (blobs) should be stored under folders representing commits in this directory. `Blob` and `Commit` hashcodes will be used as the names for these files and folders. This can be done using the `writeObject` and `mkdir` functions.

Files will be read from this directory using the `readObject` utility function (provided in the skeleton).


### Working Directory
Files in the current working directory will be compared to those stored in the `.gitlet` directory. These files may be converted to blobs and stored in `.gitlet` upon committing, or overwritten by the `checkout` function. Manipulation of these files will be done using the same methods described above.


