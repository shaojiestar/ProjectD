package projectd.client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import projectd.client.action.ClientChangePassWordAction;



public class ClientSetFrame extends JFrame{
	
	private static final long serialVersionUID = 3890826018852627470L;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	
	public ClientSetFrame() {
		setResizable(false);
		setTitle("\u8BBE\u7F6E\u7A97\u53E3");
		getContentPane().setLayout(null);
		
		init();
	}
	private void init() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "\u8FD9\u91CC\u7ED9\u4F60\u63D0\u4F9B\u4FEE\u6539\u5BC6\u7801\u529F\u80FD", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel.setBounds(10, 10, 415, 139);
		getContentPane().add(panel);
		panel.setLayout(null);
		setSize(451, 231);
		
		JLabel label = new JLabel("\u8F93\u5165\u65B0\u5BC6\u7801\uFF1A");
		label.setBounds(10, 35, 94, 31);
		panel.add(label);
		
		JLabel label_1 = new JLabel("\u786E\u8BA4\u65B0\u5BC6\u7801\uFF1A");
		label_1.setBounds(10, 72, 94, 31);
		panel.add(label_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(110, 39, 260, 24);
		panel.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(110, 76, 260, 24);
		panel.add(passwordField_1);
		
		JButton button = new JButton("\u786E\u8BA4\u4FEE\u6539");
		button.setBounds(302, 106, 101, 25);
		panel.add(button);
		button.addActionListener(new ClientChangePassWordAction(projectd.client.gui.ClientSetFrame.this));//处理点击修改事件
		
		JButton button_1 = new JButton("\u8FD4\u56DE");
		button_1.setBounds(206, 155, 101, 25);
		getContentPane().add(button_1);
		button_1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});//退出修改框
		
		JButton button_2 = new JButton("\u9000\u51FA\u7A0B\u5E8F");
		button_2.setBounds(313, 155, 101, 25);
		getContentPane().add(button_2);
		button_2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
					System.exit(1);
			}
			
		});//退出程序

	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}
	public JPasswordField getPasswordField_1() {
		return passwordField_1;
	}
	
	
}
