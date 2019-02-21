package projectd.client.gui;

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

import projectd.client.action.ClientSetAction;
import projectd.client.action.SendFileAction;
import projectd.client.action.SendMessageAction;
import projectd.client.thread.CilentMainThread;

public class ClientMainFrame extends JFrame{

	private static final long serialVersionUID = -1595601749947100078L;
	
	private Image icon;
	private TrayIcon trayIcon;
	private SystemTray systemTray;
	private PopupMenu pop = new PopupMenu();
	private MenuItem itPutHand = new MenuItem("举手");
	private MenuItem itMessage = new MenuItem("远程消息");
	private MenuItem itScreen = new MenuItem("屏幕录制");
	private MenuItem itSendFile = new MenuItem("将文件发送给教师");
	private MenuItem itSet = new MenuItem("设置");
	private MenuItem itAbout = new MenuItem("关于");
	private MenuItem itHelp = new MenuItem("帮助");
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
		systemTray = SystemTray.getSystemTray();// 获得系统托盘的实例
		trayIcon = new TrayIcon(icon, "Project D 电子教室", pop);
		icon = new ImageIcon("images/ClientTrayIcon.jpg").getImage();
		init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CilentMainThread.begin(this);
		//启动主线程接受教师机发来的指令		
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
			systemTray.add(trayIcon); // 设置托盘的图标
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
		});//简单处理鼠标左键双击事件
		
		itAbout.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				ClientAboutDialog ad=new ClientAboutDialog();
				ad.setVisible(true);
			}
			
		});//简单的处理说明事件		
		itSet.addActionListener(new ClientSetAction(PASS_WORD));//设置事件
		itSendFile.addActionListener(new SendFileAction());//发送文件事件
		itPutHand.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try {
					String mess=InetAddress.getLocalHost().getHostName()+InetAddress.getLocalHost().getHostAddress();
					String str=("举手");
					CilentMainThread.setStr(str.concat(mess));
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
					
			}
			
		});//处理举手事件
		
		itMessage.addActionListener(new SendMessageAction());//处理消息发送事件
	}
}
