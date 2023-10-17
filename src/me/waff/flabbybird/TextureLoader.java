package me.waff.flabbybird;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextureLoader {
	
	private final String texturesPath = "/textures.png";
	private BufferedImage textureAtlas;
	private final int TILE_WIDTH = 32, TILE_HEIGHT = 32;
	
	private final String backgroundPath = "/flappy-background.png";
	private BufferedImage background;
	
	private final String messagePath = "/message.png";
	private BufferedImage message;
	
	private final String scoreboardPath = "/scoreboard.png";
	private BufferedImage scoreboard;
	
	private final String restartBtnPath = "/restartButton.png";
	private BufferedImage restartBtn;
	
	public static TextureLoader instance;
	
	public static TextureLoader getInstance(){
		if (instance == null){
			instance = new TextureLoader();
		}
		return instance;
	}
	
	public TextureLoader(){
		loadTextures();
	}
	
	private void loadTextures(){
		try {
			this.textureAtlas = ImageIO.read(Game.class.getResourceAsStream(texturesPath));
			this.background = ImageIO.read(Game.class.getResourceAsStream(backgroundPath));
			this.message = ImageIO.read(Game.class.getResourceAsStream(messagePath));
			this.scoreboard = ImageIO.read(Game.class.getResourceAsStream(scoreboardPath));
			this.restartBtn = ImageIO.read(Game.class.getResourceAsStream(restartBtnPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int index){
		int imageRowIndex = (int) Math.floor(index / 8);
		int imageColIndex = index - (imageRowIndex*TILE_HEIGHT);
			
		return textureAtlas.getSubimage(imageColIndex*TILE_WIDTH, imageRowIndex*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
	}
	
	public BufferedImage getBackground() { return this.background; }
	public BufferedImage getMessage() { return this.message; }
	public BufferedImage getScoreboard() { return this.scoreboard; }
	public BufferedImage getRestartButton() { return this.restartBtn; }
	
}
