package wordgame;

import java.util.ArrayList;

import wordgame.abstraction.GameType;
import wordgame.control.WindowManager;

public class GameIHM {
	public static void main(String[] args) {
		
		//WindowManager.CREATE_GAME_FRAME.launch();
		
		ArrayList<String> players = new ArrayList<String>();
		players.add("Alpha");
		players.add("Beta");
		players.add("Gama");
		players.add("Omega");
		WindowManager.WORDGAME_FRAME.launch(GameType.TOPWORD, players);
		
	}
}
