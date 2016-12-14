package wordgame.abstraction.common;

import wordgame.abstraction.interfaces.Player;
import wordgame.abstraction.interfaces.Rack;

public class BasicPlayer implements Player {
	
	private String nickname;
	private int score;
	private Rack rack;
	
	public BasicPlayer(String nickname) {
		this.nickname = nickname;
		this.score = 0;
		this.rack = new BasicRack();
	}
	
	public String getNickname() { return nickname; }

	public int getScore() { return score; }

	public Rack getRack() { return this.rack; }
	
	public void addPoint(int p) { this.score += p; }
	
}
