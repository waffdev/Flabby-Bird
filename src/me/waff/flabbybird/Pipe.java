package me.waff.flabbybird;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Pipe {
	
	private BufferedImage imgPipeTop;
	private BufferedImage imgPipe;
	
	private int x;
	private int bottomPipeLength, topPipeLength; // length of the pipe BEFORE the top in tiles
	
	private Rectangle topColBox;
	private Rectangle btmColBox;
	private Rectangle scoreColBox;
	
	private boolean pointScored = false;
	
	public int getX() { return this.x; }
	//public int getY() { return this.y; }
	public int getBottomPipeLength() { return this.bottomPipeLength; }
	public int getTopPipeLength() { return this.topPipeLength; }
	public void setX(int x) { this.x = x; } 
	
	public Pipe(int x, int y, int bottomPipeLength, int topPipeLength){
		this.x = x;
		//this.y = y;
		this.bottomPipeLength = bottomPipeLength;
		this.topPipeLength = topPipeLength;
		imgPipeTop = TextureLoader.getInstance().getSprite(3);
		imgPipe = TextureLoader.getInstance().getSprite(4);
		
		topColBox = new Rectangle(32, 32*(topPipeLength + 1));
		topColBox.x = x;
		topColBox.y = 0;
		
		btmColBox = new Rectangle(32, 32*(bottomPipeLength + 1));
		btmColBox.x = x;
		btmColBox.y = Game.HEIGHT - (32 * (bottomPipeLength + 1));
		
		scoreColBox = new Rectangle(32, 2*32);
		scoreColBox.x = x;
		scoreColBox.y = (topPipeLength+1)*32;
	}
	
	public void tick() {
		topColBox.x = x;
		btmColBox.x = x;
		scoreColBox.x = x;
		
		Player p = Game.theGame.getThePlayer();
		if (p.getCollisionBox().intersects(topColBox) || p.getCollisionBox().intersects(btmColBox)){
			p.die();
		} else if (p.getCollisionBox().intersects(scoreColBox)){
			if (!pointScored){
				p.incrementScore();
				pointScored = true;
			}
		}
	
	}
	
	public void render(Graphics2D g2d){
		
		// Draw bottom pipe
		for (int i = 0; i < bottomPipeLength; i++){
			g2d.drawImage(imgPipe, this.x, Game.HEIGHT - 32*(i+1), null); // i + 1, draws image at top left, working from bottom it needs to be one higher
		}
		g2d.drawImage(imgPipeTop, this.x, Game.HEIGHT - 32*(bottomPipeLength+1), null);
		
		// Draw top pipe
		for (int i = 0; i < topPipeLength; i++){
			g2d.drawImage(imgPipe, this.x, 32*i, null);
		}
		g2d.drawImage(TextureUtils.rotate180(imgPipeTop, true), this.x, (32*topPipeLength), null);
		
		// debug collisison code
		/*g2d.setColor(Color.RED);
		g2d.drawRect(topColBox.x, topColBox.y, topColBox.width, topColBox.height);
		g2d.drawRect(btmColBox.x, btmColBox.y, btmColBox.width, btmColBox.height);
		g2d.setColor(Color.YELLOW);
		g2d.drawRect(scoreColBox.x, scoreColBox.y, scoreColBox.width, scoreColBox.height);*/
	}
	
}
