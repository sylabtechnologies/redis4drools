import java.util.*;

// demo for atomic it-works-pcode approach 
public class FileDemo {
    public static void main(String[] args) {
        FileSysFacade facade = new FileSysFacade();
        List<String[]> instructions = List.of(
            new String[]{"CREATE", "file1"},
            new String[]{"CREATE", "dir/file1"},
            new String[]{"CREATE", "file2"},
            new String[]{"MOVE", "file2", "dir/"},
            new String[]{"COPY", "file1", "dir/"}
        );
        facade.process(instructions);

        System.out.println(facade);
    }
}

// functional interface
interface FileCommand {
    boolean execute(FileSys sys, String[] args);
}

// dispatcher
class FileSysFacade {
    private final FileSys sys = new FileSys();
    private final Map<String, FileCommand> commands = new HashMap<>();

    public FileSysFacade() {
        commands.put("CREATE", (s, args) -> s.create(args[0]));
        commands.put("COPY", (s, args) -> s.copy(args[0], args[1]));
        commands.put("MOVE", (s, args) -> s.move(args[0], args[1]));
    }

    public void process(List<String[]> operations) {
        for (String[] op : operations) {
            String commandName = op[0].toUpperCase();
            FileCommand cmd = commands.get(commandName);
            if (cmd != null) {
                // pass rest of args (skip command name)
                boolean result = cmd.execute(sys, Arrays.copyOfRange(op, 1, op.length));
                System.out.println(commandName + " " + Arrays.toString(Arrays.copyOfRange(op, 1, op.length)) + " -> " + result);
            } else {
                System.out.println("Unknown command: " + commandName);
            }
        }
    }

    @Override
    public String toString() {
        return sys.toString();
    }

}

// concrete commands/pcode!
class FileSys {
    private HashSet<MyFile> storage = new HashSet<>();

    // return false if exists
    public boolean create(String filename) {
        var file = new MyFile(filename);
        return storage.add(file); // delegate to util
    }

    // return false if exists or no file
    public boolean copy(String filename, String path) {
        var file = new MyFile(filename);
        if (!storage.contains(file)) return false;

        if (path == null || path.isBlank() || path.charAt(path.length() - 1) != '/')  throw new IllegalArgumentException("invalid path");

        var newfile = new MyFile(path + file.name);
        return storage.add(newfile);
    }

    public boolean move(String filename, String path) {
        var fileToMove = new MyFile(filename);
        if (!storage.contains(fileToMove)) return false;
        
        if (path == null || path.isBlank() || path.charAt(path.length() - 1) != '/')  throw new IllegalArgumentException("invalid path");
        var newFile = new MyFile(path + fileToMove.name);
        if (storage.contains(newFile)) {
            System.out.println("cannot move itself");
            return false;
        }

        storage.remove(fileToMove);
        return storage.add(newFile); // delegate to util
    }

    @Override
    public String toString() {
        return storage.toString();
    }
}

class MyFile {
    public final String path;
    public final String name;

    // separate functy - figure out directory structure
    public MyFile(String fullname) {
        if (fullname == null)
            throw new IllegalArgumentException("invalid file name");
        fullname = fullname.trim();
        // check illegal composition
        if (fullname.isBlank() || fullname.charAt(fullname.trim().length() - 1) == '/')
            throw new IllegalArgumentException("invalid file name");

        int indexOfSlash = fullname.lastIndexOf('/');
        // add default root path
        if (indexOfSlash == 0) {}
            fullname = '/' + fullname;
            
        indexOfSlash = fullname.lastIndexOf('/');
        this.path = fullname.substring(0, indexOfSlash + 1);
        this.name = fullname.substring(indexOfSlash + 1);
        // System.out.println(this);
    }

    @Override
    public String toString() {
        return this.path + this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MyFile myFile = (MyFile) obj;
        return java.util.Objects.equals(path, myFile.path) &&
               java.util.Objects.equals(name, myFile.name);
    }

    @Override
    public int hashCode() {
        return  toString().hashCode();
    }

}


