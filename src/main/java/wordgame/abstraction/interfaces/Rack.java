package wordgame.abstraction.interfaces;

import java.util.List;

import wordgame.abstraction.common.WordgameException;

public interface Rack {
	
	public static final int RACK_SIZE = 7;
	
	int size();
	void addLetter(Character letter);
	Character pickLetter(Character letter) throws WordgameException;
	boolean asLettersToForm(String word, Character[] lettersOnBoard);
	List<Character> getContent();
}
