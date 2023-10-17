package me.waff.flabbybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class GuiOverlay {

	private Font scoreFont;
	private BufferedImage preGameMessage;
	
	public GuiOverlay(){
		try {
			//URL fontUrl = GuiOverlay.class.getResource("/04B_19__.ttf");
			scoreFont = Font.createFont(Font.TRUETYPE_FONT, GuiOverlay.class.getResourceAsStream("/04B_19__.TTF"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.preGameMessage = TextureLoader.getInstance().getMessage();
	}
	
	public void render(Graphics2D g2d){
		if (Game.theGame.preGame){
			g2d.drawImage(preGameMessage, (Game.WIDTH / 2) - (preGameMessage.getWidth() / 2), (Game.HEIGHT / 2) - (preGameMessage.getHeight() / 2), null);
		}
		
		String score = Integer.toString(Game.theGame.getThePlayer().getScore());
		
		g2d.setFont(scoreFont.deriveFont(Font.PLAIN, 56F));
		
		int x = (Game.WIDTH / 2) - (25*(score.length()-1));
		int y = 75;
		g2d.setColor(Color.BLACK);
		g2d.drawString(score, x+2, y+2); // drop shadow
		g2d.setColor(Color.WHITE);
		g2d.drawString(score, x, y);

	}
	
	
	
}
