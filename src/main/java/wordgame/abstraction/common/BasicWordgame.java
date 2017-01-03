package wordgame.abstraction.common;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import wordgame.abstraction.interfaces.Board;
import wordgame.abstraction.interfaces.Cell;
import wordgame.abstraction.interfaces.Direction;
import wordgame.abstraction.interfaces.LetterBag;
import wordgame.abstraction.interfaces.Player;
import wordgame.abstraction.interfaces.Rack;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.abstraction.interfaces.WordgameFactory;

public class BasicWordgame extends Wordgame implements WordgameFactory {
	
	// ATTRIBUTES
	
	private Board board;
	private Dictionnary dico;
	private LetterBag letterBag;
	private List<Player> players;
	
	private Player currentPlayer = null;
	
	private int maxPlayer;
	private int rackSize;
	
	private boolean firstMovePlayed = false;
	
	// FACTORIES
	
	public static WordgameFactory FACTORY = new BasicWordgame();
	
	public Wordgame gameFactory() {
		return new BasicWordgame(); 
	}
	
	public Board boardFactory(int size) {
		return new SquareBoard(size);
	}

	public Cell cellFactory(char content) {
		return new BasicCell(content);
	}
	
	// METHODS
	
	public boolean init(String configFile, WordgameFactory fac) {
		this.players = new ArrayList<Player>();
		this.maxPlayer = 4;
		this.rackSize = 7;
		
		// Create config file parser
		JsonParser parser = new JsonParser();
		JsonObject config;
		try {
			config = parser.parse(new FileReader(configFile)).getAsJsonObject();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		// Create board
		JsonObject boardConfig = config.get("board").getAsJsonObject();
		int size = boardConfig.get("size").getAsInt();
		
		this.board = fac.boardFactory(size);
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++) {
				Coordinate coord = new Coordinate((char) (Coordinate.A_ASCII_CODE + x), y+1);
				Cell cell = fac.cellFactory((char)0x25a1);//□');
				this.board.setCell(coord, cell);
			}
		}
		
		// Create dico
		String dicoFile = "/" + config.get("dictionnary").getAsString();
		dicoFile = BasicWordgame.class.getResource(dicoFile).getPath();
		dico = new Dictionnary(dicoFile);
		
		// Create letter bag
		JsonArray alphabetConf = config.get("letters").getAsJsonArray();
		letterBag = new BasicLetterBag(alphabetConf);
		
		return true;
	}
	
	public boolean addPlayer(String name) {
		if(this.players.size() >= this.maxPlayer) {
			return false;
		}
		for(Player p : this.players) {
			if(p.getNickname().equals(name)){
				return false;
			}
		}
		
		Player p = new BasicPlayer(name);
		this.players.add(p);
		this.currentPlayer = players.get(players.size()-1);
		this.fillCurrentPlayerRack();
		return true;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public Board getBoard() {
		return this.board;
	}
	
	public LetterBag getLetterBag() {
		return this.letterBag;
	}
	
	public void fillCurrentPlayerRack() {
		Rack currentPlayerRack = this.getCurrentPlayer().getRack();
		while(currentPlayerRack.size() < this.rackSize) {
			try {
				currentPlayerRack.addLetter(this.letterBag.pick());
			} catch (WordgameException e) {
				e.printStackTrace();
			}
		}
	}

	public Character[] getLettersOnBoard(Coordinate start, Direction direction, int length) {
		Character[] letters = new Character[length];
		Coordinate currentCoord = start;
		
		for( int i= 0; i < length; i++) {
			try {
				letters[i] = board.getCell(currentCoord).getContent();
			} catch (WordgameException e) {
				e.printStackTrace();
			}
			
			if (direction == Direction.COLUMN) { currentCoord = currentCoord.incY(); }
			else { currentCoord = currentCoord.incX(); }
		}
		return letters;
	}
	
	public List<String> getWordFormedByMove(Coordinate start, Direction direction, String word){
		List<String> result = new ArrayList<String>();
		Coordinate currentCoord = start;
		
		for( int i= 0; i < word.length(); i++) {
			String transversalWord = Character.toString( word.charAt(i) );
			// Si mot en colonne
			if (direction == Direction.COLUMN) {
				// Check si mot sur la ligne de la lettre
				Coordinate currentTransCoord = currentCoord;
				
				// Avant la lettre
				while( true ){
					currentTransCoord = currentTransCoord.decX();
					try {
						if (!this.board.getCell(currentTransCoord).isEmpty()) {
							transversalWord = this.board.getCell(currentTransCoord).getContent() + transversalWord;
						} else {
							break;
						}
					} catch (WordgameException e) {
						break;
					}

				}
				// Après la lettre
				currentTransCoord = currentCoord;
				while( true ){
					currentTransCoord = currentTransCoord.incX();
					try {
						if (!this.board.getCell(currentTransCoord).isEmpty()) {
							transversalWord = transversalWord + this.board.getCell(currentTransCoord).getContent();
						} else {
							break;
						}
					} catch (WordgameException e) {
						break;
					}

				}
				
				if(transversalWord.length() > 1){
					result.add(transversalWord);
				}
				
				currentCoord = currentCoord.incY(); 
			}
			// Si mot ligne
			else {
				// Check si mot sur la colone de la lettre
				Coordinate currentTransCoord = currentCoord;
				
				// Au dessus de la lettre
				while( true ){
					currentTransCoord = currentTransCoord.decY();
					try {
						if (!this.board.getCell(currentTransCoord).isEmpty()) {
							transversalWord = this.board.getCell(currentTransCoord).getContent() + transversalWord;
						} else {
							break;
						}
					} catch (WordgameException e) {
						break;
					}

				}
				
				// Au dessous de la lettre
				currentTransCoord = currentCoord;
				while( true ){
					currentTransCoord = currentTransCoord.incY();
					try {
						if (!this.board.getCell(currentTransCoord).isEmpty()) {
							transversalWord = transversalWord + this.board.getCell(currentTransCoord).getContent();
						} else {
							break;
						}
					} catch (WordgameException e) {
						break;
					}

				}
				
				if(transversalWord.length() > 1){
					result.add(transversalWord);
				}
				
				currentCoord = currentCoord.incX(); 
			}
		}
		return result;
	}
	
	public boolean crossAnotherWord(Coordinate start, Direction direction, int length){
		Character[] lettersOnBoard = this.getLettersOnBoard(start, direction, length);
		for(int i = 0; i < lettersOnBoard.length; i++){
			if(Character.isLetter(lettersOnBoard[i]) == true){
				return true;
			}
		}
		return false;
	}
	
	public boolean validFirstMove(Coordinate start, Direction direction, int length){
		return true;
	}
	
	public boolean validMove(Coordinate start, Direction direction, String word) {
		boolean valid = true;
		
		Coordinate extremity = start;
		boolean before = true, after = true;
		if(direction == Direction.LINE){
			try {
				before = board.getCell(extremity.decX()).isEmpty();
			} catch (Exception e) {}
			try { 
				for(int i = 0; i < word.length(); i++){ extremity = extremity.incX(); }
				after = board.getCell(extremity.incX()).isEmpty();
			} catch (Exception e) {}
			valid = valid && (before && after);
		}
		else { // (direction == Direction.COLUMN)
			try {
				before = board.getCell(extremity.decY()).isEmpty();
			} catch (WordgameException e) {}
			try { 
				for(int i = 0; i < word.length(); i++){ extremity = extremity.incY(); }
				after = board.getCell(extremity.incY()).isEmpty();
			} catch (WordgameException e) {}
			valid = valid && (before && after);
		}
		if(!valid){
			System.out.println("Enter the all word you want to form (extremity not empty)");
			return valid;
		}
		
		valid = valid && dico.contain(word);
		if(!valid) {
			System.out.println("Word not in dictionnary.");
			return valid;
		}
		
		Character[] lettersOnBoard = this.getLettersOnBoard(start, direction, word.length());
		valid = valid && currentPlayer.getRack().asLettersToForm(word, lettersOnBoard);
		if(!valid){
			System.out.println("Can't form this word with those letters.");
			return valid;
		}
		
		if(firstMovePlayed == true){
			valid = valid && crossAnotherWord(start, direction, word.length());
			if(!valid){
				System.out.println("The word must cross another one");
				return valid;
			}
		}
		
		return valid;
	}
	
	public boolean putWord(Coordinate start, Direction direction, String word) {
				
		Coordinate currentCoord = start;
		for( int i = 0; i < word.length(); i++) {
			try {
				char letter = word.charAt(i);
				if (board.getCell(currentCoord).getContent() != letter) {
					this.currentPlayer.getRack().pickLetter(letter);
					board.getCell(currentCoord).setContent(letter);
				}
			} catch (WordgameException e) {
				e.printStackTrace();
				return false;
			}
			
			if (direction == Direction.COLUMN) { currentCoord = currentCoord.incY(); }
			else { currentCoord = currentCoord.incX(); }
		}
		
		firstMovePlayed = true;
		
		return true;
	}
	
	public int getScoreForMove(Coordinate start, Direction direction, String word) {
		int score = 0;
		
		System.out.println("> Word formed : " + word);
		
		for( int i= 0; i < word.length(); i++) {
			score += this.letterBag.pointFor(word.charAt(i));
		}
		
		if(this.getWordFormedByMove(start, direction, word).size() > 0){
			for(String w : this.getWordFormedByMove(start, direction, word)){
				for( int i= 0; i < w.length(); i++) {
					score += this.letterBag.pointFor(w.charAt(i));
				}
				System.out.println("> Word formed : " + w);
			}
		}
		
		return score;
	}

	private void nextPlayer() {
		int playerIndex = this.players.indexOf(this.currentPlayer);
		if (playerIndex >= this.players.size() - 1) {
			this.currentPlayer = this.players.get(0);
		}
		else {
			this.currentPlayer = this.players.get(playerIndex + 1);
		}
	}
	
	public void skipTurn() {
		this.nextPlayer();
	}

	public boolean isOver() {
		if(this.letterBag.size() <= 0){
			for(Player p : this.players) {
				if (p.getRack().size() <= 0) {
					return true;
				}
			}
		}
		return false;
	}

}
