import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

/**
 * searchEngine
 */
public class searchEngine {

    private Scanner scan = new Scanner(System.in);
    private Scanner scanar = new Scanner(System.in);
    private static int version, loadFactor, hashType, probeType, wordType;
    private static ArrayList<String> stopWords = new ArrayList<>();
    private static ArrayList<String> searchWords = new ArrayList<>();
    private static HashTable<String, Integer> myHasTable;
    private static int[] txtWordCounter;

    // This program coded with VSCode
    searchEngine() {
        
        // This is an string which contains non-alphanumeric chars
        String DELIMITERS = "[-+=" +
                " " + // space
                "\r\n " + // carriage return line fit
                "1234567890" + // numbers
                "’'\"" + // apostrophe
                "(){}<>\\[\\]" + // brackets
                ":" + // colon
                "," + // comma
                "‒–—―" + // dashes
                "…" + // ellipsis
                "!" + // exclamation mark
                "." + // full stop/period
                "«»" + // guillemets
                "-‐" + // hyphen
                "?" + // question mark
                "‘’“”" + // quotation marks
                ";" + // semicolon
                "/" + // slash/stroke
                "⁄" + // solidus
                "␠" + // space?
                "·" + // interpunct
                "&" + // ampersand
                "@" + // at sign
                "*" + // asterisk
                "\\" + // backslash
                "•" + // bullet
                "^" + // caret
                "¤¢$€£¥₩₪" + // currency
                "†‡" + // dagger
                "°" + // degree
                "¡" + // inverted exclamation point
                "¿" + // inverted question mark
                "¬" + // negation
                "#" + // number sign (hashtag)
                "№" + // numero sign ()
                "%‰‱" + // percent and related signs
                "¶" + // pilcrow
                "′" + // prime
                "§" + // section sign
                "~" + // tilde/swung dash
                "¨" + // umlaut/diaeresis
                "_" + // underscore/understrike
                "|¦" + // vertical/pipe/broken bar
                "⁂" + // asterism
                "☞" + // index/fist
                "∴" + // therefore sign
                "‽" + // interrobang
                "※" + // reference mark
                "]";

        // I created a simple menu
        System.out.println("\nWelcome to Simple Search Engine Program");
        System.out.println("Please read the given report before using the program\n");
        System.out.println("Please select an version below\n");
        System.out.println("1- Manuel version");
        System.out.println("2- Ali Hoca's version\n");

        System.out.print("Version: ");
        version = scan.nextInt();
        System.out.println();

        // If the menu 1 is selected, manual code will start. I mean we can change important variables such as;
        // Load Factor, Key creating type, Hashing type
        if (version == 1) {
            System.out.println("\nPlease enter a load number (1-9)");
            System.out.print("Load factor = ");
            loadFactor = scan.nextInt();
            if (loadFactor > 9 || loadFactor < 1)
                System.exit(1);

            System.out.println("\nPlease enter a hashType (0-JavaHash, 1-SSF, 2-PAF)");
            System.out.print("HashType: ");
            hashType = scan.nextInt();
            if (hashType > 2 || hashType < 0)
                System.exit(2);

            System.out.println("\nPlease enter a probeType (1- Linear Probing, 2- Double Probing)");
            System.out.print("Probing Type: ");
            probeType = scan.nextInt();
            if (probeType > 2 || probeType < 1)
                System.exit(3);

            System.out.println("\nPlease enter wordType (1- Every word, 2- Remove stopwords)");
            System.out.print("Word Type: ");
            wordType = scan.nextInt();
            if(wordType < 1 || wordType > 2)
                System.exit(4);
            

            // In this part search words are being read
            BufferedReader searchReader = null;
            String searchWord;
            try {
                searchReader = new BufferedReader(new FileReader("search.txt"));

                while ((searchWord = searchReader.readLine()) != null) {
                    searchWords.add(searchWord);
                }

                searchReader.close();
            } catch (Exception e) {
                System.out.println(e);
                System.exit(5);
            }
            
        } else if (version == 2) { // If the mode is Ali Hoca, important variables are being assigned for Ali hoca        
            loadFactor = 6;
            hashType = 2;
            probeType = 2;
            wordType = 2;
        } else { // If the user selects neither 1 nor 2, program will be stoped
            System.out.println("Wrong choice");
            System.exit(0);
        }

        // This part reads stopwords
        BufferedReader stopWordReader = null;
        String stopWord;
        try {
            stopWordReader = new BufferedReader(new FileReader("stop_words_en.txt"));

            while ((stopWord = stopWordReader.readLine()) != null) {
                stopWords.add(stopWord);
            }
            stopWordReader.close();
            Collections.sort(stopWords);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(6);
        }

        // This part reads all sport txts from a file called sport
        File dir = new File("sport");
        File[] files = dir.listFiles();
        if(files == null){
            System.out.println("'sport' file can not be found. Please add it to path.");
            System.exit(7);
        }

        // Creating hashtable
        myHasTable = new HashTable<>(files.length, ((double)loadFactor / (double)10), hashType, probeType);
        // Creating an array to count the number of elements in each file
        txtWordCounter = new int[files.length];

        // Fetching all the files
        int wordCounter = 0;
        long indexingTime = System.nanoTime();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                BufferedReader inputStream = null;
                String line;

                try {
                    // Reading each text in the file called 'sport'
                    inputStream = new BufferedReader(new FileReader(files[i]));
                    while ((line = inputStream.readLine()) != null) {
                        line = line.toLowerCase(Locale.ENGLISH);

                        // Removing non-alphanumeric chars from line and getting an array wich contains each word of line
                        String[] temp = line.split(DELIMITERS);
                        
                        // Adding the words to HashTable and also counting non null and non stop-word words in each file
                        // For me counting non stop-word words in each file makes more sense because user will probably not search any stop-word
                        for (String word : temp) {
                            // If the word is null, that word will not be added to hashtable
                            // If it is not, for users choice the word is being checked whether the word is a stopword
                            if (word != null && !word.equalsIgnoreCase("")  && ((wordType == 1) || (wordType == 2 && !stopWords.contains(word)))) {
                                myHasTable.add(word, 1, i); // Adding word to hashTable
                                txtWordCounter[i]++; // Increasing number of word in selected file
                                wordCounter++; // Increasing total number of word in all txt files
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e);
                    System.exit(8);
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            System.out.println(e);
                            e.printStackTrace();
                            System.exit(9);
                        }
                    }
                }
            }
        }
        indexingTime = System.nanoTime() - indexingTime;

        // Manual version
        if(version == 1){

            // With this for loop every single word in search.txt are being checked if hash table contains them
            // Creating an long array to store every search time for each word
            int searchWordsSize = searchWords.size();
            Long[] searchTimeByWord = new Long[searchWordsSize];
            
            Long searchStart = System.nanoTime();
            for (int i = 0; i < searchWordsSize; i++) {
                Long tempSearchTimeStart = System.nanoTime();
                boolean result = myHasTable.contains(stopWords.get(i));
                
                if(result){
                    //System.out.println("Hash Table does not contain: " + word);
                }
                
                searchTimeByWord[i] = System.nanoTime() - tempSearchTimeStart;
            }
            Long totalSearchTime = System.nanoTime() - searchStart;

            // Finding max and min search times
            Long maxSearchTime = Long.MIN_VALUE;
            Long minSearchTime = Long.MAX_VALUE;
            int maxSearchTimeIndex = -1;
            int minSearchTimeIndex = -1;
            for (int i = 0; i < searchWordsSize; i++) {
                Long val = searchTimeByWord[i];
                if(val > maxSearchTime){
                    maxSearchTime = searchTimeByWord[i];
                    maxSearchTimeIndex = i;
                }
                if(val < minSearchTime){
                    minSearchTime = searchTimeByWord[i];
                    minSearchTimeIndex = i;
                }
            }

            // Printing statistic variables
            System.out.println("\n");
            System.out.println("Total Collusion: " + myHasTable.getTotalCollusion());
            System.out.println("Total index time: " + indexingTime); //((double)indexingTime / (double)1000000000) -> to make result in sec
            System.out.println("Average search time for each word in search.txt: " + ((double)totalSearchTime / (double)searchWordsSize));
            System.out.printf("Minimum search time and key: %s | %d\n"
                , searchWords.get(minSearchTimeIndex), searchTimeByWord[minSearchTimeIndex]);
            System.out.printf("Maximum search time and key: %s | %d\n"
                , searchWords.get(maxSearchTimeIndex), searchTimeByWord[maxSearchTimeIndex]);
            System.out.println("Table Size: " + myHasTable.getTableSize());
            System.out.println("Rehash Count: " + myHasTable.getRehashCounter());
            System.out.println("Total search time in search function: " + totalSearchTime);
            System.out.println("Number of words in hashTable: " + wordCounter);
            System.out.println();

        }else if (version == 2){ // Ali Hoca's version
            
            // Warning! 
            // In this part if user tries to find an stop word, the program will fail because stopwords are removed from the cloud

            // Getting input from user
            System.out.print("Please enter the words you are looking for: ");
            String sentence = scanar.nextLine().toLowerCase(Locale.ENGLISH);
            System.out.println();

            // If input is null program stops.
            if(sentence == null){
                System.out.println("You entered nothing program will stop.");
                System.exit(10);
            }

            // Having the words as an array
            String[] words = sentence.split(" ");
            // Creating an double array
            double[] result = new double[files.length];
        
            // For loop for every word given by user             
            for (int i = 0; i < words.length; i++) {
                int[] temparr = myHasTable.getValue(words[i]);

                // If the hash table does not contain word, temparr will be null and at this point we needed an 'if' statement
                if(temparr != null){
                    for (int j = 0; j < temparr.length; j++) {
                        result[j] += temparr[j];
                    }
                }
                else{
                    if(stopWords.contains(words[i])){
                        System.out.printf("'%s' is an stopword so hash table does not contain it.\n", words[i]);
                    }else{
                        System.out.printf("There is not any file contains '%s' word.\n", words[i]);
                    }        
                }
            }

            // To have more logical result, simply dividing our counter to number of words in each txt file
            for (int i = 0; i < result.length; i++) {
                result[i] /= (double)txtWordCounter[i];
            }

            // Finding maximum value from array
            int index = -1;
            double maxValue = Double.MIN_VALUE;
            for (int i = 0; i < files.length; i++) {
                double tempValue = result[i];
                //System.out.println(i + "|" + tempValue); -> To see intensity of the words we are looking for
                if(tempValue > maxValue){
                    maxValue = tempValue;
                    index = i;
                }
            }

            // Printing result
            // If any value can be found in given result txt it is being printed
            if(maxValue != 0 && index != -1){
                System.out.printf("\nThe most logical file is %s\n", files[index].getName());
            }else{ // But if there is not any result which means all index values are 0, then this output accours
                System.out.println("\nThere is not any txt file contains at least one of the given words.");
            }
        }
        System.out.println();

        // Closing all scanners
        scan.close();
        scanar.close();
    }
}