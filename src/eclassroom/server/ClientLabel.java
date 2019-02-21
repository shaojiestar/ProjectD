package eclassroom.server;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClientLabel extends JLabel{
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage bufferedImage;
	private GetClientImage getClientImage;
	private int width;
	private int height;
	
	public ClientLabel(GetClientImage getClientImage,int width,int height) {
		this.getClientImage=getClientImage;
		this.width=width;
		this.height=height;
		new GetImage().start();
		new PaintThread().start();
	}
	
	public void paint(Graphics g){
		g.drawImage(bufferedImage, 0, 0,width,height, null);
	}
	
	private class PaintThread  extends Thread{
		public void run(){
			while(true){
				repaint();   //手动画窗口，调用该方法，不要调用paint方法！
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}//end of while
		}//end of run
	}//end of inner class
	
	private class GetImage extends Thread{
		public void run(){
			while(true){
				bufferedImage=getClientImage.getBufferedImage();
			}
		}//end of run
	}//end of inner class
	
}//end of outer class
