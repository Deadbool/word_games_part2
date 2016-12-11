package wordgame;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import wordgame.common.BasicLetterBag;
import wordgame.common.WordgameException;

public class BasicLetterBagTest {
	
	private BasicLetterBag bag;
	
	@Before
	public void setUp() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		String configFile = BasicLetterBagTest.class.getResource("/scrabble.wg").getPath();
		final JsonParser parser = new JsonParser();
		final JsonObject config = parser.parse(new FileReader(configFile)).getAsJsonObject();
		JsonArray alphabetConf = config.get("letters").getAsJsonArray();
		
		bag = new BasicLetterBag(alphabetConf);
	}

	@Test
	public void constructorTest() {
		assertNotNull(bag);
	}
	
	@Test
	public void getSizeTest() {
		assertEquals(102, bag.size());
	}
	
	@Test
	public void getPointForTest() {
		assertEquals(1, bag.pointFor('A'));
		assertEquals(10, bag.pointFor('K'));
		assertEquals(8, bag.pointFor('Q'));
		assertEquals(0, bag.pointFor('?'));
	}
	
	@Test
	public void pickOneTest() throws WordgameException {
		assertEquals(102, bag.size());
		bag.pick();
		assertEquals(101, bag.size());
	}
	
	@Test
	public void pickAllTest() throws WordgameException{
		for(int i = 0; i < 102; i++) {
			bag.pick();
		}
		assertEquals(0, bag.size());
	}
	
	@Test(expected=WordgameException.class)
	public void noMoreLetterPickTest() throws WordgameException {
		for(int i = 0; i <= 102; i++) {
			bag.pick();
		}
	}
	
	@Test
	public void exchangeTest() throws WordgameException {
		assertEquals(102, bag.size());
		char picked = bag.pick();
		assertEquals(101, bag.size());
		bag.exchange(picked);
		assertEquals(101, bag.size());
	}
	
	@Test(expected=WordgameException.class)
	public void noMoreLetterExchangeTest() throws WordgameException {
		for(int i = 0; i < 95; i++) {
			bag.pick();
		}
		bag.exchange('A');
		bag.pick();
		bag.exchange('A');
	}

}
