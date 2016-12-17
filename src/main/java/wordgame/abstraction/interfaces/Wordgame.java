package wordgame.abstraction.interfaces;

import java.util.List;
import java.util.Observable;

import wordgame.abstraction.common.Coordinate;
import wordgame.control.Event;

public abstract class Wordgame extends Observable {
	public abstract boolean init(String configFile, WordgameFactory fac);
	public abstract boolean addPlayer(String string);
	public abstract List<Player> getPlayers();
	public abstract Player getCurrentPlayer();
	public abstract Board getBoard();
	public abstract LetterBag getLetterBag();
	
	public abstract void fillCurrentPlayerRack();
	
	public abstract Character[] getLettersOnBoard(Coordinate start, Direction direction, int length);
	public abstract List<String> getWordFormedByMove(Coordinate start, Direction direction, String word);
	public abstract boolean crossAnotherWord(Coordinate start, Direction direction, int length);
	public abstract boolean validFirstMove(Coordinate start, Direction direction, int length);
	public abstract boolean validMove(Coordinate start, Direction direction, String word);
	public abstract boolean putWord(Coordinate start, Direction direction, String word);
	public abstract int getScoreForMove(Coordinate start, Direction direction, String word);
	
	public abstract void skipTurn();
	public abstract boolean isOver();
	
	public void newTurn() {
		this.setChanged();
		this.notifyObservers(Event.NEW_TURN);
		System.err.println("BasicWordGame::newTurn() send NEW_TURN");
	}
}
