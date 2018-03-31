package map;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import man.Bullet;
import man.ComputerPlayer;
import man.Person;
import man.Player;
import others.Extension;
import others.Timer;
import resources.Resource;
import socket.SocketService;

public class Drawperson implements gameConfig{
	static ArrayList<Bullet> tempList = new ArrayList<Bullet>();
	static ArrayList<Person> petem=new ArrayList<Person>();
	static HashMap<String,Long> map = new HashMap<String,Long>();
	public static ArrayList<Person> drawperson(ArrayList<Person> peList,Graphics g,Player player,boolean LAN,boolean service,SocketService ss,Extension p11,ArrayList<Resource> reList,Timer time)
	{
		if(ReadMapFile.pathis.equals("map/SnowWorld.map")||ReadMapFile.pathis.equals("map/DeathDesert.map"))
			g.setColor(bulletcolor2);
		else
			g.setColor(bulletcolor);
		String info=new String(peList.size()+" alive   "+player.killnumber+" kill");
		for (Person per : peList)
		{
			if (per.killmessage.size() !=0) {
				for (int i=0;i<per.killmessage.size();i++)
					map.put(per.killmessage.get(i).toString(),time.whattime());
				per.killmessage.clear();
			}
			if((per instanceof Player))
			{			
				if(per.alive)
				{
				if(per.personid==player.personid)
					{
					((Player) per).draw(g);
					if(LAN)
					{
						for(Bullet b:((Player) per).LANbulist)
						{
							b.send=false;
							ArrayList<Person> pp=new ArrayList<Person>();
							pp.add(per);
							b.impact(pp, player);
						}
						((Player) per).LANbulist.clear();
					}
					}
				else
					per.drawB(g, player);
				petem.add(per);
				}
				
			}	
			else if (per instanceof ComputerPlayer) 
			{
				if(per.alive)
				{
					//获取毒圈  玩家信息 
					if(!LAN||service)
					{
					if(MainFrame.timeup==0)
					((ComputerPlayer) per).getInformation(p11.wlimit/2-p11.width,(ArrayList<Person>) peList.clone(), reList );    
					else
						{ArrayList<Person> pp=new ArrayList<Person>();
						pp.add(player);
						((ComputerPlayer) per).getInformation(p11.wlimit/2-p11.width,(ArrayList<Person>) pp.clone(), reList );    
						}
					}
					((ComputerPlayer) per).draw(g,player);
					petem.add(per);
				}
			}
			if(!per.buList.isEmpty())
			{
				for(Bullet i :per.buList)
				{
					i.impact(peList,player);
					if(i.alive)
						{
						g.fillRect((player.px-elesize/2)+(i.x-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(i.y-(player.getI())*elesize)-(player.my%elesize), 3, 3);
						tempList.add(i);
						}
				}
				per.buList.clear();
				per.buList.addAll(tempList);
				tempList.clear();
			}
		}
		//人物头顶的第三层的掩盖层
		for(int i=player.getI()-8;i<=player.getI()+9;i++)
		{
			for(int j=player.getJ()-9;j<=player.getJ()+9;j++)
			{
				if(i>=0&&j>=0&&i<ReadMapFile.map3.length&&j<ReadMapFile.map3[0].length&&ReadMapFile.map3[i][j]!=0)
				{
					g.drawImage( GetMap.int2icon(0).getImage(), (player.px-elesize/2)+((j-player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+((i-player.getI())*elesize)-(player.my%elesize), elesize, elesize, null);
					ImageIcon icon3 = GetMap.int2icon(ReadMapFile.map3[i][j]);
					Image ico=icon3.getImage();
					g.drawImage(ico, (player.px-elesize/2)+((j-player.getJ())*elesize)-(player.mx%elesize)-(int) (elesize*0.5), (player.py-elesize/2)+((i-player.getI())*elesize)-(player.my%elesize)-(int) (elesize*0.5),(int) (elesize*1.5), (int)(elesize*1.5), null);

				}
			}
		}
		if(MainFrame.shadow&&!player.end)
			g.drawImage(shadow.getImage(), 0, 0, panelX, panelY, null);
		g.setColor(strcolor);
		g.drawString(info, panelX/2-20, 20);
		int loc=40;
		HashMap<String,Long> tmap=new HashMap<String,Long>();
		for(String str:map.keySet())
		{
			if(map.get(str)>time.whattime())
			{
				map.clear();
				break;
			}
			if(time.whattime()-map.get(str)<3000)
				{g.drawString(str, panelX/2-20, loc);
				loc+=20;
				tmap.put(str, map.get(str));
				}
		}
		map.clear();
		map.putAll(tmap);
		peList.clear();
		peList.addAll(petem);
		petem.clear();
		return peList;
	}

}
