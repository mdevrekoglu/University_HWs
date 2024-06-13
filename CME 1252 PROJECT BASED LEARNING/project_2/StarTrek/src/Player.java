import java.awt.event.KeyEvent;
import java.util.Random;

public class Player {
		
	// There is only one player therefore all variables are static
    private static int cx;
	private static int cy;
	private static int life;
	private static int energy;
	private static int score;
	private static int speed;
	private static Stack backpack;
	private static Random rnd = new Random();
	
	public Player() {
		super();
		Player.cx = -1;
		Player.cy = -1;
		Player.life = 6;
		Player.energy = 50;
		Player.score = 0;
		Player.speed = 2;
		Player.backpack = new Stack(8);
	}

	public int getCx() {
		return cx;
	}
	public void setCx(int cx) {
		Player.cx = cx;
	}
	public int getCy() {
		return cy;
	}
	public void setCy(int cy) {
		Player.cy = cy;
	}
	public Stack getBackpack() {
		return backpack;
	}
	public void setBackpack(Stack backpack) {
		Player.backpack = backpack;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		Player.speed = speed;
	}
	public static int getScore() {
		return score;
	}
	public static void setScore(int score) {
		Player.score = score;
	}
	
	// Creates, prints, and assigns player ship
	public static void createPlayerShip() {
		// determine player location
		do {
			cx = rnd.nextInt(0, 55);
			cy = rnd.nextInt(0, 23);
		}while(StarTrek.map[cy][cx] != ' ');
				
			
		StarTrek.map[cy][cx] = 'P';
						
		// PRINT
		StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
		StarTrek.cn.getTextWindow().output('P', StarTrek.playerColor);		
	}
		
	// If player dies
	public void reborn() {
		if(life > 0) {
			createPlayerShip();
			printLife();
		}
	}
	
	// Checks if the game is ended
	public boolean isTheGameEnded(){
		if(life>0){
			return false;
		}
		else{
			return true;
		}	
	}
	
	// PRINT OPERATIONS
    public static void printEnergy(){
        StarTrek.cn.getTextWindow().setCursorPosition(57, 14);
		System.out.print("Player Energy : " + energy + "  ");   
    }
   
    public static void printScore() {
		StarTrek.cn.getTextWindow().setCursorPosition(57, 15);
		System.out.print("Player Score  : " + score);
	}
   
    public static void printLife() {
    	life--;
		StarTrek.cn.getTextWindow().setCursorPosition(57, 16);
		System.out.print("Player Life   : " + life);
	}
	
    public static void printBackpack() {
		int length = backpack.size();
		Stack temp = new Stack(8);
		for(int i = 0; i < 8; i++) {
			if(i < 8 - length) {
				StarTrek.cn.getTextWindow().setCursorPosition(60, 5 + i);
				System.out.print("|   |");
			}
			else {
				StarTrek.cn.getTextWindow().setCursorPosition(60, 5 + i);
				System.out.print("| " + (char)backpack.peek() + " |");
				temp.push(backpack.pop());
			}
		}
		
		StarTrek.cn.getTextWindow().setCursorPosition(60, 13);
		System.out.print("+---+");		
		length = temp.size();
		for (int i = 0; i < length; i++) {
			backpack.push(temp.pop());
		}
	}
    
    // Change the speed
    public static void setSpeed() {
		if(energy > 0)
			energy = (energy - 1);
		
		if(energy > 0 && speed != 2)
			speed = 2;
		else if(energy == 0 && speed != 1)
			speed = 1;
	}
	  
	// Move and backpack operation
	public static void move(int direction) throws InterruptedException {		
				
		// Checks if there is any neighbor enemy ship
		boolean backPackCheck = false;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (cx + j < 55 && cx + j >= 0 && cy + i < 23 && cy + i >= 0 && (i == 0 || j == 0)
						&& StarTrek.map[cy + i][cx + j] == 'C') {
								
					for(int q = 0; q < 2; q++)
						if(!backpack.isEmpty())
							backpack.pop();
								
					printBackpack();
					backPackCheck = true;
					break;
				}
			}
			if (backPackCheck)
				break;
		}	
		
		//If player can move the remain part works
		if(canMove(direction)) {
			int tempX = 0, tempY = 0;
			tempX = calVariable(direction)[0];
			tempY = calVariable(direction)[1];
					
			
			// To delete old place of player				
			StarTrek.map[cy][cx] = ' ';
			StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
			StarTrek.cn.getTextWindow().output(' ', StarTrek.defaultColor);
			
			int newScore = StarTrek.calculateScore(StarTrek.map[cy + tempY][cx + tempX]);
			if(newScore > 0 && !backpack.isFull()) {
				score += newScore;
				printScore();
			}
			
			
			if(StarTrek.map[cy + tempY][cx + tempX] == '@') {// If player moves to black hole
									
				int[] coordinates = BlackHole.findCoordinate(tempX, tempY);
				
				StarTrek.map[coordinates[1]][coordinates[0]] = ' ';
				StarTrek.map[coordinates[3]][coordinates[2]] = 'P';							
				
				// To see teleportation
				Thread.sleep(500);
				
				StarTrek.cn.getTextWindow().setCursorPosition(coordinates[0], coordinates[1]);
				System.out.print(' ');
				
				StarTrek.blackHole = new BlackHole();
				
				// To print new place
				cx = coordinates[2];
				cy = coordinates[3];
				
			}
			else if(StarTrek.map[cy + tempY][cx + tempX] == ' '){// If player moves to empty place
				// To print new place
				cx += tempX;
				cy += tempY;		
			}
			else {// If player moves to number
				
				StarTrek.cn.setTextAttributes(StarTrek.defaultColor);			
				int index = -1;
				
				
				for(int i = 0; i < StarTrek.numbers.length; i++) {// Finds the index of number
					if(StarTrek.numbers[i].getCx() == StarTrek.playerShip.getCx() + tempX 
							&& StarTrek.numbers[i].getCy() == StarTrek.playerShip.getCy() + tempY) {
						index = i;
						break;
					}
				}
				
				if(!backpack.isFull()) {// If backpack is not full than there will be push
					boolean flag = false;
					
					// And if backpack has at least 1 element which is not '*' or '=' there will be a control
					if(!backpack.isEmpty() && ((char)backpack.peek() != '*' && (char)backpack.peek() != '=')) {
						flag = true;
					}
					
					// Put the new number in backpack
					backpack.push(StarTrek.numbers[index].getType());					
					printBackpack();
					
					// Control the backpack for combination of the numbers in backpack
					if(flag) {
						Thread.sleep(500);
						char character1 = (char)backpack.pop();
						char character2 = (char)backpack.pop();
						if(character1 == '1' && (character2 == '1' || character2 == '?') && StarTrek.option != 1) {
							backpack.push('?');
						}
						else if(character1 == '2' && (character2 == '2' || character2 == '?')) {
							energy += 30;
						}
						else if(character1 == '3' && (character2 == '3' || character2 == '?')) {
							backpack.push('=');
						}
						else if(character1 == '4' && (character2 == '4'|| character2 == '?')) {
							energy += 240;
						}
						else if(character1 == '5' && (character2 == '5'|| character2 == '?')) {
							backpack.push('*');
						}
						printBackpack();
					}
				}	
				// Delete the number from game area
				StarTrek.numbers[index].resetNumber();
				
				// To print new place
				cx += tempX;
				cy += tempY;
			}
			
			// To print the player
			StarTrek.map[cy][cx] = 'P';
			StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
			StarTrek.cn.getTextWindow().output('P', StarTrek.playerColor);
		}		
	}
	
	// This one checks if player can move
	public static boolean canMove(int direction) {
		
		int tempX = 0, tempY = 0;	
		tempX = calVariable(direction)[0];
		tempY = calVariable(direction)[1];
		
		
		if(StarTrek.map[cy + tempY][cx + tempX] != StarTrek.shape && StarTrek.map[cy + tempY][cx + tempX] != '*'
				&& StarTrek.map[cy + tempY][cx + tempX] != '=' && StarTrek.map[cy + tempY][cx + tempX] != 'X'
				&& StarTrek.map[cy + tempY][cx + tempX] != 'C') {
			return true;
		}
				
		return false;
	}
	
	// Backpack drop operation
	public static void backpackDrop(int direction) {
				
		if(!backpack.isEmpty()) {
			// If the element is a number this part works
			if((char)backpack.peek() != '*' && (char)backpack.peek() != '=') {
				backpack.pop();
				Player.printBackpack();
			}
			else{
				
				int tempX = 0, tempY = 0;				
				tempX = calVariable(direction)[0];
				tempY = calVariable(direction)[1];
				
				//If the place is empty then rest of the part works
				if(StarTrek.map[cy + tempY][cx + tempX] == ' ') {
					char character = (char)backpack.pop();
					
					// Place the device according to properties of it
					for(int i = 0; i < StarTrek.devices.length; i++) {
						if(StarTrek.devices[i].getType() == ' ') {
							StarTrek.devices[i].setType(character);
							StarTrek.devices[i].setCx(cx + tempX);
							StarTrek.devices[i].setCy(cy + tempY);
							StarTrek.devices[i].setTimeLeft(25);
							break;
						}
					}
					
					// Place the device on map
					StarTrek.map[cy + tempY][cx + tempX] = character;		
					StarTrek.cn.getTextWindow().setCursorPosition(cx + tempX, cy + tempY);
					StarTrek.cn.getTextWindow().output(character, StarTrek.enemyColor);
					printBackpack();
				}		
			}
		}
	}
	
	// Calculates tempX and tempY variables according to direction
	private static int[] calVariable(int direction) {
		int[] result = new int[2];	
		if (direction == KeyEvent.VK_RIGHT || direction == KeyEvent.VK_D) {
			result[0] = 1;
		}
		else if (direction == KeyEvent.VK_LEFT || direction == KeyEvent.VK_A) {
			result[0] = -1;
		}
		else if (direction == KeyEvent.VK_UP || direction == KeyEvent.VK_W) {
			result[1] = -1;
		}
		else if (direction == KeyEvent.VK_DOWN || direction == KeyEvent.VK_S) {
			result[1] = 1;
		}
		return result;
	}
	
	// If the player is neighbor with 'X' player loses score. This is an additional improvement
	public static void Xcontrol() {
		for (int i = 0; i < 4; i++) {
			if( (cx+i < 55 && StarTrek.map[cy][cx + i] == 'X') | (cx-i >0 && StarTrek.map[cy][cx - i] == 'X')
					|(cy-i>0 && StarTrek.map[cy - i][cx] == 'X') | ( cy+i < 23 && StarTrek.map[cy + i][cx] == 'X')) {
				score--;
			}
		}
	}
}