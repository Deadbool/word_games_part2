package wordgame.control;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.GraphicalCharter;

public class RackControl implements Observer, MouseListener {
	
	private static final int TILE_SIZE = 50;

	private Wordgame model;
	private int index;
	private JLabel tile;
	
	
	public RackControl(JLabel tile, int index, Wordgame model) {
		this.tile = tile;
		this.index = index;
		this.model = model;
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
				new Point(12, 12), getLetter()+" cursor");
		
		tile.setCursor(cur);
	}

	public void mouseReleased(MouseEvent e) {
		updateTile();
		tile.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
}
