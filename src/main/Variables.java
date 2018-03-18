package main;

import java.util.HashMap;

import terrain.Tile;

public class Variables {
	/**
	 * VARIABLES
	 * 
	 * Some constants that need to be held in a static class. These can still be
	 * set and changed like any variables that are in RPGFrame. This acts as an
	 * extension of RPGFrame.
	 */

	public static HashMap<String, Double> vars = new HashMap<String, Double>();

	public static double rotation;

	public static double TILE_SIZE = 400; // 1600

	public static void addVar(String s, Double d) {
		vars.put(s, d);
	}

	public static void updateVar(String s, Double d) {
		vars.replace(s, d);
	}

	public static double get(String s) {
		return vars.get(s);
	}

	public static void setRotation(double r) {
		rotation = r;
	}

	public static int absToBoard(double a) {
		if (a % TILE_SIZE == 0) {
			return (int) (a / TILE_SIZE);
		}
		int r = a < 0 ? (int) (a / TILE_SIZE - 1) : (int) (a / TILE_SIZE);
		return r;
	}
}
