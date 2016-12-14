package wordgame.abstraction.decorators.topword;

import wordgame.abstraction.common.BasicCell;
import wordgame.abstraction.common.BasicWordgame;
import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.common.SquareBoard;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.decorators.WordgameDecorator;
import wordgame.abstraction.decorators.scrabble.Multiplier;
import wordgame.abstraction.decorators.scrabble.ScrabbleCellDecorator;
import wordgame.abstraction.decorators.scrabble.ScrabbleDecorator;
import wordgame.abstraction.decorators.scrabble.Multiplier.MultiplierType;
import wordgame.abstraction.interfaces.Board;
import wordgame.abstraction.interfaces.Cell;
import wordgame.abstraction.interfaces.Direction;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.abstraction.interfaces.WordgameFactory;

public class TopwordDecorator extends WordgameDecorator {

	// ATTRIBUTES & CONSTRUCTOR
	
	private boolean firstMoveDone = false;

	public TopwordDecorator(Wordgame decoratedWordgame) {
		super(decoratedWordgame);
	}
	
	// FACTORIES
	
	public static WordgameFactory FACTORY = new TopwordDecorator(new BasicWordgame());

	public Wordgame gameFactory() {
		return new TopwordDecorator(new BasicWordgame());
	}

	public Board boardFactory(int size) {
		return new SquareBoard(size);
	}

	public Cell cellFactory(char content) {
		return new TopwordCellDecorator(new BasicCell(content));
	}
	
	// METHODS
	
	private boolean crossMiddleCell(Coordinate start, Direction direction, int length) {
		Coordinate currentCoord = start;
		
		for( int i= 0; i < length; i++) {
			if( (currentCoord.x == 'E' && currentCoord.y == 5) ||
				(currentCoord.x == 'E' && currentCoord.y == 6) ||
				(currentCoord.x == 'F' && currentCoord.y == 5) ||
				(currentCoord.x == 'F' && currentCoord.y == 6) 
			) {
				return true;
			}
			
			if (direction == Direction.COLUMN) { currentCoord = currentCoord.incY(); }
			else { currentCoord = currentCoord.incX(); }
		}
		return false;
	}
	
	public boolean validFirstMove(Coordinate start, Direction direction, int length) {
		if(firstMoveDone  == false){
			if(crossMiddleCell(start, direction, length) == false){
				System.out.println("The first word need to cross one of the 4 middle cells.");
				return false;
			}
			else {
				return true;
			}
		}
		return false;
	}
	
	public boolean validMove(Coordinate start, Direction direction, String word) {
		if(firstMoveDone == false){
			return validFirstMove(start, direction, word.length());
		}
		else {
			return super.validMove(start, direction, word);
		}
	}
	
	public boolean putWord(Coordinate start, Direction direction, String word) {
		Coordinate currentCoord = start;
		for( int i = 0; i < word.length(); i++) {
			try {
				char letter = word.charAt(i);
				if (getBoard().getCell(currentCoord).getContent() != letter) {
					if (((TopwordCellDecorator) getBoard().getCell(currentCoord)).getLevel() < 7) {
						getCurrentPlayer().getRack().pickLetter(letter);
						getBoard().getCell(currentCoord).setContent(letter);
					}
					else {
						System.out.println("Max level reached, can't place " + letter);
						return false;
					}
				}
			} catch (WordgameException e) {
				e.printStackTrace();
				return false;
			}
			
			if (direction == Direction.COLUMN) { currentCoord = currentCoord.incY(); }
			else { currentCoord = currentCoord.incX(); }
		}
		
		firstMoveDone = true;
		return true;
	}
	
	public int getScoreForMove(Coordinate start, Direction direction, String word) {
		int score = super.getScoreForMove(start, direction, word);
		
		Coordinate currentCoord = start;
		for( int i= 0; i < word.length(); i++) {
			try {
				TopwordCellDecorator c = (TopwordCellDecorator) this.getBoard().getCell(currentCoord);
				score += (c.getLevel() == 0) ? 1 : c.getLevel();
			} catch (WordgameException e) {
				e.printStackTrace();
			}
			
			if (direction == Direction.COLUMN) { currentCoord = currentCoord.incY(); }
			else { currentCoord = currentCoord.incX(); }
		}
		
		return score;
	}

}
