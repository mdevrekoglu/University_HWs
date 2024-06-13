
public class Device {
	
	private int cx;
	private int cy;
	private char type;
	private int timeLeft;

	public Device(int cx, int cy, char type, int timeLeft) {
		
		this.cx = cx;
		this.cy = cy;
		this.type = type;
		this.timeLeft = timeLeft;		
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
	public int getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	// Control the situation of the numbers by time
	public void setTimeLeftByGame() {	
		if(timeLeft > 0) {// If time is bigger than zero time decreases
			timeLeft -= 1;
		}
		else { // If the time is zero
			StarTrek.cn.getTextWindow().setCursorPosition(cx, cy);
			StarTrek.map[cy][cx] = ' ';
			System.out.print(' ');
			StarTrek.cn.getTextWindow().setCursorPosition(0, 0);
	        cx = -1;
	        cy = -1;
	        type = ' ';      
	        timeLeft = 25;
		}				
	}   
}
