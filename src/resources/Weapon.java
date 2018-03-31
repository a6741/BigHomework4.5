package resources;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import man.Bullet;
import man.Person;
import man.Player;
import map.gameConfig;

public class Weapon extends Resource implements gameConfig{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int atk;//威力
	public static ImageIcon con=new ImageIcon("武器1.png");
	public int getatak()
	{
		return atk;
	}
	public void draw(Graphics g,Player p)//画出资源
	{ 
		
		g.drawImage(con.getImage(),(p.px-elesize/2)+(this.x-elesize/2-(p.getJ())*elesize)-(p.mx%elesize), (p.py-elesize/2)+(this.y-elesize/2-(p.getI())*elesize)-(p.my%elesize), 3*elesize/5,3*elesize/5,null);
	}
	
	public ArrayList<Bullet> attack(Person a)//射出子弹
	{
		ArrayList<Bullet> k=new ArrayList<Bullet>();

			Bullet b1=new Bullet(a);
			b1.start();		
			if((a.up&&a.down)||(a.left&&a.right)||!a.isalive())
			{
				b1.alive=false;
			}
			else
				k.add(b1);
		return k;
	}
}
