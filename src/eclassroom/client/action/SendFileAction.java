package eclassroom.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import eclassroom.client.thread.ClientMainThread;

import projectd.client.thread.UDPClientSendFile;

public class SendFileAction implements ActionListener{
	
	public SendFileAction() {
		
	}

	public void actionPerformed(ActionEvent e) {	
		try {
			String mess=InetAddress.getLocalHost().getHostName()+InetAddress.getLocalHost().getHostAddress();
			String str=("·¢ËÍÎÄ¼þ");
			ClientMainThread.setStr(str.concat(mess));
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		UDPClientSendFile client = new UDPClientSendFile();
		
	}
	
}
