package projectd.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import projectd.client.gui.ClientMainFrame;
import projectd.client.gui.ClientSetFrame;

public class ClientChangePassWordAction implements ActionListener{
	
	private ClientSetFrame csf;
	public ClientChangePassWordAction(ClientSetFrame clientSetFrame) {
		this.csf=clientSetFrame;
		}
		public void actionPerformed(ActionEvent e) {
		String strPassOne=csf.getPasswordField().toString();
		String strPassTwo=csf.getPasswordField_1().toString();
		if(strPassOne.trim().equals("")||strPassTwo.trim().equals("")){
			JOptionPane.showMessageDialog(null, "������������");
		}else{
			if(strPassOne.trim().equals(strPassTwo)){
				ClientMainFrame.setPASS_WORD(strPassOne);
				JOptionPane.showMessageDialog(null, "�����޸ĳɹ�,�������ǣ�"+ClientMainFrame.getPASS_WORD());
			}else if(!strPassOne.trim().equals(strPassTwo)){
				JOptionPane.showMessageDialog(null, "������������벻һ��");
				csf.getPasswordField().setText("");
				csf.getPasswordField_1().setText("");
			}
			else{
				JOptionPane.showMessageDialog(null, "�޸�ʧ��");
			}
		}
		
		
	}

}
