package wordgame.abstraction.common;

import java.util.HashMap;
import java.util.Map;

import wordgame.abstraction.interfaces.Board;
import wordgame.abstraction.interfaces.Cell;

public class SquareBoard implements Board {
	
	// Attributes & constructor
	
	protected final int size;
	private Map<String, Cell> grid;
	
	public SquareBoard(int size){
		this.size = size;
		this.grid = new HashMap<String, Cell>(size * size);
	}
	
	// Getters

	public int getWidth() {
		return this.size;
	}

	public int getHeight() {
		return this.size;
	}
	
	public boolean validCoord(Coordinate c) {
		if(Coordinate.A_ASCII_CODE <= c.x && 
			c.x <= Coordinate.A_ASCII_CODE + this.size &&
			1 <= c.y && c.y <= this.size){
			return true;
		}
		return false;
	}

	public Cell getCell(Coordinate c) throws WordgameException {
		if(!validCoord(c)){
			throw new WordgameException("Invalid coordinate");
		}
		return this.grid.get(c.toString());
	}
	
	public String[][] toArray() {
		String[][] dataGrid = new String[this.size][this.size];
		
		for(int x = 0; x < this.size; x++){
			for(int y = 0; y < this.size; y++) {
				try {
					dataGrid[x][y] = this.getCell(new Coordinate((char) (Coordinate.A_ASCII_CODE + x), y+1)).toString();
				} catch (WordgameException e) {
					e.printStackTrace();
				}
			}
		}
		
		return dataGrid;
	}
	
	// Setter
	
	public void setCell(Coordinate c, Cell value) {
		this.grid.put(c.toString(), value);
	}
	
}
