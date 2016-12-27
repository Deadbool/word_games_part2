package wordgame.presentation.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import wordgame.abstraction.common.BasicRack;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.interfaces.Rack;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.ChangeLettersTileControl;
import wordgame.presentation.GraphicalCharter;
import wordgame.presentation.components.RButton;
import wordgame.presentation.components.RTile;

public class CreatePlayerDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JTextField input;
	
	private boolean running;
	private String player;
	
	public CreatePlayerDialog() {
		this.setTitle("Nouveau joueur");
		this.setModal(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		mainPanel.add(createCenter(), BorderLayout.CENTER);
		mainPanel.add(createButtons(), BorderLayout.SOUTH);
	}
	
	public void launch() {
		player = "";
		running = true;
		input.setText("");
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		while (running) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private JPanel createCenter() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		JLabel label = new JLabel("Nom du joueur: ");
		panel.add(label);
		label.setFont(new Font("Arial", Font.PLAIN, 18));
		
		input = new JTextField();
		panel.add(input);
		input.setFont(new Font("Arial", Font.PLAIN, 16));
		input.setPreferredSize(new Dimension(150, 30));
				
		return panel;
	}
	
	private JPanel createButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		panel.add(Box.createHorizontalGlue());
		
		RButton cancel = new RButton("Annuler");
		panel.add(cancel);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player = "";
				setVisible(false);
				running = false;
			}
		});
		
		panel.add(Box.createRigidArea(new Dimension(30, 1)));
		
		RButton validate = new RButton("Valider");
		panel.add(validate);
		validate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player = input.getText();
				setVisible(false);
				running = false;
			}
		});
		
		panel.add(Box.createHorizontalGlue());
		
		return panel;
	}
	
	public String getPlayer() {
		return player;
	}
	
}
