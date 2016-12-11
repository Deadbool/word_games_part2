package wordgame.common;

import java.util.ArrayList;
import java.util.List;

import wordgame.interfaces.Rack;

public class BasicRack implements Rack {
	
	private List<Character> letters;
	
	public BasicRack() {
		letters = new ArrayList<Character>(7);
	}
	
	public BasicRack(int size) {
		letters = new ArrayList<Character>(size);
	}
	
	public int size() {
		return letters.size();
	}
	
	public void addLetter(Character letter) {
		letters.add(letter);
	}
	
	public Character pickLetter(Character letter) throws WordgameException {
		if(!this.letters.remove(letter)) {
			throw new WordgameException("Letter '" + letter + "' not in rack");
		}
		return letter;
	}
	
	public boolean asLettersToForm(String word, Character[] lettersOnBoard) {
		if (word.length() != lettersOnBoard.length) {
			return false;
		}
		
		List<Character> lettersCopy = new ArrayList<Character>(this.letters);
		
		for(int i = 0; i < word.length(); i++) {
			Character letter = word.charAt(i);
			// Lettre déjà sur le plateau
			if (lettersOnBoard[i] != null && lettersOnBoard[i] == letter) {
				// continue
			}
			// Lettre en main
			else if (lettersCopy.contains(letter)){
				lettersCopy.remove(letter);
			}
			else {
				return false;
			}
		}
		
		return true;
	}

	public List<Character> getContent() {
		return this.letters;
	}
	
}
