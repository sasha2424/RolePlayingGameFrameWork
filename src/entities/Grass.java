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
		super.standardDraw(w, g, player, rotation, height,0);
	}

	@Override
	public void tick(EntityHandler e) {
		// TODO Auto-generated method stub

	}

}
