package socket;

import java.io.Serializable;
import java.util.ArrayList;
import man.Bullet;
import man.ComputerPlayer;
import man.Person;
import man.Player;
import others.Extension;
import resources.Resource;

public class Socketsolver {
	static ArrayList<Serializable> LANarray=new ArrayList<Serializable>();
	public static ArrayList<Object> socketsolver(boolean LAN,boolean service,ArrayList<Person> peList,ArrayList<Resource> reList,SocketService ss,SocketClient sock,Extension p11,Player player) 
	{
		if(LAN&&service)
		{
			LANarray.clear();
			int i=-1;
			for (Person per:peList)
			{
				if(per instanceof Player && per.personid!=player.personid)
					{i=peList.indexOf(per);
					System.out.println(i);
					LANarray.add(((Player)per).LANbulist);
					}
			else if(per instanceof ComputerPlayer)
			{
//
			}

				else
					LANarray.add(per);
			} 
			for(Resource res:reList)
			{
				LANarray.add(res);
			}
			LANarray.add(p11);
			if(ss.socket!=null)
				{
				ss.Seta(LANarray);
				if(ss.theget!=null)
				{if(i!=-1)
					peList.remove(i);
				Player player2=(Player) ss.theget;
				player.LANbulist=player2.LANbulist;
				peList.add(player2);}

				}
			LANarray.clear();
			
		}
		if(LAN&&!service)
		{

				for (Person per:peList)
				{
					if(per instanceof Player && per.personid!=player.personid)
						player.LANbulist=((Player)per).LANbulist;
				}
				sock.send(player);
			LANarray=sock.theget;
			if(LANarray.size()>0)
			{
			peList.clear();
			reList.clear();
			if(player.alive)
				peList.add(player);
			}
			for(Serializable lan:LANarray)
			{
				if(lan instanceof Person)
				{
					peList.add((Person) lan);		
				}
				else if(lan instanceof Resource)
				{ 
					reList.add((Resource) lan);
				}
				else if(lan instanceof Extension)
					p11=(Extension) lan;
				else if(lan instanceof  ArrayList<?>)
				{
					player.LANbulist.addAll((ArrayList<Bullet>)lan);
				}
			}
			LANarray.clear();
			}
		peList.add(player);
		ArrayList<Object> obj=new ArrayList<Object>();
		obj.add(peList);
		obj.add(reList);
		obj.add(p11);
		return obj;
	}

}
