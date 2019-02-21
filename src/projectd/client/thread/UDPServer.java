package projectd.client.thread;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class UDPServer extends JFrame {
	private String host_ip; // ����ip
	private int host_port; // �����˿�76
	private int file_port = 3001; // �ļ�����˿�
	private DatagramSocket client_socket;
	private DatagramPacket packet;
	private String selected; // ѡ�е��ļ���
	private JLabel ip_lb, port_lb;
	private JTextField ip_tf, port_tf;
	private JButton link_bt;
	private UDPServer server;

	// ���췽��
	public UDPServer(String host_ip, int host_port) {
		System.out.println(host_ip + "," + host_port);
		this.host_ip = host_ip;
		this.host_port = host_port;
	}

	public UDPServer() {
		setSize(350, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		ip_lb = new JLabel("IP");
		port_lb = new JLabel("�˿�");
		ip_tf = new JTextField(20);
		port_tf = new JTextField(20);
		link_bt = new JButton("����");
		ip_tf = new JTextField("127.0.0.1");
		port_tf = new JTextField("3000");
		// ��ȡ���������ļ��б�
		link_bt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String ip = ip_tf.getText().trim();
				int port = Integer.parseInt(port_tf.getText().trim());
				server = new UDPServer(ip, port);
				server.connect();
			}
		});
		// ��������
		panel.add(ip_lb);
		panel.add(ip_tf);
		panel.add(port_lb);
		panel.add(port_tf);
		panel.add(link_bt);
		frame.add(panel);
		frame.setResizable(false);
		frame.setSize(270, 120);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	// ���ӵ���������
	public void connect() {
		try {
			client_socket = new DatagramSocket();
			byte[] b = new byte[1024];
			packet = new DatagramPacket(b, b.length, InetAddress
					.getByName(host_ip), host_port);
			client_socket.send(packet);
			packet = new DatagramPacket(b, b.length);
			client_socket.receive(packet);
			String files = new String(packet.getData(), 0, packet.getLength());
			if (!files.trim().equals("")) {
				new DownFile(files).start();
			}
			client_socket.close();
		} catch (UnknownHostException e) {
			System.out
					.println("Error setting up socket connection: unknown host at "
							+ host_ip + ":" + host_port);
		} catch (IOException e) {
			System.out.println("Error setting up socket connection: " + e);
		}
	}




	/**
	 * �����ļ���һ���ļ�һ���߳�
	 */
	private class DownFile extends Thread {
		
		String filePath=new String();
		public DownFile(String file){
			filePath=file;
		}
		public void run() {
			JFileChooser chooser = new JFileChooser();
			// ����Ĭ�ϱ�����ļ���
			
			chooser.setSelectedFile(new File(".", filePath));
			int value = chooser.showSaveDialog(null);
			if (value == JFileChooser.APPROVE_OPTION) {
				try {
					byte[] temp = filePath.getBytes();
					DatagramPacket packet = new DatagramPacket(temp,
							temp.length, InetAddress.getByName("localhost"),3001);
					client_socket = new DatagramSocket();
					client_socket.send(packet);
					// ȡ��ѡȡ���ļ�
					File file = chooser.getSelectedFile();
					// ׼��д��
					FileOutputStream fileout = new FileOutputStream(file);
					// ȡ�÷������˵���
					// ����Ϊ����
					byte[] b = new byte[1024];
					int i = 0;
					packet = new DatagramPacket(b, b.length);
					do {
						client_socket.receive(packet);
						fileout.write(b, 0, packet.getLength());
						fileout.flush();
					} while (packet.getLength() != 0);
					// �ر���
					client_socket.close();
					fileout.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] arguments) {
		UDPServer frame = new UDPServer();
	}
}
