package eclassroom.client.view;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientBlackScreen extends JFrame{

	private static final long serialVersionUID = -6846798174115834081L;
	private JPanel pnlMain=new JPanel();
	
	public ClientBlackScreen() {
		init();
		
	}

	private void init() {
			createBlack();		
	}

	private void createBlack() {
		this.add(pnlMain);
		pnlMain.setBackground(Color.BLACK);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
	}

}
