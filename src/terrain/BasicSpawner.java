package terrain;

import entitieHandling.Player;
import handlers.EntityHandler;
import handlers.Spawner;
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

			// add spawn from the players tile to the entityHandler
			frame.getEntityHandler().addEntities(frame.getTerrainGenerator().getBiome(p.getBoardX(), p.getBoardY())
					.getSpawnSet(frame, p, frame.getTileHandler().getPlayerTile(p)));
		}
	}

}
