package terrain;

import entitiesHandling.EntityHandler;
import entitiesHandling.Player;
import main.RPGFrame;

public class BasicSpawner extends Spawner {

	private int timer = 0; // timer for when mobs are spawned
	private int timerLimitMax = 1000;
	private int timerLimit = (int) (Math.random() * timerLimitMax);

	public BasicSpawner() {
	}

	@Override
	public void spawnEntities(RPGFrame frame, Player p) {
		timer++;
		if (timer > timerLimit) {
			timerLimit = (int) (Math.random() * timerLimitMax);
			timer = 0;

			// get spawn for biome at the player tile
			frame.getEntityHandler().addEntities(frame.getTerrainGenerator().getBiome(p.getBoardX(), p.getBoardY()).getSpawnSet(p,
					frame.getTileHandler().getPlayerTile(p)));
		}
	}

}
