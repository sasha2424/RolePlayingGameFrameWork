package items;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;

import handlers.SpriteSheetLoader;
import main.RPGFrame;

/***
 * 
 * Class representing all ingame items.
 * 
 * @author Alexander Ivanov
 * @version 2018.03.15
 *
 */

public class Item implements Serializable {

	protected transient Image texture;
	protected int imageX;
	protected int imageY;

	public static final int ICON_SIZE = 50;
	public static final int GROUND_SIZE = 25;

	public Item(RPGFrame frame, int spriteX, int spriteY) {
		texture = frame.getSpriteSheetLoader().getTexture(spriteX, spriteY);
		imageX = spriteX;
		imageY = spriteY;
	}

	public void updateTexture(RPGFrame frame) {
		texture = frame.getSpriteSheetLoader().getTexture(imageX, imageY);
	}

	public void draw(Graphics2D g, double x, double y) {
		g.drawImage(texture, (int) x, (int) y, ICON_SIZE, ICON_SIZE, null);
	}

	public void drawOnGround(Graphics2D g, int x, int y) {
		g.drawImage(texture, (int) x, (int) y, GROUND_SIZE, GROUND_SIZE, null);

	}

}
