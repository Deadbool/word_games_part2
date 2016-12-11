package wordgame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import wordgame.common.Coordinate;

public class CoordinateTest {
	
	Coordinate coord;
	
	@Before
	public void setUp() {
		coord = new Coordinate('A', 1);
	}

	@Test
	public void constructorTest() {
		assertNotNull(coord);
		assertEquals('A', coord.x);
		assertEquals(1, coord.y);
	}
	
	@Test
	public void incTest() {
		Coordinate c = coord.incX();
		assertEquals('B', c.x);
		assertEquals(1, c.y);
		c = c.incY();
		assertEquals('B', c.x);
		assertEquals(2, c.y);
	}

}
