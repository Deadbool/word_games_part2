package wordgame.abstraction.common;

import java.util.ArrayList;
import java.util.List;

import wordgame.abstraction.interfaces.Rack;

public class BasicRack implements Rack {
		
	private List<Character> letters;
	
	public BasicRack() {
		letters = new ArrayList<Character>(RACK_SIZE);
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
			System.err.println(this.letters.toString());
			System.err.println(this.letters.contains(Rack.JOKER_CHAR));
			if (this.letters.remove((Rack.JOKER_CHAR))) {
				return letter;
			} else {
				throw new WordgameException("Letter '" + letter + "' not in rack");
			}
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
			}else if (lettersCopy.contains(Rack.JOKER_CHAR)){
				lettersCopy.remove(Rack.JOKER_CHAR);
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
