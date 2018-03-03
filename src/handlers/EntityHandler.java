package handlers;

import java.util.ArrayList;

import entitieHandling.Entity;
import entitieHandling.Player;
import main.RPGFrame;
import terrain.Tile;

public abstract class EntityHandler {
	/**
	 * ENTITYHANDLER
	 * 
	 * This manages all of the entities in the game. -draw -tick Also give each
	 * entity a way to search for nearby entities and interact with their
	 * surroundings.
	 */

	protected ArrayList<Entity> entities;

	public EntityHandler() {
		entities = new ArrayList<Entity>();
	}

	public abstract void tick(RPGFrame frame, Player p);

	public static double getAngle(Entity a, Entity b) {

		double dx = a.getAbsX() - b.getAbsX();
		double dy = a.getAbsY() - b.getAbsY();

		if (b instanceof Player) {
			dx -= ((Player) b).getSize() / 2;
			dy -= ((Player) b).getSize() / 2;
		}
		double angle = Math.atan(dy / dx);
		if (dx < 0)
			angle += Math.PI;
		return angle;
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void addEntities(ArrayList<Entity> e) {
		if (e != null) {
			for (Entity a : e) {
				entities.add(a);
			}
		}
	}

	public abstract void playerInteract(RPGFrame frame, Player player, double r);

	// same as addEntities() but also updates the texture of each one
	// only for when tiles are being reloaded
	public void addEntitiesFromSave(RPGFrame frame, ArrayList<Entity> e) {
		if (e != null) {
			for (Entity a : e) {
				a.updateTexture(frame);
				entities.add(a);
			}
		}
	}

	public abstract void renderAll(RPGFrame frame, RenderQueue renderQueue, Player p);

	public ArrayList<Entity> getEntitiesInTile(Tile t) {
		ArrayList<Entity> e = new ArrayList<Entity>();
		for (int i = 0; i < entities.size(); i++) {
			if (!(entities.get(i) instanceof Player)) {
				if (t.inBorder(entities.get(i).getAbsX(), entities.get(i).getAbsY())) {
					e.add(entities.get(i));
					entities.remove(i);
				}
			}
		}
		return e;
	}

	public Entity getNearestEntity(Entity e) {
		Entity closest = null;
		for (int i = 0; i < entities.size(); i++) {
			if (closest == null && entities.get(i) != e) {
				closest = entities.get(i);
			} else if (entities.get(i) != e && dist(e, closest) > dist(e, entities.get(i))) {
				closest = entities.get(i);
			}
		}
		return closest;
	}

	public Entity getNearestEntity(Entity e, String name) {
		Entity closest = null;
		for (int i = 0; i < entities.size(); i++) {
			if (closest == null && entities.get(i) != e && entities.get(i).getName().equals(name)) {
				closest = entities.get(i);
			}
			if (closest != null && entities.get(i) != e && dist(e, closest) > dist(e, entities.get(i))
					&& entities.get(i).getName().equals(name)) {
				closest = entities.get(i);
			}
		}
		return closest;
	}

	public static double dist(Entity e1, Entity e2) {
		double dx = (e1.getAbsX() - e2.getAbsX()) * (e1.getAbsX() - e2.getAbsX());
		double dy = (e1.getAbsY() - e2.getAbsY()) * (e1.getAbsY() - e2.getAbsY());
		return Math.sqrt(dx + dy);
	}

	protected double angle(Entity e1, Entity e2) {
		double dx = e1.getAbsX() - e2.getAbsX();
		double dy = e1.getAbsY() - e2.getAbsY();
		double r = Math.atan2(dy, dx);
		r += Math.PI;
		return r;
	}

	protected double angleDist(double a, double b) {
		double d = Math.abs(b - a);
		double distance = d > Math.PI ? Math.PI * 2 - d : d;
		return distance;
	}

	public int getEntityCount() {
		return entities.size();
	}

}
