package eclassroom.client;

import eclassroom.client.view.ClientMainFrame;
import eclassroom.client.thread.ClientInformation;
import eclassroom.client.thread.ClientMainThread;
import eclassroom.client.thread.ClientMainThread.ReceiveThread;


public class MainClient {
	public static void main(String[] args) {
		
		ClientMainFrame clientFrame=new ClientMainFrame();
		clientFrame.setVisible(false);
		ReceiveThread receive=new ClientMainThread(clientFrame).new ReceiveThread();
		new Thread(receive).start();
		
		ClientInformation clientInformation=new ClientInformation();
		clientInformation.receiveInformation();
	}//end of class MainClient
}
