package handlers;

import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.Collections;

import entityHandling.Entity;
import entityHandling.Player;
import main.RPGFrame;
import rendering.Renderable;
import terrain.Tile;

/**
 * RENDERQUEUE
 * 
 * Temporarily stores Renderable objects. Once the RenderAll() method is called
 * objects are sorted based on the order in which they should be rendered and
 * are displayed unto the screen;
 * 
 * The RenderQueue needs to be externally cleared after rendering;
 * 
 */

public abstract class RenderQueue {

	protected ArrayList<Tile> toRender;

	public RenderQueue() {
		toRender = new ArrayList<Tile>();
	}

	public void clear() {
		toRender.clear();
	}

	public abstract void addRenderable(Renderable e);

	public abstract void renderAll(RPGFrame w, Graphics2D g, Player player, double rotation, double height);

}
