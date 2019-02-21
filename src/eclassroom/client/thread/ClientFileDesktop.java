package eclassroom.client.thread;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class ClientFileDesktop {
	private MulticastSocket clientSocket;// ���ͷ���ಥ��
	private static int serverPort = 44444;
	private BufferedOutputStream fos;
	private String file;
	
	public ClientFileDesktop(String file) {
		this.file=file;
	}

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
			String address="C:\\Documents and Settings\\Administrator\\����\\"+file;
//			String address="C:\\Users\\luorcy\\Desktop\\"+file;
			try {			
				fos = new BufferedOutputStream(new FileOutputStream(new File(address),true));
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
				String message = new String(data, 0, packet.getLength());// ��ȡ���ݰ�������
				if(message.equals("over")){
					break;
				}else{
					try {
						fos.write(data, 0, size);
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

	public  void  beginReceive() {
		ReceiveThread receive=new ClientFileDesktop(file).new ReceiveThread();
		new Thread(receive).start();
		
	}
	
	public static void begin(String file){
		ClientFileDesktop cfd=new ClientFileDesktop(file);
		cfd.beginReceive();
	}

}
