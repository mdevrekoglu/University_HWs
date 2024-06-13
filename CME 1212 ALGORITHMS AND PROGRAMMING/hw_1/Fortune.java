import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Fortune {

	// To avoid any letter mistakes
	public static Locale ENG = Locale.forLanguageTag("en");
	// To use random library
	public static Random rnd = new Random();
	// main
	public static void main(String[] args) {

		// Reads the countries.txt
		Stack inputCountries = null;
		try {
			BufferedReader objReader_1 = new BufferedReader(new FileReader("D:\\countries.txt"));
			BufferedReader objReader_2 = new BufferedReader(new FileReader("D:\\countries.txt"));

			// Counting the number of rows
			int counter = 0;
			while (objReader_1.readLine() != null)
				counter++;

			// Creating stack for names and scores
			inputCountries = new Stack(counter);

			// Assigning the countries in fast way
			for (int i = 0; i < counter; i++)
				inputCountries.push(objReader_2.readLine().toUpperCase(ENG).replaceAll(" ", ""));

			objReader_1.close();
			objReader_2.close();
		} catch (Exception e) {
			System.out.println("There is no file such as D:\\countries.txt");
			System.exit(0);
		}

		// This part shorts the countries
		Stack countries = new Stack(inputCountries.size());
		while (!inputCountries.isEmpty()) {
			Stack temp = new Stack(1);
			temp.push(inputCountries.pop());

			while (!countries.isEmpty() && countries.peek().toString().compareTo(temp.peek().toString()) < 0) {
				inputCountries.push(countries.pop());
			}
			countries.push(temp.pop());
		}

		// Reads HighScoreTable.txt
		Stack inputNames = null;
		Stack inputScores = null;
		try {
			BufferedReader objReader_1 = new BufferedReader(new FileReader("D:\\HighScoreTable.txt"));
			BufferedReader objReader_2 = new BufferedReader(new FileReader("D:\\HighScoreTable.txt"));

			// Counting the number of rows
			int counter = 0;
			while (objReader_1.readLine() != null) // (strCurrentLine = objReader.readLine()) != null
				counter++;

			inputNames = new Stack(counter + 1);
			inputScores = new Stack(counter + 1);

			// Assigning the names and scores in fast way
			for (int i = 0; i < counter; i++) {
				String[] temp = objReader_2.readLine().toUpperCase(ENG).split(" ");
				inputNames.push(temp[0]);
				inputScores.push(temp[1]);
			}

			objReader_1.close();
			objReader_2.close();
		} catch (Exception e) {
			System.out.println("There is no file such as D:\\HighScoreTable.txt");
			System.exit(0);
		}

		// This one creates all chars in alphabet
		Stack letters = new Stack(26);
		for (int i = 65; i <= 90; i++) {
			letters.push((char) i);
		}

		int step = 0, score = 0, characterCounter;

		// Creates random integer		
		int randomCountryNumber = rnd.nextInt(0, countries.size());
		System.out.println("Randomly generated number: " + randomCountryNumber + "\n\n");

		// Pops all elements until the correct country
		for (int i = 0; i < randomCountryNumber - 1; i++)
			countries.pop();

		// Creates two queue to store country's name and pattern
		Queue pattern = new Queue(countries.peek().toString().length());
		Queue word = new Queue(countries.peek().toString().length());
		for (int i = 0; i < countries.peek().toString().length(); i++) {
			word.enqueue(countries.peek().toString().charAt(i));
			pattern.enqueue("-");
		}
			
		// This is the main loop
		boolean gameFlag;
		do {
			characterCounter = 0;
			gameFlag = true;
			step++;

			// Generates a random char and creates a tempStack and tempLetters stack
			int randomAlp = rnd.nextInt(0, letters.size());
			Stack tempStack = new Stack(1);
			Stack tempLetters = new Stack(letters.size() - 1);	
			
			// Assigns the pattern if there is match with created letter
			int length = letters.size();
			for (int i = 0; i < length; i++) {
				if (i == randomAlp) {
					tempStack.push(letters.pop());
				} 
				else {
					tempLetters.push(letters.pop());
				}
			}			

			// Creates temporary pattern
			System.out.print("Pattern: ");
			length = pattern.size();
			Queue tempPattern = new Queue(length);
			Queue tempWord = new Queue(length);
			
			// A for loop to reach all elements
			for (int i = 0; i < length; i++) {
				if (tempStack.peek().toString().equalsIgnoreCase(word.peek().toString())) {
					tempWord.enqueue(word.dequeue());
					pattern.dequeue();
					tempPattern.enqueue(tempStack.peek());
					characterCounter++;
					System.out.print(tempStack.peek() + " ");
				} else {
					tempWord.enqueue(word.dequeue());
					System.out.print(pattern.peek().toString() + " ");
					tempPattern.enqueue(pattern.dequeue());
				}
			}
			pattern = tempPattern;
			word = tempWord;

			// There is a function which calculates the score
			score = wheel(score, characterCounter);
			System.out.print("        Step: " + step + "        Score: " + score + "        ");
		
			// To print alphabet and Reassigns the main letter stack  I used a tempLetter
			length = tempLetters.size();
			letters = new Stack(length);
			for (int i = 0; i < length; i++) {
				System.out.print(tempLetters.peek());
				letters.push(tempLetters.pop());
			}
			
			// To print necessary informations for game
			System.out.println("\nGuess: " + tempStack.peek());
			System.out.println("\n");

			length = word.size();
			tempPattern = new Queue(length);

			// Stores new pattern and reassigns the main pattern and word queue
			for (int i = 0; i < length; i++) {
				if (!word.peek().toString().equalsIgnoreCase(pattern.peek().toString())) {
					gameFlag = false;
				}
				tempWord.enqueue(word.dequeue());
				tempPattern.enqueue(pattern.dequeue());
			}
			word = tempWord;
			pattern = tempPattern;
		} while (!gameFlag);

		// This part is about high score table
		System.out.print("Please enter your NAME for High Score table: ");
		Scanner scan = new Scanner(System.in);
		
		// Takes input from user to save name
		inputNames.push(scan.next());
		inputScores.push(score);
		System.out.println("\n");

		// Creates new variables to store names and scores
		Stack names = new Stack(inputNames.size());
		Stack scores = new Stack(inputNames.size());
		
		// And in this part there is a sort by scores
		while (!inputScores.isEmpty()) {
			int tempInt = Integer.parseInt(inputScores.pop().toString());
			Stack tempStack = new Stack(1);
			tempStack.push(inputNames.pop().toString());

			while (!scores.isEmpty() && Integer.parseInt(scores.peek().toString()) > tempInt) {
				inputScores.push(scores.pop());
				inputNames.push(names.pop());
			}
			scores.push(tempInt);
			names.push(tempStack.pop());
		}
		scan.close();
		// There is a write part
		try {
			PrintWriter writer = new PrintWriter("D:\\HighScoreTable.txt", "UTF-8");
			int length = names.size();
			
			for (int i = 0; i < length; i++) {
				if(i < 10)
					System.out.println(names.peek() + " " + scores.peek());
				writer.println(names.pop() + " " + scores.pop());
			}
			
			writer.close();
		}
		catch(Exception e) {
			System.out.println("There is no file such as D:\\HighScoreTable.txt");
			System.exit(1);
		}
	}

	// A function which calculates the score
	public static int wheel(int score, int characterCounter) {

		// If the guess does not match, the score does not change either
		int randomWheel = rnd.nextInt(1, 9);
		System.out.print("      Wheel: ");
		if (randomWheel == 1) {
			score += 10 * characterCounter;
			System.out.print(10);
		} else if (randomWheel == 2) {
			score += 50 * characterCounter;
			System.out.print(50);
		} else if (randomWheel == 3) {
			score += 100 * characterCounter;
			System.out.print(100);
		} else if (randomWheel == 4) {
			score += 250 * characterCounter;
			System.out.print(250);
		} else if (randomWheel == 5) {
			score += 500 * characterCounter;
			System.out.print(500);
		} else if (randomWheel == 6) {
			score += 1000 * characterCounter;
			System.out.print(1000);
		} else if (randomWheel == 7 && characterCounter != 0) {
			score *= 2;
			System.out.print("Double Money");
		} else if (randomWheel == 7 && characterCounter == 0) {
			score *= 1;
			System.out.print("Double Money");
		} else if (randomWheel == 8) {
			score *= 0;
			System.out.print("Bankrupt");
		}
		return score;
	}
}
