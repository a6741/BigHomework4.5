package map;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.ImageIcon;

public interface gameConfig extends Serializable{

	String title = "凌云杀神";
	//游戏主题窗口大小
	int frameX = 800;
	int frameY = 700;
	//游戏面板大小
	int panelX = 750;
	int panelY = 650;
	//信息面板大小
	int infoX = 150;
	int infoY = 220;
	//头像边长
	int headr = 50;
	//游戏素材大小（正方形）
	int elesize = 50;
	//人物大小（一格一格移动）
	int playersize = 50;
	Color bulletcolor=Color.YELLOW;
	Color bulletcolor2=Color.RED;
	Color infocolor=Color.BLACK;
	Color strcolor=Color.WHITE;
	ImageIcon infoico = new ImageIcon("框.png");
	//------------[游戏素材]----------
	ImageIcon icon0= new ImageIcon("mapresource/000草地.png");
	ImageIcon icon002 = new ImageIcon("mapresource/002普通地砖.png");
	ImageIcon icon003 = new ImageIcon("mapresource/003沼泽地板.png");
	ImageIcon icon008 = new ImageIcon("mapresource/sea.png");
	ImageIcon icon100 = new ImageIcon("mapresource/100红树.png");
	ImageIcon icon101 = new ImageIcon("mapresource/101绿树.png");
	ImageIcon icon102 = new ImageIcon("mapresource/102绿竹.png");
	ImageIcon icon301 = new ImageIcon("mapresource/301黑迷雾.png");
	ImageIcon icon300 = new ImageIcon("mapresource/300树木.png");
	ImageIcon icon200 = new ImageIcon("mapresource/200草丛.png");
	ImageIcon icon302 = new ImageIcon("mapresource/302灰迷雾.png");
	ImageIcon icon700 = new ImageIcon("mapresource/700雪地.png");
	ImageIcon icon701 = new ImageIcon("mapresource/701树.png");
	ImageIcon icon703 = new ImageIcon("mapresource/703树.png");
	ImageIcon icon702 = new ImageIcon("mapresource/702树.png");
	ImageIcon icon704 = new ImageIcon("mapresource/704装饰.png");
	ImageIcon icon705 = new ImageIcon("mapresource/705装饰.png");
	ImageIcon icon706 = new ImageIcon("mapresource/706铃铛.png");
	ImageIcon icon707 = new ImageIcon("mapresource/707装饰.png");
	ImageIcon icon800 = new ImageIcon("mapresource/800鬼.png");
	ImageIcon icon801 = new ImageIcon("mapresource/801鬼屋.png");
	ImageIcon icon802 = new ImageIcon("mapresource/802树.png");
	ImageIcon icon803 = new ImageIcon("mapresource/803糖果.png");
	ImageIcon icon804 = new ImageIcon("mapresource/804糖果.png");
	ImageIcon icon805 = new ImageIcon("mapresource/805南瓜.png");
	ImageIcon icon806 = new ImageIcon("mapresource/806南瓜.png");
	ImageIcon icon807 = new ImageIcon("mapresource/807南瓜.png");
	ImageIcon icon808 = new ImageIcon("mapresource/808南瓜.png");
	ImageIcon icon900 = new ImageIcon("mapresource/900沙.png");
	ImageIcon icon901 = new ImageIcon("mapresource/901木头.png");
	ImageIcon icon902 = new ImageIcon("mapresource/902鼓.png");
	ImageIcon icon903 = new ImageIcon("mapresource/903废墟.png");
	ImageIcon icon904 = new ImageIcon("mapresource/904石头.png");
	ImageIcon icon905 = new ImageIcon("mapresource/905树.png");
	//限制视角的镜头
	ImageIcon shadow = new ImageIcon("镜头阴影2.png");
}
