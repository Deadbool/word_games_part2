package wordgame.control.wordgameFrame;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.Event;

public class BagControl implements Observer {
	
	private JLabel count;
	
	public BagControl(JLabel count) {
		this.count = count;
	}

	public void update(Observable o, Object arg) {
		Wordgame model = (Wordgame) o;
		switch ((Event) arg) {
			case NEW_TURN:
				count.setText(""+model.getLetterBag().size());
				break;
		}
	}

}
