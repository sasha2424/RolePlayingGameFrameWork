package rendering;

import java.awt.Graphics2D;

import java.io.Serializable;

import entitieStructure.Entity;
import entitieStructure.Player;
import main.RPGFrame;
import main.Variables;
import terrain.Tile;

public abstract class Renderable implements Comparable<Renderable>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int RP = 0;// render priority (higher is rendered on top)

	protected double absX;
	protected double absY;

	abstract public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height);

	public int getRP() {
		return RP;
	}

	public void setRP(int rP) {
		RP = rP;
	}

	public double getAbsX() {
		return absX;
	}

	public void setAbsX(double absX) {
		this.absX = absX;
	}

	public double getAbsY() {
		return absY;
	}

	public void setAbsY(double absY) {
		this.absY = absY;
	}

	@Override
	public int compareTo(Renderable o) {
		return (int) (getY(absX, absY, Variables.rotation, 0) - getY(o.getAbsX(), o.getAbsY(), Variables.rotation, 0));
	}

	private static double getY(double x, double y, double r, double Height) {
		return (y) * Math.cos(r) + (x) * Math.sin(r) + Height;
	}

}
