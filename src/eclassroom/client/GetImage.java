package eclassroom.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class GetImage extends JFrame{
	
	private BufferedImage bufferedImage;
	private Socket socket;
	
	public GetImage(Socket socket) {
		this.socket=socket;
		//this.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		this.setBounds(20, 20, 700, 700);
		setTitle(""+socket.getLocalAddress().getHostName());
		this.setVisible(true);
		this.setBackground(Color.cyan);
		new PaintThread().start();
		new GetSeverImage().start();
	}//end of method
	
	public void paint(Graphics g){
		//g.drawImage(bufferedImage, 0, 0,700,700, null);
		g.drawImage(bufferedImage, 0, 0, null);
	}//end of method
	
	private class PaintThread  extends Thread{
		public void run(){
			while(true){
				repaint();   //手动画窗口，调用该方法，不要调用paint方法！
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//end of try/catch
			}//end of while
		}//end of run
	}//end of inner class
	
	private class GetSeverImage extends Thread{
		public void run(){
			while (true) {
				try {
					BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
					JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(bufferedInputStream);
					bufferedImage=decoder.decodeAsBufferedImage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(ImageFormatException e){
					e.printStackTrace();
				}//end of try/catch
			}//end of while
		}//end of run
	}//end of inner class
	
	private class GetFile extends Thread{
		public void run(){
			try {
				DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end of try/catch
		}//end of run
	}//end of inner class
	
}//end of outer class
