package DownLoad;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Config.LOG;
import GetComic.GetPicture;
import GetComic.SaveImg;

public class DLThread implements Runnable{
	private String html = null;
	private String title = null;
	private String path = null;
	
	public DLThread(String html,String title,String path)
	{
		this.html = html;
		this.title = title;
		this.path = path;
		processTitle();
	}
	
	@Override
	public void run() {
		//System.out.println(Thread.currentThread().getName() + "开始下载" + title);
		File dir = new File(path + "/" + title);
		if(dir.exists() && dir.isDirectory())
		{
			dir.delete();
		}
		
		if(!dir.mkdir())
		{
			LOG.log("创建章节文件夹失败，请检查磁盘是否已满/地址错误/文件夹已存在:" + dir.getAbsolutePath());
			return;
		}
		
		ArrayList<String> PicPath = new GetPicture(html).getPicturePath();
		
		if(PicPath.isEmpty())
		{
			LOG.log("解析图片地址失败，需要重新适配:" + html);
			return;
		}
		
		int index = 1;
		int result = 0;
		for(String path : PicPath)
		{
			//System.out.println(Thread.currentThread().getName() + "开始下载第" + index + "P");
			result = (new SaveImg(path, dir.getAbsolutePath() + "/", index + ".jpg")).SavePicture();
			if(0 == result)
			{
				LOG.log("下载图片地址失败，需要重新适配:" + path);
				return;
			}
			index ++;
		}
	}
	//一些带奇怪符号的名称可能会导致创建文件夹失败，所以需要进行预处理
	private void processTitle()
	{
		title = title.replaceAll("[\\^%&',;=?$]+", "");
		System.out.println(title);
	}

}
