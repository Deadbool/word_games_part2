package wordgame.presentation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.PlayerListControl;

public class WordgameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Model
	private Wordgame model;

	public WordgameFrame(Wordgame game) {
		super("Wordgame");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.model = game;
		this.getContentPane().setLayout(new BorderLayout());
		
		this.createMenu();
		this.createLeft();
		
		this.pack();
	}
	
	public void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
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
	
	public void createLeft() {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList<String> playerList = new JList<String>(listModel);
		
		PlayerListControl listControl = new PlayerListControl(model, playerList);
		model.addObserver(listControl);
		
		this.add(playerList, BorderLayout.WEST);
	}
}
