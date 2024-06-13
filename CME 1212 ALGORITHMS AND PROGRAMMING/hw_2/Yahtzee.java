import java.util.Random;
import java.util.Scanner;

public class Yahtzee {
	
	private static Random rnd = new Random();
	private static Scanner scan = new Scanner(System.in);
	private static SingleLinkedList playerList;

	public static void main(String[] args) throws InterruptedException { 

		// To create a SingleLinkedList that store all informations of players
		playerList = new SingleLinkedList();
		// This one reads the HighScoreTable. The algorithm is explained in SSL class
		playerList.readFromFile();

		// Taking the players name for high score table
		// But only winner's name is being written on high score table			
		System.out.print("Please enter Player-1's name: ");
		Player player1 = new Player(scan.next(), 0);
		System.out.print("Please enter Player-2's name: ");
		Player player2 = new Player(scan.next(), 0);
		System.out.println("\n");
		
		
		// Creating necessary variables
		// These SLL are for store the players numbers
		SingleLinkedList player1List = new SingleLinkedList();
		SingleLinkedList player2List = new SingleLinkedList();			
		// Booleans for game
		Boolean pointPlayer1, pointPlayer2, flag;
		
		// This is the main game loop, After 10 turn game is being finished
		for(int q = 1; q <= 10; q++) {
			// Resetting variables every turn
			pointPlayer1 = pointPlayer2 = true;
			flag = false;
			
			
			// Adding new numbers to the Linked Lists
			for(int i = 0; i < 3; i++) {
				player1List.add(rnd.nextInt(1, 7));
				player2List.add(rnd.nextInt(1, 7));
			}
						
			// Printing turns and lists
			System.out.println("\nTurn: " + q);	
			System.out.print(player1.getPlayerName() + ": ");
			player1List.display();
			System.out.println("   | Score: " + player1.getPlayerScore());
			System.out.print(player2.getPlayerName() + ": ");
			player2List.display();
			System.out.println("   | Score: " + player2.getPlayerScore());	
				
			// Checks if large straight occurs
			for(int i = 1; i <= 6; i++) {
				// If there is a missing number pointPlayer is being false
				// And flag is being true. Flag means if there is match, the turn will be written again
				if(!player1List.contain(i)) {
					pointPlayer1 = false;					
				}	
				if(!player2List.contain(i)) {
					pointPlayer2 = false;
				}
			}
			
			// If large straight occurs, with for loops numbers 1 to 6 are being deleted from player's numbers
			if(pointPlayer1) {
				for(int i = 1; i <= 6; i++) {
					player1List.remove(i);	
				}
				flag = true;
				player1.addScore(40);		
			}
			if(pointPlayer2) {
				for(int i = 1; i <= 6; i++) {
					player2List.remove(i);					
				}
				flag = true;
				player2.addScore(40);
			}
			
			// Checks if Yahtzee occurs
			for(int i = 1; i <= 6; i++) {
				// It counts the number of 'x' in list(x is the number 1 to 6)
				int total1 = player1List.count(i);
				// If condition if the condition is met, the number is being deleted 4 times
				if(total1 >= 4) {
					for(int j = 0; j < 4; j++) {
						player1List.remove(i);
					}
					flag = true;
					player1.addScore(10);
				}
				// It counts the number of 'x' in list(x is the number 1 to 6)
				int total2 = player2List.count(i);
				// If condition if the condition is met, the number is being deleted 4 times
				if(total2 >= 4) {
					for(int j = 0; j < 4; j++) {
						player2List.remove(i);
					}
					flag = true;
					player2.addScore(10);
				}
			}
				
			// If there is a change, I mean if any player gains score, the variables are being written again
			if(flag) {
				System.out.println();
				System.out.print(player1.getPlayerName() + ": ");
				player1List.display();
				System.out.println("   | Score: " + player1.getPlayerScore());
				System.out.print(player2.getPlayerName() + ": ");
				player2List.display();
				System.out.println("   | Score: " + player2.getPlayerScore());	
			}			
		}
		
		// Choosing the winner
		// Only the winner's name is being added to the list
		// If two player has equal score both of them name is being added to the list
		if(player1.getPlayerScore() > player2.getPlayerScore()) {
			playerList.add(player1);
		}
		else if(player1.getPlayerScore() < player2.getPlayerScore()) {
			playerList.add(player2);
		}
		else {
			playerList.add(player1);
			playerList.add(player2);
		}

		// This one sorts the list. The algorithm is explained in SSL class
		playerList.sort();
		// This one prints TheHighScoreTable
		playerList.printHighScore();
		// This one saves the informations to list. The algorithm is explained in SSL class
		playerList.saveToFile();	
	}
}
