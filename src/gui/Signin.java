package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import data.SqlConnection;

//获取用户名：name()
//获取密码:password()
public class Signin extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int WIDTH = 300;
	int HEIGHT = 250;
	private JLabel l1,l2,l3;
	private JTextField jtf;
	private JPasswordField jpf,jpf2;
	private JPanel p1,p2,p3,p4;
	private JButton ok;
	SqlConnection sql;
	public Signin(SqlConnection sql){
		try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        }catch(Exception e){} 
		setSize(WIDTH,HEIGHT);
		setTitle("注册");
		setResizable(false);
		setLayout(new GridLayout(4,1));
		//用户名，密码
		l1 = new JLabel("  用户名:");
		jtf = new JTextField(16);
		p1 = new JPanel();
		p1.add(l1);
		p1.add(jtf);
		
		l2 = new JLabel("    密码:");
		jpf = new JPasswordField(16);
		p2 = new JPanel();
		p2.add(l2);
		p2.add(jpf);
		this.sql=sql;
		l3 = new JLabel("确认密码:");
		jpf2 = new JPasswordField(16);
		p3 = new JPanel();
		p3.add(l3);
		p3.add(jpf2);
		
		//按钮
		ok = new JButton("注册");
		ok.addActionListener(this);
		p4 = new JPanel();
		p4.add(ok,BorderLayout.CENTER);
		
		add(p1);
		add(p2);
		add(p3);
		add(p4);
	}
	
	public String name(){
		return jtf.getText();
	}
	
	public String password(){
		String p1 = new String(jpf.getPassword());
		return p1;
	}
	
	public String password2(){
		String p2 = new String(jpf2.getPassword());
		return p2;
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == ok){
			if(!password().equals(password2()))
				JOptionPane.showMessageDialog(null, "密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
			else{
				if(sql.insert(name(), password()))
					{
					JOptionPane.showMessageDialog(null, "注册成功！", "恭喜", JOptionPane.INFORMATION_MESSAGE);
					this.setVisible(false);
					}
				else
					JOptionPane.showMessageDialog(null, "该账号已存在！", "错误", JOptionPane.ERROR_MESSAGE);

			}		
		}
	}
	
	public static void main(String[] args){
		Signin si = new Signin(new SqlConnection());
		si.setDefaultCloseOperation(Signin.DISPOSE_ON_CLOSE);
		si.setLocationRelativeTo(null);
		si.setVisible(true);
	}
}
