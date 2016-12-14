package wordgame.abstraction.decorators;

import wordgame.abstraction.interfaces.Cell;

public abstract class CellDecorator implements Cell {
	
	protected Cell decoratedCell;

	public CellDecorator(Cell decoratedCell) {
		super();
		this.decoratedCell = decoratedCell;
	}

	public char getContent() {
		return decoratedCell.getContent();
	}

	public void setContent(char content) {
		decoratedCell.setContent(content);
	}
	
	public boolean isEmpty() {
		return decoratedCell.isEmpty();
	}
	
	public String toString() {
		return decoratedCell.toString();
	}

}
