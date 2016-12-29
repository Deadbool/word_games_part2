package wordgame.control.wordgameFrame;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import wordgame.abstraction.interfaces.Cell;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.Event;
import wordgame.control.WindowManager;
import wordgame.presentation.GraphicalCharter;
import wordgame.presentation.components.RCell;
import wordgame.presentation.components.RTile;

public class RackControl implements Observer, MouseListener {

	private Wordgame model;
	
	private RTile tile;
	
	private JFrame frame;
	private JPanel board;
	
	public RackControl(RTile tile, Wordgame model, JPanel board, JFrame frame) {
		this.tile = tile;
		this.model = model;
		this.frame = frame;
		this.board = board;
		fromModel();
	}

	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
		case NEW_TURN:
			fromModel();
			//System.err.println("RackControl::update() receive NEW_TURN");
			break;
		}	
	}
	
	public void fromModel() {
		if (model.getCurrentPlayer().getRack().size() > tile.getIndex()) {
			tile.setContent(getLetterFromModel());
		} else {
			tile.setEmpty();
		}
	}
	
	private char getLetterFromModel() {
		return model.getCurrentPlayer().getRack().getContent().get(tile.getIndex()).charValue();
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		tile.setIcon(null);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cur = toolkit.createCustomCursor(GraphicalCharter.getCursor(""+getLetterFromModel()),
				new Point(1, 1), getLetterFromModel()+" cursor");
		
		frame.setCursor(cur);
	}

	public void mouseReleased(MouseEvent e) {
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		int cellX = e.getXOnScreen() - (frame.getX() + board.getX());
		int cellY = e.getYOnScreen() - (frame.getY() + board.getY()) - RCell.CELL_SIZE;
		
		try {
			RCell targetCell = (RCell) (board.findComponentAt(cellX, cellY));
			
			if (targetCell.isEmpty()) {
				
				if ('_' == getLetterFromModel()) {
					WindowManager.JOKER_DIALOG.launch();
					targetCell.setContent(WindowManager.JOKER_DIALOG.getLetter());
				} else {
					targetCell.setContent(getLetterFromModel());
				}
				
				BoardControl.GET.addCell(targetCell);
			
			} else {
				fromModel();
			}
			
		} catch (Exception ex) {
			fromModel();
		}
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
}
