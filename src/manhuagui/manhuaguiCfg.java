package manhuagui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import Start.LOG;

public class manhuaguiCfg {
	public static String JSFile = "";
	
	static
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(Start.Start.class.getResourceAsStream("/config.js")));
		String line = null;
		
		try {
			try{
				while((line = br.readLine()) != null)
				{
					JSFile = JSFile.concat(line);
				}
			}
			catch(Exception e)
			{
				LOG.log(e.getMessage(), LOG.NormalType);
				e.printStackTrace();
			}
			finally
			{
				br.close();
			}
		} catch (Exception e) {
			LOG.log(e.getMessage(), LOG.NormalType);
			e.printStackTrace();
		}
	}
}
