package rendering;

import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.Collections;

import entitiesHandling.Entity;
import entitiesHandling.Player;
import main.RPGFrame;
import terrain.TerrainGenerator;
import terrain.Tile;

public class RenderQueue {

	ArrayList<Tile> toRender;

	public RenderQueue() {
		toRender = new ArrayList<Tile>();
	}

	public void clear() {
		toRender.clear();
	}

	public void addRenderable(Renderable e) {
		if (e instanceof Tile) {
			toRender.add((Tile) e);
		} else {
			for (Tile t : toRender) {
				if (t.inBorder(e.getAbsX(), e.absY)) {
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
