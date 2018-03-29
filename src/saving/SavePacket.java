package saving;

import java.io.Serializable;
import java.util.ArrayList;

import entityHandling.Entity;
import terrain.Tile;

/***
 * 
 * Class used for saving and loading entities and tiles jointly.
 * 
 * @author Alexander Ivanov
* @version 2018.03.15
 *
 */

public class SavePacket implements Serializable {

	private static final long serialVersionUID = 1L;

	private Tile t;
	private ArrayList<Entity> entities;

	public SavePacket(Tile t, ArrayList<Entity> e) {
		this.t = t;
		this.entities = e;
	}

	public String getName() {
		return "(" + t.getBoardX() + "," + t.getBoardY() + ")";
	}

	public Tile getTile() {
		return t;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public String getStats() {
		String r = t.getAbsX() + "\t" + t.getAbsY() + "\n";
		r += t.getBoardX() + "\t" + t.getBoardY() + "\n";
		r += "Entity Count: " + entities.size() + "\n";
		return r;
	}

}
