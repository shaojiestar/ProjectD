package projectd.client.thread;

import projectd.client.gui.MessageFrame;


public class ClientMessageThread {
	private String message;
	public ClientMessageThread(String message) {
		this.message=message;
	}
	
	public void beginBlack(){
		MessageFrame cbs=new MessageFrame(message);
		cbs.setVisible(true);
		try {
			Thread.sleep(5000);
			cbs.setVisible(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void begin(String message){
		ClientMessageThread cmt=new ClientMessageThread(message);
		cmt.beginBlack();
	}

}
