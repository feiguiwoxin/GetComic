package GetComic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class GetPicture {
	private String HtmlInfo = null;
	private String ConfigJS = null;
	private String ParkerJS = null;
	
	public GetPicture(String UrlAdd)
	{
		this.HtmlInfo = GetHtml.GetInfo(UrlAdd);
		System.out.println(HtmlInfo);
		GetJsInfo();
	}
	
	private void GetJsInfo()
	{
		if(null == HtmlInfo) return;
		String configMath = "<script type=\"text/javascript\" src=\"http://c.3qfm.com/scripts/config_(.+?).js\"></script>";
		String parkerMatch = "\\(function\\(p,a,c,k,e,d\\)(.+?)</script>";
		
		Pattern pattern = Pattern.compile(configMath);
		Matcher match = pattern.matcher(HtmlInfo);
		
		while(match.find())
		{
			ConfigJS = match.group(0);
			System.out.println(ConfigJS);
		}
		
		pattern = Pattern.compile(parkerMatch);
		match = pattern.matcher(HtmlInfo);
		
		while(match.find())
		{
			ParkerJS = "<script type=\"text/javascript\">eval" + match.group(0);
			System.out.println(ParkerJS);
		}
		
		return;
	}
	
}
