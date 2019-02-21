package eclassroom.client.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ClientInformation {
	private DatagramSocket socket=null;
	private MulticastSocket socketMulti;
	private InetAddress addressMulti;
	private InetAddress addressClient ;
	private int serverPort=4400;
	
	public void sendInformation(String ip) {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		byte[] data=new byte[1024];
		try {
			addressClient=InetAddress.getByName(ip);
			socket=new DatagramSocket(serverPort);
			System.out.println("Enter:");
			String str=br.readLine();
			data=str.getBytes();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}//end of try-catch			
		DatagramPacket packet=new DatagramPacket(data,data.length,addressClient,60000);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}//end of try-catch
	}//end of method SendInformation
	
	public void receiveInformation(){
		byte[] data=new byte[1024];
		DatagramPacket packet=new DatagramPacket(data,data.length);
		try {
			addressMulti=InetAddress.getByName("224.224.10.0");
			socketMulti=new MulticastSocket(33000);
			socketMulti.joinGroup(addressMulti);
			socketMulti.receive(packet);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end of try-catch		
		String message=new String(data,0,packet.getLength());
		System.out.println(message);		
	}//end of receiveInformation()
	
	public static void main(String[] args) {
		ClientInformation client=new ClientInformation();
		client.sendInformation(ClientThread.getServerIP());
		client.receiveInformation();
		
	}
	
}
