package me.waff.flabbybird;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private Window theWindow;
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	private InputHandler inputHandler;
	
	// Game loop vars
	private boolean running = false;
	private Thread thread;
	
	public static Game theGame;
	
	// Game vars
	private Player thePlayer;
	private LevelManager levelManager;
	private GuiManager guiManager;
	public boolean inGame = false;
	public boolean preGame = true;
	
	public Window getTheWindow() { return this.theWindow; }
	public Player getThePlayer() { return this.thePlayer; }
	public LevelManager getLevelManager() { return this.levelManager; }
	public InputHandler getInputHandler() { return this.inputHandler; }
	public GuiManager getGuiManager() { return this.guiManager; }
	
	public static void main(String[] args){
		theGame = new Game();
	}
	
	Game(){
		theWindow = new Window("Flabby Bird", WIDTH, HEIGHT, this);
		inputHandler = new InputHandler();
		levelManager = new LevelManager();
		guiManager = new GuiManager();
		this.addKeyListener(inputHandler);
		this.addMouseListener(inputHandler);
		this.start();
	}
	
	private synchronized void start(){
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();
		
		this.requestFocus();
		
		init();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void init() {
		TextureLoader.getInstance(); // create texture loader singleton if not already created 
		thePlayer = new Player(25, 25);
	}
	
	public void tick() {
		this.requestFocus();
		
		guiManager.tick();
		
		if (inGame){
			levelManager.tick();
			thePlayer.tick();
		} else {
			if (inputHandler.isKeyDown(InputKey.JUMP) && preGame){
				inGame = true;
				preGame = false;
				return;
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		
		g2d.setColor(new Color(0x91fff9));
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		levelManager.render(g2d);
		thePlayer.render(g2d);
		guiManager.render(g2d);
		
		g2d.dispose();
		bs.show();
	}
	
	public void onPlayerDie(){
		guiManager.onPlayerDie();
		this.inGame = false;
	}
	
	public void reset(){
		thePlayer = new Player(25, 25);
		levelManager = new LevelManager();
		inGame = false;
		preGame = true;
		
	}
	
	
}
