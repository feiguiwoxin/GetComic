package UI;

import java.awt.Toolkit;
import java.util.ArrayList;

//这个类用于记录一些常用的变量
public class UIConfig {
	public static final int WinH = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int WinW = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int ComicH = 600;
	public static final int ComicW = 500;
	public static ArrayList<ComicWebInfo> ComicWebList = new ArrayList<ComicWebInfo>();
	
	static
	{
		ComicWebList.add(new ComicWebInfo("manhuagui.DlManhuagui", "漫画柜"));
		/*第一个参数表明你新增Comic子类的地址，需要包含包名
		 * 第二个参数表明你想给这个网站取的名字，会显示在主界面的选择框中
		 * */
		//ComicWebList.add(new ComicWebInfo("类地址", "漫画名"));
	}
}

class ComicWebInfo
{
	private String package_addr = null;
	private String name = null;
	
	public ComicWebInfo(String package_addr,String name)
	{
		this.package_addr = package_addr;
		this.name = name;
	}
	
	public String getpackage_addr()
	{
		return package_addr;
	}
	
	public String getname()
	{
		return name;
	}
}
