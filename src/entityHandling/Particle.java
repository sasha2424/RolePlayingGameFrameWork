package entityHandling;

import java.awt.Graphics2D;

import handlers.TerrainGenerator;
import main.RPGFrame;
import terrain.Tile;

public class Particle extends StationaryEntity {
	private int t = 0; // tick counter for duration
	private int animation = 0;
	private static final int TICKS_PER_TEXTURE = 5;
	private int duration;

	public Particle(RPGFrame frame, double X, double Y, int type, int duration) {
		super(X, Y);
		// TODO make seperate particle classes
		if (type == 0) {
			this.textureX = new int[] { 0, 1, 2 };
			this.textureY = new int[] { 2, 2, 2 };
		}
		this.updateTexture(frame);
		name = "tree";
		this.duration = duration;
	}

	public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {

		double x = (double) (getStandardRenderX(absX - player.getX(), absY - player.getY(), rotation,
				w.getCurrentWidth() / 2));
		double y = (double) (getStandardRenderY(absX - player.getX(), absY - player.getY(), rotation,
				w.getCurrentHeight() / 2));

		double deltaH = getHeightDifference(w, height);
		int k = 40;
		g.drawImage(texture[(int) (animation / TICKS_PER_TEXTURE)], (int) (x - k / 2), (int) (y + deltaH - k), k, k,
				null);

		animation++;
		if (animation >= texture.length * TICKS_PER_TEXTURE) {
			animation = 0;
		}
	}

	@Override
	public void tick(RPGFrame frame) {
		t++;
		if (t > duration) {
			this.hasDied = true;
		}
	}

	@Override
	public void nearPlayer(RPGFrame frame, Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deathEvent(RPGFrame frame, Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeHit(RPGFrame frame, double A) {
		// TODO Auto-generated method stub

	}

}