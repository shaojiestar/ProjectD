package eclassroom.client.action;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import projectd.client.gui.ClientSetFrame;

public class ClientSetAction implements ActionListener {
	private String passWord;

	public ClientSetAction(String passWord) {
		this.passWord = passWord;
	}

	public void actionPerformed(ActionEvent e) {
		while (true) {
			String pass = JOptionPane.showInputDialog("«Î ‰»Î√‹¬Î£°");
			if (pass.trim().equals(passWord)) {
				ClientSetFrame csf = new ClientSetFrame();
				csf.setVisible(true);
				csf.setLocation(
						Toolkit.getDefaultToolkit().getScreenSize().width / 2,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2);
				break;
			}
		}

	}
}
