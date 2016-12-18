package wordgame.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.decorators.scrabble.Multiplier;
import wordgame.abstraction.decorators.scrabble.ScrabbleCellDecorator;
import wordgame.abstraction.decorators.scrabble.ScrabbleDecorator;
import wordgame.abstraction.interfaces.Cell;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.GraphicalCharter;

public class CellControl implements Observer, MouseListener {
	private static final int CELL_SIZE = 40;
	
	private int row;
	private int col;
	private JLabel cell;
	private Wordgame model;

	public CellControl(JLabel cell, int row, int col, Wordgame model) {
		this.model = model;
		this.cell = cell;
		this.row = row;
		this.col = col;
		
		updateCell();
	}
	
	private void setCell(ImageIcon img) {
		cell.setIcon(GraphicalCharter.resizeImageIcon(img, CELL_SIZE, CELL_SIZE));
	}
	
	private void updateCell() {
		try {
			ImageIcon img = null;
			Cell boardCell = model.getBoard().getCell(Coordinate.fromRowCol(row, col));
			
			if (boardCell.isEmpty()) {
				if (model instanceof ScrabbleDecorator && row==7 && col==7) {
					img = GraphicalCharter.getCell("CENTRALE");
				} else {
					if (boardCell instanceof ScrabbleCellDecorator &&
							((ScrabbleCellDecorator)boardCell).getModifier() != null) {
						Multiplier multi = (Multiplier)((ScrabbleCellDecorator)boardCell).getModifier();
						img = GraphicalCharter.getCell(multi.getType().toString()+"_COUNT_"+multi.getTimes());
						
						
					} else {
						img = GraphicalCharter.getCell("VIDE");
					}
				}
			} else {
				img = GraphicalCharter.getCell(""+boardCell.getContent());
			}
			
			setCell(img);
			
		} catch (WordgameException e) {
			e.printStackTrace();
		}
	}

	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
			case NEW_TURN:
				updateCell();
				//System.err.println("BoardControl::update() receive NEW_TURN");
				break;
		}
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
}
