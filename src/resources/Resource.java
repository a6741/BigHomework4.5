package resources;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.ImageIcon;

import man.Person;
import man.Player;
import map.MainFrame;
import map.ReadMapFile;
import map.gameConfig;
import others.MP3;
import others.Timer;

public class Resource extends Thread implements gameConfig,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean isAlive = true;
	public static ImageIcon con;
	public int x=0;
	public int y=0;
	static HashMap<Object[],Long> getresource = new HashMap<Object[],Long>();
	final int time=10050;//存在时间
	//出现
	public void appear(int[][] map) 
	{
		int xlength=(map[0].length-1)*elesize;
		int ylength=(map[0].length-1)*elesize;
		
		Random rand = new Random();
		x=y=0;
		while(ReadMapFile.map2[y/elesize][x/elesize]!=0)
		{
		x = rand.nextInt(xlength);
		y = rand.nextInt(ylength);
		}
		switch(this.getClass().getName())
		{
		case "resources.Hp":
			((Hp)this).hp=rand.nextInt(15)+10;
			break;
		case "resources.Shoes":
			((Shoes)this).speed=1;
			break;
		case "resources.Weapon":
			((Weapon)this).atk=rand.nextInt(9)+10;
			break;
		case "resources.Weapon1":
			((Weapon)this).atk=rand.nextInt(9)+5;
			break;
		case "resources.Weapon2":
			((Weapon)this).atk=rand.nextInt(9)+3;
			break;
		case "resources.ProClothing":
			((ProClothing)this).def=rand.nextInt(3)+1;
			break;
		case "resources.A_Bullet":
			((A_Bullet)this).B_number=rand.nextInt(10)+10;
			break;
		default:
			break;
		}
	}
	
	//消失
	public void disappear()
	{
		isAlive=false;
	}

	int map[][]=new int[ReadMapFile.map1.length][ReadMapFile.map1[0].length];
	public void run() {
		while(true){
			if(!this.isAlive)
				{try {
					Thread.sleep(time/4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}}
			this.isAlive=true;
			appear(map);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void isget(ArrayList<Person> peList,Graphics g,Timer time,Player p)
	{				
	if(ReadMapFile.pathis.equals("map/SnowWorld.map")||ReadMapFile.pathis.equals("map/DeathDesert.map"))
		g.setColor(bulletcolor2);
	else
		g.setColor(bulletcolor);
		for(Person per:peList)
		{
			if((per.x+elesize/2>this.x&&per.x-elesize/2<this.x) && (per.y+elesize/2>this.y&&per.y-elesize/2<this.y))
			{
				if(per.personid==p.personid&&MainFrame.music)
				{
				MP3 ss=new MP3("get.mp3");
				ss.isloop=false;
				ss.start();
				}
				int num;
				switch(this.getClass().getName())
				{
				case "resources.Hp":
					if(per.hp+((Hp)this).gethp()>per.hplimit)
					{
						num=per.hplimit-per.hp;
						per.hp+=num;
					}
					else
						{
						num=((Hp)this).gethp();
						per.hp+=num;
						}
					getresource.put(new Object[]{per.name+" HP+"+num,this.x,this.y},time.whattime());
					
					break;
				case "resources.Shoes":
					if(per.step<4)
						{per.step+=((Shoes)this).speedup();
						num=((Shoes)this).speedup();
						}
					else
						num=0;
					per.speedup+=500;
					getresource.put(new Object[]{per.name+" SPEED+"+num,this.x,this.y},time.whattime());
					break;
				case "resources.Weapon"://有问题
					per.bulletnum+=10;
					per.wp=(Weapon) this;
					//if(per.atk<((Weapon)this).getatak())
					per.atk=((Weapon)this).getatak();
					getresource.put(new Object[]{per.name+" get normal gun",this.x,this.y},time.whattime());					
					break;
				case "resources.Weapon1":
					per.bulletnum+=10;
					per.wp=(Weapon) this;
					//if(per.atk<((Weapon)this).getatak())
					per.atk=((Weapon)this).getatak();
					getresource.put(new Object[]{per.name+" get scatter gun",this.x,this.y},time.whattime());
					break;
				case "resources.Weapon2":
					per.bulletnum+=10;
					per.wp=(Weapon) this;
					//if(per.atk<((Weapon)this).getatak())
					per.atk=((Weapon)this).getatak();
					getresource.put(new Object[]{per.name+" get strongger gun",this.x,this.y},time.whattime());
					break;
				case "resources.ProClothing":
					if(per.def<18) {
					per.def+=((ProClothing)this).getPro();
					num=((ProClothing)this).getPro();
					}
					else
						num=0;
					getresource.put(new Object[]{per.name+" DEF+"+num,this.x,this.y},time.whattime());
					break;
				case "resources.A_Bullet":
					//if(((A_Bullet)this).getnumber()+per.wp.number<=per.wp.limnumber)
					per.bulletnum+=((A_Bullet)this).getnumber();
					num=((A_Bullet)this).getnumber();
					getresource.put(new Object[]{per.name+" BULLET+"+num,this.x,this.y},time.whattime());
					break;
				default:
					break;
				}
				HashMap<Object[],Long> tmap=new HashMap<Object[],Long>();
				for(Object[] str:getresource.keySet())
				{
					if(getresource.get(str)>time.whattime())
					{
						getresource.clear();
						break;
					}
					if(time.whattime()-getresource.get(str)<1000)
						{
						g.drawString(str[0].toString(),(p.px-elesize/2)+(Integer.parseInt(str[1].toString())-elesize/2-(p.getJ())*elesize)-(p.mx%elesize), (p.py-elesize/2)+(Integer.parseInt(str[2].toString())-elesize/2-(p.getI())*elesize)-(p.my%elesize));	tmap.put(str,getresource.get(str));
						tmap.put(str,getresource.get(str));
						}
				}
				getresource.clear();
				getresource.putAll(tmap);
				this.disappear();
				return;
			}
		}
		HashMap<Object[],Long> tmap=new HashMap<Object[],Long>();
		for(Object[] str:getresource.keySet())
		{					
			if(getresource.get(str)>time.whattime())
			{
				getresource.clear();
				break;
			}
			if(time.whattime()-getresource.get(str)<1000)
				{
				g.drawString(str[0].toString(),(p.px-elesize/2)+(Integer.parseInt(str[1].toString())-elesize/2-(p.getJ())*elesize)-(p.mx%elesize), (p.py-elesize/2)+(Integer.parseInt(str[2].toString())-elesize/2-(p.getI())*elesize)-(p.my%elesize));
				tmap.put(str,getresource.get(str));
				}
		}
		getresource.clear();
		getresource.putAll(tmap);
	}
	public void draw(Graphics g,Player p)//画出资源
	{ 
		
		g.drawImage(con.getImage(),(p.px-elesize/2)+(this.x-elesize/2-(p.getJ())*elesize)-(p.mx%elesize), (p.py-elesize/2)+(this.y-elesize/2-(p.getI())*elesize)-(p.my%elesize), elesize, elesize,null);
	}
	
}