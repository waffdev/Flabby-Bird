package me.waff.flabbybird;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player {
	
	// Texture Vars
	private int textureIndex = 1;
	private BufferedImage sprite;
	public int getTextureIndex() { return textureIndex; }
	
	// General Vars
	private int x,y;
	private float velY;
	private float tVo; // terminal velocity
	private boolean alive = true;
	private Rectangle colBox;
	
	private int score = 0;
	
	// Jump Physics
	private final double GRAVITY = .41D;
	private final int SHIFT = 5;
	private double delay = 0;
	
	// Getters & Setters
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public Rectangle getCollisionBox() { return this.colBox; }
	public int getScore() { return this.score; }
	public boolean getAlive() { return this.alive; }
	
	// Sprite Rotation Values
	private double rotateAngle = 0;
	private int ceilingAngle = -30;
	
	// Sound
	private SoundEffect sfxScore;
	private SoundEffect sfxWing;
	private SoundEffect sfxDie;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.velY = 5.0f;
		this.tVo = 5.0f;
		this.colBox = new Rectangle(28, 20);
		this.colBox.y = y;
		
		this.sfxScore = SoundEffect.POINT;
		this.sfxWing = SoundEffect.WING;
		this.sfxDie = SoundEffect.DIE;
		
		sprite = TextureLoader.getInstance().getSprite(textureIndex);
	}
	
	// Render the player 
	public void render(Graphics2D g2d) {
	
		if (Game.theGame.inGame){
			if (alive){
				if (rotateAngle < 90 && !(velY < 5f)){
					rotateAngle++;
				}
				
				if (velY < 5f && rotateAngle > ceilingAngle)
					rotateAngle--;	
			} else {
				if (rotateAngle < 90)
					rotateAngle += 5;
			}
		}
		
		
		g2d.drawImage(TextureUtils.rotate(sprite, rotateAngle), x, y, null);
		
		// debug collision code
		/*g2d.setColor(Color.GREEN);
		g2d.drawRect(colBox.x, colBox.y, colBox.width, colBox.height);*/
	}
	
	public void tick(){
		
		if (alive){
			colBox.x = x + 2;
			colBox.y = y + 5;
			
			InputHandler inputHandler = Game.theGame.getInputHandler();
			
			velY += GRAVITY;
			
			if (delay > 0)
				delay--;
			
			if (inputHandler.isKeyDown(InputKey.JUMP) && !inputHandler.jumpAcknowledged){
				jump();
				inputHandler.jumpAcknowledged = true;
			}
			
			
			if (velY > tVo)
				velY = tVo;
			
			y += (int) velY;
			
		}
		
	}
	
	public void jump(){
		if (delay < 1){
			velY = -SHIFT;
			delay = SHIFT;
		}
		sfxWing.play();
	}
	
	public void die(){
		if (alive) {
			sfxDie.play();
			this.alive = false;
			velY = 0;
			Game.theGame.onPlayerDie();
		}
	}
	
	public void incrementScore(){
		this.score++;
		sfxScore.play();
	}
	
	
}
