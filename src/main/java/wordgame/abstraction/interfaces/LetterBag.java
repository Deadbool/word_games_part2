package wordgame.abstraction.interfaces;

import wordgame.abstraction.common.WordgameException;

public interface LetterBag {
	char pick() throws WordgameException;
	char exchange(char letter) throws WordgameException;
	int pointFor(char letter);
	int size();
}
