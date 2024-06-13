package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Statistic 
{
	private static StatisticCategory[] statisticCategory;
	private static StatisticParticipant[] statisticParticipant;
	
	
	public static void readStatistic(Question[] questions, Participant[] participants)
	{
		//With this class we are storing old and new statistics
		//If there is a file named statistics, the program reads it and sums with new statistics
		//If there is not program creates a 'txt' file named statistics and writes it
		
		int counterParticipant = 0, counterCategory = 0;
		String sentence;
		StatisticCategory[] readCategory = null;
		StatisticParticipant[] readParticipant = null;		
		boolean flag = true;
		
		try 
		{
			//We are creating 3 reader because first is for count the variable types
			//Second one is for first type of variable(StatisticCategory)
			//Third one is for second type of variable(StatisticParticipants)
			BufferedReader objReader1 = new BufferedReader(new FileReader("statistics.txt"));
			BufferedReader objReader2 = new BufferedReader(new FileReader("statistics.txt"));
			BufferedReader objReader3 = new BufferedReader(new FileReader("statistics.txt"));
			
			while ((sentence = objReader1.readLine()) != null) 
			{
				if(!sentence.equals(""))//Checks if the line is null
				{
					String[] temp = sentence.trim().split("#");
					if(temp.length == 3 && Operations.arrChecker(temp)) 
						counterCategory++;
					if(temp.length == 4 && Operations.arrChecker(temp)) 
						counterParticipant++;
				}				
			}				
					
			//Assigns the size of array
			readCategory = new StatisticCategory[counterCategory];
			int i = 0;			
 			while (counterCategory > i)
			{			
				String[] temp = objReader2.readLine().trim().split("#");
				if(temp.length == 3 && Operations.arrChecker(temp)) 
				{
					readCategory[i] = new StatisticCategory(temp[0], Integer.parseInt(temp[1].replaceAll("[^0-9]","")),
							Integer.parseInt(temp[2].replaceAll("[^0-9]","")));//Assigns the variable with input
					i++;
				}			
			}		
 			
 			//Assigns the size of array
 			readParticipant = new StatisticParticipant[counterParticipant];
			i = 0;
			while(counterParticipant > i) 
			{
				String[] temp = objReader3.readLine().trim().split("#");
				if(temp.length == 4 && Operations.arrChecker(temp))
				{
					readParticipant[i] = new StatisticParticipant(temp[0], temp[1], 
							Integer.parseInt(temp[2].replaceAll("[^0-9]","")),
							Integer.parseInt(temp[3].replaceAll("[^0-9]","")));//Assigns the variable with input
					i++;
				}			
			}
			
			objReader1.close();
			objReader2.close();
			objReader3.close();
		}
		catch(Exception e)//If there is no file this part works and makes flag 'false' thanks to that our code can continue
		{
			System.out.println("There is not a file such as 'statistic.txt'");
			flag = false;
		}
		
		//In this part we storage the new statistics for category
		String newCategorySentence = "";
		for(int i = 0; i < questions.length; i++) 
			if(!newCategorySentence.contains(questions[i].getCategory())) 
				newCategorySentence += questions[i].getCategory() + "#";		
		
		//In this part we storage the read statistics for category
		if(flag) 
			for(int i = 0; i < readCategory.length; i++) 
				if(!newCategorySentence.contains(readCategory[i].getCategory())) 
					newCategorySentence += readCategory[i].getCategory() + "#";		
		
		//In this part we assign the new statistics for category
		String[] tempCategoryArr = newCategorySentence.trim().split("#");
		int[][] tempCategoryInt = new int[tempCategoryArr.length][2];			
		for(int i = 0; i < tempCategoryArr.length; i++) 
			for(int j = 0; j < questions.length; j++) 
				if(tempCategoryArr[i].equals(questions[j].getCategory())) 
					if(questions[j].getIsTrueFalse().equals("true")) 
						tempCategoryInt[i][0]++;
					else if(!questions[j].getIsTrueFalse().equals("")) 
						tempCategoryInt[i][1]++;	
		
		//In this part we assign the read statistics for category
		if(flag) 
			for(int i = 0; i < tempCategoryArr.length; i++) 
				for(int j = 0; j < readCategory.length; j++) 
					if(tempCategoryArr[i].equals(readCategory[j].getCategory())) 
					{															
						tempCategoryInt[i][0] += readCategory[j].getNumcorrect();
						tempCategoryInt[i][1] += readCategory[j].getNumfalse();				
					}	
		
		//And we create our newest category Statistic
		statisticCategory = new StatisticCategory[tempCategoryArr.length];
		for(int i = 0; i < tempCategoryArr.length; i++) 
			statisticCategory[i] = new StatisticCategory(tempCategoryArr[i], tempCategoryInt[i][0], tempCategoryInt[i][1]);
		
		//-------------------------------------------------------------------------------------------------------------------
		
		//In this part we storage the new statistics for participants
		String newParticipantSentence = "";
		for(int i = 0; i < participants.length; i++) 
			if(!newParticipantSentence.contains(participants[i].getName())) 
				newParticipantSentence += participants[i].getName() + "#";
		
		//In this part we storage the read statistics for participants
		if(flag) 
			for(int i = 0; i < readParticipant.length; i++) 
				if(!newParticipantSentence.contains(readParticipant[i].getName())) 
					newParticipantSentence += readParticipant[i].getName() + "#";
		
		//In this part we assign the new statistics for participants
		String[] tempParticipantArr = newParticipantSentence.trim().split("#");
		String[] cities = new String[tempParticipantArr.length];
		int[][] trueAge = new int[tempParticipantArr.length][2];		
		for(int i = 0; i < tempParticipantArr.length; i++) 
			for(int j = 0; j < participants.length; j++) 
				if(tempParticipantArr[i].equals(participants[j].getName())) 
				{
					cities[i] = participants[j].getAddress().getCity();
					trueAge[i][0] += participants[j].getTrueAnswered();
					trueAge[i][1] = participants[j].getBirthDate().getAge();

				}
		
		//In this part we assign the read statistics for participants
		if(flag) 
			for(int i = 0; i < tempParticipantArr.length; i++) 
				for(int j = 0; j < readParticipant.length; j++) 
					if(tempParticipantArr[i].equals(readParticipant[j].getName())) 
					{															
						cities[i] = readParticipant[j].getCity();
						trueAge[i][0] += readParticipant[j].getCorrect();
						trueAge[i][1] = readParticipant[j].getAge();
					}	
		
		
		//And we create our newest participant Statistic
		statisticParticipant = new StatisticParticipant[tempParticipantArr.length];
			for(int i = 0; i < tempParticipantArr.length; i++) 
				statisticParticipant[i] = new StatisticParticipant(tempParticipantArr[i], cities[i], trueAge[i][0], trueAge[i][1]);	
	}

	public static void writeStatistic() throws FileNotFoundException, UnsupportedEncodingException 
	{
		//This function writes the all statistics to txt
		
		PrintWriter writer = new PrintWriter("statistics.txt", "UTF-8");
		
		for(int i = 0; i < statisticCategory.length; i++) 
			writer.println(statisticCategory[i].getCategory() + "#" + statisticCategory[i].getNumcorrect() 
					+ "#" + statisticCategory[i].getNumfalse());
		
		for(int i = 0; i < statisticParticipant.length; i++)
			writer.println(statisticParticipant[i].getName() + "#" + statisticParticipant[i].getCity() + "#" +
					statisticParticipant[i].getCorrect() + "#" + statisticParticipant[i].getAge());
			
		writer.close();
	}
	
	public static StatisticCategory[] getStatisticCategory()
	{
		return statisticCategory;
	}
	
	public static StatisticParticipant[] getStatisticParticipant()
	{
		return statisticParticipant;
	}
}
