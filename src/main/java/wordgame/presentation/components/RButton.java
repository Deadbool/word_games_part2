package wordgame.presentation.components;

import javax.swing.JButton;

import wordgame.presentation.GraphicalCharter;

public class RButton extends JButton {
	private static final long serialVersionUID = 1L;

	public RButton(String text) {
		super(text);
		setBackground(GraphicalCharter.BACKGROUND);
		setFont(GraphicalCharter.BUTTON_FONT);
		setForeground(GraphicalCharter.TEXT);
		setFocusable(false);
	}
	
}
