package wordgame.control;

import wordgame.GameTUI;
import wordgame.abstraction.decorators.scrabble.ScrabbleDecorator;
import wordgame.abstraction.decorators.topword.TopwordDecorator;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.WordgameFrame;

public class WindowManager {
	
	private static WordgameFrame frame;
	
	public static void launchScrabble() {
		Wordgame game = ScrabbleDecorator.FACTORY.gameFactory();
		
		game.init(GameTUI.class.getResource("/scrabble.wg").getPath(), ScrabbleDecorator.FACTORY);
		game.addPlayer("Max");
		game.addPlayer("Léon");
		game.addPlayer("George");
		game.addPlayer("Nicolas");
		
		game.newTurn();
		
		frame = new WordgameFrame(game);
		frame.setVisible(true);
	}
	
	public static void launchTopword() {
		Wordgame game = TopwordDecorator.FACTORY.gameFactory();
		
		game.init(GameTUI.class.getResource("/topword.wg").getPath(), TopwordDecorator.FACTORY);
		game.addPlayer("Max");
		game.addPlayer("Léon");
		game.addPlayer("George");
		game.addPlayer("Nicolas");
		
		game.newTurn();
		
		frame = new WordgameFrame(game);
		frame.setVisible(true);
	}
}
