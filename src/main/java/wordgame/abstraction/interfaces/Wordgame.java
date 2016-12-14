package wordgame.abstraction.interfaces;

import java.util.List;

import wordgame.abstraction.common.Coordinate;

public interface Wordgame {
	boolean init(String configFile, WordgameFactory fac);
	boolean addPlayer(String string);
	List<Player> getPlayers();
	Player getCurrentPlayer();
	Board getBoard();
	LetterBag getLetterBag();
	
	void fillCurrentPlayerRack();
	
	Character[] getLettersOnBoard(Coordinate start, Direction direction, int length);
	List<String> getWordFormedByMove(Coordinate start, Direction direction, String word);
	boolean crossAnotherWord(Coordinate start, Direction direction, int length);
	boolean validFirstMove(Coordinate start, Direction direction, int length);
	boolean validMove(Coordinate start, Direction direction, String word);
	boolean putWord(Coordinate start, Direction direction, String word);
	int getScoreForMove(Coordinate start, Direction direction, String word);
	
	void skipTurn();
	boolean isOver();
}
