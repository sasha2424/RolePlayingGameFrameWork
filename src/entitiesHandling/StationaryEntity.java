package entitiesHandling;

public abstract class StationaryEntity extends Entity {

	public boolean spawnsMonster;

	public StationaryEntity(double X, double Y, int[] x, int[] y) {
		super(X, Y, x, y);
		canMove = false;
	}

	public StationaryEntity(double X, double Y) {
		super(X, Y);
		canMove = false;
	}
	// public abstract void displayInfoText();
	// use event handler to make text pop up on screen

	public void interactPlayer(EntityHandler e, Player player) {
		this.HP.increment(-1 * player.A.getVal());
	}

	public void nearPlayer(EntityHandler e, Player p) {
		// TODO maybe have some sort of particles for some things
	}

	public void deathEvent(EntityHandler e, Player player) {
		// TODO
	}

}
