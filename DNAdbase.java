import java.io.File;
import java.io.IOException;
//This class reads a command file, initializes a DNAparser, and parses the commands.
// The hash size for the database is specified as a command-line argument.
public class DNAdbase {
    private static int hashSize;
    private static File command; // File object representing the input file
    private static DNAparser parse; // Hash table for storing DNA sequences

    // Constructor: Initializes the DNAparser with a File object and a specified hash table size
    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            command = new File(args[0]);
            hashSize = Integer.parseInt(args[1]);
            parse = new DNAparser(command, hashSize);
            parse.parse();
        }
        else {
            System.out.println("Please input a correctly formatted command");
        }
    }

    public DNAparser getParser() {
        return parse;
    }
}