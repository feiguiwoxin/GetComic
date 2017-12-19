package GetComic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetChapter {
	private ArrayList<Chapter> Chapter = new ArrayList<Chapter>();
	private String UrlAdd = null;
	private String ComicNum;
	private int LenOfComic;
	private String result = null;
	
	public GetChapter(String ComicNum)
	{
		this.ComicNum = ComicNum;
		this.LenOfComic = this.ComicNum.length();
		this.UrlAdd = "http://www.manhuagui.com/comic/" + ComicNum + "/" + "?" + (new Date()).getTime();
		GetHtml();
		AnalyHtml();
		//TODO 排序待优化，后续考虑通过标题数字进行排序优化，需要考虑卷，回关键字
		Collections.reverse(Chapter);
	}
	
	//设置HTTP访问申请头
	private void SetProp(URLConnection con)
	{
		con.setRequestProperty("accept", "*/*");
		con.setRequestProperty("connection", "Keep-Alive");
		con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");
		return;
	}
	
	//抓取网页内容
	private void GetHtml()
	{
		if(null == UrlAdd) return;
		String line;
		URL url;
		
		try {
			url = new URL(UrlAdd);
			URLConnection urlcon = url.openConnection();
			SetProp(urlcon);
			urlcon.connect();
			BufferedReader urlRead = new BufferedReader(new InputStreamReader(urlcon.getInputStream(), "utf-8"));
			while((line = urlRead.readLine()) != null)
			{
				result += line;
			}
		} catch (Exception e) {
			//TODO 弹出对话框来表示输入的数字有问题
			System.out.println("Error");
			e.printStackTrace();
		}		
		
		return;
	}
	
	//分析网页内容，提取回合标题和内容
	private void AnalyHtml()
	{
		String Title = null;
		String Html = null;
		int startidx = 0;
		int endidx = 0;
		
		if(null == result) return;
	
		String Rex = "<li><a href=\"/comic/" + ComicNum + "/(.+?)\" title=\"(.+?)\"";
		Pattern pattern = Pattern.compile(Rex);
		Matcher matcher = pattern.matcher(result);
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
