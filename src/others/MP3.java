package others;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class MP3 extends Thread{
	public String filename;
	public Player player;
	public boolean isloop=true;
    public MP3(String filename) {
        this.filename = filename;
        
    }

    public void run() {
    	do
    	{
        try {
            BufferedInputStream buffer = new BufferedInputStream(
                    new FileInputStream(filename));
            player = new Player(buffer);
            player.play();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    	}while(isloop);
    }
    public void close() {
    		isloop=false;
            player.close();
    }
}

