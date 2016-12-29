package wordgame.control.wordgameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import wordgame.abstraction.common.BasicRack;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.Event;
import wordgame.control.WindowManager;

public class ChangeButtonControl implements ActionListener, Observer {
	
	private Wordgame model;
	private JButton button;
	
	public ChangeButtonControl(JButton button, Wordgame model) {
		this.button = button;
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent e) {
		WindowManager.CHANGE_LETTERS_DIALOG.launch(model);
	}

	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
			case NEW_TURN:
				button.setEnabled(model.getLetterBag().size() >= BasicRack.RACK_SIZE);
				break;
		}
	}
}
