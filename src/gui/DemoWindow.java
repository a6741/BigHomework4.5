package gui;

import javax.swing.*;
import java.awt.*;

public  class DemoWindow extends JWindow implements Runnable {
	private JProgressBar progress = new JProgressBar(1,100);
	ImagePanel ip = new ImagePanel();
	public DemoWindow(){
		add(ip,BorderLayout.CENTER);
		add(progress,BorderLayout.SOUTH);
		//进度条
		progress.setStringPainted(true);
		progress.setBorderPainted(false);
		progress.setString("loading……");
		progress.setForeground(Color.black);
		progress.setBackground(Color.white);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		pack();
		Toolkit thekit = getToolkit();
		Dimension wndSize = thekit.getScreenSize();
		//窗体在正中间
		setLocation((wndSize.width - ip.getWidth())/2,(wndSize.height - ip.getHeight())/2);
		setVisible(true);
		this.toFront();
		Thread splashThread = new Thread(this);
		splashThread.start();	
	}
	
	public void run(){
		try{
			for(int i = 0;i < 100;i ++){
				Thread.sleep(20);
				progress.setValue(progress.getValue()+1);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		this.dispose();
	
		Gui gui = new Gui();
		gui.setTitle("凌云杀神");
		gui.setLocationRelativeTo(null);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setResizable(false);
		gui.setVisible(true);
	}
	
	class ImagePanel extends JPanel{
		ImageIcon imageic =new ImageIcon(DemoWindow.class.getResource("/guiresource/loading.gif"));
		Image image = imageic.getImage();
		public ImagePanel(){
			MediaTracker tracker = new MediaTracker(this);
			try{
				tracker.addImage(image, 0);
				tracker.waitForID(0);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			setPreferredSize(new Dimension(image.getWidth(this),image.getHeight(this)));
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(image,0,0,this);
		}
	}	
}
  