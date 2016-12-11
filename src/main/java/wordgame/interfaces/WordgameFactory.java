package wordgame.interfaces;

public interface WordgameFactory {
	Wordgame gameFactory();
	Board boardFactory(int size);
	Cell cellFactory(char content);
}
