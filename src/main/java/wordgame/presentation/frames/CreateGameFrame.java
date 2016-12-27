package wordgame.presentation.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import wordgame.abstraction.common.BasicRack;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.interfaces.Rack;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.ChangeLettersTileControl;
import wordgame.control.WindowManager;
import wordgame.presentation.GraphicalCharter;
import wordgame.presentation.components.RButton;

public class CreateGameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private RButton add;
	private RButton delete;
	private JList<String> playersList;
	
	public CreateGameFrame() {
		this.setTitle("Nouvelle partie");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		mainPanel.add(createCenter(), BorderLayout.CENTER);
		mainPanel.add(createButtons(), BorderLayout.SOUTH);
	}
	
	public void launch() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel createCenter() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		centerPanel.add(createPlayersPanel(), BorderLayout.CENTER);
		centerPanel.add(createGameTypePanel(), BorderLayout.SOUTH);
		
		return centerPanel;
	}

	private JPanel createPlayersPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		TitledBorder listBorder = BorderFactory.createTitledBorder("Joueurs");
		listBorder.setTitleFont(new Font("Arial", Font.BOLD, 18));
		listBorder.setBorder(BorderFactory.createLoweredBevelBorder());
		panel.setBorder(listBorder);
		
		panel.add(createList(), BorderLayout.CENTER);
		panel.add(createListButtons(), BorderLayout.EAST);
		return panel;
	}
	
	private JPanel createGameTypePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		TitledBorder listBorder = BorderFactory.createTitledBorder("Type de jeu");
		listBorder.setTitleFont(new Font("Arial", Font.BOLD, 18));
		listBorder.setBorder(BorderFactory.createLoweredBevelBorder());
		panel.setBorder(listBorder);
		
		return panel;
	}
	
	private JPanel createList() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		panel.setPreferredSize(new Dimension(150, 125));
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		playersList = new JList<String>(listModel);
		panel.add(playersList);
		playersList.setFont(GraphicalCharter.BASIC_FONT);
		playersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playersList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				delete.setEnabled(!playersList.isSelectionEmpty());
			}
		});
		
		return panel;
	}
	
	private JPanel createListButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
				
		delete = new RButton("Supprimer");
		panel.add(delete);
		delete.setEnabled(false);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int i = playersList.getSelectedIndex();
					((DefaultListModel<String>)playersList.getModel()).remove(i);
					playersList.setSelectedIndex(i);
				} catch (Exception ex) {}
				
				add.setEnabled(playersList.getModel().getSize() < 4);
			}
		});
		
		panel.add(Box.createRigidArea(new Dimension(1, 20)));
		
		add = new RButton("Ajouter");
		panel.add(add);
		add.setMaximumSize(new Dimension(200, 30));
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManager.CREATE_PLAYER_DIALOG.launch();
				String name = WindowManager.CREATE_PLAYER_DIALOG.getPlayer();
				if (name != "") {
					((DefaultListModel<String>)playersList.getModel()).addElement(name);
					playersList.setSelectedIndex(playersList.getModel().getSize()-1);
					add.setEnabled(playersList.getModel().getSize() < 4);
				}	
			}
		});
		
		panel.add(Box.createVerticalGlue());
		
		return panel;
	}
	
	private JPanel createButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		panel.add(Box.createHorizontalGlue());
		
		RButton cancel = new RButton("Annuler");
		panel.add(cancel);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getDefaultCloseOperation() == EXIT_ON_CLOSE)
					System.exit(0);
				else
					setVisible(false);
			}
		});
		
		panel.add(Box.createRigidArea(new Dimension(30, 1)));
		
		RButton validate = new RButton("Commencer la partie");
		panel.add(validate);
		validate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		panel.add(Box.createHorizontalGlue());
		
		return panel;
	}
	
}
