package eclassroom.server.send;

import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ProChangeListener implements ChangeListener{
	
	private ViewFile vf;
	
	public ProChangeListener(ViewFile vf) {
		this.vf=vf;
	}

	public void stateChanged(ChangeEvent e) {
		JProgressBar progress=(JProgressBar)e.getSource();
		int value=progress.getValue();
		vf.getLabel().setText("��ǰ����"+value+"%");
		vf.getLabel().updateUI();
	}

}
