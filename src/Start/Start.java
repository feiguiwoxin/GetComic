package Start;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import GetComic.Chapter;
import GetComic.GetChapter;
import GetComic.GetHtml;
import GetComic.GetPicture;
import GetComic.SaveImg;

public class Start {

	public static void main(String[] args)
	{
		//TODO 做成有UI画面的输入框
		ArrayList<Chapter> result = null;
		
		result = (new GetChapter("1231231231231")).getChapter();
		for(Chapter ch:result)
		{
			System.out.println(ch.getTitle() + "," + ch.getHtml());
		}
		
		//new GetPicture("http://www.manhuagui.com/comic/16725/172846.html");
		
		//new SaveImg("http://i.hamreus.com:8080/ps1/d/djssg/re/%E7%AC%AC130%E5%9B%9E/2_6313.jpg.webp", "html/", "02.webp").SavePicture();
		

	}
}

