import java.util.Random;

public class BlackHole {
	
	// To store places of BlackHoles
	private static int Cx1;
	private static int Cx2;
	private static int Cy1;
	private static int Cy2;
	
	Random rnd = new Random();
	
	// The constructor to create BlackHole
	BlackHole(){
		int x, y;
		do {
			x = rnd.nextInt(0, 23);
			y = rnd.nextInt(0, 55);
		}while(StarTrek.map[x][y] != ' ');		
		Cx1 = y;
		Cy1 = x;
		
		StarTrek.map[Cy1][Cx1] = '@';
		
		do {
			x = rnd.nextInt(0, 23);
			y = rnd.nextInt(0, 55);
		}while(StarTrek.map[x][y] != ' ');		
		Cx2 = y;
		Cy2 = x;
			
		StarTrek.map[Cy2][Cx2] = '@';
		
		
		StarTrek.cn.getTextWindow().setCursorPosition(Cx1, Cy1);
		StarTrek.cn.getTextWindow().output('@', StarTrek.blackHoleColor);
		StarTrek.cn.getTextWindow().setCursorPosition(Cx2, Cy2);
		StarTrek.cn.getTextWindow().output('@', StarTrek.blackHoleColor);	
	}
	
	// An function to find where are the ship exactly teleporting
	public static int[] findCoordinate(int tempX, int tempY) {
		int[] coordinate = new int[4];
		
		if(StarTrek.playerShip.getCx() + tempX  == Cx1 && StarTrek.playerShip.getCy() + tempY == Cy1) {
			coordinate[0] = Cx1;
			coordinate[1] = Cy1;
			coordinate[2] = Cx2;
			coordinate[3] = Cy2;
		}
		else {
			coordinate[0] = Cx2;
			coordinate[1] = Cy2;
			coordinate[2] = Cx1;
			coordinate[3] = Cy1;
		}
		return coordinate;
	}
	
	
	public int getCx1() {
		return Cx1;
	}
	public int getCx2() {
		return Cx2;
	}
	public int getCy1() {
		return Cy1;
	}
	public int getCy2() {
		return Cy2;
	}
}
