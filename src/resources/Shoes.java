package resources;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import man.Player;
import map.gameConfig;

public class Shoes extends Resource implements gameConfig{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int speed;
	public static ImageIcon con=new ImageIcon("鞋子.png");
	public int speedup()
	{
		return speed;
	}
	public void draw(Graphics g,Player p)//画出资源
	{ 
		
		g.drawImage(con.getImage(),(p.px-elesize/2)+(this.x-elesize/2-(p.getJ())*elesize)-(p.mx%elesize), (p.py-elesize/2)+(this.y-elesize/2-(p.getI())*elesize)-(p.my%elesize), 3*elesize/5,3*elesize/5,null);
	}
}
