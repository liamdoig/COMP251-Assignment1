package A1;

import static A1.main.*;

public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }

    /**
     * Implements the hash function g(k)
     */
    public int probe(int key, int i) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int hashValue = ((((this.A * key) % power2(w)) >> (w-r)) + i) % power2(r);
    	
        return hashValue;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int i = 0; 							// the number of collisions 
    	
    	int hashValue = probe(key, i); 	
    	int ogHashValue = hashValue;
    	//TODO: what if there are no empty slots in the table? Then we are in an infinite loop... And that is BAD. i.e.) Table is full 
    	while (!isSlotEmpty(hashValue)) {	//check slot is empty, 
    		i++;							// if it isn't, then increment i by 1
    		hashValue = probe(key, i);		// and get a new HashValue 
    		if (hashValue == ogHashValue) { // if the hashValue = to the Original Hash Value then we have gone through the whole table, 
    			break;						//so break out of loop
    		}
    	}
    	Table[hashValue] = key; 			//insert key into table with index hashValue

        return i;							// return i as it equals the number of collisions 
    }

    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */
    public int removeKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int i = 0; 							// the number of collisions 
    	
    	int hashValue = probe(key, i); 		// get the original hashValue
    	int ogHashValue = hashValue;		// copy of the original hashValue 
    	boolean keyExists = true;
    	
    	//TODO: account for edge case of key not being in hash table, so we do not want to make any changes, and return the number of slots visited
    	while (Table[hashValue] != key) {	// check if the key at Table[hashValue] is the right key we want to delete
    		i++;							// if it isn't, then increment i by 1
    		hashValue = probe(key, i);		// and get a new HashValue to try again!  
    		
    		if (hashValue == ogHashValue) { // if current hashValue == ogHashValue then break out of the loop as we have gone through all cells
    			keyExists = false;
    			break;
    		}
    	}
    	
    	if (keyExists) {						// if keyExists then make the value at that key = -1 (removed), else do nothing
        	Table[hashValue] = -1; 				//set that key value to -1 (deleted or empty slot)
    	} 

        return i;							// return i as it equals the number of collisions 
    }

}
