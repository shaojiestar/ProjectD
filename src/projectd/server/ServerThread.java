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
	private static MulticastSocket serverSocket;// 发送服务多播组
	private DatagramSocket clientSocket;// 接收
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
				weather.append("广播消息看看广播过来了没有");
				if (ip!=null) {
					weather.append(ip);
				}	
			} catch (UnknownHostException e1) {
				System.out.println("获取IP失败");
			}//end of try-catch
		}

		public void run() {
			
			while (true) {
				while (true) {					
					try {
						DatagramPacket packet = null; // 声明DatagramPacket对象					
						byte data[] = weather.toString().getBytes(); // 声明字节数组
						packet = new DatagramPacket(data, data.length, serverAddress, serverPort); // 将数据打包
						System.out.println(new String(data)); // 将广播信息输出
						serverSocket.send(packet);// 发送数据	
						Thread.sleep(5000);
					} catch (IOException e) {
						System.out.println("发送失败："+e.getMessage());//输出异常信息
					} catch (InterruptedException e) {
						System.out.println("线程出线问题："+e.getMessage());
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
				System.out.println("声明DatagramSocket失败"+e.getMessage());
			}//end of try-catch
			
		}
		public void run() {
			
			while(true){				
				byte[] newData = new byte[1024]; // 创建byte数组
				packet = new DatagramPacket(newData, newData.length); // 待接收的数据包
				try {
					clientSocket.receive(packet);// 接收数据包
					InetAddress netAddress=packet.getAddress();
					User user=new User(netAddress,netAddress.getHostAddress(),netAddress.getHostName());
					vector.add(user);
					newData=packet.getData();
				} catch (IOException e) {
					System.out.println("接收失败！");
				} //end of try-catch
				String message = new String(newData, 0, packet.getLength()); // 获取数据包中内容
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


