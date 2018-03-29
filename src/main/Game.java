package main;

/***
 * 
 * Main Class from where a framework instance is created and run.
 * 
 * @author Alexander Ivanov
 * @version 2018.03.15
 *
 */

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
