package eclassroom.server;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
/*
 * 屏幕监控
 */
public class ScreenMonitorFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage bufferedImage;
	private GetClientImage getClientImage;
	
	public ScreenMonitorFrame(GetClientImage getClientImage,final SendMakings sendMakings) {
		this.setLayout(new FlowLayout());
		//this.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		this.setBounds(20, 20, 700, 700);
		this.setVisible(true);
		this.getClientImage=getClientImage;
		
		//启动线程
		new PaintThread().start();
		new GetImage().start();
		
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				sendMakings.Send(e);
			}
			public void mouseEntered(MouseEvent e) {
				sendMakings.Send(e);
			}
			public void mouseExited(MouseEvent e) {
				sendMakings.Send(e);
			}
			public void mousePressed(MouseEvent e) {
				sendMakings.Send(e);
			}
			public void mouseReleased(MouseEvent e) {
				sendMakings.Send(e);
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e) {
				sendMakings.Send(e);
			}
			public void mouseMoved(MouseEvent e) {
				sendMakings.Send(e);
			}
		});
		
		addMouseWheelListener(new MouseWheelListener(){
			public void mouseWheelMoved(MouseWheelEvent e) {
				sendMakings.Send(e);
			}
			
		});
		
		addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				sendMakings.Send(e);
			}
			public void keyReleased(KeyEvent e) {
				sendMakings.Send(e);
			}
			public void keyTyped(KeyEvent e) {
				sendMakings.Send(e);
			}
		});	
	
	}//end of method
	
	public void paint(Graphics g){
		g.drawImage(bufferedImage, 700, 700, null);
		//g.drawImage(bufferedImage,Toolkit.getDefaultToolkit().getScreenSize().height,Toolkit.getDefaultToolkit().getScreenSize().height, null);
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
	
}//end  of outer class
