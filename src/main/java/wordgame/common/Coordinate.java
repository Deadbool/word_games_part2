package wordgame.common;

import wordgame.interfaces.Tuple;

public class Coordinate implements Tuple<Character, Integer> {
	
	public static final int A_ASCII_CODE = 65;
	
	public final char x;
	public final int y;
	
	public Coordinate(char x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return this.x + ";" + this.y;
	}
	
	public Coordinate incX() { return new Coordinate((char) (this.x + 1), this.y); }
	
	public Coordinate incY() { return new Coordinate(this.x, this.y + 1); }
	
	public Coordinate decX() { return new Coordinate((char) (this.x - 1), this.y); }
	
	public Coordinate decY() { return new Coordinate(this.x, this.y - 1); }
	
}
