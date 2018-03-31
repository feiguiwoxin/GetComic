package ComicCore;

import manhuagui.DlManhuagui;

public class comicfactory {
	public Comic create(int websiteIdx)
	{
		if(websiteIdx == 0)
		{
			return new DlManhuagui();
		}
		/*
		else if(websiteIdx == 1)注意此处的等号右边的值就是你在SelectWebSite中添加网址的次序-1
		{
			return new 你实现Comic接口的那个类
		}
		*/
		
		return null;
	}
}
