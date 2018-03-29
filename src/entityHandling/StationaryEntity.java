package entityHandling;

import main.RPGFrame;
import player.Player;

/**
 * Class representing stationary entities.
 * 
 * @author Alexander Ivanov
 * @version 2018.03.15
 *
 */
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
