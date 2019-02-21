package eclassroom.server.send;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import eclassroom.server.thread.ServerThread;

public class SureAction implements ActionListener{
	
	private ViewFile vf;
	
	public SureAction(ViewFile vf) {
		this.vf=vf;
	}

	public void actionPerformed(ActionEvent e) {
		//在这里添加语句用来修改主线程要发送的数据
		String fileName=vf.getLblShowName().getText();
		JRadioButton btn1=vf.getBtnTop();
		JRadioButton btn2=vf.getBtnUser();
		JRadioButton btn3=vf.getBtnWord();
		
		if(btn1.isSelected()){
			ServerThread.str="文件分发桌面"+fileName;
		}else if(btn3.isSelected()){
			ServerThread.str="文件分发我的文档"+fileName;
		}else if(btn2.isSelected()){
			ServerThread.str="文件分发自定义"+fileName;
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		SendThreadFour st=new SendThreadFour(vf);
		st.beginSend();
	}//end of faction

}

