package wordgame.control.wordgameFrame;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.decorators.topword.TopwordCellDecorator;
import wordgame.abstraction.interfaces.Cell;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.control.Event;
import wordgame.control.WindowManager;
import wordgame.presentation.GraphicalCharter;
import wordgame.presentation.components.RCell;
import wordgame.presentation.components.RTile;

public class RackControl implements Observer, MouseListener {

	private boolean overlay;
	
	private Wordgame model;
	
	private RTile tile;
	
	private JFrame frame;
	private JPanel board;
	
	public RackControl(RTile tile, Wordgame model, JPanel board, JFrame frame, boolean overlay) {
		this.tile = tile;
		this.model = model;
		this.frame = frame;
		this.board = board;
		this.overlay = overlay;
		fromModel();
	}

	public void update(Observable o, Object arg) {
		switch ((Event) arg) {
		case NEW_TURN:
			fromModel();
			//System.err.println("RackControl::update() receive NEW_TURN");
			break;
		}	
	}
	
	public void fromModel() {
		if (model.getCurrentPlayer().getRack().size() > tile.getIndex()) {
			tile.setContent(getLetterFromModel());
		} else {
			tile.setEmpty();
		}
	}
	
	private char getLetterFromModel() {
		return model.getCurrentPlayer().getRack().getContent().get(tile.getIndex()).charValue();
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		tile.setIcon(null);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cur = toolkit.createCustomCursor(GraphicalCharter.getCursor(""+getLetterFromModel()),
				new Point(1, 1), getLetterFromModel()+" cursor");
		
		frame.setCursor(cur);
	}

	public void mouseReleased(MouseEvent e) {
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		int cellX = e.getXOnScreen() - (frame.getX() + board.getX());
		int cellY = e.getYOnScreen() - (frame.getY() + board.getY()) - RCell.CELL_SIZE;
		
		try {
			RCell targetCell = (RCell) (board.findComponentAt(cellX, cellY));
			
			Cell modelCell = model.getBoard().getCell(Coordinate.fromRowCol(targetCell.getRow(), targetCell.getCol()));
			if (modelCell instanceof TopwordCellDecorator &&
					((TopwordCellDecorator)modelCell).getLevel() >= TopwordCellDecorator.MAX_LEVEL) {
				JOptionPane.showMessageDialog(frame,
						"Cette case contient déjà le nombre maximum de lettres autorisé. ("+TopwordCellDecorator.MAX_LEVEL+")");
				fromModel();
				return;
			}
			
			if (overlay && targetCell.getLetter() == this.getLetterFromModel()) {
				JOptionPane.showMessageDialog(frame,
						"Impossible de superposer deux lettres identiques.");
				fromModel();
				return;
			}
			
			if (targetCell != null &&
					(targetCell.isEmpty() || (overlay && !BoardControl.GET.getWordCells().contains(targetCell)))) {
				
				if ('_' == getLetterFromModel()) {
					WindowManager.JOKER_DIALOG.launch();
					targetCell.setContent(WindowManager.JOKER_DIALOG.getLetter());
				} else {
					targetCell.setContent(getLetterFromModel());
				}
				
				BoardControl.GET.addCell(targetCell);
			
			} else {
				fromModel();
			}
			
		} catch (Exception ex) {
			fromModel();
		}
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
}
