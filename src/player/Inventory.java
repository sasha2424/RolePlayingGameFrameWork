package player;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import items.Item;
import main.RPGFrame;

/***
 * 
 * Class holding all of the players items.
 * 
 * @author Alexander Ivanov
 * @version 2018.03.15
 *
 */
public class Inventory implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int SPACING = 60; // TODO move to main

	private final int DOWN_SHIFT = 210; // TODO move to main

	private int WIDTH = 5;
	private int HEIGHT = 5;

	private int ICON_SIZE = 30;

	private ArrayList<ItemSlot> inventory;

	private ArrayList<ItemSlot> handbar;

	private ArrayList<ItemSlot> equipment;

	public Inventory() {
		inventory = new ArrayList<ItemSlot>();
		handbar = new ArrayList<ItemSlot>();
		equipment = new ArrayList<ItemSlot>();

		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				inventory.add(new ItemSlot(i * SPACING, j * SPACING + DOWN_SHIFT, ICON_SIZE, ICON_SIZE));
			}
		}
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				inventory.add(new ItemSlot(i * SPACING, j * SPACING + DOWN_SHIFT, ICON_SIZE, ICON_SIZE));
			}
		}
	}

	public void reloadItemTextures(RPGFrame frame) {
		for (int i = 0; i < inventory.size(); i++) {
			inventory.get(i).getItem().updateTexture(frame);
		}
	}

	public boolean addItem(Item item) {
		for (int i = 0; i < handbar.size(); i++) {
			if (handbar.get(i).getItem() == null) {
				handbar.get(i).addItem(item);
				return true;
			}
		}
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getItem() == null) {
				inventory.get(i).addItem(item);
				return true;
			}
		}

		return false;
	}

	public void render(RPGFrame w, Graphics2D g) {
		g.fillRect(0, 0, (int) w.getCurrentWidth(), (int) w.getCurrentHeight());
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getItem() != null) {
				inventory.get(i).draw(g);
			}
		}
		renderHandBar(w, g);
	}

	public void renderHandBar(RPGFrame w, Graphics2D g) {
		for (int i = 0; i < handbar.size(); i++) {
			if (handbar.get(i).getItem() != null) {
				handbar.get(i).draw(g);
			}
		}
	}

	private class ItemSlot {
		private double x, y, sizeX, sizeY;

		private Item item;

		public ItemSlot(double x, double y, double sizeX, double sizeY) {
			super();
			this.x = x;
			this.y = y;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
		}

		public void addItem(Item i) {
			item = i;
		}

		public Item getItem() {
			return item;
		}

		public void draw(Graphics2D g) {
			if (item != null) {
				item.draw(g, sizeX, sizeY);
			}
		}

		public boolean mouseOnItem(Point p) {
			if (x < p.getX() && p.getX() < x + sizeX) {
				if (y < p.getY() && p.getY() < y + sizeY) {
					return true;
				}
			}
			return false;
		}

	}

}
