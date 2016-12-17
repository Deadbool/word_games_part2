package wordgame.abstraction.interfaces;

import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.common.WordgameException;

public interface Board {
	int getWidth();
	int getHeight();
	String[][] toStringArray(); // for text diplaying
	boolean validCoord(Coordinate c);
	Cell getCell(Coordinate c) throws WordgameException;
	void setCell(Coordinate c, Cell value);
}
