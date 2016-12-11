package wordgame;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import wordgame.common.Coordinate;
import wordgame.common.SquareBoard;
import wordgame.common.WordgameException;

public class SquareBoardTest {
	
	SquareBoard board;
	
	@Before
	public void setUp() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		final JsonParser parser = new JsonParser();
		
		String configFile = SquareBoardTest.class.getResource("/scrabble.wg").getPath();
		final JsonObject config = parser.parse(new FileReader(configFile)).getAsJsonObject();
		JsonObject layout = config.get("board").getAsJsonObject();
		
		board = new SquareBoard(layout.get("size").getAsInt());
	}

	@Test
	public void constructorTest() throws WordgameException {
		assertEquals(15, board.getHeight());
		assertEquals(15, board.getWidth());
		
		assertNull(board.getCell(new Coordinate('A', 1)));
		assertNull(board.getCell(new Coordinate('A', 15)));
		assertNull(board.getCell(new Coordinate('O', 1)));
		assertNull(board.getCell(new Coordinate('O', 15)));
	}
	
	@Test
	public void validCoordTest() {
		assertTrue(board.validCoord(new Coordinate('A', 1)));
		assertTrue(board.validCoord(new Coordinate('A', 15)));
		assertTrue(board.validCoord(new Coordinate('O', 1)));
		assertTrue(board.validCoord(new Coordinate('O', 15)));
		
		assertFalse(board.validCoord(new Coordinate('A', 16)));
		assertFalse(board.validCoord(new Coordinate('X', 1)));
		assertFalse(board.validCoord(new Coordinate('X', 16)));
	}
	
	@Test(expected=WordgameException.class)
	public void getCellOutOfBound() throws WordgameException {
		board.getCell(new Coordinate('Z', 20));
	}

}
