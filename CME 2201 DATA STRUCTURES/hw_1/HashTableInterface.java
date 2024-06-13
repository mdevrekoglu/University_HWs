
public interface HashTableInterface<K, V> {
    
    /**
	 * Adds a new entry to the HashTable. If the given search key already exists
	 * In the HashTable, increases the corresponding value.
	 * 
	 * @param key   an object search key of the new entry
	 * @param value an object associated with the search key
	 * @param bookNo index of the book
	 *
	 */
	public void add(K key, V value, int bookNo);

	/**
	 *  This function is being used on rehash function
	 * 	Main purpose is adding hashDatas to new hashTable
	 * 
	 *  @param data the special variable that contains key and counter of books
	 * 
	 */
	public void add(hashData<K, V> data);

	/**
	 * Removes a specific entry from the HashTable.
	 * 
	 * @param key an object search key of the entry to be removed
	 *
	 */
	public void remove(K key);

	/**
	 * Retrieves from an integer array from the HashData the value associated with a given search key.
	 * 
	 * @param key an object search key of the entry to be retrieved
	 * @return either the value that is associated with the search key or null if no
	 *         such object exists
	 */
	public int[] getValue(K key);

	/**
	 * Sees whether a specific entry is in this HashTable.
	 * 
	 * @param key an object search key of the desired entry
	 * @return true if key is associated with an entry in the HashTable
	 */
	public boolean contains(K key);

	/**
	 * Sees whether this HashTable is empty.
	 * 
	 * @return true if the HashTable is empty
	 */
	public boolean isEmpty();

	/**
	 * Gets the size of this HashTable.
	 * 
	 * @return the number of entries (key-value pairs) currently in the HashTable
	 */
	public int getSize();

	/** Removes all entries from this dictionary. */
	public void clear();

	/**
	 * 	Makes given number prime.
	 * 
	 *  @param num the number will be prime
	 *  @return prime number
	 * 
	 */
	public int makePrime(int num);
	
	/**
	 *  This function creates an index for given key
	 *  There is 3 type of hashing 0- Java hash, 1- SSF, 2-PAF
	 * 	Simple Summation Function (SSF)
	 * 	Polynomial Accumulation Function (PAF)
	 * 
	 * 	@param key this is the variable that function will create index for
	 *  @return index for the key
	 * 
	 */
	public int getHashIndex(K key);

	/**
	 * 	This function doubles the old HashTables size and reassigns the old datas
	 * 
	 */
	public void rehash();

	/**
	 * 	Checks if the load factor of the HashTable is greater than MaxLoadFactor
	 * 	If it is after that function rehash starts to works
	 * 	
	 * 	@return True or False depending on LoadFactor
	 * 
	 */
	public boolean isHashTableTooFull();

	/**
	 * 	Finds an index for given key
	 * 	It can be null (empty index) or a data for same key
	 * 
	 * 	@param index start index
	 * 	@param key key
	 * 
	 *  @return last empty index
	 */
	public int probe(int index, K key);

	/**
	 * 
	 * 	@param index start index
	 *  @param key key that we are looking for
	 * 
	 * 	@return index for given key it can be -1 which means there is not any index for given key 
	 * 			or it can be an integer index
	 * 
	 */
	public int locate(int index, K key);
}
