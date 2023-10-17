package me.waff.flabbybird;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelManager {
	
	private Random random = new Random();
	private final int pipeGap = 150;
	
	private List<Pipe> pipes;
	private Floor floor;
	
	private BufferedImage background;
	
	public LevelManager(){
		this.pipes = new ArrayList<Pipe>();
		this.floor = new Floor();
		
		pipes.add(new Pipe(175, 0, 10, (Game.HEIGHT - (10 + 4)*32)/32));
		for (int i = 0; i < 2; i++){
			addPipe();
		}
		
		this.background = TextureLoader.getInstance().getBackground();
		
	}
	
	public void tick(){
		List<Pipe> appendList = new ArrayList<Pipe>();
		boolean addPipeOnThisTick = false;
		for (Pipe p : pipes){
			p.setX(p.getX() - 1);
			
			if (p.getX() < -32){
				appendList.add(p);
				addPipeOnThisTick = true;
			}
			
			p.tick();
		}
		
		for (Pipe p : appendList)
			pipes.remove(p);
				
		if (addPipeOnThisTick)
			addPipe();
				
		floor.tick();
	}
	
	public void render(Graphics2D g2d){
		g2d.drawImage(background, 0, 0, null);
		
		for (Pipe p : pipes){
			p.render(g2d);
		}
		
		floor.render(g2d);
	}
	
	private void addPipe(){
		int pipeLength = random.nextInt((15 - 2) + 2) + 2;
		pipes.add(new Pipe(175 + (pipes.size() * 32) + (pipeGap * pipes.size()), 0, pipeLength, (Game.HEIGHT - (pipeLength + 4)*32)/32));
	}
	
}
