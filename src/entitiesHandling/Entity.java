package entitiesHandling;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;

import main.DoubleStat;
import main.RPGFrame;
import main.SpriteSheetLoader;
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

	// battle stats
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

	public Entity(double X, double Y, int[] x, int[] y) {
		this.absX = X;
		this.absY = Y;
		textureX = x;
		textureY = y;
		texture = new Image[x.length];
		updateTexture();

		// TODO put these in each entity as a required setup method
		HP = new DoubleStat(10, 10);
		A = new DoubleStat(1, 1);
		D = new DoubleStat(1, 1);

		hasDied = false;

		this.setRP(1);
	}

	public void updateTexture() {
		texture = new Image[textureX.length];
		for (int i = 0; i < textureX.length; i++) {
			texture[i] = SpriteSheetLoader.getTexture(textureX[i], textureY[i]);
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
	public abstract void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height);

	public abstract void tick(EntityHandler e);

	public abstract void interactPlayer(EntityHandler e, Player p);
	// perform action with player/to self

	public abstract void nearPlayer(EntityHandler e, Player p);

	public abstract void deathEvent(EntityHandler e, Player p);

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

	protected static double getX(double x, double y, double r, double ScreanWidth) {
		return (x) * Math.cos(r) - (y) * Math.sin(r) + ScreanWidth;
	}

	protected static double getY(double x, double y, double r, double ScreanHeight) {
		return (y) * Math.cos(r) + (x) * Math.sin(r) + ScreanHeight;
	}

	public String getName() {
		return name;
	}

	public boolean isDead() {
		return hasDied;
	}

	

}
