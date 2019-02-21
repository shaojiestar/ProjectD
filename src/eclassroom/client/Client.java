package eclassroom.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client{
	 
	private Socket socket;
	
	private void GetConnetion(){
		try {
			socket=new Socket(InetAddress.getLocalHost(),2010);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end of try/catch
	}//end of method
	
	public Client() {
		GetConnetion();
//		new GetImage(socket);
		new SendImage(socket).start();
//		try {
//			Socket socket1=new Socket(InetAddress.getLocalHost(),2012);
//			new GetMakings(socket1).start();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}//end of try/catch
	}//end of method
	
	public static void main(String[] args) {
		new Client();
	}//end of main
	
	public void startGetImage(){
		new GetImage(socket);
	}//end of startGetImage()
}//end of class
