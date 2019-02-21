package eclassroom.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;

public class MessageFrame extends JWindow{

	private static final long serialVersionUID = -2616811905279788676L;
	
	private String message;
	private JTextArea pnlShow=new JTextArea();
	private JPanel pnlMain=new JPanel();
	
	public MessageFrame(String mess) {
		pnlMain.setLayout(new BorderLayout());
		this.message=mess;
		pnlShow.setText(message);
		pnlShow.setLineWrap(true);
		pnlShow.setSize(new Dimension(300,100));
		pnlShow.setBackground(Color.LIGHT_GRAY);
		pnlMain.add(pnlShow,BorderLayout.CENTER);
		pnlMain.setBackground(Color.LIGHT_GRAY);
		System.out.println(pnlShow.getRows());
		setContentPane(pnlMain);
		setSize(new Dimension(300,100));
		setLocationRelativeTo(this);
		
	}	
	
	public static void main(String[] args) {
		MessageFrame mf=new MessageFrame("Õâ¸öÊÇÓÃÀ´²âÊÔµÄ¹þ¹þ¹þ¹þ¹þ¹þ¹þ¹þ¹þ¹þ¹þ¹þ¹þ°¡¹þ°¡°¡");
		mf.setVisible(true);
	}
}
