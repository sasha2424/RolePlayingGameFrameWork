package entitiesHandling;

import java.util.ArrayList;

import main.RPGFrame;
import rendering.RenderQueue;
import terrain.Tile;

public class EntityHandler {

	private ArrayList<Entity> entities;

	public EntityHandler() {
		entities = new ArrayList<Entity>();
	}

	public void tick(RPGFrame frame, Player p) {
		if (entities != null) {
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				if (!e.isDead()) {
					e.tick(frame);

					if (!(e instanceof Player) && dist(e, p) < Player.interactDistance * 3) {
						e.nearPlayer(frame, p);
					}

				} else {
					e.deathEvent(frame, p);
					entities.remove(e);
				}
			}
		}
	}

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

	public void playerInteract(RPGFrame frame, Player player, double r) {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e != player && dist(e, player) <= Player.interactDistance) {
				double a = angle(player, e);
				if (angleDist(a, r) < player.interectAngle) {
					e.interactPlayer(frame, player);
				}
			}
		}
	}

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

	public void renderAll(RPGFrame frame, RenderQueue renderQueue, Player p) {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (dist(p, e) < frame.RENDER_DISTANCE_ENTITY) {
				renderQueue.addRenderable(e);
			}
		}
	}

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

	public Entity randomEntity() { // FIXME get list of all Entities and use
									// those
		return null;
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

	private double angle(Entity e1, Entity e2) {
		double dx = e1.getAbsX() - e2.getAbsX();
		double dy = e1.getAbsY() - e2.getAbsY();
		double r = Math.atan2(dy, dx);
		r += Math.PI;
		return r;
	}

	private double angleDist(double a, double b) {
		double d = Math.abs(b - a);
		double distance = d > Math.PI ? Math.PI * 2 - d : d;
		return distance;
	}

	public int getEntityCount() {
		return entities.size();
	}

}
