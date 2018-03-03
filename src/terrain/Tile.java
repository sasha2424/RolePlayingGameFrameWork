package terrain;

import java.awt.Color;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

import entitieHandling.Entity;
import entitieHandling.Player;
import main.RPGFrame;
import main.Variables;
import rendering.Renderable;

public class Tile extends Renderable implements Serializable {

	/**
	 * 
	 */

	private transient ArrayList<Renderable> contained;

	private static final long serialVersionUID = 1L;

	private int boardX, boardY; // grid location

	private double H;

	// copy of the rotation in RPGFrame
	// TODO find a better way of getting this value

	public Tile(int x, int y, double h) {
		this.boardX = x;
		this.boardY = y;
		this.H = h;
		absX = boardX * Variables.TILE_SIZE;
		absY = boardY * Variables.TILE_SIZE;
		this.setRP(0);
	}

	public boolean inBorder(double x, double y) {
		return absX < x && x < absX + Variables.TILE_SIZE && absY < y && y < absY + Variables.TILE_SIZE;
	}

	public void draw(RPGFrame w, Graphics2D g, Player player, double rotation, double height) {
		double[] h = w.getTileHandler().getAdjacentTileHeights(boardX, boardY);
		// top left bottom right

		double k = w.getTerrainGenerator().getEntityHeight(player);

		double[][] C = getCoords(rotation, Variables.TILE_SIZE, absX - player.getX(), absY - player.getY(),
				w.getCurrentWidth() / 2, w.getCurrentHeight() / 2);

		// don't draw walls if tile is at ground level
		if (H != 0) {

			g.setColor(w.getTerrainGenerator().getBiome(boardX, boardY).getGroundColor());

			if (Math.PI / 2 < rotation && rotation < 3 * Math.PI / 2) {
				rect(g, C[0][0], C[1][0] - H + k, C[0][3], C[1][3] - H + k, C[0][3], C[1][3] - h[0] + k, C[0][0],
						C[1][0] - h[0] + k);
			}

			g.setColor(w.getTerrainGenerator().getBiome(boardX, boardY).getGroundColor());

			if (Math.PI < rotation && rotation < 2 * Math.PI) {
				rect(g, C[0][0], C[1][0] - H + k, C[0][1], C[1][1] - H + k, C[0][1], C[1][1] - h[1] + k, C[0][0],
						C[1][0] - h[1] + k);
			}
			g.setColor(w.getTerrainGenerator().getBiome(boardX, boardY).getGroundColor());

			if (!(Math.PI / 2 < rotation && rotation < 3 * Math.PI / 2)) {
				rect(g, C[0][1], C[1][1] - H + k, C[0][2], C[1][2] - H + k, C[0][2], C[1][2] - h[2] + k, C[0][1],
						C[1][1] - h[2] + k);
			}

			g.setColor(w.getTerrainGenerator().getBiome(boardX, boardY).getGroundColor());

			if (0 < rotation && rotation < Math.PI) {
				rect(g, C[0][3], C[1][3] - H + k, C[0][2], C[1][2] - H + k, C[0][2], C[1][2] - h[3] + k, C[0][3],
						C[1][3] - h[3] + k);
			}
		}

		// set surface color
		g.setColor(w.getTerrainGenerator().getBiome(boardX, boardY).getSurfaceColor());

		// draw tile surface
		rect(g, C[0][0], C[1][0] - H + k, C[0][1], C[1][1] - H + k, C[0][2], C[1][2] - H + k, C[0][3], C[1][3] - H + k);

		/*
		 * //TODO put in a loop //TODO can this be cleaner?
		 * 
		 * SUPER LAGGY BUT PUTS TEXTURE ON GROUND
		 * 
		 * g.translate(w.getCurrentWidth() / 2, w.getCurrentHeight() / 2);
		 * g.rotate(rotation); g.translate(this.getAbsX() -
		 * player.getX(),this.getAbsY() - player.getY()); g.rotate(-rotation);
		 * g.translate(0,- H + k); g.rotate(rotation);
		 * 
		 * g.drawImage(Biome.getBiome(boardX, boardY).getSurfaceTexture(), 0, 0,
		 * 1600, 1600, null); // magic number
		 * 
		 * g.rotate(-rotation); g.translate(0, H - k); g.rotate(rotation);
		 * g.translate(-this.getAbsX() + player.getX(), -this.getAbsY()
		 * +player.getY()); g.rotate(-rotation);
		 * g.translate(-w.getCurrentWidth()/ 2, -w.getCurrentHeight() / 2);
		 * 
		 */

	}

	private static double getX(double x, double y, double r, double Width) {
		return (x) * Math.cos(r) - (y) * Math.sin(r) + Width;
	}

	private static double getY(double x, double y, double r, double Height) {
		return (y) * Math.cos(r) + (x) * Math.sin(r) + Height;
	}

	private void rect(Graphics g, double X1, double Y1, double X2, double Y2, double X3, double Y3, double X4,
			double Y4) {

		g.fillPolygon(new int[] { (int) X1, (int) X2, (int) X3, (int) X4 },
				new int[] { (int) Y1, (int) Y2, (int) Y3, (int) Y4 }, 4);

		g.setColor(Color.black);

		g.drawPolygon(new int[] { (int) X1, (int) X2, (int) X3, (int) X4 },
				new int[] { (int) Y1, (int) Y2, (int) Y3, (int) Y4 }, 4);

	}

	private double[][] getCoords(double r, double s, double x, double y, double Width, double Height) {

		// returns a 2d array for the x and y coordinates of the corners of the
		// top surface;

		/*
		 * first x then y 4 for each as the 4 top locations.
		 */
		double[][] t = new double[2][8];

		t[0][0] = getX(x, y, r, Width);
		t[0][1] = getX(x, y + s, r, Width);
		t[0][2] = getX(x + s, y + s, r, Width);//
		t[0][3] = getX(x + s, y, r, Width);//

		t[1][0] = getY(x, y, r, Height);
		t[1][1] = getY(x, y + s, r, Height);
		t[1][2] = getY(x + s, y + s, r, Height);
		t[1][3] = getY(x + s, y, r, Height);

		return t;
	}

	public int getBoardX() {
		return boardX;
	}

	public void setBoardX(int boardX) {
		this.boardX = boardX;
	}

	public int getBoardY() {
		return boardY;
	}

	public void setBoardY(int boardY) {
		this.boardY = boardY;
	}

	public double getH() {
		return H;
	}

	public void setH(double h) {
		H = h;
	}

	public void addRenderable(Renderable r) {
		if (contained == null)
			contained = new ArrayList<Renderable>();
		contained.add(r);
	}

	public ArrayList<Renderable> getContainedEntities() {
		return contained;
	}

	public void clearContained() {
		if (contained != null)
			contained.clear();
	}

}
