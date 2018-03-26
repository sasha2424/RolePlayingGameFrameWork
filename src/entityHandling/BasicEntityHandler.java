package entityHandling;

import handlers.EntityHandler;
import handlers.RenderQueue;
import main.RPGFrame;
import player.Player;

public class BasicEntityHandler extends EntityHandler {

	public void renderAll(RPGFrame frame, RenderQueue renderQueue, Player p) {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (dist(p, e) < frame.RENDER_DISTANCE_ENTITY) {
				renderQueue.addRenderable(e);
			}
		}
	}

	public void tick(RPGFrame frame, Player p) {
		if (entities != null) {
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				if (!e.isDead()) {
					e.tick(frame);

					if (!(e instanceof Player) && dist(e, p) < p.interactDistance * 3) {
						e.nearPlayer(frame, p);
					}

				} else {
					e.deathEvent(frame, p);
					entities.remove(e);
				}
			}
		}
	}
	
	public void playerInteract(RPGFrame frame, Player player, double r) {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e != player && dist(e, player) <= player.interactDistance) {
				double a = angle(player, e);
				if (angleDist(a, r) < player.interectAngle) {
					e.interactPlayer(frame, player);
				}
			}
		}
	}

}
