package me.waff.flabbybird;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
		
	private boolean isJumpKeyDown = false; 
	private boolean isUpKeyDown = false;
	private boolean isDownKeyDown = false;
	
	public boolean jumpAcknowledged = false;
	
	private boolean leftClick = false;
	private Point clickCoords;
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_SPACE:
			isJumpKeyDown = true;
			return;
		case KeyEvent.VK_UP:
			isUpKeyDown = true;
			return;
		case KeyEvent.VK_DOWN:
			isDownKeyDown = true;
		default:
			return;
		}
		
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
		
		switch (e.getKeyCode()){
		case KeyEvent.VK_SPACE:
			isJumpKeyDown = false;
			jumpAcknowledged = false;
			return;
		case KeyEvent.VK_UP:
			isUpKeyDown = false;
			return;
		case KeyEvent.VK_DOWN:
			isDownKeyDown = false;
		default:
			return;
		}
		
		 
	}
	
	public boolean isKeyDown(InputKey i){	
		if (i == InputKey.JUMP)
			return isJumpKeyDown;
		else if (i == InputKey.UP)
			return isUpKeyDown;
		else if (i == InputKey.DOWN)
			return isDownKeyDown;
		else
			return false;
	}
	
	public boolean isMouseButtonDown(int btn){
		switch(btn){
		case 1:
			return leftClick;
		default:
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()){
		case 1:
			leftClick = true;
			isJumpKeyDown = true;
			jumpAcknowledged = false;
			clickCoords = new Point(e.getX(), e.getY());
			return;
		default:
			return;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()){
		case 1:
			leftClick = false;
			isJumpKeyDown = false;
			Game.theGame.getGuiManager().mouseReleased(e);
			return;
		default:
			return;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	public Point getClickCoords(){ return this.clickCoords; }

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}


