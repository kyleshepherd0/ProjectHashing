/************************************************************************
 * @file HashFunction.java
 * @brief This function contains all the actual functions such as insert,
 *        remove, search, and print. It also contains the sfold which inserts
 *        the actual data into the hashtable. Includes the file-writer that
 *        writes the output to a file
 * @author Pelin Blanton and Kyle Shepherd
 * @date December 8, 2023
 ************************************************************************/
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HashFunction implements HashTable<String, HashObject> {
    private final HashObject[] hashes; //builds the hashtable

    // Constructor
    public HashFunction(int hashSize) {
        this.hashes = new HashObject[hashSize]; //constructs the hashtable from the inputted size
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

        // Return -1 to indicate an unsuccessful insertion
        return -1;
    }

    //removes a HashObject from the hash table
    public HashObject remove(String id) {
        int hashValue = hash(id); // Calculate the hash value using the hash function
        for (int i = 0; i < hashes.length; i++) {  // Iterate through the hash table using linear probing
            int index = (hashValue + i) % hashes.length;  // Calculate the index using linear probing technique
            if (hashes[index] != null && hashes[index].getId().equals(id)) {  // Check if the current slot is not empty and has the target ID
                // Retrieve the HashObject to be removed
                HashObject removedObject = hashes[index];
                // Set the slot to null to indicate removal
                hashes[index] = null;
                // Print a message indicating the object has been removed from the current index
                System.out.println("Object removed at index " + index + ": " + removedObject);
                // Return the removed object as it was found and removed
                return removedObject;
            }
        }
        System.out.println("Remove not found for ID: " + id);  // Object with the specified ID not found in the hash table
        return null;  // Return null to indicate that the object was not found and removed
    }

    // returns an array of non-null HashObjects in the hash table
    public void print() throws IOException {
        // Iterate through the hash table
        for (int index = 0; index < hashes.length; index++) {
            // Check if the current slot is not empty
            if (hashes[index] != null) {
                //write breakdown information to a file
                // writeBreakdownToFile(hashes[index].getId(), "Output.txt");
                // Print the HashObject information along with the index
                System.out.println(hashes[index] + " " + index);
            }
        }
    /*
    HashObject[] result = new HashObject[notNull];
    int index = 0;
    // Create a result array of non-null HashObjects
    for (HashObject hashObject : hashes) {
        if (hashObject != null) {
            result[index++] = hashObject;
        }
    }
    return result;
    */
    }

    //searches for a HashObject in the hash table
    public HashObject search(String id) {
        int hashValue = hash(id); // Calculate the hash value using the hash function
        for (int i = 0; i < hashes.length; i++) { // Iterate through the hash table using linear probing
            int index = (hashValue + i) % hashes.length; // Calculate the index using linear probing technique
            // Check if the current slot is not empty and has the target ID
            if (hashes[index] != null && hashes[index].getId().equals(id)) {
                // Print a message indicating the object has been found at the current index
                System.out.println("Object found at index " + index + ": " + hashes[index]);
                return hashes[index]; // Return the found object
            }
        }
        // Object with the specified ID not found in the hash table
        System.out.println("Search not found for ID: " + id);
        // Return null to indicate that the object was not found
        return null;
    }
    public void writeBreakdownToFile(String sequence, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("BREAK DOWN START"); // Write the start marker for the breakdown information
            writer.newLine();

            if (sequence != null) { // If the sequence is not null, write it to the file
                writer.write(sequence);
            }

            int hashValue = hash(sequence); // Calculate the hash value using the hash function for the provided sequence

            for (int i = 0; i < hashes.length; i++) {  // Iterate through the hash table and write breakdown information
                int index = (hashValue + i) % hashes.length; // Calculate the index using linear probing technique
                writer.write(": hash slot [" + index + "]"); // Write information about each hash slot to the file
                writer.newLine();
            }

            writer.write("END"); // Write the end marker for the breakdown information
            writer.newLine();

            // Print a message indicating that breakdown information has been written to the file
            System.out.println("Breakdown information written to file: " + filename);
        }
        catch (IOException e) { // Print an error message if an IOException occurs during file writing
            System.err.println("Error writing breakdown information to file: " + e.getMessage());
        }
    }
}