package GetComic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//该类用于获取漫画的章节名和各章节的URL
public class GetChapter {
	private ArrayList<Chapter> Chapter = new ArrayList<Chapter>();
	private String UrlAdd = null;
	private String ComicNum;
	
	public GetChapter(String ComicNum)
	{
		this.ComicNum = ComicNum;
		this.UrlAdd = "http://www.manhuagui.com/comic/" + ComicNum + "/" + "?" + (new Date()).getTime();
		AnalyHtml(GetHtml.GetInfo(this.UrlAdd));
		//TODO 排序待优化，后续考虑通过标题数字进行排序优化，需要考虑卷，回关键字
		Collections.reverse(Chapter);
	}	
		
	//分析网页内容，提取章节标题和URL
	private void AnalyHtml(String HtmlInfo)
	{
		String Title = null;
		String Html = null;
		
		if(null == HtmlInfo) return;
		//使用正则表达式进行匹配
		String Rex = "<li><a href=\"/comic/" + ComicNum + "/(.+?)\" title=\"(.+?)\"";
		Pattern pattern = Pattern.compile(Rex);
		Matcher matcher = pattern.matcher(HtmlInfo);
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
}
