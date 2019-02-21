package eclassroom;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;



public class ZPanel extends JPanel {

	private static final long serialVersionUID = 6702278957072713279L;
	private Icon wallpaper;
	
	private Point loc = null;    
	private Point tmp = null;   
	private boolean isDragged = false;

	public ZPanel() {
		
	}

	protected void paintComponent(Graphics g) {
		if (null != wallpaper) {
			processBackground(g);
		}
//		System.out.println("f:paintComponent(Graphics g)");
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
	public void setDragable() {     
       	this.addMouseListener(new MouseAdapter() {   
       			public void mouseReleased(MouseEvent e) {   
       	   	       	isDragged = false; 
       	   	       ZPanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
       			}
       	            public void mousePressed(MouseEvent e) {      
       	            	   tmp = new Point(e.getX(), e.getY());      
       	            	   isDragged = true; 
       	            	ZPanel.this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
       	            }
       	});  
       this.addMouseMotionListener(new MouseMotionAdapter() {
       	   public void mouseDragged(MouseEvent e) {
       	   	   if(isDragged) {
       	   	   	  loc = new Point(ZPanel.this.getLocation().x + e.getX() - tmp.x,ZPanel.this.getLocation().y + e.getY() - tmp.y); 
       	   	   	  ZPanel.this.setLocation(loc);
       	   	   }
       	   	} 
       	}); 
}  
}
