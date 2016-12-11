package wordgame.decorators.topword;

import wordgame.decorators.CellDecorator;
import wordgame.interfaces.Cell;

public class TopwordCellDecorator extends CellDecorator {
	
	private int level = 0;

	public TopwordCellDecorator(Cell decoratedCell) {
		super(decoratedCell);
	}
	
	public void setContent(char content) {
		decoratedCell.setContent(content);
		level++;
	}
	
	public int getLevel() {
		return this.level;
	}

}
