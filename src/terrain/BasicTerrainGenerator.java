package terrain;

import java.util.Random;

import biomes.Desert;
import handlers.TerrainGenerator;

/***
 * 
 * Example TerrainGenerator class.
 * 
 * @author Alexander Ivanov
* @version 2018.03.15
 *
 */

public class BasicTerrainGenerator extends TerrainGenerator {

	public BasicTerrainGenerator(long seed) {
		super(seed);
	}

	@Override
	public Biome getBiome(int boardX, int boardY) {
		int i = (int) (boardX / .834);
		int j = (int) (boardY / .981);

		Random rand = new Random(0);

		int rangeout = 4;
		int rangein = 2;
		int totalBiomes = 4;

		// blur of size 4
		int[] count = new int[totalBiomes];
		for (int a = i - rangeout; a < i + rangeout; a++) {
			for (int b = j - rangeout; b < j + rangeout; b++) {

				// blur of size 2
				int[] countin = new int[totalBiomes];
				for (int q = a - rangein; q < a + rangein; q++) {
					for (int w = b - rangein; w < b + rangein; w++) {
						rand.setSeed(SEED + 4713 * q + 7608 * w);
						countin[rand.nextInt(totalBiomes)]++;
						// base random function
					}
				}
				int k = 0;
				for (int q = 0; q < totalBiomes; q++) {
					if (countin[q] > countin[k]) {
						k = q;
					}
				}
				count[k]++;
			}
		}
		int k = 0;
		for (int a = 0; a < totalBiomes; a++) {
			if (count[a] > count[k]) {
				k = a;
			}
		}

		if (k == 0) {
			// return new Desert();
		}
		if (k == 1) {
			// return new Plain();
		}
		if (k == 2) {
			// return new Forrest();
		}
		if (k == 3) {
			// return new Jungle();
		}
		if (k == 4) {
			// return new Ocean();
		}
		return new Desert();
		// return null;
	}

	@Override
	public double getTileHeight(int boardX, int boardY) {
		Random rand = new Random(SEED + 1398 * boardX + 1412 * boardY);
		return 50 * rand.nextInt(3) + 10;
		// return 0;
	}

}
