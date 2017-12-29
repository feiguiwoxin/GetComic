package GetComic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

//工具函数，用于获取网页的源代码，注意，只能获取到动态JS执行之前的网页源代码
public class GetHtml {
	
	public static String GetInfo(String UrlAdd)
	{
		String HttpInfo = null;
		if(null == UrlAdd) return null;
		String line;
		URL url;
		
		try {
			url = new URL(UrlAdd);
			URLConnection urlcon = url.openConnection();
			urlcon.setRequestProperty("accept", "*/*");
			urlcon.setRequestProperty("connection", "Keep-Alive");
			urlcon.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0");
			urlcon.connect();
			BufferedReader urlRead = new BufferedReader(new InputStreamReader(urlcon.getInputStream(), "utf-8"));
			while((line = urlRead.readLine()) != null)
			{
				HttpInfo += line;
			}
			urlRead.close();
		} catch (Exception e) {
			return null;
		}
		return HttpInfo;
	}
}
