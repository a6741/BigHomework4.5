package map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import gui.Gui;
import man.Bullet;
import man.ComputerPlayer;
import man.Person;
import man.Player;
import others.Extension;
import others.MP3;
import others.Timer;
import resources.A_Bullet;
import resources.Hp;
import resources.ProClothing;
import resources.Resource;
import resources.Shoes;
import resources.Weapon;
import resources.Weapon1;
import resources.Weapon2;
import socket.SocketClient;
import socket.SocketService;
import socket.Socketsolver;
/**
 * 地图主要窗体
 * @author 
 *
 */
public class MainFrame extends JFrame implements gameConfig{
	/**
	 * 
	 */
	private boolean lock=false;
	private static final long serialVersionUID = 1L;
	//游戏面板
	boolean shift;
	public static boolean two=true;
	public static boolean shadow=false;
	public static boolean issign=false;
	MP3 m=new MP3("BGM2.mp3");
	Extension p11=new Extension(1,1,0,0,0,0,ReadMapFile.map1.length*elesize,ReadMapFile.map1[0].length*elesize);
	public static boolean service;//在局域网联机时判断本程序是否为服务器
	public static boolean LAN;//判断是否联机
	public static boolean breakthrough=false;
	public static int timeup=0;
	public static int chapter=0;
	public static boolean music,bgm=true;
	JPanel panel;
	Timer time=new Timer();
	Player player2 = new Player(300,8,6,1,Gui.user+" 2",ReadMapFile.map1.length,ReadMapFile.map1[0].length);
	Player player = new Player(300,8,6,1,Gui.user,ReadMapFile.map1.length,ReadMapFile.map1[0].length);
	SocketService ss=new SocketService();
	ArrayList<Person> peList=new ArrayList<Person>();
	ArrayList<Resource> reList=new ArrayList<Resource>();
	SocketClient sock=null;
	Gui gui;
	public static String ip;
	public MainFrame(Gui gui) {
		init();
		this.gui=gui;

	}
	/**
	 * 设置窗体
	 */
	public void init(){
		this.setTitle(title);
		this.setResizable(false);
		this.setSize(frameX, frameY);
		setLocationRelativeTo(null);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(2);
		//创建游戏面板
		WindowListener wl=new WindowListener();
		this.addWindowListener(wl);
		if(bgm)
			m.start();
		panel = setpanel();
		
		this.add(panel);
		this.setVisible(true);
		//键盘监视器
		PanelListenner plis = new PanelListenner();
		this.addKeyListener(plis);
		//启动客户端/服务器
		if(LAN)
		{
			if(service)
			{
				//ss.startServer();
				ss.start();
			}
			else if(!service)
			{
			sock=new SocketClient(ip,5209);//在此处输入服务器ip及端口
			sock.start();
			}
		}
		if((!LAN)&&!breakthrough)
		{
			if(two)
			{
			for(int npcnum=0;npcnum<7;npcnum++)
			{
				peList.add(new ComputerPlayer(200,8,6,1,"npc"+(npcnum+1)));
			}
			}
			else if(timeup==0)
			{
			for(int npcnum=0;npcnum<4;npcnum++)
			{
				peList.add(new ComputerPlayer(200,8,6,1,"npc"+(npcnum+1)));
			}
			}
			else
			{
				for(int npcnum=0;npcnum<4;npcnum++)
				{
					peList.add(new ComputerPlayer(500,10,10,2,"npc"+(npcnum+1)));
				}
			}
			for(Person cper:peList)
			{
				cper.start();
			}
		}
		if(breakthrough)
		{

			for(;chapter<4;chapter++)
			{
				peList.add(new ComputerPlayer(20,4,4,1,"npc"+(chapter+1)));
			}
			for(Person cper:peList)
			{
				cper.start();
			}
		}
		//启动人物线程
		player.start();
		peList.add(player);
		if(two)
		{
			player2.x=player.x;
			player2.mx=player.mx;
			player2.y=player.y;
			player2.my=player.my;
			player2.start();
			peList.add(player2);
		}
		//启动资源线程
		for(int renum=0;renum<4;renum++)
		{
			reList.add(new Hp());
			reList.add(new ProClothing());
			reList.add(new A_Bullet());
		}
		for(int renum=0;renum<2;renum++)
		{
			reList.add(new Weapon());
			reList.add(new Weapon1());
			reList.add(new Weapon2());
			reList.add(new Shoes());
		}
		for(Resource  re:reList)
		{
			re.start();
		}
		//启动新面板线程
		UpdateThread ut = new UpdateThread(panel);
		ut.start();
		time.start();
	}
	
	/**
	 * 设置游戏面板
	 */
	public JPanel setpanel(){
		JPanel panel = new MyPanel();
		panel.setPreferredSize(new Dimension(panelX, panelY));
		panel.setLayout(null);
		panel.setBackground(Color.black);
		
		return panel;
	}
	/**
	 * 安装监视器类
	 * @author 
	 *
	 */
	 
	
	class PanelListenner extends KeyAdapter{
		//当按下键盘
		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();
			Player p1,p2;
			if(!shift)
			{
				p1=player;
				p2=player2;
			}
			else
			{
				p2=player;
				p1=player2;
			}
			switch (code) {
			case KeyEvent.VK_UP:
				if(!p1.ismove())
				{
					p1.nofacearound();//如果按方向键之前是静止，则先将原本面朝方向置为false
				}
				p1.up = true;
				p1.facetoup=true;
				break;
			case KeyEvent.VK_DOWN:
				if(!p1.ismove())
				{
					p1.nofacearound();
				}
				p1.down = true;
				p1.facetodown=true;
				break;
			case KeyEvent.VK_LEFT:
				if(!p1.ismove())
				{
					p1.nofacearound();
				}
				p1.left = true;
				p1.facetoleft=true;
				break;
			case KeyEvent.VK_RIGHT:
				if(!p1.ismove())
				{
					p1.nofacearound();
				}
				p1.right = true;
				p1.facetoright=true;
				break;
			case KeyEvent.VK_SPACE://
				ArrayList<Bullet> tempList =p1.attack();
				p1.buList.addAll(tempList);
				if(tempList.size()>0&&music)
				{MP3 ss=new MP3("shot.mp3");
				ss.isloop=false;
				ss.start();}
				break;
				////
			case KeyEvent.VK_W:
				if(!p2.ismove())
				{
					p2.nofacearound();//如果按方向键之前是静止，则先将原本面朝方向置为false
				}
				p2.up = true;
				p2.facetoup=true;
				break;
			case KeyEvent.VK_S:
				if(!p2.ismove())
				{
					p2.nofacearound();
				}
				p2.down = true;
				p2.facetodown=true;
				break;
			case KeyEvent.VK_A:
				if(!p2.ismove())
				{
					p2.nofacearound();
				}
				p2.left = true;
				p2.facetoleft=true;
				break;
			case KeyEvent.VK_D:
				if(!p2.ismove())
				{
					p2.nofacearound();
				}
				p2.right = true;
				p2.facetoright=true;
				break;
			case KeyEvent.VK_Q://
				tempList =p2.attack();
				p2.buList.addAll(tempList);
				if(tempList.size()>0&&music)
				{MP3 ss=new MP3("shot.mp3");
				ss.isloop=false;
				ss.start();}
				break;
			case KeyEvent.VK_SHIFT://
				if(two)
				{
				Player tp;
				tp=player;
				player=player2;
				player2=tp;
				if(shift)
					shift=false;
				else
					shift=true;}
				break;
			default:
				break;
			}
		}
		//松开键盘
		public void keyReleased(KeyEvent e){
			int code = e.getKeyCode();
			Player p1,p2;
			if(!shift)
			{
				p1=player;
				p2=player2;
			}
			else
			{
				p2=player;
				p1=player2;
			}
			switch (code) {
			case KeyEvent.VK_UP:
				p1.up = false;
				if(!p1.ismove())
				{
					p1.nofacearound();//如果释放按键后是静止，则面朝方向为释放的方向键
					p1.facetoup=true;
				}
				else
				{
					p1.facetoup=false;//如果不是静止，则释放的方向键朝向方向置为false
				}
				break;
			case KeyEvent.VK_DOWN:
				p1.down = false;
				if(!p1.ismove())
				{
					p1.nofacearound();
					p1.facetodown=true;
				}
				else
				{
					p1.facetodown=false;
				}
				break;
			case KeyEvent.VK_LEFT:
				p1.left = false;
				if(!p1.ismove())
				{
					p1.nofacearound();
					p1.facetoleft=true;
				}
				else
				{
					p1.facetoleft=false;
				}
				break;
			case KeyEvent.VK_RIGHT:
				p1.right = false;
				if(!p1.ismove())
				{
					p1.nofacearound();
					p1.facetoright=true;
				}
				else
				{
					p1.facetoright=false;
				}
				break;
			////
			case KeyEvent.VK_W:
				p2.up = false;
				if(!p2.ismove())
				{
					p2.nofacearound();//如果释放按键后是静止，则面朝方向为释放的方向键
					p2.facetoup=true;
				}
				else
				{
					p2.facetoup=false;//如果不是静止，则释放的方向键朝向方向置为false
				}
				break;
			case KeyEvent.VK_S:
				p2.down = false;
				if(!p2.ismove())
				{
					p2.nofacearound();
					p2.facetodown=true;
				}
				else
				{
					p2.facetodown=false;
				}
				break;
			case KeyEvent.VK_A:
				p2.left = false;
				if(!p2.ismove())
				{
					p2.nofacearound();
					p2.facetoleft=true;
				}
				else
				{
					p2.facetoleft=false;
				}
				break;
			case KeyEvent.VK_D:
				p2.right = false;
				if(!p2.ismove())
				{
					p2.nofacearound();
					p2.facetoright=true;
				}
				else
				{
					p2.facetoright=false;
				}
				break;
			default:
				break;
			}
		}
	}
	class WindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		   {
			if(!player.end)
				{Gui.sql.Re_gamenumber(Gui.user);
				Gui.sql.Re_time(Gui.user,time.whattime());
				Gui.sql.Re_killingnumber(Gui.user, player.killnumber);
				if(MainFrame.breakthrough)
				{
					Gui.sql.Re_checkpoint(Gui.user, MainFrame.chapter);
				}
				}
			if(m.isAlive())
				m.close();
		    setVisible(false);
		    gui.dispose();
		    Gui gui=new Gui();
			gui.setTitle("凌云杀神");
			gui.setLocationRelativeTo(null);
			gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gui.setResizable(false);
			gui.setVisible(true);
		   }
	}
	class MyPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ArrayList<String> info=player.getinfomation();
			g.setFont(new Font("Tahoma", Font.BOLD, 12));
			//人物脚下与同一层次的地图
			for(int i=player.getI()-8;i<=player.getI()+8;i++){
				for(int j=player.getJ()-9;j<=player.getJ()+9;j++){
					
					if(i>=0&&j>=0&&i<ReadMapFile.map1.length&&j<ReadMapFile.map1[0].length){
						
						ImageIcon icon1 = GetMap.int2icon(ReadMapFile.map1[i][j]);
						g.drawImage(icon1.getImage(), (player.px-elesize/2)+((j-player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+((i-player.getI())*elesize)-(player.my%elesize), elesize, elesize, null);
						if(ReadMapFile.map2[i][j]!=0) 
						{
						ImageIcon icon2 = GetMap.int2icon(ReadMapFile.map2[i][j]);
						g.drawImage(icon2.getImage(), (player.px-elesize/2)+((j-player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+((i-player.getI())*elesize)-(player.my%elesize), elesize, elesize, null);
						}
						//player.px-elesize/2为主角在屏幕上开始画的坐标
						//(j-player.getJ())*elesize为主角与该地图块之间的水平距离
						//player.mx%elesize代表主角相对与脚下地图块的x起始点的差值，即主角离脚下地图块左上角的水平距离
					}
					else
					{
						g.drawImage(icon008.getImage(), (player.px-elesize/2)+((j-player.getJ())*elesize)-(player.mx%elesize), (player.py-elesize/2)+((i-player.getI())*elesize)-(player.my%elesize), elesize, elesize, null);	
					}
				}
			}
			ArrayList<Object> s=Socketsolver.socketsolver(LAN,service,peList,reList,ss,sock,p11,player);
			peList=(ArrayList<Person>) s.get(0);
			reList=(ArrayList<Resource>) s.get(1);
			Extension p12=(Extension) s.get(2);
			p11.width=p12.width;
			p11.height=p12.height;
			for(Resource r:reList)
			{
				if(r.isAlive)
				{
					r.isget(peList,g,time,player);
					if(r.isAlive)
						r.draw(g,player);
				}
			}
			player=(Player) peList.get(peList.size()-1);
			peList.remove(peList.size()-1);
			peList=Drawperson.drawperson(peList,g,player,LAN,service,ss,p11,reList,time);
			if(!breakthrough)
			{
			p11.draw(g, player);
			if((p11.ptime+=1)%p11.pfrequency==0)
				{p11.Extensions();
				p11.isinpoison(peList);}
			}
			g.drawImage(infoico.getImage(),panelX-infoX,0,infoX,infoY,null);
			int loc=50;
			if(timeup==0)
				g.drawString(Timer.format(time.whattime()), 30, 20);
			else
			{
				long lasttime=timeup-time.whattime();
				if(lasttime>0)
					g.drawString(Timer.format(lasttime), 30, 20);
				else
					{peList.clear();
					peList.add(player);
					g.drawString(Timer.format(0), 30, 20);
					}
			}
			if(breakthrough&&peList.size()<5)
			{
				ComputerPlayer cper=new ComputerPlayer(20+chapter*10,(int)(4+chapter*0.2),(int)(4+chapter*0.2),(int)(1+chapter*0.1),"npc"+(chapter+1));
				peList.add(cper);
				cper.start();
				chapter+=1;
			}
			if(breakthrough)
			{
			g.drawString("the chapter of "+chapter,panelX-infoX-120,20);
			}
			g.setColor(infocolor);
			for(String ts:info)
			{
				g.drawString(ts, panelX-infoX+30, loc);
				loc+=30;
			}
			player.judge(peList, g,time);
		}
	}
}
