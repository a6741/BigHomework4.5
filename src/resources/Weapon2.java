package resources;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import man.Bullet;
import man.Person;
import man.Player;

public class Weapon2 extends Weapon{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ImageIcon con=new ImageIcon("武器3.png");
	public void draw(Graphics g,Player p)//画出资源
	{ 
		
		g.drawImage(con.getImage(),(p.px-elesize/2)+(this.x-elesize/2-(p.getJ())*elesize)-(p.mx%elesize), (p.py-elesize/2)+(this.y-elesize/2-(p.getI())*elesize)-(p.my%elesize), 3*elesize/5,3*elesize/5,null);
	}
	
	public ArrayList<Bullet> attack(Person a)//射出子弹
	{
		int xy;
		ArrayList<Bullet> k=new ArrayList<Bullet>();
			if(a.facetoup||a.facetodown)
			{
				xy=a.x;
				Bullet b1=new Bullet(a);
				a.x+=elesize/4;
				Bullet b2=new Bullet(a);
				a.x=xy;
				a.x-=elesize/4;
				Bullet b3=new Bullet(a);
				a.x=xy;
				b1.start();
				b2.start();
				b3.start();
				if((a.up&&a.down)||(a.left&&a.right)||!a.isalive())
				{
				b1.alive=false;
				b2.alive=false;
				b3.alive=false;
				}
				else
				{
					k.add(b1);
					k.add(b2);
					k.add(b3);
				}
			}
			if(a.facetoleft||a.facetoright)
			{
				xy=a.y;
				Bullet b1=new Bullet(a);
				a.y+=elesize/4;
				Bullet b2=new Bullet(a);
				a.y=xy;
				a.y-=elesize/4;
				Bullet b3=new Bullet(a);
				a.y=xy;
				b1.start();
				b2.start();
				b3.start();
				
				if((a.up&&a.down)||(a.left&&a.right)||!a.isalive())
				{
				b1.alive=false;
				b2.alive=false;
				b3.alive=false;
				}
				else
				{
					k.add(b1);
					k.add(b2);
					k.add(b3);
				}
			}
			
			
		return k;
	}

}
