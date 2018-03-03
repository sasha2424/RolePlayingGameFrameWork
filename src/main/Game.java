package main;

import entities.Rat;
import entitiesHandling.EntityHandler;
import entitiesHandling.Player;
import rendering.RenderQueue;
import saving.SaveHandler;
import terrain.TileHandler;

public class Game {
	/*
	 * Class used for creating a frame work instance and running it.
	 * This is where defaults can be changed to add custom terrainGenerators or EntityHandlers ...
	 * 
	 */
	
	public static void main(String[] args){
		RPGFrame frame = new RPGFrame();

		frame.start();
	}

}
