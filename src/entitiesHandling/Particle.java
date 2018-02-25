package entitiesHandling;

import java.awt.Graphics2D;

import main.RPGFrame;
import terrain.TerrainGenerator;
import terrain.Tile;

public class Particle extends StationaryEntity {
	private int t = 0; // tick counter for duration
	private int animation = 0;
	private static final int TICKS_PER_TEXTURE = 5;
	private int duration;

	public Particle(double X, double Y, int type, int duration) {
		super(X, Y);
		if (type == 0) {
			this.textureX = new int[] { 0, 1, 2 };
			this.textureY = new int[] { 2, 2, 2 };
		}
		this.updateTexture();
		name = "tree";
		this.duration = duration;
	}

	public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {

		double x = (double) (getX(absX - player.getX(), absY - player.getY(), rotation, w.getCurrentWidth() / 2));
		double y = (double) (getY(absX - player.getX(), absY - player.getY(), rotation, w.getCurrentHeight() / 2));

		int k = 40;
		g.drawImage(texture[(int) (animation / TICKS_PER_TEXTURE)], (int) (x - k / 2), (int) (y + height - k), k, k,
				null);

		animation++;
		if (animation >= texture.length * TICKS_PER_TEXTURE) {
			animation = 0;
		}
	}

	@Override
	public void tick(EntityHandler e) {
		t++;
		if (t > duration) {
			this.hasDied = true;
		}
	}

}