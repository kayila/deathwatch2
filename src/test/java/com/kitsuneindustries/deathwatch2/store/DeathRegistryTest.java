package com.kitsuneindustries.deathwatch2.store;

import java.util.Calendar;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.kitsuneindustries.deathwatch2.data.PlayerDeath;

@Test
public class DeathRegistryTest {
	
	private static final Random random = new Random();
	private DeathRegistry deathStore;
	
	@BeforeMethod
	public void setup() {
		// This should blow away the DB between classes
		PersistanceHelper.setup("mem:");
		deathStore = new DeathRegistry();
	}
	
	@Test
	public void testStoreAndRetrieve() {
		setup();
		PlayerDeath expected = createDeath();
		deathStore.persist(expected);
		Optional<PlayerDeath> actual = deathStore.find(expected.getId());
		
		assertTrue(actual.isPresent());
		assertEquals(actual.get(), expected);
	}
	
	@Test
	public void testFindLastDeathByName() {
		Calendar cal = Calendar.getInstance();
		PlayerDeath first = createDeath();
		PlayerDeath last = createDeath();
		first.setTimestamp(cal.getTime());
		cal.add(Calendar.DATE, 1);
		last.setTimestamp(cal.getTime());
		deathStore.persist(last);
		deathStore.persist(first);
		Optional<PlayerDeath> actual = deathStore.findLastDeathByName(first.getPlayerName());
		
		assertTrue(actual.isPresent());
		assertNotEquals(last, first);
		assertEquals(actual.get(), last);
	}
	
	@Test
	public void testEquals() {
		PlayerDeath death = createDeath();
		PlayerDeath death2 = new PlayerDeath();
		death2.setId(death.getId());
		death2.setTimestamp(death.getTimestamp());
		death2.setPlayerName(death.getPlayerName());
		death2.setPlayerUUID(death.getPlayerUUID());
		death2.setDimension(death.getDimension());
		death2.setX(death.getX());
		death2.setY(death.getY());
		death2.setZ(death.getZ());
		death2.setKiller(death.getKiller());
		death2.setType(death.getType());
		death2.setMessage(death.getMessage());
		
		assertEquals(death, death);
		assertEquals(death2, death);
	}
	
	private PlayerDeath createDeath() {
		PlayerDeath death = new PlayerDeath();
		death.setPlayerName("The Doctor");
		death.setPlayerUUID(UUID.randomUUID());
		death.setDimension(random.nextInt());
		death.setX(random.nextDouble());
		death.setY(random.nextDouble());
		death.setZ(random.nextDouble());
		death.setKiller("Missy");
		death.setMessage("Never more");
		return death;
		
	}
	
}
