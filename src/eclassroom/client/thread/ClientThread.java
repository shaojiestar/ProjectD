package eclassroom.client.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;



public class ClientThread {

	private MulticastSocket clientSocket;// 发送服务多播组
	private DatagramSocket serverSocket;// 接收
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
				byte[] newData = new byte[1024]; // 创建byte数组
				packet = new DatagramPacket(newData, newData.length); // 待接收的数据包
				try {
					clientSocket.receive(packet);// 接收数据包
				} catch (IOException e) {
					System.out.println("接收失败！");
				} // end of try-catch
				String message = new String(packet.getData(), 0, packet
						.getLength()); // 获取数据包中内容
				System.out.println(message);
				if(message.contains("我是老师")){
					int index=(new String("我是老师").length());
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
				System.out.println("获取IP错误：" + e.getMessage());
			} // end of try-catch

		}

		public void run() {
//			while(true){
				try {
					DatagramPacket packet = null; // 声明DatagramPacket对象
					String str="handsup";
					byte[] data =str.getBytes(); // 声明字节数组
					packet = new DatagramPacket(data, data.length,clientAddress,5000);// 将数据打包
					System.out.println(new String(data)); // 将广播信息输出
					serverSocket.send(packet);// 发送数据
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
