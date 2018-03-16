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

import entityHandling.*;
import handlers.EntityHandler;
import handlers.InputHandler;
import handlers.RenderQueue;
import handlers.SaveHandler;
import handlers.Spawner;
import handlers.SpriteSheetLoader;
import handlers.TerrainGenerator;
import handlers.TileHandler;
import rendering.*;
import spriteSheets.BasicSpriteSheetLoader;
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

	// file path for the folder with all of the save data
	private String DEFAULT_SAVE_DIR = "./Save/";

	// the size of each entities texture
	private int DEFAULT_SPRITE_SHEET_SIZE = 10;

	// the range of tiles to always be loaded by the game
	// 1 would mean current tile is loaded
	// 2 would make all surrounding tiles loaded
	// ...
	public int LOAD_SIZE = 4;// 2

	// the distance that the player needs to move before tiles are reloaded
	// if tiles were just loaded and the player travels over X tiles, tiles are
	// reloaded
	public int BUFFER = 2;

	// range of tiles to render
	// tiles may be loaded but this limit which ones will be rendered
	public int RENDER_DISTANCE_TILE = 2;

	// range in which entities will be rendered
	// use tile size for reference (in "Variables")
	public int RENDER_DISTANCE_ENTITY = 2000;

	// various Handlers that are used in the game
	// descriptions can be found in each class
	private TileHandler tileHandler;
	private InputHandler inputHandler;
	private EntityHandler entityHandler;
	private Spawner spawnHandler;
	private SaveHandler saveHandler;
	private TerrainGenerator terrainGenerator;
	private RenderQueue renderQueue;
	private SpriteSheetLoader spriteSheetLoader;

	// rotation of the board which is changed by scrolling
	private double rotation = 0;
	// rotation of player which is changed by mouse position
	private double playerRotation = 0;

	// player used in the game
	private Player player;

	private String name; // this is the display name at the top of the window

	// current game state
	// 0 is the game
	// 1 is the inventory
	// ... more can be added for additional menus and states
	private int Tab = 0;

	public RPGFrame(String name) {
		this.name = name;
		// create default spriteSheetLoader
		spriteSheetLoader = new BasicSpriteSheetLoader();
		// load in the spritesheet
		spriteSheetLoader.load(DEFAULT_SPRITE_SHEET, DEFAULT_SPRITE_SHEET_SIZE);
		// get random seed
		SEED = getSeed();

		// attempt to load player from file (load default on fail)
		player = loadPlayer();
		System.out.println(player);

		entityHandler = new BasicEntityHandler();

		entityHandler.addEntity(player);

		terrainGenerator = new BasicTerrainGenerator(SEED);

		tileHandler = new BasicTileHandler();

		inputHandler = new InputHandler();
		inputHandler.setScrollSensitivity(SCROLL_SENSITIVITY);
		// setting the keys that are to be tracked by the inputHandler
		// toggle means that a single press will toggle the key state
		// notoggle requires the key to be held to keep the pressed state
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

		renderQueue = new BasicRenderQueue();

	}

	/**
	 * Set the default window size.
	 * 
	 * @param WindowSizeX
	 *            width of the window
	 * @param WindowSizeY
	 *            height of the window
	 */
	public void setWindowSize(double WindowSizeX, double WindowSizeY) {
		WIDTH = WindowSizeX;
		HEIGHT = WindowSizeY;
	}

	/**
	 * Set the seed to be used for the terrain generation.
	 * 
	 * @param seed
	 *            any positive number
	 */
	public void setSeed(long seed) {
		SEED = seed;
	}

	/**
	 * Set the sensitivity of scrolling to rotate the board. Usually a
	 * sensitivity of 2-7 is more than enough.
	 * 
	 * @param scrollSensitivity
	 *            a positive integer
	 */
	public void setScrollSensitivity(int scrollSensitivity) {
		SCROLL_SENSITIVITY = scrollSensitivity;
	}

	/**
	 * Set the file to be used when loading in the spritesheet. The file should
	 * be placed into the spritesheet folder.
	 * 
	 * @param fileName
	 *            name of the file including ending (.png)
	 */
	public void setSpriteSheet(String fileName) {
		DEFAULT_SPRITE_SHEET = "./spritesheets/" + fileName;
	}

	/**
	 * Set the folder to be used when saving and loading the game.
	 * 
	 * @param folderName
	 *            name of the folder
	 * 
	 */
	public void setSaveDirectory(String folderName) {
		DEFAULT_SAVE_DIR = folderName;
	}

	/**
	 * Setting the distance from the player to be loaded. All tiles within this
	 * range will be loaded in. Loading many tiles may lag the game when new
	 * tiles are being generated and the algorithm for the generation is large.
	 * 
	 * @param loadSize
	 *            integer representing the range of tiles to load
	 */
	public void setLoadSize(int loadSize) {
		LOAD_SIZE = loadSize;
	}

	/**
	 * Setting the buffer for how far the player needs to move before new tiles
	 * are loaded. A standard value is one less than LOAD_SIZE
	 * 
	 * @param buffer
	 *            positive integer that should be less than LOAD_SIZE
	 */
	public void setloadBuffer(int buffer) {
		BUFFER = buffer;
	}

	/**
	 * Setting the renderDistance for tiles. All tiles within this distance will
	 * be rendered.
	 * 
	 * @param r
	 *            positive integer less than or equal to LOAD_SIZE
	 */
	public void setTileRenderDistance(int r) {
		RENDER_DISTANCE_TILE = r;
	}

	/**
	 * Setting the range in which all entities are rendered. Should be around
	 * double the TILE_SIZE for best results.
	 * 
	 * @param r
	 *            positive integer
	 */
	public void setEntityRenderDistance(int r) {
		RENDER_DISTANCE_ENTITY = r;
	}

	/**
	 * Add a custom TileHandler.
	 * 
	 * @param e
	 *            TileHandler
	 */
	public void addTileHandler(TileHandler e) {
		tileHandler = e;
	}

	/**
	 * Add a custom InputHandler.
	 * 
	 * @param e
	 *            InputHandler
	 */
	public void addInputHandler(InputHandler e) {
		inputHandler = e;
	}

	/**
	 * Add a custom EntityHandler.
	 * 
	 * @param e
	 *            EntityHandler
	 */
	public void addEntityHandler(EntityHandler e) {
		entityHandler = e;
	}

	/**
	 * Add a custom Spawner.
	 * 
	 * @param e
	 *            Spawner
	 */
	public void addSpawner(Spawner e) {
		spawnHandler = e;
	}

	/**
	 * Add a custom SaveHandler.
	 * 
	 * @param e
	 *            SaveHandler
	 */
	public void addSaveHandler(SaveHandler e) {
		saveHandler = e;
	}

	/**
	 * Add a custom TerrainGenerator.
	 * 
	 * @param e
	 *            TerrainGenerator
	 */
	public void addTerrainGenerator(TerrainGenerator e) {
		terrainGenerator = e;
	}

	/**
	 * Add a custom RenderQueue.
	 * 
	 * @param e
	 *            RenderQueue
	 */
	public void addRenderQueue(RenderQueue e) {
		renderQueue = e;
	}

	/**
	 * Add a custom SpriteSheetLoader.
	 * 
	 * @param e
	 *            SpriteSheetLoader
	 */
	public void addSpriteSheetLoader(SpriteSheetLoader e) {
		spriteSheetLoader = e;
	}

	/**
	 * Set a player to be used during the game.
	 * 
	 * @param p
	 *            Player
	 */
	public void setPlayer(Player p) {
		player = p;
	}

	/**
	 * Starts the window and game
	 */
	public void start() {
		this.run();
	}

	// This is the loop which is run until the game is closed
	@Override
	public void run() {
		JFrame frame = new JFrame(name);
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

				entityHandler.tick(this, player);
				spawnHandler.spawnEntities(this, player);

				// do all player key actions
				// ******************
				if (inputHandler.getKeyPressed("W")) {
					player.move(playerRotation - rotation);
				}
				if (inputHandler.getKeyPressed("Space") && player.canAttack()) {
					entityHandler.playerInteract(this, player, playerRotation - rotation);
					player.resetAttackCounter();
				}

				if (inputHandler.getKeyPressed("Escape")) {
					saveHandler.saveAll(this, player);
					savePlayer(player);
					System.out.println("Game Saved");
					System.exit(ABORT);
				}

			}

		}

	}

	// This is the graphics method in the window
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);

		setPlayerRotation();
		setBoardRotation();

		if (inputHandler.getKeyPressed("E")) {
			Tab = 1;
			player.inventory.render(this, g2d);
		} else { // in game
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

	/*
	 * Private method to get the rotation of the board from the mouses scroll.
	 */
	private void setBoardRotation() {
		rotation = (2 * Math.PI * inputHandler.getScroll() / 100) % (Math.PI * 2);
		if (rotation < 0) {
			rotation += Math.PI * 2;
		}
		Variables.setRotation(rotation);
	}

	/*
	 * Private method to get the player rotation from the mouse position.
	 */
	private void setPlayerRotation() {
		Point mouse = getMouse();
		double dx = -mouse.getX() + getCurrentWidth() / 2 + this.getLocationOnScreen().getX();
		double dy = -mouse.getY() + getCurrentHeight() / 2 + this.getLocationOnScreen().getY();
		double angle = Math.atan2(dy, dx) + Math.PI;
		player.setRotation(angle);
		playerRotation = angle;
	}

	/**
	 * Method to get the point on screen where the mouse is located
	 * 
	 * @return a Point object representing the mouses location
	 */
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

	/**
	 * Gets the seed for the game either from the save file or, if that fails,
	 * then a random one is generated.
	 * 
	 * @return Long representing the SEED for the game
	 */
	private long getSeed() {

		String saveDataFile = DEFAULT_SAVE_DIR + "save.txt";

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
				// failed to read seed from file
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
				// failed to write new seed to file
				e.printStackTrace();
			}
		}

		// seed if file save failed.
		return 0;

	}

	/**
	 * This attempts to load a player from the save folder. If it fails it
	 * returns a default player.
	 * 
	 * @return Player
	 */
	private Player loadPlayer() {
		try {
			FileInputStream fis = new FileInputStream(DEFAULT_SAVE_DIR + "player.ser");
			ObjectInputStream in = new ObjectInputStream(fis);
			Player p = (Player) in.readObject();
			in.close();
			fis.close();
			p.updateTexture(this);
			p.inventory.reloadItemTextures(this);
			return p;
		} catch (FileNotFoundException ex) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}

		System.out.println("player load failed");

		// default player
		return new Player(this, 0, 0, new int[] { 1 }, new int[] { 0 });
	}

	/**
	 * This saves the player into the save folder.
	 * 
	 * @param p
	 *            Player to be saved
	 */
	private void savePlayer(Player p) {
		try {
			String save = DEFAULT_SAVE_DIR + "player.ser";

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

	public SpriteSheetLoader getSpriteSheetLoader() {
		return spriteSheetLoader;
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public Spawner getSpawnHandler() {
		return spawnHandler;
	}

	public SaveHandler getSaveHandler() {
		return saveHandler;
	}

	public RenderQueue getRenderQueue() {
		return renderQueue;
	}

	public String getDefaultSpriteSheet() {
		return this.DEFAULT_SPRITE_SHEET;
	}

	public String getDefaultSaveDirectory() {
		return this.DEFAULT_SAVE_DIR;
	}

}
