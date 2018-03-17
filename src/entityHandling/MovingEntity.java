package entityHandling;

import main.RPGFrame;

public abstract class MovingEntity extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Boolean shouldMove = true;
	protected double moveDistance = 50;

	private int timer = 0; // for deathAnimation()

	public MovingEntity(RPGFrame frame, double X, double Y, int[] x, int[] y) {
		super(frame, X, Y, x, y);
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

	public void interactPlayer(RPGFrame frame, Player player) {
		this.HP.increment(-1 * player.A.getVal());
		this.summonParticle(frame, 0, 10, 30);
	}

	protected void summonParticle(RPGFrame frame, int type, double range, int duration) {
		double randX = Math.random() * range * 2 - range;
		double randY = Math.random() * range * 2 - range;
		frame.getEntityHandler()
				.addEntity(new Particle(frame, this.getAbsX() + randX, this.getAbsY() + randY, type, duration));
	}

	/**
	 * Override this method to have custom death animations
	 * @param frame game frame to draw particles in
	 * @param type type of particle to summon
	 * @param length duration of animation
	 * @param parts number of segments in the animation
	 */
	protected void deathAnimation(RPGFrame frame, int type, int length, int parts) {
		int d = (int) (length / parts);
		if (timer % d == 0) {
			frame.getEntityHandler().addEntity(new Particle(frame, this.getAbsX(), this.getAbsY(), type, d));
		}
		timer++;
		if (timer > length) {
			this.hasDied = true;
		}
	}

}
