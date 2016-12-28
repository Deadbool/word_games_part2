package wordgame.presentation.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import wordgame.presentation.GraphicalCharter;

public class RTile extends JLabel {
	private static final long serialVersionUID = 1L;
	
	public static final int TILE_SIZE = 50;
	
	private int index;
	
	public RTile(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setContent(char letter) {
		setTileIcon(GraphicalCharter.getTile(""+letter));
	}
	
	public void setEmpty() {
		setTileIcon(null);
	}
	
	private void setTileIcon(ImageIcon icon) {
		setIcon(GraphicalCharter.resizeImageIcon(icon, TILE_SIZE, TILE_SIZE));
	}
}
