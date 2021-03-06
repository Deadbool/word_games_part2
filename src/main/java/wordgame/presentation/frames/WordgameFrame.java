package wordgame.presentation.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import wordgame.GameTUI;
import wordgame.abstraction.GameType;
import wordgame.abstraction.common.BasicRack;
import wordgame.abstraction.decorators.scrabble.ScrabbleDecorator;
import wordgame.abstraction.decorators.topword.TopwordDecorator;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.WindowManager;
import wordgame.control.wordgameFrame.BagControl;
import wordgame.control.wordgameFrame.BoardControl;
import wordgame.control.wordgameFrame.CellControl;
import wordgame.control.wordgameFrame.ChangeButtonControl;
import wordgame.control.wordgameFrame.PlayButtonControl;
import wordgame.control.wordgameFrame.PlayerListControl;
import wordgame.control.wordgameFrame.RackControl;
import wordgame.presentation.GraphicalCharter;
import wordgame.presentation.components.RCell;
import wordgame.presentation.components.RTile;

public class WordgameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GameType gameType;
	
	// Model
	private Wordgame model;
	private JPanel boardPanel;
	
	public WordgameFrame() {
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void launch(GameType gameType, List<String> players) {
		this.gameType = gameType;
		
		switch (gameType) {
			case SCRABBLE:
				model = ScrabbleDecorator.FACTORY.gameFactory();
				model.init(GameTUI.class.getResource("/scrabble.wg").getPath(), ScrabbleDecorator.FACTORY);
				setTitle("Scrabble");
				break;
				
			case TOPWORD:
				model = TopwordDecorator.FACTORY.gameFactory();
				model.init(GameTUI.class.getResource("/topword.wg").getPath(), TopwordDecorator.FACTORY);
				setTitle("Topword");
				break;
				
			default:
				return;		
		}
		
		for (String player : players) {
			model.addPlayer(player);
		}
				
		JPanel mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(GraphicalCharter.BACKGROUND);
		
		createMenu();
		getContentPane().add(createCenter(), BorderLayout.CENTER);
		getContentPane().add(createRight(), BorderLayout.EAST);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
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
		miNewScrabble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManager.CREATE_GAME_FRAME.launch(GameType.SCRABBLE);
				setVisible(false);
			}
		});
		
		JMenuItem miNewTopword = new JMenuItem("Topword");
		miNew.add(miNewTopword);
		miNewTopword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowManager.CREATE_GAME_FRAME.launch(GameType.TOPWORD);
				setVisible(false);
			}
		});
		
		JMenuItem miLoad = new JMenuItem("Charger une partie...");
		mFile.addSeparator();
		mFile.add(miLoad);
		miLoad.setEnabled(false);
		
		JMenuItem miSave = new JMenuItem("Sauvegarder la partie en cours...");
		mFile.add(miSave);
		miSave.setEnabled(false);
				
		JMenuItem miQuit = new JMenuItem("Quitter");
		miQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);		
			}
		});
		mFile.addSeparator();
		mFile.add(miQuit);
	}

	private JPanel createCenter() {
		JPanel center = new JPanel(new BorderLayout());
		center.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		center.setBackground(GraphicalCharter.BACKGROUND);
		
		center.add(createBoard(), BorderLayout.CENTER);
		center.add(createRack(), BorderLayout.SOUTH);
		
		return center;
	}

	private JPanel createBoard() {
		int rows = model.getBoard().getHeight();
		int cols = model.getBoard().getWidth();
		
		GridLayout boardLayout = new GridLayout(rows, cols);
		JPanel board = new JPanel(boardLayout);
		board.setBackground(GraphicalCharter.BACKGROUND);
		this.boardPanel = board;
		
		for (int r=0; r < model.getBoard().getHeight(); r++) {
			for (int c=0; c < model.getBoard().getWidth(); c++) {
				RCell cell = new RCell(r, c);
				board.add(cell);
				
				CellControl cellControl = new CellControl(cell, model, boardPanel, this, gameType == GameType.TOPWORD);
				model.addObserver(cellControl);
				cell.addMouseListener(cellControl);
			}
		}	
		
		board.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		BoardControl.GET.setModel(model);
		model.addObserver(BoardControl.GET);
		
		return board;
	}

	private JPanel createRack() {
		JPanel rack = new JPanel();
		rack.setLayout(new GridLayout(1, BasicRack.RACK_SIZE + 1));
		rack.setBackground(GraphicalCharter.BACKGROUND);
						
		for (int i=0; i < BasicRack.RACK_SIZE; i++) {
			RTile tile = new RTile(i);
			rack.add(tile);
			tile.setHorizontalAlignment(SwingConstants.CENTER);
			
			RackControl rackControl = new RackControl(tile, model, this.boardPanel, this, gameType == GameType.TOPWORD);
			model.addObserver(rackControl);
			
			tile.addMouseListener(rackControl);
		}
		
		JButton resetRack = new JButton(GraphicalCharter.resizeImageIcon(GraphicalCharter.RELOAD,
				RTile.TILE_SIZE, RTile.TILE_SIZE));
		resetRack.setBorder(null);
		resetRack.setBackground(GraphicalCharter.BACKGROUND);
		resetRack.setFocusable(false);
		resetRack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.newTurn();				
			}
		});
		
		rack.add(resetRack);
		
		return rack;
	}
	
	private JPanel createRight() {
		JPanel right = new JPanel();
		right.setLayout(new BorderLayout());
		right.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		right.setPreferredSize(new Dimension(230, 500));
		
		right.add(createPlayerList(), BorderLayout.NORTH);
		right.add(createButtons(), BorderLayout.CENTER);
		right.add(createBag(), BorderLayout.SOUTH);
		
		return right;
	}
	
	private JPanel createPlayerList() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList<String> playerList = new JList<String>(listModel);
		panel.add(playerList);
		playerList.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		playerList.setForeground(GraphicalCharter.REVERSE_TEXT);
		playerList.setFont(GraphicalCharter.getBasicFont(24));
		
		PlayerListControl listControl = new PlayerListControl(model, playerList);
		model.addObserver(listControl);
		
		return panel;
	}
	
	private JPanel createButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(GraphicalCharter.REVERSE_BACKGROUND);
		
		panel.add(Box.createVerticalGlue());
		JButton pass = new JButton(GraphicalCharter.BUTTON_PASS);
		panel.add(pass);
		panel.add(Box.createVerticalGlue());
		pass.setBorder(null);
		pass.setAlignmentX(CENTER_ALIGNMENT);
		pass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.skipTurn();
			}
		});
		
		JButton change = new JButton(GraphicalCharter.BUTTON_CHANGE);
		panel.add(change);
		panel.add(Box.createVerticalGlue());
		change.setBorder(null);
		change.setAlignmentX(CENTER_ALIGNMENT);
		change.addActionListener(new ChangeButtonControl(change, model));
		
		JButton play = new JButton(GraphicalCharter.BUTTON_PLAY);
		panel.add(play);
		panel.add(Box.createVerticalGlue());
		play.setBorder(null);
		play.setAlignmentX(CENTER_ALIGNMENT);
		play.addActionListener(new PlayButtonControl(model, this));
		
		return panel;
	}

	private JPanel createBag() {
		JPanel panel = new JPanel();
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
		model.addObserver(new BagControl(letterCount));
		
		layer.add(bag, new Integer(0), 0);
		layer.add(letterCount, new Integer(1), 0);
		
		return panel;
	}
}
