package eclassroom.server.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import eclassroom.server.MainServer;
import eclassroom.server.thread.ServerInformation;
import eclassroom.server.thread.ServerThread;
import eclassroom.server.view.ServerFrame;

public class SendInformationAction implements ActionListener{
	private ServerFrame serverFrame;
	private ServerInformation server=MainServer.getServerMessage();
	
	public SendInformationAction(ServerFrame frame) {
		this.serverFrame=frame;		
	}

	public void actionPerformed(ActionEvent e) {
		new Thread(new SendMessageThread()).start();		
	}
	
	private class SendMessageThread implements Runnable {
		public void run() {
			String message = serverFrame.getTxtInsert().getText();
			if(message.trim().equals("")||message==null){
				JOptionPane.showMessageDialog(null, "不能发送空","警告",JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				serverFrame.getTxtDisplay().append(message+"\n");
				serverFrame.getTxtInsert().setText("");
				ServerThread.str="广播消息"+message;
				server.SendInformation("广播消息"+message);			
				System.out.println("send over");
			}//end of if-else			
		}//end of run()
	}//end of inner class SendMessageThread
	
}//end of class SendInformationAction
