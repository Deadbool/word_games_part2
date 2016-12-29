package wordgame.abstraction.decorators;

import java.util.List;

import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.interfaces.Board;
import wordgame.abstraction.interfaces.Direction;
import wordgame.abstraction.interfaces.LetterBag;
import wordgame.abstraction.interfaces.Player;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.abstraction.interfaces.WordgameFactory;

public abstract class WordgameDecorator extends Wordgame implements WordgameFactory {
	
	// ATTRIBUTES & CONSTRUCTOR
	
	protected Wordgame decoratedWordgame;

	public WordgameDecorator(Wordgame decoratedWordgame) {
		this.decoratedWordgame = decoratedWordgame;
	}
	
	// METHODS

	public boolean init(String config, WordgameFactory fac) {
		return decoratedWordgame.init(config, fac);
	}

	public boolean addPlayer(String string) {
		return decoratedWordgame.addPlayer(string);
	}

	public List<Player> getPlayers() {
		return decoratedWordgame.getPlayers();
	}

	public Player getCurrentPlayer() {
		return decoratedWordgame.getCurrentPlayer();
	}

	public Board getBoard() {
		return decoratedWordgame.getBoard();
	}

	public LetterBag getLetterBag() {
		return decoratedWordgame.getLetterBag();
	}

	public void fillCurrentPlayerRack() {
		decoratedWordgame.fillCurrentPlayerRack();
	}

	public Character[] getLettersOnBoard(Coordinate start, Direction direction, int length) {
		return decoratedWordgame.getLettersOnBoard(start, direction, length);
	}
	
	public boolean validFirstMove(Coordinate start, Direction direction, int length){
		return decoratedWordgame.validFirstMove(start, direction, length);
	}
	
	public List<String> getWordFormedByMove(Coordinate start, Direction direction, String word) {
		return decoratedWordgame.getWordFormedByMove(start, direction, word);
	}
	
	public boolean crossAnotherWord(Coordinate start, Direction direction, int length){
		return decoratedWordgame.crossAnotherWord(start, direction, length);
	}
	
	public boolean validMove(Coordinate start, Direction direction, String word) {
		return decoratedWordgame.validMove(start, direction, word);
	}

	public boolean putWord(Coordinate start, Direction direction, String word) {
		return decoratedWordgame.putWord(start, direction, word);
	}

	public int getScoreForMove(Coordinate start, Direction direction, String word) {
		return decoratedWordgame.getScoreForMove(start, direction, word);
	}

	public void skipTurn() {
		decoratedWordgame.skipTurn();
		newTurn();
	}

	public boolean isOver() {
		return decoratedWordgame.isOver();
	}

}
