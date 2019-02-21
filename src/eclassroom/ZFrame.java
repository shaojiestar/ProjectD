package eclassroom;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ZFrame extends JFrame {

	private static final long serialVersionUID = 6702278957072713279L;
	private Icon wallpaper;

	public ZFrame() {
		
	}

	protected void paintComponent(Graphics g) {
		if (null != wallpaper) {
			processBackground(g);
		}
		System.out.println("f:paintComponent(Graphics g)");
	}

	public void setBackground(Icon wallpaper) {
		this.wallpaper = wallpaper;
		this.repaint();
	}

	private void processBackground(Graphics g) {
		ImageIcon icon = (ImageIcon) wallpaper;
		Image image = icon.getImage();
		int cw = getWidth();
		int ch = getHeight();
		int iw = image.getWidth(this);
		int ih = image.getHeight(this);
		int x = 0;
		int y = 0;
		while (y <= ch) {
			g.drawImage(image, x, y, this);
			x += iw;
			if (x >= cw) {
				x = 0;
				y += ih;
			}
		}
	}
}

