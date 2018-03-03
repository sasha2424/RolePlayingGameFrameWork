package main;

import java.awt.BorderLayout;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entitiesHandling.*;
import rendering.*;
import saving.SaveHandler;
import terrain.*;

/***
 * @author Alexander Ivanov
 * 
 *         FrameWork for sky-view, 3rd person, RPG games. Instance should be
 *         created in another class Then methods should be used to change
 *         default parameters.
 *
 */

public class RPGFrame extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	// TODO make different for each class

	/*
	 * Variables currently stored in "Variables.java"
	 *
	 * size of tiles: TILE_SIZE rotation of board: rotation
	 */

	// default window size
	private double WIDTH = 600;
	private double HEIGHT = 600;

	// random Seed for terrainGeneration
	// can be set to a value to force identical terrain on each run
	private long SEED;

	// scale for the scroll sensitivity
	// ranges from 1 to unlimited although over 10 is not recommended
	private int SCROLL_SENSITIVITY = 4;

	// file path for the image containing all of the in-game graphics
	private String DEFAULT_SPRITE_SHEET = "./spritesheets/Entities.png";

	// the size of each entities texture
	private int DEFAULT_SPRITE_SHEET_SIZE = 10;

	// the range of tiles to always be loaded by the game
	// 1 would mean current tile is loaded
	// 2 would make all surrounding tiles loaded
	// ...
	public int LOAD_SIZE = 2;// 2

	// the distance that the player needs to move before tiles are reloaded
	// if tiles were just loaded and the player travels over X tiles, tiles are
	// reloaded
	public int BUFFER = 1;

	// range of tiles to render
	// tiles may be loaded but this limit which ones will be rendered
	public int RENDER_DISTANCE_TILE = 2;

	// range in which entities will be rendered
	// use tile size for reference (in "Variables")
	public int RENDER_DISTANCE_ENTITY = 2000;

	//various Handlers that are used in the game
	//descriptions can be found in each class
	private TileHandler tileHandler;
	private InputHandler inputHandler;
	private EntityHandler entityHandler;
	private Spawner spawnHandler;
	private SaveHandler saveHandler;
	private EventHandler eventHandler;
	private TerrainGenerator terrainGenerator;
	private RenderQueue renderQueue;

	//rotation of the board which is changed by scrolling
	private double rotation = 0;
	//rotation of player which is changed by mouse position
	private double playerRotation = 0;

	//player used in the game
	private Player player;

	//current game state
	//0 is the game
	//1 is the inventory
	//... more can be added for additional menus and states
	private int Tab = 0;

	public RPGFrame() {
		SpriteSheetLoader.load(DEFAULT_SPRITE_SHEET, DEFAULT_SPRITE_SHEET_SIZE);
		SEED = getSeed();
		// TODO put this into a debug
		// System.out.println("Seed: " + SEED);

		player = loadPlayer();

		System.out.println(player);

		entityHandler = new EntityHandler();

		entityHandler.addEntity(player);

		terrainGenerator = new BasicTerrainGenerator(SEED);

		tileHandler = new TileHandler(entityHandler);

		inputHandler = new InputHandler();
		inputHandler.setScrollSensitivity(SCROLL_SENSITIVITY);
		inputHandler.trackNewKey("W", "noToggle");
		inputHandler.trackNewKey("A", "noToggle");
		inputHandler.trackNewKey("S", "noToggle");
		inputHandler.trackNewKey("D", "noToggle");
		inputHandler.trackNewKey("E", "toggle");
		inputHandler.trackNewKey("Space", "noToggle");
		inputHandler.trackNewKey("Q", "noToggle");
		inputHandler.trackNewKey("Escape", "toggle");

		spawnHandler = new BasicSpawner();

		saveHandler = new SaveHandler();

		eventHandler = new EventHandler();

		renderQueue = new RenderQueue();

	}

	public void setWindowSize(double WindowSizeX, double WindowSizeY) {
		WIDTH = WindowSizeX;
		HEIGHT = WindowSizeY;
	}

	public void setSeed(long seed) {
		SEED = seed;
	}

	public void setScrollSensitivity(int scrollSensitivity) {
		SCROLL_SENSITIVITY = scrollSensitivity;
	}

	public void setSpriteSheet(String fileName) {
		DEFAULT_SPRITE_SHEET = "./spritesheets/" + fileName;
	}

	public void setLoadSize(int loadSize) {
		LOAD_SIZE = loadSize;
	}

	public void setloadBuffer(int buffer) {
		BUFFER = buffer;
	}

	public void setTileRenderDistance(int r) {
		RENDER_DISTANCE_TILE = r;
	}

	public void setEntityRenderDistance(int r) {
		RENDER_DISTANCE_ENTITY = r;
	}

	public void addTileHandler(TileHandler e) {
		tileHandler = e;
	}

	public void addInputHandler(InputHandler e) {
		inputHandler = e;
	}

	public void addEntityHandler(EntityHandler e) {
		entityHandler = e;
	}

	public void addSpawner(Spawner e) {
		spawnHandler = e;
	}

	public void addSaveHandler(SaveHandler e) {
		saveHandler = e;
	}

	public void addeventHandler(EventHandler e) {
		eventHandler = e;
	}

	public void addTerrainGenerator(TerrainGenerator e) {
		terrainGenerator = e;
	}

	public void addRenderQueue(RenderQueue e) {
		renderQueue = e;
	}

	public void setPlayer(Player p) {
		player = p;
	}

	public void start() {
		this.run();
	}

	@Override
	public void run() {
		JFrame frame = new JFrame("Grim");
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((int) WIDTH, (int) HEIGHT);
		frame.setVisible(true);

		frame.addKeyListener(inputHandler);
		frame.addMouseWheelListener(inputHandler);

		long t = System.currentTimeMillis();
		long dt = 0;
		while (true) {
			dt = System.currentTimeMillis() - t;
			frame.repaint();
			if (dt > 20) {
				t = System.currentTimeMillis();

				entityHandler.tick(player);
				spawnHandler.spawnEntities(this, player);

				// do all player key actions
				// ******************
				if (inputHandler.getKeyPressed("W")) {
					player.move(playerRotation - rotation);
				}
				if (inputHandler.getKeyPressed("Space") && player.canAttack()) {
					entityHandler.playerInteract(player, playerRotation - rotation);
					player.resetAttackCounter();
				}

				if (inputHandler.getKeyPressed("Escape")) {
					saveHandler.saveAll(this, player);
					savePlayer(player);
					System.exit(ABORT);
				}

			}

		}

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);

		setPlayerRotation();
		setBoardRotation();

		if(inputHandler.getKeyPressed("E")){
			Tab = 1;
			player.inventory.render(this, g2d);
		}else { // in game
			Tab = 0;
			if (!inputHandler.getKeyPressed("Escape")) {
				// check for when game is closing

				saveHandler.updateLoadedTiles(this, player);

				tileHandler.renderAll(this, renderQueue, player);
				entityHandler.renderAll(this, renderQueue, player);
				renderQueue.renderAll(this, g2d, player, rotation, terrainGenerator.getEntityHeight(player));
				renderQueue.clear();

				g2d.drawString(player.getBoardX() + "  " + player.getBoardY(), 10, 10);

				player.inventory.renderHandBar(this, g2d);
			}
		}
	}

	private void setBoardRotation() {
		rotation = (2 * Math.PI * inputHandler.getScroll() / 100) % (Math.PI * 2);
		if (rotation < 0) {
			rotation += Math.PI * 2;
		}
		Variables.setRotation(rotation);
	}

	private void setPlayerRotation() {
		Point mouse = getMouse();
		double dx = -mouse.getX() + getCurrentWidth() / 2 + this.getLocationOnScreen().getX();
		double dy = -mouse.getY() + getCurrentHeight() / 2 + this.getLocationOnScreen().getY();
		// TODO figure out why?
		double angle = Math.atan2(dy, dx) + Math.PI;
		player.setRotation(angle);
		playerRotation = angle;
	}

	private Point getMouse() {
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point screan = this.getLocation();
		Point r = new Point();
		r.setLocation(mouse.getX() + screan.getX(), mouse.getY() + screan.getY());
		return r;
	}

	public double getCurrentWidth() {
		return (double) this.getSize().getWidth();
	}

	public double getCurrentHeight() {
		return (double) this.getSize().getHeight();
	}

	private long getSeed() {

		String saveDataFile = "./Save/save.txt";

		File f = new File(saveDataFile);
		if (f.exists() && !f.isDirectory()) {
			// GET SEED FROM EXISTING FILE
			try {
				String text = "";
				BufferedReader br = new BufferedReader(new FileReader(saveDataFile));
				String line = br.readLine();
				while (line != null) {
					text = text + line + "\n";
					line = br.readLine();
				}
				br.close();
				Long s = Long.parseLong(text.split("\n")[0]);
				return s;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// CREATE FILE AND ADD SEED TO FILE
			Long S = (long) (Math.random() * 10);
			PrintWriter writer;
			try {
				writer = new PrintWriter(saveDataFile);
				writer.println("" + (S));
				writer.close();
				return S;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return 0; // seed if file save failed.

	}

	private Player loadPlayer() {
		try {
			FileInputStream fis = new FileInputStream("./Save/player.ser");
			ObjectInputStream in = new ObjectInputStream(fis);
			Player p = (Player) in.readObject();
			in.close();
			fis.close();
			p.updateTexture();
			p.inventory.reloadItemTextures();
			return p;
		} catch (FileNotFoundException ex) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}

		System.out.println("player load failed");

		return new Player(0, 0, new int[] { 1 }, new int[] { 0 });
	}

	private void savePlayer(Player p) {
		try {
			String save = "./Save/player.ser";

			FileOutputStream fos = new FileOutputStream(save);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(p);

			out.close();
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public TileHandler getTileHandler() {
		return tileHandler;
	}

	public TerrainGenerator getTerrainGenerator() {
		return terrainGenerator;
	}

	public EntityHandler getEntityHandler() {
		return entityHandler;
	}

}
