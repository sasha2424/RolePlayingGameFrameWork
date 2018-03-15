package terrain;

import java.util.ArrayList;

import entityHandling.Player;
import handlers.EntityHandler;
import handlers.RenderQueue;
import handlers.TileHandler;
import main.RPGFrame;

public class BasicTileHandler extends TileHandler {

	public void renderAll(RPGFrame frame, RenderQueue renderQueue, Player p) {
		for (int i = 0; i < tiles.size(); i++) {
			Tile t = tiles.get(i);

			if (Math.abs(t.getBoardX() - p.getBoardX()) < frame.RENDER_DISTANCE_TILE
					&& Math.abs(t.getBoardY() - p.getBoardY()) < frame.RENDER_DISTANCE_TILE) {
				renderQueue.addRenderable(t);
			}
		}
	}
	
	public ArrayList<Tile> getTilesToSave(int LOAD_SIZE, int playerX, int playerY) {
		ArrayList<Tile> r = new ArrayList<Tile>();
		for (int i = 0; i < tiles.size(); i++) {
			Tile t = tiles.get(i);
			if (Math.abs(t.getBoardX() - playerX) > LOAD_SIZE || Math.abs(t.getBoardY() - playerY) > LOAD_SIZE) {
				r.add(tiles.remove(i));
				i--;
			}
		}
		return r;
	}
}
