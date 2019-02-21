package projectd.server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Vector;


public class ServerThread {
	private static Vector<User> vector=new Vector<User>();
	private static MulticastSocket serverSocket;// ���ͷ���ಥ��
	private DatagramSocket clientSocket;// ����
	private static int serverPort=40000;
	private static int clientPort=5000;
	private SendThread send=new SendThread();
	private ReceiveThread receive=new ReceiveThread();
	private Thread sendThread;
	private Thread receiveThread;
	private static InetAddress serverAddress;
	
	static{
		try {
			serverAddress=InetAddress.getByName("224.224.10.0");
			serverSocket=new MulticastSocket(serverPort);
			serverSocket.joinGroup(serverAddress);
			serverSocket.setTimeToLive(1);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void startSendThread(){
				
		sendThread=new Thread(send);
		sendThread.start();		
	}
	public void startReceiveThread(){
		
		receiveThread=new Thread(receive);
		receiveThread.start();
	}
	public void closeSendThread(){
		sendThread.interrupt();
	}
	public void closeReceiveThread(){
		receiveThread.interrupt();
	}
	
	private class SendThread implements Runnable{
		
		private StringBuffer weather=new StringBuffer();
		public SendThread() {
			
			try {					
				String ip=(InetAddress.getLocalHost()).getHostAddress();
				weather.append("�㲥��Ϣ�����㲥������û��");
				if (ip!=null) {
					weather.append(ip);
				}	
			} catch (UnknownHostException e1) {
				System.out.println("��ȡIPʧ��");
			}//end of try-catch
		}

		public void run() {
			
			while (true) {
				while (true) {					
					try {
						DatagramPacket packet = null; // ����DatagramPacket����					
						byte data[] = weather.toString().getBytes(); // �����ֽ�����
						packet = new DatagramPacket(data, data.length, serverAddress, serverPort); // �����ݴ��
						System.out.println(new String(data)); // ���㲥��Ϣ���
						serverSocket.send(packet);// ��������	
						Thread.sleep(5000);
					} catch (IOException e) {
						System.out.println("����ʧ�ܣ�"+e.getMessage());//����쳣��Ϣ
					} catch (InterruptedException e) {
						System.out.println("�̳߳������⣺"+e.getMessage());
					}//end of try-catch
					
				}//end of while()
			}// end of while()
		}// end of run()
		
	}// end of inner class SendThread
	
	public class ReceiveThread implements Runnable{
		
//		private InetAddress clientAddress;
		private DatagramPacket packet=null;
		public ReceiveThread(){
			try{
				clientSocket=new DatagramSocket(clientPort);
			} catch (SocketException e) {
				System.out.println("����DatagramSocketʧ��"+e.getMessage());
			}//end of try-catch
			
		}
		public void run() {
			
			while(true){				
				byte[] newData = new byte[1024]; // ����byte����
				packet = new DatagramPacket(newData, newData.length); // �����յ����ݰ�
				try {
					clientSocket.receive(packet);// �������ݰ�
					InetAddress netAddress=packet.getAddress();
					User user=new User(netAddress,netAddress.getHostAddress(),netAddress.getHostName());
					vector.add(user);
					newData=packet.getData();
				} catch (IOException e) {
					System.out.println("����ʧ�ܣ�");
				} //end of try-catch
				String message = new String(newData, 0, packet.getLength()); // ��ȡ���ݰ�������
				System.out.println(message);
			}//end of while()
			
		}//end of method run()
		
	}//end of inner class receiveThread
	
	public static void main(String[] args) {
		ServerThread server=new ServerThread();		
		server.startSendThread();
		server.startReceiveThread();
	}//end of method main

	public Vector<User> getVector() {
		return vector;
	}
	
	public static MulticastSocket getServerSocket(){
		return serverSocket;
	}
}


