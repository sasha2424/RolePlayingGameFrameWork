package handlers;

import entityHandling.Player;
import main.RPGFrame;

/**
 * SPAWNER
 * 
 * Spawns entities in the world based on the loaded tiles Entities that are
 * spawned are determined by the biomes that run the spawn method
 */

public abstract class Spawner {

	public abstract void spawnEntities(RPGFrame frame, Player p);

}
