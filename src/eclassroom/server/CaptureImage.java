package eclassroom.server;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;


import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CaptureImage extends Thread {
	private Robot robot;
	private BufferedImage bufferedImage;
	private Socket socket;

	public CaptureImage(Socket socket) {
		this.socket = socket;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}// end of method

	public void run() {
		while (true) {
			bufferedImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			try {
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bufferedOutputStream);
				encoder.encode(bufferedImage);
			} catch (IOException e) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}//end of try/catch
		}//end of while
	}//end of run
	
}//end of class
