package game;

import java.io.IOException;
import java.util.*;

public class Dictionary {
	private static String[] dictionary = null;
	private static String[][] dictionaryByLength = null;
		
	public static void setDictionary() throws IOException 
	{
		//This function sets dictionary to use in spellCheck
		//First of all we read dictionary.txt and assign it in a String ARR
		dictionary = Operations.textReader("dictionary.txt");
		
		//Then we find the longest word and create an array for all types of long
		int max = -1;
		for(int i = 0; i < dictionary.length; i++) 
			if(dictionary[i].length() > max)
				max = dictionary[i].length();			
		int[] columnMax = new int[max];
		for(int i = 0; i < dictionary.length; i++) 
			columnMax[dictionary[i].length() - 1]++;
		
		//Then we assign the size of our matrix and sort it
		dictionaryByLength = new String[max][];			
		for(int i = 0; i < 31; i++) 
		{
			int counter = 0;
			String[] temp = new String[columnMax[i]];
			for(int j = 0; j < dictionary.length; j++) 
				if(dictionary[j].length() - 1 == i) 
				{
					temp[counter] = dictionary[j];
					counter++;
				}
			dictionaryByLength[i] = temp;
			Arrays.sort(dictionaryByLength[i]);
		}
	}
	
	//If we need only dictionary this one works
	public static String[] getDictionary() 
	{
		return dictionary;
	}
	//If we need dictionary by length this one works
	public static String[] getDicByLen(int n) 
	{
		return dictionaryByLength[n - 1];
	}
}
