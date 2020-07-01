/**
 * This hash map will be implemented with an array of linked lists.
 * @param <K> key
 * @param <V> value
 */
public class MyHashMap <K, V> {
    /**
     * The array will provide for 127 linked lists (indexes from 0 to 126).
     */
    private SinglyLinkedList[] array;

    /**
     * size of map
     */
    private int size;

    /**
     * Constructor with size
     * @param size
     */
    public MyHashMap(int size) {
        this.size = size;
        array = new SinglyLinkedList[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = new SinglyLinkedList();
        }
    }

    /**
     * Put key value pair into map
     * @param key key to search
     * @param value value
     */
    public void put(K key, V value) {
        int hashValue = hashValue(key);
        array[hashValue].addAtEndNode(key, value);
    }

    /**
     * Get the value with the key in the map
     * @param key key to search
     * @return value
     */
    public V get(K key) {
        int hashValue = hashValue(key);
        array[hashValue].reset();
        while (array[hashValue].hasNext()) {
            ObjectNode<K, V> node = array[hashValue].next();
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
        }
        return null;
    }

    /**
     * Check whether the map contains the key
     * @param key key
     * @return true is contains
     */
    public boolean containsKey(K key) {
        int hashValue = hashValue(key);
        return array[hashValue].listSearch(key) != null;
    }

    private int hashValue(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % size;
    }
}
