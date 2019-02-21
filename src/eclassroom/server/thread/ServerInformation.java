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
	private int portMulti;//���й㲥��port
	private int port;//����UDPͨ�ŵ�port
	private String str;//Ҫ���͵����
	private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));	
	public ServerInformation(int port,int portMulti) {
		this.port=port;
		this.portMulti=portMulti;		
		try {
			socket=new DatagramSocket(this.port);
			socketMulti=new MulticastSocket(this.portMulti);
			addressMulti=InetAddress.getByName("224.224.10.0");//�ಥ��ַ
			socketMulti.joinGroup(addressMulti);//����ಥ��
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
			
			 // ����byte����
			packet = new DatagramPacket(newData, newData.length); // �����յ����ݰ�
			try {
				socket.receive(packet);// �������ݰ�
				newData=packet.getData();
			} catch (IOException e) {
				System.out.println("����ʧ�ܣ�");
			} //end of try-catch
			String message = new String(newData, 0, packet.getLength()); // ��ȡ���ݰ�������
			System.out.println(message);
//			SendInformation();//��������Ϣʱ�ͻ���Ϣ��
		}//end of while()
	}
	public synchronized  void SendInformation(String str){
		DatagramPacket packet;		
		try {
			
//			System.out.println("�����룺");
//			String str=br.readLine();//����ָ��
			System.out.println("SendInformation");
			byte[] data=str.getBytes();//��ָ��ת���ֽ�����
			packet=new DatagramPacket(data,data.length,addressMulti,portMulti);//������ݱ�
			socketMulti.send(packet);//�������ݱ�
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
