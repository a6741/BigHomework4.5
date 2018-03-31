package data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlConnection {
	public void doit(String sql)
	{
        Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        try {
			Statement stmt = (Statement) conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}  
        close(conn);
	}
	public void close(Connection conn)
    {
    	if(conn!=null)  
        {  
            try {  
                conn.close();  
            } catch (SQLException e) {
                e.printStackTrace();  
                conn=null;  
            }  
        }  
    	
    }
	public void buildplayer()
	{
        Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        StringBuffer s=new StringBuffer("create table player(user varchar(9) NOT NULL,password text NOT NULL,gamenumber int,winnumber int,killingnumber int,time long,checkpoint int");
    	String s2=",PRIMARY KEY (user))";
    	File d = new File("man"); 
    	File list[] = d.listFiles();  
    	for (File f : list)
    	{
    		s.append(",pic"+f.getName().replaceAll(".png", "")+" bool DEFAULT false");
    	}
    	s.append(s2);
    	PreparedStatement pstmt = null;
        try {
			pstmt = conn.prepareStatement(s.toString());
		    try {
					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
			e.printStackTrace();
		}
        close(conn);
	}
	public boolean insert(String user,String password) 
	{
        Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}         
        try {  
            Statement stmt = (Statement) conn.createStatement();
            String sql = "select * from player where user='"+user+"'";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            	{System.out.println("已存在");
            	return false;}
            sql = "insert into player(user,password ,gamenumber,winnumber,killingnumber,time,checkpoint) values('"+user+"','"+password+"',"+0+","+0+","+0+","+0+","+0+")"; //插入数据
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();  
        }
        close(conn);
        return true;
	}
	public int gamenumber(String user) 
    {
    	int number=-1;
        Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}    
    	try {  
            Statement stmt = (Statement) conn.createStatement();
            String sql = "select gamenumber from player where user='"+user+"'";
            doit(sql);
            ResultSet rs = stmt.executeQuery(sql);
               while (rs.next()) {
                    number=rs.getInt(1);
                  }
        } catch (SQLException e) {
            e.printStackTrace();  
        }
        close(conn);
    	return number;
    }
	
	 public int winnumber(String user)
	    {
	    	int number=-1;
	        Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}    
	    	try {  
	            Statement stmt = (Statement) conn.createStatement();
	            String sql = "select winnumber from player where user='"+user+"'";
	            ResultSet rs = stmt.executeQuery(sql);
	               while (rs.next()) {
	                    number=rs.getInt(1);
	                  }

	        } catch (SQLException e) {
	            e.printStackTrace();  
	        }
	        close(conn);
	    	return number;
	    }
	 
	 public int killingnumber(String user)
	    {
	    	int number=-1;
	        Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}    ;
	    	try {
	            Statement stmt = (Statement) conn.createStatement();
	            String sql = "select killingnumber from player where user='"+user+"'";
	            ResultSet rs = stmt.executeQuery(sql);
	               while (rs.next()) {
	                    number=rs.getInt(1);
	                  }
	        } catch (SQLException e) {
	            e.printStackTrace();  
	        }
	        close(conn);
	    	return number;
	    	
	    }
	 
	 public long time(String user)
	    {
	    	long time=-1;
	        Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}    
	    	try {
	           Statement stmt = (Statement) conn.createStatement();
	           String sql = "select time from player where user='"+user+"'";
	           ResultSet rs = stmt.executeQuery(sql);
               while (rs.next()) {
                    time=rs.getLong(1);
                  }
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }
	        close(conn);
	    	return time;
	    	
	    }
	 
	 public int chenkpoint(String user)
	    {
	    	int point=-1;
	        Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}      
	    	try { 
	            Statement stmt = (Statement) conn.createStatement();
	            String sql = "select checkpoint from player where user='"+user+"'";
	            ResultSet rs = stmt.executeQuery(sql);
	               while (rs.next()) {
	                    point=rs.getInt(1);
	                  }
	        } catch (SQLException e) { 
	            e.printStackTrace();  
	        }
	        close(conn);
	    	return point;
	    	
	    }
	 
	 //验证密码
	    public boolean check(String user,String password)
	    {
	    	boolean check=false;
	        Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}      
	    	try { 
	            Statement stmt = (Statement) conn.createStatement();
	            String sql = "select password from player where user='"+user+"'";
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) {
	            	if(password.equals(rs.getString(1)))
	            	{
	            		check=true;
	            	}
               }

	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }
	        close(conn);
	    	return check;
	    }
	  //修改局数+1
	    public void Re_gamenumber(String user)
	    {
        	String sql= "update player set gamenumber=gamenumber+1 where user='"+user+"'";
            doit(sql);
	    }
	    //修改闯关数
	    public void Re_checkpoint(String user,int num)
	    { 
        	String sql= "update player set checkpoint="+num+" where user='"+user+"' and checkpoint<"+num;
            doit(sql);
	    }
	    
	    //修改获胜局数+1
	    public void Re_winnumber(String user)
	    {
	        String sql= "update player set winnumber=winnumber+1 where user='"+user+"'";
	        doit(sql);
	    }    
	  //修改击杀数+a
	    public void Re_killingnumber(String user,int a)
	    {
        	String sql= "update player set killingnumber=killingnumber+"+a+" where user='"+user+"'";
            doit(sql);
	    }
	    
	    //修改时间+a
	    public void Re_time(String user,long a)
	    {
        	String sql = "update player set time=time+"+a+" where user='"+user+"'";
            doit(sql);
	    }
	    //获得图鉴
	    public void getone(String s,String user)
	    {
	    	String sql= "update player set pic"+s+"=1 where user='"+user+"'";
            doit(sql);
	    }
	    public void deletealll()
	    {
	    	Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}      
	    	try {             
	            Statement stmt = (Statement) conn.createStatement();
	            String sql = "delete from player";
	            stmt.executeUpdate(sql);
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }
	        close(conn);	
	    }
	    public void droptable()
	    {
	    	Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}      
	    	try {              
	            Statement stmt = (Statement) conn.createStatement();
	            String sql = "drop table player";
	            stmt.executeUpdate(sql);
	        } catch (SQLException e) {    
	            e.printStackTrace();  
	        }
	        close(conn);
	    }
	    public ArrayList<String> thepic(String user) 
	    {
	    	ArrayList<String> nlist=new ArrayList<String>();
	    	boolean number=false;
	    	Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}    
	    	try {  
	        	String sql;
	            Statement stmt = (Statement) conn.createStatement();
		    	File d = new File("man"); 
		    	File list[] = d.listFiles();  
		    	for (File f : list)
		    	{
	            sql = "select pic"+f.getName().replaceAll(".png", "")+" from player where user='"+user+"'";
	            ResultSet rs = stmt.executeQuery(sql);
	               while (rs.next()) {
	                    number=rs.getBoolean(1);
	                 }
	               if(number)
	            	   nlist.add(f.getName());
		    	}
	        } catch (SQLException e) {
	            e.printStackTrace();  
	        } 
	        close(conn);
	    	return nlist;
	    }
}
