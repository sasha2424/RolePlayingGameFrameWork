package terrain;

import entitiesHandling.Entity;

public abstract class TerrainGenerator {
	//generates terrain height and biome for any given board coordinate
	
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
