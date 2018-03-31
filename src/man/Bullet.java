package man;

import java.util.ArrayList;

import map.MainFrame;
import map.ReadMapFile;
import others.MP3;

public class Bullet extends Move {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int range=80;//射程
	private Person from;
	private int i=0;
	public boolean send=false;
	public Bullet(Person from) {
		super(from.atk,5);
		this.from=from;
		this.x=from.x;
		this.y=from.y;
		this.up=from.facetoup;
		this.down=from.facetodown;
		this.left=from.facetoleft;
		this.right=from.facetoright;
		if(this.up)
		{
			this.y-=elesize/2;
		}
		if(this.down)
		{
			this.y+=elesize/2;
		}
		if(this.right)
		{
			this.x+=elesize/2;
		}
		if(this.left)
		{
			this.x-=elesize/2;
		}
		this.alive=true;
	}
	public void run() {
		while(i<range&&this.alive){
			if(ReadMapFile.map2[y/elesize][x/elesize]!=0)
				{this.alive=false;
				break;
				}
			while(send);
			move();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i+=1;}
		this.alive=false;
	}
	public void impact(ArrayList<Person> list,Player player)
	{
		for(Person i :list)
		{
			if(!i.equals(from))
			{
			if(this.x<=(i.x+elesize/2)&&(this.x>=(i.x-elesize/2))&&this.y<=(i.y+elesize/2)&&(this.y>=(i.y-elesize/2))&&i.hp>0)
			{
				if(i.personid!=player.personid&&i instanceof Player&&MainFrame.LAN)
				{
					{
					Bullet bb=new Bullet(this.from);
					bb.x=i.x;
					bb.y=i.y;
					bb.i=0;
					bb.send=true;
					bb.up=this.up;
					bb.down=this.down;
					bb.left=this.left;
					bb.right=this.right;
					bb.atk=this.atk;
					((Player)i).LANbulist.add(bb);
					}
				}
				else
				{					
					if(this.atk-i.def>=1)
					{i.hp-=this.atk-i.def;
					}
					else
						{i.hp-=1;}
					
					if (i.hp<=0) {
							i.killmessage.add(from.name+" kill "+i.name) ;
							if (from instanceof Player)
							{((Player) from).killnumber++;
							String s;
							if(((Player) from).killnumber<6)
							 	{s = String.valueOf(((Player) from).killnumber);}
							else
								{s="6";}
							if(MainFrame.music)
							{
							MP3 ss=new MP3(s+".mp3");
							ss.isloop=false;
							ss.start();
							}
							}
							}
						
					
					if(this.up)
					{
						if(ReadMapFile.map2[y/elesize-1][x/elesize]==0)
						{
						i.y-=5;
						i.my-=5;
						}
					}
					if(this.down)
					{
						if(y/elesize+1<40)
						if(ReadMapFile.map2[y/elesize+1][x/elesize]==0)
						{
						i.y+=5;
						i.my+=5;
						}
					}
					if(this.right)
					{
						if(x/elesize+1<40)
							if(ReadMapFile.map2[y/elesize][x/elesize+1]==0){ 
						i.x+=5;
						i.mx+=5;
					}
					}
					if(this.left)
					{if(ReadMapFile.map2[y/elesize][x/elesize-1]==0)
					{

						i.x-=5;
						i.mx-=5;
					}
					}
					
				}
				this.alive=false;
				return;
			}
		}
		}
	}
	
}
