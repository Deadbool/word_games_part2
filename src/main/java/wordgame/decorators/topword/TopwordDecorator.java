package wordgame.decorators.topword;

import wordgame.common.BasicCell;
import wordgame.common.BasicWordgame;
import wordgame.common.Coordinate;
import wordgame.common.SquareBoard;
import wordgame.common.WordgameException;
import wordgame.decorators.WordgameDecorator;
import wordgame.decorators.scrabble.Multiplier;
import wordgame.decorators.scrabble.ScrabbleCellDecorator;
import wordgame.decorators.scrabble.ScrabbleDecorator;
import wordgame.decorators.scrabble.Multiplier.MultiplierType;
import wordgame.interfaces.Board;
import wordgame.interfaces.Cell;
import wordgame.interfaces.Direction;
import wordgame.interfaces.Wordgame;
import wordgame.interfaces.WordgameFactory;

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
