package wordgame.control;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.interfaces.Cell;
import wordgame.abstraction.interfaces.Direction;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.RCell;

public class BoardControl implements Observer {
	
	public static BoardControl GET = new BoardControl();
	
	private Wordgame model;
	private ArrayList<RCell> wordCells;
	
	public BoardControl() {
		wordCells = new ArrayList<RCell>();
	}
	
	public void setModel(Wordgame model) {
		this.model = model;
	}
	
	public ArrayList<RCell> getWordCells() {
		return wordCells;
	}
	
	public void addCell(RCell cell) {
		wordCells.add(cell);
		System.out.println("Word on board: " + getWord());
	}
	
	public void removeCell(RCell cell) {
		wordCells.remove(cell);
	}
	
	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
			case NEW_TURN:
				wordCells.clear();
				break;
		}
	}
	
	public Direction getDirection() {
		if (wordCells.size() == 0)
			return null;
		else if (wordCells.size() == 1)
			return Direction.LINE;
		
		int row = wordCells.get(0).getRow();
		int col = wordCells.get(0).getCol();
		
		if (row == wordCells.get(1).getRow()) {
			// Horizontal
			for (int i=1; i < wordCells.size(); i++) {
				if (wordCells.get(i).getRow() != row)
					return null;
			}
			return Direction.LINE;
			
		} else if (col == wordCells.get(1).getCol()) {
			// Vertical
			for (int i=1; i < wordCells.size(); i++) {
				if (wordCells.get(i).getCol() != col)
					return null;
			}
			return Direction.COLUMN;
			
		} else {
			return null;
		}
	}

	public String getWord() {
		String word = "";
		Direction dir = getDirection();
		
		if (dir != null) {
			wordCells.sort(new Comparator<RCell>() {
				public int compare(RCell o1, RCell o2) {
					return (o1.getRow() + o1.getCol() > o2.getRow() + o2.getCol()) ? 1 : -1;
				}
			});
			
			
			if (dir == Direction.LINE) {
				int startPos =  wordCells.get(0).getCol();
				int offset = 0;
				
				for (int i=0; i < wordCells.size(); i++) {
					RCell cell = wordCells.get(i);
					int expectedPos = startPos + i + offset;
					
					if (cell.getCol() == expectedPos) {
						word += cell.getLetter();
					} else {
						try {
							Cell modelCell = model.getBoard().getCell(Coordinate.fromRowCol(cell.getRow(), expectedPos));
							if (modelCell.isEmpty())
								return "";
							word += modelCell.getContent();
						} catch (WordgameException e) {
							e.printStackTrace();
						}
						++offset;
					}
				}
			} else {
				int startPos =  wordCells.get(0).getRow();
				int offset = 0;
				
				for (int i=0; i < wordCells.size(); i++) {
					RCell cell = wordCells.get(i);
					int expectedPos = startPos + i + offset;
					
					if (cell.getRow() == expectedPos) {
						word += cell.getLetter();
					} else {
						try {
							Cell modelCell = model.getBoard().getCell(Coordinate.fromRowCol(expectedPos, cell.getCol()));
							if (modelCell.isEmpty())
								return "";
							word += modelCell.getContent();
						} catch (WordgameException e) {
							e.printStackTrace();
						}
						++offset;
					}
				}
			}
		}
		
		return word;
	}
}
