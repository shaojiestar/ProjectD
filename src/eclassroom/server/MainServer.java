package eclassroom.server;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

import eclassroom.server.thread.ServerInformation;
import eclassroom.server.thread.ServerThread;
import eclassroom.server.view.ServerFrame;

public class MainServer {
	private static ServerInformation server=new ServerInformation(60000,33000);
	
	public static ServerInformation getServerMessage(){
		return server;
	}
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		ServerThread serverThread=new ServerThread();
		serverThread.startSendThread();
		serverThread.startReceiveThread();
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		ServerFrame serverFrame=new ServerFrame();
		serverFrame.setVisible(true);
		
		new Thread(new Runnable(){

			public void run() {
				System.out.println(Thread.currentThread().getName()+"接收学生信息");
				server.ReceiveInformation();
			}
			
		}).start();
	}
}
