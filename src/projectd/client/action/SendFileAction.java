package projectd.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import projectd.client.thread.UDPClientSendFile;

public class SendFileAction implements ActionListener{
	
	public SendFileAction() {
		
	}

	public void actionPerformed(ActionEvent e) {	
		UDPClientSendFile client = new UDPClientSendFile();
	}

}
