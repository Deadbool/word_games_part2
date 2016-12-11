package wordgame.interfaces;

import wordgame.common.Coordinate;
import wordgame.common.WordgameException;

public interface Board {
	int getWidth();
	int getHeight();
	String[][] toArray(); // for text diplaying
	boolean validCoord(Coordinate c);
	Cell getCell(Coordinate c) throws WordgameException;
	void setCell(Coordinate c, Cell value);
}
