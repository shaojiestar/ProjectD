package eclassroom.server.send;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SendThreadFour {

	private static InetAddress serverAddress;
	private static MulticastSocket serverSocket;// ���ͷ���ಥ��
	private static int serverPort = 44444;
	private ViewFile vf;

	public SendThreadFour(ViewFile vf) {
		this.vf = vf;
	}

	static {
		try {
			serverAddress = InetAddress.getByName("224.224.10.0");
			serverSocket = new MulticastSocket(serverPort);
			serverSocket.joinGroup(serverAddress);
			serverSocket.setTimeToLive(1);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void beginSend(){
		SendThread send=new SendThread();
		new Thread(send).start();
	}
	
	class SendThread implements Runnable{

	public void run() {
		try {
			
			DatagramPacket packet = null; // ����DatagramPacket����
			File file=new File(vf.getLblShow().getText());
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
			vf.getProgressBar().setMaximum((int) file.length());//���ý������ĳ���
			byte[] data = new byte[8];
			try {
				int size = fis.read(data);
				while(size!=-1){
					packet = new DatagramPacket(data, data.length, serverAddress, serverPort); // �����ݴ��
					serverSocket.send(packet);// ��������
					size = fis.read(data);
					//���¿��ƽ�����
					int value=vf.getProgressBar().getValue();
					if(value<vf.getProgressBar().getMaximum()){						
						vf.getProgressBar().setValue(value+8);
					}else{
						break;
					}							
				}//end of while
				fis.close();//�ر��ļ���
				data=new String("over").getBytes();
				packet = new DatagramPacket(data, data.length, serverAddress, serverPort); // �����ݴ��
				serverSocket.send(packet);// ��������
				JOptionPane .showConfirmDialog(vf, "����OVER���ͳɹ�","�ļ��������",JOptionPane.CANCEL_OPTION);
				vf.dispose();
			} catch (IOException e) {
				System.out.println("����ʧ�ܣ�"+e.getMessage());// ����쳣��Ϣ
			}//end of try-catch	
		} catch (Exception e) {
			System.out.println("����δ֪����"+e.getMessage());
		}
	}//end of run
		
	}//end of thread
	
	
	
}
