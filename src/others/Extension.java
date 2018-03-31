package others;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import man.Person;
import man.Player;
import map.gameConfig;

public class Extension implements gameConfig,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double damage;
	public double speed;
	public int ptime=0;//毒圈增长时间
	public int pfrequency=35;//毒圈增长频率
	public int x;
	public int y;
	public int height,width;
	public int hlimit,wlimit;
	ImageIcon poison = new ImageIcon("半透明.jpg");
	public Extension(double speed,double damage,int x, int y,int height,int width,int hlimit,int wlimit) {
		this.speed=speed;
		this.damage=damage;
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		this.hlimit=hlimit;
		this.wlimit=wlimit;
	}
	
	public void Extensions(){	
		if(width<wlimit/2)
			width+=speed;
		if(height<hlimit/2)
			height+=speed;
	}
	public void draw(Graphics g,Player player)
	{
		g.drawImage(poison.getImage(),(player.px-elesize/2)+(x-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-(player.getI())*elesize)-(player.my%elesize), width, hlimit,null);
		g.drawImage(poison.getImage(),(player.px-elesize/2)+(width-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-(player.getI())*elesize)-(player.my%elesize), wlimit-width*2, height,null);
		g.drawImage(poison.getImage(),(player.px-elesize/2)+(wlimit-width-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-(player.getI())*elesize)-(player.my%elesize), width, hlimit,null);
		g.drawImage(poison.getImage(),(player.px-elesize/2)+(width-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(wlimit-height-(player.getI())*elesize)-(player.my%elesize), wlimit-width*2, height,null);
	
	}
	public void isinpoison(ArrayList<Person> peList)
	{
		for(Person per:peList)
		{
			if(per.x<width||per.y<height||per.x>wlimit-width||per.y>hlimit-height)
			{
				per.hp-=damage;
				if (per.hp <=0)
					per.killmessage.add(per.name + " was killed by poison") ;
			}
		}
	}
}
