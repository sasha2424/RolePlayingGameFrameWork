package entityHandling;

import main.RPGFrame;
import player.Player;

public abstract class MovingEntity extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Boolean shouldMove = true;
	protected double moveDistance = 30;
	protected double attackRange = 40;

	protected int attackTimer;
	protected int attackDuration;

	private int timer = 0; // for deathAnimation()

	public MovingEntity(RPGFrame frame, double X, double Y, int[] x, int[] y) {
		super(frame, X, Y, x, y);
		canMove = true;
		attackTimer = 0;
		attackDuration = 50;
	}

	@Override
	public void tick(RPGFrame frame) {
		attackTimer++;
	}

	public void takeHit(RPGFrame frame, double A) {
		HP.increment(-1 * A);
		frame.getEntityHandler().summonParticle(frame, this, 0, 10, 30);
	}

	/**
	 * Returns if the current entity can attack. Also resets necessary timers.
	 * 
	 * @return true if entity can attack based on its attack duration.
	 */
	protected Boolean canAttack(double distance) {
		if (attackTimer > attackDuration && distance < attackRange) {
			attackTimer = 0;
			return true;
		}
		return false;
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
		this.takeHit(frame, player.A.getVal());
	}

	/**
	 * Override this method to have custom death animations
	 * 
	 * @param frame
	 *            game frame to draw particles in
	 * @param type
	 *            type of particle to summon
	 * @param length
	 *            duration of animation
	 * @param parts
	 *            number of segments in the animation
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
