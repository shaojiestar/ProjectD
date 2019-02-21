package projectd.client.gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;

public class MessageFrame extends JWindow{

	private static final long serialVersionUID = -2616811905279788676L;
	
	private String message;
	private JTextArea pnlShow=new JTextArea();;
	private JPanel pnlMain=new JPanel();
	
	public MessageFrame(String mess) {
		this.message=mess;
		pnlShow.setText(message);
		pnlShow.setLineWrap(true);
		pnlShow.setSize(new Dimension(300,100));
		pnlMain.add(pnlShow);
		setContentPane(pnlMain);
		setSize(new Dimension(300,100));
		setLocationRelativeTo(this);
		
	}	
}
