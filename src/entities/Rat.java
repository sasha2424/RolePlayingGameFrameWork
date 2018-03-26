package entities;

import java.awt.Graphics2D;

import entityHandling.Entity;
import entityHandling.ItemDrop;
import entityHandling.MovingEntity;
import handlers.EntityHandler;
import items.Fur;
import main.DoubleStat;
import main.RPGFrame;
import player.Player;

public class Rat extends MovingEntity {

	private double angleCounter;
	private double angleV;
	private double angleMax = Math.PI / 8;

	private int timer = 0;

	public Rat(RPGFrame frame, double X, double Y) {
		super(frame, X, Y, new int[] { 5, 6 }, new int[] { 0, 0 });
		speed = new DoubleStat(3, 3);
		HP = new DoubleStat(5, 5);
		A = new DoubleStat(1,1);
		angleCounter = 0;
		angleV = .02;
		name = "rat";
		size = 30;
		collisionRange = 20;

	}

	@Override
	public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {
		// TODO draw graphics in super class method (because all the same)

		// gets the X and Y coordinates on the screen where the entity should be
		// rendered
		double x = (double) (getStandardRenderX(absX - player.getX(), absY - player.getY(), rotation,
				w.getCurrentWidth() / 2));
		double y = (double) (getStandardRenderY(absX - player.getX(), absY - player.getY(), rotation,
				w.getCurrentHeight() / 2));

		// gets the angle to the player
		double angle = EntityHandler.getAngle(this, player);

		// gets the difference in height between the player and entity
		// this is used to make sure the entity appears on the right tile
		double deltaH = this.getHeightDifference(w, height);

		// translate and rotate based on location and board rotation
		g.translate(x, y + deltaH);
		g.rotate(angle + rotation - Math.PI / 2);

		// draw the rat body
		g.drawImage(texture[0], -size / 2, -size / 2, size, size, null);

		// rotate the tail based on time (angleCounter) and draw
		g.rotate(angleCounter);
		g.drawImage(texture[1], -size / 2, +size / 2, size, size, null);
		g.rotate(-angleCounter);

		// undo rotations
		g.rotate(-angle - rotation + Math.PI / 2);
		g.translate(-x, -y - deltaH);

		// adjust rotation of tail
		angleCounter += angleV;
		if (angleCounter > angleMax || angleCounter < -angleMax) {
			angleV = -angleV;
		}
	}

	@Override
	public void tick(RPGFrame frame) {
		super.tick(frame);
		if (HP.getVal() > 0) {

			Entity nearest = frame.getEntityHandler().getNearestEntity(this, "player");
			if (nearest != null) {
				double dx = nearest.getAbsX() - this.getAbsX();
				double dy = nearest.getAbsY() - this.getAbsY();
				double d = Math.sqrt(dx * dx + dy * dy);
				if (this.canAttack(d)) {
					nearest.takeHit(frame, A.getVal());
				}
				velX = this.speed.getVal() * dx / d;
				velY = this.speed.getVal() * dy / d;
				super.move(d);
			}

		} else if (HP.getVal() <= 0) {
			super.deathAnimation(frame, 0, 30, 3);
		}
	}

	public void deathEvent(RPGFrame frame, Player player) {
		if (Math.random() < 1)
			frame.getEntityHandler().addEntity(new ItemDrop(absX, absY, new Fur(frame)));
	}

	@Override
	public void nearPlayer(RPGFrame frame, Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeHit(RPGFrame frame, double A) {
		super.takeHit(frame, A);
	}

}
