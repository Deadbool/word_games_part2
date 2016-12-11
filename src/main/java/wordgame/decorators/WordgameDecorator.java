package wordgame.decorators;

import java.util.List;

import wordgame.common.Coordinate;
import wordgame.interfaces.Board;
import wordgame.interfaces.Direction;
import wordgame.interfaces.LetterBag;
import wordgame.interfaces.Player;
import wordgame.interfaces.Wordgame;
import wordgame.interfaces.WordgameFactory;

public abstract class WordgameDecorator implements Wordgame, WordgameFactory {
	
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
	}

	public boolean isOver() {
		return decoratedWordgame.isOver();
	}

}
