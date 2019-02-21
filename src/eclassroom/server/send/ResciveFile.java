package eclassroom.server.send;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ResciveFile {

	private MulticastSocket clientSocket;// ���ͷ���ಥ��
	private DatagramSocket serverSocket;// ����
	private static int serverPort = 40000;
	private static int clientPort = 30000;
	private BufferedOutputStream fos;

	private class ReceiveThread implements Runnable {
		private InetAddress serverAddress;
		byte[] newData =new byte[8];

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
			
			try {
				fos = new BufferedOutputStream(new FileOutputStream(new File("c:\\x.rar"),true));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			while(true){
				DatagramPacket packet;
				 // ����byte����
				packet = new DatagramPacket(newData, newData.length); // �����յ����ݰ�
				try {
					clientSocket.receive(packet);// �������ݰ�
				} catch (IOException e) {
					System.out.println("����ʧ�ܣ�");
				} // end of try-catch
				byte data[]=packet.getData();
				int size=packet.getData().length;
				String message = new String(data, 0, packet.getLength());				// ��ȡ���ݰ�������
				if(message.equals("over")){
					System.out.println("������ܵ���OVER");
					break;
				}else{
					try {
						fos.write(data, 0, size);
						System.out.println("д��ɹ�"+"���д��Ĵ�СΪ"+size);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}		//end of while	
			try {	
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			}// end of run()
		}

	public static void main(String[] args) {
		ReceiveThread receive=new ResciveFile().new ReceiveThread();
		new Thread(receive).start();
		
	}
}
