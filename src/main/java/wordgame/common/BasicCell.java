package wordgame.common;

import wordgame.interfaces.Cell;

public class BasicCell implements Cell {
	
	private char content;

	public BasicCell(char content) {
		this.content = content;
	}

	public char getContent() {
		return this.content;
	}
	
	public void setContent(char content) {
		this.content = content;
	}
	
	public boolean isEmpty() {
		return !Character.isLetterOrDigit(this.content);
	}
	
	public String toString() {
		return Character.toString(this.content);
	}

}
