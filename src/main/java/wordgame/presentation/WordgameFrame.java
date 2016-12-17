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

import wordgame.abstraction.common.BasicRack;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.BoardControl;
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
		setLocationRelativeTo(null);
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mFile = new JMenu("Fichier");
		menuBar.add(mFile);
		
		JMenu miNew = new JMenu("Nouvelle partie");
		mFile.add(miNew);
		
		JMenuItem miNewScrabble = new JMenuItem("Scrabble");
		miNew.add(miNewScrabble);
		JMenuItem miNewTopword = new JMenuItem("Topword");
		miNew.add(miNewTopword);
		
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
		
		createBoard(center);
		
		createRack(center);
	}

	private void createBoard(JPanel parent) {
		int rows = model.getBoard().getHeight();
		int cols = model.getBoard().getWidth();
		JPanel board = new JPanel(new GridLayout(rows, cols));
		board.setBackground(GraphicalCharter.BACKGROUND);
		
		JLabel cells[][] = new JLabel[rows][cols];
		
		for (int r=0; r < cells.length; r++) {
			for (int c=0; c < cells[r].length; c++) {
				cells[r][c] = new JLabel();
				board.add(cells[r][c]);
			}
		}
		
		BoardControl boardControl = new BoardControl(cells, model);
		model.addObserver(boardControl);
		parent.add(board, BorderLayout.CENTER);
		board.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
	}

	private void createRack(JPanel parent) {
		JPanel rack = new JPanel();
		rack.setLayout(new GridLayout(1, BasicRack.RACK_SIZE));
		rack.setBackground(GraphicalCharter.BACKGROUND);
		
		ArrayList<JLabel> tiles = new ArrayList<JLabel>();
		
		for (int i=0; i < BasicRack.RACK_SIZE; i++) {
			tiles.add(new JLabel(GraphicalCharter.resizeImageIcon(GraphicalCharter.getTile("espace"), 50, 50)));
			rack.add(tiles.get(i));
		}
				
		parent.add(rack, BorderLayout.SOUTH);
	}
	
	private void createRight() {
		JPanel bot = new JPanel();
		getContentPane().add(bot, BorderLayout.EAST);
		bot.setLayout(new BoxLayout(bot, BoxLayout.Y_AXIS));
		bot.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		createPlayerList(bot);
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
