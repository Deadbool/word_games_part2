package wordgame.abstraction.decorators.scrabble;

import static org.fusesource.jansi.Ansi.ansi;

import wordgame.abstraction.decorators.CellDecorator;
import wordgame.abstraction.interfaces.Cell;

public class ScrabbleCellDecorator extends CellDecorator {

	public enum Color {
		LIGHT_BLUE, DARK_BLUE, PINK, RED, WHITE;
	}
	
	private Color color;
	private PointModifier modifier;
	
	public ScrabbleCellDecorator(Cell decoratedCell) {
		super(decoratedCell);
		this.color = Color.WHITE;
	}
	
	public void setModifier(PointModifier modifier, Color color) {
		this.modifier = modifier;
		this.color = color;
		this.decoratedCell.setContent((char)0x25a0);//'■');
	}
	
	public PointModifier getModifier() {
		return this.modifier;
	}
	
	public String toString() {
		String result;
		
		if(this.color == Color.LIGHT_BLUE) {
			result = ansi().fgBrightBlue() + super.toString() + ansi().reset();
		}
		else if(this.color == Color.DARK_BLUE) {
			result = ansi().fgBlue() + super.toString() + ansi().reset();
		}
		else if(this.color == Color.PINK) {
			result = ansi().fgMagenta() + super.toString() + ansi().reset();
		}
		else if(this.color == Color.RED) {
			result = ansi().fgRed() + super.toString() + ansi().reset();
		}
		else {
			result = super.toString();
		}
		
		return result;
	}

}
