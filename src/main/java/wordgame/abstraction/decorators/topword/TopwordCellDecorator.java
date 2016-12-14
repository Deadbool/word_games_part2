package wordgame.abstraction.decorators.topword;

import wordgame.abstraction.decorators.CellDecorator;
import wordgame.abstraction.interfaces.Cell;

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
