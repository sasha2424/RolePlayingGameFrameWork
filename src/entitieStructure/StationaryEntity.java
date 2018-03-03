package entitieStructure;

import main.RPGFrame;

public abstract class StationaryEntity extends Entity {

	public boolean spawnsMonster;

	public StationaryEntity(RPGFrame frame, double X, double Y, int[] x, int[] y) {
		super(frame, X, Y, x, y);
		canMove = false;
	}

	public StationaryEntity(double X, double Y) {
		super(X, Y);
		canMove = false;
	}

	public void interactPlayer(RPGFrame frame, Player player) {
		this.HP.increment(-1 * player.A.getVal());
	}

}
