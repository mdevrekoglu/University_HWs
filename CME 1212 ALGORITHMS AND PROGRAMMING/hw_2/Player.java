
public class Player {
		
	// There is two variables to store informations of player
	private String playerName;
	private int playerScore;
	
	// Constructor
	public Player(String playerName, int playerScore) {
		super();
		this.playerName = playerName;
		this.playerScore = playerScore;
	}
	
	// Get and Sets
	public String getPlayerName() {
		return playerName;
	}
	public int getPlayerScore() {
		return playerScore;
	}	
	public void addScore(int scoreToAdd) {
		playerScore += scoreToAdd;
	}
}
