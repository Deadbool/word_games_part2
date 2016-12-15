package wordgame.presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

import wordgame.abstraction.common.BasicRack;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.PlayerListControl;

public class WordgameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Model
	private Wordgame model;
	
	public WordgameFrame(Wordgame game) {
		super("Wordgame");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.model = game;
		
		JPanel mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		//this.setPreferredSize(new Dimension(800, 600));
		setResizable(false);
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(GraphicalCharter.BACKGROUND);
		
		createMenu();
		createCenter();
		createRight();
		
		pack();
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mFile = new JMenu("Fichier");
		menuBar.add(mFile);
		
		JMenuItem miNew = new JMenuItem("Nouvelle partie");
		mFile.add(miNew);		
		
		JMenuItem miLoad = new JMenuItem("Charger une partie...");
		mFile.addSeparator();
		mFile.add(miLoad);
		
		JMenuItem miSave = new JMenuItem("Sauvegarder la partie en cours...");
		mFile.add(miSave);
				
		JMenuItem miQuit = new JMenuItem("Quitter");
		miQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);		
			}
		});
		mFile.addSeparator();
		mFile.add(miQuit);
	}

	private void createCenter() {
		JPanel center = new JPanel(new BorderLayout());
		getContentPane().add(center, BorderLayout.CENTER);
		center.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		center.setBackground(GraphicalCharter.BACKGROUND);
		
		BoardPanel board = new BoardPanel(model.getBoard().getWidth(), model.getBoard().getHeight());
		center.add(board, BorderLayout.CENTER);
		board.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		createRack(center);
	}

	private void createRack(JPanel parent) {
		JPanel rack = new JPanel();
		rack.setLayout(new GridLayout(1, BasicRack.RACK_SIZE));
		
		ArrayList<JLabel> tiles = new ArrayList<JLabel>();
		
		for (int i=0; i < BasicRack.RACK_SIZE; i++) {
			tiles.add(new JLabel(GraphicalCharter.getDraggedTile("_")));
			rack.add(tiles.get(i));
		}
				
		parent.add(rack, BorderLayout.SOUTH);
	}
	
	private void createRight() {
		JPanel bot = new JPanel();
		bot.setLayout(new BoxLayout(bot, BoxLayout.Y_AXIS));
		bot.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		createPlayerList(bot);
		
		getContentPane().add(bot, BorderLayout.EAST);
	}
	
	private void createPlayerList(JPanel parent) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList<String> playerList = new JList<String>(listModel);
		playerList.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		playerList.setForeground(GraphicalCharter.REVERSE_TEXT);
		playerList.setFont(GraphicalCharter.BASIC_FONT);
		
		PlayerListControl listControl = new PlayerListControl(model, playerList);
		model.addObserver(listControl);
		
		panel.add(playerList);
		parent.add(panel);
	}
}
