package eclassroom.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;


public class MainPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	
	private List<Socket>sockets=new ArrayList<Socket>();
	private List<ClientLabel>clientPanels =new ArrayList<ClientLabel>();
	private Map<Socket, ClientLabel> clients=new HashMap<Socket, ClientLabel>();
	private ServerSocket serverSocket;
	private int x=20;
	private int y=20;
	private int width=64;
	private int height=64;
	
	//���캯��
	public MainPanel() {
		this.setLayout(null);
//		new RemovePanel().start();
	}
	
	public void run() {
			try {
				serverSocket = new ServerSocket(2010);
				while(true){
					final Socket socket = serverSocket.accept();
					sockets.add(socket);
//					new CaptureImage(socket).start();        //����ͼƬ��ѧ����
					final GetClientImage getClientImage=new GetClientImage(socket);  //��ѧ���˽��յ�ͼƬ
					getClientImage.start();   
//					final SendMakings sendMakings=new SendMakings();  //��������ѧ���˲�����serversocket
//					sendMakings.start();
					final ClientLabel clientLabel=new ClientLabel(getClientImage,width,height); //ѧ�������
					clientPanels.add(clientLabel);
					clients.put(socket, clientLabel);
					clientLabel.setBounds(x,y ,width, height);
					MainPanel.this.add(clientLabel);
//					clientPanel.addMouseListener(new MouseAdapter(){
//						public void mouseClicked(MouseEvent e) {
//							int n=e.getClickCount();
//								if(n==2){
//									if(!socket.isClosed()){
//										new ScreenMonitorFrame(getClientImage,sendMakings);
//										
//									}
//								}
//							}
//						});
						
					x=x+100;
					if(x>500){
						x=20;
						y=y+100;
					}
					repaint();
					}
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}//end of run
	
	
//	private class RemovePanel extends Thread{
//		public void run(){
//			while(true){
//				Set<Socket> itor=clients.keySet();
//				for(Socket socket:itor){
//					if(socket.isClosed()){
//						sockets.remove(socket);
//						clientPanels.remove(clients.get(socket));
//					}
//					else {
//						ClientLabel clientPanel=clients.get(socket);
//						add(clientPanel);
//					}//end of if/else
//				}//end of for
//			}//end of while
//		}//end of run
//	}//end of inner class
	
	public List<Socket> getSockets() {
		return sockets;
	}
	
	public void setSockets(List<Socket> sockets) {
		this.sockets = sockets;
	}
	
}//end of outer class

