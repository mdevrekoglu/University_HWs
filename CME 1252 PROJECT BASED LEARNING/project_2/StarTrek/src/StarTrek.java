import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class StarTrek {

	// ENIGMA CONSOLE 
	public static enigma.console.Console cn = Enigma.getConsole("Game", 120, 30, 25, 1);

	// VARIABLES FOR GAME
	public static String name;
	public static char[] characters = new char[40];
	public static char[][] map = new char[23][55];
	private int keypr; // key pressed?
	private int rkey; // key (for press/release)
	private KeyListener klis;
	public static char shape = ' ';
	private static String mazeName = "maze.txt";
	public static int option = 1;
	private static Queue input = new Queue(15);
	public static Random rnd = new Random();
	public static Scanner inputName = new Scanner(System.in);

	// TEXT ATTRIBUTES
	public static TextAttributes defaultColor = new TextAttributes(Color.BLACK, Color.WHITE);
	public static TextAttributes wallColor = new TextAttributes(Color.WHITE, Color.BLACK);
	public static TextAttributes playerColor = new TextAttributes(Color.BLUE, Color.WHITE);
	public static TextAttributes bigNumberColor = new TextAttributes(Color.GREEN, Color.WHITE);
	public static TextAttributes blackHoleColor = new TextAttributes(Color.MAGENTA, Color.WHITE);
	public static TextAttributes enemyColor = new TextAttributes(Color.RED, Color.WHITE);
	public static TextAttributes menuColor = new TextAttributes(Color.YELLOW, Color.BLACK);
	public static TextAttributes aboutColor = new TextAttributes(Color.GRAY, Color.BLACK);
	public static TextAttributes defaultColor1 = new TextAttributes(Color.BLACK, Color.BLACK);
	public static TextAttributes scoreColor = new TextAttributes(Color.CYAN, Color.BLACK);
	public static TextAttributes XColor = new TextAttributes(Color.CYAN, Color.WHITE);

	// ELEMENTS IN GAME
	public static Player playerShip = new Player();
	public static Number[] numbers = new Number[500]; // hold the numbers
	public static Computer[] enemyShips = new Computer[100];
	public static Device[] devices = new Device[500];// hold the devices
	public static BlackHole blackHole;

	// ADDITIONAL NEW CHAR CONS
	StarTrek() throws InterruptedException, IOException {

		// ---------------------------------------------------------------------------------
		// Adding keyListerner
		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};
		cn.getTextWindow().addKeyListener(klis);
		// --------------------------------------------------------------------------------
		
		// Creates menu
		menu();

		while (true) {
			Thread.sleep(20);
			
			if (keypr == 1) { 
				keypr = 0;
				
				if (rkey == KeyEvent.VK_1) { // MENU 1 -> HOW TO PLAY
					cn.setTextAttributes(defaultColor1);
					consoleClear();
					about();

					while (true) {
						if(keypr == 1) {
							if (rkey == KeyEvent.VK_ESCAPE) {
								break;
							}
							else {
								keypr = 0;
							}
						}
						Thread.sleep(20);
					}
				} 
				else if (rkey == KeyEvent.VK_2) { // MENU 2 -> GAME MODE SELECT
					cn.setTextAttributes(defaultColor1);
					consoleClear();
					aboutMaze();

					while (true) {
						if(keypr == 1) {
							if (rkey == KeyEvent.VK_A) {
								mazeName = "maze.txt";
								option = 1;
								break;
							} else if (rkey == KeyEvent.VK_B) {
								mazeName = "maze_2.txt";
								option = 2;
								break;
							} else if (rkey == KeyEvent.VK_C) {
								mazeName = "maze_3.txt";
								option = 3;
								break;
							}
							else {
								keypr = 0;
							}
						}
						Thread.sleep(20);
					}
					consoleClear();
				} 
				else if (rkey == KeyEvent.VK_3) { // MENU 3 -> PLAY
					consoleClear();
					playerShip = new Player();
					System.out.print("Please enter your name:");// An input from user to save his/her name on score board
					name = inputName.next();
					keypr = 0;			
					cn.setTextAttributes(defaultColor);
					consoleClear();
					readMap();
					shape =  map[0][0];

					// Setting elements default
					for (int i = 0; i < numbers.length; i++)
						numbers[i] = new Number(-1, -1, ' ', rnd.nextInt(37, 41));

					for (int i = 0; i < devices.length; i++)
						devices[i] = new Device(-1, -1, ' ', -1);

					for (int i = 0; i < enemyShips.length; i++)
						enemyShips[i] = new Computer(-1, -1, false);

					// Setting input queue
					fillInput();

					// Map printing
					printMap();
					printTime(0);
					printInput();
					Player.createPlayerShip();
					enemyShips[0].createEnemyShip();
					Player.printEnergy();
					Player.printScore();
					Player.printLife();
					Computer.printComputerTotalScore();
					Player.printBackpack();
					
					// Works one time to fill game area with 20 elements
					for (int i = 0; i < 20; i++) {
						assignInput();
						fillInput();
					}

					// If the selected mode is not classic there are a few new features such as 'X' and blac khole				
					if (option == 3) {
						addX();
						// For void
						blackHole = new BlackHole();
					} else if (option == 2) {
						// For void
						blackHole = new BlackHole();
					}


					// Variable for the operations to take place
					int counter = 0;
					boolean game = true;
					// MAIN GAME LOOP
					while (game) {
						
						// Checking if the game ended
						if (playerShip.isTheGameEnded()) {
							cn.setTextAttributes(defaultColor1);
							consoleClear();
							cn.setTextAttributes(menuColor);
							gameOver();
							boolean tempFlag = true;
							keypr = 0;
							while (tempFlag) {
								Thread.sleep(20);
								if (keypr == 1) {
									if (rkey == KeyEvent.VK_ESCAPE) {
										tempFlag = false;
									}
									else {
										keypr = 0;
									}
								}
							}
							cn.setTextAttributes(defaultColor1);
							consoleClear();						
							tempFlag = true;
							cn.setTextAttributes(menuColor);
							HighScoreTableForEndgame(Player.getScore(), Computer.getScore(), name);
							keypr = 0;
							while (tempFlag) {
								Thread.sleep(20);
								if (keypr == 1) {
									if (rkey == KeyEvent.VK_ESCAPE) {
										tempFlag = false;
									}
									else {
										keypr = 0;
									}
								}
							}
							consoleClear();
							break;
						}

						Player.Xcontrol();
						Thread.sleep(20);
						counter++;
						Player.printScore();

						
						// ---------------------------------------------------------------------------
						if (keypr == 1) {
							// If keyboard button pressed player move
							if (rkey == KeyEvent.VK_LEFT || rkey == KeyEvent.VK_RIGHT 
									|| rkey == KeyEvent.VK_UP || rkey == KeyEvent.VK_DOWN) {

								if (counter % (42 / playerShip.getSpeed()) == 0) {
									Player.move(rkey);
									keypr = 0;
								}
							}
							// Remove elements from the backpack
							else if (rkey == KeyEvent.VK_W || rkey == KeyEvent.VK_S || rkey == KeyEvent.VK_D
									|| rkey == KeyEvent.VK_A) {
								Player.backpackDrop(rkey);
								keypr = 0;
							} 
							else if (rkey == KeyEvent.VK_ESCAPE) {
								cn.getTextWindow().setCursorPosition(80, 0);
								System.out.println("Are you sure? - Y/N");
								keypr = 0;
								while (true) {
									Thread.sleep(20);								
									if(keypr == 1) {
										if (rkey == KeyEvent.VK_Y) {
											game = false;
											cn.setTextAttributes(defaultColor1);
											consoleClear();
											cn.setTextAttributes(menuColor);
											gameOver();
											keypr = 0;
											while(true) {
												Thread.sleep(20);
												if(keypr == 1) {
													if (rkey == KeyEvent.VK_ESCAPE) {
														break;
													}
													else {
														keypr = 0;
													}
												}
											}	
											consoleClear();
											break;
										}
										else if (rkey == KeyEvent.VK_N) {
											cn.getTextWindow().setCursorPosition(80, 0);
											System.out.println("                     ");
											break;
										}
										else {
											keypr = 0;
										}
									}
								}
							}
							else {
								keypr = 0;
							}
						}
						// -----------------------------------------------------------------------------

						// ELEMENTS AND ENEMY MOVE 1 SECOND
						if (counter % 42 == 0) {
							printTime(counter / 42);

							// Control the situation of the devices
							for (int i = 0; i < devices.length; i++) {
								if(devices[i].getType() != ' ')
									devices[i].setTimeLeftByGame();
							}
							
							// To move computer
							for (int i = 0; i < enemyShips.length; i++) {
								if (enemyShips[i].getAlive())
									enemyShips[i].move();
							}				

							// To move numbers
							for (int i = 0; i < numbers.length; i++) {
								numbers[i].move();
							}
							
							// To set player's speed and energy
							Player.setSpeed(); // change the speed
							Player.printEnergy(); // print energy
						}

						// Creates input variable on the playing field every three seconds
						if (counter % 126 == 0) {
							assignInput();
							fillInput();
							printInput();
						}
					}
				}
				else if (rkey == KeyEvent.VK_4) { // Menu 4 -> High Score Table
					consoleClear();
					HighScoreTable();
					
					while (true) {
						Thread.sleep(20);
						if(keypr == 1) {
							if (rkey == KeyEvent.VK_ESCAPE) {
								consoleClear();
								break;
							}
							else {
								keypr = 0;
							}
						}
					}

				} 
				else if (rkey == KeyEvent.VK_5) { // Menu 5 -> Exit
					consoleClear();
					cn.setTextAttributes(wallColor);
					System.out.println("Are you sure?" + "  Y/N");
					
					
					while (true) {			
						Thread.sleep(20);
						if(keypr == 1) {
							if (rkey == KeyEvent.VK_Y) {
								System.exit(0);
								break;
							}
							else if (rkey == KeyEvent.VK_N) {
								break;
							}
							else {
								keypr = 0;
							}
						}
					}

				}
				menu();
			}
		}
	}

	// Read the map
	private static void readMap() throws InterruptedException {
		try {
			BufferedReader objReader_1 = new BufferedReader(new FileReader(mazeName));
			String sentence;
			int counter = 0;
			while ((sentence = objReader_1.readLine()) != null) {
				map[counter] = sentence.toCharArray();
				counter++;
			}
			objReader_1.close();
		} catch (Exception e) {
			System.out.println("Thre is no file such as maze.txt");
			Thread.sleep(10000);
			System.exit(0);
		}
	}

	// Clear console
	private static void consoleClear() {
		cn.getTextWindow().setCursorPosition(0, 0);
		for (int i = 0; i < cn.getTextWindow().getRows(); i++) {
			for (int j = 0; j < cn.getTextWindow().getColumns() - 1; j++)
				System.out.print(" ");
			if(i != cn.getTextWindow().getRows() - 1)
				System.out.println();
		}
		cn.getTextWindow().setCursorPosition(0, 0);
	}

	// To assign input queue
	private static void fillInput() {
		while (!input.isFull()) {
			int number = rnd.nextInt(0, 40);

			if (number < 12) {
				input.enqueue('1');
			} else if (number < 20) {
				input.enqueue('2');
			} else if (number < 26) {
				input.enqueue('3');
			} else if (number < 31) {
				input.enqueue('4');
			} else if (number < 35) {
				input.enqueue('5');
			} else if (number < 37) {
				input.enqueue('=');
			} else if (number < 38) {
				input.enqueue('*');
			} else if (number < 40) {
				input.enqueue('C');
			}
		}
	}

	// Put the treasures on map according to their properties
	private static void assignInput() {
		// Determine the location
		int x, y;
		do {
			x = rnd.nextInt(0, 23);
			y = rnd.nextInt(0, 55);
		} while (map[x][y] != ' ');

		char element = (char) input.dequeue();
		map[x][y] = element;
		cn.getTextWindow().setCursorPosition(y, x);

		// Seperate treasures according to their types
		if (element == '*' || element == '=') {
			for (int i = 0; i < devices.length; i++) { // To find empty index
				if (devices[i].getType() == ' ') {
					devices[i].setCx(y);
					devices[i].setCy(x);
					devices[i].setType(element);
					devices[i].setTimeLeft(25);
					cn.getTextWindow().setCursorPosition(y, x);
					cn.getTextWindow().output(element, enemyColor);
					break;
				}
			}
		} else if (element == 'C') {
			for (int i = 0; i < enemyShips.length; i++) { // To find empty index
				if (!enemyShips[i].getAlive()) {
					enemyShips[i].setCx(y);
					enemyShips[i].setCy(x);
					enemyShips[i].setAlive(true);
					cn.getTextWindow().output(element, enemyColor);
					break;
				}
			}
		}
		else {
			for (int i = 0; i < numbers.length; i++) {
				if (numbers[i].getType() == ' ') {
					// int cx, int cy, char type, int direction
					numbers[i].setCx(y);
					numbers[i].setCy(x);
					numbers[i].setType(element);
					numbers[i].setDirection(rnd.nextInt(37, 41));
					if (element == '4' || element == '5') {
						cn.getTextWindow().output(element, bigNumberColor);
					} else {
						cn.getTextWindow().output(element, defaultColor);
					}
					break;
				}
			}
		}
	}

	// Read and print High Score Table
	private static void HighScoreTable() throws IOException {

		cn.setTextAttributes(menuColor);
		String[] namesOfScoreTable = new String[10];
		int[] scoresOfScoreTable = new int[10];
		int index = 0;
		File myObj = new File("HighScoreTable.txt");
		Scanner myReader = new Scanner(myObj);
		while (myReader.hasNextLine()) {
			String[] temp = myReader.nextLine().split("#");
			namesOfScoreTable[index] = temp[0];
			scoresOfScoreTable[index] = Integer.valueOf(temp[1]);
			index++;
		}
		myReader.close();
		System.out.println("###########################");
		System.out.println("|     HIGH SCORE TABLE    |");
		System.out.println("|    NAME    |    SCORE   |");
		for (int i = 0; i < 10; i++) {
			cn.getTextWindow().setCursorPosition(0, i + 3);
			System.out.print("|" + namesOfScoreTable[i]);
			cn.getTextWindow().setCursorPosition(13, i + 3);
			System.out.print("|" + scoresOfScoreTable[i]);
			cn.getTextWindow().setCursorPosition(26, i + 3);
			System.out.println("|");

		}
		cn.getTextWindow().setCursorPosition(0, 13);
		System.out.println("###########################");
		cn.setTextAttributes(defaultColor);

	}

	// Save the new scores and print them
	private static void HighScoreTableForEndgame(int scoreOfPlayer, int scoreOfComputer, String nameOfPlayer)
			throws IOException {
		String[] namesOfScoreTable = new String[10];
		int[] scoresOfScoreTable = new int[10];
		int index = 0;
		File myObj = new File("HighScoreTable.txt");
		Scanner myReader = new Scanner(myObj);
		while (myReader.hasNextLine()) {
			String[] temp = myReader.nextLine().split("#");
			namesOfScoreTable[index] = temp[0];
			scoresOfScoreTable[index] = Integer.valueOf(temp[1]);
			index++;
		}
		myReader.close();

		// Writing the new score board to file
		int finalScore = scoreOfPlayer - scoreOfComputer;
		for (int i = 0; i < 10; i++) {
			if (finalScore > scoresOfScoreTable[i]) {
				scoresOfScoreTable[i] = finalScore;
				namesOfScoreTable[i] = nameOfPlayer;
				break;
			}
		}
		FileWriter myWriter = new FileWriter("HighScoreTable.txt");
		for (int i = 0; i < 10; i++) {
			myWriter.write(namesOfScoreTable[i] + "#" + scoresOfScoreTable[i] + "\n");
		}
		myWriter.close();
		System.out.println("###########################");
		System.out.println("|     HIGH SCORE TABLE    |");
		System.out.println("|    NAME    |    SCORE   |");
		for (int i = 0; i < 10; i++) {
			cn.getTextWindow().setCursorPosition(0, i + 3);
			System.out.print("|" + namesOfScoreTable[i]);
			cn.getTextWindow().setCursorPosition(13, i + 3);
			System.out.print("|" + scoresOfScoreTable[i]);
			cn.getTextWindow().setCursorPosition(26, i + 3);
			System.out.println("|");

		}
		cn.getTextWindow().setCursorPosition(0, 13);
		System.out.println("###########################");
	}

	// Prints the map
	private static void printMap() {
		for (int i = 0; i < 23; i++) {
			for (int j = 0; j < 55; j++) {
				if (map[i][j] == shape)
					cn.setTextAttributes(wallColor);
				else if (map[i][j] == '*' || map[i][j] == '=' || map[i][j] == 'C')
					cn.setTextAttributes(enemyColor);
				else if (map[i][j] == '4' || map[i][j] == '5')
					cn.setTextAttributes(bigNumberColor);
				else if (map[i][j] == 'P')
					cn.setTextAttributes(playerColor);
				else
					cn.setTextAttributes(defaultColor);

				System.out.print(map[i][j]);
			}
			System.out.println();
		}
		cn.setTextAttributes(defaultColor);
	}

	// Prints the current second
	private static void printTime(int time) {
		cn.getTextWindow().setCursorPosition(57, 22);
		System.out.print("Time: " + time);

	}

	// Prints input queue
	private static void printInput() {
		cn.getTextWindow().setCursorPosition(57, 1);
		cn.setTextAttributes(wallColor);
		System.out.print("<<<<<<<<<<<<<<<");

		cn.getTextWindow().setCursorPosition(57, 2);
		cn.setTextAttributes(defaultColor);

		int length = input.size();
		Queue temp = new Queue(length);
		for (int i = 0; i < length; i++) {
			System.out.print(input.peek());
			temp.enqueue(input.dequeue());
		}

		cn.getTextWindow().setCursorPosition(57, 3);
		cn.setTextAttributes(wallColor);
		System.out.print("<<<<<<<<<<<<<<<");

		cn.setTextAttributes(defaultColor);
		input = temp;

	}

	// Print menu
	public void menu() throws IOException, InterruptedException {
		cn.setTextAttributes(defaultColor1);
		consoleClear();
		BufferedReader menu = new BufferedReader(new FileReader("menu.txt"));
		String line = menu.readLine();
		while (line != null) {
			cn.setTextAttributes(menuColor);
			System.out.println(line);
			line = menu.readLine();

		}
		menu.close();
		cn.getTextWindow().setCursorPosition(0, 0);
	}

	// Print menu 1
	public void about() throws IOException, InterruptedException {
		BufferedReader about = new BufferedReader(new FileReader("About.txt"));
		String line = about.readLine();
		while (line != null) {
			cn.setTextAttributes(aboutColor);
			System.out.println(line);
			line = about.readLine();
		}
		about.close();

		// consoleClear();
	}

	// Print game over to screen
	private static void gameOver() throws IOException, InterruptedException {
		BufferedReader finish = new BufferedReader(new FileReader("final.txt"));
		String line = finish.readLine();
		while (line != null) {
			System.out.println(line);
			line = finish.readLine();
		}
		finish.close();
	}

	// Print menu 2
	private static void aboutMaze() {
		cn.setTextAttributes(wallColor);
		cn.getTextWindow().setCursorPosition(50, 0);
		System.out.println("MODES");
		cn.setTextAttributes(scoreColor);
		System.out.println("A) Classic mode.");
		System.out.println("B) Numbers of the computers increases by 2 in every 30 seconds.");
		System.out.println(
				"C) There are X  elements which moves randomly in the maze and if player encounter with it, loses him/her points.");
		System.out.println("Please press the option you want to choose");
	}

	// This is an additional improvement, if player selects third mode this one adds 'X' to field
	private static void addX() {
		int x, y;
		int limit = 0;
		for (int i = 0; i < numbers.length; i++) {

			if (numbers[i].getType() == ' ') {
				do {
					x = rnd.nextInt(0, 23);
					y = rnd.nextInt(0, 55);
				} while (map[x][y] != ' ');
				map[x][y] = 'X';

				// int cx, int cy, char type, int direction
				numbers[i].setCx(y);
				numbers[i].setCy(x);
				numbers[i].setType('X');

				cn.getTextWindow().setCursorPosition(y, x);
				cn.getTextWindow().output('X', XColor);
				cn.setTextAttributes(defaultColor);
				limit++;

			}
			if (limit == 5)
				break;

		}
	}
	
	// Calculates score both human and computer
	public static int calculateScore(char character) {
		
		switch (character) {			
		case '1':
			return 1;
			
		case '2':
			return 5;
			
		case '3':
			return 15;
			
		case '4':
			return 50;
			
		case '5':
			return 150;
			
		case 'C':
			return 300;
			
		case 'P':
			return 150;	
		
		case ' ':
			return 0;	
		}				
		return -1;	
	}

	// This is an additional improvement, if player selects second mode this one adds computer to field
	public void increaseComputer() {
		// SET ON MAP
		int x, y;
		for (int i = 0; i < 2; i++) {
			do {
				x = rnd.nextInt(0, 23);
				y = rnd.nextInt(0, 55);
			} while (map[x][y] != ' ');

			// SET SOME VARIABLES
			enemyShips[0].setCx(y);
			enemyShips[0].setCy(x);
			enemyShips[0].setAlive(true);

			// PRINT
			cn.getTextWindow().setCursorPosition(y, x);
			cn.setTextAttributes(enemyColor);
			System.out.print("C");

			cn.setTextAttributes(defaultColor);
		}
	}
}