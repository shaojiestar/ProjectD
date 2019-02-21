package projectd.client.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UDPClientSendFile extends JFrame {

	private static final long serialVersionUID = 1954055732184537566L;
	private String file_path; // �ļ�·��
	private int server_port; // �������˿�
	private int file_port; // �ļ�����˿�
	private DatagramSocket server;
	private DatagramSocket file_server;
	private boolean flag = true; // ���Ʒ����Ƿ�����
	private JTable table = new JTable();
	private Vector<Vector<Serializable>> row = new Vector<Vector<Serializable>>();
	private Vector<String> head = new Vector<String>();
	private JFrame table_frame = new JFrame();
	private JScrollPane pane;
	private DefaultTableModel tableModel = new DefaultTableModel();
//	private ProgressCellModel cellModel = new ProgressCellModel();
	private javax.swing.JLabel msg_lb;
	private javax.swing.JLabel path_lb;
	private javax.swing.JTextField path_tf;
	private javax.swing.JLabel port_lb;
	private javax.swing.JTextField port_tf;
	private javax.swing.JButton select_dir;
	private javax.swing.JButton start_server;
	private javax.swing.JButton stop_server;
	private UDPClientSendFile udp_client;

	public UDPClientSendFile(String file_path, int server_port) {
		this.file_path = file_path;
		this.server_port = server_port;
		this.file_port = 3001;
		addTableHead();
		try {
			String mess = InetAddress.getLocalHost().getHostName()+InetAddress.getLocalHost().getHostAddress();
			String str=("����");
			CilentMainThread.setStr(str.concat(mess));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		setLocationRelativeTo(this);
	}

	/**
	 * ���Ǹ�table�Ӹ���ͷ�õ�
	 */
	private void addTableHead() {
		head.add("IP");
		head.add("�ļ���");
		head.add("��С");
		head.add("����");
		head.add("�ٷֱ�");
		head.add("״̬");
		pane = new JScrollPane(table);
	}

	public UDPClientSendFile() {
		addTableHead();
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.setSize(300, 150);
		select_dir = new JButton("ѡ���ļ�");
		msg_lb = new JLabel("������ֹͣ");
		path_lb = new JLabel("·��");
		port_lb = new JLabel("�˿�");
		path_tf = new JTextField(20);
		path_tf.setEditable(false);
		port_tf = new JTextField(20);
		start_server = new JButton("����");
		stop_server = new JButton("ֹͣ����");
		select_dir.setText("ѡ���ļ�");
		panel.add(path_lb);
		panel.add(path_tf);
		panel.add(select_dir);
		panel.add(start_server);
		panel.add(stop_server);
		panel.add(msg_lb);
		frame.add(panel);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		/**
		 * ���¼�������ȡѡ����ļ���·��
		 */
		select_dir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int value = chooser.showOpenDialog(null);
				if (value == JFileChooser.APPROVE_OPTION) {
					path_tf.setText(chooser.getSelectedFile().getPath());
				}
			}
		});//end of action
		
		/**
		 * ���¼���������һ���µ�UDP����
		 */
		start_server.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int listen_port = 3000;
				if (!port_tf.getText().trim().equals("")) {
					listen_port = Integer.parseInt(port_tf.getText().trim());
				}
				udp_client = new UDPClientSendFile(path_tf.getText(), listen_port);
				udp_client.startServer();
				msg_lb.setText("����������");
			}
		});//end of action
		
		/**
		 * ���¼������ر����е�UDP����
		 */
		stop_server.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				udp_client.stopServer();
				msg_lb.setText("�����ѹر�");
			}
		});//end of action

		
	}//�ڶ������췽�����������



	/**
	 * �÷���������������
	 */
	void startServer() {
		// ����ServerSocket
		try {
			if (server == null) {
				server = new DatagramSocket(server_port);
			}
			if (file_server == null) {
				file_server = new DatagramSocket(file_port);
			}
			// ������һ���̣߳����ȴ��ͻ��˵�����
			new ConnThread().start();
			new FileThread().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//end of method
	
	/**
	 * ���߳�������ȡ������������IP
	 * @author luorcy
	 *
	 */
	private class ConnThread extends Thread {
		public void run() {
			while (flag) {
				try {
					byte buffer[] = new byte[256];
					DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
					server.receive(packet);
					putFile(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}//end of method
	/**
	 * �÷�����Ҫ���͵��ļ������͸�������
	 */
	private void putFile(DatagramPacket packet) {
		// ȡ�����ӵĵ�ַ�Ͷ˿�
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		File file = new File(file_path);
		String file_name =file.getName();
		System.out.print(file_name);
		String s=file_name ;
		byte[] b = s.getBytes();
		packet = new DatagramPacket(b, b.length, address, port);
		try {
			server.send(packet);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}//end of method
	
	/**
	 * ���߳̿������һֱ���ܷ�������Ϣ
	 * һ��������ȷ�Ͻ��ܵ���Ϣ���͹����������������߳�
	 * @author luorcy
	 *
	 */
	private class FileThread extends Thread {
		public void run() {
			while (flag) {
				try {
					byte buffer[] = new byte[256];
					DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
					file_server.receive(packet);
					// ��������һ���̣߳��ȴ��ͻ��˻�ȡ�ļ�
					new SendThread(packet).start();
					new TableThread().start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}//end of method
	
	/**
	 * �����ļ��̣߳����߳������������ļ��ķ���
	 * @author luorcy
	 *
	 */
	private class SendThread extends Thread {
		private DatagramPacket packet;
		public SendThread(DatagramPacket packet) {
			this.packet = packet;
		}
		public void run() {
			download(packet);
		}
	}//end of method
	
	/**
	 * �����ļ��ķ���
	 * @param packet
	 */
	private void download(DatagramPacket packet) {
		try {
			File file = new File(file_path);
			FileInputStream filein = new FileInputStream(file);
			byte[] b = new byte[1024];
			long i = 0; // �Ѵ�����ֽ���
			String l = ""; // ��K�������Ѵ��������
			String per = ""; // ��ʾ�ٷֱ�
			int length = 0;
			table_frame.setSize(400, 400);
			table_frame.add(pane);
			Vector<Serializable> cell = new Vector<Serializable>();
			cell.add(packet.getAddress());
			cell.add(file_path);
			cell.add(l);
			cell.add(per);
			cell.add("������...");
			row.add(cell);
			tableModel.setDataVector(row, head);
			table.setModel(tableModel);
			table_frame.setVisible(true);
			// ȡ�����ӵĵ�ַ�Ͷ˿�
			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			while ((length = filein.read(b)) != -1) {
				packet = new DatagramPacket(b, length, address, port);
				try {
					file_server.send(packet);
				} catch (IOException ex) {
					Logger.getLogger(UDPServer.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				i = i + length;
				l = i / 1024 + "K";
				per = (i * 100 / file.length()) + "%";
				cell.set(2, l);
				cell.set(3, (int) (i * 100 / file.length()));
				cell.set(4, per);
				cell.set(5, "������...");
			}
			filein.close();
			cell.set(5, "�������");
		} catch (Exception e) {
			System.out.println("Error handling a client: " + e);
		}
	}//end of method

	// �߳�,ˢ��table
	private class TableThread extends Thread {
		public void run() {
			while (true) {
				table.repaint();
			}
		}
	}

	public void stopServer() {
		flag = false;
	}

	public static void main(String[] args) {
		
	}
}
