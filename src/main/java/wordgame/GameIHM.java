package wordgame;

import wordgame.abstraction.decorators.topword.TopwordDecorator;
import wordgame.abstraction.interfaces.Player;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.WordgameFrame;

public class GameIHM {
	public static void main(String[] args) {
		
		Wordgame game = TopwordDecorator.FACTORY.gameFactory();
		
		game.init(GameTUI.class.getResource("/topword.wg").getPath(), TopwordDecorator.FACTORY);
		game.addPlayer("Max");
		game.addPlayer("Léon");
		game.addPlayer("George");
		
		WordgameFrame window = new WordgameFrame(game);
		window.setVisible(true);
	
		for (Player player : game.getPlayers()) {
			player.addPoint(5);
			game.skipTurn();
		}
		
	}
}
