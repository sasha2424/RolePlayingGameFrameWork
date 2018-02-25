package entities;

import java.awt.Graphics;

import java.awt.Graphics2D;

import entitiesHandling.EntityHandler;
import entitiesHandling.Player;
import entitiesHandling.StationaryEntity;
import main.RPGFrame;
import terrain.Tile;

public class Grass extends StationaryEntity {

	public Grass(double X, double Y) {
		super(X, Y, new int[] { 3 }, new int[] { 0 });
		name = "grass";
		size = 30;
		collisionRange = 30;
	}

	public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {

		double x = (double) (getX(absX - player.getX(), absY - player.getY(), rotation, w.getCurrentWidth() / 2));
		double y = (double) (getY(absX - player.getX(), absY - player.getY(), rotation, w.getCurrentHeight() / 2));

		g.drawImage(texture[0], (int) (x - size / 2), (int) (y + height - size), size, size, null);

	}

	@Override
	public void tick(EntityHandler e) {
		// TODO Auto-generated method stub

	}

}
