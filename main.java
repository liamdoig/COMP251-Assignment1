package A1;

import A1.Chaining.*;
import A1.Open_Addressing.*;
import java.io.*;
import java.util.*;

public class main {
	
    /**
     * Calculate 2^w
     */
    public static int power2(int w) {
        return (int) Math.pow(2, w);
    }

    /**
     * Uniformly generate a random integer between min and max, excluding both
     */
    public static int generateRandom(int min, int max, int seed) {
        Random generator = new Random();
        //if the seed is equal or above 0, we use the input seed, otherwise not.
        if (seed >= 0) {
            generator.setSeed(seed);
        }
        int i = generator.nextInt(max - min - 1);
        return i + min + 1;
    }

    /**
     * export CSV file
     */
    public static void generateCSVOutputFile(String filePathName, ArrayList<Double> alphaList, ArrayList<Double> avColListChain, ArrayList<Double> avColListProbe) {
        File file = new File(filePathName);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            int len = alphaList.size();
            fw.append("Alpha");
            for (int i = 0; i < len; i++) {
                fw.append("," + alphaList.get(i));
            }
            fw.append('\n');
            fw.append("Chain");
            for (int i = 0; i < len; i++) {
                fw.append("," + avColListChain.get(i));
            }
            fw.append('\n');
            fw.append("Open Addressing");
            for (int i = 0; i < len; i++) {
                fw.append(", " + avColListProbe.get(i));
            }
            fw.append('\n');
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*===========PART 1 : Experimenting with n===================*/
        //Initializing the three arraylists that will go into the output 
        ArrayList<Double> alphaList = new ArrayList<Double>();
        ArrayList<Double> avColListChain = new ArrayList<Double>();
        ArrayList<Double> avColListProbe = new ArrayList<Double>();

        //Keys to insert into both hash tables
        int[] keysToInsert = {164, 127, 481, 132, 467, 160, 205, 186, 107, 179,
            955, 533, 858, 906, 207, 810, 110, 159, 484, 62, 387, 436, 761, 507,
            832, 881, 181, 784, 84, 133, 458, 36};

        //values of n to test for in the experiment
        int[] nList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        //value of w to use for the experiment on n
        int w = 10;

        for (int n : nList) {

            //initializing two hash tables with a seed
            Chaining MyChainTable = new Chaining(w, 137);
            Open_Addressing MyProbeTable = new Open_Addressing(w, 137);

            /*Use the hash tables to compute the average number of 
                        collisions over keys keysToInsert, for each value of n. 
                        The format of the three arraylists to fillis as follows:
                        
                        alphaList = arraylist of all tested alphas 
                                   (corresponding to each tested n)
                        avColListChain = average number of collisions for each
                                         Chain experiment 
                                         (make sure the order matches alphaList)
                        avColListProbe =  average number of collisions for each
                                         Linear Probe experiment
                                           (make sure the order matches)
                        The CSV file will output the result which you can visualize
             */
            //ADD YOUR CODE HERE
            int chainSum=0;
            for (int i=0; i < n; i++) {
            	chainSum += MyChainTable.insertKey(keysToInsert[i]); //inserting keys into chain while keeping track of collisions sum
            }
            avColListChain.add((double) chainSum/n); 				 //adding to the end of the avColList

            int probeSum=0;
            for (int j=0; j < n; j++) {
            	probeSum += MyProbeTable.insertKey(keysToInsert[j]); // inserting keys into probe table while tracking probe collisions
            }
            avColListProbe.add((double) probeSum/n);				 // adding to the end of avColListProbe
            
            alphaList.add((double) n/MyChainTable.m); 				 // adding to the end of the alphaList
        }

        generateCSVOutputFile("n_comparison.csv", alphaList, avColListChain, avColListProbe);

        /*===========    PART 2 : Test removeKey  ===================*/
 /* In this exercise, you apply your removeKey method on an example.
        Make sure you use the same seed, 137, as you did in part 1. You will
        be penalized if you don't use the same seed.
         */
        //Please note the output CSV will be slightly wrong; ignore the labels.
        ArrayList<Double> removeCollisions = new ArrayList<Double>();
        ArrayList<Double> removeIndex = new ArrayList<Double>();
        int[] keysToRemove = {6, 8, 164, 180, 127, 3, 481, 132, 4, 467, 5, 160,
            205, 186, 107, 179};

        //ADD YOUR CODE HERE
        Open_Addressing MyProbeTable2 = new Open_Addressing(w, 137); // initializing new probe table with seed = 137
        int n = 16;
        
        for (int i=0; i < n; i++) {									 // inserting keys 
            MyProbeTable2.insertKey(keysToInsert[i]);
        }
        
        for (int i=0; i < n; i++) {									 // removing keys 
        	removeIndex.add((double) i);							 // storing remove index and the key to be removed
        	removeCollisions.add((double) MyProbeTable2.removeKey(keysToRemove[i])); 
        }
        
        generateCSVOutputFile("remove_collisions.csv", removeIndex, removeCollisions, removeCollisions);

        /*===========PART 3 : Experimenting with w===================*/

 /*In this exercise, the hash tables are random with no seed. You choose 
                values for the constant, then vary w and observe your results.
         */
        //generating random hash tables with no seed can be done by sending -1
        //as the seed. You can read the generateRandom method for detail.
        //randomNumber = generateRandom(0,55,-1);
        //Chaining MyChainTable = new Chaining(w, -1);
        //Open_Addressing MyProbeTable = new Open_Addressing(w, -1);
        //Lists to fill for the output CSV, exactly the same as in Task 1.
        ArrayList<Double> alphaList2 = new ArrayList<Double>();
        ArrayList<Double> avColListChain2 = new ArrayList<Double>();
        ArrayList<Double> avColListProbe2 = new ArrayList<Double>();

        //ADD YOUR CODE HERE
        int[] randomKeys = new int[25];
        for (int j=0; j < randomKeys.length; j++) {
        	
        	int randomKey = generateRandom(0,55,-1);						// generate random key between 0 and 55
        	
        	while (keyExists(randomKey, randomKeys)) {						// while the key already is in the randomKeys array
        		randomKey = generateRandom(0,55,-1);						// generate a new random key
        	}
        	randomKeys[j] = randomKey;
        }
        
        // w = 10, then add 2 each time --> m = 32, 64, 128, 256, 512
        // holding n constant ... inserting 25 keys each time 
        w = 10;
        for (int i=0; i < 5; i+=2){
        	// initialize chain and probe collision sums 
            int chainColSum = 0, probeColSum = 0, avgChainSum = 0, avgProbeSum = 0;
        	
            w += i;																// adjust w - this affects the load factor which is then displayed upon the x-axis of the graph
        	Chaining wTable = new Chaining(w,-1);								// creating table to calculate alpha with.

            //TODO: "you will need to execute 10 simulations for each value of w to obtain representative averages" 
            int simulation = 0;
            while (simulation <= 10) {
            	// do 10 simulations and get averages of each
            	Chaining MyChainTable3 = new Chaining(w, -1);					// initializing new chain table with w and random seed
                for (int j=0; j < randomKeys.length; j++) {						// insert the randomKeys + get the sum 
                	chainColSum += MyChainTable3.insertKey(randomKeys[j]);
                }
                avgChainSum += (chainColSum/randomKeys.length);					// sum Average collisions for each set of insert
            	
                Open_Addressing MyProbeTable3 = new Open_Addressing(w,-1);		// initializing new probe table with w and random seed
            	for (int j=0; j < randomKeys.length; j++) {						// inserting random keys into probe table + getting the collisions sum
            		probeColSum += MyProbeTable3.insertKey(randomKeys[j]);
            	}
            	avgProbeSum += (probeColSum / randomKeys.length);				// sum the average of the probe collisions for each table inserts
                
                simulation++;
            }
        	
            avColListChain2.add((double) avgChainSum/10);						// adding the average of average collisions to the list
        	avColListProbe2.add((double) avgProbeSum/10);						// adding the average of average collisions to the list

			alphaList2.add((double) randomKeys.length/wTable.m); 				// adding to the alpha list ( # of keys inserted / # of slots(m) )
        } 
        
        generateCSVOutputFile("w_comparison.csv", alphaList2, avColListChain2, avColListProbe2);

    }
    
    private static boolean keyExists(int key, int[] keys) {
    	/* Helper method.
    	 * Purpose: to determine if a keyExists in the current array of keys 
    	 * This will be used in task 3, when determining the randomKeys to insert into the tables
    	 * Have to check to make sure that random keys are not duplicated as per assignment instructions
    	 */
    	boolean keyExists = false;
    	
    	for (int i=0; i < keys.length; i++) {
    		if (keys[i] == key) {
    			keyExists = true;
    		}
    	}
    	return keyExists;
    }
}
