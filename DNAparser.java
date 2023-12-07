import  java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
public class DNAparser {
    private File com;
    private HashFunction hashTable1;

    public DNAparser(File c, int size) {
        com = c;
        this.hashTable1 = new HashFunction(size);
    }
    public void parse() throws IOException {
        try (Scanner scanner = new Scanner(com)) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine().trim();
                if (nextLine.equals("")) {
                    continue;
                }
                //Each line contains a sequence ID with space
                String[] line = nextLine.split("\\s+");
                //if(line[0].equals("")){
                //  continue;
                // }
//                System.out.println(line[0]);
                if (line.length >= 1) {
//                    System.out.println(line);
                    if (line[0].equals("insert")) {
                        String sequenceID = line[1];
                        String sequence = scanner.nextLine();
                        //insert sequence into the hashtable
                        hashTable1.insert(sequenceID, sequence);

                    } else if (line[0].equals("print")) {
                        //print hashtable
                        hashTable1.print();

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