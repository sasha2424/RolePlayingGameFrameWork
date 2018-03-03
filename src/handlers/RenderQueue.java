package handlers;

import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.Collections;

import entitieStructure.Entity;
import entitieStructure.Player;
import main.RPGFrame;
import rendering.Renderable;
import terrain.Tile;

public class RenderQueue {
	
	/**
	 * RENDERQUEUE
	 * 
	 * Temporarily stores Renderable objects.
	 * Once the RenderAll() method is called objects are sorted based on
	 * the order in which they should be rendered and are displayed unto the screen.
	 * 
	 * The RenderQueue needs to be externally cleared after rendering.
	 * 
	 */

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
