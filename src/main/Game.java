package main;

public class Game {
	/*
	 * Class used for creating a frame work instance and running it. This is
	 * where defaults can be changed to add custom terrainGenerators or
	 * EntityHandlers ...
	 * 
	 */

	public static void main(String[] args) {
		RPGFrame frame = new RPGFrame("Grim");
		frame.start();
	}

}
