package eclassroom.server.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eclassroom.server.send.ViewFile;

public class SendFileAction implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		ViewFile viewFile=new ViewFile();
		viewFile.setVisible(true);
	}

}