package wordgame.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import wordgame.abstraction.common.BasicRack;
import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.interfaces.Direction;
import wordgame.abstraction.interfaces.Wordgame;

public class ChangeButtonControl implements ActionListener, Observer {
	
	private JFrame frame;
	private Wordgame model;
	private JButton button;
	
	public ChangeButtonControl(JButton button, Wordgame model, JFrame frame) {
		this.button = button;
		this.model = model;
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent e) {
		WindowManager.showChangeLettersDialog(model, frame);
	}

	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
			case NEW_TURN:
				button.setEnabled(model.getLetterBag().size() >= BasicRack.RACK_SIZE);
				break;
		}
	}
}
