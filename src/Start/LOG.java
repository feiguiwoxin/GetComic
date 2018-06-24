package Start;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.swing.JOptionPane;

public class LOG {

	private static StringBuffer logbuff = new StringBuffer();
	private static int lognum = 0;
	
	static
	{
		loginDate();
	}
	
	public static synchronized void log(String str)
	{			
		lognum ++;
		logbuff = logbuff.append(String.format("%tT:%s\r\n", new Date(), str));
		
		if(lognum > 20 || str.length() > 1024)
		{
			logintofile();
		}
	}
	
	private static void loginDate()
	{
		logbuff = logbuff.append(String.format("%1$tF %1$tT:START!!!\r\n", new Date()));
		logintofile();
	}
	
	public static synchronized void logintofile()
	{
		if(logbuff.length() <= 0) return;
		File file = new File("./Comiclog.txt");
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(logbuff.toString());
			logbuff.setLength(0);;
			lognum = 0;
			fw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "写入日志错误，请关闭日志文件", "错误", JOptionPane.CLOSED_OPTION);
			return;
		}
	}
}
