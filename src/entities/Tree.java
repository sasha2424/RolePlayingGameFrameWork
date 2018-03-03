package entities;

import java.awt.Graphics;

import java.awt.Graphics2D;

import entitiesHandling.EntityHandler;
import entitiesHandling.Player;
import entitiesHandling.StationaryEntity;
import main.RPGFrame;
import terrain.Tile;

public class Tree extends StationaryEntity {

	public Tree(double X, double Y) {
		super(X, Y, new int[] { 0 }, new int[] { 0 });
		name = "tree";
		size = 70;
		collisionRange = 40;
	}

	public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {
		super.standardDraw(w, g, player, rotation, height,0);
	}

	@Override
	public void tick(EntityHandler e) {

	}

}
