package entitiesHandling;

import java.awt.Graphics2D;

import items.Item;
import main.RPGFrame;
import terrain.TerrainGenerator;
import terrain.Tile;

public class ItemDrop extends Entity {

	private Item item;
	private double bounce = 0;
	private double bounceV = -5;
	private double bounceA = .05;

	public static final double PICKUP_DISTANCE = 20;

	public ItemDrop(double X, double Y, Item item) {
		super(X, Y);
		this.item = item;
		name = "drop";
	}

	@Override
	public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {
		double x = (double) (getStandardRenderX(absX - player.getX(), absY - player.getY(), rotation,
				w.getCurrentWidth() / 2));
		double y = (double) (getStandardRenderY(absX - player.getX(), absY - player.getY(), rotation,
				w.getCurrentHeight() / 2));

		double deltaH = getHeightDifference(w, height);

		item.drawOnGround(g, (int) (x - size / 2), (int) (y - size / 2 + deltaH));

	}

	@Override
	public void tick(RPGFrame frame) {
		bounce += bounceV;
		bounceV += bounceA;
		if (bounce > 0) {
			bounce = 0;
			bounceA = -bounceA;
		}

	}

	@Override
	public void interactPlayer(RPGFrame frame, Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nearPlayer(RPGFrame frame, Player p) {
		if (EntityHandler.dist(p, this) < PICKUP_DISTANCE) {
			if (p.inventory.addItem(item))
				this.hasDied = true;
		}

	}

	@Override
	public void deathEvent(RPGFrame frame, Player p) {
		// TODO Auto-generated method stub

	}

}
