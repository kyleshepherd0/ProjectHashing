/************************************************************************
 * @file HashTable.java
 * @brief this is the abstract constructor that is used as the parent
 *        for the hash function which implements all of these functions
 * @author Pelin Blanton and Kyle Shepherd
 * @date December 8, 2023
 ************************************************************************/
import java.io.IOException;

public interface HashTable<T extends Comparable<T>, K> {
        public int insert(T id, T sequenceID); //abstract insert function

        public K remove(T sequenceID); //abstract remove function

        public int hash(T sequenceID); //abstract hash function

        public K search(T id); //abstract search function

        public void print () throws IOException ; //abstract print function



}
