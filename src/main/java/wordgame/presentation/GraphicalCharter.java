package wordgame.presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class GraphicalCharter {
	
	private static final String IMAGES_FOLDER_PATH = "images/";
	
	// Colors
	public static final Color BACKGROUND = Color.DARK_GRAY;
	public static final Color REVERSE_BACKGROUND = Color.LIGHT_GRAY;
	public static final Color TEXT = Color.WHITE;
	public static final Color REVERSE_TEXT = Color.BLACK;
	
	// Fonts
	public static final Font BASIC_FONT = new Font("Calibri", Font.PLAIN, 24);
	public static final Font BAG_FONT = new Font("Arial", Font.BOLD, 28);
	
	// Buttons
	public static final ImageIcon BUTTON_PASS = 
			resizeImageIcon(new ImageIcon(IMAGES_FOLDER_PATH + "PASSER.gif"), 120, 60);
	public static final ImageIcon BUTTON_CHANGE = 
			resizeImageIcon(new ImageIcon(IMAGES_FOLDER_PATH + "ECHANGER.gif"), 150, 60);
	public static final ImageIcon BUTTON_PLAY =
			resizeImageIcon(new ImageIcon(IMAGES_FOLDER_PATH + "VALIDER.gif"), 120, 60);
	
	// Cells
	public static ImageIcon getCell(String imgSuffix) {
		return new ImageIcon(IMAGES_FOLDER_PATH + "C_" + imgSuffix.toUpperCase() + ".gif");
	}
	
	// Tiles
	public static ImageIcon getTile(String letter) {
		return new ImageIcon(IMAGES_FOLDER_PATH + letter.toLowerCase() + ".gif");
	}
	
	// Dragged tiles
	public static ImageIcon getDraggedTile(String letter) {
		return new ImageIcon(IMAGES_FOLDER_PATH + "cur_" + letter.toLowerCase() + ".gif");
	}
	
	// Bag
	public static final ImageIcon BAG = new ImageIcon(IMAGES_FOLDER_PATH + "BAG.png");
	
	public static ImageIcon resizeImageIcon(ImageIcon img, int w, int h) {
		BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resized.createGraphics();
		
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.drawImage(img.getImage(), 0, 0, w, h, null);
		graphics.dispose();
		
		return new ImageIcon(resized);
	}
}
