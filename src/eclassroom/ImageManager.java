package eclassroom;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageManager {

	public static ImageIcon getImageIcon(String filename){
		return new ImageIcon(ImageManager.class.getResource("/eclassroom/resource/"+filename));
	}
	public static ImageIcon getPathImageIcon(String filename){
		return new ImageIcon(ImageManager.class.getResource(filename));
	}
	public static Image getImage(String filename){
		return new ImageIcon(ImageManager.class.getResource("/eclassroom/resource/"+filename)).getImage();
	}
	public static Image getPathImage(String filename){
		return new ImageIcon(ImageManager.class.getResource(filename)).getImage();
	}
}
