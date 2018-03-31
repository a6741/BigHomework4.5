package others;

/**  
 * 计时器  
 */    
public class Timer extends Thread {    
     
    /** 
     *  
     */   
    private long time;
    public boolean stopped=false;
    public void run() 
    {
    	// 记录程序开始时间
        long programStart = System.currentTimeMillis();   
        while (true) {    
            if (!stopped) {    
                long elapsed = System.currentTimeMillis() - programStart;    
                time=elapsed;    
            }    
 
            try {    
                sleep(1);  // 1毫秒更新一次显示  
            } catch (InterruptedException e) {    
                e.printStackTrace();    
                System.exit(1);    
            }    
        }    
    }
    public long whattime()
    {
    	return time;
    }
 
    // 将毫秒数格式化    
    public static String format(long elapsed) 
    {    
        int minute, second;    
        elapsed=elapsed / 1000;
        
        second = (int) (elapsed % 60);    
        elapsed = elapsed / 60;    
 
        minute = (int) (elapsed % 60);    
        elapsed = elapsed / 60;    
 
        return String.format("%02d:%02d",minute, second);    
    }    
        
}    