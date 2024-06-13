class Node {
		
		// 0 = start, 1 = finish, 2 = wall, 3 = empty, 4 = checked, 5 = finalpath
		private int cellType = 0;// stores cell type
		private int hops;// stores how many jump done
		private int x;// stores x coordinate
		private int y;// stores y coordinate
		private int lastX;// stores x of the point it came from
		private int lastY;// stores y of the point it came from
	
		public Node(int type, int x, int y) {	//CONSTRUCTOR
			cellType = type;
			this.x = x;
			this.y = y;
			hops = -1;
		}
		
		public int getX() {
			return x;		
		}	
		
		public int getY() {
			return y;
		}
		
		public int getLastX() {
			return lastX;
		}
		
		public int getLastY() {
			return lastY;
		}
		
		public int getType() {
			return cellType;
		}
		
		public int getHops() {
			return hops;
		}
		
		public void setType(int type) {
			cellType = type;
		}
		
		public void setLastNode(int x, int y) {
			lastX = x;
			lastY = y;
		}
		
		public void setHops(int hops) {
			this.hops = hops;
		}
}
