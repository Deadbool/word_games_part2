package wordgame.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import wordgame.abstraction.common.BasicRack;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.BoardControl;
import wordgame.control.PlayerListControl;
import wordgame.control.RackControl;

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
		//setResizable(false);
		
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
		parent.add(rack, BorderLayout.SOUTH);
		rack.setLayout(new GridLayout(1, BasicRack.RACK_SIZE));
		rack.setBackground(GraphicalCharter.BACKGROUND);
		
		ArrayList<JLabel> tiles = new ArrayList<JLabel>();
		
		for (int i=0; i < BasicRack.RACK_SIZE; i++) {
			tiles.add(new JLabel());
			rack.add(tiles.get(i));
		}
		
		RackControl rackControl = new RackControl(tiles, model);
		model.addObserver(rackControl);
	}
	
	private void createRight() {
		JPanel right = new JPanel();
		getContentPane().add(right, BorderLayout.EAST);
		right.setLayout(new BorderLayout());
		right.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		createPlayerList(right);
		createBag(right);
	}

	private void createBag(JPanel parent) {
		JPanel panel = new JPanel();
		parent.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.add(Box.createHorizontalGlue());
		
		JLayeredPane layer = new JLayeredPane();
		panel.add(layer);
		layer.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		layer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		layer.setPreferredSize(new Dimension(120, 120));
		
		JLabel bag = new JLabel(GraphicalCharter.BAG);
		bag.setBounds(0, 0, 120, 120);
		bag.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel letterCount = new JLabel(""+model.getLetterBag().size());
		letterCount.setFont(GraphicalCharter.BAG_FONT);
		letterCount.setForeground(GraphicalCharter.TEXT);
		letterCount.setBounds(0, 58, 120, 62);
		letterCount.setHorizontalAlignment(SwingConstants.CENTER);
		letterCount.setVerticalAlignment(SwingConstants.TOP);
		
		layer.add(bag, new Integer(0), 0);
		layer.add(letterCount, new Integer(1), 0);
	}
	
	private void createPlayerList(JPanel parent) {
		JPanel panel = new JPanel();
		parent.add(panel, BorderLayout.NORTH);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList<String> playerList = new JList<String>(listModel);
		panel.add(playerList);
		playerList.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		playerList.setForeground(GraphicalCharter.REVERSE_TEXT);
		playerList.setFont(GraphicalCharter.BASIC_FONT);
		
		PlayerListControl listControl = new PlayerListControl(model, playerList);
		model.addObserver(listControl);
		
	}
}
