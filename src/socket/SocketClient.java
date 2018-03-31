package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


public class SocketClient extends Thread{
    // 搭建客户端
	public boolean issend=false;
	Socket theservicesocket;
	public ArrayList<Serializable> theget=new ArrayList<Serializable>();
	public SocketClient(String address,int port)
	{
		// 1、创建客户端Socket，指定服务器地址和端口
		try {
			theservicesocket = new Socket(address,port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("客户端启动成功");
        try {
        	theservicesocket.setTcpNoDelay(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public  void run(){
       while(true)
	       {
	       	theget=get();
	       }
   }
	public ArrayList<Serializable> get()
	{
		ArrayList<Serializable> p=new ArrayList<Serializable>();
        ObjectInputStream ois;
		try {
			ois = new ObjectInputStream((theservicesocket.getInputStream()));
	        try {
				p = (ArrayList<Serializable>)ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
    public void send(Serializable thesend){
        try {
            // 2、获取输出流，向服务器端发送信息
        	ObjectOutputStream re=new ObjectOutputStream((theservicesocket.getOutputStream()));
	   		 re.writeObject(thesend);
	   		 re.flush();
        } catch (Exception e) {
            System.out.println("can not listen to:" + e);// 出错，打印出错信息
        }
        issend=true;
    }

//    public static void main(String[] args)
//    {
//    	ArrayList<Serializable> k=new ArrayList<Serializable>();
//    	k.add(new ComputerPlayer(20,8,6,1,"npc1"));
//    	k.add(new ComputerPlayer(20,8,6,1,"npc2"));
//    	k.add(new ComputerPlayer(20,8,6,1,"npc3"));
//    	SocketClient sc=new SocketClient("127.0.0.1",5209);
//    	sc.start();
//    	Random random = new Random();
//    	while(true)
//    	{
//    	if(random.nextInt(200)<70||k.size()<=1)
//    		{k.add(new ComputerPlayer(20,8,6,1,"npc4"));
//    		}else
//    		{k.remove(0);}
//    	if(sc.theservicesocket != null)
//    		sc.send(new ComputerPlayer(20,8,6,1,"npc1"));
//    	}
//    }
}