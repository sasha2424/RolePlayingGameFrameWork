package spriteSheets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class SpriteSheetLoader {

	protected BufferedImage Entities;

	protected int SPRITE_SIZE;
	protected String FILE;

	public abstract void load(String file, int size);

	public abstract Image getTexture(int x, int y);

}
