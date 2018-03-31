package man;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import gui.Gui;
import map.MainFrame;
import map.ReadMapFile;
import others.Timer;

public class Player extends Person{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean start=false;//联机模式下判断对方是否已链接
	public boolean end=false;
	public ImageIcon endima;//游戏结束的图案
	public ArrayList<Bullet> LANbulist= new ArrayList<Bullet>();
	public int px = panelX/2;
	public int py = panelY/2;
	public ImageIcon OVER = new ImageIcon("OVER.png");
	public ImageIcon win = new ImageIcon("win.png");
	public int killnumber=0;
	public Player(int hp, int atk, int def, int step,String name,int widesize,int highsize) {
		super(hp, atk, def, step,name);
		Random rand = new Random();
		x=y=0;
		while(ReadMapFile.map2[y/elesize][x/elesize]!=0)
		{
		x=(rand.nextInt(widesize)+1)*elesize-elesize/2;//为保证人物移动不出问题，人物出生点需要在地图格子中央
		y=(rand.nextInt(highsize)+1)*elesize-elesize/2;//改变*前面的数字以改变人物位置
		}
		mx=x-25;
		my=y-25;
	}
	//角色相对游戏面板的位置不变，此处动的是地图
	public void draw(Graphics g)//画出人物
	{
	Image im=man.getImage();
	if(!ismove())
	{
	if (facetoright)	
		g.drawImage(im,px-elesize/2-5, py-elesize/2-5, px+elesize/2+5, py+elesize/2+5,0,im.getHeight(null)/2,im.getWidth(null)/4, 3*im.getHeight(null)/4, null); 
	else if (facetoleft)
		g.drawImage(im,px-elesize/2-5, py-elesize/2-5, px+elesize/2+5, py+elesize/2+5,0,im.getHeight(null)/4,im.getWidth(null)/4, im.getHeight(null)/2, null); 
	else if (facetoup)
		g.drawImage(im,px-elesize/2-5, py-elesize/2-5, px+elesize/2+5, py+elesize/2+5,0,3*im.getHeight(null)/4,im.getWidth(null)/4, im.getHeight(null), null); 
	else
		g.drawImage(im,px-elesize/2-5, py-elesize/2-5, px+elesize/2+5, py+elesize/2+5,0,0, im.getWidth(null)/4,im.getHeight(null)/4, null); 
	}
	else
	{
		if (right)	
			g.drawImage(im,px-elesize/2-5, py-elesize/2-5, px+elesize/2+5, py+elesize/2+5,((imanum/drawfrequency)%4)*im.getWidth(null)/4,im.getHeight(null)/2,((imanum/drawfrequency)%4+1)*im.getWidth(null)/4, 3*im.getHeight(null)/4, null); 
		else if (left)
			g.drawImage(im,px-elesize/2-5, py-elesize/2-5, px+elesize/2+5, py+elesize/2+5,((imanum/drawfrequency)%4)*im.getWidth(null)/4,im.getHeight(null)/4,((imanum/drawfrequency)%4+1)*im.getWidth(null)/4, im.getHeight(null)/2, null); 
		else if (up)
			g.drawImage(im,px-elesize/2-5, py-elesize/2-5, px+elesize/2+5, py+elesize/2+5,((imanum/drawfrequency)%4)*im.getWidth(null)/4,3*im.getHeight(null)/4,((imanum/drawfrequency)%4+1)*im.getWidth(null)/4, im.getHeight(null), null); 
		else
			g.drawImage(im,px-elesize/2-5, py-elesize/2-5, px+elesize/2+5, py+elesize/2+5,((imanum/drawfrequency)%4)*im.getWidth(null)/4,0, ((imanum/drawfrequency)%4+1)*im.getWidth(null)/4,im.getHeight(null)/4, null); 
		imanum+=1;
	}
	}
	public void judge(ArrayList<Person> pe,Graphics g,Timer time)
	{
		if(MainFrame.LAN)
		{
			if(pe.size()>1)
				start=true;
		}
		else
			start=true;
		if(!end&&start&&!MainFrame.two)
		{
		if(hp<=0)
		{
			g.drawImage(OVER.getImage(),panelX-680, panelY-600,panelX+500,panelY+500, 0, 0, 1000, 1000, null); 
			end=true;
			endima=OVER;
			Gui.sql.Re_gamenumber(Gui.user);
			Gui.sql.Re_time(Gui.user,time.whattime());
			Gui.sql.Re_killingnumber(Gui.user, this.killnumber);
			if(MainFrame.breakthrough)
			{
				Gui.sql.Re_checkpoint(Gui.user, MainFrame.chapter);
			}
			Gui.sql.getone(man.getDescription(), Gui.user);
		}
		else
		{
			if(pe.size()==1)
			{
				g.drawImage(win.getImage(),panelX-680, panelY-600,panelX+500,panelY+500, 0, 0, 1000, 1000, null); 
				end=true;
				endima=win;
				Gui.sql.Re_gamenumber(Gui.user);
				Gui.sql.Re_winnumber(Gui.user);
				Gui.sql.Re_time(Gui.user,time.whattime());
				Gui.sql.Re_killingnumber(Gui.user, this.killnumber);
				Gui.sql.getone(man.getDescription(), Gui.user);
			}
		}
		}
		else if(MainFrame.two&&!end)
		{
			boolean wins=true;
			boolean losts=true;
			for(Person p:pe)
			{
				if(p instanceof ComputerPlayer)
				{
					wins=false;
				}
				else if(p instanceof Player)
				{
					losts=false;
				}
			}
			if(wins)
			{
				g.drawImage(win.getImage(),panelX-680, panelY-600,panelX+500,panelY+500, 0, 0, 1000, 1000, null); 
				end=true;
				endima=win;
				Gui.sql.Re_gamenumber(Gui.user);
				Gui.sql.Re_winnumber(Gui.user);
				Gui.sql.Re_time(Gui.user,time.whattime());
				Gui.sql.Re_killingnumber(Gui.user, this.killnumber);
				Gui.sql.getone(man.getDescription(), Gui.user);
			}
			else if(losts)
			{
				g.drawImage(OVER.getImage(),panelX-680, panelY-600,panelX+500,panelY+500, 0, 0, 1000, 1000, null); 
				end=true;
				endima=OVER;
				Gui.sql.Re_gamenumber(Gui.user);
				Gui.sql.Re_time(Gui.user,time.whattime());
				Gui.sql.Re_killingnumber(Gui.user, this.killnumber);
				Gui.sql.getone(man.getDescription(), Gui.user);
				if(MainFrame.breakthrough)
				{
					Gui.sql.Re_checkpoint(Gui.user, MainFrame.chapter);
				}
			}
		}
		else if(start)
			g.drawImage(endima.getImage(),panelX-680, panelY-600,panelX+500,panelY+500, 0, 0, 1000, 1000, null); 


	}
}
