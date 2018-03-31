package resources;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import man.Bullet;
import man.Person;
import man.Player;

public class Weapon1 extends Weapon{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ImageIcon con=new ImageIcon("武器2.png");
	public void draw(Graphics g,Player p)//画出资源
	{ 
		
		g.drawImage(con.getImage(),(p.px-elesize/2)+(this.x-elesize/2-(p.getJ())*elesize)-(p.mx%elesize), (p.py-elesize/2)+(this.y-elesize/2-(p.getI())*elesize)-(p.my%elesize), 3*elesize/5,3*elesize/5,null);
	}
	
	public ArrayList<Bullet> attack(Person a)//射出子弹
	{
		ArrayList<Bullet> k=new ArrayList<Bullet>();
		if(a.facetoup||a.facetodown)
		{
			Bullet b1=new Bullet(a);
			a.facetoright=true;
			Bullet b2=new Bullet(a);
			a.facetoright=false;
			a.facetoleft=true;
			Bullet b3=new Bullet(a);
			a.facetoleft=false;		
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
		
		else if(a.facetoleft||a.facetoright)
		{
			Bullet b1=new Bullet(a);
			
			a.facetoup=true;
			Bullet b2=new Bullet(a);
			a.facetoup=false;
			
			a.facetodown=true;
			Bullet b3=new Bullet(a);
			a.facetodown=false;			
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
