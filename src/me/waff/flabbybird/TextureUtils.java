package me.waff.flabbybird;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class TextureUtils {

	
	public static BufferedImage rotate180(BufferedImage img, boolean flip){
		int width = img.getWidth();
		int height = img.getHeight();
		
		BufferedImage newImage = new BufferedImage(width, height, img.getType());
		
		Graphics2D g2d = newImage.createGraphics();
		g2d.rotate(Math.toRadians(180), width / 2, height / 2);
		g2d.drawImage(img, null, 0, 0);
		
		if (flip){
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-newImage.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			newImage = op.filter(newImage, null);
		}
		
		return newImage;
	}
	
	public static BufferedImage rotate(BufferedImage img, double angle){
		int width = img.getWidth();
		int height = img.getHeight();
		
		BufferedImage newImage = new BufferedImage(width, height, img.getType());
		
		Graphics2D g2d = newImage.createGraphics();
		g2d.rotate(Math.toRadians(angle), width / 2, height / 2);
		g2d.drawImage(img, null, 0, 0);
		
		return newImage;
	}
	
	
}
