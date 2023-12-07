import java.io.IOException;

public interface HashTable<T extends Comparable<T>, K> {

    public int insert(T id, T sequenceID);

    public K remove(T sequenceID);

    public int hash(T sequenceID);

    public K search(T id);
    
    public K[] print() throws IOException;

}
