package ComicCore;

import java.util.ArrayList;
/****************************
 * 对于漫画下载软件来说：
 * 1）获取到漫画的章节信息,可以用于显示在界面上让用户选择哪些章节
 * 2）获取每个章节中的漫画图片地址，按照地址进行下载即可
 * 3) 获取漫画书名，用于保存时用作保存文件夹名字
 * 因此抽象出这两个方法作为接口，可以认为任何类只要实现了这两个方法，就都可以进行漫画下载了
 ****************************/
public interface Comic{
	public ArrayList<Chapter> GetChapetr(String ComicId);
	public ArrayList<String> GetPicUrlByChapter(String ChapterUrl);
	public String GetBookName();
}