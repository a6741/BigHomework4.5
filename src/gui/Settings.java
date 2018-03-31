package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import others.MP3;

//调用bgm()和music()来获取背景音乐和音效设置
public class Settings extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int DEFAULT_WIDTH  = 400;
	int DEFAULT_HEIGHT = 300;
	private JLabel title;
	private JPanel p1,p2;
	private JRadioButton a1,a2,a3,a4;
	private ButtonGroup group1,group2;
	private JButton confirm;
	Gui gui;
	public Settings(Gui gui){
		try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        }catch(Exception e){} 
		setTitle("游戏");
		setResizable(false);
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		title = new JLabel("<html><font face = 'Microsoft YaHei' size = '5' color ='blue'>"
				+ "游戏设置：</font><html>",JLabel.CENTER);
		add(title,BorderLayout.NORTH);
		//单选区
		JLabel sg = new JLabel("背景音乐：",JLabel.CENTER);
		JLabel sd = new JLabel("音效：",JLabel.CENTER);
		a1 = new JRadioButton("开");
		a2 = new JRadioButton("关");
		a3 = new JRadioButton("开");
		a4 = new JRadioButton("关");
			//默认都开
			a1.setSelected(true);
			a3.setSelected(true);
		JPanel b1 = new JPanel();
		JPanel b2 = new JPanel();
		b1.add(a1);
		b1.add(a2);
		b2.add(a3);
		b2.add(a4);
		this.gui=gui;
		group1 = new ButtonGroup();
		group2 = new ButtonGroup();
		group1.add(a1);
		group1.add(a2);
		group2.add(a3);
		group2.add(a4);
		
		
		p1 = new JPanel();
		p1.setLayout(new GridLayout(4,1));
		p1.add(sg);
		p1.add(b1);
		p1.add(sd);
		p1.add(b2);
		
		add(p1,BorderLayout.CENTER);
		
		//p2:按钮区
		confirm = new JButton("ok");
		confirm.addActionListener(this);
		p2 = new JPanel();
		p2.add(confirm,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
	}

	public boolean bgm(){
		if(a1.isSelected())
			return true;
		else
			return false;
	}
	
	public boolean music(){
		if(a3.isSelected())
			return true;
		else
			return false;
	}
	
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == confirm){
			if(!bgm())
			{
				if(gui.m.isAlive())
					gui.m.close();
			}
			else if(!gui.m.isAlive())
			{
				gui.m=new MP3("BGM1.mp3");
				gui.m.start();
			}
			gui.music=music();
			this.setVisible(false);
		}
	}
	
//	public static void main(String[] args){
//		Settings st = new Settings();
//		st.setDefaultCloseOperation(st.DISPOSE_ON_CLOSE);
//		st.setLocationRelativeTo(null);
//		st.setVisible(true);
//	}
}
