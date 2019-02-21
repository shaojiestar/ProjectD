package eclassroom.server;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

import java.net.Socket;


import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class GetClientImage extends Thread{
	
	private Socket socket;
	private BufferedImage bufferedImage;
	
	public GetClientImage(Socket socket) {
		this.socket=socket;
	}
	
	public void run(){
		while (true) {
			try {
				BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
				JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(bufferedInputStream);
				bufferedImage=decoder.decodeAsBufferedImage();
			} catch (IOException e) {
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}catch(ImageFormatException e){
				
			}//end of try/catch
		}//end of while
	}//end of run
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
}//end of class
	