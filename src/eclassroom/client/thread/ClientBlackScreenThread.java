package eclassroom.client.thread;

import projectd.client.gui.ClientBlackScreen;

public class ClientBlackScreenThread {

	public ClientBlackScreenThread() {
		
	}
	
	public void beginBlack(){
		ClientBlackScreen cbs=new ClientBlackScreen();
		cbs.setVisible(true);
		try {
			Thread.sleep(5000);
			cbs.setVisible(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void begin(){
		ClientBlackScreenThread cbst=new ClientBlackScreenThread();
		cbst.beginBlack();
	}
}
