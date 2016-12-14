package wordgame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import wordgame.abstraction.common.BasicRack;
import wordgame.abstraction.common.WordgameException;
import wordgame.abstraction.interfaces.Rack;

public class BasicRackTest {
	
	Rack rack;
	
	@Before
	public void setUp() {
		rack = new BasicRack();
	}
	
	@Test
	public void constructorTest() {
		assertNotNull(rack);
		assertEquals(0, rack.size());
	}
	
	@Test
	public void addLetterTest() {
		rack.addLetter('A');
		assertEquals(1, rack.size());
		rack.addLetter('B');
		rack.addLetter('C');
		assertEquals(3, rack.size());
	}
	
	@Test
	public void asLettersToFormTest() {
		Character[] onBoard = new Character[6];
		onBoard[2] = 'n';
		onBoard[4] = 'e';
		rack.addLetter('m');
		rack.addLetter('a');
		rack.addLetter('g');
		rack.addLetter('r');
		rack.addLetter('a');
		rack.addLetter('v');
		rack.addLetter('i');
		assertFalse(rack.asLettersToForm("annette", onBoard));
		assertFalse(rack.asLettersToForm("mabger", onBoard));
		assertFalse(rack.asLettersToForm("banger", onBoard));
		assertTrue(rack.asLettersToForm("manger", onBoard));
	}

	@Test
	public void pickLetterTest() throws WordgameException {
		rack.addLetter('m');
		rack.addLetter('a');
		char picked = rack.pickLetter('m');
		assertEquals('m', picked);
	}
	
	@Test(expected=WordgameException.class)
	public void pickLetterExceptionTest() throws WordgameException {
		rack.pickLetter('a');
	}
}
