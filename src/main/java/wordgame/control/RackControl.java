package wordgame.control;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.GraphicalCharter;

public class RackControl implements Observer {
	
	private static final int TILE_SIZE = 50;

	private Wordgame model;
	private ArrayList<JLabel> tiles;
	
	public RackControl(ArrayList<JLabel> tiles, Wordgame model) {
		this.tiles = tiles;
		this.model = model;
		updateTiles();
	}

	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
		case NEW_TURN:
			updateTiles();
			System.err.println("RackControl::update() receive NEW_TURN");
			break;
		}	
	}
	
	public void updateTiles() {
		for (int i=0; i < tiles.size(); i++) {
			tiles.get(i).setIcon(GraphicalCharter.resizeImageIcon(GraphicalCharter.getTile(
					""+model.getCurrentPlayer().getRack().getContent().
					get(i).charValue()), TILE_SIZE, TILE_SIZE));
		}
	}
	
}
