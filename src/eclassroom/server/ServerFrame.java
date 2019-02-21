package eclassroom.server;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;


import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

import eclassroom.ImageManager;
import eclassroom.ZPanel;
import eclassroom.server.action.BlackScreenAction;
import eclassroom.server.action.SendFileAction;
import eclassroom.server.action.SendInformationAction;
import eclassroom.server.thread.ServerThread;

import java.awt.Font;



public class ServerFrame extends JFrame{
	
	private static final long serialVersionUID = 4972433032838001273L;
	
	private JButton btnTail = new JButton("<html><font face=华文行楷 size=5 color=red>屏幕监控</font></html>");
	private JButton btnRelay = new JButton("<html><font face=华文行楷 size=5 color=red>监控转播</font></html>");
	private JLabel lblInformation = new JLabel(ImageManager.getImageIcon("xx.png"));//"<html><body><font size=6 face=华文行楷>消息</font></body></html>"
	private JButton btnInformation = new JButton("<html><font face=华文行楷 size=5 color=red>远程信息</font></html>");
	private JButton btnSpeech = new JButton("<html><font face=华文行楷 size=5 color=red>语音交流</font></html>");
	private JButton btnFile = new JButton("<html><font face=华文行楷 size=5 color=red>文件分发</font></html>");
	private JButton btnBlackScreen = new JButton("<html><font face=华文行楷 size=5 color=red>黑屏肃静</font></html>");
	private ZPanel pnlLog = new ZPanel();
	private ZPanel pnlMain = new ZPanel();
	private ZPanel pnlEast = new ZPanel();
	private ZPanel pnlCenter = new ZPanel();
	private ZPanel pnlSouth = new ZPanel();
	private ZPanel pnlNorth = new ZPanel();
	private JLabel lblDemo = new JLabel(ImageManager.getImageIcon("ybt.png"));//"<html><body><font size=6 face=华文行楷>演播厅</font></body></html>"
	private JButton btnScreen = new JButton("<html><font face=华文行楷 size=5 color=red>屏幕广播</font></html>");
	private JButton btnVideo = new JButton("<html><font face=华文行楷 size=5 color=red>视频广播</font></html>");
	private JButton btnRecord = new JButton("<html><font face=华文行楷 size=5 color=red>屏幕录制</font></html>");
	private JLabel lblTail = new JLabel(ImageManager.getImageIcon("jk.png"));//"<html><body><font size=6 face=华文行楷>监控</font></body></html>"
	private JScrollPane pnlSrcLog = new JScrollPane();
	private JScrollPane pnlSrcDisplay = new JScrollPane();
	private JTextArea txtDisplay = new JTextArea();
	private JScrollPane pnlSrcInsert = new JScrollPane();
	private JTextArea txtInsert = new JTextArea();
	private JButton btnSend = new JButton("<html><font face=华文行楷 size=5 color=red>发  送</font></html>");
	private JTextArea txtLog = new JTextArea();
	private JButton btnSmall = new JButton(ImageManager.getImageIcon("small.png"));
	private JButton btnRevert = new JButton(ImageManager.getImageIcon("about.png"));
	private JButton btnBig = new JButton(ImageManager.getImageIcon("big.png"));
	private JLabel lblIcon = new JLabel(ImageManager.getImageIcon("IconSmall.png"));
	
	private JPopupMenu popIcon=new JPopupMenu();
	private JMenuItem miSmall=new JMenuItem("最小化");
	private JMenuItem miClose=new JMenuItem("关闭");
	
	private Image icon;
	private TrayIcon trayIcon;
	private SystemTray systemTray;
	private PopupMenu pop = new PopupMenu();
	private MenuItem itMessage = new MenuItem("远程消息");
	private MenuItem itScreen = new MenuItem("屏幕录制");
	private MenuItem itSet = new MenuItem("设置");
	private MenuItem itAbout = new MenuItem("关于");
	private MenuItem itHelp = new MenuItem("帮助");
	
	private Point loc = null;    
	private Point tmp = null;   
	private boolean isDragged = false;
	
	
	public ServerFrame() {
		icon = new ImageIcon(ImageManager.getImage("Icon.png")).getImage();
		systemTray = SystemTray.getSystemTray();// 获得系统托盘的实例
		trayIcon = new TrayIcon(icon, "Project D 电子教室", pop);	
		
		init();
	}
	
	private void setDragable() {     
       	this.addMouseListener(new MouseAdapter() {   
       			public void mouseReleased(MouseEvent e) {   
       	   	       	isDragged = false; 
       	   	       ServerFrame.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
       			}
       	            public void mousePressed(MouseEvent e) {      
       	            	   tmp = new Point(e.getX(), e.getY());      
       	            	   isDragged = true; 
       	            ServerFrame.this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
       	            }
       	});  
       this.addMouseMotionListener(new MouseMotionAdapter() {
       	   public void mouseDragged(MouseEvent e) {
       	   	   if(isDragged) {
       	   	   	  loc = new Point(ServerFrame.this.getLocation().x + e.getX() - tmp.x,ServerFrame.this.getLocation().y + e.getY() - tmp.y); 
       	   	   	 ServerFrame.this.setLocation(loc);
       	   	   }
       	   	} 
       	}); 
}  
	private void init(){
		
		
		if(SystemTray.isSupported()){
			pop.add(itMessage);
			pop.addSeparator();
			pop.add(itScreen);
			pop.addSeparator();
			pop.add(itSet);
			pop.add(itAbout);
			pop.add(itHelp);
		}	
		
//		pnlNorth.setDragable();
		
		
//		btnRevert.setEnabled(false);
		miClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,KeyEvent.ALT_MASK));
		popIcon.add(miSmall);
		popIcon.addSeparator();
		popIcon.add(miClose);
		
		
		txtDisplay.setEditable(false);
		
		txtDisplay.setBackground(new Color(255, 204, 102));
		
		
		pnlMain.setBackground(ImageManager.getImageIcon("backgroup.jpg"));
		pnlCenter.setBackground(ImageManager.getImageIcon("backgroup.jpg"));
		pnlLog.setBackground(ImageManager.getImageIcon("backgroup.jpg"));
		pnlEast.setBackground(ImageManager.getImageIcon("backgroup.jpg"));
		pnlSouth.setBackground(ImageManager.getImageIcon("backgroup.jpg"));
		pnlNorth.setBackground(ImageManager.getImageIcon("backgroup.jpg"));
		
		
		pnlMain.setBounds(0, 0, 800, 700);
		getContentPane().add(pnlMain);
		pnlMain.setLayout(null);
		pnlEast.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		
		pnlEast.setBounds(581, 43, 201, 436);
		pnlMain.add(pnlEast);
		pnlEast.setLayout(null);
		lblDemo.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		lblDemo.setBounds(10, 10, 181, 28);
//		lblDemo.setIcon(ImageManager.getImageIcon("yanboting.gif"));
		pnlEast.add(lblDemo);
		btnScreen.setBackground(new Color(255, 204, 102));
	
		
		btnScreen.setBounds(10, 44, 181, 28);
//		btnScreen.setIcon(ImageManager.getImageIcon("11.gif"));
		pnlEast.add(btnScreen);
		btnVideo.setBackground(new Color(255, 204, 102));
		
		
		btnVideo.setBounds(10, 78, 181, 28);
		pnlEast.add(btnVideo);
		btnRecord.setBackground(new Color(255, 204, 102));
		
		
		btnRecord.setBounds(10, 110, 181, 28);
		pnlEast.add(btnRecord);
		lblTail.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		lblTail.setBounds(10, 144, 181, 28);
//		lblTail.setIcon(ImageManager.getImageIcon("jiankong.gif"));
		pnlEast.add(lblTail);
		btnTail.setBackground(new Color(255, 204, 102));
		btnTail.setBounds(10, 181, 181, 28);
		
		pnlEast.add(btnTail);
		btnRelay.setBackground(new Color(255, 204, 102));
		btnRelay.setBounds(10, 212, 181, 28);
		
		pnlEast.add(btnRelay);
		lblInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformation.setBounds(10, 246, 181, 28);
//		lblInformation.setIcon(ImageManager.getImageIcon("xiaoxi.gif"));
		
		pnlEast.add(lblInformation);
		btnInformation.setBackground(new Color(255, 204, 102));
		btnInformation.setBounds(10, 282, 181, 28);
		
		pnlEast.add(btnInformation);
		btnSpeech.setBackground(new Color(255, 204, 102));
		btnSpeech.setBounds(10, 317, 181, 28);
		
		pnlEast.add(btnSpeech);
		btnFile.setBackground(new Color(255, 204, 102));
		btnFile.setBounds(10, 351, 181, 28);
		
		pnlEast.add(btnFile);
		btnBlackScreen.setBackground(new Color(255, 204, 102));
		btnBlackScreen.setBounds(10, 387, 181, 28);
		
		pnlEast.add(btnBlackScreen);
		pnlCenter.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		
		pnlCenter.setBounds(10, 43, 553, 497);
		pnlMain.add(pnlCenter);
		pnlCenter.setLayout(null);
		pnlLog.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u65E5  \u5FD7", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		pnlLog.setBounds(581, 485, 201, 205);
		
		pnlMain.add(pnlLog);
		pnlLog.setLayout(null);
		
		pnlSrcLog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pnlSrcLog.setBounds(10, 20, 181, 175);
		pnlLog.add(pnlSrcLog);
		txtLog.setEditable(false);
		txtLog.setForeground(Color.ORANGE);
		txtLog.setBackground(new Color(255, 204, 102));
		
		pnlSrcLog.setViewportView(txtLog);
//		ediLog.setForeground(Color.MAGENTA);
		pnlSouth.setBounds(10, 546, 553, 144);
		pnlMain.add(pnlSouth);
		pnlSouth.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlSouth.setLayout(null);
		pnlSrcDisplay.setBounds(10, 10, 533, 72);
		
		pnlSouth.add(pnlSrcDisplay);
		
		pnlSrcDisplay.setViewportView(txtDisplay);
		pnlSrcInsert.setBounds(25, 88, 381, 46);
		
		pnlSouth.add(pnlSrcInsert);
		txtInsert.setBackground(new Color(255, 204, 102));
		
		pnlSrcInsert.setViewportView(txtInsert);
		btnSend.setBackground(new Color(255, 204, 102));
		btnSend.setBounds(424, 88, 101, 46);
		
		pnlSouth.add(btnSend);
		pnlNorth.setBounds(0, 0, 800, 41);
		
		pnlMain.add(pnlNorth);
		pnlNorth.setLayout(null);
		lblIcon.setFont(new Font("STZhongsong", Font.ITALIC, 20));
		lblIcon.setForeground(Color.BLACK);
		lblIcon.setText("Project_D e-Learning Class");
		lblIcon.setBounds(10, 0, 318, 36);
		pnlNorth.add(lblIcon);
		btnSmall.setBounds(617, 3, 50, 30);
		pnlNorth.add(btnSmall);
		btnRevert.setBounds(673, 3, 50, 30);
		pnlNorth.add(btnRevert);
		btnBig.setBounds(729, 3, 50, 30);
		pnlNorth.add(btnBig);
		
		setDragable();
		setSize(new Dimension(800, 700));
		setTitle("Project_D e-Learning class");
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setIconImage(ImageManager.getImage("Icon.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		
		//最大化
		btnBig.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				ServerFrame.this.setExtendedState(JFrame.NORMAL);
				System.exit(-1);
			}			
		});//end of btnBig
		//最小化
		btnSmall.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
//				ServerFrame.this.setExtendedState(JFrame.ICONIFIED);
				ServerFrame.this.setVisible(false);
				try {
					systemTray.add(trayIcon);
				} catch (AWTException e1) {
					e1.printStackTrace();
				} // 设置托盘的图标
			}			
		});//end of btnSmall
		//图标的右键
		lblIcon.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				int count=e.getClickCount();
				if(count==2){
//					ServerFrame.this.setExtendedState(JFrame.ICONIFIED);
					ServerFrame.this.setVisible(false);
					try {
						systemTray.add(trayIcon);
					} catch (AWTException e1) {
						e1.printStackTrace();
					} // 设置托盘的图标
				}
				if(e.getButton()==MouseEvent.BUTTON3){
					popIcon.show(lblIcon, e.getX(), e.getY());
				}
			}
			
		});
		
//		btnSend.addActionListener(new SendInformationAction(this));
		btnFile.addActionListener(new SendFileAction());
		btnBlackScreen.addActionListener(new BlackScreenAction());//简单处理黑屏事件
		//右键最小化
		miSmall.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
//				ServerFrame.this.setExtendedState(JFrame.ICONIFIED);
				ServerFrame.this.setVisible(false);
				try {
					systemTray.add(trayIcon);
				} catch (AWTException e1) {
					e1.printStackTrace();
				} // 设置托盘的图标
			}			
		});
		//右键关闭
		miClose.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				ServerFrame.this.setExtendedState(JFrame.NORMAL);
				System.exit(-1);
			}			
		});
		
		//托盘事件
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2&&e.getButton()==MouseEvent.BUTTON1) {
					ServerFrame.this.setVisible(true);
					systemTray.remove(trayIcon);
				}
			}
		});//简单处理鼠标左键双击事件
		
		
	} //end of InIt()
	
	public JTextArea getTxtDisplay(){
		return txtDisplay;
	}
	public JTextArea getTxtInsert(){
		return txtInsert;
	}
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		ServerFrame frame=new ServerFrame();
		frame.setVisible(true);
	}
}
