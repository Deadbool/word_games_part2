package wordgame.control.changeLettersDialog;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import wordgame.presentation.dialogs.ChangeLettersDialog;

public class TileControl implements MouseListener {
	
	private ChangeLettersDialog dialog;
	private int index;
	
	public TileControl(int index, ChangeLettersDialog dialog) {
		this.index = index;
		this.dialog = dialog;
	}
	
	public void mouseClicked(MouseEvent e) {
		dialog.select(index, !dialog.isSelected(index));
	}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
}
