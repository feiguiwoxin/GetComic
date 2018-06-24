package DownLoad;

import java.io.File;
import java.util.ArrayList;

import ComicCore.Chapter;
import ComicCore.Comic;
import ComicCore.ComicInfo;
import Start.LOG;
import UI.FrameComic;

public class DLThread implements Runnable{
	private String html = null;
	private String title = null;
	private String path = null;
	private FrameComic fc = null;
	private int len = 0;
	private Chapter chapter = null;
	private Comic getComic = null;
	
	public DLThread(Chapter chapter,String path, Comic getComic)
	{
		this.chapter = chapter;
		this.html = chapter.getHtml();
		this.title = chapter.getTitle();
		this.path = path;
		this.getComic = getComic;
		processTitle();
	}
	
	public void setFC(FrameComic fc)
	{
		this.fc = fc;
	}
	
	@Override
	public void run() {
		
		if(!DLControl.RunThread)
		{
			return;
		}
		
		File dir = new File(path + "/" + title);
		if(!(dir.exists() && dir.isDirectory()))
		{
			if(!dir.mkdir())
			{
				LOG.log("创建章节文件夹失败，请检查磁盘是否已满/地址错误:" + dir.getAbsolutePath());
				return;
			}
		}			
		
		ArrayList<String> PicPath = getComic.GetPicUrlByChapter(html);
		if(PicPath == null)
		{
			fc.FinishDl(chapter, 0);
			return;
		}
		else if(PicPath.isEmpty())
		{
			LOG.log("解析图片地址失败:" + html);
			fc.FinishDl(chapter, 0);
			return;
		}
		
		len = PicPath.size();
		int numlen = getNumLen(len);
		int index = 1;
		int result = 0;
		
		for(String path : PicPath)
		{				
			fc.UpdateDLinfo(chapter, index, len);
			result = ComicInfo.SavePicture(path, dir.getAbsolutePath() + "/", String.format("%0" + numlen + "d", index) + ".jpg", html);
			
			if(!DLControl.RunThread)
			{
				return;
			}
			
			if(0 == result)
			{
				LOG.log("下载图片地址失败:" + path);
				fc.FinishDl(chapter, 0);
				return;
			}
			
			index ++;
		}
		
		fc.FinishDl(chapter, 1);
	}
	//一些带奇怪符号的名称可能会导致创建文件夹失败，所以需要进行预处理
	private void processTitle()
	{
		title = title.replaceAll("[\\^%&',.;=?$]+", "");
	}
	
	private int getNumLen(int num)
	{
		int len = 0;
		
		do
		{
			num = num /10;
			len ++;
		}
		while(num != 0);
		
		return len;
	}
}
