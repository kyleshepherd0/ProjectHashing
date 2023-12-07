import java.io.File;
import java.io.IOException;

public class DNAdbase {

    private static int hashSize;
    private static File command;
    private static DNAparser parse;

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
