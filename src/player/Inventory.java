package player;

import java.awt.Graphics2D;
import java.io.Serializable;
import items.Item;
import main.RPGFrame;

public class Inventory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int SPACING = 60;

	private static final int DOWN_SHIFT = 210;

	private static final int WIDTH = 5;
	private static final int HEIGHT = 5;

	private Item[][] inventory;

	public Inventory() {
		inventory = new Item[WIDTH][HEIGHT];
	}

	public void reloadItemTextures(RPGFrame frame) {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (inventory[i][j] != null) {
					inventory[i][j].updateTexture(frame);
				}
			}
		}
	}

	public boolean addItem(Item item) {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (inventory[i][j] == null) {
					inventory[i][j] = item;
					return true;
				}
			}
		}
		return false;
	}

	public void render(RPGFrame w, Graphics2D g) {
		g.fillRect(0, 0, (int) w.getCurrentWidth(), (int) w.getCurrentHeight());
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (inventory[j][i] != null) {
					inventory[j][i].draw(g, i * SPACING, j * SPACING + DOWN_SHIFT);
				}
			}
		}
	}

	public void renderHandBar(RPGFrame w, Graphics2D g) {
		for (int i = 0; i < HEIGHT; i++) {
			if (inventory[0][i] != null) {
				inventory[0][i].draw(g, i % 10 * SPACING * 2, w.getCurrentHeight() - 2 * SPACING);
			}
		}
	}
}
