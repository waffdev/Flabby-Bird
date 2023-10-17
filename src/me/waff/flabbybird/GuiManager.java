package me.waff.flabbybird;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class GuiManager {
	
	private GuiOverlay overlayScreen;
	private GuiScoreboard scoreScreen;
	
	private boolean renderDeathEffect = false;
	private int deathEffectTimer = 60;
	
	public GuiManager(){
		this.overlayScreen = new GuiOverlay();
		this.scoreScreen = new GuiScoreboard();
	}
	
	public void tick(){
		if (!Game.theGame.getThePlayer().getAlive()){
			scoreScreen.tick();
		}
	}
	
	public void render( Graphics2D g2d){
		if (renderDeathEffect){
			if (deathEffectTimer <= 0) { renderDeathEffect = false; return; }
			g2d.setColor(new Color(255, 255, 255, 255 - (int)(4.25 * deathEffectTimer)));
			g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			deathEffectTimer--;
		}
	
		overlayScreen.render(g2d);
		
		if (!Game.theGame.getThePlayer().getAlive()){
			scoreScreen.render(g2d);
		}
	}
	
	public void onPlayerDie(){
		this.renderDeathEffect = true;
		scoreScreen.init();
	}
	
	public void mouseReleased(MouseEvent e){
		if (!Game.theGame.getThePlayer().getAlive()){
			scoreScreen.mouseReleased(e);
		}
	}
}
