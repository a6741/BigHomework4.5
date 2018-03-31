package man;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import map.MainFrame;
import resources.Weapon;

//Person是AI和玩家的父类
public class Person extends Move{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Bullet> buList = new ArrayList<Bullet>();
	public final int personid;//用以区分其他玩家
	public int drawfrequency;//移动时画出动作变化的频率
	public int bulletnum=30;//子弹数
	public String name;
	public Weapon wp=new Weapon();//武器
	public boolean facetoup=false;//判断角色面朝哪里
	public boolean facetodown=true;//判断角色面朝哪里
	public boolean facetoright=false;//判断角色面朝哪里
	public boolean facetoleft=false;//判断角色面朝哪里
	public int speedup=0;//用以标记加速鞋子的时间
	public int hp;//血量
	public int hplimit;
	public int def;//防御力
	public ImageIcon man=new ImageIcon("框.png");
	public int imanum=0;
	public ArrayList<String> killmessage= new ArrayList<String>();
	public Person(int hp,int atk,int def,int step,String name) {
		super(atk,step);
		Random rand = new Random();
		personid=rand.nextInt(1000000)+1;
		this.hp=hp;
		this.hplimit=hp;
		this.def=def;
		this.name=name;
		this.alive=true;
		if(MainFrame.LAN)
			drawfrequency=8;
		else
			drawfrequency=20;
		File file=new File("hhhhh");
		String str="";
		while(man.getImage().getWidth(null)!=man.getImage().getHeight(null))
		{
		file=new File("hhhhh");
		while(!file.exists())
		{str = String.format("%03d", (rand.nextInt(386)+1));
		file = new File("man/"+str+".png");}
		man=new ImageIcon("man/"+str+".png");
		man.setDescription(str);
		}
	}
	public boolean ismove()//判断是否在移动中
	{
		if(up||down||right||left)
		{
			return true;
		}
		else
			return false;
	}
	public void run() {
		while(isalive()){
			move();
			if(speedup>0)
				speedup-=1;
			else if(step>1)
				{step-=1;
				speedup+=500;
				}
				
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public ArrayList<Bullet> attack()//射出子弹
	{
		ArrayList<Bullet> b=new ArrayList<Bullet>();
		if(bulletnum>0)
			{b=wp.attack(this);
			if(b.size()>0)
				bulletnum-=1;
			}
		return b;
	}
	public boolean isalive()//判断是否仍存活
	{
		if(this.hp<=0)
			{this.die();}
		return alive;
	}
	public ArrayList<String> getinfomation()
	{
		if(hp<0)
			hp=0;
		if(bulletnum<0)
			bulletnum=0;
		ArrayList<String> s=new ArrayList<String>();
		s.add("name: "+name);
		s.add("attack: "+atk);
		s.add("defend: "+def);
		s.add("HP: "+hp);
		s.add("speed: "+step);
		s.add("bullet: "+bulletnum);
		return s;
		
	}

	public void nofacearound()//把faceto都变成false，避免出现一直true的情况
	{
		facetoup=false;
		facetodown=false;
		facetoright=false;
		facetoleft=false;
	}
	
	public void drawB(Graphics g,Player player)//画出人物
	{
	Image im=man.getImage();
	if(!ismove())
	{
	if (facetoright)
		g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5-(player.getI())*elesize)-(player.my%elesize),0,im.getHeight(null)/2,im.getWidth(null)/4, 3*im.getHeight(null)/4, null); 
	else if (facetoleft)
		g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5-(player.getI())*elesize)-(player.my%elesize),0,im.getHeight(null)/4,im.getWidth(null)/4, im.getHeight(null)/2, null); 
	else if (facetoup)
		g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5-(player.getI())*elesize)-(player.my%elesize),0,3*im.getHeight(null)/4,im.getWidth(null)/4, im.getHeight(null), null); 
	else
		g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5-(player.getI())*elesize)-(player.my%elesize),0,0, im.getWidth(null)/4,im.getHeight(null)/4, null); 
	}
	else
	{
		if (right)	
			g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5-(player.getI())*elesize)-(player.my%elesize),((imanum/drawfrequency)%4)*im.getWidth(null)/4,im.getHeight(null)/2,((imanum/drawfrequency)%4+1)*im.getWidth(null)/4, 3*im.getHeight(null)/4, null); 
		else if (left)
			g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5-(player.getI())*elesize)-(player.my%elesize),((imanum/drawfrequency)%4)*im.getWidth(null)/4,im.getHeight(null)/4,((imanum/drawfrequency)%4+1)*im.getWidth(null)/4, im.getHeight(null)/2, null); 
		else if (up)
			g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5+5-(player.getI())*elesize)-(player.my%elesize),((imanum/drawfrequency)%4)*im.getWidth(null)/4,3*im.getHeight(null)/4,((imanum/drawfrequency)%4+1)*im.getWidth(null)/4, im.getHeight(null), null); 
		else
			g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5-(player.getI())*elesize)-(player.my%elesize),((imanum/drawfrequency)%4)*im.getWidth(null)/4,0, ((imanum/drawfrequency)%4+1)*im.getWidth(null)/4,im.getHeight(null)/4, null); 
		imanum+=1;
	}
	}
}
