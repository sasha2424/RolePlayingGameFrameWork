package entities;

import java.awt.Graphics;

import java.awt.Graphics2D;

import entityHandling.StationaryEntity;
import handlers.EntityHandler;
import main.RPGFrame;
import player.Player;
import terrain.Tile;

/**
 * 
 * Example Stationary Entity class.
 * 
 * @author Alexander Ivanov
 * @version 2018.03.15
 *
 */

public class Grass extends StationaryEntity {

	public Grass(RPGFrame frame, double X, double Y) {
		super(frame, X, Y, new int[] { 3 }, new int[] { 0 });
		name = "grass";
		size = 30;
		collisionRange = 30;
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
