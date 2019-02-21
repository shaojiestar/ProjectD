package projectd.client.thread;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JFileChooser;

public class ClientFileOther {
	private MulticastSocket clientSocket;// 发送服务多播组
	private static int serverPort = 44444;
	private BufferedOutputStream fos;
	private String file;

	public ClientFileOther(String file) {
		this.file = file;
	}

	private class ReceiveThread implements Runnable {
		private InetAddress serverAddress;
		byte[] newData = new byte[8];

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
			// String address="C:\\Documents and Settings\\Administrator\\My
			// Documents"+file;
			JFileChooser chooser = new JFileChooser();
			chooser.setSelectedFile(new File(file));
			int val = chooser.showSaveDialog(null);			
			File newFile = chooser.getSelectedFile();
			if (val == JFileChooser.APPROVE_OPTION) {
				try {
					fos = new BufferedOutputStream(new FileOutputStream(newFile));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				while (true) {
					DatagramPacket packet;
					// 创建byte数组
					packet = new DatagramPacket(newData, newData.length); // 待接收的数据包
					try {
						clientSocket.receive(packet);// 接收数据包
					} catch (IOException e) {
						System.out.println("接收失败！");
					} // end of try-catch
					byte data[] = packet.getData();
					int size = packet.getData().length;
					String message = new String(data, 0, packet.getLength()); // 获取数据包中内容
					if (message.equals("over")) {
						break;
					} else {
						try {
							fos.write(data, 0, size);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} // end of while
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}//end of if 判断是否选择保存了的

		}// end of run()
	}

	public void beginReceive() {
		ReceiveThread receive = new ClientFileOther(file).new ReceiveThread();
		new Thread(receive).start();

	}
	
	public static void begin(String file){
		ClientFileOther cfo=new ClientFileOther(file);
		cfo.beginReceive();
	}

}
