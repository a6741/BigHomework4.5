package resources;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import man.Player;

public class A_Bullet extends Weapon{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int B_number;
	public static ImageIcon con=new ImageIcon("子弹.png");
	public int getnumber()
	{
		return B_number;
	}
	
	public void draw(Graphics g,Player p)//画出资源
	{ 
		
		g.drawImage(con.getImage(),(p.px-elesize/2)+(this.x-elesize/2-(p.getJ())*elesize)-(p.mx%elesize), (p.py-elesize/2)+(this.y-elesize/2-(p.getI())*elesize)-(p.my%elesize), 3*elesize/5,3*elesize/5,null);
	}

}
