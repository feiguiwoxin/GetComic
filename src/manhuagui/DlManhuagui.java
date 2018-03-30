package manhuagui;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JOptionPane;

import Config.LOG;
import Config.ValidConfig;
import GetComic.Chapter;
import GetComic.ComicInfo;

public class DlManhuagui extends ComicInfo{
	private StringBuilder UrlAddr = null;
	private String ComicNum = null;
	
	public DlManhuagui(String ComicNum)
	{
		this.UrlAddr = new StringBuilder("http://www.manhuagui.com/comic/").append(ComicNum)
									.append("/?").append((new Date()).getTime());
		this.ComicNum = ComicNum;
	}

	@Override
	public ArrayList<Chapter> GetChapetr() {
		String HtmlInfo = this.GetHtmlInfo(UrlAddr.toString());
		if(null == HtmlInfo) return null;
		
		String Title = null;
		String Html = null;
		ArrayList<Chapter> Chapters = new ArrayList<Chapter>();
		
		String BookNameRex = "name: '(.+?)'";
		Pattern pattern = Pattern.compile(BookNameRex);
		Matcher matcher = pattern.matcher(HtmlInfo);
		
		while(matcher.find())
		{
			BookName = matcher.group(1);
		}
		
		//漫画柜在一部分网站上对于章节信息也采用了base64压缩，需要先进行解压缩
		if(HtmlInfo.indexOf("__VIEWSTATE") != -1)
		{
			HtmlInfo = adjustHtmlInfo(HtmlInfo);
		}
		
		//使用正则表达式进行匹配
		String Rex = "<li><a href=\"/comic/" + ComicNum + "/(.+?)\" title=\"(.+?)\"";
		pattern = Pattern.compile(Rex);
		matcher = pattern.matcher(HtmlInfo);
		while(matcher.find())
		{
			Title = matcher.group(2);
			Html = "http://www.manhuagui.com/comic/" + ComicNum + "/" + matcher.group(1);
			Chapters.add(new Chapter(Title, Html));
		}
		
		return Chapters;
	}
	
	private String adjustHtmlInfo(String HtmlInfo)
	{
		String Rex = "VIEWSTATE\" value=\"(.+?)\"";
		String keyword = null;
		String tmpHtmlInfo = null;
		Pattern pattern = Pattern.compile(Rex);
		Matcher matcher = pattern.matcher(HtmlInfo);
		
		while(matcher.find())
		{
			keyword = matcher.group(1);
		}
		
		ScriptEngineManager enginemanager = new ScriptEngineManager();
		ScriptEngine engine = enginemanager.getEngineByName("js");
		try {
			engine.eval(ValidConfig.JSFile);
			Invocable inv = (Invocable)engine;
			tmpHtmlInfo = (String)inv.invokeFunction("getChapter", keyword);			
		} catch (Exception e) {
			LOG.log(e.getMessage(), LOG.NormalType);
			e.printStackTrace();
		}
		
		if(tmpHtmlInfo.length() > 0)
		{
			JOptionPane.showMessageDialog(null, "当前漫画为网站标定为版权受限的漫画，请不要下载过多，可能被封IP,详细见下面的说明", "提示", JOptionPane.CLOSED_OPTION);
			HtmlInfo = tmpHtmlInfo;
		}
		
		return HtmlInfo;
	}

	@Override
	public ArrayList<String> GetPicUrlByChapter(String ChapterUrl) {
		String HtmlInfo = this.GetHtmlInfo(ChapterUrl);
		if(null == HtmlInfo) return null;
		
		String PicInfo = GetJsInfo(HtmlInfo);
		if(null == PicInfo) return null;
		
		ArrayList<String> PicturePath = new ArrayList<String>();	
		String path = null;
		String[] files = null;
		String cid = null;
		String md5 = null;
		String MatchString = "cid\":(.+?),\"cname(.+?)files\":\\[(.+?)\\],(.+?)path\":\"(.+?)\"(.+?)md5\":\"(.+?)\"";
		Pattern pattern = Pattern.compile(MatchString);
		Matcher match = pattern.matcher(PicInfo);
		
		while(match.find())
		{
			cid = match.group(1);
			path = match.group(5);
			files = match.group(3).replaceAll("\"","").replaceAll(".webp", "").split(",");
			md5 = match.group(7);
		}
		for(String file:files)
		{
			PicturePath.add("http://i.hamreus.com" + path + file + "?cid="+ cid + "&md5=" + md5);
		}
		
		return PicturePath;
	}
	
	/*从网页内容中获取我们需要用于解码的JS，这里漫画柜使用了2层压缩：
	 * 1.eval函数本身执行的就是一个parker压缩;
	 * 2.对于parker压缩的第四个入参也就是k,需要先进行base64解压缩;
	 * 这个函数的目的就是为了获取解码需要的相关参数*/
	private String GetJsInfo(String HtmlInfo)
	{
		if(null == HtmlInfo) return null;
		
		String Para1 = null,Para2 = null,Para3 = null,Para4 = null,Para5 = null;
		String Base64StringMatch = "\\(function\\(p,a,c,k,e,d\\)\\{(.+?)\\}\\('(.+?)',(.+?),(.+?),'(.+?)'(.+?),(.+?),";
		
		Pattern pattern = Pattern.compile(Base64StringMatch);
		Matcher match = pattern.matcher(HtmlInfo);
		
		while(match.find())
		{
			Para1 = match.group(2);
			Para2 = match.group(3);
			Para3 = match.group(4);
			Para4 = match.group(5);
			Para5 = match.group(7);
		}
		
		if(null == Para1)
		{
			return null;
		}
		
		return paraseInfo(Para1,Integer.parseInt(Para2), Integer.parseInt(Para3), Para4,Integer.parseInt(Para5));
	}
	//此处直接调用修改自源HTML的脚本来进行解码,另外，此处采用eval(String)而不用eval(FileReader)是因为打包成JAR后无法使用后者
	private String paraseInfo(String para1, int para2, int para3, String para4, int para5)
	{
		ScriptEngineManager engineManager = new ScriptEngineManager();
		ScriptEngine engine = engineManager.getEngineByName("js");
		try {
			engine.eval(ValidConfig.JSFile);
			Invocable inv = (Invocable)engine;
			String ParkerInfo = (String)inv.invokeFunction("parase", para1,para2,para3,para4,para5);
			return ParkerInfo;
		} catch (Exception e) {
			LOG.log(e.getMessage(), LOG.NormalType);
			e.printStackTrace();
			return null;
		}	
	}
}
