package eclassroom.server;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private List<Socket> sockets = new ArrayList<Socket>();
	private JButton buttonStreenBroade;
	private JButton buttonStreenMonitor;
	private JButton sendFileButton;
	private MainPanel mainPanel = new MainPanel();
	private Thread mainPanelthread = new Thread(mainPanel);
	private boolean start = true;

	private void initButtons() {
		buttonStreenBroade = new JButton("屏幕广播");
		buttonStreenBroade.setBounds(40, 60, 100, 30);
		this.add(buttonStreenBroade);
		buttonStreenBroade.addActionListener(this);
		buttonStreenMonitor = new JButton("远程协助");
		buttonStreenMonitor.setBounds(200, 60, 100, 30);
		this.add(buttonStreenMonitor);
		buttonStreenMonitor.addActionListener(this);
		sendFileButton = new JButton("发送文件");
		this.add(sendFileButton);
		sendFileButton.setBounds(350, 60, 100, 30);
		sendFileButton.addActionListener(this);
	}//end  of method

	public MainFrame() {
		this.setLayout(null);
		this.setBounds(100, 40, 700, 700);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(Color.cyan);
		this.add(mainPanel);
		mainPanel.setBounds(5,5, 670, 650);
		mainPanel.setBackground(Color.yellow);
//		initButtons();
		sockets = mainPanel.getSockets();
		mainPanelthread.start();
	}

	public static void main(String[] args) {
		new MainFrame();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonStreenBroade) {
			if (this.isStart()) {
				mainPanelthread.start();
				buttonStreenBroade.setText("停止广播");
				setStart(false);
			} else {
				buttonStreenBroade.setText("屏幕广播");
				this.setStart(true);
			}
		}//end of if
		
		if (e.getSource() == buttonStreenMonitor) {
			buttonStreenMonitor.setText("停止远程协助");
		}//end of if
		
		if (e.getSource() == sendFileButton) {
			/*
			 * FileDialog senDialog=new FileDialog(this,"打开",FileDialog.LOAD);
			 * senDialog.setVisible(true); String
			 * dirString=senDialog.getDirectory(); String
			 * fileString=senDialog.getFile(); String
			 * nameString=dirString+fileString; try { FileInputStream
			 * fileInputStream=new FileInputStream(nameString); int temp;
			 * DataOutputStream dataOutputStream; for(Socket socket:sockets){
			 * dataOutputStream=new DataOutputStream(socket.getOutputStream());
			 * while((temp=fileInputStream.read())!=-1){
			 * dataOutputStream.writeInt(temp); } dataOutputStream.flush();
			 *  } } catch (FileNotFoundException e1) { // TODO Auto-generated
			 * catch block e1.printStackTrace(); } catch (IOException e1) { //
			 * TODO Auto-generated catch block e1.printStackTrace(); }
			 */
		}//end of if

	}//end of method

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}
	
}//end of class
