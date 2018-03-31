package gui;

import java.awt.*;
import javax.swing.*;
public class Rules extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel title;
	JPanel head,inf,button,body;
	JButton back;
	Image image,bodyimage;
	int pagenum=0;
	public Rules(){
		setBorder(BorderFactory.createTitledBorder("<html>" +  
                "<font face='Microsoft Yahei' size='5' color='black'>凌云杀神V1.24e" +  
                "</font></html>"));
		ImageIcon j = new ImageIcon(Rules.class.getResource("/guiresource/a1.jpg"));
		image = j.getImage();
		setLayout(new BorderLayout());
		title = new JLabel("<html><font face='Arial' size='6'>"
				+ "<b>游戏规则介绍：</b></font><html>",JLabel.CENTER);
		ImageIcon bodyimage=new ImageIcon("1.png");
		body=new JPanel();
		JLabel cl = new JLabel();
		cl.setIcon(bodyimage);
		body.add(cl);
		JScrollPane jsp = new JScrollPane(body);
		body.setOpaque(false);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false); 
		back = new JButton("了解！");
		head = new JPanel();
		button = new JPanel();
		inf = new JPanel();
		ImageIcon i = new ImageIcon(Rules.class.getResource("/guiresource/a.png"));
		JLabel il = new JLabel(i);
		head.add(title);
		inf.add(il);
		button.add(back);
		head.setOpaque(false);
		inf.setOpaque(false);
		button.setOpaque(false);
		add(head,BorderLayout.NORTH);
		add(button,BorderLayout.SOUTH);
		add(jsp,BorderLayout.CENTER);
	}
	
	public void paintComponent(Graphics g)  
	{  
	    super.paintComponent(g);    
	    g.drawImage(image,0,0,this);
	}
}

