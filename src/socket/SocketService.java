package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class SocketService extends Thread {
    //搭建服务器端
	 ServerSocket server=null;
	 //定义一个服务器socket
	public Serializable theget;
	 Socket socket=null;
     public  void run(){
        startServer();
        while(true)
        {
        	theget=get();
        }
    }  
    public void startServer()
    {
         try{
             server=new ServerSocket(5209);
             //b)指定绑定的端口，并监听此端口。
             System.out.println("服务器启动成功");
             //创建一个ServerSocket在端口5209监听客户请求
         }catch(Exception e) {
                 System.out.println("没有启动监听："+e);
                 //出错，打印出错信息
         }
         try{
             socket=server.accept();
             
         }catch(Exception e) {
             System.out.println("Error."+e);
         }
         try {
			socket.setTcpNoDelay(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
    }
    public synchronized void Seta(ArrayList<Serializable> aa)
    {
      try {
    	 ObjectOutputStream re=new ObjectOutputStream((socket.getOutputStream()));
		 re.writeObject(aa);
		 re.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	public Serializable get()
	{
		Serializable p=null;
        ObjectInputStream ois;
		try {
			ois = new ObjectInputStream((socket.getInputStream()));
	        try {
				p = (Serializable)ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
//    public static void main(String[] args) 
//    {
//    	SocketService s=new SocketService();
//    	
//    	ArrayList<Serializable> k=new ArrayList<Serializable>();
//    	k.add(new ComputerPlayer(20,8,6,1,"npc1"));
//    	k.add(new ComputerPlayer(20,8,6,1,"npc2"));
//    	k.add(new ComputerPlayer(20,8,6,1,"npc3"));
//    	
//    	s.start();
//
//    	Random random = new Random();
//
//    	while(true)
//    	{
//    	if(random.nextInt(200)<70||k.size()<=1)
//    		{k.add(new ComputerPlayer(20,8,6,1,"npc4"));
//    		}else
//    		{k.remove(0);}
//    	if(s.socket != null)
//    		{s.Seta(k);
//    		}
//    	}
//    }
}