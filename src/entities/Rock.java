package entities;

import java.awt.Graphics;

import java.awt.Graphics2D;

import entityHandling.StationaryEntity;
import handlers.EntityHandler;
import main.RPGFrame;
import player.Player;
import terrain.Tile;

public class Rock extends StationaryEntity {

	public Rock(RPGFrame frame, double X, double Y) {
		super(frame, X, Y, new int[] { 1 }, new int[] { 1 });
		name = "tree";
		size = 70;
		collisionRange = 70;
	}

	public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {
		super.standardDraw(w, g, player, rotation, height, 0);
	}

	@Override
	public void tick(RPGFrame frame) {
		// TODO Auto-generated method stub

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
