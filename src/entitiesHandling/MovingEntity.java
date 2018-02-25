package entitiesHandling;

import java.awt.Graphics;
import java.awt.Graphics2D;

import main.RPGFrame;
import processing.core.PApplet;
import terrain.TerrainGenerator;
import terrain.Tile;

public abstract class MovingEntity extends Entity {

	protected Boolean shouldMove = true;
	protected double moveDistance = 50;

	private int timer = 0; // for deathAnimation()

	public MovingEntity(double X, double Y, int[] x, int[] y) {
		super(X, Y, x, y);
		canMove = true;

	}

	protected void move(double distance) {

		if (distance < moveDistance) {
			velX = 0;
			velY = 0;
			this.shouldMove = false;
		} else {
			this.shouldMove = true;
		}
		absX += velX;
		absY += velY;
	}

	public void interactPlayer(EntityHandler e, Player player) {
		this.HP.increment(-1 * player.A.getVal());
		this.summonParticle(e, 0, 10, 30);
	}

	public void nearPlayer(EntityHandler e, Player p) {
		// TODO maybe have some sort of particles for some things
	}

	protected void summonParticle(EntityHandler e, int type, double range, int duration) {
		double randX = Math.random() * range * 2 - range;
		double randY = Math.random() * range * 2 - range;
		e.addEntity(new Particle(this.getAbsX() + randX, this.getAbsY() + randY, type, duration));
	}

	protected void deathAnimation(EntityHandler e, int type, int length, int parts) {
		int d = (int) (length / parts);
		if (timer % d == 0) {
			e.addEntity(new Particle(this.getAbsX(), this.getAbsY(), type, d));
		}
		timer++;
		if (timer > length) {
			this.hasDied = true;
		}
	}

}
