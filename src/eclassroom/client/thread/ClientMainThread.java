package eclassroom.client.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import eclassroom.client.view.ClientMainFrame;



public class ClientMainThread {
	private MulticastSocket clientSocket;// ���ͷ���ಥ��
	private DatagramSocket serverSocket;
	private static int serverPort = 40000;
	private static int clientPort = 50000;
	public static String serverIP=null;
	private ClientMainFrame cmf;
	public static String str="�����ǿͻ���";
	private static String addressMess=null;

	public static void setStr(String str) {
		try {
			ClientMainThread.str = str+InetAddress.getLocalHost().getHostName()+InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public ClientMainThread(ClientMainFrame cmf) {		
		try {
			this.cmf=cmf;
			addressMess=InetAddress.getLocalHost().getHostName()+InetAddress.getLocalHost().getHostAddress();
			str="�����ǿͻ���"+addressMess;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

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

			while (true) {

				DatagramPacket packet;
				byte[] newData = new byte[1024]; // ����byte����
				packet = new DatagramPacket(newData, newData.length); // �����յ����ݰ�
				try {
					clientSocket.receive(packet);// �������ݰ�
					cmf.setTeacherIP(packet.getAddress());//����õĽ�ʦ��ip����������
				} catch (IOException e) {
					System.out.println("����ʧ�ܣ�");
				} // end of try-catch
				String message = new String(packet.getData(), 0, packet.getLength()); // ��ȡ���ݰ�������
				System.out.println(message);
				if(message.contains("������ʦ")){
					SendThread send;
					try {
						serverIP=message.substring(4);
						send = new SendThread(clientPort,serverIP);
						new Thread(send).start();
					} catch (SocketException e) {
						e.printStackTrace();
					}catch (Exception e1){
						e1.printStackTrace();
					}
				}//��ȡ��ʦ��
				if(message.contains("�����ྲ")){
					ClientBlackScreenThread.begin();
				}//��׽��ʦ���ĺ����ྲ�¼�
				if(message.contains("�ļ��ַ�����")){
					String fileName=message.substring(6, message.length());
					ClientFileDesktop.begin(fileName);
				}//��׽��ʦ�����ļ��ַ��¼�
				if(message.contains("�ļ��ַ��ҵ��ĵ�")){
					String fileName=message.substring(8, message.length());
					ClientFileDocument.begin(fileName);
				}//��׽��ʦ�����ļ��ַ��¼�
				if(message.contains("�ļ��ַ��Զ���")){
					String fileName=message.substring(6, message.length());
					ClientFileOther.begin(fileName);
				}//��׽��ʦ�����ļ��ַ��¼�
				if(message.contains("�㲥��Ϣ")){
					String mess=message.substring(4, message.length());
					ClientMessageThread.begin(mess);
				}
			}// end of while()
		}// end of run()
	}// end of inner class SendThread
	
	public class SendThread implements Runnable {

		private InetAddress clientAddress;
		private int port;

		public SendThread(int port,String serverIP) throws SocketException {
			this.port=port;
			try {
				if (serverIP!=null) {
					clientAddress = InetAddress.getByName(serverIP);
				}else{
					System.out.println("��ȡServerIPʧ�ܣ�");
				}
				serverSocket = new DatagramSocket(this.port);
			} catch (UnknownHostException e) {
				System.out.println("��ȡIP����" + e.getMessage());
			} catch (Exception e1){
				// end of try-catch
			}

		}

		public void run() {
			while (true) {			
				try {
					DatagramPacket packet = null; // ����DatagramPacket����
					Thread.sleep(1000);
					if(str!=null){
						byte[] data =str.getBytes(); // �����ֽ�����
						System.out.println(new String(data)); // ���㲥��Ϣ���
						packet = new DatagramPacket(data, data.length,clientAddress,5000); // �����ݴ��
						serverSocket.send(packet);// ��������
					}				
					if(!str.equals("�����ǿͻ���"+addressMess)){
						str="�����ǿͻ���"+addressMess;
					}					
					break;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//end of try-catch				
			}// end of while()
		}// end of method run()
	}// end of inner class receiveThread
	
//	public void beginMainThread(){
//		ReceiveThread receive=new ClientMainThread(cmf).new ReceiveThread();
//		new Thread(receive).start();
//	}
//	
//	public static void begin(ClientMainFrame clientMainFrame){
//		ClientMainThread cmt=new ClientMainThread(clientMainFrame);
//		cmt.beginMainThread();
//	}
	
	public static String getServerIP(){
		return serverIP;
	}
	
}
