package DownLoad;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import ComicCore.Chapter;
import ComicCore.Comic;
import ComicCore.comicfactory;
import UI.FrameComic;
//这个类为下载控制中心，主要用于与面板的通信和下载线程的控制
public class DLControl {
	public static volatile boolean RunThread = true;
	
	private String ComicId = null;
	private String package_addr = null;
	private int PoolSize = 0;
	private String FilePath = null;
	private FrameComic fc = null;
	private ExecutorService fixpool = null;
	private String BookName = null;
	private Comic getComic = null;
	
	//获取网页的章节
	public boolean AnalyChapter()
	{
		if(null == ComicId) return false;
		getComic = new comicfactory().create(package_addr);
		if(null == getComic) return false;
		ArrayList<Chapter> Chapters = getComic.GetChapetr(ComicId);
		if(null == Chapters) return false;
		BookName = getComic.GetBookName();
		if (BookName == null) BookName = "default";
		
		if(Chapters.isEmpty())
		{
			return false;
		}
		fc.addChapters(Chapters);
		return true;
	}

	public void InterrputDL()
	{
		if(null != fixpool)
		{
			fixpool.shutdownNow();
			DLControl.RunThread = false;	
		}
		fc.InterrputDL();
	}
	
	public DLControl(FrameComic fc)
	{
		this.fc = fc;
	}
	
	public boolean StartDL(int poolsize)
	{
		ArrayList<Chapter> Chapters = fc.getDLInfo();
		if(fixpool == null)
		{
			PoolSize = poolsize;
			fixpool = Executors.newFixedThreadPool(PoolSize);
		}	
		else if(fixpool != null)
		{
			PoolSize = poolsize;
			fixpool.shutdownNow();	
			fixpool = Executors.newFixedThreadPool(PoolSize);			
		}
		
		if(Chapters.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "没有任何选择章节", "错误说明", JOptionPane.CLOSED_OPTION);
			return false;
		}
		
		if(ThreadControl(Chapters))
		{
			fc.disableAllbox();
			return true;
		}

		return false;
	}
	
	public boolean ThreadControl(ArrayList<Chapter> Chapters)
	{	
		File HeadDir = new File(FilePath + "/" +  BookName + "(" + ComicId + ")");
		
		if(!(HeadDir.exists() && HeadDir.isDirectory()))
		{
			if(!HeadDir.mkdirs())
			{
				JOptionPane.showMessageDialog(null, "创建初始文件夹失败，请检查磁盘是否已满/地址错误:"+ HeadDir.getAbsolutePath(), "错误说明", JOptionPane.CLOSED_OPTION);
				return false;
			}
		}
		
		for(Chapter c : Chapters)
		{	
			DLThread dl = new DLThread(c, HeadDir.getAbsolutePath(), getComic);
			dl.setFC(fc);
			fixpool.execute(dl);	
		}
		
		return true;
	}

	public void setComicId(String ComicId) {
		this.ComicId = ComicId;
	}

	public void setFilePath(String filePath) {
		FilePath = filePath;
	}

	public void setpackage_addr(String package_addr) {
		this.package_addr = package_addr;
	}
}
