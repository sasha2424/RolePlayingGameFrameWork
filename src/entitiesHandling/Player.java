package entitiesHandling;

import java.awt.Graphics2D;
import java.io.Serializable;

import main.DoubleStat;
import main.RPGFrame;
import main.Variables;
import player.Inventory;
import terrain.TerrainGenerator;
import terrain.Tile;

public class Player extends MovingEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final double speed = 10;
	public static final double interactDistance = 80;
	public static final double interectAngle = Math.PI / 8;
	public static final int attackDelay = 10;
	private int attackTickCount = 0;
	public Inventory inventory;

	private double rotation;

	public Player(RPGFrame frame, int X, int Y, int[] x, int[] y) {
		super(frame, X, Y, x, y);
		this.name = "player";
		inventory = new Inventory();
		size = 30;
		collisionRange = 30;
		rotation = 0;
		this.A = new DoubleStat(100, 100);
	}

	public String toString() {
		return rotation + "   " + speed;
	}

	public boolean canAttack() {
		return attackTickCount > attackDelay;
	}

	public void resetAttackCounter() {
		attackTickCount = 0;
	}

	public double getX() {
		return absX;
	}

	public double getY() {
		return absY;
	}

	public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {
		g.translate(w.getCurrentWidth() / 2, w.getCurrentHeight() / 2);
		g.rotate(this.rotation + Math.PI / 2); // Additional rotation bc of
												// texture2
		g.drawImage(texture[0], (int) (-size / 2), (int) (-size / 2), size, size, null);
		g.rotate(-this.rotation - Math.PI / 2);
		g.translate(-w.getCurrentWidth() / 2, -w.getCurrentHeight() / 2);

		int cx = (int) (w.getCurrentWidth() / 2);
		int cy = (int) (w.getCurrentHeight() / 2);

		g.drawLine(cx, cy, cx + (int) (interactDistance * Math.cos(this.rotation)),
				cy + (int) (interactDistance * Math.sin(this.rotation)));

		if (true) { // TODO DEBUG needs to be setup
			double r = interectAngle;
			g.drawLine(cx, cy, cx + (int) (interactDistance * Math.cos(this.rotation + r)),
					cy + (int) (interactDistance * Math.sin(this.rotation + r)));
			g.drawLine(cx, cy, cx + (int) (interactDistance * Math.cos(this.rotation - r)),
					cy + (int) (interactDistance * Math.sin(this.rotation - r)));
		}

	}

	public void move(double rotation) {
		incrementX(speed * Math.cos(rotation));
		incrementY(speed * Math.sin(rotation));
	}

	public void incrementX(double dx) {
		absX += dx;
	}

	public void incrementY(double dy) {
		absY += dy;
	}

	@Override
	public void tick(RPGFrame frame) {
		attackTickCount++;
		// TODO stuff like burns and poison and effects

	}

	public void deathEvent(EntityHandler e, Player player) {
		// TODO clear inventory
	}

	public int getSize() {
		return size;
	}

	public void setRotation(double r) {
		rotation = r;
	}

	@Override
	public void nearPlayer(RPGFrame frame, Player p) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deathEvent(RPGFrame frame, Player p) {
		// TODO Auto-generated method stub
	}
}
