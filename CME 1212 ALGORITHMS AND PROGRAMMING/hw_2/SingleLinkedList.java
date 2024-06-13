import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class SingleLinkedList {
	Node head;
	
	// Same as I learned from lesson
	public void add(Object data) {
		
		if(head == null) {
			Node newNode = new Node(data);
			head = newNode;
		}
		else {
			Node temp = head;
			Node newNode = new Node(data);
			
			while(temp.getLink() != null) {
				temp = temp.getLink();
			}
			
			temp.setLink(newNode);
		}	
	}
	
	// Same as I learned from lesson
	public int size() {

		if(head == null) {
			return 0;
		}
		else {
			int counter = 0;
			Node temp = head;
				
			while(temp != null) {
				counter++;
				temp = temp.getLink();
			}
			
			return counter;
		}
	}
	
	// Same as I learned from lesson
	public void display() {
		if(head == null) {
			System.out.println("Linked list is empty!");
		}
		else {	
			Node temp = head;
			
			while(temp != null) {
				System.out.print(temp.getData());
				temp = temp.getLink();
			}
		}
	}
	
	// This one removes an object that we want only one time
	// There is a small change between I learned from lesson
	public void remove(Object dataToDelete) {
		if(head == null) {
			System.out.println("Linked list is empty!");
		}
		else {		
			// I created that boolean to remove only one element
			boolean removed = false;
			while((Integer)head.getData() == (Integer)dataToDelete && !removed) {
				head = head.getLink();
				removed = true;
			}
			
			if(!removed) {
				Node temp = head;
				Node previous = null;
			
				while(temp != null && !removed) {
					if((Integer)temp.getData() == (Integer)dataToDelete && !removed) {
						previous.setLink(temp.getLink());
						temp = previous;	
						removed = true;
					}
					previous = temp;
					temp = temp.getLink();
				}	
			}			
		}
	}
	
	// Same as I learned from lesson
	public int findMax() {
		if(head == null) {
			System.out.println("Linked list is empty!");
			return Integer.MIN_VALUE;
		}
		else {
			int maxValue = Integer.MIN_VALUE;		
			Node temp = head;
			
			while(temp != null) {
				if((Integer)temp.getData() > maxValue) {
					maxValue = (Integer)temp.getData();
				}
				temp = temp.getLink();
			}		
			return maxValue;
		}	
	}
	
	// Same as I learned from lesson
	public int findMin() {
		if(head == null) {
			//System.out.println("Linked list is empty!");
			return Integer.MAX_VALUE;
		}
		else {
			int minValue = Integer.MAX_VALUE;
			
			Node temp = head;
			
			while(temp != null) {
				if((Integer)temp.getData() < minValue) {
					minValue = (Integer)temp.getData();
				}
				temp = temp.getLink();
			}
			return minValue;
		}
	}

	// Checks if the given object is being contained in SLL
	public Boolean contain(Object data) {
		if(head == null) {
			System.out.println("Linked list is empty!");
			return false;
		}
		else {
			Node temp = head;
			
			while(temp != null) {
				if((Integer)temp.getData() == (Integer)data)
					return true;
				temp = temp.getLink();
			}
			return false;
		}
	}
	
	// An function that determines if the SLL is empty
	public Boolean isEmpty() {
		return head == null;
	}
	
	// Counts number of specific element in SLL
	public int count(Object data) {
		
		if(head == null) {
			System.out.println("Linked list is empty!");
			return 0;
		}
		else {		
			int counter = 0;
			Node temp = head;
			
			while(temp != null) {
				if((Integer)temp.getData() == (Integer)data)
					counter++;
				temp = temp.getLink();
			}			
			return counter;
		}
	}

	// Removes an element by index
	public void removeByIndex(int index) {	
		if(head == null) {
			System.out.println("Linked list is empty!");
		}
		else if(index < 0 || index > size()) {
			System.out.println("The index is wrong!");
		}
		else {
			
			if(index == 0) {
				head = head.getLink();
			}
			else {
				int counter = 0;
				Node temp = head;
				Node previous = null;
				
				while(counter != index) {
					previous = temp;
					temp = temp.getLink();			
					counter++;
				}
				previous.setLink(temp.getLink());
				temp = previous;
			}
		}
	}
		
	// It is an sort algorithm, it can be usable with Player Class
	public void sort(){
		
		// Creating an list to save sorted elements
		SingleLinkedList sorted = new SingleLinkedList();	
		// Size of the main SLL
		int size = size();
		
		// This one works until the both SLL has equal elements
		while(sorted.size() != size) {
			Node temp = head;
			int biggestNumber = Integer.MIN_VALUE;
			int index = -1;
			Object data = null;
			
			// Finds the biggest score and it's index
			for(int i = 0; i < size(); i++) {
				
				if(((Player)temp.getData()).getPlayerScore() >= biggestNumber) {
					data = temp.getData();
					biggestNumber = ((Player)data).getPlayerScore();
					index = i;
				}
				temp = temp.getLink();
			}
			// Add that variable to the sorted list
			sorted.add(data);
			// Than remove added data from old SLL
			removeByIndex(index);	
		}	
		// After all reassign the old SLL
		head = sorted.head;
	}
	
	// Prints the HighScoreTable, it can be usable with Player Class
	public void printHighScore() {
		if(head == null) {
			System.out.println("Linked list is empty!");
		}
		else {
			int counter = 0;
			Node temp = head;
			System.out.println("\n\nHighScoreTable:\n");
			while(counter < 10 && temp != null) {
				System.out.println(String.format("%-12s  %s",((Player)temp.getData()).getPlayerName(), ((Player)temp.getData()).getPlayerScore()));
				temp = temp.getLink();
				counter++;
			}
		}
	}
	
	// Reads from .txt and assigns it, it can be usable with Player Class
	public void readFromFile() {

		// The high score table is formatted as shown in example
		// One name and after one score, for example;
		// John
		// 60
		try {
			// I imported the BufferReader to read file
			BufferedReader objReader = new BufferedReader(new FileReader("D:\\HighScoreTable.txt"));

			// First two lines
			String sentence1 = objReader.readLine();
			String sentence2 = objReader.readLine();
					
			while (sentence1 != null && sentence2 != null) {
				add(new Player(sentence1, Integer.parseInt(sentence2)));
				sentence1 = objReader.readLine();
				sentence2 = objReader.readLine();
			}
			
			objReader.close();
		} catch (Exception e) {
			System.out.println("There is no file such as D:\\HighScoreTable.txt");
			System.exit(0);
		}
	}

	// Saves the informations of SLL to .txt, it can be usable with Player Class
	public void saveToFile() {		
		if(head == null) {
			System.out.println("Linked list is empty!");
		}
		else {// There is a write part	
			
			try {					
				PrintWriter writer = new PrintWriter("D:\\HighScoreTable.txt", "UTF-8");				
				Node temp = head;
				
				while(temp != null) {
					writer.println(((Player)temp.getData()).getPlayerName());
					writer.println(((Player)temp.getData()).getPlayerScore());
					temp = temp.getLink();
				}
				
				writer.close();
			}
			catch(Exception e) {
				System.out.println("There is no file such as D:\\HighScoreTable.txt");
				System.exit(2);
			}		
		}		
	}	
}
