package spriteSheets;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import handlers.SpriteSheetLoader;

public class BasicSpriteSheetLoader extends SpriteSheetLoader {

	public void load(String file, int size) {
		FILE = file;
		SPRITE_SIZE = size;
		try {
			Entities = ImageIO.read(new File(FILE));
		} catch (IOException e1) {
			System.err.println("Failed to load Image");
			e1.printStackTrace();
		}
	}

	public Image getTexture(int x, int y) { // row column
		// TODO add a check for size and error printout
		return Entities.getSubimage(x * SPRITE_SIZE, y * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
	}
}
