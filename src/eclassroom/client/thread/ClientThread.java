package eclassroom.client.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;



public class ClientThread {

	private MulticastSocket clientSocket;// ���ͷ���ಥ��
	private DatagramSocket serverSocket;// ����
	private static int serverPort = 40000;
	private static int clientPort = 50000;
	private static String serverIP=null;

	public class ReceiveThread implements Runnable {
		private InetAddress serverAddress;

		public ReceiveThread() {
			try {
				serverAddress = InetAddress.getByName("224.224.10.0");
				clientSocket = new MulticastSocket(serverPort);
				clientSocket.joinGroup(serverAddress);
				clientSocket.setTimeToLive(1);
			} catch (final IOException e) {
			}// end of try-catch
		}

		public void run() {

//			while (true) {

				DatagramPacket packet;
				byte[] newData = new byte[1024]; // ����byte����
				packet = new DatagramPacket(newData, newData.length); // �����յ����ݰ�
				try {
					clientSocket.receive(packet);// �������ݰ�
				} catch (IOException e) {
					System.out.println("����ʧ�ܣ�");
				} // end of try-catch
				String message = new String(packet.getData(), 0, packet
						.getLength()); // ��ȡ���ݰ�������
				System.out.println(message);
				if(message.contains("������ʦ")){
					int index=(new String("������ʦ").length());
					serverIP=message.substring(index);
//					System.out.println(serverIP);
					try {
						SendThread send = new SendThread(clientPort);
						new Thread(send).start();
					} catch (SocketException e) {
						e.printStackTrace();
					}//end of try-catch
//					break;
				}//end of if
//			}// end of while()
		}// end of run()

	}// end of inner class SendThread

	public class SendThread implements Runnable {

		private InetAddress clientAddress;
		private int port;

		public SendThread(int port) throws SocketException {
			this.port=port;
			try {
				if (serverIP!=null) {
					clientAddress = InetAddress.getByName(serverIP);
				}				
				serverSocket = new DatagramSocket(this.port);
			} catch (UnknownHostException e) {
				System.out.println("��ȡIP����" + e.getMessage());
			} // end of try-catch

		}

		public void run() {
//			while(true){
				try {
					DatagramPacket packet = null; // ����DatagramPacket����
					String str="handsup";
					byte[] data =str.getBytes(); // �����ֽ�����
					packet = new DatagramPacket(data, data.length,clientAddress,5000);// �����ݴ��
					System.out.println(new String(data)); // ���㲥��Ϣ���
					serverSocket.send(packet);// ��������
					Thread.sleep(5000);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//end of try-catch		
//			}//end of while()				

		}// end of method run()

	}// end of inner class receiveThread

	public static void main(String[] args) {
		ReceiveThread receive=new ClientThread().new ReceiveThread();
		new Thread(receive).start();
	}
	
	
	
	public static String getServerIP(){
		return serverIP;
	}
}
