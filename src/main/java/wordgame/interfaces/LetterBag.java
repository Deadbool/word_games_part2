package wordgame.interfaces;

import wordgame.common.WordgameException;

public interface LetterBag {
	char pick() throws WordgameException;
	char exchange(char letter) throws WordgameException;
	int pointFor(char letter);
	int size();
}
