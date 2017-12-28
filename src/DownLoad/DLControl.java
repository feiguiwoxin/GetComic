package DownLoad;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import Config.LOG;
import GetComic.Chapter;
import GetComic.GetChapter;
import UI.FrameComic;

public class DLControl {
	private String ComicNum = null;
	private int PoolSize = 0;
	private String FilePath = null;
	private FrameComic fc = null;
	private ExecutorService fixpool = null;
	
	public boolean AnalyChapter()
	{
		if(null == ComicNum) return false;
		ArrayList<Chapter> Chapters = new GetChapter(ComicNum).getChapter();
		if(Chapters.isEmpty())
		{
			LOG.log("分解章节失败，请检查漫画ID是否正确：" + ComicNum);
			return false;
		}
		fc.addChapters(Chapters);
		return true;
	}
	
	public void InterputDL()
	{
		fc.ActiveAllbox();
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
			fixpool.shutdownNow();
		}	
		else if(fixpool != null && PoolSize != poolsize)
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
		File HeadDir = new File("/Comic/" + ComicNum);
		
		if(!(HeadDir.exists() && HeadDir.isDirectory()))
		{
			if(!HeadDir.mkdirs())
			{
				LOG.log("创建初始文件夹失败，请检查磁盘是否已满/地址错误/文件夹已存在:" + HeadDir.getAbsolutePath());
				return false;
			}
		}
		
		ExecutorService fixpool = Executors.newFixedThreadPool(PoolSize);
		
		for(Chapter c : Chapters)
		{	
			DLThread dl = new DLThread(c, HeadDir.getAbsolutePath());
			dl.setFC(fc);
			fixpool.execute(dl);	
		}
		
		//fixpool.shutdown();
		return true;
	}

	public void setComicNum(String comicNum) {
		ComicNum = comicNum;
	}
}
