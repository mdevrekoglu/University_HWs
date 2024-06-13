import java.util.ArrayList;
import java.util.Random;

public class Computer {

	private int cx;
	private int cy;
	private boolean alive;
	private static int score = 0;
	private Node[][] map; // A map for path finding algorithm
	private boolean solving = true;
	private Random rnd = new Random();

	public Computer(int cx, int cy, boolean alive) {
		super();
		this.cx = cx;
		this.cy = cy;
		this.alive = alive;
	}

	public int getCx() {
		return cx;
	}
	public void setCx(int cx) {
		this.cx = cx;
	}
	public int getCy() {
		return cy;
	}
	public void setCy(int cy) {
		this.cy = cy;
	}
	public boolean getAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public static int getScore() {
		return score;
	}
	public void setScore(int score) {
		Computer.score = score;
	}
	
	// To delete dead computers
	public void resetComputer() {
		StarTrek.map[cy][cx] = ' ';
		StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
		StarTrek.cn.getTextWindow().output(' ', StarTrek.defaultColor);
		cx = -1;
		cy = -1;
		alive = false;
	}

	// PRINT OPERATIONS
	public static void printComputerTotalScore() {
		StarTrek.cn.getTextWindow().setCursorPosition(57, 19);
		System.out.print("Computer Score: " + score);
	}

	public void createEnemyShip() {

		// SET ON MAP
		do {
			cx = rnd.nextInt(0, 55);
			cy = rnd.nextInt(0, 23);
		} while (StarTrek.map[cy][cx] != ' ');

		// SET SOME VARIABLES
		StarTrek.map[cy][cx] = 'C';
		alive = true;

		// PRINT
		StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
		StarTrek.cn.getTextWindow().output('C', StarTrek.enemyColor);
	}
	
	// This is main function for computer movements
	public void move() {

		// First of all we create an board to make things faster
		char[][] board = StarTrek.map;
		// Than we assign our second map which is created with node class
		reset(board);
		
		
		boolean flag = false;
		boolean canMove = true;

		// In this part we check if the computer can move
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (cx + j < 55 && cx + j >= 0 && cy + i < 23 && cy + i >= 0) {
					if (board[cy + i][cx + j] == '*') {
						// If the computer is neighbor with '*' than the computer is being deleted
						int newScore = StarTrek.calculateScore('C');
						if(newScore > 0) {
							Player.setScore(newScore + Player.getScore());
						}
						resetComputer();
						canMove = false;
						break;
					} 
					else if (board[cy + i][cx + j] == '=') {
						// If the computer is neighbor with '=' than the computer can't move
						canMove = false;
						break;
					}
				}
			}
			if (!canMove)
				break;
		}

		// If there is big number which is two lands away from the computer than the target is that number
		if (canMove) {
			for (int i = -2; i <= 2; i++) {
				for (int j = -2; j <= 2; j++) {
					if (cx + j < 55 && cx + j >= 0 && cy + i < 23 && cy + i >= 0) {
						if (board[cy + i][cx + j] == '4' || board[cy + i][cx + j] == '5') {
							map[cy + i][cx + j] = new Node(1, cy + i, cx + j);// Finish point
							flag = true;
							break;
						}
					}
				}
				if (flag)
					break;
			}

			// If there is number not any big number which is two lands away from the computer, 
			// Than there is a check for small numbers such as 1, 2, 3
			if (!flag) {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (cx + j < 55 && cx + j >= 0 && cy + i < 23 && cy + i >= 0) {
							if (board[cy + i][cx + j] == '1' || board[cy + i][cx + j] == '2'
									|| board[cy + i][cx + j] == '3') {
								map[cy + i][cx + j] = new Node(1, cy + i, cx + j);// Finish point
								flag = true;
								break;
							}
						}
					}
					if (flag)
						break;
				}
			}

			// If there is not any number the target is player
			if (!flag) {
				map[StarTrek.playerShip.getCy()][StarTrek.playerShip.getCx()] = new Node(1, StarTrek.playerShip.getCy(),
						StarTrek.playerShip.getCx());
			}

			// This is the main path finding algorithm We will explain this function on functions part
			Dijkstra();

			// Reset the boolean to use again
			flag = false;
			
			// After path finding the move will occur. In this part we are searching for 1 or 5 numbers
			// If number is 1 it means computer is neighbor with target. If the number is 5 than this is the path
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if ((i == 0 || j == 0) && (cx + j < 55 && cx + j >= 0 && cy + i < 55 && cy + i >= 0)) {
						if (map[cy + i][cx + j].getType() == 5 || map[cy + i][cx + j].getType() == 1) {

							StarTrek.map[cy][cx] = ' ';
							StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
							System.out.print(' ');

							cx += j;
							cy += i;
							
							int newScore = StarTrek.calculateScore(StarTrek.map[cy][cx]);
							if(newScore > 0) {
								score += newScore * 2;
								printComputerTotalScore();
							}

							// If computer reaches Player, player reborn if he/she has extra life
							if (StarTrek.map[cy][cx] == 'P') {
								StarTrek.playerShip.reborn();
							} 
							else { // If there is number, number is being reset
								for (int q = 0; q < StarTrek.numbers.length; q++) {
									if (StarTrek.numbers[q].getCx() == cx && StarTrek.numbers[q].getCy() == cy) {
										StarTrek.numbers[q].resetNumber();
										break;
									}
								}
							}

							// Last move operations
							StarTrek.map[cy][cx] = 'C';
							StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
							StarTrek.cn.getTextWindow().output('C', StarTrek.enemyColor);

							flag = true;
							break;
						}
					}
				}
				if (flag)
					break;
			}
		}
	}

	// There are some informations that we should know before understanding algorithm
	// 0 = start, 1 = finish, 2 = wall, 3 = empty, 4 = checked, 5 = final path
	// '*, C, @, #, =' behave like wall to computer
	// Numbers are empty place for computer. That mean computer can move on them
	// Finish point is being assigned in move function
	public void reset(char[][] board) {
		solving = true;
		map = new Node[23][55];

		for (int i = 0; i < 23; i++) {
			for (int j = 0; j < 55; j++) {
				if (board[i][j] == StarTrek.shape || board[i][j] == 'C' || board[i][j] == '@' || board[i][j] == '*'
						|| board[i][j] == '=') {
					map[i][j] = new Node(2, i, j);
				} else {
					map[i][j] = new Node(3, i, j);
				}
			}
		}
		map[cy][cx] = new Node(0, cy, cx);// Assigns start point
	}

	public void Dijkstra() {
		ArrayList<Node> priority = new ArrayList<Node>();
		priority.add(map[cy][cx]);

		while (solving) {
			if (priority.size() <= 0) { // If the arraylist's size is less or equal to 0, that mean there is no path
				solving = false;
				break;
			}
			int hops = priority.get(0).getHops() + 1; // Increase the number of jumps
			ArrayList<Node> explored = exploreNeighbors(priority.get(0), hops); // Store the points that we just explored
			
			if (explored.size() > 0) {
				priority.remove(0); // If there is at least 1 point that we explored, old one is being removed and
				priority.addAll(explored); // All of the new ones is being added 
			} else { 
				priority.remove(0); // If there is not any new path old one is being deleted
			}
		}
	}

	// To explore neighbors
	public ArrayList<Node> exploreNeighbors(Node current, int hops) { 
		ArrayList<Node> explored = new ArrayList<Node>(); // An array list to store new nodes
		for (int a = -1; a <= 1; a++) {
			for (int b = -1; b <= 1; b++) {
				if (a == 0 || b == 0) { // The movement can happen only 4 directions
					int xbound = current.getX() + a;
					int ybound = current.getY() + b;
					if ((xbound > -1 && xbound < 23) && (ybound > -1 && ybound < 55)) { // Checks if the point is in map
						Node neighbor = map[xbound][ybound];
						// Checks if the chosen point is wall
						if ((neighbor.getHops() == -1 || neighbor.getHops() > hops) && neighbor.getType() != 2) {							
							explore(neighbor, current.getX(), current.getY(), hops); // Another function to explore specific node
							explored.add(neighbor); // Add new node to explored list
						}
					}
				}
			}
		}
		return explored;
	}

	// To explore one specific node
	public void explore(Node current, int lastx, int lasty, int hops) {																		
		// If the selected node is not target and start point assign the node as checked(Checked means 4)
		if (current.getType() != 0 && current.getType() != 1)
			current.setType(4);
		current.setLastNode(lastx, lasty);// Set the node that where we came from									
		current.setHops(hops);
		
		// If the chosen node is target than backtrack starts
		if (current.getType() == 1) { 
			backtrack(current.getLastX(), current.getLastY(), hops);
		}
	}

	// This one works if the algorithm reach the target
	public void backtrack(int lx, int ly, int hops) {
		// While there is hop, set current node as final path
		while (hops > 0) {
			Node current = map[lx][ly];
			current.setType(5);
			lx = current.getLastX();
			ly = current.getLastY();
			hops--;
		}
		// After all assignings set solving false to continue code
		solving = false;
	}
}
