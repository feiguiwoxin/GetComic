package Start;

import java.util.ArrayList;

import GetComic.Chapter;
import GetComic.GetChapter;

public class Start {

	public static void main(String[] args)
	{
		//TODO 做成有UI画面的输入框
		ArrayList<Chapter> result = null;
		
		result = (new GetChapter("18008")).getChapter();
		for(Chapter ch:result)
		{
			System.out.println(ch.getTitle() + "," + ch.getHtml());
		}
	}
}
