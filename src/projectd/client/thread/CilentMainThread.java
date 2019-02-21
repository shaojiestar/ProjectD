package projectd.client.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import projectd.client.gui.ClientMainFrame;

public class CilentMainThread {
	private MulticastSocket clientSocket;// 发送服务多播组
	private DatagramSocket serverSocket;
	private static int serverPort = 40000;
	private static int clientPort = 50000;
	public static String serverIP=null;
	private ClientMainFrame cmf;
	private static String str=null;
	private static String addressMess=null;
	
	public static String getStr() {
		return str;
	}

	public static void setStr(String str) {
		CilentMainThread.str = str;
	}

	public CilentMainThread(ClientMainFrame cmf) {		
		try {
			this.cmf=cmf;
			addressMess=InetAddress.getLocalHost().getHostName()+InetAddress.getLocalHost().getHostAddress();
			str="这里是客户机"+addressMess;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	class ReceiveThread implements Runnable {
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
				byte[] newData = new byte[1024]; // 创建byte数组
				packet = new DatagramPacket(newData, newData.length); // 待接收的数据包
				try {
					clientSocket.receive(packet);// 接收数据包
					cmf.setTeacherIP(packet.getAddress());//将获得的教师的ip传到主窗口
				} catch (IOException e) {
					System.out.println("接收失败！");
				} // end of try-catch
				String message = new String(packet.getData(), 0, packet.getLength()); // 获取数据包中内容
				if(message.contains("我是老师")){
					SendThread send;
					try {
						serverIP=message.substring(4);
						send = new SendThread(clientPort);
						new Thread(send).start();
					} catch (SocketException e) {
						e.printStackTrace();
					}catch (Exception e1){
						e1.printStackTrace();
					}
				}//获取教师机
				if(message.contains("黑屏肃静")){
					ClientBlackScreenThread.begin();
				}//捕捉教师机的黑屏肃静事件
				if(message.contains("文件分发桌面")){
					String fileName=message.substring(6, message.length());
					ClientMessageThread.begin(fileName);
				}//捕捉教师机的文件分发事件
				if(message.contains("文件分发我的文档")){
					String fileName=message.substring(8, message.length());
					ClientMessageThread.begin(fileName);
				}//捕捉教师机的文件分发事件
				if(message.contains("文件分发自定义")){
					String fileName=message.substring(6, message.length());
					ClientMessageThread.begin(fileName);
				}//捕捉教师机的文件分发事件
				if(message.contains("广播消息")){
					String mess=message.substring(4, message.length());
					ClientMessageThread.begin(mess);
				}
			}// end of while()
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
			} catch (Exception e1){
				// end of try-catch
			}

		}

		public void run() {
			while (true) {			
				try {
					DatagramPacket packet = null; // 声明DatagramPacket对象
					byte[] data =str.getBytes(); // 声明字节数组
					packet = new DatagramPacket(data, data.length,clientAddress,5000); // 将数据打包
					System.out.println(new String(data)); // 将广播信息输出
					serverSocket.send(packet);// 发送数据
					Thread.sleep(5000);
					str="这里是客户机"+addressMess;
					break;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//end of try-catch				
			}// end of while()
		}// end of method run()
	}// end of inner class receiveThread
	
	public void beginMainThread(){
		ReceiveThread receive=new CilentMainThread(cmf).new ReceiveThread();
		new Thread(receive).start();
	}
	
	public static void begin(ClientMainFrame cmf){
		CilentMainThread cmt=new CilentMainThread(cmf);
		cmt.beginMainThread();
	}
	
}
