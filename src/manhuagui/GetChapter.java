package manhuagui;

import java.util.ArrayList;
import java.util.Collections;
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
//该类用于获取漫画的章节名和各章节的URL
public class GetChapter {
	private ArrayList<Chapter> Chapter = new ArrayList<Chapter>();
	private String UrlAdd = null;
	private String ComicNum = null;
	private String BookName = null;
	
	public GetChapter(String ComicNum)
	{
		this.ComicNum = ComicNum;
		this.UrlAdd = "http://www.manhuagui.com/comic/" + ComicNum + "/" + "?" + (new Date()).getTime();
		AnalyHtml(GetHtml.GetInfo(this.UrlAdd));
		//TODO 排序待优化，后续考虑通过标题数字进行排序优化，需要考虑卷，回关键字
		Collections.reverse(Chapter);
	}	
		
	//分析网页内容，提取章节标题和URL以及书名
	private void AnalyHtml(String HtmlInfo)
	{
		String Title = null;
		String Html = null;
		
		if(null == HtmlInfo) return;
		
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
			String Rex = "VIEWSTATE\" value=\"(.+?)\"";
			String keyword = null;
			String tmpHtmlInfo = null;
			pattern = Pattern.compile(Rex);
			matcher = pattern.matcher(HtmlInfo);
			
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
		}
		
		//使用正则表达式进行匹配
		String Rex = "<li><a href=\"/comic/" + ComicNum + "/(.+?)\" title=\"(.+?)\"";
		pattern = Pattern.compile(Rex);
		matcher = pattern.matcher(HtmlInfo);
		while(matcher.find())
		{
			Title = matcher.group(2);
			Html = "http://www.manhuagui.com/comic/" + ComicNum + "/" + matcher.group(1);
			Chapter.add(new Chapter(Title, Html));
		}
		
		return;
	}
	
	public ArrayList<Chapter> getChapter() {
		return Chapter;
	}
	
	public String getBookName()
	{
		return this.BookName;
	}
}
