package wordgame.control;

import javax.swing.JFrame;

import wordgame.GameTUI;
import wordgame.abstraction.decorators.scrabble.ScrabbleDecorator;
import wordgame.abstraction.decorators.topword.TopwordDecorator;
import wordgame.abstraction.interfaces.Wordgame;
import wordgame.presentation.dialogs.ChangeLettersDialog;
import wordgame.presentation.dialogs.CreatePlayerDialog;
import wordgame.presentation.frames.CreateGameFrame;
import wordgame.presentation.frames.WordgameFrame;

public class WindowManager {
	
	public static WordgameFrame WORDGAME_FRAME;
	public static final CreateGameFrame CREATE_GAME_FRAME = new CreateGameFrame();
	
	public static final ChangeLettersDialog CHANGE_LETTERS_DIALOG = new ChangeLettersDialog();
	public static final CreatePlayerDialog CREATE_PLAYER_DIALOG = new CreatePlayerDialog();
	
	public static void launchScrabble() {
		Wordgame game = ScrabbleDecorator.FACTORY.gameFactory();
		
		game.init(GameTUI.class.getResource("/scrabble.wg").getPath(), ScrabbleDecorator.FACTORY);
		game.addPlayer("Max");
		game.addPlayer("Léon");
		game.addPlayer("George");
		game.addPlayer("Nicolas");
		
		game.newTurn();
		
		/*wordgameFrame = new WordgameFrame(game);
		wordgameFrame.setVisible(true);*/
	}
	
	public static void launchTopword() {
		Wordgame game = TopwordDecorator.FACTORY.gameFactory();
		
		game.init(GameTUI.class.getResource("/topword.wg").getPath(), TopwordDecorator.FACTORY);
		game.addPlayer("Max");
		game.addPlayer("Léon");
		game.addPlayer("George");
		game.addPlayer("Nicolas");
		
		game.newTurn();
		
		/*wordgameFrame = new WordgameFrame(game);
		wordgameFrame.setVisible(true);*/
	}
}
