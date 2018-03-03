package handlers;

import entitieHandling.Entity;
import terrain.Biome;

public abstract class TerrainGenerator {
	/**
	 * TERRAINGENERATOR
	 * 
	 * Generates the height and biome for any given tile location.
	 * This is only used when new tiles are created.
	 * Example height and biome generation algorithms are present in BasicTerrainGenerator
	 */
	
	protected long SEED;
	
	public TerrainGenerator(long seed){
		SEED = seed;
	}
	
	public abstract Biome getBiome(int boardX,int boardY);
	
	public abstract double getTileHeight(int boardX,int boardY);
	
	public double getEntityHeight(Entity e){
		return getTileHeight(e.getBoardX(),e.getBoardY());
	}
}
