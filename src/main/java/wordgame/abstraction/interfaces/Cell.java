package wordgame.abstraction.interfaces;

public interface Cell {
	char getContent();
	void setContent(char content);
	boolean isEmpty();
	String toString();
}
