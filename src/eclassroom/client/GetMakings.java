package eclassroom.client;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;

public class GetMakings extends Thread {
	
	private Robot robot;
	private Socket socket;
	
	public GetMakings(Socket socket) {
		this.socket = socket;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.out.println("AWTException");
		}//end of try/catch
	}//end of method

	public void run() {
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream((socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}//end of try/catch
		while (true) {
			try {
				if (objectInputStream == null) {
					System.out.println("Á÷ÊÇ¿ÕµÄ");
				}
				if (objectInputStream != null) {
					InputEvent event = (InputEvent) objectInputStream.readObject();
					handMakings(event);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}//end of try/catch
		}//end of while
	}//end of run

	private void handMakings(InputEvent event) {
		
		MouseEvent eMouseEvent;
		MouseWheelEvent mouseWheelEvent;
		KeyEvent event2;
		int MouseButton = 0;
		switch (event.getID()) {
		case MouseEvent.MOUSE_DRAGGED:
			eMouseEvent = (MouseEvent) event;
			robot.mouseMove(eMouseEvent.getX(), eMouseEvent.getY());
			break;
		case MouseEvent.MOUSE_MOVED:
			eMouseEvent = (MouseEvent) event;
			robot.mouseMove(eMouseEvent.getX(), eMouseEvent.getY());
			break;
		case MouseEvent.MOUSE_PRESSED:
			eMouseEvent = (MouseEvent) event;
			robot.mouseMove(eMouseEvent.getX(), eMouseEvent.getY());
			MouseButton = GetMouseButton(eMouseEvent.getButton());
			if (MouseButton != 0) {
				robot.mousePress(MouseButton);
			}
			break;
		case MouseEvent.MOUSE_RELEASED:
			eMouseEvent = (MouseEvent) event;
			robot.mouseMove(eMouseEvent.getX(), eMouseEvent.getY());
			MouseButton = GetMouseButton(eMouseEvent.getButton());
			if (MouseButton != 0) {
				robot.mouseRelease(MouseButton);
			}
			break;
		case MouseEvent.MOUSE_WHEEL:
			mouseWheelEvent = (MouseWheelEvent) event;
			robot.mouseWheel(mouseWheelEvent.getWheelRotation());
			break;
		case KeyEvent.KEY_PRESSED:
			event2 = (KeyEvent) event;
			robot.keyPress(event2.getKeyCode());
			break;
		case KeyEvent.KEY_RELEASED:
			event2 = (KeyEvent) event;
			robot.keyRelease(event2.getKeyCode());
			break;
		}//end of switch/case
	}//end of method

	private int GetMouseButton(int button) {
		if (button == MouseEvent.BUTTON1) {
			return InputEvent.BUTTON1_MASK;
		}
		if (button == MouseEvent.BUTTON2) {
			return InputEvent.BUTTON2_MASK;
		} else {
			return 0;
		}
	}//end of method
	
}//end of class
