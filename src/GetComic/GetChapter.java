package GetComic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
		String SearchWord = "<li><a href=\"/comic/" + ComicNum;
		int index = 0;
		while(-1 != (index = result.indexOf(SearchWord)))
		{
			//截取是为了方便后续的搜索速度，同时也让查找到的第一个特定字符串满足我们的需要
			result = result.substring(index);
			//查找网址内容
			startidx = result.indexOf(ComicNum);
			endidx = result.indexOf("\"", startidx);
			Html = "http://www.manhuagui.com/comic/" + ComicNum + "/" + result.substring(startidx + LenOfComic + 1, endidx);
			//查找标题内容
			startidx = result.indexOf("title");
			endidx = result.indexOf("\"", startidx + 8);
			Title = result.substring(startidx + 7, endidx);
			//存入列表中
			Chapter.add(new Chapter(Title, Html));
			result = result.substring(endidx);
		}	
		
		return;
	}

	public ArrayList<Chapter> getChapter() {
		return Chapter;
	}
}
