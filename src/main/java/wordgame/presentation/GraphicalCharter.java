package wordgame.presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class GraphicalCharter {
	
	private static final String IMAGES_FOLDER_PATH = "images/";
	
	// Color
	public static final Color BACKGROUND = Color.DARK_GRAY;
	public static final Color REVERSE_BACKGROUND = Color.LIGHT_GRAY;
	public static final Color TEXT = Color.WHITE;
	public static final Color REVERSE_TEXT = Color.BLACK;
	
	// Font
	public static final Font BASIC_FONT = new Font("Calibri", Font.PLAIN, 24);
	
	// Cell
	public static final ImageIcon EMPTY_CELL = new ImageIcon(IMAGES_FOLDER_PATH + "C_VIDE.gif");
	
	public static ImageIcon resizeImageIcon(ImageIcon img, int w, int h) {
		BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resized.createGraphics();
		
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.drawImage(img.getImage(), 0, 0, w, h, null);
		graphics.dispose();
		
		return new ImageIcon(resized);
	}
}
