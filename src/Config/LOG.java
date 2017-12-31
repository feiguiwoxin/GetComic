package Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LOG {

	public static String logbuff = "";
	public static final int INFO = 1;
	public static final int ERR = 2;
	
	public static void log(String str,int type)
	{
		if(type > 2 || type <1)
		{
			return;
		}
		
		if(type == ERR)
		{
			logbuff.concat(str + "/r/n");
			logintofile();
		}
	}
	
	private static void logintofile()
	{
		File file = new File("." + "Comiclog.txt");
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(logbuff);
			logbuff = "";
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
