
public class HashTable<K, V> implements HashTableInterface<K, V>{
    private hashData<K, V>[] hashTable; 
	private int LOCATIONS_USED; 
	private int DEFAULT_SIZE; 
	private double MAX_LOAD_FACTOR;
    private int COLUMN_SIZE;
	private int HASH_TYPE;
	private int PROBE_TYPE;

	// For statistic
	private int totalCollusion = 0;
	private int rehashCounter = 0;

	// A constructor to create hashtable without selecting its size
    @SuppressWarnings("unchecked")
	public HashTable(int columnSize, double maxLoadFactor, int hashType, int probeType) {
        // Assigning values
		this.COLUMN_SIZE = columnSize;
		this.MAX_LOAD_FACTOR = maxLoadFactor;
		this.HASH_TYPE = hashType;
		this.PROBE_TYPE = probeType;

		// Creating hashTable
		DEFAULT_SIZE = 2477;
		hashTable = new hashData[DEFAULT_SIZE];

		// Assigning default value   
		LOCATIONS_USED = 0;
	} 

	// A constructor to create hashtable with seleceting its size
	@SuppressWarnings("unchecked")
	public HashTable(int tableSize, int numberOfBooks, double maxLoadFactor, int hashType, int probeType) {
        // Assigning values
		this.COLUMN_SIZE = numberOfBooks;
		this.MAX_LOAD_FACTOR = maxLoadFactor;
		this.HASH_TYPE = hashType;
		this.PROBE_TYPE = probeType;

		// Finding prime size and creating hashTable
		int primeSize = makePrime(tableSize);
		hashTable = new hashData[primeSize];

		// Assigning default value
		LOCATIONS_USED = 0;
	}

    // If the given number is not a prime number, this function makes it prime.
    public int makePrime(int num) {
        boolean search = true;
        while(search){
            for (int i = 2; i <= num / 2; i++) {
                if ((num % i) == 0) {
                    num++;
                    break;
                }
                if(i == num / 2){
                    search = false;
                }
            }
        }
		
		return num;
	}

	// This function adds number of element that given txt contains given 'key'
    public void add(K key, V value, int bookNo) {

		// Checking if rehash is necessary
		if (isHashTableTooFull()){
			rehash();
			rehashCounter++;
		}
		
		// Finding first index
		int index = getHashIndex(key);
		// Probing if necessary
		index = probe(index, key);

		// If the given key is being added to the hashtable first time we are creating a variable called hashData
		// Because this data type makes process very fast
		if (hashTable[index] == null) { 
			hashTable[index] = new hashData<K, V>(COLUMN_SIZE, key);
            hashTable[index].add(value, bookNo);
			LOCATIONS_USED++;
		} else { // If the given key is already added, simply the count is increasing
			hashTable[index].add(value, bookNo);
		}
	}

	// If the function called 'rehash' starts to work this one starts to work as well
	// Main purpose of this function is getting ald data and replacing it to its new index
    public void add(hashData<K, V> data){
        if (isHashTableTooFull())
			rehash();
		
		int index = getHashIndex(data.getKey());
		index = probe(index, data.getKey());

        hashTable[index] = data;
    }

	// Creates a specific key for each 'word'
	public int getHashIndex(K key) {
		
		if(HASH_TYPE == 0){ // Normal hashing with java
			int result = key.hashCode() % hashTable.length;
			if (result < 0)
				result += hashTable.length;

			return result;
		}
		else if(HASH_TYPE == 1){ // Simple Summation Function (SSF)
			int result = 0;
			String word = (String)key;
			
			for (int i = 0; i < word.length(); i++) {
				result += word.charAt(i);
			}

			result %= hashTable.length;

			return result;
		}
		else if(HASH_TYPE == 2){ // Polynomial Accumulation Function (PAF)
			int result = 0;
			String word = (String)key;

			for (int i = 1; i <= word.length(); i++) {
				result += Math.pow(5, word.length() - i) * word.charAt(i - 1); 
			}

			result %= hashTable.length;

			return result;
		}
		return -1;
	}

	// An boolean function to check if we need to rehash
	public boolean isHashTableTooFull() {
		double load_factor = (double)LOCATIONS_USED / (double)hashTable.length;
		if (load_factor >= MAX_LOAD_FACTOR)
			return true;
		return false;
	}

	// This function works when rehash is being needed. When it does it doubles the old table's size and finds the prime size
	// And adds old variables to table with their new indexses
	@SuppressWarnings("unchecked")
	public void rehash() {
		hashData<K, V>[] oldTable = hashTable; // Copying old table to get variables
		int oldSize = hashTable.length; // Saving oldSize for 'fori' loop		
		hashTable = new hashData[makePrime(oldSize * 2)]; // Creating new hashTable and having a new size greater than double of old size and prime
		
		// For loop to find variables
		for (int index = 0; index < oldSize; index++) {
			if (oldTable[index] != null){
                add(oldTable[index]);
            }	
		}
	}

    // This function finds an index for key. Key may be already added so this function finds the keys index or an empty place for key
    public int probe(int index, K key) {
		boolean found = false;
		int collusion = 0;
		int probeValue =  31 - (index % 31); // It is the probe value i calculate it early because index value will be changed in the while loop
		while (!found) {
			if (hashTable[index] == null || key.equals(hashTable[index].getKey())) {
                found = true;
				return index;
			} 
			else
			{
				collusion++; // Increasing number of collusions
				if(PROBE_TYPE == 1){ // Linear Probing (LP)
					index = (index + 1) % hashTable.length;
				}
				else if(PROBE_TYPE == 2){ // Double Probing (DP) ? Double Hashing (DH) Using 31 as prime number	
					index = (index + (probeValue * collusion)) % hashTable.length;
				}    	
			}

			if(index < 0){
				index += hashTable.length;
			}
		} 
        return -1;
	}

	// This is a simple remove function for hashtable
	public void remove(K key) {
		int index = getHashIndex(key);
		index = locate(index, key);
		if (index != -1) { 
            hashTable[index] = null;
			LOCATIONS_USED--;
		}
	}
    
	// Checks whether the given key accours in hashTable
	public int locate(int index, K key) {
		int collusion = 0;
		int probeValue = 31 - (index % 31);// It is the probe value i calculate it early because index value will be changed in the while loop

		while (true) {
            if(hashTable[index] == null){
				totalCollusion += collusion;
                return -1;
            }
			else if (key.equals(hashTable[index].getKey())){
				totalCollusion += collusion;
                return index;
            }			
			else {
				if(PROBE_TYPE == 1){ // Linear Probing (LP)
					index = (index + 1) % hashTable.length; 
				}
				else if(PROBE_TYPE == 2){ // Double Probing (DP) ? Double Hashing (DH) Using 31 as prime number
					index = (index + (probeValue * collusion)) % hashTable.length;
				}             
            }

			if(index < 0){ // If index is out of range(which means index is negative), this statement makes it positive
				index += hashTable.length;
			}
			collusion++; // Increasing number of collusions
		} 
	}
    
    // Returns the given keys counter array which counts the number of occurs that 'key's' in each text
	public int[] getValue(K key) {
        int[] result = null;
		int index = getHashIndex(key);
		index = locate(index, key);

		if (index != -1)
			result = hashTable[index].getValue(); 
        
		return result;
	}

	// Checks whether hashTable contains given 'key'
	public boolean contains(K key) {
		int index = getHashIndex(key);
		index = locate(index, key);
		if (index != -1)
			return true;
		return false;
	}

	// Resets hashTable
	public void clear(){
		hashTable = null;
		LOCATIONS_USED = 0;
	}

	// Returns total collusion there is a change in 'locate'
	public int getTotalCollusion(){
		return totalCollusion;
	}

	// Returns table size
	public int getTableSize(){
		return hashTable.length;
	}

	// Returns how many times table hashed
	public int getRehashCounter(){
		return rehashCounter;
	}
	
	// Returns if the HashTable is empty
	public boolean isEmpty(){
		return 0 == LOCATIONS_USED;
	}

	// Returns number of elements in HashTable
	public int getSize(){
		return LOCATIONS_USED;
	}
}
