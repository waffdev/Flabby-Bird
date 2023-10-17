package me.waff.flabbybird;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Floor {
	
	private Rectangle colBox;
	
	private List<FloorTile> floorTiles;
	private BufferedImage topTexture;
	private BufferedImage btmTexture;
	
	public BufferedImage getTopTexture() { return this.topTexture; }
	public BufferedImage getBottomTexture() { return this.btmTexture; } 
	
	public Floor() {
		floorTiles = new ArrayList<FloorTile>();
		colBox = new Rectangle(Game.WIDTH, Game.HEIGHT - 32*2);
		
		colBox.x = 0;
		colBox.y = Game.HEIGHT - 32*2;
		
		topTexture = TextureLoader.getInstance().getSprite(5);
		btmTexture = TextureLoader.getInstance().getSprite(6);
		
		for (int i = 0; i < (Game.WIDTH / 32) + 2; i++) { // add two tiles extra on the X for a buffer
			floorTiles.add(new FloorTile(i*32, Game.HEIGHT - 32*2, FloorTileType.TOP, this));
			floorTiles.add(new FloorTile(i*32, Game.HEIGHT - 32, FloorTileType.BOTTOM, this));
		}
		
	}
	
	public void tick(){
		List<FloorTile> tilesToRemove = new ArrayList<FloorTile>();
		List<FloorTile> tilesToAdd = new ArrayList<FloorTile>();
		
		for (FloorTile tile : floorTiles){
			if (tile.getX() < -32){
				tilesToRemove.add(tile);
				tilesToAdd.add(new FloorTile(Game.WIDTH + 30, Game.HEIGHT - 32*2, FloorTileType.TOP, this)); // not a clue why but +30 because +32 creates tiny gaps???
				tilesToAdd.add(new FloorTile(Game.WIDTH + 30, Game.HEIGHT - 32, FloorTileType.BOTTOM, this));
			}
			
			tile.setX(tile.getX() - 1);
		}
		
		for (FloorTile tile : tilesToAdd)
			floorTiles.add(tile);
		
		for (FloorTile tile : tilesToRemove)
			floorTiles.remove(tile);
				
		Player p = Game.theGame.getThePlayer();
		if (p.getCollisionBox().intersects(colBox))
			p.die();
	}
	
	public void render(Graphics2D g2d){
		for (FloorTile tile : floorTiles){
			tile.render(g2d);
		}
	}
	
}

class FloorTile {
	
	private int x, y;
	private FloorTileType type = FloorTileType.TOP;
	private BufferedImage texture;
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public FloorTileType getType() { return this.type; }
	
	public void setX(int x) { this.x = x; }
	
	FloorTile(int x, int y, FloorTileType type, Floor f){
		this.x = x;
		this.y = y;
		this.type = type;
		
		if (type == FloorTileType.TOP)
			this.texture = f.getTopTexture();
		else if (type == FloorTileType.BOTTOM)
			this.texture = f.getBottomTexture();
	}
	
	public void render(Graphics2D g2d){
		g2d.drawImage(texture, x, y, null);
	}
	
}

enum FloorTileType {
	TOP, BOTTOM;
}
