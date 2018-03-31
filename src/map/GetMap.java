package map;

import javax.swing.ImageIcon;

public class GetMap implements gameConfig{
	//数字匹配地图
		static ImageIcon int2icon(int num){
			if(num==000){
				if(ReadMapFile.pathis.equals("map/SnowWorld.map"))
					{
					return icon700;				
					}
				else if(ReadMapFile.pathis.equals("map/GhostForest.map"))
					{
					return icon003;				
					}
				else if(ReadMapFile.pathis.equals("map/DeathDesert.map"))
				{
				return icon900;				
				}
				else
					return icon0;
			}//else if(num==1){
				//return icon1;
			//}
		else if(num==002){
				return icon002;
			}
		else if(num==003){
				return icon003;
			}
		else if(num==8)
			{
				return icon008;
			}
		else if(num==100){
				return icon100;
			}
		else if(num==101){
				return icon101;
			}
		else if(num==102){
				return icon102;
			}
		else if(num==300){
				return icon300;
			}
		else if(num==200){
				return icon200;
			}
		else if(num==302){
				return icon302;
			}
		else if(num==301){
				return icon301;
			}
		else if(num==700){
			return icon700;
		}
		else if(num==701){
			return icon701;
		}
		else if(num==702){
			return icon702;
		}
		else if(num==703){
			return icon703;
		}
		else if(num==704){
			return icon704;
		}
		else if(num==705){
			return icon705;
		}
		else if(num==706){
			return icon706;
		}
		else if(num==707){
			return icon707;
		}
		else if(num==800){
			return icon800;
		}
		else if(num==801){
			return icon801;
		}
		else if(num==802){
			return icon802;
		}
		else if(num==803){
			return icon803;
		}
		else if(num==804){
			return icon804;
		}
		else if(num==805){
			return icon805;
		}
		else if(num==806){
			return icon806;
		}
		else if(num==807){
			return icon807;
		}
		else if(num==808){
			return icon808;
		}
		else if(num==901){
			return icon901;
		}
		else if(num==902){
			return icon902;
		}
		else if(num==903){
			return icon903;
		}
		else if(num==904){
			return icon904;
		}
		else if(num==905){
			return icon905;
		}
			return null;
		}
}
