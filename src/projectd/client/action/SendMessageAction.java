package projectd.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import projectd.client.gui.ClientMessageSendFrame;

public class SendMessageAction implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		ClientMessageSendFrame cmsf=new ClientMessageSendFrame();
		cmsf.setVisible(true);
		
	}

}
