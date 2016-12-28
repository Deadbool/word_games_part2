package wordgame.control;

import wordgame.presentation.dialogs.ChangeLettersDialog;
import wordgame.presentation.dialogs.CreatePlayerDialog;
import wordgame.presentation.frames.CreateGameFrame;
import wordgame.presentation.frames.WordgameFrame;

public class WindowManager {
	
	// Frames
	public static final WordgameFrame WORDGAME_FRAME = new WordgameFrame();
	public static final CreateGameFrame CREATE_GAME_FRAME = new CreateGameFrame();
	
	// Dialogs
	public static final ChangeLettersDialog CHANGE_LETTERS_DIALOG = new ChangeLettersDialog();
	public static final CreatePlayerDialog CREATE_PLAYER_DIALOG = new CreatePlayerDialog();
}
