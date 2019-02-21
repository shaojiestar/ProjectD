package projectd.client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import projectd.client.thread.CilentMainThread;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientMessageSendFrame extends JFrame{
	
	private static final long serialVersionUID = 3939691112988467561L;
	private JButton button;
	private JButton button_1;
	private JTextArea textArea;
	
	public ClientMessageSendFrame() {
		setTitle("消息发送框");
		getContentPane().setLayout(null);
		setSize(new Dimension(451,285));
		setLocationRelativeTo(this);
		
		button = new JButton("确定");
		button.setBounds(222, 219, 93, 23);
		getContentPane().add(button);
		
		button_1 = new JButton("取消");
		button_1.setBounds(331, 219, 93, 23);
		getContentPane().add(button_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u8FD9\u91CC\u7528\u6765\u53D1\u9001\u6D88\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 414, 203);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 394, 172);
		panel.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		button_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}		
		});//取消按钮事件
		
		button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try {
					String  mess = InetAddress.getLocalHost().getHostName()+InetAddress.getLocalHost().getHostAddress();
					String str=("远程消息");
					String data=textArea.getText();
					CilentMainThread.setStr(str.concat(mess).concat(data));
					System.out.println(CilentMainThread.getStr());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
			}
			
		});//确定按钮事件	
		
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	
}
