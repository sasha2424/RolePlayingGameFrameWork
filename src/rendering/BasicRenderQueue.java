package rendering;

import java.awt.Graphics2D;
import java.util.Collections;

import entitieHandling.Player;
import handlers.RenderQueue;
import main.RPGFrame;
import terrain.Tile;

public class BasicRenderQueue extends RenderQueue {

	public void addRenderable(Renderable e) {
		if (e instanceof Tile) {
			toRender.add((Tile) e);
		} else {
			for (Tile t : toRender) {
				if (t.inBorder(e.getAbsX(), e.getAbsY())) {
					t.addRenderable(e);
				}
			}
		}

	}

	public void renderAll(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {
		Collections.sort(toRender);
		for (Tile t : toRender) {
			t.draw(w, g, player, rotation, height);
			if (t.getContainedEntities() != null) {
				for (Renderable e : t.getContainedEntities()) {
					e.draw(w, g, player, rotation, height);
				}
			}
			t.clearContained();
		}
	}

}
