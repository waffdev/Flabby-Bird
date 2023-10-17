package me.waff.flabbybird;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window {
	
	private JFrame frame;
	
	public Window(String title, int width, int height, Game g){
		this.frame = new JFrame(title);
		Dimension d = new Dimension(width, height);
		g.setMinimumSize(d);
		g.setMaximumSize(d);
		g.setPreferredSize(d);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(g);
		frame.pack();
		frame.setLocationRelativeTo(null);
		try {
			frame.setIconImage(ImageIO.read(Window.class.getResourceAsStream("/Flappy_Bird_icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setVisible(true);
		
	}
	
}
