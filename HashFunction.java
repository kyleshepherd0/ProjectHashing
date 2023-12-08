import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class HashFunction implements HashTable<String, HashObject> {
    private final HashObject[] hashes;
    private final HashObject[] hashes1;

    // Constructor
    public HashFunction(int hashSize) {
        this.hashes = new HashObject[hashSize/2];
        this.hashes1 = new HashObject[hashSize/2];
    }

    // using folding on a string, summed 4 bytes at a time
    private long sfold(String s, int M) {
        long sum = 0, mul = 1;
        for (int i = 0; i < s.length(); i++) {
            mul = (i % 4 == 0) ? 1 : mul * 256;
            sum += s.charAt(i) * mul;
        }
        return (int) (Math.abs(sum) % M);
    }

    //returns the hash value for a given ID
    public int hash(String id) {
        return (int) sfold(id, hashes.length);//Bucket
    }

    //inserts a HashObject into the hash table
    public int insert(String id, String seq) {
        HashObject hashObject = new HashObject(id, seq);
        int hashValue = hash(id);

        for (int i = 0; i < hashes.length; i++) {
            int index = (hashValue + i) % 32;

            if (hashes[index] == null) {
                hashes[index] = hashObject;
                System.out.println("Object inserted at index " + index + ": " + hashObject);
                return index;  // successful, return the index
            }
        }
        //can't insert
        System.out.println("Error: Unable to insert, table is full.");
        return -1;
    }

    //removes a HashObject from the hash table
    public HashObject remove(String id) {
        int hashValue = hash(id);

        for (int i = 0; i < hashes.length; i++) {
            int index = (hashValue + i) % hashes.length;

            if (hashes[index] != null && hashes[index].getId().equals(id)) {
                HashObject removedObject = hashes[index];
                hashes[index] = null;
                System.out.println("Object removed at index " + index + ": " + removedObject);
                return removedObject; // found and removed
            }
        }

        // not found
        System.out.println("Remove not found for ID: " + id);
        return null;
    }

    // returns an array of non-null HashObjects in the hash table
    public HashObject[] print() throws IOException {
        int notNull = 0;

        //if the object is not empty, count it
        for (HashObject hashObject : hashes) {
            if (hashObject != null) {
                notNull++;
            }
        }

        HashObject[] result = new HashObject[notNull];
        int index = 0;
        //create result array
        for (HashObject hashObject : hashes) {
            if (hashObject != null) {
                result[index++] = hashObject;
            }
        }
        FileWriter fw = new FileWriter("Output.txt");


        return result;
    }

    //searches for a HashObject in the hash table
    public HashObject search(String id) {
        int hashValue = hash(id);

        for (int i = 0; i < hashes.length; i++) {
            int index = (hashValue + i) % hashes.length;
            if (hashes[index] != null && hashes[index].getId().equals(id)) {
                System.out.println("Object found at index " + index + ": " + hashes[index]);
                return hashes[index]; // found the object
            }
        }

        // object not found
        System.out.println("Search not found for ID: " + id);
        return null;
    }
}