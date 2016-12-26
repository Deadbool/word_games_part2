package wordgame.control;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.GraphicalCharter;
import wordgame.presentation.RCell;

public class RackControl implements Observer, MouseListener {
	
	public static final int TILE_SIZE = 50;

	private Wordgame model;
	private int index;
	private JLabel tile;
	
	private JFrame frame;
	private JPanel board;
	
	public RackControl(JLabel tile, int index, Wordgame model, JPanel board, JFrame frame) {
		this.tile = tile;
		this.index = index;
		this.model = model;
		this.frame = frame;
		this.board = board;
		updateTile();
	}

	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
		case NEW_TURN:
			updateTile();
			//System.err.println("RackControl::update() receive NEW_TURN");
			break;
		}	
	}
	
	public void updateTile() {
		tile.setIcon(GraphicalCharter.resizeImageIcon(GraphicalCharter.getTile(
			""+getLetter()), TILE_SIZE, TILE_SIZE));
	}
	
	public char getLetter() {
		return model.getCurrentPlayer().getRack().getContent().get(index).charValue();
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		tile.setIcon(null);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cur = toolkit.createCustomCursor(GraphicalCharter.getCursor(""+getLetter()),
				new Point(1, 1), getLetter()+" cursor");
		
		frame.setCursor(cur);
	}

	public void mouseReleased(MouseEvent e) {
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		int cellX = e.getXOnScreen() - (frame.getX() + board.getX());
		int cellY = e.getYOnScreen() - (frame.getY() + board.getY()) - RCell.CELL_SIZE;
		
		try {
			RCell targetCell = (RCell) (board.findComponentAt(cellX, cellY));
			
			if (targetCell.isEmpty()) {
				targetCell.setContent(getLetter());
			} else {
				updateTile();
			}
			
		} catch (Exception ex) {
			updateTile();
		}
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
}
