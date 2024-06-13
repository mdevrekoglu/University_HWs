
/**
 * hashData
 */

    // This data storages the key wich is exactly the word itself and keeps track of how many times that word occurs in each txt file
    // For example for 001.txt file there is only 1 'Sarah'

public class hashData<K, V> {
    // An array to keep track of how many times that word occours in each txt file
    private int[] counter;
    // An variable to hold 'key' which is 'word'
    private K key;

    // An constructor for class
    hashData(int size, K dataKey){
        this.counter = new int[size];
        this.key = dataKey;
    }

    // An function that increases the number of elements in given index
    public void add(V value, int bookNo){
        counter[bookNo]++;
    }

    // Returns the key which is also 'word'
    public K getKey() {
        return key;
    }

    // Returns all array which keeps track of how many times that word occours in each txt file
    public int[] getValue(){
        return counter;
    }
}