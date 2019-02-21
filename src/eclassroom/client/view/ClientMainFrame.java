package eclassroom.client.view;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import eclassroom.client.action.ClientSetAction;
import eclassroom.client.action.SendFileAction;
import eclassroom.client.action.SendMessageAction;
import eclassroom.client.thread.ClientMainThread;


public class ClientMainFrame extends JFrame{

	private static final long serialVersionUID = -1595601749947100078L;
	
	private Image icon;
	private TrayIcon trayIcon;
	private SystemTray systemTray;
	private PopupMenu pop = new PopupMenu();
	private MenuItem itPutHand = new MenuItem("����");
	private MenuItem itMessage = new MenuItem("Զ����Ϣ");
	private MenuItem itScreen = new MenuItem("��Ļ¼��");
	private MenuItem itSendFile = new MenuItem("���ļ����͸���ʦ");
	private MenuItem itSet = new MenuItem("����");
	private MenuItem itAbout = new MenuItem("����");
	private MenuItem itHelp = new MenuItem("����");
	private static String PASS_WORD = "projectd";
	private InetAddress TeacherIP;
	
	public InetAddress getTeacherIP(){
		return TeacherIP;
	}
	
	public void setTeacherIP(InetAddress ip){
		this.TeacherIP=ip;
	}
	
	public static String getPASS_WORD() {
		return PASS_WORD;
	}

	public static void setPASS_WORD(String pass_word) {
		PASS_WORD = pass_word;
	}

	public ClientMainFrame() {
		icon = new ImageIcon("images/ClientTrayIcon.jpg").getImage();
		systemTray = SystemTray.getSystemTray();// ���ϵͳ���̵�ʵ��
		trayIcon = new TrayIcon(icon, "Project D ���ӽ���", pop);
		icon = new ImageIcon("images/ClientTrayIcon.jpg").getImage();
		init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		ClientMainThread.begin(this);
		//�������߳̽��ܽ�ʦ��������ָ��		
	}

	private void init() {
		if(SystemTray.isSupported()){
			pop.add(itPutHand);
			pop.add(itMessage);
			pop.addSeparator();
			pop.add(itScreen);
			pop.addSeparator();
			pop.add(itSendFile);
			pop.add(itSet);
			pop.add(itAbout);
			pop.add(itHelp);
		}	
		try {
			systemTray.add(trayIcon); // �������̵�ͼ��
		} catch (AWTException e1) {
			e1.printStackTrace();
		}	

		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2&&e.getButton()==MouseEvent.BUTTON1) {
					ClientAboutDialog ad=new ClientAboutDialog();
					ad.setVisible(true);
				}
			}
		});//�򵥴���������˫���¼�
		
		itAbout.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				ClientAboutDialog ad=new ClientAboutDialog();
				ad.setVisible(true);
			}
			
		});//�򵥵Ĵ���˵���¼�		
		itSet.addActionListener(new ClientSetAction(PASS_WORD));//�����¼�
		itSendFile.addActionListener(new SendFileAction());//�����ļ��¼�
		itPutHand.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try {
					String mess=InetAddress.getLocalHost().getHostName()+InetAddress.getLocalHost().getHostAddress();
					String str="���ھ���"+mess;
					ClientMainThread.setStr(str);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
					
			}
			
		});//��������¼�
		
		itMessage.addActionListener(new SendMessageAction());//������Ϣ�����¼�
	}
}
