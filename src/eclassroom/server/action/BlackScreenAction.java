package eclassroom.server.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import eclassroom.server.thread.ServerThread;

public class BlackScreenAction implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		int result=JOptionPane.showConfirmDialog(null,"您确定要黑屏吗？","提示",JOptionPane.OK_OPTION);
		if(result==JOptionPane.OK_OPTION){
			ServerThread.str="黑屏肃静";
		}else{
			;
		}
	}

}
