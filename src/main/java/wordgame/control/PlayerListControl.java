package wordgame.control;

import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;

import wordgame.abstraction.interfaces.Player;
import wordgame.abstraction.interfaces.Wordgame;

public class PlayerListControl implements Observer {
	
	private Wordgame model;
	private JList<String> list;
	
	public PlayerListControl(Wordgame model, JList<String> list) {
		this.model = model;
		this.list = list;
		list.setSelectionModel(new NoSelectionModel());
		updateScores();
	}

	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
		case NEW_TURN:
			//System.err.println("PlayerListControl::update() receive NEW_TURN");
			updateScores();
			break;
		}
	}
	
	private void updateScores() {
		DefaultListModel<String> listModel = (DefaultListModel<String>) this.list.getModel();
		listModel.clear();
		
		for (Player player : model.getPlayers()) {
			listModel.addElement((model.getCurrentPlayer().equals(player)?"> ":"")
					+ player.getNickname() + " - " + player.getScore());
		}
	}
	
	private static class NoSelectionModel extends DefaultListSelectionModel {
		private static final long serialVersionUID = 1L;
		public void setAnchorSelectionIndex(final int anchorIndex) {}
		public void setLeadAnchorNotificationEnabled(final boolean flag) {}
		public void setLeadSelectionIndex(final int leadIndex) {}
		public void setSelectionInterval(final int index0, final int index1) { }
	}
}
