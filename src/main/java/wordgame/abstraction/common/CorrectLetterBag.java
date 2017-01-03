package wordgame.abstraction.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import wordgame.abstraction.interfaces.LetterBag;

public class CorrectLetterBag implements LetterBag {
	
	private ArrayList<Character> content = new ArrayList<Character>();
	private HashMap<Character, Integer> points = new HashMap<Character, Integer>();
	
	public CorrectLetterBag(JsonArray alphabetConf){
		for(Iterator<JsonElement> iterator = alphabetConf.iterator(); iterator.hasNext();){
			JsonArray l = ((JsonElement) iterator.next()).getAsJsonArray();
			
			char letter = l.get(0).getAsCharacter();
			int quantity = l.get(1).getAsInt();
			int point = l.get(2).getAsInt();
					
			points.put(letter, point);
			for (int i=0; i < quantity; i++) {
				this.content.add(letter);
			}
		}
	}
	
	public char pick() throws WordgameException {
		if(this.size() <= 0) {
			throw new WordgameException("Letter bag is empty");
		}
				
		int index = ThreadLocalRandom.current().nextInt(0, content.size());
		char picked = content.get(index);
		content.remove(index);
		
		return picked;
	}
	
	public char exchange(char letter) throws WordgameException {
		if(size() >= 7){
			char picked = this.pick();
			content.add(letter);
			return picked;
		}
		else {
			throw new WordgameException("Exchange forbiden, not enought letters");
		}
	}
	
	public int pointFor(char letter) {
		return points.get(letter);
	}
	
	public int size() {
		return this.content.size();
	}
}
