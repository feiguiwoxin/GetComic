package DownLoad;

import java.io.File;
import java.util.ArrayList;

import GetComic.Chapter;
import GetComic.GetPicture;
import GetComic.SaveImg;
import UI.FrameComic;

public class DLThread implements Runnable{
	private String html = null;
	private String title = null;
	private String path = null;
	private FrameComic fc = null;
	private int len = 0;
	private Chapter chapter = null;
	private SaveImg currimg = null;
	
	public DLThread(Chapter chapter,String path)
	{
		this.chapter = chapter;
		this.html = chapter.getHtml();
		this.title = chapter.getTitle();
		this.path = path;
		processTitle();
	}
	
	public void setFC(FrameComic fc)
	{
		this.fc = fc;
	}
	
	@Override
	public void run() {
		File dir = new File(path + "/" + title);
		if(!(dir.exists() && dir.isDirectory()))
		{
			if(!dir.mkdir())
			{
				System.out.println("创建章节文件夹失败，请检查磁盘是否已满/地址错误/文件夹已存在:" + dir.getAbsolutePath());
				return;
			}
		}			
		
		ArrayList<String> PicPath = new GetPicture(html).getPicturePath();
		
		if(PicPath.isEmpty())
		{
			System.out.println("解析图片地址失败，需要重新适配:" + html);
			return;
		}
		
		len = PicPath.size();
		int index = 1;
		int result = 0;
		
		for(String path : PicPath)
		{				
			fc.UpdateDLinfo(chapter, index, len);
			currimg = new SaveImg(path, dir.getAbsolutePath() + "/", index + ".jpg");
			result = currimg.SavePicture();
			if(0 == result)
			{
				System.out.println("下载图片地址失败，需要重新适配:" + path);
				return;
			}
			else if(2 == result)
			{
				return;
			}
			
			index ++;
		}
		
		fc.FinishDl(chapter);
	}
	//一些带奇怪符号的名称可能会导致创建文件夹失败，所以需要进行预处理
	private void processTitle()
	{
		title = title.replaceAll("[\\^%&',.;=?$]+", "");
	}
	
	public void setInterrput()
	{
		currimg.setInterrput();
	}
}
