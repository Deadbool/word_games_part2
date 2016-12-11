package wordgame.common;

public class WordgameException extends Exception {

	private static final long serialVersionUID = 1L;

	public WordgameException(String msg) {
		super("Wordgame error : " + msg);
	}
}
