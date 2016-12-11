package wordgame.interfaces;

public interface Player {
	String getNickname();
	public int getScore();
	public Rack getRack();
	void addPoint(int p);
}
