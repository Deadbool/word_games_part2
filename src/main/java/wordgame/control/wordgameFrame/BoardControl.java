package wordgame.control.wordgameFrame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.interfaces.Cell;
import wordgame.abstraction.interfaces.Direction;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.Event;
import wordgame.presentation.components.RCell;

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
		sortCells();
		System.out.println("Word on board: " + getWord() + ", " + getWordPosition() + ", " + getWordDirection());
	}
	
	public void removeCell(RCell cell) {
		wordCells.remove(cell);
		sortCells();
	}
	
	private void sortCells() {
		wordCells.sort(new Comparator<RCell>() {
			public int compare(RCell o1, RCell o2) {
				return (o1.getRow() + o1.getCol() > o2.getRow() + o2.getCol()) ? 1 : -1;
			}
		});
	}
	
	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
			case NEW_TURN:
				wordCells.clear();
				break;
		}
	}
	
	public Direction getWordDirection() {
		if (wordCells.size() == 0)
			return null;
		else if (wordCells.size() == 1) {
			RCell cell = wordCells.get(0);
			boolean top = false;
			boolean bot = false;
			
			try {
				top = !model.getBoard().getCell(Coordinate.fromRowCol(cell.getRow()-1, cell.getCol())).isEmpty();				
			} catch (WordgameException e) {
				e.printStackTrace();
			}
			
			try {
				bot = !model.getBoard().getCell(Coordinate.fromRowCol(cell.getRow()+1, cell.getCol())).isEmpty();				
			} catch (WordgameException e) {
				e.printStackTrace();
			}
			
			return (top || bot) ? Direction.COLUMN : Direction.LINE;
		}
		
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
		Direction dir = getWordDirection();
		
		if (dir != null) {
			if (dir == Direction.LINE) {
				int startPos =  wordCells.get(0).getCol();
				int offset = 0;
				int expectedPos = 0;
				int row = 0;
				Cell modelCell = null;
				
				int realStartPos = startPos;
				try {
					modelCell = model.getBoard().getCell(Coordinate.fromRowCol(wordCells.get(0).getRow(), realStartPos - 1));
					while (!modelCell.isEmpty()) {
						word = modelCell.getContent() + word;
						--realStartPos;
						modelCell = model.getBoard().getCell(Coordinate.fromRowCol(wordCells.get(0).getRow(), realStartPos - 1));
					}
				} catch (WordgameException e1) {
					e1.printStackTrace();
				}				
				
				for (int i=0; i < wordCells.size() + offset; i++) {
					RCell cell = wordCells.get(i - offset);
					row = cell.getRow();
					expectedPos = startPos + i;
					
					if (cell.getCol() == expectedPos) {
						word += cell.getLetter();
					} else {
						try {
							modelCell = model.getBoard().getCell(Coordinate.fromRowCol(row, expectedPos));
							if (modelCell.isEmpty())
								return "";
							
							word += modelCell.getContent();
							++offset;
							
						} catch (WordgameException e) {
							e.printStackTrace();
							return "";
						}
					}
				}
				
				try {
					++expectedPos;
					modelCell = model.getBoard().getCell(Coordinate.fromRowCol(row, expectedPos));
					while (!modelCell.isEmpty()) {
						word += modelCell.getContent();
						++expectedPos;
						modelCell = model.getBoard().getCell(Coordinate.fromRowCol(row, expectedPos));
					}
					
				} catch (WordgameException e) {
					e.printStackTrace();
				}
				
			} else {
				int startPos =  wordCells.get(0).getRow();
				int offset = 0;
				int col = 0;
				int expectedPos = 0;
				Cell modelCell = null;
				
				int realStartPos = startPos;
				try {
					modelCell = model.getBoard().getCell(Coordinate.fromRowCol(realStartPos - 1, wordCells.get(0).getCol()));
					while (!modelCell.isEmpty()) {
						word = modelCell.getContent() + word;
						--realStartPos;
						modelCell = model.getBoard().getCell(Coordinate.fromRowCol(realStartPos - 1, wordCells.get(0).getCol()));
					}
				} catch (WordgameException e1) {
					e1.printStackTrace();
				}
				
				for (int i=0; i < wordCells.size() + offset; i++) {
					RCell cell = wordCells.get(i - offset);
					col = cell.getCol();
					expectedPos = startPos + i;
					
					if (cell.getRow() == expectedPos) {
						word += cell.getLetter();
					} else {
						try {
							modelCell = model.getBoard().getCell(Coordinate.fromRowCol(expectedPos, col));
							if (modelCell.isEmpty())
								return "";
							
							word += modelCell.getContent();
							++offset;
							
						} catch (WordgameException e) {
							e.printStackTrace();
							return "";
						}
					}
				}
				
				try {
					++expectedPos;
					modelCell = model.getBoard().getCell(Coordinate.fromRowCol(expectedPos, col));					
					while (!modelCell.isEmpty()) {
						word += modelCell.getContent();
						++expectedPos;
						modelCell = model.getBoard().getCell(Coordinate.fromRowCol(expectedPos, col));
					}
					
				} catch (WordgameException e) {
					e.printStackTrace();
				}
			}
		}
		
		return word;
	}
	
	public Coordinate getWordPosition() {
		Direction dir = getWordDirection();
		if (wordCells.size() == 0 || dir == null)
			return null;
		
		Cell modelCell = null;
		RCell origin = wordCells.get(0);
		int row = origin.getRow();
		int col = origin.getCol();
		
		if (dir == Direction.LINE) {
			try {
				modelCell = model.getBoard().getCell(Coordinate.fromRowCol(wordCells.get(0).getRow(), col - 1));
				while (!modelCell.isEmpty()) {
					--col;
					modelCell = model.getBoard().getCell(Coordinate.fromRowCol(wordCells.get(0).getRow(), col - 1));
				}
			} catch (WordgameException e1) {}				
			
		} else {
			try {
				modelCell = model.getBoard().getCell(Coordinate.fromRowCol(row - 1, wordCells.get(0).getCol()));
				while (!modelCell.isEmpty()) {
					--row;
					modelCell = model.getBoard().getCell(Coordinate.fromRowCol(row - 1, wordCells.get(0).getCol()));
				}
			} catch (WordgameException e1) {}	
		}
		
		return Coordinate.fromRowCol(row, col);
	}
}
