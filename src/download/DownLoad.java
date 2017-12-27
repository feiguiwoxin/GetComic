package download;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import GetComic.GetPicture;
import GetComic.SaveImg;

public class DownLoad implements Runnable{
	private String html = null;
	private String title = null;
	private String path = null;
	
	public DownLoad(String html,String title,String path)
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
			JOptionPane.showConfirmDialog(null, "创建章节文件夹失败，请检查磁盘是否已满/地址错误/文件夹已存在"+ dir.getPath(), "错误说明", JOptionPane.CLOSED_OPTION);
			//TODO 后续写入到日志当中
			System.out.println("创建章节文件夹失败，请检查磁盘是否已满/地址错误/文件夹已存在\n" + dir.getPath());
			return;
		}
		
		ArrayList<String> PicPath = new GetPicture(html).getPicturePath();
		
		if(PicPath.isEmpty())
		{
			//TODO 后续写入到日志当中
			System.out.println("解析图片地址失败，需要重新适配");
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
				//TODO 后续写入到日志当中
				System.out.println("下载图片地址失败，需要重新适配");
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
