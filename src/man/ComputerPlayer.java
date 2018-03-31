package man;           

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.ImageIcon;

import map.ReadMapFile;
import resources.Resource;

public class ComputerPlayer extends Person{   
	
	public boolean issafe = true;
	public boolean isresource = false ;
	public boolean isenemy = false ;
	public int safex=1000;           //毒圈的中心点 x,y 安全半径R
	public int safey=1000;
	public int safeR;
	public boolean special=false;
	public int specialnum=0;
	public int feelingR=350;		//感知范围
	public int attackmodel;
	public ArrayList<Person> enemy = new ArrayList<Person>();
	public ArrayList<Resource> resource = new ArrayList<Resource>();
	Resource goalresource;
	Person goalenemy;
	int[][] map=ReadMapFile.map3.clone(); 
	int speedlimit = 0;
	int movelimit = 0;
	double mover = Math.random();
	public static int Difficulty;
	 
	public ComputerPlayer(int hp, int atk, int def, int step, String name) {
		super(hp, atk, def, step, name);
		this.setplace();
	}
	
	//设置初始位置
	public void setplace() {
		this.x = (int) (Math.random()*2000);
		this.y = (int) (Math.random()*2000); 
		while(ReadMapFile.map2[y/elesize][x/elesize]!=0){
			this.x = (int) (Math.random()*2000);
			this.y = (int) (Math.random()*2000); 
		}
	}
	
	// 获取毒圈  敌人 资源 信息         
	public synchronized void getInformation(int safeR, ArrayList<Person> enemy, ArrayList<Resource> resource) {
		this.safeR = safeR;
		this.resource = resource;
		this.enemy=enemy;
	}

	
	//墙体碰撞判断
	public void movejudge(String direction){
		if(direction=="up"){
			if(y/elesize-1>0)
			{
			if(ReadMapFile.map2[y/elesize-1][x/elesize]==0) {
			y=y-step;
			}
			else if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0){
				int y1 = (y/elesize-1)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){  
					y=y-step;
				}
			}
		}
		}
		if(direction=="down"){
		if(y/elesize+1<=40)
		{
			if(ReadMapFile.map2[y/elesize+1][x/elesize]==0){
			y=y+step;
			}
			else if(ReadMapFile.map2[y/elesize+1][x/elesize]!=0){
				int y1 = (y/elesize+1)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){
					y=y+step;
				}
			}
		}
			}
		
		if(direction=="left"){
		if(x/elesize-1>0)
		{
			if(ReadMapFile.map2[y/elesize][x/elesize-1]==0){
			x=x-step;
			}
			else if(ReadMapFile.map2[y/elesize][x/elesize-1]!=0) {
				int x1 = (x/elesize-1)*elesize+elesize/2; 
				if((x-x1)*(x-x1)>=elesize*elesize){ 
					x=x-step;
				}
			}
		}
		}
		if(direction=="right"){
			if(x/elesize+1<=40)
			{
			if(ReadMapFile.map2[y/elesize][x/elesize+1]==0){ 
			x=x+step;
			}
			else if (ReadMapFile.map2[y/elesize][x/elesize+1]!=0){  
				int x1 = (x/elesize+1)*elesize+elesize/2; 
				if((x-x1)*(x-x1)>=elesize*elesize){  
					x=x+step;
				}
			}
		}
		}
	}
	
	//距离判断
	public boolean judgement(int x, int y, int r) {
		if (Math.pow(this.x-x,2)+Math.pow(this.y-y,2) < r*r ) {
			return true;
		}else {
			return false;
		}
	}
	
	// 判断是否在圈内
	public void issafe() {   
		issafe = true;
		if (! (x<safex+safeR && x>safex-safeR && y<safey+safeR && y>safey-safeR) ) 
			issafe = false;
	}
	
	// 判断感知范围内是否有资源
	public void isresource() {   
		isresource = false;
		goalresource = null;
		for (Resource res :resource) {
			if 	(res.isAlive)
				if (judgement (res.x, res.y, feelingR))
					if (res.x<safex+safeR && res.x>safex-safeR && res.y<safey+safeR && res.y>safey-safeR) { 
						goalresource = res;
						isresource = true;
						if(y/elesize==res.y/elesize && x/elesize==res.x/elesize) {
						
						}else {
							if(res.x > x) {
								if(ReadMapFile.map2[y/elesize][x/elesize+1]!=0) {
									 if(res.y >y) {
										 if(ReadMapFile.map2[y/elesize+1][x/elesize]!=0) {
											 goalresource = null;
											 isresource = false;
											 special=true;
										 }
									 }else if(res.y<y) {
										 if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0) {
											 goalresource = null;
											 isresource = false;
											 special=true;
										 }
									 }else if(res.y==y) {
										 goalresource = null;
										 isresource = false;
										 special=true;
									 }
								}
							}else if(res.x < x){
								if(ReadMapFile.map2[y/elesize][x/elesize-1]!=0 ) {
									 if(res.y >y) {
										 if(ReadMapFile.map2[y/elesize+1][x/elesize]!=0) {
											 goalresource = null;
											 isresource = false;
											 special=true;
										 }
									 }else if(res.y<y) {
										 if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0) {
											 goalresource = null;
											 isresource = false;
											 special=true;
										 }
									 }else if(res.y==y) {
										 goalresource = null;
										 isresource = false;
										 special=true;
									 }
								}
							}else if(res.x==x){
								if(res.y >y) {
									 if(ReadMapFile.map2[y/elesize+1][x/elesize]!=0) {
										 goalresource = null;
										 isresource = false;
										 special=true;
									 }
								 }else if(res.y<y) {
									 if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0) {
										 goalresource = null;
										 isresource = false;
										 special=true;
									 }									 
								 }
							}				
						}
						if (isresource)
							break;
					}
		}
				
	}
			
	
	// 判断感知范围内是否有敌人
	public void isenemy() { 
		isenemy = false;
		goalenemy = null;
		for (Person per :enemy) {
			if (per.personid != this.personid)
				if (per.isalive())
					if (judgement (per.x, per.y, feelingR))
						if (per.x<safex+safeR && per.x>safex-safeR && per.y<safey+safeR && per.y>safey-safeR){
							goalenemy = per;
							isenemy = true;
							if(y/elesize==per.y/elesize && x/elesize==per.x/elesize) {
								
							}else {
								if(per.x > x) {
									if(ReadMapFile.map2[y/elesize][x/elesize+1]!=0) {
										 goalenemy =null;
										 isenemy = false;
										 special=true;
									}
								}else if(per.x < x){
									if(ReadMapFile.map2[y/elesize][x/elesize-1]!=0) {
										 goalenemy =null;
										 isenemy = false;
										 special=true;
									}
								}
								if(per.y >y) {
									 if(ReadMapFile.map2[y/elesize+1][x/elesize]!=0) {
										 goalenemy =null;
										 isenemy = false;
										 special=true;
									 }
								 }else if(per.y<y) {
									 if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0) {
										 goalenemy =null;
										 isenemy = false;
										 special=true;
									 }
															 
								 }
							}				
							
							break;
						}	
		}
		if(goalenemy!=null) {
			if ( x > goalenemy.x && y > goalenemy.y-100 && y < goalenemy.y+100) {
				for (int i=(goalenemy.x/elesize)+1;i<x/elesize;i++)
					if (ReadMapFile.map2[y/elesize][i]!=0 ) {
						isenemy=false;
						goalenemy=null;
						special=true;
						break;
					}
			}else if( x < goalenemy.x && y > goalenemy.y-100 && y < goalenemy.y+100){
				for (int i=x/elesize+1;i<goalenemy.x/elesize;i++)
					if (ReadMapFile.map2[y/elesize][i]!=0 ) {
						isenemy=false;
						goalenemy=null;
						special=true;
						break;
					}
			}else if( y > goalenemy.y && x > goalenemy.x-100 && x < goalenemy.x+100){
				for (int i=goalenemy.y/elesize+1;i<y/elesize;i++)
					if (ReadMapFile.map2[i][x/elesize]!=0 ){
						isenemy=false;
						goalenemy=null;
						special=true;
						break;
					}
			}else if( y < goalenemy.y && x > goalenemy.x-100 && x < goalenemy.x+100){
				for (int i=y/elesize+1;i<goalenemy.y/elesize;i++)
					if (ReadMapFile.map2[i][x/elesize]!=0 ) {
						isenemy=false;
						goalenemy=null;
						special=true;
						break;
					}
			}else if( x > goalenemy.x+100 && y > goalenemy.y+100){
				for (int i=0;i<8;i++)
					if (ReadMapFile.map2[y/elesize-i][x/elesize-i]!=0 ||ReadMapFile.map2[y/elesize-i-1][x/elesize-i]!=0||ReadMapFile.map2[y/elesize-i][x/elesize-i-1]!=0) {
						isenemy=false;
						goalenemy=null;
						special=true;
						break;
					}
			}else if( x < goalenemy.x-100 && y > goalenemy.y+100){
				for (int i=0;i<8;i++)
					if (ReadMapFile.map2[y/elesize-i][x/elesize+i]!=0||ReadMapFile.map2[y/elesize-i][x/elesize+i+1]!=0||ReadMapFile.map2[y/elesize-i-1][x/elesize+i]!=0) {
						isenemy=false;
						goalenemy=null;
						special=true;
						break;
					}
			}else if( x > goalenemy.x+100 && y < goalenemy.y-100){
				for (int i=0;i<8;i++)
					if (ReadMapFile.map2[y/elesize+i][x/elesize-i]!=0||ReadMapFile.map2[y/elesize+i][x/elesize-i-1]!=0||ReadMapFile.map2[y/elesize+i+1][x/elesize-i]!=0) {
						isenemy=false;
						goalenemy=null;
						special=true;
						break;
					}
			}else if( x < goalenemy.x-100 && y < goalenemy.y-100){
				for (int i=0;i<8;i++)
					if (ReadMapFile.map2[y/elesize+i][x/elesize+i]!=0||ReadMapFile.map2[y/elesize+i+1][x/elesize+i]!=0||ReadMapFile.map2[y/elesize+i][x/elesize+i+1]!=0) {
						isenemy=false;
						goalenemy=null;
						special=true;
						break;
					}
			}
		}
		try {
			
			if (map[goalenemy.y/elesize][goalenemy.x/elesize]!=0) {
				isenemy=false;
				goalenemy=null;
			}
		}catch(NullPointerException e){
		}
	}

	//寻找安全地带的移动方式
	public void moveforsafe(){	
		nofacearound();
    	int rex=x;
    	int rey=y;
		if (safex-x > 0){
			movejudge("right");
			facetoright = true;
		}else if (safex-x < 0){
			movejudge("left");
			facetoleft = true;
		}
		if (safey-y > 0){
			movejudge("down");
			facetodown = true;
		}else if(safey-y < 0){
			movejudge("up");
			facetoup = true;
		}
		if(rex==x && rey==y) {
			special=true;
		}
	}
	
	//有资源时的移动方式
	public void moveforresource(){	
		nofacearound();
		int rex=x;
    	int rey=y;
		if (goalresource.x-x > 0){
			movejudge("right");
			facetoright = true;
		}else if (goalresource.x-x < 0){
			movejudge("left");
			facetoleft = true;
		}
		if (goalresource.y-y > 0){
			movejudge("down");
			facetodown = true;
		}else if(goalresource.y-y < 0){
			movejudge("up");
			facetoup = true;
		}
		if(rex==x && rey==y) {
			special=true;
		}
	}
	
	//有敌人时的移动方式
	public void moveforenemy(){	
		if	(Difficulty==2) {
			switch (attackmodel) {
			case 1:
				if (goalenemy.y-y > 0){
					movejudge("down");
				}else if(goalenemy.y-y < 0){
					movejudge("up");
				}
				break;
			case 2:
				if (goalenemy.y-y > 0){
					movejudge("down");
				}else if(goalenemy.y-y < 0){
					movejudge("up");
				}
				break;
			case 3:
				if (goalenemy.x-x > 0){
					movejudge("right");
				}else if(goalenemy.x-x < 0){
					movejudge("left");
				}
				break;
			case 4:
				if (goalenemy.x-x > 0){
					movejudge("right");
				}else if(goalenemy.x-x < 0){
					movejudge("left");
				}
				break;
			case 5:
				if(Math.pow((goalenemy.y-y), 2)/Math.pow((goalenemy.x-x), 2) <1) {
					movejudge("left");
					movejudge("down");
				}else if(Math.pow((goalenemy.y-y), 2)/Math.pow((goalenemy.x-x), 2) >1) {
					movejudge("right");
					movejudge("up");
				}
				break;
			case 6:	
				if(Math.pow((goalenemy.y-y), 2)/Math.pow((goalenemy.x-x), 2) <1) {
					movejudge("right");
					movejudge("down");
				}else if(Math.pow((goalenemy.y-y), 2)/Math.pow((goalenemy.x-x), 2) >1) {
					movejudge("left");
					movejudge("up");
				}	
				break;
			case 7:	
				if(Math.pow((goalenemy.y-y), 2)/Math.pow((goalenemy.x-x), 2) >1) {
					movejudge("right");
					movejudge("down");
				}else if(Math.pow((goalenemy.y-y), 2)/Math.pow((goalenemy.x-x), 2) <1) {
					movejudge("left");
					movejudge("up");
				}
				break;
			case 8:
				if(Math.pow((goalenemy.y-y), 2)/Math.pow((goalenemy.x-x), 2) >1) {
					movejudge("left");
					movejudge("down");
				}else if(Math.pow((goalenemy.y-y), 2)/Math.pow((goalenemy.x-x), 2) <1) {
					movejudge("right");
					movejudge("up");
				}
				break;
			default:
				break;
			}
		}else if (Difficulty==1) {
			if (y>goalenemy.y) {
				movejudge("up");
			}else if(y<goalenemy.y) {
				movejudge("down");
			}
				
		}
	}
	
	//随机移动
	public void moveforrandom(){	
		nofacearound();
		int rex=x;
		int rey=y;
		for(;;) {
			if (mover<=0.25){
				movejudge("right");
				facetoright = true;
			}else if (mover>0.25 & mover<=0.5) {
				movejudge("left");
				facetoleft = true;
				
			}else if (mover>0.5 & mover<=0.75) {
				movejudge("down");
				facetodown = true;
				
			}else {
				movejudge("up");
				facetoup = true;
			}
			if (rex==x && rey==y) {
				mover=Math.random();
			}else {
				break;
			}
		}
	}
	
	public void attackdirection()//射击方向
	{
		nofacearound();
		if (Difficulty==2) {
			if ( x > goalenemy.x && y > goalenemy.y-100 && y < goalenemy.y+100) {
					
					attackmodel = 1;
					facetoleft = true;
				
			}else if( x < goalenemy.x && y > goalenemy.y-100 && y < goalenemy.y+100){
				
			
					attackmodel = 2;
					facetoright = true;
				
			}else if( y > goalenemy.y && x > goalenemy.x-100 && x < goalenemy.x+100){
				
				
					attackmodel = 3;
					facetoup = true;
				
			}else if( y < goalenemy.y && x > goalenemy.x-100 && x < goalenemy.x+100){
				
					
					attackmodel = 4;
					facetodown = true;
				
			}else if( x > goalenemy.x+100 && y > goalenemy.y+100){
				
					
					attackmodel = 5;
					facetoleft = true;
					facetoup = true;
				
			}else if( x < goalenemy.x-100 && y > goalenemy.y+100){
				
					
					attackmodel = 6;
					facetoright = true;
					facetoup = true;
				
			}else if( x > goalenemy.x+100 && y < goalenemy.y-100){
				
					attackmodel = 7;
					facetoleft = true;
					facetodown = true;
				
			}else if( x < goalenemy.x-100 && y < goalenemy.y-100){
					
					attackmodel = 8;
					facetoright = true;
					facetodown = true;
				
			}
		}else if (Difficulty==1) {
			if (x>goalenemy.x) {
				facetoleft=true;
			}else {
				facetoright=true;
			}
		}
				
	}
	
	// 先判断   圈内先打人后资源   圈外先进圈
	public void run() {
		while(isalive()){
			if(special) {
				moveforrandom();
				movelimit +=1;
				if (movelimit%100 == 0) {
					mover = Math.random();
					movelimit=0;
				}
				specialnum++;
				if (specialnum==100) {
					special=false;
					specialnum=0;
				}
			}else {
				issafe();
				if (!issafe) {
					moveforsafe();
				}else {
					isenemy();
					if (isenemy ) {
					
						attackdirection();
						if (speedlimit%20 == 0) {   //限制子弹发射速度
							this.buList.addAll(this.attack());
							speedlimit=0;
						}
						speedlimit +=1;
						if (hp>20 && this.bulletnum != 0) {
							moveforenemy();
						}else {
							isresource();
							if (isresource) {
								moveforresource();
							}else {
								moveforrandom();
								movelimit +=1;
								if (movelimit%50 == 0) {
									mover = Math.random();
									movelimit=0;
								}
							}
						}
					}else {
						isresource();
						if (isresource) {
							moveforresource();
						}else {
							moveforrandom();
							movelimit +=1;
							if (movelimit%50 == 0) {
								mover = Math.random();
								movelimit=0;
							}
						}
					}
				}			
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void draw(Graphics g ,Player player){
		{
			Image im=man.getImage();
			if (facetoright)	
				g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5-(player.getI())*elesize)-(player.my%elesize),((imanum/(drawfrequency*2))%4)*im.getWidth(null)/4,im.getHeight(null)/2,((imanum/(drawfrequency*2))%4+1)*im.getWidth(null)/4, 3*im.getHeight(null)/4, null); 
			else if (facetoleft)
				g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5-(player.getI())*elesize)-(player.my%elesize),((imanum/(drawfrequency*2))%4)*im.getWidth(null)/4,im.getHeight(null)/4,((imanum/(drawfrequency*2))%4+1)*im.getWidth(null)/4, im.getHeight(null)/2, null); 
			else if (facetoup)
				g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5+5-(player.getI())*elesize)-(player.my%elesize),((imanum/(drawfrequency*2))%4)*im.getWidth(null)/4,3*im.getHeight(null)/4,((imanum/(drawfrequency*2))%4+1)*im.getWidth(null)/4, im.getHeight(null), null); 
			else
				g.drawImage(im,(player.px-elesize/2)+(x-elesize/2-5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y-elesize/2-5-(player.getI())*elesize)-(player.my%elesize),(player.px-elesize/2)+(x+elesize/2+5-(player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+(y+elesize/2+5-(player.getI())*elesize)-(player.my%elesize),((imanum/(drawfrequency*2))%4)*im.getWidth(null)/4,0, ((imanum/(drawfrequency*2))%4+1)*im.getWidth(null)/4,im.getHeight(null)/4, null); 
			imanum+=1;
		}
		}
}
