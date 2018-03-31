package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import map.MainFrame;
import map.ReadMapFile;

//分别可以调用gamemode(),difficulty()和roomnumber()来获取游戏设置
public class Start extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int DEFAULT_WIDTH = 400;
	int DEFAULT_HEIGHT = 300;
	private JLabel title;
	private JPanel p1,p2;
	private JRadioButton a1,a2,a3,a4,a5,a6,a7,a8;
	private ButtonGroup group1,group2;
	private JButton confirm;
	private JTextField jtf;
	private JComboBox<String> jcb ;
	private String str[];
	Gui gui;
	public Start(Gui gui){
		try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        }catch(Exception e){} 
		setTitle("游戏");
		setResizable(false);
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		title = new JLabel("<html><font face = 'Microsoft YaHei' size = '5' color ='red'>"
				+ "选择你的游戏方式：</font><html>",JLabel.CENTER);
		add(title,BorderLayout.NORTH);
		this.gui=gui;
		//单选区
		JLabel sg = new JLabel("模式：",JLabel.CENTER);
		JLabel sd = new JLabel("难度：",JLabel.CENTER);
		a1 = new JRadioButton("单机");
		a2 = new JRadioButton("联机游戏");
		a3 = new JRadioButton("高");
		a4 = new JRadioButton("中");
		a5 = new JRadioButton("低");
		a6=new JRadioButton("闯关模式");
		a7=new JRadioButton("生存模式");
		a8=new JRadioButton("双人游戏");
			//默认为单机低难度
			a1.setSelected(true);
			a5.setSelected(true);
		JPanel b1 = new JPanel();
		JPanel b2 = new JPanel();
		JPanel b3 = new JPanel();
		File d = new File("map"); 
    	File list[] = d.listFiles();
    	ArrayList<String> li=new ArrayList<String>();
    	for (File f : list)
    	{
    		if(f.getName().substring(f.getName().length()-4,f.getName().length()).equals(".map"))
    			li.add(f.getName().replace(".map", ""));
    	}
    	str=(String[])li.toArray(new String[0]);
		jcb = new JComboBox<String>(str); 
		JLabel r = new JLabel("选择地图");
		b3.add(r);
		b3.add(jcb);
		b1.add(a1);
		b1.add(a2);
		b1.add(a6);
		b1.add(a7);
		b1.add(a8);
		b2.add(a3);
		b2.add(a4);
		b2.add(a5);
		group1 = new ButtonGroup();
		group2 = new ButtonGroup();
		group1.add(a1);
		group1.add(a2);
		group2.add(a3);
		group2.add(a4);
		group2.add(a5);
		group1.add(a6);
		group1.add(a8);
		group1.add(a7);
		
		//文本框，输入联机房间号
		JPanel rn = new JPanel();
		JLabel r1 = new JLabel("联机房间号:");
		jtf = new JTextField(10);
		rn.add(r1);
		rn.add(jtf);
		
		p1 = new JPanel();
		p1.setLayout(new GridLayout(5,1));
		p1.add(sg);
		p1.add(b1);
		p1.add(sd);
		p1.add(b2);
		p1.add(rn);

		add(p1,BorderLayout.NORTH);
		add(b3,BorderLayout.CENTER);
		//p2:按钮区
		confirm = new JButton("ok");
		confirm.addActionListener(this);
		p2 = new JPanel();
		p2.add(confirm,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		
	}
	
	public int gamemode(){
		if(a1.isSelected()){
			System.out.println("单机");
			return 0;
		}
		else if(a2.isSelected())
		{
			System.out.println("联机");
			return 1;
		}
		else if(a6.isSelected())
		{
			System.out.println("闯关");
			return 2;
		}		
		else if(a8.isSelected())
		{
			System.out.println("双人");
			return 4;
		}
		else
		{
			System.out.println("生存模式");
			return 3;
		}
		
	}
	
	public int difficulty(){
		if(a3.isSelected()){
			System.out.println("高");
			return 1;
		}
		else if(a4.isSelected()){
			System.out.println("中");
			man.ComputerPlayer.Difficulty=2;
			return 2;
		}
		else{
			System.out.println("低");
			man.ComputerPlayer.Difficulty=1;
			return 3;
		}
	}
	public String chosemap(){
		return str[jcb.getSelectedIndex()];
		
	}
	public String roomnumber(){
		return jtf.getText();
	}
	
	public void actionPerformed(ActionEvent e){
		ReadMapFile.readfile("map/"+chosemap()+".map");
		if(e.getSource() == confirm){
			int k=gamemode();
			if(k==0)
				{MainFrame.LAN=false;
				MainFrame.breakthrough=false;
				MainFrame.timeup=0;
				MainFrame.two=false;
				}
			else if(k==1)
				{MainFrame.LAN=true;
				MainFrame.breakthrough=false;
				MainFrame.timeup=0;
				MainFrame.two=false;}
			else if(k==2)
			{MainFrame.LAN=false;
			MainFrame.breakthrough=true;
			MainFrame.chapter=0;
			MainFrame.timeup=0;
			MainFrame.two=false;}
			else if(k==3)
			{MainFrame.LAN=false;
			MainFrame.breakthrough=false;
			MainFrame.timeup=180000;
			MainFrame.two=false;
			}
			else
			{
				MainFrame.two=true;
				MainFrame.LAN=false;
				MainFrame.breakthrough=false;
				MainFrame.timeup=0;
			}
			int y = difficulty();
			if(y==1) {
				MainFrame.shadow=true;
				man.ComputerPlayer.Difficulty=2;
			}else if(y==2){
				MainFrame.shadow=false;
				man.ComputerPlayer.Difficulty=2;
			}else if(y==3){
				MainFrame.shadow=false;
				man.ComputerPlayer.Difficulty=1;
			}
			if(roomnumber()==null||roomnumber().length()<4)
			{
				MainFrame.service=true;
			}
			else
			{
				MainFrame.service=false;
				MainFrame.ip=roomnumber();
			}
			System.out.println(roomnumber());
			this.setVisible(false);
			if(gui.m.isAlive())
				MainFrame.bgm=true;
			else
				MainFrame.bgm=false;
			if(gui.m.isAlive())
				gui.m.close();
			gui.setVisible(false);
			MainFrame.music=gui.music;
			MainFrame mf = new MainFrame(gui);
		}
	}
	
//	public static void main(String[] args){
//		Start frame = new Start();
//		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//	}
}
