package eclassroom.server.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HandsUP extends JDialog{

	
	private static final long serialVersionUID = -9193512922078052762L;
	
	private JPanel pnlMain=new JPanel();
	private JLabel lblMain=new JLabel();
	
	public HandsUP(String str) {
		this.lblMain.setText(str);
		init();
	} 
	public HandsUP(){
		init();
	}
	private void init(){
		pnlMain.add(lblMain);
		pnlMain.setBackground(Color.YELLOW);
		setContentPane(pnlMain);
		final Dimension toolKit=Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(toolKit.width-120, toolKit.height);
		setSize(new Dimension(120,100));
		setResizable(false);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//控制窗口往上跑
		new Thread(new Runnable(){
			
			public void run() {				
				try {
					for (int i = 0; i < 15; i++) {
						setLocation(toolKit.width-120, toolKit.height-10*i);
						Thread.sleep(50);
					}//end of for					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//end of try-catch
			}//end of run()			
		}).start();//end of Thread
		//设置5秒钟后关闭
		new Thread(new Runnable(){
			
			public void run() {
				try {
					Thread.sleep(5000);
					HandsUP.this.dispose();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}//end of run()			
		}).start();//end of Thread
		//设置点击窗口就关闭掉
		HandsUP.this.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				int count=e.getClickCount();
				if(count==1){
					HandsUP.this.dispose();
				}//end of if
			}//end of mouseCliecked
			
		});//end of action
	}//end of InIt()
	
//	public static void main(String[] args) {
//		HandsUP up=new HandsUP("举手");
//		up.setVisible(true);
//	}
	

}
