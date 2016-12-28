package wordgame.control.wordgameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.interfaces.Direction;
import wordgame.abstraction.interfaces.Wordgame;

public class PlayButtonControl implements ActionListener {
	
	private JFrame frame;
	private Wordgame model;
	
	public PlayButtonControl(Wordgame model, JFrame frame) {
		this.model = model;
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent e) {
		BoardControl board = BoardControl.GET;
		Coordinate pos = board.getWordPosition();
		Direction dir = board.getWordDirection();
		String word = board.getWord();
		
		if (model.validMove(pos, dir, word)) {
			if (model.putWord(pos, dir, word)) {
				model.getCurrentPlayer().addPoint(model.getScoreForMove(pos, dir, word));
				model.fillCurrentPlayerRack();
				model.skipTurn();
			} else {
				JOptionPane.showMessageDialog(frame,
						"Plus ce place sur cette case.");
			}
		} else {
			JOptionPane.showMessageDialog(frame,
			    "Mot ou positionnement invalide.");
		}
	}
}
