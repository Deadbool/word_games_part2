package wordgame;

import wordgame.abstraction.decorators.topword.TopwordDecorator;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.WordgameFrame;

public class GameIHM {
	public static void main(String[] args) {
		
		Wordgame game = TopwordDecorator.FACTORY.gameFactory();
		
		game.init(GameTUI.class.getResource("/topword.wg").getPath(), TopwordDecorator.FACTORY);
		game.addPlayer("Max");
		game.addPlayer("Léon");
		game.addPlayer("George");
		game.addPlayer("Nicolas");
		
		WordgameFrame window = new WordgameFrame(game);
		window.setVisible(true);
	}
}
