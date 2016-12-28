package wordgame.presentation.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import wordgame.abstraction.common.BasicRack;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.interfaces.Rack;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.changeLettersDialog.TileControl;
import wordgame.presentation.GraphicalCharter;
import wordgame.presentation.components.RButton;
import wordgame.presentation.components.RTile;

public class ChangeLettersDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private Wordgame model;
	private RButton validate;
	private ArrayList<RTile> tiles;
	private int selectedCount;
	private ArrayList<Boolean> selected;
	
	public ChangeLettersDialog() {
		this.setTitle("Echanger des lettres");
		this.setModal(true);
		this.setResizable(false);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		mainPanel.add(createRack(), BorderLayout.CENTER);
		mainPanel.add(createButtons(), BorderLayout.SOUTH);
	}
	
	public void launch(Wordgame model) {
		this.model = model;
		
		for (int i=0; i < tiles.size(); i++) {
			tiles.get(i).setContent(model.getCurrentPlayer().getRack().getContent().get(i));
			select(i, false);
		}
		
		selectedCount = 0;
		validate.setText("Echanger " + selectedCount + " lettre" + (selectedCount==1?"":"s"));
		validate.setEnabled(false);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void select(int i, boolean select) {
		if (!select) {
			selected.set(i, false);
			tiles.get(i).setBorder(null);
			selectedCount -= (selectedCount > 0) ? 1 : 0;
		} else {
			selected.set(i, true);
			tiles.get(i).setBorder(BorderFactory.createLineBorder(Color.RED));
			++selectedCount;
		}
		
		validate.setText("Echanger " + selectedCount + " lettre" + (selectedCount==1?"":"s"));
		validate.setEnabled(selectedCount > 0);
	}
	
	public boolean isSelected(int i) {
		return selected.get(i);
	}
	
	private JPanel createRack() {
		JPanel rack = new JPanel();
		rack.setLayout(new GridLayout(1, BasicRack.RACK_SIZE, 10, 0));
		rack.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		tiles = new ArrayList<RTile>();
		selected = new ArrayList<Boolean>();
						
		for (int i=0; i < BasicRack.RACK_SIZE; i++) {
			RTile tile = new RTile(i);
			tiles.add(tile);
			selected.add(false);
			rack.add(tile);
			tile.setHorizontalAlignment(SwingConstants.CENTER);
			
			tile.addMouseListener(new TileControl(i, this));
		}
				
		return rack;
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
				setVisible(false);
			}
		});
		
		panel.add(Box.createRigidArea(new Dimension(30, 1)));
		
		validate = new RButton("Echanger 0 lettres");
		panel.add(validate);
		validate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=tiles.size()-1; i >= 0; i--) {
					if (!selected.get(i)) {
						continue;
					}
					
					Rack rack = model.getCurrentPlayer().getRack();
					char old = rack.getContent().get(i);
					
					try {
						rack.getContent().remove(i);
						rack.addLetter(model.getLetterBag().exchange(old));
												
					} catch (WordgameException ex) {
						ex.printStackTrace();
					}
				}
				
				model.skipTurn();
				setVisible(false);
			}
		});
		
		panel.add(Box.createHorizontalGlue());
		
		return panel;
	}
	
}
