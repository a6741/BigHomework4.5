package gui;

import java.awt.*;
import javax.swing.*;

import data.SqlConnection;
import map.MainFrame;
import others.Timer;
public class PlayerInf extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int width = 400;
	int height = 400;
	JButton back,tujian;
	JTextArea jt2=new JTextArea();
	SqlConnection sql;
	public PlayerInf(SqlConnection sql){
		try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        }catch(Exception e){}
		//setSize(width,height);
		this.sql=sql;
			JLabel title;
			JPanel head,button;
			setBorder(BorderFactory.createTitledBorder("<html>" +  
	                "<font face='Microsoft Yahei' size='5' color='black'>凌云杀神V1.24e" +  
	                "</font></html>"));
			setLayout(new BorderLayout());
			title = new JLabel("<html><font face='Arial' size='6'>"
					+ "<b>个人信息</b></font><html>",JLabel.CENTER);
			back = new JButton("了解！");
			tujian = new JButton("图鉴");
			head = new JPanel();
			button = new JPanel();
			head.add(title);
			button.add(tujian);
			button.add(back);
			head.setOpaque(false);
			button.setOpaque(false);
			add(head,BorderLayout.NORTH);
			add(button,BorderLayout.SOUTH);
			jt2.setText("\n\n\n\n\t登录以查看玩家个人信息");
			jt2.setFont(new Font("Microsoft Yahei", Font.BOLD, 28));
			jt2.setEditable(false);
			jt2.setOpaque(false);
			add(jt2,BorderLayout.CENTER);
			
	}
	public void refresh()
	{
		jt2.setText("\n\n\n\n\t登录以查看玩家个人信息");
		jt2.setFont(new Font("Microsoft Yahei", Font.BOLD, 28));
		if(MainFrame.issign)
		{
			String user=Gui.user;
			jt2.setText("\n\n\n\t\t玩家账户: "+Gui.user);
			jt2.append("\n\n\t\t总游戏局数  "+sql.gamenumber(user));
			if(sql.gamenumber(user)>0)
				jt2.append("\n\n\t\t游戏胜率  "+ String.format("%.1f",((float)sql.winnumber(user)/(float)sql.gamenumber(user)*100))+"%");
			jt2.append("\n\n\t\t总杀敌数 "+sql.killingnumber(user));
			jt2.append("\n\n\t\t总游戏时间  "+Timer.format(sql.time(user)));
			jt2.append("\n\n\t\t最佳闯关成绩  "+sql.chenkpoint(user));
			jt2.setFont(new Font("Microsoft Yahei", Font.BOLD, 18));
			
		}
		jt2.setEditable(false);
		jt2.setOpaque(false);
	}
	public void paintComponent(Graphics g)  
	{  
	    super.paintComponent(g);
		ImageIcon j = new ImageIcon(Rules.class.getResource("/guiresource/bbb.jpg"));
	    g.drawImage(j.getImage(),0,0,this);
	}
}
