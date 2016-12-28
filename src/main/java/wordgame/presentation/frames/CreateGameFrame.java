package wordgame.presentation.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import wordgame.abstraction.GameType;
import wordgame.control.WindowManager;
import wordgame.presentation.GraphicalCharter;
import wordgame.presentation.components.RButton;

public class CreateGameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private RButton add;
	private RButton delete;
	
	private JRadioButton scrabble;
	private JRadioButton topword;
	
	private RButton validate;
	
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
		delete.setEnabled(false);
		validate.setEnabled(false);
		
		((DefaultListModel<String>) playersList.getModel()).clear();
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void launch(GameType type) {
		switch (type) {
			case SCRABBLE:
				scrabble.setSelected(true);
				break;
			case TOPWORD:
				topword.setSelected(true);
				break;
		}
		
		launch();
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
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		TitledBorder listBorder = BorderFactory.createTitledBorder("Type de jeu");
		listBorder.setTitleFont(new Font("Arial", Font.BOLD, 18));
		listBorder.setBorder(BorderFactory.createLoweredBevelBorder());
		panel.setBorder(listBorder);
		
		ButtonGroup group = new ButtonGroup();
		
		scrabble = new JRadioButton("Scrabble");
		panel.add(scrabble);
		group.add(scrabble);
		scrabble.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		scrabble.setFont(GraphicalCharter.BASIC_FONT);
		scrabble.setFocusable(false);
		scrabble.setSelected(true);
		
		topword = new JRadioButton("Topword");
		panel.add(topword);
		group.add(topword);
		topword.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		topword.setFont(GraphicalCharter.BASIC_FONT);
		topword.setFocusable(false);
		
		return panel;
	}
	
	private JPanel createList() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		panel.setPreferredSize(new Dimension(150, 125));
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		listModel.addListDataListener(new ListDataListener() {
			public void intervalRemoved(ListDataEvent e) {
				validate.setEnabled(((DefaultListModel<String>)playersList.getModel()).size() >= 2);
			}
			public void intervalAdded(ListDataEvent e) {
				validate.setEnabled(((DefaultListModel<String>)playersList.getModel()).size() >= 2);
			}
			public void contentsChanged(ListDataEvent e) {}
		});
		
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
		
		validate = new RButton("Commencer la partie");
		panel.add(validate);
		validate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> players = new ArrayList<String>();
				for (int i=0; i < playersList.getModel().getSize(); i++) {
					players.add(playersList.getModel().getElementAt(i));
				}
				
				setVisible(false);
				
				if (scrabble.isSelected()) {
					WindowManager.WORDGAME_FRAME.launch(GameType.SCRABBLE, players);
				} else if (topword.isSelected()) {
					WindowManager.WORDGAME_FRAME.launch(GameType.TOPWORD, players);
				} else {
					JOptionPane.showMessageDialog(null,
							"Veuillez choisir un type de jeu.");
					setVisible(true);
				}
			}
		});
		
		panel.add(Box.createHorizontalGlue());
		
		return panel;
	}
	
}
