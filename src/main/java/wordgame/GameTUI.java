package wordgame;
import java.util.List;
import java.util.Scanner;

import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;

import wordgame.abstraction.common.BasicWordgame;
import wordgame.abstraction.common.Coordinate;
import wordgame.abstraction.decorators.scrabble.ScrabbleDecorator;
import wordgame.abstraction.decorators.topword.TopwordCellDecorator;
import wordgame.abstraction.decorators.topword.TopwordDecorator;
import wordgame.abstraction.interfaces.Board;
import wordgame.abstraction.interfaces.Direction;
import wordgame.abstraction.interfaces.LetterBag;
import wordgame.abstraction.interfaces.Player;
import wordgame.abstraction.interfaces.Rack;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.abstraction.interfaces.WordgameFactory;

public class GameTUI {

	public static void main(String[] args) {
		System.out.println("1 - Topword");
		System.out.println("2 - Scrabble");
		System.out.println("Choose your game :");
		Scanner sc = new Scanner(System.in);
		int choosed = sc.nextInt();
		
		switch (choosed) {
		case 1:
			gameLoop(GameTUI.class.getResource("/topword.wg").getPath(), TopwordDecorator.FACTORY);
			break;
			
		case 2:
			gameLoop(GameTUI.class.getResource("/scrabble.wg").getPath(), ScrabbleDecorator.FACTORY);
			break;

		default:
			break;
		}
		
		System.out.println("\nBye.\n");
		sc.close();
	}
	
	/* 
	 * Game loop 
	 */
	public static void gameLoop(String gameConfig, WordgameFactory fac){
		Scanner sc = new Scanner(System.in);
		Wordgame game = fac.gameFactory();
		boolean turnPlayed;
		
		game.init(gameConfig, fac);
		game.addPlayer("Max");
		game.addPlayer("Léon");
		game.addPlayer("George");
		
		do{ // while(!game.isOver())
			turnPlayed = false;
			
			System.out.println("\n───────────────────────────────────\n");
			AnsiConsole.out.println( ansi().eraseScreen() );
		    
		    // Display game
		    tuiDisplay(game.getLetterBag());
			tuiDisplay(game.getPlayers(), game.getCurrentPlayer());
			tuiDisplay(game.getBoard());
			tuiDisplay(game.getCurrentPlayer().getRack());
			
			do { // while (turnPlayed == false)
				game.newTurn();
				System.out.print("\nWord, X, Y, line/column OR pass => ");
				String instruction = sc.nextLine();
				if(instruction.contains("pass")) {
					game.skipTurn();
					turnPlayed = true;
				}
				else if(!instruction.isEmpty()){
					String[] instructionArgs = instruction.split(",");
					
					if(instructionArgs.length == 4) {
						String word = instructionArgs[0].trim();
						char x = instructionArgs[1].trim().charAt(0);
						int y = Integer.parseInt(instructionArgs[2].trim());
						Direction direction = instructionArgs[3].contains("c") ? Direction.COLUMN : Direction.LINE ;
						
						if(game.validMove(new Coordinate(x, y), direction, word)) {
							turnPlayed = game.putWord(new Coordinate(x, y), direction, word);
						}
						if(turnPlayed){
							game.getCurrentPlayer().addPoint(game.getScoreForMove(new Coordinate(x, y), direction, word));
							game.fillCurrentPlayerRack();
							game.skipTurn();
						}
					}
					else {
						System.out.println("Invalid input");
					}
				}
			} while (turnPlayed == false);
		} while(!game.isOver());
		
		System.out.println("GAME OVER");
		sc.close();
	}
	
	/*
	 * Text based UI methods
	 */
	public static void tuiDisplay(LetterBag lb) {
		System.out.println("Remaining letters in bag : " + lb.size() + "\n");
	}
	
	public static void tuiDisplay(List<Player> plrs, Player currentPlayer) {
		for(Player p : plrs){
			System.out.print( p == currentPlayer ? "> " : "- " );
			System.out.print( p.getNickname() + " (" + p.getScore() + " points)\n" );
		}
		System.out.println("");
	}
	
	public static void tuiDisplay(Board b) {
		String[][] dataGrid = b.toStringArray();
		//AnsiConsole.out.println(smallBoardString(dataGrid) + "\n");
		 System.out.println(smallBoardString(dataGrid) + "\n"); // for eclipse dev with ansi color plugin
	}
	
	public static void tuiDisplay(Rack p) {
		System.out.print("Rack : ");
		for(Character l : p.getContent()){
			System.out.print(" " + l + " ");
		}
		System.out.print("\n");
	}
	
	public static String bigBoardString(String[][] dataGrid) {
		String result = "      ";
		
		// Abscice coord
		for(int x = 0; x < dataGrid.length; x++) {
			result += Character.toString( (char) (Coordinate.A_ASCII_CODE + x) ) + "   ";
		}
		result += "\n";
		
		// Board lines
		for(int y = 0; y < dataGrid.length; y++){
			
			// Upper border
			if (y == 0){
				for(int i = 0; i < dataGrid.length+1; i++) {
					if (i == 0)
						result += "    ┌";
					else if (i == dataGrid.length)
						result += "───┐";
					else
						result += "───┬";
				}
				result += "\n";
			}
			// Inter cells border
			else {
				for(int i = 0; i < dataGrid.length+1; i++) {
					if(i == 0)
						result += "    ├";
					else if (i == dataGrid.length)
						result += "───┤";
					else
						result += "───┼";
				}
				result += "\n";
			}
			
			// Ordonate coord
			result += " " + String.format("%02d", y+1) + " │ ";
			// Letters
			for(int x = 0; x < dataGrid.length; x++) {
				result += dataGrid[x][y] + " │ ";
			}
			// Ordonate coord
			result += String.format("%02d", y+1);
			result += "\n";
		}
		 
		// Lower border
		for(int i = 0; i < dataGrid.length+1; i++) {
			if (i == 0)
				result += "    └";
			else if (i == dataGrid.length)
				result += "───┘";
			else
				result += "───┴";
		}
		result += "\n";
		
		// Abscice coord
		result += "      ";
		for(int x = 0; x < dataGrid.length; x++) {
			result += Character.toString( (char) (Coordinate.A_ASCII_CODE + x) ) + "   ";
		}
		
		return result;
	}
	
	public static String smallBoardString(String[][] dataGrid) {
		String result = "    ";
		
		// Abscice coord
		for(int x = 0; x < dataGrid.length; x++) {
			result += Character.toString( (char) (Coordinate.A_ASCII_CODE + x) ) + " ";
		}
		result += "\n";
		
		// Board lines
		for(int y = 0; y < dataGrid.length; y++){
			
			// Ordonate coord
			result += " " + String.format("%02d", y+1) + " ";
			// Letters
			for(int x = 0; x < dataGrid.length; x++) {
				result += dataGrid[x][y] + " ";
			}
			// Ordonate coord
			result += String.format("%02d", y+1);
			result += "\n";
		}
		
		// Abscice coord
		result += "    ";
		for(int x = 0; x < dataGrid.length; x++) {
			result += Character.toString( (char) (Coordinate.A_ASCII_CODE + x) ) + " ";
		}
		
		return result;
	}
}
