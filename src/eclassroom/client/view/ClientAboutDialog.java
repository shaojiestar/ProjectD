package eclassroom.client.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class ClientAboutDialog extends JDialog {

	private static final long serialVersionUID = -1409577237816429007L;
	private final JPanel contentPanel = new JPanel();
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ClientAboutDialog dialog = new ClientAboutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ClientAboutDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setTitle("Project D 电子教室");
		setResizable(false);
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.DARK_GRAY));
		panel.setBounds(10, 25, 414, 116);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblProjectD = new JLabel("Project D \u7535\u5B50\u6559\u5BA4");
		lblProjectD.setBounds(131, 10, 257, 29);
		panel.add(lblProjectD);
		
		JLabel label = new JLabel("\u521D\u7EA7\u9879\u76EE\u7B80\u5355\u7248");
		label.setBounds(131, 45, 257, 29);
		panel.add(label);
		
		JLabel lblProjectD_1 = new JLabel("Project D \u9879\u76EE\u5C0F\u7EC4");
		lblProjectD_1.setBounds(131, 80, 257, 26);
		panel.add(lblProjectD_1);
		
		JLabel label_picture = new JLabel(new ImageIcon("images/ClientTrayIcon.jpg"));
		label_picture.setBounds(10, 16, 100, 90);
		panel.add(label_picture);
		
		JLabel label_1 = new JLabel("\u7EC4\u957F\uFF1A\u90DD\u9759");
		label_1.setBounds(10, 147, 414, 29);
		contentPanel.add(label_1);
		
		JLabel label_2 = new JLabel("\u6210\u5458\uFF1A\u6BB7\u54CD \u4F55\u9633 \u5218\u534E\u6797 \u8C22\u658C");
		label_2.setBounds(10, 183, 414, 29);
		contentPanel.add(label_2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("\u786E\u5B9A");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
			}
		}
		cancelButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});//点击确定按钮关闭“关于窗口”;
	}
}
