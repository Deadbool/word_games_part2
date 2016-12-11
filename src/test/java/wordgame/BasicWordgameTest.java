package wordgame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wordgame.common.BasicWordgame;
import wordgame.common.Coordinate;
import wordgame.common.WordgameException;
import wordgame.interfaces.Board;
import wordgame.interfaces.Direction;
import wordgame.interfaces.LetterBag;
import wordgame.interfaces.Player;
import wordgame.interfaces.Wordgame;

public class BasicWordgameTest {

	private Wordgame game;
	
	@Before
	public void setUp() {
		this.game = new BasicWordgame();
	}
	
	@Test
	public void initTest_ScrabbleConfig() {
		assertTrue(game.init(BasicWordgameTest.class.getResource("/scrabble.wg").getPath(), BasicWordgame.FACTORY));
	}
	
	@Test
	public void initTest_TopwordConfig() {
		assertTrue(game.init(BasicWordgameTest.class.getResource("/topword.wg").getPath(), BasicWordgame.FACTORY));
	}
	
	@Test
	public void playerTest() {
		game.init(BasicWordgameTest.class.getResource("/scrabble.wg").getPath(), BasicWordgame.FACTORY);
		
		assertEquals(0, game.getPlayers().size());
		assertNull(game.getCurrentPlayer());
		
		assertTrue(game.addPlayer("Max"));
		assertTrue(game.addPlayer("Léon"));
		assertFalse(game.addPlayer("Max"));
		
		assertNotNull(game.getCurrentPlayer());
		assertEquals(2, game.getPlayers().size());
	}
	
	@Test
	public void boardTest_Scrabble() {
		game.init(BasicWordgameTest.class.getResource("/scrabble.wg").getPath(), BasicWordgame.FACTORY);
		assertNotNull(game.getBoard());
		assertEquals(15, game.getBoard().getHeight());
		assertEquals(game.getBoard().getWidth(), game.getBoard().getHeight());
	}
	
	@Test
	public void boardTest_Topword() {
		game.init(BasicWordgameTest.class.getResource("/topword.wg").getPath(), BasicWordgame.FACTORY);
		assertNotNull(game.getBoard());
		assertEquals(10, game.getBoard().getHeight());
		assertEquals(game.getBoard().getWidth(), game.getBoard().getHeight());
	}
	
	@Test
	public void gameNotOverTest() {
		game.init(BasicWordgameTest.class.getResource("/topword.wg").getPath(), BasicWordgame.FACTORY);
		game.addPlayer("Max");
		assertFalse(game.isOver());
	}
	
	@Test
	public void gameOverTest1() throws WordgameException {
		game.init(BasicWordgameTest.class.getResource("/topword.wg").getPath(), BasicWordgame.FACTORY);
		game.addPlayer("Max");
		game.addPlayer("Léon");
		
		assertFalse(game.isOver());
		
		LetterBag bag = game.getLetterBag();
		while( bag.size() > 0) {
			bag.pick();
		}
		List<Player> p = game.getPlayers();
		List<Character> lettersInRack = new ArrayList<Character>(p.get(0).getRack().getContent());
		for(Character c : lettersInRack) {
			p.get(0).getRack().pickLetter(c);
		}
		
		assertTrue(game.isOver());
	}
	
	@Test
	public void getWordFormedByMoveTest() throws WordgameException {
		game.init(BasicWordgameTest.class.getResource("/topword.wg").getPath(), BasicWordgame.FACTORY);
		Board b = this.game.getBoard();
		
		b.getCell(new Coordinate('A', 1)).setContent('M');
		b.getCell(new Coordinate('A', 2)).setContent('A');
		b.getCell(new Coordinate('A', 3)).setContent('N');
		b.getCell(new Coordinate('A', 4)).setContent('G');
		b.getCell(new Coordinate('A', 5)).setContent('E');
		b.getCell(new Coordinate('A', 6)).setContent('R');
		
		b.getCell(new Coordinate('E', 1)).setContent('D');
		b.getCell(new Coordinate('E', 2)).setContent('A');
		b.getCell(new Coordinate('E', 3)).setContent('R');
		b.getCell(new Coordinate('E', 4)).setContent('D');
		
		List<String> formed = game.getWordFormedByMove(new Coordinate('A', 4), Direction.LINE, "GRAND");
		assertEquals(2, formed.size());
		assertEquals("MANGER", formed.get(0));
		assertEquals("DARD", formed.get(1));
	}
}
