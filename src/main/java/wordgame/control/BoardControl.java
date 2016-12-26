package wordgame.control;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import wordgame.presentation.RCell;

public class BoardControl implements Observer {
	
	public static BoardControl GET = new BoardControl();
	
	private ArrayList<RCell> wordCells;
	
	public BoardControl() {
		wordCells = new ArrayList<RCell>();
	}
	
	public ArrayList<RCell> getWordCells() {
		return wordCells;
	}
	
	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
			case NEW_TURN:
				wordCells.clear();
				break;
		}
	}
	
}
