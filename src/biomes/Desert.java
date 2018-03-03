package biomes;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import entitieHandling.Entity;
import entitieHandling.Player;
import entities.Grass;
import entities.Rat;
import entities.Rock;
import main.RPGFrame;
import terrain.Biome;
import terrain.Tile;

public class Desert extends Biome {

	@Override
	public Color getSurfaceColor() {
		// TODO Auto-generated method stub
		return new Color(255, 0, 0);
	}

	@Override
	public Color getGroundColor() {
		// TODO Auto-generated method stub
		return new Color(150, 100, 100);
	}

	@Override
	public Image getSurfaceTexture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Entity> generateEntitiesForTile(RPGFrame frame, Tile t) {
		ArrayList<Entity> e = new ArrayList<Entity>();
		for (int i = 0; i < 1; i++) {
			e.add(new Grass(frame, this.random(t.getBoardX()), this.random(t.getBoardY())));
		}
		for (int i = 0; i < 1; i++) {
			e.add(new Grass(frame, this.random(t.getBoardX()), this.random(t.getBoardY())));
		}
		return e;
	}

	protected ArrayList<Entity> getSpawnSet(RPGFrame frame, Player p, Tile t) {
		ArrayList<Entity> e = new ArrayList<Entity>();
		e.add(new Rat(frame, this.random(t.getBoardX()), this.random(t.getBoardY())));
		return e;
	}

}
