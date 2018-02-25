package terrain;

import entitiesHandling.EntityHandler;
import entitiesHandling.Player;
import main.RPGFrame;

public abstract class Spawner {
	//Spawns entities in the world based on the loaded tiles
	//Entities that are spawned are determined by the biomes that run the spawn method
	
	public abstract void spawnEntities(RPGFrame frame, Player p);

}
