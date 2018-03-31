package man;

import map.ReadMapFile;
import map.gameConfig;

public class Move extends Thread implements gameConfig{//所有能动的东西的父类，包括子弹和人
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//角色在整个地图中的位置
	public int x;
	public int y;
	//实现流畅移动的偏量位移
	public int mx = 0;
	public int my = 0;
	public boolean up = false;
	public boolean down = false;
	public boolean left = false;
	public boolean right = false;
	//角色的步长
	public int step;
	public int atk;//攻击力
	public boolean alive=false;//是否存活
	public void move(){
		if(up){
			if(ReadMapFile.map2[y/elesize-1][x/elesize]==0) {
			//改变角色在地图中的位置
			y=y-step;
			//改变角色相对固定点的偏量位移
			my=my-step;
			}
			else if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0){
				int y1 = (y/elesize-1)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){  
					//改变角色小于角色的物体在地图中的位置
					y=y-step;
					//改变小于角色相对固定点的偏量位移
					my=my-step;
				}				else if(this.getClass().getName()=="man.Bullet")
				{
					this.alive=false;
				}
			}
		}
		if(down){
			if(ReadMapFile.map2[y/elesize+1][x/elesize]==0){
			y=y+step;
			my=my+step;
			}
			else if(ReadMapFile.map2[y/elesize+1][x/elesize]!=0){
				int y1 = (y/elesize+1)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){
					y=y+step;
					my=my+step;
				}				else if(this.getClass().getName()=="man.Bullet")
				{
					this.alive=false;
				}
			}
			}
		
		if(left){
			if(ReadMapFile.map2[y/elesize][x/elesize-1]==0){
			x=x-step;
			mx=mx-step;
			}
			else if(ReadMapFile.map2[y/elesize][x/elesize-1]!=0) {
				int x1 = (x/elesize-1)*elesize+elesize/2; 
				if((x-x1)*(x-x1)>=elesize*elesize){ 
					x=x-step;
					mx=mx-step;
				}				else if(this.getClass().getName()=="man.Bullet")
				{
					this.alive=false;
				}
			}
		}
		if(right){
			if(ReadMapFile.map2[y/elesize][x/elesize+1]==0){ 
			x=x+step;
			mx=mx+step;
			}
			else if (ReadMapFile.map2[y/elesize][x/elesize+1]!=0){
				int x1 = (x/elesize+1)*elesize+elesize/2; 
				if((x-x1)*(x-x1)>=elesize*elesize){  
					x=x+step;
					mx=mx+step;
				}				else if(this.getClass().getName()=="man.Bullet")
				{
					this.alive=false;
				}
			}
		}
	}
	public Move(int atk,int step) {
		this.atk=atk;
		this.step=step;
	}
	//得到角色在数组中的位置I
	public int getI(){
		return (y-(playersize/2))/50;
	}
	//得到角色在数组中的位置J
	public int getJ(){
		return (x-(playersize/2))/50;
	}
	public void die()//角色死亡
	{
		alive=false;
	}
}
