package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import data.SqlConnection;
import map.MainFrame;
import others.MP3;

public class Gui extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static SqlConnection sql= new SqlConnection();
	public static String user="visitor";
	public MP3 m=new MP3("BGM1.mp3");
	JPanel cardPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	Rules panel1;PlayerInf pi;
	Dialog dialog1,dialog2;
	MyButton b01,b02,b03,b04,b05,d01,d02;
	JButton signupButton,signinButton;
	JFrame dg1,dg2;
	boolean music=true;
	//卡片式布局
	public CardLayout card = new CardLayout();
	String cardName[] = {"0","1","2","3","4","5"};
	//窗体大小
	int guix = 900;
	int guiy = 600;
	
	public Gui(){
		//窗体风格，大小
		try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        }catch(Exception e){} 
		this.setLayout(new BorderLayout());
		this.setSize(guix, guiy);
		cardPanel.setLayout(card);

		
//-------------------------主界面--------------------------------------------------
		final JLayeredPane layeredPane = new JLayeredPane();    
        layeredPane.setBorder(BorderFactory.createTitledBorder("<html>" +  
                "<font face='Microsoft Yahei' size='5' color='black'>凌云杀神V1.24e" +  
                "</font></html>"));  
        Dimension dimension = new Dimension(700,600);  
        layeredPane.setPreferredSize(dimension);  
          
        final JPanel panelBg = new JPanel();  
        final Image imageBg = Toolkit.getDefaultToolkit()  
                .getImage(this.getClass().getResource("/guiresource/background.jpg"));  
        ImageIcon imageIcon = new ImageIcon(imageBg.getScaledInstance  
                (dimension.width, dimension.height, Image.SCALE_FAST));  
        final JLabel bg = new JLabel(imageIcon);  
        final Point origin = new Point(10, 30);  
        final Rectangle rectangle = new Rectangle(origin, dimension);  
        panelBg.setBounds(rectangle);  
        panelBg.add(bg);  
        if(MainFrame.bgm==true)
        	m.start();
        final JPanel panelContent = new JPanel();  
        signupButton = new JButton("登录");       
        signinButton = new JButton("注册");    
        panelContent.setBounds(rectangle);  
        panelContent.setOpaque(false); // 设置为透明  
        panelContent.add(signupButton);   
        panelContent.add(signinButton);  
          
        layeredPane.add(panelBg, new Integer(0)); // the same to layeredPane.add(panelBg);  
        layeredPane.add(panelContent, new Integer(1));  
        signupButton.addActionListener(this);
        signinButton.addActionListener(this);
		
        //玩法介绍
        panel1 = new Rules();
        panel1.back.addActionListener(this);
        //个人信息
        pi = new PlayerInf(sql);
        pi.back.addActionListener(this);
        pi.tujian.addActionListener(this);
		cardPanel.add(cardName[0],layeredPane);
		cardPanel.add(cardName[1],panel1);
		cardPanel.add(cardName[2], pi);
		card.show(cardPanel, cardName[0]);	
		add(cardPanel,BorderLayout.CENTER);				
		
		//按钮设置
		b01 = new MyButton("/guiresource/button1.jpg","/guiresource/button11.jpg");
		b02 = new MyButton("/guiresource/button8.jpg","/guiresource/button88.jpg");
		b03 = new MyButton("/guiresource/button3.jpg","/guiresource/button33.jpg");
		b04 = new MyButton("/guiresource/button9.jpg","/guiresource/button99.jpg");
		b05 = new MyButton("/guiresource/button5.jpg","/guiresource/button55.jpg");
		//
		b01.addActionListener(this);
		b02.addActionListener(this);
		b03.addActionListener(this);
		b04.addActionListener(this);
		b05.addActionListener(this);
		buttonPanel.add(b01);
		buttonPanel.add(b02);
		buttonPanel.add(b03);
		buttonPanel.add(b04);
		buttonPanel.add(b05);
		buttonPanel.setLayout(new GridLayout(5,1));
		add(buttonPanel,BorderLayout.WEST);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b01){
			Start s = new Start(this);
			s.setDefaultCloseOperation(Start.DISPOSE_ON_CLOSE);
			s.setLocationRelativeTo(null);
			s.setVisible(true);
		}
		
		//玩法介绍
		if(e.getSource() == b02)
			card.show(cardPanel, cardName[1]);
		
		//返回主界面
		if(e.getSource() == panel1.back||e.getSource() == pi.back)
			card.show(cardPanel, cardName[0]);
		
		//首选项
		if(e.getSource() == b03){
			Settings st = new Settings(this);
			st.setDefaultCloseOperation(Settings.DISPOSE_ON_CLOSE);
			st.setLocationRelativeTo(null);
			st.setVisible(true);
		}
		
		//登录
		if(e.getSource() == signupButton){
			if(!MainFrame.issign)
			{
				Signup su = new Signup(sql);
				su.setDefaultCloseOperation(Signup.DISPOSE_ON_CLOSE);
				su.setLocationRelativeTo(null);
				su.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "您已登录", "Alert", JOptionPane.ERROR_MESSAGE);
			}
		}//注册在这里
		if(e.getSource() == signinButton){
			Signin si = new Signin(sql);
			si.setDefaultCloseOperation(Signin.DISPOSE_ON_CLOSE);
			si.setLocationRelativeTo(null);
			si.setVisible(true);
		}
		//个人信息
		if(e.getSource() == b04){
			pi.refresh();
			card.show(cardPanel, cardName[2]);

		}
		//图鉴
		if(e.getSource() == pi.tujian&&MainFrame.issign)
			{
			JButton next,last; 
			next = new JButton(">");
			last = new JButton("<");
			JFrame dg4 = new JFrame();	
			dg4.setResizable(false);
			dg4.setSize(300,300);
			final JPanel dg4_m = new JPanel();
			final JPanel button = new JPanel();
			final CardLayout c1=new CardLayout();
			dg4_m.setLayout(c1);
			ArrayList<String> t=new ArrayList<String>();
			t=sql.thepic(user);
			JLabel dg4_1 = new JLabel("图鉴完成度"+ t.size()+"/"+(new File("man")).listFiles().length,JLabel.CENTER);
			dg4.add(dg4_1,BorderLayout.NORTH);		
			dg4.setVisible(true);
			int i=0;
			for(String s:t)
			{
				JPanel body=new JPanel();
				ImageIcon image=new ImageIcon("man/"+s);
				JLabel cl = new JLabel("第"+(i+1)+"/"+t.size()+"页");
				cl.setIcon(image);
				cl.setVisible(true);
				body.add(cl);
				dg4_m.add(i+"",body);
				i+=1;
			}
			button.add(last);
			button.add(next);
			dg4.add(dg4_m,BorderLayout.CENTER);
			dg4.add(button, BorderLayout.SOUTH);
			next.addMouseListener(new MouseAdapter()
	        {
	              public void mouseClicked(MouseEvent e)
	              {
	                  c1.next(dg4_m);
	              }
	        });
			last.addMouseListener(new MouseAdapter()
	        {
	              public void mouseClicked(MouseEvent e)
	              {
	                  c1.previous(dg4_m);
	              }
	        });
			dg4.setLocationRelativeTo(null);
			}
		if(e.getSource() == b05){
			JFrame dg3 = new JFrame();	
			dg3.setResizable(false);
			dg3.setSize(220,300);
			JPanel dg3_m = new JPanel();
//			dg3_m.setLayout(new GridLayout(3,1));
			JLabel dg3_1 = new JLabel("JAVA程序设计",JLabel.CENTER);
			JLabel dg3_4 = new JLabel("赖劲垲 林竟鹏 高咏鸿",JLabel.CENTER);
			JLabel dg3_5 = new JLabel("  吴优 付识为 张鹏程",JLabel.CENTER);
			JLabel dg3_2 = new JLabel("@2017",JLabel.CENTER);
			JPanel dg3_s = new JPanel();
			dg3_s.setLayout(new GridLayout(4,1));
			dg3_s.add(dg3_1);
			dg3_s.add(dg3_4);
			dg3_s.add(dg3_5);
			dg3_s.add(dg3_2);
			ImageIcon dg3_p = new ImageIcon(Gui.class.getResource("/guiresource/jv.jpg"));
			JLabel dg3_3 = new JLabel(dg3_p,JLabel.CENTER);
			dg3_m.add(dg3_3,BorderLayout.CENTER);
			dg3.add(dg3_m,BorderLayout.CENTER);
			dg3.add(dg3_s,BorderLayout.SOUTH);
			dg3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			dg3.setLocationRelativeTo(null);
			dg3.setVisible(true);
		}
	}

}
