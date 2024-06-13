import java.awt.event.KeyEvent;
import java.util.Random;

public class Number {
	
	private int cx;
	private int cy;
	private char type;
	private int direction;// stores the direction of last move

	public Number(int cx, int cy, char type, int direction) {
		super();
		this.cx = cx;
		this.cy = cy;
		this.type = type;
		this.direction = direction;
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
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}

	// Remove the number from map and game area
	public void resetNumber() {	
		StarTrek.map[cy][cx] = ' ';
		StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
		StarTrek.cn.getTextWindow().output(' ', StarTrek.defaultColor);
        cx = -1;
        cy = -1;
        type = ' ';
        direction = 0;
	}
	
	// To move numbers
	public void move() {		
		
		boolean flag = true;
		boolean canMove = false;
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(cy + i >= 0 && cy + i < 55 && cx + j >= 0 && cx + j < 55) {
					if(StarTrek.map[cy + i][cx + j] == '*') { // Checks if the number is eliminated
						int newScore = StarTrek.calculateScore(type);
						if(newScore > 0) {
							Player.setScore(newScore + Player.getScore());
						}									
						resetNumber();
						flag = false;
						break;
					}
					else if(StarTrek.map[cy + i][cx + j] == '=') {// Checks if the number can move
						flag = false;
						break;
					}			
					else if((i == 0 || j == 0) && StarTrek.map[cy + i][cx + j] == ' ' 
							&& (type == '4' || type == '5' || type=='X')) { // Checks if the number can move
						canMove = true;
					}
				}		
			}
			if(!flag)
				break;
		}
		
		if(canMove && flag) {// If number can move and number didn't eliminated this part works
			Random rnd = new Random();
			// Print the number to new position
			StarTrek.map[cy][cx] = ' ';
			StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
			StarTrek.cn.getTextWindow().output(' ', StarTrek.defaultColor);
						
			int tempX = calVariable(direction)[0];
			int tempY = calVariable(direction)[1];
			
					
			while(StarTrek.map[cy + tempY][cx + tempX] != ' ') {
				direction = rnd.nextInt(37, 41);// Generate random number for direction
				
				tempX = calVariable(direction)[0];
				tempY = calVariable(direction)[1];	 
			}
			cx += tempX;
			cy += tempY;			
			
			StarTrek.map[cy][cx] = type;
			StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
			if(type!= 'X')
				StarTrek.cn.getTextWindow().output(type, StarTrek.bigNumberColor);	
			else
				StarTrek.cn.getTextWindow().output(type, StarTrek.XColor);	
		}					
	}
	
	// This one calculates tempX and tempY variables according to move's direction
	private int[] calVariable(int direction) {
		int[] result = new int[2];	
		if (direction == KeyEvent.VK_RIGHT) {
			result[0] = 1;
		}
		else if (direction == KeyEvent.VK_LEFT) {
			result[0] = -1;
		}
		else if (direction == KeyEvent.VK_UP) {
			result[1] = -1;
		}
		else if (direction == KeyEvent.VK_DOWN) {
			result[1] = 1;
		}
		return result;
	}
}