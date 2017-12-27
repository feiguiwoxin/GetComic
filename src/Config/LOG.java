package Config;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

public class LOG {
	private static String LOGGER = new Date().toString() + "\r\n";
	
	public static boolean LogIntoFile(String FilePath)
	{
		File file = new File(FilePath);
		if(file.exists()) file.delete();
		
		try {
			FileWriter fr = new FileWriter(file);
			fr.write(LOGGER);
			fr.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void log(String Info)
	{
		LOGGER = LOGGER.concat(Info + "\r\n");
	}
}
