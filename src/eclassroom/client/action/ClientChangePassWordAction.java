package eclassroom.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import projectd.client.gui.ClientMainFrame;
import projectd.client.gui.ClientSetFrame;

public class ClientChangePassWordAction implements ActionListener{
	
	private eclassroom.client.view.ClientSetFrame csf;
	public ClientChangePassWordAction(eclassroom.client.view.ClientSetFrame clientSetFrame) {
		this.csf=clientSetFrame;
	}

	public void actionPerformed(ActionEvent e) {
		String strPassOne=csf.getPasswordField().getText();
		String strPassTwo=csf.getPasswordField_1().getText();
		if(strPassOne.trim().equals("")||strPassTwo.trim().equals("")){
			JOptionPane.showMessageDialog(null, "请输入新密码");
		}else{
			if(strPassOne.trim().equals(strPassTwo)){
				ClientMainFrame.setPASS_WORD(strPassOne);
				JOptionPane.showMessageDialog(null, "密码修改成功,新密码是："+ClientMainFrame.getPASS_WORD());
			}else if(!strPassOne.trim().equals(strPassTwo)){
				JOptionPane.showMessageDialog(null, "两次输入的密码不一致");
				csf.getPasswordField().setText("");
				csf.getPasswordField_1().setText("");
			}
			else{
				JOptionPane.showMessageDialog(null, "修改失败");
			}
		}
		
		
	}

}
