import  java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
public class DNAparser {
    private File com; // File object representing the input file
    private HashFunction hashTable1; // Hash table for storing DNA sequences
    // Constructor: Initializes the DNAparser with a File object and a specified hash table size
    public DNAparser(File c, int size) {
        com = c;
        this.hashTable1 = new HashFunction(size);
    }
    // Method to parse the input file and perform operations based on the commands
    public void parse() throws IOException {
        try (Scanner scanner = new Scanner(com)) {
            // Loop through each line in the input file
            while (scanner.hasNextLine()) {
                // Read the next line and remove leading/trailing whitespace
                String nextLine = scanner.nextLine().trim();
                if (nextLine.equals("")) {  // Skip empty lines
                    continue;
                }
                //Each line contains a sequence ID with space
                // Split the line into an array of strings based on whitespace
                String[] line = nextLine.split("\\s+");
                if (line.length >= 1) {
                    if (line[0].equals("insert")) {
                        String sequenceID = line[1];
                        String sequence = scanner.nextLine();
                        //insert sequence into the hashtable
                        hashTable1.insert(sequenceID, sequence);
                    } else if (line[0].equals("print")) {
                        //print hashtable
                        hashTable1.print();
                        //Call writeBreakdownToFile to write to the output file
                        hashTable1.writeBreakdownToFile("sampleOutput.txt");

                    } else if (line[0].equals("search") && line.length == 2) {
                        String sequenceID = line[1];
                        //search sequence into the hashtable
                        hashTable1.search(sequenceID);

                    } else if (line[0].equals("remove") && line.length == 2) {
                        String sequenceID = line[1];
                        //remove sequence in the hashtable
                        hashTable1.remove(sequenceID);
                    } else {
                        System.out.println("Invalid line format: " + Arrays.toString(line));
                    }
                } else {
                    System.out.println("Invalid line format: " + Arrays.toString(line));
                }
            }
        }
    }
}
