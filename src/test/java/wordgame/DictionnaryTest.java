package wordgame;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import wordgame.common.Dictionnary;

public class DictionnaryTest {

	private Dictionnary dico;
	
	@Before
	public void setUp() throws FileNotFoundException {
		String dicoFile = DictionnaryTest.class.getResource("/Dico.txt").getPath();
		this.dico = new Dictionnary(dicoFile);
	}
	
	@Test
	public void constructorTest() {
		assertNotNull(dico);
	}
	
	@Test
	public void containTest() {
		assertTrue(dico.contain("A"));
		assertTrue(dico.contain("EBARBASSENT"));
		assertTrue(dico.contain("ANTICONSTITUTIONNELLEMENT"));
		
		assertFalse(dico.contain(""));
		assertFalse(dico.contain(" "));
		assertFalse(dico.contain("selfie"));
		assertFalse(dico.contain("acdc"));
	}

}
