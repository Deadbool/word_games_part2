package wordgame.presentation;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import wordgame.abstraction.decorators.scrabble.Multiplier;

public class RCell extends JLabel {
	private static final long serialVersionUID = 1L;
	
	public static final int CELL_SIZE = 40;

	private static final char EMPTY_LETTER = ' ';
	
	private char letter;
	
	public RCell () {
		setEmpty();
	}
	
	public void setContent(char let) {
		letter = let;
		updateIcon(GraphicalCharter.getCell(let+""));
	}
	
	public void setContent(Multiplier multi) {
		letter = EMPTY_LETTER;
		updateIcon(GraphicalCharter.getCell(multi.getType().toString()+"_COUNT_"+multi.getTimes()));
	}
	
	public void setEmpty() {
		letter = EMPTY_LETTER;
		updateIcon(GraphicalCharter.getCell("VIDE"));
	}
	
	public void setCentral() {
		letter = EMPTY_LETTER;
		updateIcon(GraphicalCharter.getCell("CENTRALE"));
	}
	
	private void updateIcon(ImageIcon img) {
		setIcon(GraphicalCharter.resizeImageIcon(img, CELL_SIZE, CELL_SIZE));
	}
	
	public void setFromRCell(RCell cell) {
		letter = cell.letter;
		updateIcon((ImageIcon) cell.getIcon()); 
	}
	
	public boolean isEmpty() {
		return letter == EMPTY_LETTER;
	}
	
	public char getLetter() {
		return letter;
	}
	
}
