package handlers;

import java.util.ArrayList;

import main.RPGFrame;
import main.Variables;
import player.Player;
import terrain.Tile;

/**
 * TILEHANDLER
 * 
 * This manages all of the tiles in the game. Performs Rendering and provides
 * various methods for modifying the tiles set and receiving certain tiles.
 * 
 * @author Alexander Ivanov
 * @version 2018.03.15
 * 
 */
public abstract class TileHandler {

	protected ArrayList<Tile> tiles;

	public TileHandler() {
		tiles = new ArrayList<Tile>();
	}

	public abstract void renderAll(RPGFrame frame, RenderQueue renderQueue, Player p);

	public double[] getAdjacentTileHeights(int x, int y) {
		double[] r = new double[4];
		for (int i = 0; i < tiles.size(); i++) {
			Tile t = tiles.get(i);
			if (t.getBoardX() == x && t.getBoardY() == y - 1) {
				r[0] = t.getH();
			}
			if (t.getBoardX() == x - 1 && t.getBoardY() == y) {
				r[1] = t.getH();
			}
			if (t.getBoardX() == x && t.getBoardY() == y + 1) {
				r[2] = t.getH();
			}
			if (t.getBoardX() == x + 1 && t.getBoardY() == y) {
				r[3] = t.getH();
			}
		}
		return r;
	}

	/**
	 * Takes in x and y and returns the corresponding tile
	 * 
	 * @param x
	 *            x coordinate (abs)
	 * @param y
	 *            y coordinate (abs)
	 * @return Tile corresponding to the coordinates
	 */
	public Tile getTile(int x, int y) {
		for (int i = 0; i < tiles.size(); i++) {
			Tile t = tiles.get(i);
			if (t.getBoardX() == x && t.getBoardY() == y) {
				return t;
			}
		}
		return null;
	}

	public Tile getPlayerTile(Player p) {
		return getTile(p.getBoardX(), p.getBoardY());
	}

	public boolean missingTile(int x, int y) {
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).getBoardX() == x && tiles.get(i).getBoardY() == y) {
				return false;
			}
		}
		return true;
	}

	public void addTilefromSave(Tile t) {
		tiles.add(t);
	}

	public abstract ArrayList<Tile> getTilesToSave(int LOAD_SIZE, int playerX, int playerY);

	public Tile removeTile(int BoardX, int BoardY) {
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).getBoardX() == BoardX) {
				if (tiles.get(i).getBoardY() == BoardY) {
					return tiles.remove(i);
				}
			}

		}
		return null;
	}

	public void removeTile(Tile t) {
		tiles.remove(t);
	}

	/**
	 * Returns all of the tiles but also deletes them from this class.
	 * 
	 * @return ArrayList of Tiles currently loaded
	 */
	public ArrayList<Tile> getAllTiles() {
		ArrayList<Tile> r = new ArrayList<Tile>();
		for (int i = 0; i < tiles.size(); i++) {
			r.add(tiles.get(i));
		}
		tiles.clear();
		return r;
	}
}
