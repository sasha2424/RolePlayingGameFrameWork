package handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import entitieStructure.Entity;
import entitieStructure.Player;
import main.RPGFrame;
import main.Variables;
import saving.SavePacket;
import terrain.Biome;
import terrain.Tile;

public class SaveHandler {
	/**
	 * SAVEHANDLER
	 * 
	 * This saves and loads tiles and entities based on the player position.
	 * LOAD_SIZE in RPGFrame is the range of tiles that are loaded.
	 * Tiles and their contained entities are stored in SavePackets and are serialized
	 * into the save folder.
	 */
	
	private int oldX;
	private int oldY;

	// will be referred to as action border
	// area where all tiles are loaded

	// If player moves out of this range

	public SaveHandler() {
		oldX = 10000000;
		oldY = 10000000;
	}

	public void updateLoadedTiles(RPGFrame frame, Player player) {
		int X = player.getBoardX();
		int Y = player.getBoardY();

		if (Math.abs(X - oldX) > frame.BUFFER || Math.abs(Y - oldY) > frame.BUFFER) {
			for (Tile t : frame.getTileHandler().getTilesToSave(frame.LOAD_SIZE, X, Y)) {
				SavePacket p = new SavePacket(t, frame.getEntityHandler().getEntitiesInTile(t));
				save(p);
			}

			for (int i = X - frame.LOAD_SIZE; i < X + frame.LOAD_SIZE; i++) {
				for (int j = Y - frame.LOAD_SIZE; j < Y + frame.LOAD_SIZE; j++) {
					if (frame.getTileHandler().missingTile(i, j)) {
						SavePacket p = load(frame, i, j);
						frame.getTileHandler().addTilefromSave(p.getTile());
						frame.getEntityHandler().addEntitiesFromSave(frame, p.getEntities());
					}
				}
			}

			oldX = player.getBoardX();
			oldY = player.getBoardY();
		}
	}

	public void saveAll(RPGFrame frame, Player player) {

		ArrayList<Tile> toSave = frame.getTileHandler().getAllTiles();

		for (Tile t : toSave) {
			// TODO run saving as a separate thread to make the game faster
			SavePacket p = new SavePacket(t, frame.getEntityHandler().getEntitiesInTile(t));
			save(p);
		}

	}

	public SavePacket load(RPGFrame frame, int x, int y) {
		try {
			FileInputStream fis = new FileInputStream("./Save/(" + x + "," + y + ").ser");
			ObjectInputStream in = new ObjectInputStream(fis);
			SavePacket p = (SavePacket) in.readObject();
			in.close();
			fis.close();
			return p;
		} catch (FileNotFoundException ex) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}

		Biome b = frame.getTerrainGenerator().getBiome(x, y);
		Tile t = new Tile(x, y, frame.getTerrainGenerator().getTileHeight(x, y));
		ArrayList<Entity> e = b.generateEntitiesForTile(frame, t);
		return new SavePacket(t, e);
	}

	public void save(SavePacket p) {
		try {
			String save = "./Save/" + p.getName() + ".ser";

			FileOutputStream fos = new FileOutputStream(save);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(p);

			out.close();
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
