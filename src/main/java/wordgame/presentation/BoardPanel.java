package wordgame.presentation;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int CELL_SIZE = 60;
	
	private int rows;
	private int cols;
	private JLabel cells[][];

	public BoardPanel(int rows, int cols) {
		setLayout(new GridLayout(rows, cols));
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		setBackground(GraphicalCharter.BACKGROUND);
		
		this.rows = rows;
		this.cols = cols;
		cells = new JLabel[rows][cols];
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j < cols; j++) {
				cells[i][j] = new JLabel();
				clearCell(i, j);
				add(cells[i][j]);
			}
		}
	}
	
	public void clearCell(int row, int col) {
		setCell(row, col, GraphicalCharter.EMPTY_CELL);
	}
	
	public void setCell(int row, int col, ImageIcon img) {
		cells[row][col].setIcon(GraphicalCharter.resizeImageIcon(img, CELL_SIZE, CELL_SIZE));
	}
	
}
