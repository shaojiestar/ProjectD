package eclassroom.server;

import java.awt.event.InputEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class SendMakings extends Thread{
	
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	
	public void run() {
		try {
			while(true){
				this.socket=new ServerSocket(2012).accept();
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			}
		}catch(BindException e){
			System.out.println("rock you");
		}catch (IOException e1) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			e1.printStackTrace();
		}//end of try/catch	
	}//end of run
	
	public void Send(InputEvent event){
		if(socket == null){
			return;
		}
		try {
			objectOutputStream.writeObject(event);
			objectOutputStream.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}//end of try/catch
	}//end of method
	
}//end of class


