package map;

import java.io.DataInputStream;
import java.io.FileInputStream;
/**
 * 读入地图文件
 * @author 
 *
 */
public class ReadMapFile {
	//读入地图，map1地板；map2人物与障碍物；map3随着时间流失扩大的岩浆范围
	public static int[][] map1;
	public static int[][] map2;
	public static int[][] map3;
	public static String pathis;
	/**
	 * 地图读入
	 * 
	 */
	public static void readfile(String path){
		try{
			pathis=path;
			//从指定的路径path读入地图，以后可以换地图
			FileInputStream fis = new FileInputStream(path);
			//翻译地图，将文件输入数据流包装成基本数据流
			DataInputStream dis = new DataInputStream(fis);
			//读取三个地图数组
			int i = dis.readInt();
			int j = dis.readInt();
			map1 = new int[i][j];
			map2 = new int[i][j];
			map3 = new int[i][j];
			for(int ii=0;ii<i;ii++){
				for(int jj=0;jj<j;jj++){
					map1[ii][jj] = dis.readInt();
					map2[ii][jj] = dis.readInt();
					map3[ii][jj] = dis.readInt();
				}
			}
			dis.close();
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
