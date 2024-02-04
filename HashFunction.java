import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HashFunction implements HashTable<String, HashObject> {
    private final HashObject[] hashes;

    // Constructor
    public HashFunction(int hashSize) {
        this.hashes = new HashObject[hashSize];
    }

    // using folding on a string, summed 4 bytes at a time
    //Uses hashing to assign ASCII values to different strings into a hash table
    private long sfold(String s, int M) {
        long sum = 0, mul = 1; //Sum is used for the sum, and mul is for the multiplier
        for (int i = 0; i < s.length(); i++) { //iterates through the sequence
            mul = (i % 4 == 0) ? 1 : mul * 256; //updated multiplier depending on the iteration
            sum += s.charAt(i) * mul; //main step in determining the hashtable index
        }
        return (int) (Math.abs(sum) % M); //takes absolute value and then takes the remainder to make sure it can cast to int
    }
    //returns the hash value for a given ID
    public int hash(String id) {
        return (int) sfold(id, hashes.length); //Calls the sfold function from the insert funtion
    }
    //inserts a HashObject into the hash table
    //inserts a HashObject into the hash table
    public int insert(String id, String seq) {
        HashObject hashObject = new HashObject(id, seq); // Create a new HashObject with the given ID and sequence
        int hashValue = hash(id);// Calculate the hash value using the hash function
        for (int i = 0; i < 32; i++) {  // Attempt to insert the hashObject into the hash table using linear probing
            int index = (hashValue + i) % 32;   // Calculate the index using linear probing technique
            if (hashes[index] == null) {// Check if the slot at the calculated index is empty
                hashes[index] = hashObject; // Insert the hashObject into the empty slot
                System.out.println("Object inserted at index " + index + ": " + hashObject); // Print a success message indicating the object has been inserted at the current index

                return index;  // Return the index as a sign of successful insertion
            }
        }
        System.out.println("Error: Unable to insert, table is full.");// Unable to insert because all slots are occupied
        return -1;
    }
    //removes a HashObject from the hash table
    public HashObject remove(String id) {
        int hashValue = hash(id);  // Calculate the hash value using the hash function

        for (int i = 0; i < hashes.length; i++) {
            int index = (hashValue + i) % hashes.length; //Calculate the index using linear probing technique
            // Check if the current slot is not empty and has the target ID
            if (hashes[index] != null && hashes[index].getId().equals(id)) {
                HashObject removedObject = hashes[index];
                hashes[index] = null; // Set the slot to null to indicate removal
                System.out.println("Object removed at index " + index + ": " + removedObject);
                return removedObject; // found and removed
            }
        }
        System.out.println("Remove not found for ID: " + id);
        return null; // Return null to indicate that the object was not found and removed
    }
    //Print method to print the ID with the hash slot
    public void print() throws IOException {
        System.out.println("BREAK DOWN START");
        // Iterate through the hash table
        for (int index = 0; index < hashes.length; index++) {
            if (hashes[index] != null) {
                System.out.println(hashes[index].getId() + ": hash slot [" + index + "]");
            }
        }
        System.out.println("END");
    }

    //searches for a HashObject in the hash table
    public HashObject search(String id) {
        int hashValue = hash(id); //Calculate the hash value using the hash function

        for (int i = 0; i < hashes.length; i++) {
            int index = (hashValue + i) % hashes.length;
            // Check if the current slot is not empty and has the target ID
            if (hashes[index] != null && hashes[index].getId().equals(id)) {
                System.out.println("Object found at index " + index + ": " + hashes[index]);
                return hashes[index]; // Return the found object
            }
        }
        // Object with the specified ID not found in the hash table
        System.out.println("Search not found for ID: " + id);
        return null;
    }
    //Writes the ID with the hash slot to the file
    public void writeBreakdownToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("BREAK DOWN START");
            writer.newLine();
            //Iterate through the hash table
            for(int i = 0; i < hashes.length ; i++){
                HashObject hashObject = hashes[i];
                int hashValue = hash(hashObject.getId());
                writer.write(hashObject.getId() + ": hash slot [" + hashValue + "]");
                writer.newLine();
            }
            writer.write("END");
            writer.newLine();

            System.out.println("Breakdown information written to file: " + filename);

        }
    }
}



