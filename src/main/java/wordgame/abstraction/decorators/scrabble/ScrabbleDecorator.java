package wordgame.abstraction.decorators.scrabble;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import wordgame.abstraction.common.BasicCell;
import wordgame.abstraction.common.BasicWordgame;
import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.common.Dictionnary;
import wordgame.abstraction.common.SquareBoard;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.decorators.WordgameDecorator;
import wordgame.abstraction.decorators.scrabble.Multiplier.MultiplierType;
import wordgame.abstraction.interfaces.Board;
import wordgame.abstraction.interfaces.Cell;
import wordgame.abstraction.interfaces.Direction;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.abstraction.interfaces.WordgameFactory;

public class ScrabbleDecorator extends WordgameDecorator {

	// ATTRIBUTES & CONSTRUCTOR
	
	private boolean firstMoveDone = false;
	
	public ScrabbleDecorator(Wordgame decoratedWordgame) {
		super(decoratedWordgame);
	}
	
	// FACTORIES
	
	public static WordgameFactory FACTORY = new ScrabbleDecorator(new BasicWordgame());
	
	public Wordgame gameFactory() {
		return new ScrabbleDecorator(new BasicWordgame()); 
	}
	
	public Board boardFactory(int size) {
		return new SquareBoard(size);
	}

	public Cell cellFactory(char content) {
		return new ScrabbleCellDecorator(new BasicCell(content));
	}
	
	// METHODS
	
	@Override
	public boolean init(String configFile, WordgameFactory fac) {
		boolean basicInit = super.init(configFile, fac);
		
		if (basicInit == false) { return false; }
		
		// Create config file parser
		JsonParser parser = new JsonParser();
		JsonObject config;
		try {
			config = parser.parse(new FileReader(configFile)).getAsJsonObject();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		JsonObject boardConfig = config.get("board").getAsJsonObject();
		
		// Set case multiplier
		JsonObject multipliers = boardConfig.get("modifier").getAsJsonObject();
		for (Entry<String, JsonElement> m : multipliers.entrySet()) {
			
			Multiplier multiplier;
			ScrabbleCellDecorator.Color color;
			
			if (m.getKey().equals("3WS")) {
				multiplier = new Multiplier(3, Multiplier.MultiplierType.WORD);
				color = ScrabbleCellDecorator.Color.RED;
			}
			else if (m.getKey().equals("2WS")) {
				multiplier = new Multiplier(2, Multiplier.MultiplierType.WORD);
				color = ScrabbleCellDecorator.Color.PINK;
			}
			else if (m.getKey().equals("3LS")) {
				multiplier = new Multiplier(3, Multiplier.MultiplierType.LETTER);
				color = ScrabbleCellDecorator.Color.DARK_BLUE;
			}
			else if (m.getKey().equals("2LS")) {
				multiplier = new Multiplier(2, Multiplier.MultiplierType.LETTER);
				color = ScrabbleCellDecorator.Color.LIGHT_BLUE;
			}
			else {
				return false;
			}
			
			JsonArray cells = m.getValue().getAsJsonArray();
			for (Iterator<JsonElement> iterator = cells.iterator(); iterator.hasNext();) {
				JsonArray cell = ((JsonElement) iterator.next()).getAsJsonArray();
				char x = cell.get(0).getAsCharacter();
				int y = cell.get(1).getAsInt();
				Coordinate coord = new Coordinate(x, y);
				try {
					((ScrabbleCellDecorator) this.getBoard().getCell(coord)).setModifier(multiplier, color);
				} catch (WordgameException e) {
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}
		
	private boolean crossMiddleCell(Coordinate start, Direction direction, int length) {
		Coordinate currentCoord = start;
		
		for( int i= 0; i < length; i++) {
			if(currentCoord.x == 'H' && currentCoord.y == 8) {
				return true;
			}
			
			if (direction == Direction.COLUMN) { currentCoord = currentCoord.incY(); }
			else { currentCoord = currentCoord.incX(); }
		}
		return false;
	}
	
	public boolean validFirstMove(Coordinate start, Direction direction, int length) {
		if(firstMoveDone == false){
			if(crossMiddleCell(start, direction, length) == false){
				System.out.println("The first word need to cross the middle cell.");
				return false;
			}
			else {
				return true;
			}
		}
		return false;
	}
	
	public boolean validMove(Coordinate start, Direction direction, String word) {
		if (!Dictionnary.DICO.contain(word))
			return false;
		
		if(firstMoveDone == false){
			return validFirstMove(start, direction, word.length());
		}
		else {
			return super.validMove(start, direction, word);
		}
	}
	
	public boolean putWord(Coordinate start, Direction direction, String word) {
		Coordinate currentCoord = start;
		for( int i = 0; i < word.length(); i++) {
			try {
				char letter = word.charAt(i);
				//if (getBoard().getCell(currentCoord).isEmpty()) {
				if (getBoard().getCell(currentCoord).getContent() != letter) {
					getCurrentPlayer().getRack().pickLetter(letter);
					getBoard().getCell(currentCoord).setContent(letter);
				}
				/*else {
					System.out.println("Can't place a letter on top of another.");
					return false;
				}*/
			} catch (WordgameException e) {
				e.printStackTrace();
				return false;
			}
			
			if (direction == Direction.COLUMN) { currentCoord = currentCoord.incY(); }
			else { currentCoord = currentCoord.incX(); }
		}
		
		firstMoveDone = true;
		return true;
	}
	
	@Override
	public int getScoreForMove(Coordinate start, Direction direction, String word) {
		int score = super.getScoreForMove(start, direction, word);
		
		Coordinate currentCoord = start;
		for( int i= 0; i < word.length(); i++) {
			try {
				ScrabbleCellDecorator c = (ScrabbleCellDecorator) this.getBoard().getCell(currentCoord);
				if(c.getModifier() != null){
					Multiplier mul = (Multiplier) c.getModifier();
					if( mul.getType() == MultiplierType.LETTER ) {
						int letterValue = getLetterBag().pointFor(getBoard().getCell(currentCoord).getContent());
						score -= letterValue;
						score += mul.apply(letterValue);
						System.out.println("> Bonus : letter multiplier");
					}
				}
			} catch (WordgameException e) {
				e.printStackTrace();
			}
			
			if (direction == Direction.COLUMN) { currentCoord = currentCoord.incY(); }
			else { currentCoord = currentCoord.incX(); }
		}
		
		currentCoord = start;
		for( int i= 0; i < word.length(); i++) {
			try {
				ScrabbleCellDecorator c = (ScrabbleCellDecorator) this.getBoard().getCell(currentCoord);
				if(c.getModifier() != null){
					Multiplier mul = (Multiplier) c.getModifier();
					if( mul.getType() == MultiplierType.WORD ) {
						score = mul.apply(score);
						System.out.println("> Bonus : word multiplier");
					}
				}
			} catch (WordgameException e) {
				e.printStackTrace();
			}
			
			if (direction == Direction.COLUMN) { currentCoord = currentCoord.incY(); }
			else { currentCoord = currentCoord.incX(); }
		}
		
		System.out.println("> " + word + " placed, " + score + " points");
		
		return score;
	}
}
