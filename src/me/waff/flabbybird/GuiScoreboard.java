package me.waff.flabbybird;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GuiScoreboard {
	
	private BufferedImage scoreboardImg;
	private BufferedImage restartButton;
	float ticks = 0f;
	
	private int x;
	private int y;
	private int btnX;
	private int btnY;
	
	private Rectangle btnBounds;
	
	private Font scoreFont;
	
	public GuiScoreboard(){
		this.scoreboardImg = TextureLoader.getInstance().getScoreboard();
		this.restartButton = TextureLoader.getInstance().getRestartButton();
		this.x = (Game.WIDTH / 2) - (scoreboardImg.getWidth() / 2);
		this.y = (Game.HEIGHT / 2) - (scoreboardImg.getHeight() / 2);
		this.btnX = (Game.WIDTH / 2) - (restartButton.getWidth() / 2);
		this.btnY = ((Game.HEIGHT / 2) - (scoreboardImg.getHeight() / 2) + 150);
		this.btnBounds = new Rectangle(btnX, btnY, restartButton.getWidth(), restartButton.getHeight());
		init();
		
		try {
			scoreFont = Font.createFont(Font.TRUETYPE_FONT, Game.class.getResourceAsStream("/04B_19__.TTF"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick(){		

		if (ticks < 60){
			ticks++;	
		}
		
	}
	
	public void render(Graphics2D g2d){
		g2d.setFont(scoreFont.deriveFont(Font.PLAIN, 32F));
		String score = Integer.toString(Game.theGame.getThePlayer().getScore());
		
		if (ticks < 60){
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ticks/60));
			g2d.drawImage(scoreboardImg, x, (int)(y - (60 - ticks)), null);
			g2d.drawImage(restartButton, btnX, (int)(btnY - (60 - ticks)), null);
			g2d.setColor(Color.BLACK);
			g2d.drawString(score, (x + 40) + 2, (int)((y + 70) + 2) - (60 - ticks));
			g2d.setColor(Color.WHITE);
			g2d.drawString(score, x + 40, (int)((y + 70) - (60 - ticks)));
			
			return;
		}
		
		
		g2d.drawImage(scoreboardImg, x, y, null);
		g2d.setColor(Color.BLACK);
		g2d.drawString(score, (x + 40) + 2, (y + 70) + 2);
		g2d.setColor(Color.WHITE);
		g2d.drawString(score, x + 40, y + 70);
		
		g2d.drawImage(restartButton, btnX, btnY, null);
		
	}
	
	public void init(){
		ticks = 0;
	}
	
	public void mouseReleased(MouseEvent e){
		if (btnBounds.contains(e.getX(), e.getY())){
			Game.theGame.reset();
		}
	}
	
}
