/************************************************************************
 * @file DNAparser.java
 * @brief This is the parser that takes the input file and then goes through
 *        and calls different functions based on the sample input from the text
 *        file
 * @author Pelin Blanton and Kyle Shepherd
 * @date December 8, 2023
 ************************************************************************/
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class DNAparser {
    private File com; // File object representing the input file
    private HashFunction hashTable1; // Hash table for storing DNA sequences

    // Constructor: Initializes the DNAparser with a File object and a specified hash table size
    public DNAparser(File c, int size) {
        com = c;
        this.hashTable1 = new HashFunction(size); // Initialize the hash table
    }

    // Method to parse the input file and perform operations based on the commands
    public void parse() throws IOException {
        // Using try-with-resources to automatically close the Scanner
        try (Scanner scanner = new Scanner(com)) {
            // Loop through each line in the input file
            while (scanner.hasNextLine()) {
                // Read the next line and remove leading/trailing whitespace
                String nextLine = scanner.nextLine().trim();

                // Skip empty lines
                if (nextLine.equals("")) {
                    continue;
                }

                // Split the line into an array of strings based on whitespace
                String[] line = nextLine.split("\\s+");

                // Check if the line has at least one element
                if (line.length >= 1) {
                    // Check the command specified in the first element of the line
                    if (line[0].equals("insert")) {
                        // If the command is "insert," extract sequence ID and sequence and insert into the hashtable
                        String sequenceID = line[1];
                        String sequence = scanner.nextLine();
                        hashTable1.insert(sequenceID, sequence);
                    } else if (line[0].equals("print")) {
                        // If the command is "print," print the contents of the hashtable
                        hashTable1.print();
                    } else if (line[0].equals("search") && line.length == 2) {
                        // If the command is "search" and has the correct number of arguments, search for a sequence in the hashtable
                        String sequenceID = line[1];
                        hashTable1.search(sequenceID);
                    } else if (line[0].equals("remove") && line.length == 2) {
                        // If the command is "remove" and has the correct number of arguments, remove a sequence from the hashtable
                        String sequenceID = line[1];
                        hashTable1.remove(sequenceID);
                    } else {
                        // If the command is not recognized, print an error message
                        System.out.println("Invalid line format: " + Arrays.toString(line));
                    }
                } else {
                    // If the line does not have at least one element, print an error message
                    System.out.println("Invalid line format: " + Arrays.toString(line));
                }
            }
        }
    }
}