package wordgame.control;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.decorators.scrabble.Multiplier;
import wordgame.abstraction.decorators.scrabble.ScrabbleCellDecorator;
import wordgame.abstraction.decorators.scrabble.ScrabbleDecorator;
import wordgame.abstraction.interfaces.Cell;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.GraphicalCharter;
import wordgame.presentation.RCell;

public class CellControl implements Observer, MouseListener {
	
	private RCell cell;
	private Wordgame model;
	
	private JFrame frame;
	private JPanel board;
	
	private boolean dragging;

	public CellControl(RCell cell, Wordgame model, JPanel board, JFrame frame) {
		this.model = model;
		this.cell = cell;
		this.frame = frame;
		this.board = board;
		
		dragging = false;
		
		fromModel();
	}
	
	private void fromModel() {
		try {
			Cell modelCell = model.getBoard().getCell(Coordinate.fromRowCol(cell.getRow(), cell.getCol()));
			
			if (modelCell.isEmpty()) {
				if (model instanceof ScrabbleDecorator && cell.getRow()==7 && cell.getCol()==7) {
					cell.setCentral();
				} else {
					if (modelCell instanceof ScrabbleCellDecorator &&
							((ScrabbleCellDecorator)modelCell).getModifier() != null) {
						Multiplier multi = (Multiplier)((ScrabbleCellDecorator)modelCell).getModifier();
						cell.setContent(multi);
					} else {
						cell.setEmpty();
					}
				}
				
			} else {
				cell.setContent(modelCell.getContent());
			}
			
		} catch (WordgameException e) {
			e.printStackTrace();
			cell.setEmpty();
		}
	}

	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
			case NEW_TURN:
				fromModel();
				//System.err.println("BoardControl::update() receive NEW_TURN");
				break;
		}
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		if (!cell.isEmpty()) {			
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Cursor cur = toolkit.createCustomCursor(GraphicalCharter.getCursor(cell.getLetter()+""),
					new Point(1, 1), cell.getLetter() +" cursor");
			
			frame.setCursor(cur);
			
			dragging = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (dragging) {
			frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			dragging = false;
			
			int cellX = e.getXOnScreen() - (frame.getX() + board.getX());
			int cellY = e.getYOnScreen() - (frame.getY() + board.getY()) - RCell.CELL_SIZE;
			
			try {
				RCell targetCell = (RCell) (board.findComponentAt(cellX, cellY));
				
				if (targetCell.isEmpty()) {
					BoardControl.GET.removeCell(cell);
					
					targetCell.setFromRCell(cell);
					fromModel();
					
					BoardControl.GET.addCell(targetCell);
				}
				
			} catch (Exception ex) {}
		}
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
}
