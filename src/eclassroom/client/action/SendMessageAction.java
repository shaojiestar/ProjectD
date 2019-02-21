package eclassroom.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eclassroom.client.thread.ClientMainThread;

import projectd.client.gui.ClientMessageSendFrame;

public class SendMessageAction implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		ClientMainThread.str="我在发消息";
		
		ClientMessageSendFrame cmsf=new ClientMessageSendFrame();
		cmsf.setVisible(true);
		
	}

}
