package eclassroom.server.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class ServerInformation {
	private DatagramSocket socket;
	private MulticastSocket socketMulti;
	private InetAddress addressMulti;
	private int portMulti;//进行广播的port
	private int port;//进行UDP通信的port
	private String str;//要发送的语句
	private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));	
	public ServerInformation(int port,int portMulti) {
		this.port=port;
		this.portMulti=portMulti;		
		try {
			socket=new DatagramSocket(this.port);
			socketMulti=new MulticastSocket(this.portMulti);
			addressMulti=InetAddress.getByName("224.224.10.0");//多播地址
			socketMulti.joinGroup(addressMulti);//加入多播组
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized  void ReceiveInformation(){
		DatagramPacket packet;
		byte[] newData =new byte[1024];		
		while(true){	
			
			 // 创建byte数组
			packet = new DatagramPacket(newData, newData.length); // 待接收的数据包
			try {
				socket.receive(packet);// 接收数据包
				newData=packet.getData();
			} catch (IOException e) {
				System.out.println("接收失败！");
			} //end of try-catch
			String message = new String(newData, 0, packet.getLength()); // 获取数据包中内容
			System.out.println(message);
//			SendInformation();//当接收消息时就回信息，
		}//end of while()
	}
	public synchronized  void SendInformation(String str){
		DatagramPacket packet;		
		try {
			
//			System.out.println("请输入：");
//			String str=br.readLine();//输入指令
			System.out.println("SendInformation");
			byte[] data=str.getBytes();//把指令转成字节数组
			packet=new DatagramPacket(data,data.length,addressMulti,portMulti);//打包数据报
			socketMulti.send(packet);//发送数据报
		} catch (IOException e) {
			e.printStackTrace();
		}//end of try-catch
		
		Thread.currentThread().interrupt();
		
	}
//	
//	public static void main(String[] args) {
//		ServerInformation server=new ServerInformation(60000,33000);
//		server.ReceiveInformation();
////		server.SendInformation(str)
//	}
}
