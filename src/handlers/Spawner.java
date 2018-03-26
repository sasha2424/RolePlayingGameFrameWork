package handlers;

import main.RPGFrame;
import player.Player;

/**
 * SPAWNER
 * 
 * Spawns entities in the world based on the loaded tiles Entities that are
 * spawned are determined by the biomes that run the spawn method
 */

public abstract class Spawner {

	public abstract void spawnEntities(RPGFrame frame, Player p);

}
