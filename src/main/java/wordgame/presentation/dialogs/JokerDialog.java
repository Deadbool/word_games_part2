package wordgame.presentation.dialogs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import wordgame.presentation.GraphicalCharter;
import wordgame.presentation.components.RTile;

public class JokerDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
	private char letter;
	private boolean running;
	
	public JokerDialog() {
		this.setTitle("Joker");
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		mainPanel.add(createCenter(), BorderLayout.CENTER);
	}
	
	public void launch() {
		running = true;
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		while (running) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private JPanel createCenter() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 6, 10, 10));
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
								
		for (int i=0; i < ALPHABET.length(); i++) {
			RTile tile = new RTile(i);
			tile.setContent(ALPHABET.charAt(i));
			panel.add(tile);
			tile.setHorizontalAlignment(SwingConstants.CENTER);
			
			tile.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				
				public void mouseClicked(MouseEvent e) {
					letter = ALPHABET.charAt(((RTile)e.getSource()).getIndex());
					running = false;
					setVisible(false);
				}
			});
		}
				
		return panel;
	}
	
	public char getLetter() {
		return letter;
	}
	
}
