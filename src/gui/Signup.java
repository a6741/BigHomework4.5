package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import data.SqlConnection;
import map.MainFrame;

public class Signup extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int WIDTH = 300;
	int HEIGHT = 200;
	private JLabel l1,l2;
	private JTextField jtf;
	private JPasswordField jpf;
	private JPanel p1,p2,p3;
	private JButton ok;
	SqlConnection sql;
	public Signup(SqlConnection sql){
		try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        }catch(Exception e){} 
		setSize(WIDTH,HEIGHT);
		setTitle("登录");
		setResizable(false);
		setLayout(new GridLayout(3,1));
		this.sql=sql;
		//用户名，密码
		l1 = new JLabel("用户名:");
		jtf = new JTextField(16);
		p1 = new JPanel();
		p1.add(l1);
		p1.add(jtf);
		
		l2 = new JLabel("  密码:");
		jpf = new JPasswordField(16);
		p2 = new JPanel();
		p2.add(l2);
		p2.add(jpf);
		
		//按钮
		ok = new JButton("登录");
		ok.addActionListener(this);
		p3 = new JPanel();
		p3.add(ok,BorderLayout.CENTER);
		
		add(p1);
		add(p2);
		add(p3);
	}
	
	public String name(){
		return jtf.getText();
	}
	
	public String password(){
		return jpf.getText();
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == ok){
			if(sql.check(name(), password()))//判断账号与密码有对应
			{
			MainFrame.issign=true;
			Gui.user=name();
			this.setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "账号不存在或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	public static void main(String[] args){
		Signup su = new Signup(new SqlConnection());
		su.setDefaultCloseOperation(Signup.DISPOSE_ON_CLOSE);
		su.setLocationRelativeTo(null);
		su.setVisible(true);
	}
}
