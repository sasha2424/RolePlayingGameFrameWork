package entities;

import java.awt.Graphics2D;

import entityHandling.Entity;
import entityHandling.ItemDrop;
import entityHandling.MovingEntity;
import handlers.EntityHandler;
import items.Bread;
import items.Fur;
import main.DoubleStat;
import main.RPGFrame;
import player.Player;

/**
 * 
 * Example Moving Entity class with no complex rendering.
 * 
 * @author Alexander Ivanov
 * @version 2018.03.15
 *
 */

public class Walker extends MovingEntity {

	private static final long serialVersionUID = 1L;

	public Walker(RPGFrame frame, double X, double Y) {
		super(frame, X, Y, new int[] { 1 }, new int[] { 0 });
		speed = new DoubleStat(1, 1);
		name = "walker";
		HP = new DoubleStat(20, 20);
		size = 40;
		collisionRange = 40;

	}

	@Override
	public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {
		super.standardDraw(w, g, player, rotation, height, 0);
	}

	@Override
	public void tick(RPGFrame frame) {

		if (HP.getVal() > 0) {
			Entity nearest = frame.getEntityHandler().getNearestEntity(this, "player");
			if (nearest != null) {
				double dx = nearest.getAbsX() - this.getAbsX();
				double dy = nearest.getAbsY() - this.getAbsY();
				double d = Math.sqrt(dx * dx + dy * dy);
				velX = this.speed.getVal() * dx / d;
				velY = this.speed.getVal() * dy / d;
				super.move(d);
			}

		} else if (HP.getVal() <= 0) {
			super.deathAnimation(frame, 0, 30, 3);
		}
	}

	@Override
	public void deathEvent(RPGFrame frame, Player p) {
		if (Math.random() < .5)
			frame.getEntityHandler().addEntity(new ItemDrop(absX, absY, new Bread(frame)));

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
