package wordgame.abstraction.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import wordgame.abstraction.interfaces.LetterBag;
import wordgame.abstraction.interfaces.Tuple;

public class BasicLetterBag implements LetterBag {
	
	private class Letter implements Tuple<Integer, Integer> {
		private final int point;
		private int quantity;
		
		public Letter(int quantity, int point){
			this.point = point;
			this.quantity = quantity;
		}
		
		public int getPoint(){ return this.point; }
		public int getQuantity(){ return this.quantity; }
		public void pickedOne(){ this.quantity--; }
		public void addedOne(){ this.quantity++; }
	}
	
	private Map<Character, Letter> alphabet = new HashMap<Character, Letter>();
	private int size = 0;
	
	public BasicLetterBag(JsonArray alphabetConf){
		for(Iterator<JsonElement> iterator = alphabetConf.iterator(); iterator.hasNext();){
			JsonArray l = ((JsonElement) iterator.next()).getAsJsonArray();
			
			char letter = l.get(0).getAsCharacter();
			int quantity = l.get(1).getAsInt();
			int point = l.get(2).getAsInt();
					
			this.alphabet.put(letter, new Letter(quantity, point));
			size += quantity; 
		}
	}

	private ArrayList<Character> remainingLetters() {
		ArrayList<Character> remaining = new ArrayList<Character>();
		for(Entry<Character, Letter> letter : alphabet.entrySet()) {
			if (letter.getValue().getQuantity() > 0) {
				remaining.add(letter.getKey());
			}
		}
		return remaining;
	}
	
	public char pick() throws WordgameException {
		if(this.size() <= 0) {
			throw new WordgameException("Letter bag is empty");
		}
		
		ArrayList<Character> candidates = remainingLetters();
		
		int nb = ThreadLocalRandom.current().nextInt(0, candidates.size());
		char picked = candidates.get(nb);
		alphabet.get(picked).pickedOne();
		size--;
		return picked;
	}
	
	public char exchange(char letter) throws WordgameException {
		if(size >= 7){
			alphabet.get(letter).addedOne();
			size++;
			return this.pick();
		}
		else {
			throw new WordgameException("Exhcange forbiden, not enought letters");
		}
	}
	
	public int pointFor(char letter) {
		return alphabet.get(letter).getPoint();
	}
	
	public int size() {
		return this.size;
	}

	

}
