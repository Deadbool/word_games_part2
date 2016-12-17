package wordgame.control;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.decorators.CellDecorator;
import wordgame.abstraction.decorators.scrabble.Multiplier;
import wordgame.abstraction.decorators.scrabble.ScrabbleCellDecorator;
import wordgame.abstraction.decorators.scrabble.ScrabbleDecorator;
import wordgame.abstraction.decorators.scrabble.Multiplier.MultiplierType;
import wordgame.abstraction.interfaces.Cell;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.GraphicalCharter;

public class BoardControl implements Observer {
	private static final int CELL_SIZE = 40;
	
	private JLabel cells[][];
	private Wordgame model;

	public BoardControl(JLabel cells[][], Wordgame model) {
		this.model = model;
		this.cells = cells;
		
		updateCells();
	}
	
	private void setCell(int row, int col, ImageIcon img) {
		cells[row][col].setIcon(GraphicalCharter.resizeImageIcon(img, CELL_SIZE, CELL_SIZE));
	}
	
	private void updateCells() {
		for (int r=0; r < cells.length; r++) {
			for (int c=0; c < cells[r].length; c++) {
				
				try {
					ImageIcon img = null;
					Cell cell = model.getBoard().getCell(Coordinate.fromRowCol(r, c));
					String imgName;
					
					if (cell.isEmpty()) {
						if (model instanceof ScrabbleDecorator && r==cells.length/2 && c==cells.length/2) {
							img = GraphicalCharter.getCell("CENTRALE");
						} else {
							if (cell instanceof ScrabbleCellDecorator &&
									((ScrabbleCellDecorator)cell).getModifier() != null) {
								Multiplier multi = (Multiplier)((ScrabbleCellDecorator)cell).getModifier();
								img = GraphicalCharter.getCell(multi.getType().toString()+"_COUNT_"+multi.getTimes());
								
								
							} else {
								img = GraphicalCharter.getCell("VIDE");
							}
						}
					} else {
						
					}
					
					setCell(r, c, img);
					
				} catch (WordgameException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
			case NEW_TURN:
				updateCells();
				break;
		}
	}
	
}
