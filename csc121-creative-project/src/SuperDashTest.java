import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import processing.event.*;

class SuperDashTest {

	Player tp1 = new Player(new Posn(50, 200));

	Obstacle cw0 = new Obstacle(new Posn(10, 10));
	Obstacle cw1 = new Obstacle(new Posn(100, 150));
	Obstacle cw2 = new Obstacle(new Posn(200, 150));
	Obstacle cw3 = new Obstacle(new Posn(200, 250));
	Obstacle cw4 = new Obstacle(new Posn(300, 100));
	Obstacle cw5 = new Obstacle(new Posn(100, 250));

	SuperDashWorld world = new SuperDashWorld(new Player(new Posn(50, 200)), new MTLoO(), 100);
	
	ILoO mtloo = new MTLoO();
	ILoO oL1 = new ConsLoO(this.cw1, new ConsLoO(this.cw2, new ConsLoO(this.cw3, mtloo)));

	Posn posn1 = new Posn(50, 100);
	Posn posn2 = new Posn(150, 200);
	Posn posn3 = new Posn(30, 60);
	float distance = posn1.distanceTo(posn2);
	Posn posn = new Posn(50, 100);
	Posn scaledPosn = posn.scale(2);
	Posn offset = new Posn(30, 40);
	Posn translatedPosn = posn1.translate(offset);
	Posn diff = posn3.diff(posn1);

	@Test
	void testUpdateObstacle() {        
		assertEquals(new Obstacle(new Posn(99.0f, 150)), cw1.update());  
	}

	@Test
	void testUpdateILoO() {
		ILoO updatedList = oL1.update();
	}

	@Test
	void testRemoveEscaped() {
		ILoO updatedList = oL1.removeEscaped();
	}

	@Test
	void testAnyCollided() {
		assertFalse(oL1.anyCollided(new Posn(400, 200)));
		assertTrue(cw0.anyCollide(new Posn(15, 15)));
	}

	@Test
	void testGetPlayerLoc() {
		assertEquals(new Posn(50, 200), tp1.getLoc());
	}

	@Test
	void testDistanceTo() {
		assertEquals(141.42136, distance, 0.00001);
	}

	@Test
	void testScale() {
		assertEquals(new Posn(100, 200), scaledPosn);
	}

	@Test
	void testTranslate() {
		assertEquals(new Posn(80, 140), translatedPosn);
	}

	@Test
	void testDiff() {
		assertEquals(new Posn(20, 40), diff);
	}

	@Test
	public void testScore() {
		assertEquals(3, world.score());
	}
}
    
