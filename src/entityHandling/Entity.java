package entityHandling;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;

import handlers.SpriteSheetLoader;
import main.DoubleStat;
import main.RPGFrame;
import main.Variables;
import rendering.Renderable;
import terrain.Tile;

public abstract class Entity extends Renderable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String name;

	// Movement and rendering
	protected double velX, velY;
	protected int size;
	protected transient Image[] texture;
	protected int[] textureX, textureY;
	protected DoubleStat speed;

	protected double collisionRange;

	// These are Variables that can have modifiers
	// Look at DoubleStat class
	protected DoubleStat HP;
	protected DoubleStat A;
	protected DoubleStat D;
	protected boolean hasDied;
	// TODO add and Clothes Set to this ( class which keeps track of clothes
	// Equipped and each ones"thickness)

	protected boolean canMove;
	protected boolean canAttack;
	protected boolean canDie;
	protected boolean visible;
	protected boolean collides;

	public Entity(RPGFrame frame, double X, double Y, int[] x, int[] y) {
		this.absX = X;
		this.absY = Y;
		textureX = x;
		textureY = y;
		texture = new Image[x.length];
		updateTexture(frame);

		// TODO put these in each entity as a required setup method
		HP = new DoubleStat(10, 10);
		A = new DoubleStat(1, 1);
		D = new DoubleStat(1, 1);

		hasDied = false;

		this.setRP(1);
	}

	public void updateTexture(RPGFrame frame) {
		texture = new Image[textureX.length];
		for (int i = 0; i < textureX.length; i++) {
			texture[i] = frame.getSpriteSheetLoader().getTexture(textureX[i], textureY[i]);
		}
	}

	/***
	 * Use only if texture is specifically added after the super constructor is
	 * run
	 * 
	 * @param X
	 * @param Y
	 */
	public Entity(double X, double Y) {
		this.absX = X;
		this.absY = Y;

		// TODO put these in each entity as a required setup method
		HP = new DoubleStat(10, 10);
		A = new DoubleStat(1, 1);
		D = new DoubleStat(1, 1);

		hasDied = false;
	}

	@Override
	public abstract void draw(RPGFrame frame, Graphics2D g, Player player, double rotation, double height);

	public abstract void tick(RPGFrame frame);

	public abstract void interactPlayer(RPGFrame frame, Player p);
	// perform action with player/to self

	public abstract void nearPlayer(RPGFrame frame, Player p);

	public abstract void deathEvent(RPGFrame frame, Player p);

	/**
	 * What happens to an entity when it is hit
	 * @param frame the RPGFrame
	 * @param A the amount by which they were attacked
	 */
	public abstract void takeHit(RPGFrame frame, double A);

	public double getAbsX() {
		return absX;
	}

	public void setAbsX(double absX) {
		this.absX = absX;
	}

	public int getBoardX() {
		return Variables.absToBoard(getAbsX());
	}

	public double getAbsY() {
		return absY;
	}

	public void setAbsY(double absY) {
		this.absY = absY;
	}

	public int getBoardY() {
		return Variables.absToBoard(getAbsY());
	}

	public double getHP() {
		return HP.getVal();
	}

	public void setHP(double hP) {
		HP.set(hP);
	}

	protected static double getStandardRenderX(double x, double y, double r, double ScreanWidth) {
		return (x) * Math.cos(r) - (y) * Math.sin(r) + ScreanWidth;
	}

	protected static double getStandardRenderY(double x, double y, double r, double ScreanHeight) {
		return (y) * Math.cos(r) + (x) * Math.sin(r) + ScreanHeight;
	}

	protected void standardDraw(RPGFrame w, Graphics2D g, Player player, double rotation, double height,
			int textureNum) {
		double x = (double) (getStandardRenderX(absX - player.getX(), absY - player.getY(), rotation,
				w.getCurrentWidth() / 2));
		double y = (double) (getStandardRenderY(absX - player.getX(), absY - player.getY(), rotation,
				w.getCurrentHeight() / 2));

		double ownHeight = w.getTerrainGenerator().getEntityHeight(this);
		double deltaH = height - ownHeight;
		g.drawImage(texture[textureNum], (int) (x - size / 2), (int) (y + deltaH - size), size, size, null);
	}

	public double getHeightDifference(RPGFrame w, double playerHeight) {
		double ownHeight = w.getTerrainGenerator().getEntityHeight(this);
		return playerHeight - ownHeight;
	}

	public String getName() {
		return name;
	}

	public boolean isDead() {
		return hasDied;
	}

	public double getAttack() {
		return A.getVal();

	}

	public double getDefense() {
		return D.getVal();
	}

}
